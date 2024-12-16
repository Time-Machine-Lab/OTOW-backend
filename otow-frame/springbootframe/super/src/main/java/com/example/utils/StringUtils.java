package com.example.framepack.utils;

import java.util.Arrays;
import java.util.List;

public class StringUtils {
 
    /**
     * 将字符串反转
     * @param str 要反转的字符串
     * @return 反转后的字符串
     */
    public static String reverseString(String str) {
        return new StringBuilder(str).reverse().toString();
    }
 
    /**
     * 判断字符串是否为空
     * @param str 要判断的字符串
     * @return 如果字符串为空则返回true，否则返回false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
 
    /**
     * 去除字符串两端的空格
     * @param str 要处理的字符串
     * @return 去除两端空格后的字符串
     */
    public static String trim(String str) {
        return str.trim();
    }
 
    /**
     * 将字符串转换为大写
     * @param str 要转换的字符串
     * @return 转换为大写后的字符串
     */
    public static String toUpperCase(String str) {
        return str.toUpperCase();
    }
 
    /**
     * 将字符串转换为小写
     * @param str 要转换的字符串
     * @return 转换为小写后的字符串
     */
    public static String toLowerCase(String str) {
        return str.toLowerCase();
    }
 
    /**
     * 判断字符串是否以指定的前缀开始
     * @param str 要判断的字符串
     * @param prefix 前缀
     * @return 如果字符串以指定的前缀开始则返回true，否则返回false
     */
    public static boolean startsWith(String str, String prefix) {
        return str.startsWith(prefix);
    }
 
    /**
     * 判断字符串是否以指定的后缀结束
     * @param str 要判断的字符串
     * @param suffix 后缀
     * @return 如果字符串以指定的后缀结束则返回true，否则返回false
     */
    public static boolean endsWith(String str, String suffix) {
        return str.endsWith(suffix);
    }
 
    /**
     * 判断字符串是否包含指定子字符串
     * @param str 要判断的字符串
     * @param subStr 子字符串
     * @return 如果字符串包含指定子字符串则返回true，否则返回false
     */
    public static boolean contains(String str, String subStr) {
        return str.contains(subStr);
    }
 
    /**
     * 将字符串按照指定分隔符拆分为数组
     * @param str 要拆分的字符串
     * @param delimiter 分隔符
     * @return 拆分后的字符串数组
     */
    public static String[] split(String str, String delimiter) {
        return str.split(delimiter);
    }
 
    /**
     * 将字符串按照指定分隔符拆分为列表
     * @param str 要拆分的字符串
     * @param delimiter 分隔符
     * @return 拆分后的字符串列表
     */
    public static List<String> splitToList(String str, String delimiter) {
        return Arrays.asList(str.split(delimiter));
    }
 
    /**
     * 替换字符串中的指定子字符串
     * @param str 要替换的字符串
     * @param target 要被替换的子字符串
     * @param replacement 替换字符串
     * @return 替换后的字符串
     */
    public static String replace(String str, String target, String replacement) {
        return str.replace(target, replacement);
    }
 
    /**
     * 将字符串转换为首字母大写
     * @param str 要转换的字符串
     * @return 首字母大写的字符串
     */
    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
 
    /**
     * 将字符串转换为首字母小写
     * @param str 要转换的字符串
     * @return 首字母小写的字符串
     */
    public static String uncapitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
 
    /**
     * 将字符串转换为驼峰命名法（首字母小写）
     * 例如: "hello_world" -> "helloWorld"
     * @param str 要转换的字符串
     * @return 转换后的字符串
     */
    public static String toCamelCase(String str) {
        if (isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        String[] words = str.split("_");
        sb.append(words[0]);
        for (int i = 1; i < words.length; i++) {
            sb.append(capitalize(words[i]));
        }
        return sb.toString();
    }
 
    /**
     * 将字符串转换为帕斯卡命名法（首字母大写）
     * 例如: "hello_world" -> "HelloWorld"
     * @param str 要转换的字符串
     * @return 转换后的字符串
     */
    public static String toPascalCase(String str) {
        return capitalize(toCamelCase(str));
    }
 
    /**
     * 将字符串转换为下划线命名法（小写字母，单词间用下划线分隔）
     * 例如: "HelloWorld" -> "hello_world"
     * @param str 要转换的字符串
     * @return 转换后的字符串
     */
    public static String toSnakeCase(String str) {
        if (isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    sb.append('_');
                }
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
 
    /**
     * 获取重复指定次数的字符串
     * @param str 要重复的字符串
     * @param repeatCount 重复次数
     * @return 重复指定次数后的字符串
     */
    public static String repeat(String str, int repeatCount) {
        if (repeatCount <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < repeatCount; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
 
    /**
     * 获取字符串的长度（考虑Unicode扩展字符）
     * @param str 要获取长度的字符串
     * @return 字符串的长度
     */
    public static int length(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        return str.codePointCount(0, str.length());
    }
 
    /**
     * 判断两个字符串是否相等（忽略大小写）
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 如果两个字符串相等则返回true，否则返回false
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1.equalsIgnoreCase(str2);
    }
 
    /**
     * 判断字符串是否为数字
     * @param str 要判断的字符串
     * @return 如果字符串为数字则返回true，否则返回false
     */
    public static boolean isNumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
 