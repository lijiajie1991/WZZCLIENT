package common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
  * @说明 该方法用来存放处理日期时间相关的静态方法 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午2:49:24 
  */
public class DateTimeUtil {
	public static final String dayFormat = "yyyy-MM-dd";
	public static final String secondFormat = "yyyy-MM-dd HH:mm:ss";
	public static final String numberFormat = "yyyyMMddHHmmss";
	
	public static String getDateAfterDaysStr(Date d, int days) throws ParseException
	{
        return getDateStr(getDateAfterDays(d, days), dayFormat);
	}
	
	public static Date getDateAfterDays(Date d, int days)
	{
		Date newTime = new Date();
        Calendar calendarBiz = Calendar.getInstance();
        Calendar cldTime = Calendar.getInstance();
        calendarBiz.setTime(d);
        cldTime.setTime(newTime);
        calendarBiz.add(Calendar.DATE, days);
        Date newDate = calendarBiz.getTime();
        return newDate;
	}
	
	public static Date getDateAfterMinutes(Date d, int minutes)
	{
		Date newTime = new Date();
        Calendar calendarBiz = Calendar.getInstance();
        Calendar cldTime = Calendar.getInstance();
        calendarBiz.setTime(d);
        cldTime.setTime(newTime);
        calendarBiz.add(Calendar.MINUTE, minutes);
        Date newDate = calendarBiz.getTime();
        return newDate;
	}
	
	public static Date getLastDatethisDay(Date d) throws ParseException
	{
		Date newTime = new Date();
        Calendar calendarBiz = Calendar.getInstance();
        Calendar cldTime = Calendar.getInstance();
        calendarBiz.setTime(d);
        cldTime.setTime(newTime);
        calendarBiz.add(Calendar.MONTH, -1);
        Date newDate = calendarBiz.getTime();
        return newDate;
	}
	
	/**
	  * @功能描述 返回指定日期的字符串， 格式为yyyy-MM-dd
	  * @作者 黎嘉杰 
	  * @日期 2016年8月27日 下午2:49:48 
	  * @参数 d 指定日期
	  * @返回 指定日期的字符串
	  */
	public static String getDateStr(Date d)
	{
		return getDateStr(d, "yyyy-MM-dd");
	}
	
	/**
	  * @功能描述 返回指定日期指定格式的字符串
	  * @作者 黎嘉杰 
	  * @日期 2016年8月27日 下午2:50:34 
	  * @参数 d 指定日期  format 指定格式
	  * @返回 日期字符串
	  */
	public static String getDateStr(Date d, String format)
	{
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(d);
	}
	
	/**
	  * @功能描述 把指定对象进行解析成日期
	  * @作者 黎嘉杰 
	  * @日期 2016年8月27日 下午2:51:17 
	  * @参数 obj 指定对象， 一般为日期或者字符串
	  * @返回 解析后日期
	  */
	public static Date parseStr(Object obj) throws Exception
	{
		//如果对象为空， 返回当前日期时间
		if(obj == null)
			return new Date();
		
		//如果当前对象为日期类型， 直接强制类型转换后返回
		if(obj instanceof Date)
			return (Date) obj;
		
		//如果不为空且不是日期类型， 则调用toString方法转换为字符串
		String dateStr = obj.toString();
		
		//尝试通过三个格式进行转换
		try {
			return parseStr(dateStr, DateTimeUtil.secondFormat);
		} catch (Exception e) {
		}
		
		try {
			return parseStr(dateStr, DateTimeUtil.dayFormat);
		} catch (Exception e) {
		}
		
		try {
			return parseStr(dateStr, DateTimeUtil.numberFormat);
		} catch (Exception e) {
		}
		
		//如果三种格式都转换不成功则抛出异常
		throw new Exception("无法把" + obj + "转换成日期");
	}
	
	/**
	  * @功能描述 按照指定格式解析为日期
	  * @作者 黎嘉杰 
	  * @日期 2016年8月27日 下午2:55:44 
	  * @参数 dateStr 日期字符串   format  日期格式
	  * @返回  日期
	  */
	public static Date parseStr(String dateStr, String format) throws ParseException
	{
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.parse(dateStr);
	}
	
	/**
	  * @功能描述  把日期字符串转换成中文
	  * @作者 黎嘉杰 
	  * @日期 2016年8月27日 下午2:56:25 
	  * @参数 str 日期字符串  format 格式
	  * @返回 中文日期字符串
	  */
	public static String getGBKDateStr(String str, String format) throws ParseException
	{
		SimpleDateFormat f = new SimpleDateFormat(format);
		Date d = f.parse(str);
		
		return getDateStr(d, "yyyy年MM月dd日");
	}
}
