package com.tml.otowbackend.engine.tree.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * @author suifeng
 * 日期: 2024/12/12
 */
public class FileContentReader {

    private static final long MAX_PREVIEW_SIZE = 1024 * 1024; // 最大预览大小：1MB
    private static final int PARTIAL_CONTENT_SIZE = 16 * 1024; // 部分展示大小：16KB

    // 读取文件内容（按行分割）
    public static List<String> readFileContent(File file, long fileSize) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            long bytesRead = 0;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
                bytesRead += line.getBytes().length;

                // 如果文件内容过大，只读取部分内容
                if (bytesRead > PARTIAL_CONTENT_SIZE) {
                    lines.add(SupportedLanguages.UNSUPPORTED_FILE_MESSAGE);
                    break;
                }
            }
        } catch (IOException e) {
            lines.clear();
            lines.add(SupportedLanguages.UNSUPPORTED_FILE_MESSAGE);
        }
        return lines;
    }

    // 判断文件是否超过最大预览大小
    public static boolean isFileTooLarge(long fileSize) {
        return fileSize > MAX_PREVIEW_SIZE;
    }
}