package com.tml.otowbackend.engine.tree.service;


import com.tml.otowbackend.engine.tree.common.RCode;
import com.tml.otowbackend.engine.tree.common.ServeException;
import com.tml.otowbackend.engine.tree.core.FileContentReader;
import com.tml.otowbackend.engine.tree.core.SupportedLanguages;
import com.tml.otowbackend.engine.tree.core.VirtualFileNodeFactory;
import com.tml.otowbackend.engine.tree.core.VirtualFileTreeCache;
import com.tml.otowbackend.engine.tree.entity.dto.AddFileDTO;
import com.tml.otowbackend.engine.tree.entity.dto.AddFolderDTO;
import com.tml.otowbackend.engine.tree.entity.dto.UpdateFileDTO;
import com.tml.otowbackend.engine.tree.entity.po.VirtualFileNode;
import com.tml.otowbackend.engine.tree.entity.po.VirtualFileTree;
import com.tml.otowbackend.engine.tree.entity.po.VirtualFolderNode;
import com.tml.otowbackend.engine.tree.entity.vo.NodeVO;
import com.tml.otowbackend.engine.tree.utils.UUIDUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
@Service
public class VirtualFileService implements IVirtualFileService {

    /**
     * 初始化虚拟文件树
     *
     * @param directoryPath 文件夹路径
     * @param treeId        虚拟树ID（可选）
     * @return 虚拟树ID
     */
    @Override
    public String initializeVirtualTree(String directoryPath, String treeId) {
        if (treeId == null || treeId.isEmpty()) {
            treeId = UUIDUtils.generateShortUUID();
        } else {
            if (VirtualFileTreeCache.contains(treeId)) {
                throw ServeException.of(RCode.NUMBER_OUT, "树ID已存在: " + treeId);
            }
        }

        File rootDir = new File(directoryPath);
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            throw ServeException.of(RCode.PARAMS_ERROR, "路径不存在或不是文件夹: " + directoryPath);
        }

        // 构建虚拟文件树
        VirtualFolderNode rootNode = buildVirtualFolderNode(rootDir, null);
        VirtualFileTree virtualTree = new VirtualFileTree(treeId, rootNode);
        VirtualFileTreeCache.put(treeId, virtualTree);
        return treeId;
    }

    /**
     * 递归构建虚拟文件夹节点
     *
     * @param directory 文件夹
     * @param parentId  父节点ID
     * @return VirtualFolderNode 实例
     */
    private VirtualFolderNode buildVirtualFolderNode(File directory, String parentId) {
        String nodeId = directory.getPath();
        VirtualFolderNode folderNode = VirtualFolderNode.createStart(nodeId, directory.getName(), parentId);

        File[] children = directory.listFiles();
        if (children != null) {
            for (File child : children) {
                if (child.isDirectory()) {
                    VirtualFolderNode childFolder = buildVirtualFolderNode(child, nodeId);
                    folderNode.addChild(childFolder);
                } else if (child.isFile()) {
                    if (SupportedLanguages.isDisplayableFile(getFileExtension(child.getName())) || FileContentReader.isFileTooLarge(child.length())) {
                        // 使用工厂类创建文件节点
                        VirtualFileNode fileNode = VirtualFileNodeFactory.createVirtualFileNode(child);
                        folderNode.addFile(fileNode);
                    }
                }
            }
        }

        return folderNode;
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名称
     * @return 文件扩展名
     */
    private String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return (lastIndex > 0) ? fileName.substring(lastIndex + 1) : "unknown";
    }

    // CRUD 操作

    /**
     * 添加文件夹
     *
     * @param treeId  虚拟树ID
     * @param request 添加文件夹的请求
     * @return 新文件夹的ID
     */
    @Override
    public String addFolder(String treeId, AddFolderDTO request) {
        VirtualFileTree tree = getTree(treeId);
        VirtualFolderNode parent = getParentFolder(tree, treeId, request.getParentId());

        // 检查重名
        boolean exists = parent.getChildren().stream().anyMatch(child -> child.getName().equals(request.getFolderName()));
        if (exists) {
            throw ServeException.of(RCode.NUMBER_OUT, "文件夹已存在: " + request.getFolderName());
        }

        String folderName = request.getFolderName();
        VirtualFolderNode newFolder = VirtualFolderNode.createAdd(parent.getId() + "/" + folderName, request.getFolderName(), parent.getId());
        parent.addChild(newFolder);
        tree.addFolder(newFolder);
        return newFolder.getId();
    }

    /**
     * 添加文件
     *
     * @param treeId  虚拟树ID
     * @param request 添加文件的请求
     * @return 新文件的ID
     */
    @Override
    public String addFile(String treeId, AddFileDTO request) {
        VirtualFileTree tree = getTree(treeId);
        VirtualFolderNode parent = getParentFolder(tree, treeId, request.getParentId());

        String fileType = getFileExtension(request.getFileName());
        if (!SupportedLanguages.isDisplayableFile(fileType)) {
            throw ServeException.of(RCode.PARAMS_ERROR, "文件类型不支持展示: " + fileType);
        }

        // 检查重名
        boolean exists = parent.getFiles().stream().anyMatch(file -> file.getName().equals(request.getFileName()));
        if (exists) {
            throw ServeException.of(RCode.NUMBER_OUT, "文件已存在: " + request.getFileName());
        }

        String fileName = request.getFileName();
        VirtualFileNode newFile = VirtualFileNode.createAdd(parent.getId() + "/" + fileName, request.getFileName(), fileType, 0, request.getContent());
        parent.addFile(newFile);
        tree.addFile(newFile);
        return newFile.getId();
    }

    /**
     * 覆盖文件内容
     *
     * @param treeId  虚拟树ID
     * @param request 修改文件内容的请求
     */
    @Override
    public void updateFileContent(String treeId, UpdateFileDTO request) {
        VirtualFileTree tree = getTree(treeId);
        VirtualFileNode file = tree.getFileMap().get(request.getFileId());
        if (file == null) {
            throw ServeException.of(RCode.PARAMS_ERROR, "未找到文件ID: " + request.getFileId());
        }

        if (!SupportedLanguages.isDisplayableFile(file.getType())) {
            throw ServeException.of(RCode.PARAMS_ERROR, "文件类型不支持展示: " + file.getType());
        }

        if (FileContentReader.isFileTooLarge(file.getSize())) {
            throw ServeException.of(RCode.PARAMS_ERROR, "文件内容过大，无法覆盖");
        }

        file.setRawLines(request.getContent());
    }

    private VirtualFolderNode getParentFolder(VirtualFileTree tree, String treeId, String parentId) {
        VirtualFolderNode parent;
        if (parentId == null || parentId.isEmpty() || parentId.equals(treeId)) {
            parent = tree.getRoot();
        } else {
            parent = tree.getFolderMap().get(parentId);
            if (parent == null) {
                throw ServeException.of(RCode.PARAMS_ERROR, "未找到父节点ID: " + parentId);
            }
        }
        if (parent == null) {
            throw ServeException.of(RCode.PARAMS_ERROR, "未找到父文件夹ID: " + parentId);
        }
        return parent;
    }

    /**
     * 删除文件夹或文件
     *
     * @param treeId 虚拟树ID
     * @param nodeId 节点ID
     */
    @Override
    public void deleteNode(String treeId, String nodeId) {
        VirtualFileTree tree = getTree(treeId);
        if (tree.getRoot().getId().equals(nodeId)) {
            throw ServeException.of(RCode.PARAMS_ERROR, "无法删除根节点");
        }

        VirtualFolderNode parent = findParentOfNode(tree.getRoot(), nodeId);
        if (parent == null) {
            throw ServeException.of(RCode.PARAMS_ERROR, "未找到节点ID: " + nodeId);
        }

        // 尝试删除子文件夹
        VirtualFolderNode folderToRemove = parent.getChildren().stream()
                .filter(folder -> folder.getId().equals(nodeId))
                .findFirst()
                .orElse(null);
        if (folderToRemove != null) {
            parent.removeChild(folderToRemove);
            tree.removeFolder(nodeId);
            return;
        }

        // 尝试删除文件
        VirtualFileNode fileToRemove = parent.getFiles().stream()
                .filter(file -> file.getId().equals(nodeId))
                .findFirst()
                .orElse(null);
        if (fileToRemove != null) {
            parent.removeFile(fileToRemove);
            tree.removeFile(nodeId);
            return;
        }

        throw ServeException.of(RCode.PARAMS_ERROR, "未找到节点ID: " + nodeId);
    }

    /**
     * 修改文件或文件夹名称
     *
     * @param treeId  虚拟树ID
     * @param nodeId  节点ID
     * @param newName 新名称
     */
    @Override
    public void renameNode(String treeId, String nodeId, String newName) {
        VirtualFileTree tree = getTree(treeId);
        VirtualFolderNode parent = findParentOfNode(tree.getRoot(), nodeId);
        if (parent == null) {
            throw ServeException.of(RCode.PARAMS_ERROR, "未找到节点ID: " + nodeId);
        }

        // 修改文件夹名称
        VirtualFolderNode folder = parent.getChildren().stream()
                .filter(child -> child.getId().equals(nodeId))
                .findFirst()
                .orElse(null);
        if (folder != null) {
            // 检查重名
            boolean exists = parent.getChildren().stream()
                    .anyMatch(c -> c.getName().equals(newName) && !c.getId().equals(nodeId));
            if (exists) {
                throw ServeException.of(RCode.NUMBER_OUT, "文件夹名称已存在: " + newName);
            }
            folder.setName(newName);
            return;
        }

        // 修改文件名称
        VirtualFileNode file = parent.getFiles().stream()
                .filter(f -> f.getId().equals(nodeId))
                .findFirst()
                .orElse(null);
        if (file != null) {
            // 检查重名
            boolean exists = parent.getFiles().stream()
                    .anyMatch(f -> f.getName().equals(newName) && !f.getId().equals(nodeId));
            if (exists) {
                throw ServeException.of(RCode.NUMBER_OUT, "文件名称已存在: " + newName);
            }
            file.setName(newName);
            return;
        }

        throw ServeException.of(RCode.ERROR, "未找到节点ID: " + nodeId);
    }

    /**
     * 获取某个父节点下的子节点列表
     *
     * @param treeId   虚拟树ID
     * @param parentId 父节点ID（可以为空表示根）
     * @return 子节点列表
     */
    @Override
    public List<NodeVO> getChildrenNodes(String treeId, String parentId) {
        VirtualFileTree tree = getTree(treeId);
        VirtualFolderNode parent = getParentFolder(tree, treeId, parentId);

        List<NodeVO> children = new ArrayList<>();

        // 添加子文件夹
        for (VirtualFolderNode childFolder : parent.getChildren()) {
            children.add(NodeVO.folder(childFolder.getId(), childFolder.getName()));
        }

        // 添加文件
        for (VirtualFileNode file : parent.getFiles()) {
            children.add(NodeVO.file(file.getId(), file.getName(), file.getType(), file.getSize()));
        }

        return children;
    }

    /**
     * 获取文件内容
     *
     * @param treeId 虚拟树ID
     * @param fileId 文件ID
     * @return 文件内容
     */
    @Override
    public List<String> getFileContent(String treeId, String fileId) {
        VirtualFileTree tree = getTree(treeId);
        VirtualFileNode file = tree.getFileMap().get(fileId);
        if (file == null) {
            throw ServeException.of(RCode.PARAMS_ERROR, "未找到文件ID: " + fileId);
        }

        if (!SupportedLanguages.isDisplayableFile(file.getType())) {
            return List.of(SupportedLanguages.UNSUPPORTED_FILE_MESSAGE);
        }

        if (FileContentReader.isFileTooLarge(file.getSize())) {
            return List.of(SupportedLanguages.UNSUPPORTED_FILE_MESSAGE);
        }

        return file.getRawLines();
    }

    /**
     * 导出虚拟文件树到指定目录
     *
     * @param treeId     虚拟树ID
     * @param exportPath 导出路径
     */
    @Override
    public void exportVirtualTree(String treeId, String exportPath) {
        VirtualFileTree tree = getTree(treeId);
        File exportDir = new File(exportPath);
        if (!exportDir.exists()) {
            boolean created = exportDir.mkdirs();
            if (!created) {
                throw ServeException.of(RCode.ERROR, "无法创建导出目录: " + exportPath);
            }
        }

        exportFolderNode(tree.getRoot(), exportDir);
    }

    /**
     * 递归导出文件夹节点
     *
     * @param node        当前节点
     * @param currentDir  当前目录
     */
    private void exportFolderNode(VirtualFolderNode node, File currentDir) {
        File newDir = new File(currentDir, node.getName());
        if (!newDir.exists()) {
            boolean created = newDir.mkdir();
            if (!created) {
                throw ServeException.of(RCode.ERROR, "无法创建目录: " + newDir.getAbsolutePath());
            }
        }

        // 导出文件
        for (VirtualFileNode file : node.getFiles()) {
            File newFile = new File(newDir, file.getName());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
                for (String line : file.getRawLines()) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                throw ServeException.of(RCode.ERROR, "无法创建文件: " + newFile.getAbsolutePath());
            }
        }

        // 递归导出子文件夹
        for (VirtualFolderNode child : node.getChildren()) {
            exportFolderNode(child, newDir);
        }
    }

    /**
     * 获取虚拟文件树
     *
     * @param treeId 虚拟树ID
     * @return VirtualFileTree 实例
     */
    private VirtualFileTree getTree(String treeId) {
        VirtualFileTree tree = VirtualFileTreeCache.get(treeId);
        if (tree == null) {
            throw ServeException.of(RCode.PARAMS_ERROR, "虚拟文件树不存在: " + treeId);
        }
        return tree;
    }

    /**
     * 根据节点ID查找父文件夹节点
     *
     * @param current 当前节点
     * @param nodeId  要查找的节点ID
     * @return VirtualFolderNode 实例或 null
     */
    private VirtualFolderNode findParentOfNode(VirtualFolderNode current, String nodeId) {
        for (VirtualFolderNode child : current.getChildren()) {
            if (child.getId().equals(nodeId)) {
                return current;
            }
            VirtualFolderNode found = findParentOfNode(child, nodeId);
            if (found != null) return found;
        }

        for (VirtualFileNode file : current.getFiles()) {
            if (file.getId().equals(nodeId)) {
                return current;
            }
        }

        return null;
    }
}