package com.tml.otowbackend.util;


public class ImportUtil {

    private static final String JAVA_LANG_PACKAGE = "java.lang";

    public static boolean needsImport(Class<?> clazz, String currentPackage) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        }

        // 获取类的包名
        Package classPackage = clazz.getPackage();
        String classPackageName = (classPackage != null) ? classPackage.getName() : "";

        // 不需要 import 的情况
        if (classPackageName.equals(JAVA_LANG_PACKAGE)) {
            return false; // java.lang 包
        }
        if (classPackageName.equals(currentPackage)) {
            return false; // 与当前包相同
        }

        return true; // 其他情况需要 import
    }

    public static boolean needsImport(String classPath, String currentPackage) {

        String classPackageName = classPath.substring(0, classPath.lastIndexOf("."));

        // 不需要 import 的情况
        if (classPackageName.equals(JAVA_LANG_PACKAGE)) {
            return false; // java.lang 包
        }
        if (classPackageName.equals(currentPackage)) {
            return false; // 与当前包相同
        }

        return true; // 其他情况需要 import
    }
}
