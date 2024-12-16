package com.tml.otowbackend.service;


public interface OTOWProjectService {

    /**
     * 初始化一个新项目
     *
     * @param title       项目标题
     * @param description 项目描述
     * @return 初始化后的项目ID
     */
    Long initializeProject(String title, String description);
}