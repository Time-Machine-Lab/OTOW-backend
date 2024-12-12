package com.tml.otowbackend.controller;

import com.tml.otowbackend.engine.tree.common.R;
import com.tml.otowbackend.engine.tree.entity.dto.AddFileDTO;
import com.tml.otowbackend.engine.tree.entity.dto.AddFolderDTO;
import com.tml.otowbackend.engine.tree.entity.dto.UpdateFileDTO;
import com.tml.otowbackend.engine.tree.entity.vo.NodeVO;
import com.tml.otowbackend.engine.tree.service.IVirtualFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/virtual-tree")
@RequiredArgsConstructor
public class VirtualFileController {

    private final IVirtualFileService virtualFileService;

    /**
     * 初始化虚拟文件树
     *
     * @param directoryPath 文件夹路径
     * @param treeId        虚拟树ID（可选）
     * @return 虚拟树ID
     */
    @PostMapping("/initialize")
    public R<String> initializeVirtualTree(
            @RequestParam String directoryPath,
            @RequestParam(required = false) String treeId) {
        String generatedTreeId = virtualFileService.initializeVirtualTree(directoryPath, treeId);
        return R.success("虚拟文件树初始化成功", generatedTreeId);
    }

    /**
     * 获取某个父节点下的子节点列表
     *
     * @param treeId   虚拟树ID
     * @param parentId 父节点ID（可以为空表示根）
     * @return 子节点列表
     */
    @GetMapping("/{treeId}/nodes")
    public R<List<NodeVO>> getChildrenNodes(
            @PathVariable String treeId,
            @RequestParam(required = false) String parentId) {
        List<NodeVO> children = virtualFileService.getChildrenNodes(treeId, parentId);
        return R.success("获取子节点成功", children);
    }

    /**
     * 获取文件内容
     *
     * @param treeId 虚拟树ID
     * @param fileId 文件ID
     * @return 文件内容
     */
    @GetMapping("/{treeId}/file/{fileId}/content")
    public R<List<String>> getFileContent(
            @PathVariable String treeId,
            @PathVariable String fileId) {
        List<String> content = virtualFileService.getFileContent(treeId, fileId);
        return R.success("获取文件内容成功", content);
    }

    /**
     * 添加文件夹
     */
    @PostMapping("/{treeId}/folder")
    public R<String> addFolder(
            @PathVariable String treeId,
            @Validated @RequestBody AddFolderDTO request) {
        String newFolderId = virtualFileService.addFolder(treeId, request);
        return R.success("文件夹添加成功", newFolderId);
    }

    /**
     * 添加文件
     */
    @PostMapping("/{treeId}/file")
    public R<String> addFile(
            @PathVariable String treeId,
            @Validated @RequestBody AddFileDTO request) {
        String newFileId = virtualFileService.addFile(treeId, request);
        return R.success("文件添加成功", newFileId);
    }

    /**
     * 修改文件内容（覆盖）
     */
    @PutMapping("/{treeId}/file/content")
    public R<Void> updateFileContent(
            @PathVariable String treeId,
            @Validated @RequestBody UpdateFileDTO request) {
        virtualFileService.updateFileContent(treeId, request);
        return R.success("文件内容更新成功");
    }

    /**
     * 删除节点（文件或文件夹）
     */
    @DeleteMapping("/{treeId}/node/{nodeId}")
    public R<Void> deleteNode(
            @PathVariable String treeId,
            @PathVariable String nodeId) {
        virtualFileService.deleteNode(treeId, nodeId);
        return R.success("节点删除成功");
    }

    /**
     * 重命名节点（文件或文件夹）
     */
    @PutMapping("/{treeId}/node/{nodeId}/rename")
    public R<Void> renameNode(
            @PathVariable String treeId,
            @PathVariable String nodeId,
            @RequestParam String newName) {
        virtualFileService.renameNode(treeId, nodeId, newName);
        return R.success("节点重命名成功");
    }

    /**
     * 导出虚拟文件树到指定目录
     */
    @PostMapping("/{treeId}/export")
    public R<Void> exportVirtualTree(
            @PathVariable String treeId,
            @RequestParam String exportPath) {
        virtualFileService.exportVirtualTree(treeId, exportPath);
        return R.success("虚拟文件树导出成功");
    }
}