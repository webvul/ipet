package com.sensetime.iva.ipet.util;

import com.sensetime.iva.ipet.common.DatePattern;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author gongchao
 */
public class DateUtil {
    static SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");

    public static String dateToString(Date date,String pattern){
        try{
            return new SimpleDateFormat(pattern).format(date);
        }catch (Exception e){
            return null;
        }

    }
    public static Date stringToDate(String date,String pattern){
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取本周对应星期的日期yyyy-MM-dd
     * @return 本周末
     */
    public static Date getThisWeekDate(int week) {
        Calendar cal = Calendar.getInstance();
        //n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
        int n = 1;
        cal.add(Calendar.DATE, n*7);
       //想周几，这里就传几Calendar.MONDAY（TUESDAY...）
        cal.set(Calendar.DAY_OF_WEEK,week);
        String sunday = dateToString(cal.getTime(), DatePattern.DEFAULT_DATE);
        return stringToDate(sunday, DatePattern.DEFAULT_DATE);
    }

    /**
     * 获取当前日期 yyyy-MM-dd
     * @return 当天
     */
    public static Date getThisDate() {
        String thisDate = DateUtil.dateToString(new Date(), DatePattern.DEFAULT_DATE);
        return DateUtil.stringToDate(thisDate, DatePattern.DEFAULT_DATE);
    }

    /**
     * 得到本周周一
     *
     * @return yyyy-MM-dd
     */
    public static String getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0){
            day_of_week = 7;
        }
        c.add(Calendar.DATE, -day_of_week + 1);
        System.out.println(c.getTime());
        return format.format(c.getTime());
    }

    /**
     * 得到本周周日
     *
     * @return yyyy-MM-dd
     */
    public static Date getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0){
            day_of_week = 7;
        }
        c.add(Calendar.DATE, -day_of_week + 7);

        return c.getTime();
    }

    /**
     * 查当前日期是一周中的第几天、星期几
     * @return 1=Sunday,2=Monday,,,7=Saturday
     */
    public static int getWhicthDay(Date today){
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取date之后的第几天的日期
     * @param date 指定date
     * @param day 之后第几天
     * @return MM/dd 日期
     */
    public static String getAppointDay(Date date,int day){
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd");
        return sf.format((date.getTime()+1000*60*60*24*day));
    }

    /**
     * 获取指定date是周几  1：第一天  7：第七天
     * @param date
     * @return
     */
    public static int getDateIsWhichDayForWeek(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     * get start date of given week no of a year
     * @param year
     * @param weekNo
     * @param  week 星期几（周一为0，周二为1...）
     * @return 获取哪一年，一年第几周，星期几的日期
     */
    public static String getWeekDayOfWeekNo(String year,String weekNo,int week){
        Calendar cal = getCalendarFormYear(Integer.parseInt(year));
        cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(weekNo));
        cal.add(Calendar.DAY_OF_MONTH,week);
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +
                cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * get Calendar of given year
     * @param year
     * @return
     */
    public static Calendar getCalendarFormYear(int year){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.YEAR, year);
        return cal;
    }

    /**
     * 获取两个时间段有多少周
     * @param startDate  开始日期 yyyy-MM-dd
     * @param endDate 结束日期 yyyy-MM-dd
     * @return
     */
    public static int getWeekCount(String startDate,String endDate) {
        Date start = DateUtil.stringToDate(startDate, DatePattern.DEFAULT_DATE);
        Date end = DateUtil.stringToDate(endDate, DatePattern.DEFAULT_DATE);
        double days = (double) ((end.getTime() - start.getTime()) / (1000*3600*24));
        return (int)Math.ceil(days/7);
    }

    /**
     * 获取两个时间段有多少月
     * @param startDate  开始日期 yyyy-MM
     * @param endDate 结束日期  yyy-MM
     * @return
     */
    public static int getMonthCount(String startDate,String endDate) {
        Date start = DateUtil.stringToDate(startDate, DatePattern.yyyy_MM);
        Date end = DateUtil.stringToDate(endDate, DatePattern.yyyy_MM);
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(start);
        aft.setTime(end);
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        return result+month;
    }

    /**
     * 获取当前日期的几个月后的日期
     * @param startDate 开始日期
     * @param month  延后几个月
     * @return
     */
    public static String getNextFewMonth(String startDate,int month){
        Date date = DateUtil.stringToDate(startDate, DatePattern.yyyy_MM);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH,month);
        return dateToString(cal.getTime(),DatePattern.yyyy_MM);
    }

    /**
     * 获取两个日期的最大或最小值
     * @param date1 日期1
     * @param date2 日期2
     * @param isMin 取最小
     * @return
     */
    public static String getMinOrMax(String date1,String date2,Boolean isMin){
       if(isMin){
           if(date1.compareTo(date2)<0){
               return  date1;
           }else{
               return date2;
           }
       }else{
           if(date1.compareTo(date2)>0){
               return  date1;
           }else{
               return  date2;
           }
       }
    }

}
