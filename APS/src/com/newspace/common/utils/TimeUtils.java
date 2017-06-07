package com.newspace.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 处理和转换时间日期对象的工具类
 * @author huqili
 * @since jdk1.6
 * @date 2016年9月22日 修改
 */
public class TimeUtils
{
    /**
     * SimpleDateFormat对象
     */
    private final static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * SimpleDateFormat对象
     */
    private final static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
    
    //时间字符串格式
    public static enum DateFormat{
        /**
         * 以-和:分隔
         */
        separated,
        /**
         * 连接
         */
        join
    };
    /**
     * 将时间日期字符串转换为java.util.Date对象(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * @param dateStr 时间日期字符串
     * @return
     * @throws ParseException
     */
    public static Date parseToDate(String dateStr)
    {
        return parseToDate(dateStr, DateFormat.separated);
    }
    /**
     * 将时间日期字符串转换为java.util.Date对象
     * @param dateStr 时间日期字符串
     * @param format 时间格式
     * @return
     * @throws ParseException
     */
    public static Date parseToDate(String dateString,DateFormat format)
    {
        SimpleDateFormat sdf = getSDF(format);
        try
        {
            return sdf.parse(dateString);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    /**
     * 将时间日期字符串转换为时间戳(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Timestamp parseToTimestamp(String dateStr)
    {
        return parseToTimestamp(dateStr, DateFormat.separated);
    }
    /**
     * 将时间日期字符串转换为时间戳
     * @param dateStr
     * @param format 时间格式
     * @return 
     * @throws ParseException
     */
    public static Timestamp parseToTimestamp(String dateStr,DateFormat format)
    {
        try
        {
            return new Timestamp(parseToDate(dateStr,format).getTime());
        }
        catch (RuntimeException e)
        {
            return null;
        }
    }
    /**
     * 将java.util.Date对象转换为格式化的时间日期字符串(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * @param date 被转换的java.util.Date对象
     * @return
     */
    public static String format(Date date)
    {
        return format(date,DateFormat.separated);
    }
    /**
     * 将java.util.Date对象转换为格式化的时间日期字符串
     * @param date 被转换的java.util.Date对象
     * @param format 时间格式
     * @return
     */
    public static String format(Date date,DateFormat format)
    {
        SimpleDateFormat sdf = getSDF(format);
        try
        {
            return sdf.format(date);
        }
        catch (RuntimeException e)
        {
            return null;
        }
    }
    /**
     * 将时间戳转换为格式化的时间日期字符串(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * @param timestamp  被转换的时间戳
     * @return
     */
    public static String format(Timestamp timestamp)
    {
        return format(timestamp, DateFormat.separated);
    }
    /**
     * 将时间戳转换为格式化的时间日期字符串
     * @param timestamp  被转换的时间戳
     * @param format 时间格式
     * @return
     */
    public static String format(Timestamp timestamp,DateFormat format)
    {
        SimpleDateFormat sdf = getSDF(format);
        try
        {
            Date date = new Date(timestamp.getTime());
            return sdf.format(date);
        }
        catch (RuntimeException e)
        {
            return null;
        }
    }
    /**
     * 获取当前时间对应的java.util.Date对象
     * @return
     */
    public static Date getDate()
    {
        return new Date(System.currentTimeMillis());
    }
    /**
     * 获取当前时间对应的时间戳
     * @return
     */
    public static Timestamp getTimestamp()
    {
        return new Timestamp(System.currentTimeMillis());
    }
    /**
     * 获取当前时间的时间戳的字符串(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static String getTimestampString()
    {
        return format(new Date());
    }
    /**
     * 获取当前时间的时间戳的字符串 
     * @param format 时间字符串格式
     * @return
     */
    public static String getTimestampString(DateFormat format)
    {
        return format(new Date(),format);
    }
    
    /**
     * 将合乎格式的日期字串转换成Timestamp对象(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * 转换失败则返回null
     * @param dateString 时间字符串
     * @return
     */
    public static Timestamp getTimestamp(String dateString){
        return getTimestamp(dateString, DateFormat.separated);
    }
    
    /**
     * 将Date转换为Timestamp
     * @param date
     * @return
     */
    public static Timestamp getTimestamp(Date date)
    {
    	return new Timestamp(date.getTime());
    }
    
    /**
     * 将合乎格式的日期字串转换成Timestamp对象
     * 转换失败则返回null
     * @param dateString 时间字符串
     * @param format 时间格式
     * @return
     */
    public static Timestamp getTimestamp(String dateString,DateFormat format)
    {
        Date date = parseToDate(dateString, format);
        if (date==null)
        {
            return null;
        }
        return new Timestamp(date.getTime());
    }
    /**
     * 获取指定毫秒数对应的时间戳
     * @param timeMillis
     * @return
     */
    public static Timestamp getTimestamp(long timeMillis)
    {
        return new Timestamp(timeMillis);
    }
    
    /**
     * 获取两个java.util.Date之间的相差的毫秒数
     * @param lower
     * @param upper
     * @return
     */
    public static long timeDifference(Date lower,Date upper)
    {
        return upper.getTime()-lower.getTime();
    }
    /**
     * 获取java.util.Date中年、月、日等值
     * @param date
     * @param type Calendar.YEAR 年 Calendar.MONTH月 Calendar.DATE日等
     * @return
     */
    public static int get(Date date,int type)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(type);
    }
    /**
     * 获取时间戳中年、月、日等值 
     * @param date
     * @param type Calendar.YEAR 年 Calendar.MONTH月 Calendar.DATE日等
     * @return
     */
    public static int get(Timestamp timestamp,int type)
    {
        Date date = new Date(timestamp.getTime());
        return get(date, type);
    }
    /**
     * 获取指定的SimpleDateFormat对象
     * @param format
     * @return
     */
    private static SimpleDateFormat getSDF(DateFormat format)
    {
        switch (format)
        {
            case separated:
                return sdf1;
            case join:
                return sdf2;
            default:
                return sdf1;
        }
    }
    
    
    /**
     * 得到当前日期字符
     * @return String，当前日期字符
     */
    public static String getCurDateTime ()
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault()) ;
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss" ;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
            DATE_FORMAT) ;
        sdf.setTimeZone(TimeZone.getDefault()) ;
        return sdf.format(now.getTime()) ;
    }

    /**
     * 得到当前日期
     *
     * @param dateFormate String，格式化字符串 
     * @return String，当前日期
     */
    public static String getCurDateTime (String dateFormate)
    {
        try
        {
            Calendar now = Calendar.getInstance(TimeZone.getDefault()) ;
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                dateFormate) ;
            sdf.setTimeZone(TimeZone.getDefault()) ;
            return sdf.format(now.getTime()) ;
        }
        catch (Exception e)
        {
            return getCurDateTime() ; //如果无法转化，则返回默认格式的时间。
        }
    }

    /**
     * 格式化日期为字符串
     * @param date，日期
     * @param dateFormate，格式化字符串
     * @return String，格式化后的日期字符串
     */
    public static String getDateString (java.util.Date date, String dateFormate)
    {
        if (date == null)
        {
            return "" ;
        }
        try
        {

            SimpleDateFormat sdf = new SimpleDateFormat(dateFormate) ;
            //Stringsdf.format(date);
            return sdf.format(date) ;
        }
        catch (Exception e)
        {
            //如果无法转化，则返回默认格式的时间。
            return getCurDateTime() ; 
        }
    }
    
    /**
     * 获取n天后的日期
     * @param date
     * @param dayCount 相隔天数
     * @return
     */
    public static Date getDay(Date date,int dayCount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, dayCount);
		date = calendar.getTime();
		return date;
	}
    
    /**
     * 获取n天前的日期
     * @param date
     * @param dayCount 相隔天数
     * @return
     */
    public static Date getDaysAgo(Date date,int dayCount) {
    	return getDay(date, -dayCount);
    	
	}
    

    /**
     * 获取yyyy-MM格式的月份字符串对应的Date
     * @param monthStr yyyy-MM格式的月份字符串
     * @return date
     */
    public static Date parseMonthStrToDate(String monthStr) {
    	 SimpleDateFormat sdf_month = new SimpleDateFormat("yyyy-MM");
    	 try
         {
             return sdf_month.parse(monthStr);
         }
         catch (ParseException e)
         {
             return null;
         }
	}
    
    /**
	 * 
	* @Title: isToday 
	* @Description: 判断日期 是不是今天,是返回ture  否返回false
	* @param time
	* @return boolean
	* @throws 
	* @author  tangpan
	* @date  2016年8月2日下午4:59:55
	 */
	public static  boolean isToday(Timestamp time)
	{
		boolean flag = false;
		if( null != time)
		{
			String today = TimeUtils.format(TimeUtils.getTimestamp()).split(" ")[0];//获取今天的年月日字符串
			String _time = TimeUtils.format(time).split(" ")[0]; //获取判定的时间的 年月日
			if(today.equals(_time))
				flag = true;
		}
		return flag;
	}
    
    
    public static void main(String[] args) {
//    	Calendar calendar = Calendar.getInstance();
//		calendar.add(Calendar.DATE, -1);    //得到前一天
//		Date date = calendar.getTime();
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
//		System.out.println(df.format(date));
//    	System.out.println(parseMonthStrToDate("2015-07"));
    	
//    	System.out.println(format(new Date(), DateFormat.separated));
    	System.out.println(getDay(new Date(), 2));
	}

}
