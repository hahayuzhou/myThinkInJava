package com.thinking.my.time;

import com.sun.org.apache.xalan.internal.xsltc.dom.SortingIterator;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @Description
 * @Author liyong
 * @Date 2020/7/22 11:24 上午
 **/
public class TImeTest {

    public static void main(String[] args) {
//        LocalDate today = LocalDate.now();
//        System.out.println(today);
//
//        LocalDate date = LocalDate.of(2014, 3, 18);
//        int year = date.getYear();
//        Month month = date.getMonth();
//        int day = date.getDayOfMonth();
//        DayOfWeek dow = date.getDayOfWeek();
//        int len = date.lengthOfMonth();
//        boolean leap = date.isLeapYear();
//
//        System.out.println(date);
//        System.out.println(year);
//        System.out.println(month);
//        System.out.println(day);
//        System.out.println(dow);
//        System.out.println(len);
//        System.out.println(leap);
//
//        Period tenDays = Period.ofDays(10);
//        Period threeWeeks = Period.ofWeeks(3);
//        Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
//        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
//        df.setTimeZone(TimeZone.getTimeZone("GMT"));
//        Date now = new Date();
//        String dateString = df.format(new Date());
//
//        System.out.println(dateString);
//
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss z",Locale.ENGLISH);
//        Instant instant = now.toInstant();
////        LocalDateTime time = LocalDateTime.ofInstant(instant, ZoneId.of("GMT"));
////        dateString = dateTimeFormatter.format(now.toInstant());
////        System.out.println(dateString);
//
//        System.out.println(22);
//        LocalDateTime localDateTime =
//                now.toInstant()
//                        .atZone(ZoneId.systemDefault())
//                        .toLocalDateTime();
//        System.out.println("cc");

//        dateString = dateTimeFormatter.format(localDateTime);
//        System.out.println(dateString);

// 获取当前日期
//        LocalTime currentDate = LocalTime.now();
//        // 格式化日期
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        System.out.println(currentDate.format(formatter));

        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = formatter.format(currentTime);

        System.out.println(formattedTime);
        System.out.println(currentTime.format(formatter));


    }
}
