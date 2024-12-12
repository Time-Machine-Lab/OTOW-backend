package com.tml.otowbackend.engine.tree.template;

import com.tml.otowbackend.engine.tree.core.AbstractVirtualTreeTemplate;
import com.tml.otowbackend.engine.tree.entity.vo.NodeVO;
import com.tml.otowbackend.engine.tree.service.IVirtualFileService;

import java.util.List;


/**
 * 描述: SpringBoot项目初始模版树
 * @author suifeng
 * 日期: 2024/12/12
 */
public class SpringBootTemplate extends AbstractVirtualTreeTemplate {

    public SpringBootTemplate(IVirtualFileService virtualFileService, String treeId) {
        super(virtualFileService, treeId);
    }

    @Override
    public void buildFolderMappings(List<NodeVO> rootNodes) {

    }
}