package com.tml.otowbackend.engine.tree.core;

/**
 * 描述: 虚拟文件模板接口
 * @author suifeng
 * 日期: 2024/12/12
 */
public interface VirtualTreeTemplate {

    /**
     * 初始化模板（建立文件夹名称到ID的映射关系）
     */
    void initializeTemplate();
}