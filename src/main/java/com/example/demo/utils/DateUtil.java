package com.example.demo.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateUtil extends DateSupport {

    private static final long ONE_DAY_TIME = 24 * 60 * 60 * 1000;
    public static String DATE_FORMAT_STRING_YM = "yyyy-MM";
    public static String DATE_FORMAT_STRING_COMMON = "yyyy-MM-dd";
    public static String DATE_FORMAT_STRING_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_FORMAT_STRING_HM = "yyyy-MM-dd HH:mm";
    public static String DATE_FORMAT_STRING_JOIN = "yyyyMMddHHmmss";
    public static String DATE_FORMAT_STRING_TIME = "HH:mm:ss";
    public static String DATE_FORMAT_STRING_TIME_CN = "MM月dd日HH时mm分";
    public static String DATE_FORMAT_STRING_DEFAULT_CN = "yyyy年MM月dd日 HH时mm分ss秒";
    public static String DATE_FORMAT_STRING_COMMON_CN = "yyyy年MM月dd日";
    public static String DATE_FORMAT_STRING_EXCEL = "EEE MMM dd HH:mm:ss zzz yyyy";
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static String nowDateForStr(String format) {
        Date date = Calendar.getInstance().getTime();

        return dateForString(date, format);
    }

    /**
     * 获取某个时间段内所有日期
     *
     * @param begin
     * @param end
     * @return
     */
    public static List<String> getDayBetweenDates(String begin, String end) {
        Date dBegin = strToDate(begin);
        Date dEnd = strToDate(end);
        List<String> lDate = new ArrayList<String>();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        lDate.add(sd.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(sd.format(calBegin.getTime()));
        }
        return lDate;
    }

    /**
     * 星期一到星期日分别依次对应1到7
     *
     * @param date 日期
     * @return 星期几的数字表示
     */
    public static int dayForWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int dayInt = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayInt == 0) {
            dayInt = 7;
        }
        return dayInt;
    }

    public static String[] getDates(String dateFrom, String dateEnd, String weekDays) {
        long time;
        long perDayMilSec = 24 * 60 * 60 * 1000;
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateFrom = sdf.format(sdf.parse(dateFrom).getTime() - perDayMilSec);
            while (true) {
                time = sdf.parse(dateFrom).getTime();
                time = time + perDayMilSec;
                Date date = new Date(time);
                dateFrom = sdf.format(date);
                if (dateFrom.compareTo(dateEnd) <= 0) {
                    //查询的某一时间的星期系数
                    int weekDay = dayForWeek(date);
                    //判断当期日期的星期系数是否是需要查询的
                    if (weekDays.contains(Integer.toString(weekDay))) {
                        dateList.add(dateFrom);
                    }
                } else {
                    break;
                }
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        String[] dateArray = new String[dateList.size()];
        dateList.toArray(dateArray);
        return dateArray;
    }


    public static String getMonthLastDayYMD() {
        return getMonthLastDay().split(" ")[0];
    }

    /**
     * 根据格式yyyy-MM-dd,获取当前日期
     *
     * @return String
     * @author tangshengyu
     * @version falvm
     * @date Dec 9, 2009
     */
    public static String nowDateForStrYMD() {

        return nowDateForStr("yyyy-MM-dd");

    }

    public static String nowDateForStrYMDHMS() {

        return nowDateForStr("yyyy-MM-dd HH:mm:ss");

    }

    public static String getYMDHMSSS() {
        return nowDateForStr("yyyyMMddHHmmssSSS");
    }

    public static String getYMDHM() {
        return nowDateForStr("yyyy-MM-dd HH:mm");
    }

    public static String getYMDHMS() {
        return nowDateForStr("yyyyMMddHHmmss");
    }

    public static String dateToFullStr(Date dateDate) {
        if (dateDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(dateDate);
            return dateString;
        }
        return "";
    }

    /**
     * getMonth:根据参数获取月份时间,精确到月份. <br/>
     * -1 上个月   -2前2个月   1 下个月
     *
     * @return
     * @author Administrator
     */
    public static String getMonth(int i) {
        Date date = new Date();// 当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化对象
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(date);// 设置当前日期
        calendar.add(Calendar.MONTH, i);// 月份减一
        return sdf.format(calendar.getTime());
    }

    public static String getTommorrow() {
        String strCurrentDate = "";
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        strCurrentDate = formatter.format(c.getTime());
        return strCurrentDate;
    }

    /**
     * getLastWeekDateTime:获取当前时间的上一个周时间. <br/>
     *
     * @return
     * @author Administrator
     * @since JDK 1.6
     */
    public static String getLastWeekDateTime() {
        Date date = new Date();// 当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化对象
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(date);// 设置当前日期
        calendar.add(Calendar.WEEK_OF_MONTH, -1);// 周数减一
        return sdf.format(calendar.getTime());
    }

    public static String getWeekByNum(String dateStr, int num) {
        Date date = new Date();// 当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化对象
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(date);// 设置日期
        calendar.add(Calendar.WEEK_OF_MONTH, num);
        return sdf.format(calendar.getTime());
    }

    /**
     * getFirstLastDayInWeek:获取一周的开始和结束时间. <br/>
     *
     * @author Chenwang
     */
    public static String[] getFirstLastDayInWeek(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化对象
        Date date = new Date();
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException var10) {
            var10.printStackTrace();
        }
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar2.setTime(date);
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek <= 0) {
            dayOfWeek = 7;
        }
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1);
        calendar2.add(Calendar.DATE, offset2);
        // Monday
        String day_first = sdf.format(calendar1.getTime());
        // Sunday
        String day_last = sdf.format(calendar2.getTime());
        return new String[]{day_first, day_last};
    }

    /**
     * getLastWeekDateTime:获取当前时间的上n个周时间.
     *
     * @return
     * @author Administrator
     * @since JDK 1.6
     */
    public static String getLastWeekDateTime(int n) {
        Date date = new Date();// 当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化对象
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(date);// 设置当前日期
        calendar.add(Calendar.WEEK_OF_MONTH, -n);// 周数减n
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取 日期 是第几周 yyyy-ww
     *
     * @return
     */
    public static String getWeekNumber(String date) {
        if (date.length() < 9) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(7);
        String fmtStr = "yyyy-MM-dd";
        if (date.length() > 10) {
            fmtStr = "yyyy-MM-dd HH:mm:ss";
        }
        DateFormat df = new SimpleDateFormat(fmtStr);
        try {
            calendar.setTime(df.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        String wStr = week + "";
        if (week < 10) {
            wStr = "0" + wStr;
        }
        return calendar.get(Calendar.YEAR) + "-" + wStr;
    }

    /**
     * 获取 日期 是第几周 yyyy-ww
     *
     * @return
     */
    public static String[] getDateByWeeks(String date) {
        // 2014-03-27 23:59:59 == > yyyy-ww
        String week = getWeekNumber(date);
        String[] s = getDateByWeek(week);
        // return new String[] { s[0] + " 00:00:00", s[1] + " 23:59:59" };
        return new String[]{s[0], s[1]};
    }

    /**
     * 根据自然周获取开始日期和结束日期
     *
     * @param weekdate
     * @return
     */
    public static String[] getDateByWeek(String weekdate) {
        String[] arr = weekdate.split("-");
        int year = Integer.parseInt(arr[0]);
        int week = Integer.parseInt(arr[1]);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.WEEK_OF_YEAR, week);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        // c.setMinimalDaysInFirstWeek(7);
        // String date1 = dateFormat.format(c.getTime());
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        String start = dateFormat1.format(c.getTime());
        c.add(Calendar.DATE, 6);
        String end = dateFormat1.format(c.getTime());
        return new String[]{start, end};
    }

    /**
     * getMonthLastDay:获取当前月最后一天. <br/>
     *
     * @return
     * @author Administrator
     * @since JDK 1.6
     */
    public static String getMonthLastDay() {
        return getFirstLastDayInMonth(nowDateForStrYMDHMS())[1];
    }

    /**
     * 获取当天是第几月
     *
     * @return yyyy-MM
     */
    public static String getMonthNumber() {
        SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
        return ym.format(new Date());
    }

    /**
     * 获取当天是第几月
     *
     * @return yyyy-MM
     */
    public static String getMonthNumber(String date) {
        SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
        if (StringUtils.isEmpty(date)) {
            return ym.format(new Date());
        }
        return date.substring(0, 7);
    }

    /**
     * getLastMonthDateTime:获取当前时间的上一个月时间. <br/>
     *
     * @return
     * @author Administrator
     * @since JDK 1.6
     */
    public static String getLastMonthDateTime() {
        Date date = new Date();// 当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化对象
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(date);// 设置当前日期
        calendar.add(Calendar.MONTH, -1);// 月份减一
        return sdf.format(calendar.getTime());
    }

    /**
     * getLastMonthDateTime:获取当前时间的上n个月时间. <br/>
     *
     * @return
     * @author Administrator
     * @since JDK 1.6
     */
    public static String getLastMonthDateTime(int n) {
        Date date = new Date();// 当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化对象
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(date);// 设置当前日期
        calendar.add(Calendar.MONTH, -n);// 月份减n
        return sdf.format(calendar.getTime());
    }

    /**
     * getLastMonthDateTime:获取当前时间的上num月时间. <br/>
     *
     * @return
     * @author Administrator
     * @since JDK 1.6
     */
    public static String getSixMonthDateTime(int num) {
        Date date = new Date();// 当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化对象
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(date);// 设置当前日期
        calendar.add(Calendar.MONTH, -num);// 月份减num
        return sdf.format(calendar.getTime());
    }

    /**
     * getLastMonthDateTime:获取当前时间的上一个月时间,精确到月份. <br/>
     *
     * @return
     * @author Administrator
     * @since JDK 1.6
     */
    public static String getLastMonthDateMonth() {
        Date date = new Date();// 当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化对象
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(date);// 设置当前日期
        calendar.add(Calendar.MONTH, -1);// 月份减一
        return sdf.format(calendar.getTime());
    }

    /**
     * 某一个月第一天和最后一天
     *
     * @param dateStr
     * @return
     */
    public static String[] getFirstLastDayInMonth(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // calendar.add(Calendar.MONTH, -1);
        // calendar.set(Calendar.MONTH, value)
        Date theDate = calendar.getTime();

        // 上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(
                " 00:00:00");
        day_first = str.toString();

        // 上个月最后一天
        calendar.add(Calendar.MONTH, 1); // 加一个月
        calendar.set(Calendar.DATE, 1); // 设置为该月第一天
        calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(
                " 23:59:59");
        day_last = endStr.toString();

        return new String[]{day_first, day_last};
    }

    /**
     * getMonthByNum:获取n天后的月份日期
     *
     * @param day
     * @return
     * @author Administrator
     * @since JDK 1.6
     */
    public static String getMonthByNum(int day) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(new Date().getTime());
        c.add(Calendar.MONTH, day);// 天后的日期
        // System.out.println("date="+formatDate.format(c.getTime()));
        return formatDate.format(c.getTime());
    }

    /**
     * getMonthByNum:获取n天后的月份日期
     *
     * @param day
     * @return
     * @author Administrator
     * @since JDK 1.6
     */
    public static String getMonthByTime(String strDate, int day) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(strToDate(strDate));
        c.add(Calendar.MONTH, day);// 天后的日期
        // System.out.println("date="+formatDate.format(c.getTime()));
        return formatDate.format(c.getTime());
    }

    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将字符串转换成对应的date类型
     *
     * @param strDate
     * @return
     */
    public static Date strToDateHHmm(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 获取最近七天
     *
     * @return
     */
    public static String getDay7() {
        long day7 = 1000 * 60 * 60 * 24 * 7;
        long off = System.currentTimeMillis() - day7;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(off));
    }

    /**
     * 获取最近七天
     *
     * @return
     */
    public static String getDay7(String date) {
        Calendar c = Calendar.getInstance();
        Date et = null;
        try {
            et = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(et);
        c.add(Calendar.DAY_OF_YEAR, -7);
        return format.format(c.getTime());
    }

    /**
     * 获取指定天数前后N天的时间
     *
     * @return
     */
    public static String getDayBytime(String date, int n) {
        Calendar c = Calendar.getInstance();
        Date et = null;
        try {
            et = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(et);
        c.add(Calendar.DAY_OF_YEAR, n);
        return format.format(c.getTime());
    }

    /**
     * 根据当前日期获取当前月中的最后一天
     *
     * @return String
     * @author tangshengyu
     * @version panyu
     * @date Jul 16, 2010
     */
    public static String dateForMonthLastDayYMD(String strDate) {
        int lastDay = getDateMonthLastDay(strDate);
        Date d = strForDate(strDate, "yyyy-MM-dd");
        d.setDate(lastDay);
        return dateForString(d, "yyyy-MM-dd");
    }

    /**
     * 取当前年月日
     *
     * @return
     */
    public static String getYMDHMSS() {
        // TODO Auto-generated method stub
        return nowDateForStr("yyyyMMddHHmmss");
    }

    /**
     * 取指定时间
     *
     * @param from   从今天开始 前几天为负值，后几天为正直
     * @param format 格式化类型
     *               <p>
     *               例如取前一天getDate(-1, "YYYY_MM_DD") 取后一天 getDate(1, "YYYY_MM_DD");
     * @return
     */
    public static String getDate(int from, String format) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, from);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String day = dateFormat.format(c.getTime());
        return day;
    }

    /**
     * 获取N天前后的时间
     *
     * @return
     */
    public static String getDayTime(int n) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_YEAR, n);
        return formatter.format(c.getTime());
    }

    /**
     * 获取N天前后的时间戳
     *
     * @return
     */
    public static long getDayTimeLong(int n) {
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, n);
        return c.getTime().getTime();
    }

    /**
     * 获取N天前后的时间戳
     *
     * @return
     */
    public static long getDayLong(String str, int n) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(str);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_YEAR, n);
            return c.getTime().getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    public static String getNowMonthFirst() {
        // 获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        return format.format(c.getTime());
    }

    /**
     * 获取当前凌晨0点的时间
     *
     * @return
     */
    public static Date getCurrentZeroPoint() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取同比时间段 yyyy-MM-dd hh:mm:ss
     *
     * @param beginDay
     * @param endDay
     * @return
     */
    public static String[] getYearBasis(String beginDay, String endDay) {
        String[] section = new String[2];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date beginTime = dateFormat.parse(beginDay);
            Date endTime = dateFormat.parse(endDay);

            Calendar c = Calendar.getInstance();
            c.setTime(beginTime);
            c.add(Calendar.YEAR, -1);
            beginTime = c.getTime();
            section[0] = dateFormat.format(beginTime);

            c.setTime(endTime);
            c.add(Calendar.YEAR, -1);
            endTime = c.getTime();
            section[1] = dateFormat.format(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return section;
    }

    /**
     * 获取环比时间段
     *
     * @param beginDay
     * @param endDay
     * @return
     */
    public static String[] getLinkRelativeRatio(String beginDay, String endDay) {
        String[] section = new String[2];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date beginTime = dateFormat.parse(beginDay);
            Date endTime = dateFormat.parse(endDay);

            long sectionTime = endTime.getTime() - beginTime.getTime();

            endTime = new Date(beginTime.getTime() - ONE_DAY_TIME);
            beginTime = new Date(endTime.getTime() - sectionTime);

            section[0] = dateFormat.format(beginTime);
            section[1] = dateFormat.format(endTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return section;
    }

    /**
     * getRelaMonthTime:前后N月的月份日期
     *
     * @param str
     * @param n
     * @return
     * @author liubing
     */
    public static String getRelaMonthTime(String str, int n) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        try {
            Calendar c = Calendar.getInstance();
            Date date = dateFormat.parse(str);
            c.setTime(date);
            c.add(Calendar.MONTH, n);
            date = c.getTime();
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void transMap2Bean(Map<String, Object> map, Object obj) {
        // ConvertUtils.register(new DateLocaleConverter(), Date.class);
        ConvertUtils.register(new Converter() {

            @SuppressWarnings("rawtypes")
            @Override
            public Object convert(Class arg0, Object arg1) {
                // System.out.println("注册字符串转换为date类型转换器");
                if (arg1 == null) {
                    return null;
                }
                if (!(arg1 instanceof String)) {
                    throw new ConversionException("只支持字符串转换 !");
                }
                String str = (String) arg1;
                if (str.trim().equals("")) {
                    return null;
                }

                SimpleDateFormat sd = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");

                try {
                    return sd.parse(str);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }

        }, Date.class);
        if (map == null || obj == null) {
            return;
        }
        try {
            String key = null;
            Object value = null;
            Map newMap = new HashMap();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                newMap.put(key.toLowerCase().replace("_", ""), value);
            }
            BeanUtils.populate(obj, newMap);
        } catch (Exception e) {
            System.out.println("Map<String,Object>转化Bean异常：" + e);
        }
    }

    public static void populate2(Object obj, Map<String, Object> map) {
        Class clazz = obj.getClass();

        Map<String, Method> mapValue = new HashMap<String, Method>();

        Method[] methods = clazz.getDeclaredMethods();

        String methodName = null;
        for (Method method : methods) {
            methodName = method.getName();
            if (methodName.startsWith("set")) {
                mapValue.put(methodName.substring(3).toLowerCase(), method);
            }
        }

        try {
            String key = null;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                key = entry.getKey();
                if (mapValue.containsKey(key)) {
                    mapValue.get(key).invoke(obj, entry.getValue() + "");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date begin, Date end) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        begin = sdf.parse(sdf.format(begin));
        end = sdf.parse(sdf.format(end));

        Calendar cal = Calendar.getInstance();
        cal.setTime(begin);

        long time1 = cal.getTimeInMillis();
        cal.setTime(end);

        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取指定日期的n天后的日期
     *
     * @param dateBegin
     * @param day       正数是n天后，负数是n天前
     * @return
     */
    public static String getForwardDate(String dateBegin, int day) {
        try {
            SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdft.parse(dateBegin);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, day);
            return sdft.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateBegin;
    }

    /**
     * 获取制定时间转字符串
     */
    public static String getStringDate(Date time, String format) {
        SimpleDateFormat sdft = new SimpleDateFormat(format);
        try {
            if (time != null) {
                return sdft.format(time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * getMonthByNum:获取n月后的年月
     *
     * @param day
     * @return
     * @author Administrator
     * @since JDK 1.6
     */
    public static String getDateByLastMonth(int day) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, day);// n月后的日期
        return formatDate.format(c.getTime());
    }

    /**
     * getMonthByNum:获取n月后的年月日
     *
     * @param day
     * @return
     * @author Administrator
     * @since JDK 1.6
     */
    public static String getDateByMonth(String strDate, int day) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(strToDate(strDate));
        c.add(Calendar.MONTH, day);// n月后的日期
        return formatDate.format(c.getTime());
    }

    /**
     * 获取两个时间段内所有的月份
     *
     * @param minDate
     * @param maxDate
     * @return list
     * @throws ParseException
     */
    public static List<String> getMonthBetween(String minDate, String maxDate)
            throws ParseException {
        List<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    public static String getString(Date date, String formatString) {
        if (formatString == null) {
            return getString(date);
        } else {
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            return format.format(date);
        }
    }

    public static String getString(Date date) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat format = new SimpleDateFormat(
                    DATE_FORMAT_STRING_COMMON);
            return format.format(date);
        }
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds   精确到秒的字符串
     * @param formatStr
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }


    public static String getDateByLastMonth1(int day) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, day);//n月后的日期
        return formatDate.format(c.getTime());
    }

    /**
     * getMonth:根据参数获取月份时间,精确到月份. <br/>
     * -1 上个月   -2前2个月   1 下个月
     *
     * @return
     * @author Administrator
     */
    public static String getNextMonth(String date, int i) {
        Date time = null;
        try {
            time = new SimpleDateFormat("yyyy-MM").parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化对象
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(time);// 设置当前日期
        calendar.add(Calendar.MONTH, i);// 月份减一
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取n月前后的日期
     *
     * @param month yyyy-MM
     * @param num   月数
     * @return
     */
    public static String getMonthByNum(String month, int num) {
        month = month.substring(0, 7);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(strToDate(month + "-01"));
        c.add(Calendar.MONTH, num);//n月后的日期
        return sdf.format(c.getTime());
    }

    /**
     * 比较日期大小
     *
     * @param DATE1      第一个时间
     * @param DATE2      第二个时间
     * @param dateFormat 日期格式
     * @return Integer null日期格式有误，1：第一个日期大，0：两个日期一样，-1：第二个日期大
     */
    public static Integer compareDate(String DATE1, String DATE2, String dateFormat) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 时间戳转换成日期格式字符串
     *
     * @param object 精确到秒的字符串
     * @return
     */
    public static String timeStampDate(Object object) {
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (object == null || object.equals("") || object.equals("null")) {
            return "";
        }
        return object instanceof Number ? ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(String.valueOf(object))), ZoneId.of("Asia/Shanghai"))) : object.toString();
    }

    /**
     * 获取传入时间为第几天
     *
     * @return
     */
    public static int getDayNum(String date) {
        Calendar c = Calendar.getInstance();
        c.setTime(DateUtil.strToDate(date));
        int day = c.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * 计算两个字符串日期之间的天数差
     *
     * @return
     */
    public static Long between_days(String a, String b) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// 自定义时间格式

        Calendar calendar_a = Calendar.getInstance();// 获取日历对象
        Calendar calendar_b = Calendar.getInstance();

        Date date_a = null;
        Date date_b = null;

        try {
            date_a = simpleDateFormat.parse(a);//字符串转Date
            date_b = simpleDateFormat.parse(b);
            calendar_a.setTime(date_a);// 设置日历
            calendar_b.setTime(date_b);
        } catch (ParseException e) {
            e.printStackTrace();//格式化异常
            return 1L;
        }

        long time_a = calendar_a.getTimeInMillis();
        long time_b = calendar_b.getTimeInMillis();

        long between_days = (time_b - time_a) / (1000 * 3600 * 24);//计算相差天数

        return between_days;
    }

    /**
     * 获取当前日期monthNum个月前的时间
     *
     * @param monthNum
     * @return
     */
    public static String getMonthBeforeDay(Date date, int monthNum) {
        // 获取当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(date);//把当前时间赋给日历
        calendar.add(calendar.MONTH, monthNum); //设置为前monthNum月
        date = calendar.getTime();//获取monthNum个月前的时间

        return dateFormat.format(date);
    }

    /**
     * 两个时间之间相差距离多少秒钟
     *
     * @param time1 时间参数 1：
     * @param time2 时间参数 2：
     * @return 相差分钟数
     */

    public static long getDistanceSeconds(String time1, String time2) throws Exception {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT_STRING_DEFAULT);

        Date date1 = df.parse(time1);
        Date date2 = df.parse(time2);
        long stamp1 = date1.getTime();
        long stamp2 = date2.getTime();
        long diff;
        if (stamp1 < stamp2) {
            diff = stamp2 - stamp1;
        } else {
            diff = stamp1 - stamp2;
        }

        return diff / 1000;
    }

    /**
     * 两个时间之间相差距离多少分钟
     *
     * @param time1 时间参数 1：
     * @param time2 时间参数 2：
     * @return 相差分钟数
     */

    public static long getDistanceMinutes(String time1, String time2) throws Exception {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT_STRING_DEFAULT);

        Date date1 = df.parse(time1);
        Date date2 = df.parse(time2);
        long stamp1 = date1.getTime();
        long stamp2 = date2.getTime();
        long diff;
        if (stamp1 < stamp2) {
            diff = stamp2 - stamp1;
        } else {
            diff = stamp1 - stamp2;
        }

        return diff / (1000 * 60);
    }

    /**
     * 两个时间之间相差距离多少小时
     *
     * @param time1 时间参数 1：
     * @param time2 时间参数 2：
     * @return 相差分钟数
     */

    public static long getDistanceHours(String time1, String time2) throws Exception {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT_STRING_DEFAULT);

        Date date1 = df.parse(time1);
        Date date2 = df.parse(time2);
        long stamp1 = date1.getTime();
        long stamp2 = date2.getTime();
        long diff;
        if (stamp1 < stamp2) {
            diff = stamp2 - stamp1;
        } else {
            diff = stamp1 - stamp2;
        }

        return diff / (1000 * 60 * 60);
    }

    /**
     * 两个时间之间相差距离多少天
     *
     * @param time1 时间参数 1：
     * @param time2 时间参数 2：
     * @return 相差分钟数
     */

    public static long getDistanceDays(String time1, String time2) throws Exception {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT_STRING_DEFAULT);

        Date date1 = df.parse(time1);
        Date date2 = df.parse(time2);
        long stamp1 = date1.getTime();
        long stamp2 = date2.getTime();
        long diff;
        if (stamp1 < stamp2) {
            diff = stamp2 - stamp1;
        } else {
            diff = stamp1 - stamp2;
        }

        return diff / (1000 * 60 * 60 * 24);
    }
}
