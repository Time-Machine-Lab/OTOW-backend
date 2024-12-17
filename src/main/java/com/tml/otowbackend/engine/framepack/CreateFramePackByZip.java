package com.tml.otowbackend.engine.framepack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @Author: zq
 * @Description:
 * @Date: 2024/12/2 15:48
 * @Version: 1.8
 */
public class CreateFramePackByZip {
    /**
     * 解压ZIP文件到指定目录
     *
     * @param zipFilePath ZIP文件的路径
     * @param destDir 解压到的目标目录
     * @throws IOException 可能发生的IO异常
     */
    public static void unzip(String zipFilePath, String destDir) throws IOException {
        // 创建目标目录，如果不存在的话
        File destDirectory = new File(destDir);
        if (!destDirectory.exists()) {
            destDirectory.mkdirs();
        }

        // 创建ZipInputStream来读取ZIP文件
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry;

            // 遍历ZIP文件中的每个条目
            while ((zipEntry = zis.getNextEntry()) != null) {
                // 构建解压后文件的路径
                File newFile = new File(destDir + File.separator + zipEntry.getName());

                // 如果是目录，则创建目录
                if (zipEntry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    // 如果是文件，确保父目录存在并创建文件
                    newFile.getParentFile().mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;

                        // 读取数据并写入到目标文件
                        while ((bytesRead = zis.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }
                }
                // 完成当前条目的读取
                zis.closeEntry();
            }
        }
    }

    /**
     * 删除指定路径的文件
     *
     * @param filePath 文件的路径
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);

        // 检查文件是否存在
        if (file.exists()) {
            // 尝试删除文件，并输出删除结果
            if (file.delete()) {
                System.out.println("文件删除成功: " + filePath);
            } else {
                System.out.println("文件删除失败: " + filePath);
            }
        } else {
            System.out.println("文件找不到: " + filePath);
        }
    }

    /**
     * 克隆 GitHub 仓库，并解压其中的 ZIP 文件，删除不需要的文件
     *
     * @param repoUrl 仓库的 Git URL
     * @param localPath 本地存储路径
     */
    public static void createFramePack(String repoUrl, String localPath) {
        // 使用 ProcessBuilder 执行 git clone 命令，将 Git 仓库克隆到指定本地路径
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("git", "clone", repoUrl, localPath);

        try {
            // 启动子进程来执行 git 命令
            Process process = processBuilder.start();

            // 等待命令执行完成，获取退出码
            int exitCode = process.waitFor();

            // 检查 git 命令执行是否成功，退出码为0表示成功
            if (exitCode == 0) {
                System.out.println("仓库克隆成功!");

                // 假设仓库中包含一个名为 FramePack.zip 的压缩包，执行解压
                String zipFilePath = localPath + "\\FramePack.zip";  // 指定压缩包的路径
                // 调用解压方法解压该 ZIP 文件
                unzip(zipFilePath, localPath);

                // 解压完成后删除该 ZIP 文件
                deleteFile(zipFilePath);

                // 删除克隆仓库中的其他不相关的文件，例如 .git 文件和 README.md 文件
                deleteFile(localPath + "\\README.md");  // 删除 README.md
                deleteFile(localPath + "\\.git");  // 删除 .git 文件夹
            } else {
                // 如果 git 命令执行失败，输出失败信息
                System.out.println("仓库克隆失败.");
            }
        } catch (IOException | InterruptedException e) {
            // 捕获并打印可能的异常
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String repoUrl = "https://github.com/LuckySheeps/Frame-Pack.git";  // GitHub仓库URL
        String localPath = "D:\\filetest";  // 本地路径
        createFramePack(repoUrl,localPath);
        }
}
