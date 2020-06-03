package com.bowen.jtools.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Copyright (c) 2020 XiaoMi Inc. All Rights Reserved.
 * Created by: jiangbowen <jiangbowen@xiaomi.com>.
 * On 2020/5/8
 */
public class BWDateTimeUtils {

    private static final SimpleDateFormat FORMATTER1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final SimpleDateFormat FORMATTER2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static long getTimeStamp(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return 0L;
        }
        try {
            Date date = FORMATTER2.parse(dateStr);
            return date.getTime();
        } catch (Exception e) {
            return 0L;
        }
    }

    public static long getTimeStamp(String dateStr, SimpleDateFormat formatter) {
        if (StringUtils.isEmpty(dateStr)) {
            return 0L;
        }
        try {
            Date date = formatter.parse(dateStr);
            return date.getTime();
        } catch (Exception e) {
            return 0L;
        }
    }

    public static String getTimeString(long timeStamp) {
        return FORMATTER2.format(new Date(timeStamp));
    }

    public static String getTimeString(long timeStamp, SimpleDateFormat formatter) {
        return formatter.format(new Date(timeStamp));
    }

    public static final SimpleDateFormat publishTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static double getGapHours(String publishTime) {
        return (System.currentTimeMillis() - getTimeStamp(publishTime, publishTimeFormat)) * 1.0 / 1000 / 60 / 60;
    }

    /**
     * 显示时间函数
     * @param timestamp
     * @return
     */
    private static String getDisplayTimeStrByTimestamp(long timestamp) {
        long timeStampGap = System.currentTimeMillis() - timestamp;
        if (timeStampGap > 0 && timeStampGap <= 3600000) {
            return String.format("%d分钟前", timeStampGap / 60000);
        } else if (timeStampGap > 3600000 && timeStampGap <= 86400000) {
            return String.format("%d小时前", timeStampGap / 3600000);
        } else if (timeStampGap > 86400000 && timeStampGap <= 604800000) {
            return String.format("%d天前", timeStampGap / 86400000);
        } else {
            return new SimpleDateFormat("yyyy年M月d日").format(new Date(timestamp));
        }
    }

    public static void main(String[] args) throws ParseException {

        System.out.println(LocalDateTime.now().getHour());


        long curPublishTImeTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-05-24 19:43:00").getTime();
        long curTimestamp = System.currentTimeMillis();
        System.out.println(curTimestamp - curPublishTImeTimestamp);


        // java8
        LocalDateTime date = LocalDateTime.now().minus(1, ChronoUnit.DAYS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println(date.format(formatter) + " 00:00:00");
        System.out.println(LocalDateTime.now().minus(2, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        System.out.println(getGapHours("2020-05-22 13:54:28"));

        try {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-05-22 13:54:28"));
        } catch (Exception e) {

        }


    }

}
