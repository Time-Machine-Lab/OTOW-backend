package com.example.framepack.utils;

import java.util.Random;
 
/**
 * 随机数工具类
 */
public class RandomUtils {
 
    private static Random random = new Random();
 
    /**
     * 生成指定范围内的随机整数
     * @param min 最小值（包含）
     * @param max 最大值（包含）
     * @return 随机整数
     */
    public static int getRandomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
 
    /**
     * 生成指定范围内的随机浮点数
     * @param min 最小值（包含）
     * @param max 最大值（不包含）
     * @return 随机浮点数
     */
    public static float getRandomFloat(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }
 
    /**
     * 生成指定长度的随机字符串
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) getRandomInt(97, 122); // 生成a-z之间的随机字符
            sb.append(c);
        }
        return sb.toString();
    }
 
    /**
     * 生成指定位数的随机数字串
     * @param length 数字串长度
     * @return 随机数字串
     */
    public static String getRandomNumber(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int num = getRandomInt(0, 9);
            sb.append(num);
        }
        return sb.toString();
    }
 
    /**
     * 生成指定长度和字符范围的随机字符串
     * @param length 字符串长度
     * @param chars 字符范围
     * @return 随机字符串
     */
    public static String getRandomString(int length, char[] chars) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = getRandomInt(0, chars.length - 1);
            char c = chars[index];
            sb.append(c);
        }
        return sb.toString();
    }
 
    /**
     * 生成指定长度和字符范围的随机数字串
     * @param length 数字串长度
     * @param digits 数字范围
     * @return 随机数字串
     */
    public static String getRandomNumber(int length, int[] digits) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = getRandomInt(0, digits.length - 1);
            int num = digits[index];
            sb.append(num);
        }
        return sb.toString();
    }
    
    // 其他方法...
}
 