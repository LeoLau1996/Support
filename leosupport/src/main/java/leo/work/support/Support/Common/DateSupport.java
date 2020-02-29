package leo.work.support.Support.Common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019/5/16
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class DateSupport {

    public static String Year = "yyyy";
    public static String Month = "MM";
    public static String Day = "dd";
    public static String Hour = "HH";
    public static String Min = "mm";
    public static String Second = "ss";

    public static final int oneDayMillis = 24 * 3600 * 1000; //一天的毫秒数


    //String转Data
    public static Date String2Data(String dateString) {
        return String2Data(dateString, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date String2Data(String dateString, String Type) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Type);
            date = sdf.parse(dateString);
        } catch (Exception e) {
        }
        return date;
    }


    //String转Calendar
    public static Calendar String2Calendar(String str) {
        return String2Calendar(str, "yyyy-MM-dd HH:mm:ss");
    }

    public static Calendar String2Calendar(String str, String Type) {
        SimpleDateFormat sdf = new SimpleDateFormat(Type);
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    //Data转Calendar
    public static Calendar Date2Calendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    //Calendar转Date
    public static Date Calendar2Date(Calendar calendar) {
        return calendar.getTime();
    }

    public static String toString(long unix, String type) {
        return new SimpleDateFormat(type).format(new Date(unix));
    }

    public static String toString(Date date, String type) {
        return new SimpleDateFormat(type).format(date);
    }

    public static String toString(Calendar calendar, String type) {
        return new SimpleDateFormat(type).format(calendar.getTime());
    }

    //分钟转xx时xx分
    public static String min2Hour(int second) {
        int h = second / 3600;
        int m = (second % 3600) / 60;
        String min;
        if (m == 0)
            min = "00";
        else if (m < 10)
            min = "0" + m;
        else
            min = "" + m;
        return h + ":" + min;
    }

    //获取当前年份
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    //获取当前月份
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    //获取当前几号
    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    //增加月数
    public static long addMonth(long currentTime, int monthCount) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + monthCount);
        return now.getTimeInMillis();
    }

    //增加星期数
    public static long addWeek(long currentTime, int weekCount) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);
        now.set(Calendar.WEEK_OF_MONTH, now.get(Calendar.WEEK_OF_MONTH) + weekCount);
        return now.getTimeInMillis();
    }

    //增加天数
    public static long addDay(long currentTime, int day) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);
        now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) + day);
        return now.getTimeInMillis();
    }

    //聊天用的  刚刚、1分钟前....
    public static String timeSpan(long var) {
        String ret = null;
        long currentTime = new Date().getTime();
        long offset = currentTime - var;
        offset /= 1000;
        if (var == 0) {
            ret = "";
        } else {
            if (offset > 0) {
                if (offset / 60 == 0) {
//                    ret = offset % 60 + "秒前";
                    ret = "1分钟前";
                } else if (offset / 60 / 60 == 0) {
                    ret = offset / 60 % 60 + "分钟前";
                } else if (offset / 60 / 60 / 24 == 0) {
                    ret = offset / 60 / 60 % 24 + "小时前";
                } else {
                    ret = toString(var, "MM-dd");
                }
            } else {
                ret = "刚刚";
            }
        }
        return ret;
    }

    //获取每个月的月数
    public static int[] getMonthCount(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);//指定年份
        calendar.set(Calendar.MONTH, 1);//指定月份 Java月份从0开始算
        int twoCount = calendar.getActualMaximum(Calendar.DATE);
        int[] MonthCount = {31, twoCount, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return MonthCount;
    }

    //通过年月日获取今天星期几
    public static int getWeek(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);//指定年份
        calendar.set(Calendar.MONTH, month - 1);//指定月份 Java月份从0开始算
        calendar.set(Calendar.DAY_OF_MONTH, day);  //指定日
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);//获取指定年份月份中指定某天是星期几
        dayOfWeek = dayOfWeek - 1;
        if (dayOfWeek == 0)
            dayOfWeek = 7;
        return dayOfWeek;
    }

    //是否同一天
    public static boolean isSameDay(Date var1, Date var2) {
        return isSameDay(var1.getTime(), var2.getTime());
    }

    public static boolean isSameDay(long var1, long var2) {
        long var1DayCount = (var1 - TimeZone.getDefault().getRawOffset()) / oneDayMillis;
        long var2DayCount = (var2 - TimeZone.getDefault().getRawOffset()) / oneDayMillis;
        return var1DayCount == var2DayCount;
    }

    //是否是昨天
    public static boolean isYesterday(Date var) {
        return isYesterday(var.getTime());
    }

    public static boolean isYesterday(long var) {
        long curDayCount = (System.currentTimeMillis() - TimeZone.getDefault().getRawOffset()) / oneDayMillis;
        long varDayCount = (var - TimeZone.getDefault().getRawOffset()) / oneDayMillis;
        return curDayCount == varDayCount + 1;
    }

}
