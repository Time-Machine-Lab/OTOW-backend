package com.tml.otowbackend.engine.tree.template;

import com.tml.otowbackend.engine.tree.core.AbstractVirtualTreeTemplate;
import com.tml.otowbackend.engine.tree.entity.dto.AddFileDTO;
import com.tml.otowbackend.engine.tree.entity.vo.NodeVO;
import com.tml.otowbackend.engine.tree.service.IVirtualFileService;

import java.util.List;


/**
 * 描述: SpringBoot项目初始模版树
 * @author suifeng
 * 日期: 2024/12/12
 */
public class SpringBootTreeTemplate extends AbstractVirtualTreeTemplate {

    public SpringBootTreeTemplate(IVirtualFileService virtualFileService, String treeId) {
        super(virtualFileService, treeId);
    }

    @Override
    public int getTargetDepth() {
        return 7;
    }

    /**
     * 添加文件到指定文件夹
     */
    public void addFile(String folderName, String fileName, List<String> content) {
        String folderId = getFolderId(folderName);
        if (folderId == null) throw new IllegalStateException(folderName + " 文件夹未初始化");
        virtualFileService.addFile(treeId, new AddFileDTO(folderId, fileName, content));
    }

    public void addFileToController(String fileName, List<String> content) {
        addFile("controller", fileName, content);
    }

    public void addFileToService(String fileName, List<String> content) {
        addFile("service", fileName, content);
    }

    public void addFileToServiceImpl(String fileName, List<String> content) {
        addFile("service/impl", fileName, content);
    }

    public void addFileToMapper(String fileName, List<String> content) {
        addFile("mapper", fileName, content);
    }

    public void addFileToUtils(String fileName, List<String> content) {
        addFile("utils", fileName, content);
    }

    public void addFileToConfig(String fileName, List<String> content) {
        addFile("config", fileName, content);
    }

    @Override
    public void buildFolderMappings(List<NodeVO> rootNodes) {
        // 遍历 rootNodes，逐一建立文件夹名称到 ID 的映射关系
        for (NodeVO node : rootNodes) {
            if ("folder".equals(node.getType())) {
                switch (node.getName()) {
                    case "common":
                        folderMappings.put("common", node.getId());
                        break;
                    case "config":
                        folderMappings.put("config", node.getId());
                        break;
                    case "controller":
                        folderMappings.put("controller", node.getId());
                        break;
                    case "exception":
                        folderMappings.put("exception", node.getId());
                        break;
                    case "handler":
                        folderMappings.put("handler", node.getId());
                        break;
                    case "interceptor":
                        folderMappings.put("interceptor", node.getId());
                        break;
                    case "mapper":
                        folderMappings.put("mapper", node.getId());
                        break;
                    case "model":
                        folderMappings.put("model", node.getId());
                        mapModelSubFolders(node.getId());
                        break;
                    case "service":
                        folderMappings.put("service", node.getId());
                        mapServiceSubFolders(node.getId());
                        break;
                    case "utils":
                        folderMappings.put("utils", node.getId());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void mapModelSubFolders(String modelFolderId) {
        // 获取 model 文件夹的子节点
        List<NodeVO> modelSubFolders = virtualFileService.getChildrenNodes(treeId, modelFolderId);
        for (NodeVO subFolder : modelSubFolders) {
            if ("folder".equals(subFolder.getType())) {
                switch (subFolder.getName()) {
                    case "dto":
                        folderMappings.put("model/dto", subFolder.getId());
                        break;
                    case "entity":
                        folderMappings.put("model/entity", subFolder.getId());
                        break;
                    case "enum":
                        folderMappings.put("model/enum", subFolder.getId());
                        break;
                    case "req":
                        folderMappings.put("model/req", subFolder.getId());
                        break;
                    case "vo":
                        folderMappings.put("model/vo", subFolder.getId());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 建立 service 子文件夹的映射关系
     */
    private void mapServiceSubFolders(String serviceFolderId) {
        List<NodeVO> serviceSubFolders = virtualFileService.getChildrenNodes(treeId, serviceFolderId);
        for (NodeVO subFolder : serviceSubFolders) {
            if ("folder".equals(subFolder.getType())) {
                if (subFolder.getName().equals("impl")) {
                    folderMappings.put("service/impl", subFolder.getId());
                }
            }
        }
    }
}