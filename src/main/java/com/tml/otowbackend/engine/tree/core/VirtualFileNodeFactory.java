package com.tml.otowbackend.engine.tree.core;

import com.tml.otowbackend.engine.tree.entity.po.VirtualFileNode;
import com.tml.otowbackend.engine.tree.utils.UUIDUtils;

import java.io.File;
import java.util.List;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
public class VirtualFileNodeFactory {

    /**
     * 根据文件创建一个 VirtualFileNode 实例
     *
     * @param file 要创建节点的文件
     * @return VirtualFileNode 实例
     */
    public static VirtualFileNode createVirtualFileNode(File file) {
        String fileExtension = getFileExtension(file.getName());
        String id = file.getPath();
        String fileType = fileExtension;
        long size = file.length();

        List<String> rawLines;
        if (!SupportedLanguages.isDisplayableFile(fileExtension)) {
            rawLines = List.of(SupportedLanguages.UNSUPPORTED_FILE_MESSAGE);
            size = 0; // 如果文件不可展示，大小设置为0
        } else if (FileContentReader.isFileTooLarge(size)) {
            rawLines = List.of(SupportedLanguages.UNSUPPORTED_FILE_MESSAGE);
        } else {
            rawLines = FileContentReader.readFileContent(file, size);
        }

        return new VirtualFileNode(id, file.getName(), fileType, size, rawLines);
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名称
     * @return 文件扩展名
     */
    private static String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return (lastIndex > 0) ? fileName.substring(lastIndex + 1) : "unknown";
    }
}