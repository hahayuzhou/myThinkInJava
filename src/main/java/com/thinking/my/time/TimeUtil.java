package com.thinking.my.time;

import org.joda.time.DateTime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description 日期工具类
 * @Author liyong
 * @Date 2020/7/22 11:22 上午
 **/
public class TimeUtil {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static DateTimeFormatter y_m_d = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter ymd = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static DateTimeFormatter dateTimeFormatter_Hour = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
    private static DateTimeFormatter segmentFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");


    public static void LocalDateTimeToString() {
        LocalDateTime dateTime = LocalDateTime.now();

        //使用预定义实例来转换
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;
        String dateStr = dateTime.format(fmt);
        System.out.println("LocalDateTime转String[预定义]:" + dateStr);

        //使用pattern来转换
        //12小时制与24小时制输出由hh的大小写决定
        DateTimeFormatter fmt12 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS");
        String dateStr12 = dateTime.format(fmt12);
        System.out.println("LocalDateTime转String[pattern](12小时制):" + dateStr12);

        DateTimeFormatter fmt24 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
        String dateStr24 = dateTime.format(fmt24);
        System.out.println("LocalDateTime转String[pattern](24小时制):" + dateStr24);

        //如果想要给12小时制时间加上am/pm,这样子做：
        fmt12 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS a");
        dateStr12 = dateTime.format(fmt12);
        System.out.println("LocalDateTime转String[pattern](12小时制带am/pm):" + dateStr12);
    }

    public static void String转LocalDate和LocalDateTime() {
        String str = "2017-11-21 14:41:06";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate date = LocalDate.parse(str, fmt);
        LocalDateTime time = LocalDateTime.parse(str, fmt);
        System.out.println("date:" + date);
        System.out.println("time:" + time);
    }

    /**
     * 本月的第几天 1-31
     *
     * @param dayOfMonth
     * @return
     */
    public static long getTimeOfMonth(int dayOfMonth) {

        LocalDate localDate = LocalDate.now().withDayOfMonth(dayOfMonth);
        return getLong(localDate);

    }

    /**
     * 获取前几个月最后一天
     *
     * @param minusMonths
     * @return
     */
    public static LocalDate getLastDayOfMonTh(int minusMonths) {

        return LocalDate.now().minusMonths(minusMonths).with(TemporalAdjusters.lastDayOfMonth());

    }

    /**
     * 获取前几年前几个月最后一天
     *
     * @param minusMonths
     * @return
     */
    public static LocalDate getBeforeYearBeforeMouthsLastDay(int minusYears, int minusMonths) {

        return LocalDate.now().minusYears(minusYears).minusMonths(minusMonths).with(TemporalAdjusters.lastDayOfMonth());

    }

    /**
     * 获取前几年前几个月第几天
     *
     * @param minusYears
     * @param minusMonths
     * @param dayOfMonth
     * @return
     */
    public static LocalDate getBeforeYearBeforeMouthsDayOfMouth(int minusYears, int minusMonths, int dayOfMonth) {

        return LocalDate.now().minusYears(minusYears).minusMonths(minusMonths).withDayOfMonth(dayOfMonth);

    }
    /**
     * 获取前几年前几个月第几天
     *
     * @param minusYears
     * @param minusMonths
     * @param dayOfMonth
     * @return
     */
    public static LocalDate getBeforeYearBeforeMouthsDayOfMouth(Date date,int minusYears, int minusMonths, int dayOfMonth) {

        return getLocalDateBydata(date).minusYears(minusYears).minusMonths(minusMonths).withDayOfMonth(dayOfMonth);

    }

    /**
     * 本月的前几个月 第几天 1-31
     *
     * @param minusMonth
     * @param dayOfMonth
     * @return
     */
    public static long getTimeBeforeYearBeforeMouthsDayOfMouth(int minusYears, int minusMonth, int dayOfMonth) {

        return getLong(getBeforeYearBeforeMouthsDayOfMouth(minusYears, minusMonth, dayOfMonth));

    }

    /**
     * 本月的前几个月 第几天 1-31
     *
     * @param minusMonth
     * @param dayOfMonth
     * @return
     */
    public static long getTimeBeforeYearBeforeMouthsDayOfMouth(Date date,int minusYears, int minusMonth, int dayOfMonth) {

        return getLong(getBeforeYearBeforeMouthsDayOfMouth(date,minusYears, minusMonth, dayOfMonth));

    }

    /**
     * 1-7
     * @param dayOfWeek
     * @return
     */
    public static long getTimeOfWeek(int dayOfWeek) {

        LocalDate localDate = LocalDate.now().with(DayOfWeek.of(dayOfWeek));
        return getLong(localDate);

    }

    /**
     * 前多少周的第几天
     *
     * @param weeks
     * @param dayOfweek
     * @return
     */
    public static LocalDate getDateOfbeforeWeek_(int weeks, int dayOfweek) {
        return LocalDate.now().minusWeeks(weeks).with(DayOfWeek.of(dayOfweek));
    }
    /**
     * 前多少周的第几天
     *
     * @param weeks
     * @param dayOfweek
     * @return
     */
    public static LocalDate getDateOfbeforeWeek_(Date date,int weeks, int dayOfweek) {
        return getLocalDateBydata(date).minusWeeks(weeks).with(DayOfWeek.of(dayOfweek));
    }


    /**
     * 获取当前日期的时间 加8小时
     *
     * @param localDate
     * @return
     */
    public static long getLong(LocalDate localDate) {
        return getLong(LocalDateTime.of(localDate, LocalTime.of(8, 0)));//增加8小时 专门解决kylin build问题
    }

    /**
     * 获取当前日期的时间 加8小时
     *
     * @param localDate
     * @return
     */
    public static String getSegmentTime(LocalDate localDate) {
        return getDateTimeformat(localDate, segmentFormatter);
    }

    public static String getDateTimeformat(LocalDate localDate, DateTimeFormatter formatter) {
        return LocalDateTime.of(localDate, LocalTime.of(0, 0)).format(formatter);
    }


    public static LocalDateTime getbeforeHour(int n, DateTimeFormatter formatter) {
//        return LocalDateTime.now().minusHours(n).format(dateTimeFormatter_Hour);
        LocalDateTime localDateTime = LocalDateTime.now();
        int m = localDateTime.getMinute();
        int s = localDateTime.getSecond();
        return localDateTime.minusHours(n).minusMinutes(m).minusSeconds(s);


    }

    /**
     * 获取时间
     *
     * @param localDateTime
     * @return
     */
    public static long getLong(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }


    /**
     * localDate转Date
     */
    public static Date localDate2Date(LocalDate localDate) {
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        Instant instant1 = zonedDateTime.toInstant();
        Date from = Date.from(instant1);
        return from;
    }

    /**
     * Date 转 localDate
     */
    public static LocalDate date2LocalDate(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        LocalDate localDate = zdt.toLocalDate();
        return localDate;
    }

    public static LocalDate getbeforeDay(Integer days) {
        return LocalDate.now().minusDays(days);
    }
    public static LocalDate getbeforeDay(Date date,Integer days) {
        return getLocalDateBydata(date).minusDays(days);
    }

    /**
     * 前多少周的同一天
     *
     * @param week
     * @return
     */
    public static LocalDate getbeforeWeek(Integer week) {
        return LocalDate.now().minusWeeks(week);
    }
    /**
     * 前多少周的同一天
     * @param date
     * @param week
     * @return
     */
    public static LocalDate getbeforeWeek(Date date, Integer week) {
        return getLocalDateBydata(date).minusWeeks(week);
    }


    /**
     * 前多少 月的同一天
     *
     * @param months
     * @return
     */
    public static LocalDate getbeforeMonth(Integer months) {
        return LocalDate.now().minusMonths(months);
    }
    /**
     * 前多少 月的同一天
     *
     * @param months
     * @return
     */
    public static LocalDate getbeforeMonth(Date date,Integer months) {
        return getLocalDateBydata(date).minusMonths(months);
    }

    /**
     * 前多少年 同一天
     *
     * @param year
     * @return
     */
    public static LocalDate getbeforeYear(Integer year) {
        return LocalDate.now().minusYears(year);
    }
    /**
     * 前多少年 同一天
     *
     * @param year
     * @return
     */
    public static LocalDate getbeforeYear(Date date,Integer year) {
        return getLocalDateBydata(date).minusYears(year);
    }

    public static Long getbeforeDayTime(Integer days) {
        return getLong(getbeforeDay(days));
    }
    public static Long getbeforeDayTime(Date date ,Integer days) {
        return getLong(getbeforeDay(date,days));
    }

    public static Long getbeforeWeekTime(Integer week) {
        return getLong(getbeforeWeek(week));
    }
    public static Long getbeforeWeekTime(Date date,Integer week) {
        return getLong(getbeforeWeek(date,week));
    }

    public static Long getbeforeMonthTime(Integer month) {
        return getLong(getbeforeMonth(month));
    }

    public static Long getbeforeMonthTime(Date date,Integer month) {
        return getLong(getbeforeMonth(date,month));
    }

    public static Long getbeforeYearTime(Integer n) {
        return getLong(getbeforeYear(n));
    }

    public static Long getbeforeYearTime(Date date,Integer n) {
        return getLong(getbeforeYear(date,n));
    }


    //一天的开始
    public static Date getStartOfDay(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return getStartOfDay(localDate);
    }

    public static Date getStartOfDay(TemporalAccessor date) {
        LocalDate localDate = LocalDate.from(date);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    //一天的结束
    public static Date getEndOfDay(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return getEndOfDay(localDate);
    }

    public static Date getEndOfDay(TemporalAccessor date) {
        LocalDate localDate = LocalDate.from(date);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1L).minusNanos(1L).toInstant());
    }

    /**
     * 根据开始日期和结束日期得到segmentName
     *
     * @param s
     * @param e
     * @return
     */
    public static String getSegmentName(LocalDate s, LocalDate e) {
        return getSegmentTime(s) + "_" + getSegmentTime(e);
    }

//    /**
//     * 根据开始日期和结束日期 日期类型 和调度类型 得到segmentName  并加工需要重刷的任务
//     *
//     * @param s
//     * @param e
//     * @return
//     */
//    public static String getSegmentName(int s, int e, int dataType, String cycleType, JobTask task) {
//        int mindays = 0;
//        if (JobCycleEnum.DAY.getCode().equals(cycleType)) {
//            mindays = 1;
//        } else if (JobCycleEnum.WEEK.getCode().equals(cycleType)) {
//            mindays = 7;
//        } else {
//            throw new RuntimeException("Cycle: "+cycleType+"do not support  deleteSegment, only support DAY and WEEK");
//        }
//        LocalDate sLocalDate;
//        LocalDate eLocalDate;
//        Date date = task.getFireTime();
//        if (JobDateTypeEnum.DAY.getCode() == dataType) {
//            sLocalDate = getbeforeDay(date,s);
//            eLocalDate = getbeforeDay(date,e).minusDays(mindays);
//            task.setStartLong(getLong(sLocalDate.minusDays(mindays)));
//            task.setEndLong(getLong(sLocalDate));
//            return getSegmentTime(sLocalDate.minusDays(mindays)) + "_" + getSegmentTime(eLocalDate);
//        } else if (JobDateTypeEnum.WEEK.getCode() == dataType) {
//            sLocalDate = getbeforeWeek(date,s);
//            eLocalDate = getbeforeWeek(date,e).minusDays(mindays);
//            task.setStartLong(getLong(sLocalDate.minusDays(mindays)));
//            task.setEndLong(getLong(sLocalDate));
//            return getSegmentTime(sLocalDate.minusDays(mindays)) + "_" + getSegmentTime(eLocalDate);
//
//        } else if (JobDateTypeEnum.MONTH.getCode() == dataType) {
//            sLocalDate = getbeforeMonth(date,s);
//            eLocalDate = getbeforeMonth(date,e).minusDays(mindays);
//            task.setStartLong(getLong(sLocalDate.minusDays(mindays)));
//            task.setEndLong(getLong(sLocalDate));
//            return getSegmentTime(sLocalDate.minusDays(mindays)) + "_" + getSegmentTime(eLocalDate);
//        } else if (JobDateTypeEnum.HOUR.getCode() == dataType) {
//
//            throw new RuntimeException("do not support Hour deleteSegment");
//        } else if (JobDateTypeEnum.YEAR.getCode() == dataType) {
//            sLocalDate = getbeforeYear(date,s);
//            eLocalDate = getbeforeYear(date,e).minusDays(mindays);
//            task.setStartLong(getLong(sLocalDate.minusDays(mindays)));
//            task.setEndLong(getLong(sLocalDate));
//            return getSegmentTime(sLocalDate.minusDays(mindays)) + "_" + getSegmentTime(eLocalDate);
//        } else if (JobDateTypeEnum.WEEK_.getCode() == dataType) {
//            sLocalDate = getDateOfbeforeWeek_(date,s,1);
//            eLocalDate = getDateOfbeforeWeek_(date,e,1).minusDays(mindays);
//            task.setStartLong(getLong(sLocalDate.minusDays(mindays)));
//            task.setEndLong(getLong(sLocalDate));
//            return getSegmentTime(sLocalDate.minusDays(mindays)) + "_" + getSegmentTime(eLocalDate);
//        } else if (JobDateTypeEnum.MONTH_.getCode() == dataType) {
//            sLocalDate = getBeforeYearBeforeMouthsDayOfMouth(date,0, s, 1);
//            eLocalDate = getBeforeYearBeforeMouthsDayOfMouth(date,0, e, 1).minusDays(mindays);
//            task.setStartLong(getLong(sLocalDate.minusDays(mindays)));
//            task.setEndLong(getLong(sLocalDate));
//            return getSegmentTime(sLocalDate.minusDays(mindays)) + "_" + getSegmentTime(eLocalDate);
//        } else if (JobDateTypeEnum.YEER_.getCode() == dataType) {
//            throw new RuntimeException("do not support Year deleteSegment");
//        }
//        return null;
//    }


    public static long getBeforeHourTime(int n) {
        LocalDateTime localDateTime = LocalDateTime.now().minusHours(n);
        return getLong(localDateTime.toLocalDate()) + localDateTime.getHour() * 60 * 60 * 1000;
    }
    public static long getBeforeHourTime(Date date,int n) {
        LocalDateTime localDateTime = getLocalDateTimeBydata(date).minusHours(n);
        return getLong(localDateTime.toLocalDate()) + localDateTime.getHour() * 60 * 60 * 1000;
    }


    public static LocalDate getLocalDateBydata(Date date){
        if(date!=null){
            return  date.toInstant().atZone(ZoneOffset.of("+8")).toLocalDate();
        }else{
            return LocalDate.now();
        }
    }
    public static LocalDateTime getLocalDateTimeBydata(Date date){
        if(date!=null){
            return  date.toInstant().atZone(ZoneOffset.of("+8")).toLocalDateTime();
        }else{
            return LocalDateTime.now();
        }
    }
    public static void getLong(){
//        //方法一
//        long milliSecondsLeftToday = 86400000 - DateUtils.getFragmentInMilliseconds(Calendar.getInstance(), Calendar.DATE);
//        long secondsLeftToday = 86400 - DateUtils.getFragmentInSeconds(Calendar.getInstance(), Calendar.DATE);
//        System.out.println("当天剩余毫秒1：" + milliSecondsLeftToday);
//        System.out.println("当天剩余秒1：" + secondsLeftToday);


        //方法二
//        DateTime dateTime = new DateTime().millisOfDay().withMaximumValue();
//        long millSeconds2 = new Duration(new DateTime(), dateTime).getMillis();
//        long count = new Duration(new DateTime(), dateTime).getStandardSeconds();
//        System.out.println("当天剩余毫秒2：" + millSeconds2);
//        System.out.println("当天剩余秒2：" + count);


        //方法三:LocalDateTime和ChronoUnit为1.8新增
//        long millSeconds = ChronoUnit.MILLIS.between(LocalDateTime.now(),midnight);
        long s = System.currentTimeMillis();
            LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime midnight2 = LocalDateTime.now().plusDays(2).withHour(0).withMinute(0).withSecond(0).withNano(0);
         long  seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
         int  seconds2 = (int) ChronoUnit.SECONDS.between(midnight, midnight2);

        System.out.println(System.currentTimeMillis()-s);
//        System.out.println("当天剩余毫秒3：" + millSeconds);
        System.out.println("当天剩余秒：" + seconds);
        System.out.println("当天剩余秒2：" + seconds2);
    }

    public static void main(String[] args) {
        getLong();
    }


}
