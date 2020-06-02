package com.yutu.utils.date;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: zhaobc
 * @Date: 2019/6/20 17:14
 * @Description: 日期操作类
 **/
public class DateUtils {
    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

    private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

    private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * @Author: zhaobc
     * @Date: 2019/6/20 17:15
     * @Description: 获得年份
     **/
    public static String getYear() {
        return sdfYear.format(new Date());
    }

    Timestamp timestamp = new Timestamp(new Date().getTime());

    /**
     * @Author: zhaobc
     * @Date: 2019/6/20 17:15
     * @Description: 获得年月日
     **/
    public static String getDay() {
        return sdfDay.format(new Date());
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/6/20 17:15
     * @Description: 获得年月日数字
     **/
    public static String getDays() {
        return sdfDays.format(new Date());
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/6/20 17:15
     * @Description: 获得时间
     **/
    public static String getTime() {
        return sdfTime.format(new Date());
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/6/20 17:16
     * @Description: (日期比较 ， 如果s > = e 返回true 否则返回false)
     **/
    public static boolean compareDate(String s, String e) {
        if (fomatDate(s) == null || fomatDate(e) == null) {
            return false;
        }
        return fomatDate(s).getTime() >= fomatDate(e).getTime();
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/6/20 17:16
     * @Description: 格式话日期
     **/
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/6/20 17:17
     * @Description: 判断年月日 日期是否合法
     **/
    public static boolean isValidDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/6/20 17:17
     * @Description: 获得两个日期间隔年份
     **/
    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/6/20 17:18
     * @Description: 获得两个日期间的天数
     **/
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        // System.out.println("相隔的天数="+day);

        return day;
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/6/20 17:18
     * @Description: 得到n天之后的日期
     **/
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/6/20 17:18
     * @Description: 得到n天之前的日期
     **/
    public static String getBeforeDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        String dateStr = sdfDays.format(date);

        return dateStr;
    }


    /**
     * @Author: zhaobc
     * @Date: 2019/6/20 17:19
     * @Description: 得到n天之后是周几
     **/
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/6/20 17:19
     * @Description: 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
     **/
    public static String date2Str(Date date) {
        return date2Str(date, "yyyy-MM-dd");
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/6/20 17:19
     * @Description: 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
     **/
    public static Date str2Date(String date) {
        if (StringUtils.isNotBlank(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new Date();
        } else {
            return null;
        }
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/6/20 17:19
     * @Description: 按照参数format的格式，日期转字符串
     **/
    public static String date2Str(Date date, String format) {
        if (null == format) {
            format = "yyyy-MM-dd";
        }
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/6/20 17:20
     * @Description: 把时间根据时、分、秒转换为时间段
     **/
    public static String getTimes(String StrDate) {
        String resultTimes = "";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now;

        try {
            now = new Date();
            Date date = df.parse(StrDate);
            long times = now.getTime() - date.getTime();
            long day = times / (24 * 60 * 60 * 1000);
            long hour = (times / (60 * 60 * 1000) - day * 24);
            long min = ((times / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec = (times / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            StringBuffer sb = new StringBuffer();
            // sb.append("发表于：");
            if (hour > 0) {
                sb.append(hour + "小时前");
            } else if (min > 0) {
                sb.append(min + "分钟前");
            } else {
                sb.append(sec + "秒前");
            }

            resultTimes = sb.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return resultTimes;
    }


    /**
     * @Author: zhaobc
     * @Date: 2019/6/20 17:21
     * @Description: 获得时间戳 秒
     **/
    public static String getTimestamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
}
