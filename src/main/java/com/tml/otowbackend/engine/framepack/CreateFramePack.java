package com.tml.otowbackend.engine.framepack;

import java.io.File;
import java.io.IOException;

/**
 * @Author: zq
 * @Description:
 * @Date: 2024/12/2 15:48
 * @Version: 1.8
 */
public class CreateFramePack {
    /**
     * 创建指定的目录结构
     *
     * @param startFile 起始路径，用于指定从哪个路径开始创建目录结构
     * @return boolean 返回目录创建是否成功的标志
     */
    public static boolean createFiles(String startFile) {
        // 默认目录创建成功标志
        Boolean flag = true;

        // 需要创建的目录结构数组
        String[] directories = {
                "controller",
                "exception",
                "handler",
                "interceptor",
                "mapper",
                "model/dto",
                "model/entity",
                "model/enum",
                "model/req",
                "model/vo",
                "service/impl"
        };

        // 遍历目录结构数组，逐个创建目录
        for (String dir : directories) {
            // 创建文件夹对象，路径为起始路径 + 目录名
            File directory = new File(startFile + "/" + dir);

            // 如果目录不存在，则尝试创建目录
            if (!directory.exists()) {
                // 尝试创建目录，返回值为 boolean 类型，表示是否创建成功
                if (directory.mkdirs()) {
                    System.out.println("Created directory: " + directory.getPath());  // 输出成功信息
                } else {
                    flag = false;  // 如果某个目录创建失败，设置标志为 false
                    System.out.println("Failed to create directory: " + directory.getPath());  // 输出失败信息
                }
            }
        }

        // 返回目录创建的总体结果，成功则返回 true，失败则返回 false
        return flag;
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
     * 克隆指定的 GitHub 仓库，并根据文件清单创建必要的文件夹，删除不必要的文件。
     *
     * @param repoUrl 仓库的 Git URL
     * @param localPath 本地存储路径
     */
    public static void createFramePack(String repoUrl, String localPath) {
        // 使用 ProcessBuilder 来执行 git clone 命令，将 Git 仓库克隆到本地指定路径
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("git", "clone", repoUrl, localPath);

        try {
            // 启动子进程执行 git 命令
            Process process = processBuilder.start();
            // 等待命令执行完成并获取退出码
            int exitCode = process.waitFor();

            // 检查 git 命令的执行结果，退出码为 0 表示成功
            if (exitCode == 0) {
                System.out.println("仓库克隆成功!");

                // 删除 README.md 文件
                deleteFile(localPath + "\\README.md");

                // 拉取下来后根据文件清单补充必要的文件夹
                // 需要创建的目录结构
                String newFile = localPath + "\\src\\main\\java\\com\\example\\";
                // 调用 createFiles 方法来创建所需的文件夹
                if (createFiles(newFile)) {
                    System.out.println("加载成功！");
                } else {
                    System.out.println("加载失败！");
                }

            } else {
                System.out.println("仓库克隆失败.");
            }
        } catch (IOException | InterruptedException e) {
            // 捕获并打印可能的异常
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String repoUrl = "https://github.com/LuckySheeps/FramePack.git";  // GitHub仓库URL
        String localPath = "D:\\filetest";  // 本地路径
        createFramePack(repoUrl,localPath);
    }
}

