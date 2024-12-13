package com.example.framepack.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DateTimeUtils {
 
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
 
    // 获取当前日期
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
 
    // 获取当前时间
    public static LocalTime getCurrentTime() {
        return LocalTime.now();
    }
 
    // 获取当前日期时间
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
 
    // 获取当前时间戳
    public static long getCurrentTimestamp() {
        return Instant.now().toEpochMilli();
    }
 
    /**
     *  生成当前时间字符串，默认格式yyyy-MM-dd HH:mm:ss
     * @return java.lang.String
     **/
    public static String currentDateStr() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }
 
    /**
     * 指定日期格式生成当前时间字符串
     * @param formatter
     * @return java.lang.String
     **/
    public static String currentDateStr(String formatter) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern(formatter);
        return now.format(pattern);
    }
 
    /**
     **时间转字符串，默认格式化：yyyy-MM-dd HH:mm:ss
     * @param dateTime
     * @return java.lang.String
     **/
    public static String dateToStr(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }
 
    /**
     **时间转字符串
     * @param dateTime
     * @param formatter 格式化
     * @return java.lang.String
     **/
    public static String dateToStr(LocalDateTime dateTime, DateTimeFormatter formatter) {
        return dateTime.format(formatter);
    }
 
    /**
     **判断bigTime是否大于smallTime
     * @param smallTime
     * @param bigTime
     * @return boolean
     **/
    public static boolean lessThanTime(String smallTime, String bigTime) {
        LocalDateTime smallDateTime = LocalDateTime.parse(smallTime, formatter);
        LocalDateTime bigDateTime = LocalDateTime.parse(bigTime, formatter);
        return smallDateTime.isBefore(bigDateTime);
    }
 
    /**
     **获取num天前的日期
     * @param num
     * @return java.lang.String
     **/
    public static String getPastDate(int num) {
        LocalDate currentDate = LocalDate.now();
        LocalDate pastDate = currentDate.minusDays(num);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return pastDate.format(formatter);
    }
 
    /**
     **获取近n天的日期字符串集合
     * @param n
     * @return java.util.List<java.lang.String>
     **/
    public static List<String> getRecentNumDays(int n) {
        List<String> dates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < n; i++) {
            LocalDate date = today.minusDays(i);
            String dateStr = date.format(DateTimeFormatter.ofPattern("MM-dd"));
            dates.add(dateStr);
        }
        return dates;
    }
 
    // 将日期字符串解析成LocalDate对象
    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date);
    }
 
    // 将时间字符串解析成LocalTime对象
    public static LocalTime parseTime(String time) {
        return LocalTime.parse(time);
    }
 
    // 将日期时间字符串解析成LocalDateTime对象
    public static LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime);
    }
 
    // 将日期时间字符串按照指定格式解析成LocalDateTime对象
    public static LocalDateTime parseDateTime(String dateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dateTime, formatter);
    }
 
    // 将LocalDate对象格式化成日期字符串
    public static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
    }
 
    // 将LocalTime对象格式化成时间字符串
    public static String formatTime(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT));
    }
 
    // 将LocalDateTime对象格式化成日期时间字符串
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
    }
 
    // 将LocalDateTime对象按照指定格式格式化成日期时间字符串
    public static String formatDateTime(LocalDateTime dateTime, String format) {
        return dateTime.format(DateTimeFormatter.ofPattern(format));
    }
 
    // 获取指定日期的星期几（1-7，分别代表周一至周日）
    public static int getDayOfWeek(LocalDate date) {
        return date.getDayOfWeek().getValue();
    }
 
    // 获取指定日期的年份
    public static int getYear(LocalDate date) {
        return date.getYear();
    }
 
    // 获取指定日期的月份
    public static int getMonth(LocalDate date) {
        return date.getMonthValue();
    }
 
    // 获取指定日期的天数
    public static int getDayOfMonth(LocalDate date) {
        return date.getDayOfMonth();
    }
 
    // 获取指定日期是当年的第几天
    public static int getDayOfYear(LocalDate date) {
        return date.getDayOfYear();
    }
 
    // 获取指定日期是否为闰年
    public static boolean isLeapYear(LocalDate date) {
        return date.isLeapYear();
    }
 
    // 获取指定日期之前或之后的几天
    public static LocalDate plusDays(LocalDate date, long days) {
        return date.plusDays(days);
    }
 
    // 获取指定日期之前或之后的几周
    public static LocalDate plusWeeks(LocalDate date, long weeks) {
        return date.plusWeeks(weeks);
    }
 
    // 获取指定日期之前或之后的几个月
    public static LocalDate plusMonths(LocalDate date, long months) {
        return date.plusMonths(months);
    }
 
    // 获取指定日期之前或之后的几年
    public static LocalDate plusYears(LocalDate date, long years) {
        return date.plusYears(years);
    }
 
    // 获取指定日期之前或之后的几小时
    public static LocalDateTime plusHours(LocalDateTime dateTime, long hours) {
        return dateTime.plusHours(hours);
    }
 
    // 获取指定日期之前或之后的几分钟
    public static LocalDateTime plusMinutes(LocalDateTime dateTime, long minutes) {
        return dateTime.plusMinutes(minutes);
    }
 
    // 获取指定日期之前或之后的几秒钟
    public static LocalDateTime plusSeconds(LocalDateTime dateTime, long seconds) {
        return dateTime.plusSeconds(seconds);
    }
 
    // 获取指定日期之前或之后的几毫秒
    public static LocalDateTime plusMilliseconds(LocalDateTime dateTime, long milliseconds) {
        return dateTime.plus(milliseconds, ChronoUnit.MILLIS);
    }
 
    // 获取指定日期之前或之后的几纳秒
    public static LocalDateTime plusNanoseconds(LocalDateTime dateTime, long nanoseconds) {
        return dateTime.plus(nanoseconds, ChronoUnit.NANOS);
    }
 
    // 比较两个日期的先后顺序（返回值小于0表示date1在date2之前，等于0表示两个日期相等，大于0表示date1在date2之后）
    public static int compareDates(LocalDate date1, LocalDate date2) {
        return date1.compareTo(date2);
    }
 
    // 判断两个日期是否相等
    public static boolean areDatesEqual(LocalDate date1, LocalDate date2) {
        return date1.isEqual(date2);
    }
 
    // 计算两个日期之间的天数差
    public static long getDaysBetween(LocalDate date1, LocalDate date2) {
        return ChronoUnit.DAYS.between(date1, date2);
    }
 
    // 计算两个日期之间的周数差
    public static long getWeeksBetween(LocalDate date1, LocalDate date2) {
        return ChronoUnit.WEEKS.between(date1, date2);
    }
 
    // 计算两个日期之间的月数差
    public static long getMonthsBetween(LocalDate date1, LocalDate date2) {
        return ChronoUnit.MONTHS.between(date1, date2);
    }
 
    // 计算两个日期之间的年数差
    public static long getYearsBetween(LocalDate date1, LocalDate date2) {
        return ChronoUnit.YEARS.between(date1, date2);
    }
 
    // 判断指定日期是否在当前日期之前
    public static boolean isBeforeCurrentDate(LocalDate date) {
        return date.isBefore(getCurrentDate());
    }
 
    // 判断指定日期是否在当前日期之后
    public static boolean isAfterCurrentDate(LocalDate date) {
        return date.isAfter(getCurrentDate());
    }
 
    // 判断指定时间是否在当前时间之前
    public static boolean isBeforeCurrentTime(LocalTime time) {
        return time.isBefore(getCurrentTime());
    }
 
    // 判断指定时间是否在当前时间之后
    public static boolean isAfterCurrentTime(LocalTime time) {
        return time.isAfter(getCurrentTime());
    }
 
    // 判断指定日期时间是否在当前日期时间之前
    public static boolean isBeforeCurrentDateTime(LocalDateTime dateTime) {
        return dateTime.isBefore(getCurrentDateTime());
    }
 
    // 判断指定日期时间是否在当前日期时间之后
    public static boolean isAfterCurrentDateTime(LocalDateTime dateTime) {
        return dateTime.isAfter(getCurrentDateTime());
    }
 
    // 判断两个日期时间是否相等
    public static boolean areDateTimesEqual(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.isEqual(dateTime2);
    }
 
    // 计算两个日期时间之间的小时数差
    public static long getHoursBetween(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return ChronoUnit.HOURS.between(dateTime1, dateTime2);
    }
 
    // 计算两个日期时间之间的分钟数差
    public static long getMinutesBetween(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return ChronoUnit.MINUTES.between(dateTime1, dateTime2);
    }
 
    // 计算两个日期时间之间的秒数差
    public static long getSecondsBetween(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return ChronoUnit.SECONDS.between(dateTime1, dateTime2);
    }
 
    // 计算两个日期时间之间的毫秒数差
    public static long getMillisecondsBetween(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return ChronoUnit.MILLIS.between(dateTime1, dateTime2);
    }
 
    // 计算两个日期时间之间的纳秒数差
    public static long getNanosecondsBetween(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return ChronoUnit.NANOS.between(dateTime1, dateTime2);
    }
 
}
 