package com.tml.otowbackend.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EntityScanner {

    /**
     * 扫描指定包下的所有类
     * @param packageName 包名
     * @return 类的列表
     */
    public static List<Class<?>> scanEntities(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace(".", "/");
        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);

        if (resource == null) {
            throw new RuntimeException("包路径不存在: " + packageName);
        }

        File directory = new File(resource.getFile());
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().endsWith(".class")) {
                try {
                    String className = packageName + "." + file.getName().replace(".class", "");
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("类加载失败: " + file.getName(), e);
                }
            }
        }

        return classes;
    }
}