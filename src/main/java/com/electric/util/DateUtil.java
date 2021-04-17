package com.electric.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.electric.constant.Numbers;
import com.electric.enums.WeekEnum;

/**
 * 日期工具类
 * 
 * @Author Administrator
 * @Date 2020-9-10
 *
 */

public class DateUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 日期转字符 默认格式：yyyy-MM-dd HH:mm:ss
	 *
	 * @param date
	 * @return
	 * @create 2020年6月29日 下午1:57:07 luochao
	 * @history
	 */
	public static String dateToString(Date date) {
		return dateToString(date, TIME_FORMAT);
	}

	/**
	 * Date 对象转换成对应格式的 字符串
	 *
	 * @param date
	 * @param format 格式化模板，为空时默认："yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat formater = new SimpleDateFormat(StringUtils.isEmpty(format) ? TIME_FORMAT : format.trim());
		return formater.format(date);
	}

	/**
	 * 字符串转date
	 *
	 * @param date
	 * @param format 格式化模板，为空时默认："yyyy-MM-dd HH:mm:ss"
	 * @return
	 * @create 2020年6月30日 上午9:23:15 luochao
	 * @history
	 */
	public static Date stringToDate(String date, String format) {
		Date newDate = null;
		try {
			SimpleDateFormat formater = new SimpleDateFormat(StringUtils.isEmpty(format) ? TIME_FORMAT : format.trim());
			newDate = formater.parse(date);
		} catch (ParseException e) {
			LOGGER.error("转换时间异常", e);
		}
		return newDate;
	}

	/**
	 * 字符串转日期,支持两种格式：yyyy-MM-dd HH:mm:ss和yyyy-MM-dd，根据字符串长度来判断
	 *
	 * @param dateSource
	 * @return
	 */
	public static Date stringToDate(String date) {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		DateFormat fmt = null;
		if (date.indexOf("T") > -1) {
			String[] timeArray = date.split("[+]");
			date = timeArray[0].replace("T", " ");
			fmt = new SimpleDateFormat(TIME_FORMAT);
		} else if (date.length() <= Numbers.INT_10) {
			fmt = new SimpleDateFormat(DAY_FORMAT);
		} else if (date.length() == Numbers.INT_14) {
			fmt = new SimpleDateFormat(TIME_FORMAT_THREE);
		} else {
			fmt = new SimpleDateFormat(TIME_FORMAT);
		}
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			LOGGER.error("时间转换失败", e);
			return null;
		}
	}

	/**
	 * long转date,返回String格式
	 *
	 * @param millSec    毫秒级别
	 * @param dateFormat 格式化模板
	 * @return
	 * @create 2020年6月30日 上午9:44:49 luochao
	 * @history
	 */
	public static String longToDate(Long millSec, String dateFormat) {
		Assert.notNull(dateFormat, "format不能为空");
		Assert.notNull(millSec, "millSec不能为空");
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = new Date(millSec);
		return sdf.format(date);
	}

	/**
	 * long转date,返回Date格式
	 *
	 * @param millSec
	 * @return
	 * @create 2020年6月29日 下午3:58:35 luochao
	 * @history
	 */
	public static Date longToDate(Long millSec) {
		if (millSec == null) {
			return null;
		}
		return new Date(millSec);
	}

	/**
	 * 日期转long(毫秒级)
	 *
	 * @param date
	 * @return
	 * @create 2020年6月30日 上午9:47:53 luochao
	 * @history
	 */
	public static long dateToLong(Date date) {
		return date.getTime();
	}

	/**
	 * 获取某时间月
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		return instance.get(Calendar.MONTH) + 1;
	}

	/**
	 * 取得传入日期当月开始时间，如：2020-05-01 00:00:00
	 */
	public static String getMonthStartTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		String format = new SimpleDateFormat(DAY_FORMAT).format(c.getTime());
		return format + " " + FIRST_TIME;
	}

	/**
	 * 取得传入日期当月结束时间，如：2020-05-31 23:59:59
	 */
	public static String getMonthEndTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		String format = new SimpleDateFormat(DAY_FORMAT).format(c.getTime());
		return format + " " + LAST_TIME;
	}

	/**
	 * 取得传入日期的当前月的最大天数 例： 28 ，29 ，30 ，31
	 */
	public static int getMonthMaxDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 判断两个日期是否在同一个月
	 *
	 * @param date1
	 * @param date2
	 * @return
	 * @create 2020年9月3日 上午10:03:41 luochao
	 * @history
	 */
	public static boolean isSameMonth(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("日期不能为空");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH));
	}

	/**
	 * 当月第一天，如：2010-03-01
	 *
	 * @return
	 * @create 2020年6月29日 下午1:15:02 luochao
	 * @history
	 */
	public static String getDayFirstOfCurMonth() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		return new SimpleDateFormat(DAY_FORMAT).format(c.getTime());
	}

	/**
	 * 当月最后一天，如：2010-03-31
	 *
	 * @return
	 * @create 2020年6月29日 下午1:15:21 luochao
	 * @history
	 */
	public static String getDayLastOfCurMonth() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new SimpleDateFormat(DAY_FORMAT).format(c.getTime());
	}

	/**
	 * 获取上一个月 例：202009
	 *
	 * @param date
	 * @return
	 * @create 2020年9月2日 下午5:42:10 luochao
	 * @history
	 */
	public static String getBeforeMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		return new SimpleDateFormat(MONTH_FORMAT2).format(c.getTime());
	}

	/***
	 * 获取年 yyyy
	 *
	 * @return
	 * @create 2020年8月4日 上午10:40:05 luochao
	 * @history
	 */
	public static String getYear() {
		return new SimpleDateFormat(YEAR_FORMAT).format(new Date());
	}

	/**
	 * 获取年月的第一天 2020-01-01
	 *
	 * @param year  年
	 * @param month 月
	 * @return
	 * @create 2020年7月8日 下午3:24:55 luochao
	 * @history
	 */
	public static String getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		return getFirstDayOfMonth(cal.getTime());
	}

	/**
	 * 获取当前月的第一天
	 *
	 * @return
	 * @create 2020年7月8日 下午3:25:15 luochao
	 * @history
	 */
	public static String getFirstDayOfMonth() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		return new SimpleDateFormat(DAY_FORMAT).format(c.getTime());
	}

	/**
	 * 取得指定日期所在月的第一天 2020-01-01
	 *
	 * @param date
	 * @return
	 * @create 2020年7月8日 下午3:25:15 luochao
	 * @history
	 */
	public static String getFirstDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		return new SimpleDateFormat(DAY_FORMAT).format(c.getTime());
	}

	/**
	 * 获取某年某月的最后一天，如：2010-03-31
	 *
	 * @param year  年
	 * @param month 月
	 * @return
	 * @create 2020年7月8日 下午3:20:03 luochao
	 * @history
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		return getLastDayOfMonth(cal.getTime());
	}

	/**
	 * 获取某个日期的当前月的最后一天，如：2010-03-31
	 *
	 * @param date
	 * @return
	 * @create 2020年7月8日 下午3:19:06 luochao
	 * @history
	 */
	public static String getLastDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new SimpleDateFormat(DAY_FORMAT).format(c.getTime());
	}

	/**
	 * 获取传入时间最后一天
	 *
	 * @param day
	 * @return
	 * @create 2020年9月3日 上午10:01:28 luochao
	 * @history
	 */
	public static String getLastDayOfMonth(Integer day) {
		Date date = stringToDate(day.toString(), DAY_FORMAT2);
		return getLastDayOfMonth(date);
	}

	/**
	 * 返回本周的开始时间与结束时间
	 *
	 * @return
	 * @create 2020年6月29日 下午3:10:43 luochao
	 * @history
	 */
	public static String[] getWeekStartAndEnd() {
		String today = dateToString(new Date(), DAY_FORMAT);
		int i = getDayOfWeek();
		if (i == 0) {
			i = Numbers.INT_7;
		}
		String beforeDay = getNextDayString(today, 1 - i, DAY_FORMAT);
		String afterDay = getNextDayString(today, Numbers.INT_7 - i, DAY_FORMAT);
		return new String[] { beforeDay + " " + FIRST_TIME, afterDay + " " + LAST_TIME };
	}

	/**
	 * 判断日期是否是当天
	 *
	 * @param date
	 * @return
	 * @create 2020年9月3日 上午9:51:10 luochao
	 * @history
	 */
	public static boolean isToday(Date date) {
		return dateToString(new Date(), DateUtil.DAY_FORMAT).equals(dateToString(date, DateUtil.DAY_FORMAT));
	}

	/**
	 * 获取当前日期 例： 2020-06-29
	 *
	 * @return
	 * @create 2020年6月29日 下午1:35:12 luochao
	 * @history
	 */
	public static String getDayNow() {
		return new SimpleDateFormat(DAY_FORMAT).format(new Date());
	}

	/**
	 * 请使用getTimeNow
	 *
	 * @return
	 * @create 2020年6月29日 下午5:03:27 luochao
	 * @history
	 */
	@Deprecated
	public static String getNowTime() {
		return getTimeNow();
	}

	/**
	 * 获取当前时间 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 * @create 2018年8月28日 下午2:09:37 luochao
	 * @history
	 */
	public static String getTimeNow() {
		SimpleDateFormat formater = new SimpleDateFormat(TIME_FORMAT);
		Date today = new Date();
		return formater.format(today);
	}

	/**
	 * 获取当前时间 格式：yyyyMMddHHmmss
	 * 
	 * @return
	 * @create 2018年8月28日 下午2:09:37 luochao
	 * @history
	 */
	public static String getTimeNow2() {
		SimpleDateFormat formater = new SimpleDateFormat(TIME_FORMAT_THREE);
		Date today = new Date();
		return formater.format(today);
	}

	/**
	 * 获取当前日期 例： 2020-06-29
	 *
	 * @param format
	 * @return
	 * @create 2020年6月29日 下午1:36:06 luochao
	 * @history
	 */
	public static String getTimeNowFormat(String format) {
		if (format == null) {
			format = DAY_FORMAT;
		}
		return new SimpleDateFormat(format).format(new Date());
	}

	/**
	 * getDayStart:获取传入时间的00:00:00
	 * 
	 * @author panweiqiang
	 * @date 2017年5月22日 上午10:50:42
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date) {
		if (date == null) {
			return date;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * getDayEnd:获取传入时间的23:59:59
	 * 
	 * @author panweiqiang
	 * @date 2017年5月22日 上午10:50:22
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		if (date == null) {
			return date;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, Numbers.INT_23);
		c.set(Calendar.MINUTE, Numbers.INT_59);
		c.set(Calendar.SECOND, Numbers.INT_59);
		return c.getTime();
	}

	/**
	 * 获取指定日期是星期几
	 *
	 * @param date
	 * @return
	 * @create 2019年1月10日 下午2:31:05 luochao
	 * @history
	 */
	public static Integer getWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 获得今天是礼拜几 例 ：1,2,3
	 *
	 * @return
	 * @create 2020年6月29日 下午1:36:16 luochao
	 * @history
	 */
	public static int getDayOfWeek() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int week = c.get(Calendar.DAY_OF_WEEK) - 1;
		return week;
	}

	/**
	 * 取得指定日期所在周的星期几
	 *
	 * @param date
	 * @return
	 */
	public static Date getDayOfWeek(Date date, WeekEnum weekEnum) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + weekEnum.getCodeToInt() - 1);
		return c.getTime();
	}

	/**
	 * 得到一个延后或前移几天的时间
	 *
	 * @param nowdate    时间
	 * @param delay      天数
	 * @param dateFormat 格式化
	 * @return
	 * @create 2018年7月24日 下午1:11:52 luochao
	 * @history
	 */
	public static String getNextDayString(String nowdate, int delay, String dateFormat) {
		Date d = stringToDate(nowdate, dateFormat);
		Date nextDayDate = getNextDayDate(d, delay);
		return new SimpleDateFormat(dateFormat).format(nextDayDate);
	}

	/**
	 * 得到一个延后或前移几天的时间
	 *
	 * @param nowdate
	 * @param delay
	 * @param dateFormat
	 * @return
	 * @create 2018年7月24日 下午3:26:17 luochao
	 * @history
	 */
	public static String getNextDayString(Date nowdate, int delay, String dateFormat) {
		Date nextDayDate = getNextDayDate(nowdate, delay);
		return new SimpleDateFormat(dateFormat).format(nextDayDate);
	}

	/**
	 * 得到一个延后或前移几天的时间
	 *
	 * @param date
	 * @param delay
	 * @return
	 * @create 2018年7月24日 下午1:12:34 luochao
	 * @history
	 */
	public static Date getNextDayDate(Date date, int delay) {
		try {
			Date d = new Date(date.getTime());
			long myTime = (d.getTime() / Numbers.SECOND) + delay * Numbers.DAY_OF_SECOND;
			d.setTime(myTime * Numbers.SECOND);
			return d;
		} catch (Exception e) {
			throw new RuntimeException("时间转换错误");
		}
	}

	/**
	 * 的到一个延后或者提前的月，delay为前移或后延的月数
	 *
	 * @param nowdate 要处理的时间
	 * @param delay   基数
	 * @return
	 * @history
	 */
	public static String getNextMonth(String nowdate, int delay) {
		Date date = null;
		try {
			date = new SimpleDateFormat(MONTH_FORMAT).parse(nowdate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, delay);
			return new SimpleDateFormat(MONTH_FORMAT).format(calendar.getTime());
		} catch (ParseException e) {
			LOGGER.error("时间转换失败", e);
		}
		return null;
	}

	/**
	 * 得到一个延后或前移几月的时间
	 *
	 * @param date  要处理的时间
	 * @param delay 基数
	 * @return
	 * @history
	 */
	public static Date getNextMonth(Date date, int delay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, delay);
		return calendar.getTime();
	}

	/**
	 * 返回下周的开始时间与结束时间
	 *
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String[] getWeekNext() {
		String today = dateToString(new Date(), DAY_FORMAT);
		int i = getDayOfWeek();
		if (i == 0) {
			i = Numbers.INT_7;
		}
		String beforeDay = getNextDayString(today, Numbers.INT_8 - i, DAY_FORMAT);
		String afterDay = getNextDayString(today, Numbers.INT_14 - i, DAY_FORMAT);
		return new String[] { beforeDay + " " + FIRST_TIME, afterDay + " " + LAST_TIME };
	}

	/**
	 * 返回上周的开始时间与结束时间
	 *
	 * @return
	 * @create 2020年6月30日 上午10:09:58 luochao
	 * @history
	 */
	public static String[] getWeekLast() {
		String today = dateToString(new Date(), DAY_FORMAT);
		int i = getDayOfWeek();
		if (i == 0) {
			i = Numbers.INT_7;
		}
		String beforeDay = getNextDayString(today, -Numbers.INT_6 - i, DAY_FORMAT);
		String afterDay = getNextDayString(today, -i, DAY_FORMAT);
		return new String[] { beforeDay + " 00:00:00", afterDay + " 23:59:59" };
	}

	/**
	 * 两个时间相差距离多少天数 str1>str2返回正数 str1<str2返回负数
	 * 
	 * @param str1 时间参数 1 格式：1990-01-01
	 * @param str2 时间参数 2 格式：2009-01-01
	 * @return long 返回值为：天
	 */
	public static long getDistanceDays(String str1, String str2) {
		DateFormat df = new SimpleDateFormat(DAY_FORMAT);
		long min = 0;
		try {
			Date d1 = df.parse(str1);
			Date d2 = df.parse(str2);
			long diff = d1.getTime() - d2.getTime();
			min = diff / (Numbers.DAY);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return min;
	}

	/**
	 * 两个时间相差距离多少分钟
	 *
	 * @param str1 时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2 时间参数 2 格式：2009-01-01 12:00:00
	 * @return long 返回值为：分钟数
	 */
	public static long getDistanceMinutes(String str1, String str2) {
		DateFormat df = new SimpleDateFormat(TIME_FORMAT);
		long min = 0;
		try {
			Date d1 = df.parse(str1);
			Date d2 = df.parse(str2);
			long diff = d1.getTime() - d2.getTime();
			min = diff / (Numbers.MINUTE);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return min;
	}

	/**
	 * 两个时间相差距离多少分钟
	 *
	 * @param str1 时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2 时间参数 2 格式：2009-01-01 12:00:00
	 * @return long 返回值为：分钟数
	 */
	public static long getDistanceMinutes(Date d1, Date d2) {
		long min = 0;
		try {
			long diff = d1.getTime() - d2.getTime();
			min = diff / (Numbers.MINUTE);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return min;
	}

	/**
	 * 两个时间相差距离多少秒
	 *
	 * @param str1 时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2 时间参数 2 格式：2009-01-01 12:00:00
	 * @return long 返回值为：秒数
	 */
	public static long getDistanceSeconds(String str1, String str2) {
		DateFormat df = new SimpleDateFormat(TIME_FORMAT);
		long s = 0;
		try {
			Date d1 = df.parse(str1);
			Date d2 = df.parse(str2);
			long diff = d1.getTime() - d2.getTime();
			s = diff / Numbers.INT_1000;
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return s;
	}

	/**
	 * 两个时间相差距离多少毫秒
	 *
	 * @param str1 时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2 时间参数 2 格式：2009-01-01 12:00:00
	 * @return long 返回值为：毫秒
	 */
	public static long getDistanceMilliseconds(String str1, String str2) {
		DateFormat df = new SimpleDateFormat(TIME_FORMAT);
		long distance = 0;
		try {
			Date d1 = df.parse(str1);
			Date d2 = df.parse(str2);
			distance = d1.getTime() - d2.getTime();
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return distance;
	}

	/**
	 * compare_date:判断2个时间大小 1>2 return 1 1<2 reutrn -1 1==2 return 0
	 * 
	 * @author luochao
	 * @date 2017年5月22日 下午2:13:17
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(String date1, String date2) {
		Assert.notNull(date1, "date1不能为空");
		Assert.notNull(date2, "date2不能为空");
		DateFormat df = new SimpleDateFormat(TIME_FORMAT);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			LOGGER.error("", exception);
		}
		return 0;
	}

	/**
	 * 请使用compareDate
	 *
	 * @param date1
	 * @param date2
	 * @return
	 * @create 2020年6月29日 下午5:02:27 luochao
	 * @history
	 */
	@Deprecated
	public static int compare(String date1, String date2) {
		return compareDate(date1, date2);
	}

	/**
	 * compare_date:判断2个时间大小 1>2 return 1 1<2 reutrn -1 1==2 return 0
	 * 
	 * @author luochao
	 * @date 2017年5月22日 下午2:13:17
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(Date date1, Date date2) {
		if (date1 == null) {
			return -1;
		}
		if (date2 == null) {
			return 1;
		}
		return date1.compareTo(date2);
	}

	/**
	 * 请使用compareDate
	 *
	 * @param date1
	 * @param date2
	 * @return
	 * @create 2020年6月29日 下午5:02:44 luochao
	 * @history
	 */
	@Deprecated
	public static int compare(Date date1, Date date2) {
		return compareDate(date1, date2);
	}

	/**
	 * 给当前时间加days天数，支持负数，负数等于减
	 *
	 * @param days
	 * @return
	 * @create 2020年6月29日 下午5:25:30 luochao
	 * @history
	 */
	public static Date addDays(Long days) {
		return addDays(new Date(), days);
	}

	/**
	 * 给传入时间加days天数，支持负数，负数等于减
	 *
	 * @param date
	 * @param days
	 * @return
	 * @create 2020年6月29日 下午5:28:02 luochao
	 * @history
	 */
	public static Date addDays(Date date, Long days) {
		try {
			date = new Date(date.getTime() + (days * Numbers.DAY));
		} catch (Exception exception) {
			LOGGER.error("", exception);
		}
		return date;
	}

	/**
	 * 给传入时间加minutes分钟，支持负数，负数等于减
	 *
	 * @param date
	 * @param minute
	 * @return
	 * @create 2020年6月29日 下午5:30:26 luochao
	 * @history
	 */
	public static Date addMinutes(Date date, Long minutes) {
		try {
			date = new Date(date.getTime() + (minutes * Numbers.MINUTE));
		} catch (Exception exception) {
			LOGGER.error("", exception);
		}
		return date;
	}

	/**
	 * 取得当前日期是多少周
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date, int delay) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(Numbers.INT_7);
		c.setTime(date);
		if (delay != 0) {
			c.add(Calendar.WEEK_OF_YEAR, delay);
		}
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取某时间年
	 *
	 * @param date 时间
	 * @return
	 * @history
	 */
	public static int getYear(Date date) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		return instance.get(Calendar.YEAR);
	}

	/**
	 * 获取某时间+多少周以后的年
	 *
	 * @param date  时间
	 * @param delay 周数
	 * @return
	 * @history
	 */
	public static int getYear(Date date, int delay) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		instance.add(Calendar.WEEK_OF_YEAR, delay);
		return instance.get(Calendar.YEAR);
	}

	/**
	 * 获取某时间的年，第几周
	 *
	 * @param date
	 * @return
	 * @history
	 */
	public static String getWeekAndYear(Date date, int delay) {
		int week = getWeekOfYear(date, delay);
		int year1 = getYear(date);
		int year2 = getYear(date, delay);
		if (year1 != year2) {
			year1 = year2;
		}
		return Integer.toString(year1) + "-" + String.format("%02d", week);
	}

	/**
	 * 计算指定年度共有多少个周。
	 * 
	 * @param year 格式 yyyy ，必须大于1900年度 小于9999年
	 * @return
	 */
	public static int getWeekNumByYear(int year) {
		if (year < 1900 || year > 9999) {
			throw new NullPointerException("年度必须大于等于1900年小于等于9999年");
		}
		// 每年至少有52个周 ，最多有53个周。
		int result = 52;
		String date = getYearWeekFirstDay(year, 53);
		// 判断年度是否相符，如果相符说明有53个周。
		if (date.substring(0, 4).equals(year + "")) {
			result = 53;
		}
		return result;
	}

	/**
	 * 计算某年某周的开始日期（每年度的第一个周，是包含第一个星期一的那个周）
	 * 
	 * @param yearNum 格式 yyyy ，必须大于1900年度 小于9999年
	 * @param weekNum 1到52或者53
	 * @return 日期，格式为yyyy-MM-dd
	 */
	public static String getYearWeekFirstDay(int yearNum, int weekNum) {
		Calendar cal = Calendar.getInstance();
		// 设置每周的第一天为星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 每周从周一开始
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// 上面两句代码配合，才能实现，每年度的第一个周，是包含第一个星期一的那个周。

		// 设置每周最少为7天
		cal.setMinimalDaysInFirstWeek(7);
		cal.set(Calendar.YEAR, yearNum);
		cal.set(Calendar.WEEK_OF_YEAR, weekNum);

		// 分别取得当前日期的年、月、日
		return DateUtil.dateToString(cal.getTime(), DateUtil.DAY_FORMAT);
	}

	/**
	 * 得到两个日期相差距离多少自然周
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int weeksBetweenDays(String start, String end) {
		Assert.notNull(start, "start不能为空");
		Assert.notNull(end, "end不能为空");
		int counts = 1;
		Calendar c = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		// 设置每周的第一天为星期一
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c1.setFirstDayOfWeek(Calendar.MONDAY);

		if (compareDate(start + " " + FIRST_TIME, end + " " + FIRST_TIME) == 1) {
			String temp = start;
			start = end;
			end = temp;
		}

		Date startDate = stringToDate(getFirstDayOfWeek(stringToDate(start)));
		Date endDate = stringToDate(getLastDayOfWeek(stringToDate(end)));
		c.setTime(startDate);
		c1.setTime(endDate);
		int weekofYear = c.get(Calendar.WEEK_OF_YEAR);
		int weekofYear1 = c1.get(Calendar.WEEK_OF_YEAR);
		if (c.get(Calendar.YEAR) == c1.get(Calendar.YEAR)) {
			counts = weekofYear1 - weekofYear + 1;
		} else {
			int difyear = c1.get(Calendar.YEAR) - c.get(Calendar.YEAR);
			// 年份相差一年
			if (difyear == 1) {
				int startyearWeeks = getWeekNumByYear(c.get(Calendar.YEAR));
				String date = getYearWeekFirstDay(c.get(Calendar.YEAR), startyearWeeks);
				int ds = compareDate(stringToDate(date), stringToDate(start));
				// 这一年最后一周的开始一天小于用户选择的开始日期，比如，2009年最后一周的开始日期是2009-12-27号，所以开始日期大于或等于2009-12-27号的特殊处理。
				if (ds <= 0) {
					counts = weekofYear1 - weekofYear + 1;
				} else {
					counts = getWeekNumByYear(c.get(Calendar.YEAR)) - weekofYear + weekofYear1 + 1;
				}
			} else if (difyear > 1) {
				/* 年份相差大于一年时，要考虑到 */
				for (int i = 0; i <= difyear; i++) {
					// 开始日期的年份+i，如2007-12-22到2011-01-09，这里是遍历出07年，08年，09年分别有多少个周
					int startAllWeeks = getWeekNumByYear(c.get(Calendar.YEAR) + i);
					if (i == 0) {
						/* 开始日期这一年的周差，如果07-12-22是第51周，07年共有52个周，那么，这里得到1. */
						counts = startAllWeeks - weekofYear;
					} else if (i < difyear) {
						/* 除开始日期与结束日期所在一年的年份的周差。也就是08年共有多少周 */
						counts += startAllWeeks;
					} else if (i == difyear) {
						/* 加上09年所在的周 */
						counts += weekofYear1;
					}
				}
				counts += 1;
			}
		}
		return counts;
	}

	/**
	 * 得到某年某周的第一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static String getFirstDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);
		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * Numbers.INT_7);
		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	 * 取得指定日期所在周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return new SimpleDateFormat(DAY_FORMAT).format(c.getTime());

	}

	/**
	 * 得到某年某周的最后一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static String getLastDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);
		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * Numbers.INT_7);
		return getLastDayOfWeek(cal.getTime());
	}

	/**
	 * 取得指定日期所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + Numbers.INT_6); // Sunday
		return new SimpleDateFormat(DAY_FORMAT).format(c.getTime());
	}

	/**
	 * 返回日期更大的那个
	 *
	 * @param date1
	 * @param date2
	 * @return
	 * @create 2020年6月29日 下午2:07:48 luochao
	 * @history
	 */
	public static Date getBiggerDate(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return date1 == null ? date2 : date1;
		}
		return (date1.after(date2) ? date1 : date2);
	}

	/**
	 * 判断str是否为年月格式，是返回true，否则返回false
	 *
	 * @param str
	 * @return
	 * @create 2020年6月29日 下午2:08:28 luochao
	 * @history
	 */
	public static boolean isValidYYYYMM(String str) {
		DateFormat formatter = new SimpleDateFormat(MONTH_FORMAT);
		try {
			Date date = (Date) formatter.parse(str);
			formatter.format(date);
			return str.equals(formatter.format(date));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断某一时间是否在一个区间内
	 * 
	 * @param sourceTime 时间区间,半闭合,如[10:00-20:00)
	 * @param curTime    需要判断的时间 如10:00
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static boolean isInTime(String sourceTime, Date curTime) {
		if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
			throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
		}

		String[] args = sourceTime.split("-");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		try {
			Date parse = sdf.parse(sdf.format(curTime));
			long now = parse.getTime();
			long start = sdf.parse(args[0]).getTime();
			long end = sdf.parse(args[1]).getTime();
			if ("00:00".equals(args[1])) {
				args[1] = "24:00";
			}
			if (end < start) {
				if (now >= end && now < start) {
					return false;
				} else {
					return true;
				}
			} else {
				if (now >= start && now < end) {
					return true;
				} else {
					return false;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
		}

	}

	/**
	 * 时间处理，转换为刚刚、几分钟前、几小时前、几天前、几周前
	 *
	 * @param startTime 需要处理的时间
	 * @return
	 * @create 2018年5月23日 上午10:24:54 luochao
	 * @history
	 */
	public static String showTimeBefore(String processTime) {
		String showTime = "";
		// 根据endTime（有效时间）与 startTime（发布时间）差，控制web端时间显示
		String effectiveStr = DateUtil.dateToString(new Date());
		Long timeDiff = DateUtil.getDistanceMilliseconds(effectiveStr, processTime);
		if (Numbers.MINUTE > timeDiff) {
			// 1分钟以内，显示“刚刚”
			showTime = "刚刚";
		} else if (Numbers.MINUTE <= timeDiff && timeDiff < Numbers.HOUR) {
			// 大于等于1分钟且小于1小时，显示“**分钟前”
			showTime = timeDiff / Numbers.MINUTE + "分钟前";
		} else if (Numbers.HOUR <= timeDiff && timeDiff < Numbers.DAY) {
			// 大于等于1小时且小于1天，显示“**小时前”
			showTime = timeDiff / Numbers.HOUR + "小时前";
		} else if (Numbers.DAY <= timeDiff && timeDiff < Numbers.WEEK) {
			// 大于等于1天且小于1周，显示“**天前”
			showTime = timeDiff / Numbers.DAY + "天前";
		} else if (Numbers.WEEK <= timeDiff && timeDiff <= Numbers.FOUR_WEEK) {
			// 大于等于1周且小于等于4周，显示“**周前”
			showTime = timeDiff / Numbers.WEEK + "周前";
		} else if (Numbers.FOUR_WEEK < timeDiff) {
			// 大于4周，显示发布日期
			showTime = processTime.substring(Numbers.INT_0, Numbers.INT_10);
		} else {
			showTime = processTime;
		}
		return showTime;
	}

	/**
	 * 时间处理，转换为将于一天内过期、将于两天内过期、将于三天内过期
	 *
	 * @param endTime   当前时间
	 * @param startTime 需要处理的时间
	 * @return
	 * @create 2018年8月24日 下午2:14:32 luochao
	 * @history
	 */
	public static String showTimeAfter(String startTime) {
		String showTime = "";
		String effectiveStr = DateUtil.dateToString(new Date());
		Long timeDiff = DateUtil.getDistanceMilliseconds(startTime, effectiveStr);
		if (timeDiff < Numbers.DAY) {
			showTime = "将于一天内过期";
		} else if (Numbers.DAY < timeDiff && timeDiff < Numbers.DAY * 2) {
			showTime = "将于两天内过期";
		} else if (Numbers.DAY * 2 < timeDiff && timeDiff < Numbers.DAY * 3) {
			showTime = "将于三天内过期";
		} else {
			showTime = "有效期：" + startTime;
		}
		return showTime;
	}

	/**
	 * 判断str是否为年月日格式，是返回true，否则返回false
	 *
	 * @param str
	 * @return
	 * @create 2016年11月7日 上午10:42:04 zhuwei
	 * @history
	 */
	public static boolean isValidYYYYMMDD(String str) {
		DateFormat formatter = new SimpleDateFormat(DAY_FORMAT);
		try {
			Date date = formatter.parse(str);
			formatter.format(date);
			return str.equals(formatter.format(date));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 根据日期查询星期几
	 * 
	 * @param format
	 * @param sys    默认：星期
	 * @return
	 */
	public static String dayForWeek(String format, String sys) {
		if (StringUtils.isBlank(sys)) {
			sys = XINGQI;
		}
		String[] weeks = { "一", "二", "三", "四", "五", "六", "日" };
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		int dayForWeek = 0;
		try {
			c.setTime(df.parse(format));
			if (c.get(Calendar.DAY_OF_WEEK) == 1) {
				dayForWeek = 7;
			} else {
				dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
			}
		} catch (ParseException e) {
			LOGGER.error("查询星期异常", e);
			return null;
		}
		return sys + weeks[dayForWeek - 1];
	}

	/**
	 * 按传入时间进行格式转换
	 *
	 * @param date      传入的时间
	 * @param oldFormat 老的格式
	 * @param newFormat 新格式
	 * @return
	 * @create 2020年9月3日 上午9:56:01 luochao
	 * @history
	 */
	public static String changeFormat(String date, String oldFormat, String newFormat) {
		if (StringUtils.isEmpty(date)) {
			return "";
		}
		SimpleDateFormat oldSdf = new SimpleDateFormat(oldFormat);
		SimpleDateFormat newSdf = new SimpleDateFormat(newFormat);
		try {
			return newSdf.format(oldSdf.parse(date));
		} catch (ParseException e) {
			LOGGER.error("转换时间异常", e);
		}
		return oldFormat;
	}

	/**
	 * 获取最大的时间
	 *
	 * @param dates
	 * @return
	 */
	public static Date getMaxDate(Date... dates) {
		Date maxDate = null;
		for (Date date : dates) {
			if (maxDate == null) {
				maxDate = date;
			} else if (date != null && date.after(maxDate)) {
				maxDate = date;
			}
		}
		return maxDate;
	}

	/** 时间格式化1 */
	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/** 时间格式化2 */
	public static final String TIME_FORMAT_TWO = "yyMMddHHmmss";
	/** 时间格式化3 */
	public static final String TIME_FORMAT_THREE = "yyyyMMddHHmmss";
	/** 时间格式化4 */
	public static final String MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
	/** 时间格式化5 */
	public static final String DAY_FORMAT = "yyyy-MM-dd";
	public static final String DAY_FORMAT2 = "yyyyMMdd";
	public static final String DAY_HOUR_MINUTE_SECOND_FORMAT = "MM-dd HH:mm:ss";
	/** 时间格式化6 */
	public static final String MONTH_FORMAT = "yyyy-MM";
	public static final String MONTH_FORMAT2 = "yyyyMM";
	public static final String YEAR_FORMAT = "yyyy";

	/** 时间格式化7 */
	public static final String HOUR_MINUTE_SECOND_FORMAT = "HH:mm:ss";
	/** 23:59:59 */
	public static final String LAST_TIME = "23:59:59";
	/** 00:00:00 */
	public static final String FIRST_TIME = "00:00:00";
	/** - */
	public static final String DATA_SEPARATE = "-";

	public static final String ZHOU = "周";
	public static final String XINGQI = "星期";

	public static void main(String[] args) {
		System.out.println("*************************转换区******************************");
		System.out.println("转String,到秒:" + dateToString(new Date()));// yyyy-MM-dd
																		// HH:mm:ss
		System.out.println("转String,到天,指定格式:" + dateToString(new Date(), DAY_FORMAT));// yyyy-MM-dd
		System.out.println("转date,到秒:" + stringToDate("2020-06-30 09:24:42"));
		System.out.println("转date,到秒,指定格式:" + stringToDate("2020-06-30 09:24:42", TIME_FORMAT));
		System.out.println("转date,到天:" + stringToDate("2020-06-30"));
		System.out.println("转date,到天,指定格式:" + stringToDate("2020-06-30", DAY_FORMAT));
		System.out.println("转Long,毫秒级:" + dateToLong(new Date()));
		System.out.println("转date,毫秒级,指定格式:" + longToDate(1593481952585L, TIME_FORMAT));
		System.out.println("当前时间,到天:" + getDayNow());// yyyy-MM-dd
		System.out.println("当前时间,到秒:" + getTimeNow());// yyyy-MM-dd HH:mm:ss
		System.out.println("当前时间,到秒:" + getTimeNow2());// yyyyMMddHHmmss
		System.out.println("当前时间,指定格式:" + getTimeNowFormat(TIME_FORMAT));// yyyy-MM-dd
																			// HH:mm:ss
		System.out.println("*************************转换区******************************");

		System.out.println("*************************获取指定时间区******************************");
		System.out.println("获取当月天数:" + getMonthMaxDay(new Date()));
		System.out.println("当天开始时间,到秒:" + getDayStart(new Date()));// yyyy-MM-dd
																	// HH:mm:ss
		System.out.println("当天结束时间,到秒:" + getDayEnd(new Date()));// yyyy-MM-dd
																	// HH:mm:ss
		System.out.println("当前月开始时间,到天:" + getDayFirstOfCurMonth());// yyyy-MM-dd
		System.out.println("当前月结束时间，到天:" + getDayLastOfCurMonth());// yyyy-MM-dd
		System.out.println("指定时间月开始时间,到秒:" + getMonthStartTime(new Date()));// yyyy-MM-dd
																			// HH:mm:ss
		System.out.println("指定时间月结束时间,到秒:" + getMonthEndTime(new Date()));// yyyy-MM-dd
																			// HH:mm:ss
		System.out.println("传入时间周几:" + getWeek(new Date()));
		System.out.println("当前时间周几:" + getDayOfWeek());
		System.out.println("指定时间内传入星期的时间:" + getDayOfWeek(new Date(), WeekEnum.WEDNESDAY));
		System.out.println("上周的开始时间与结束时间:" + JSON.toJSONString(getWeekLast()));// yyyy-MM-dd
																				// HH:mm:ss
		System.out.println("下周的开始时间与结束时间:" + JSON.toJSONString(getWeekNext()));// yyyy-MM-dd
																				// HH:mm:ss
		System.out.println("传入时间加减多少周的年份,到年:" + getWeekOfYear(new Date(), 0));
		System.out.println("传入时间的年份,到年:" + getYear(new Date()));
		System.out.println("传入时间加减多少周的年份,到年:" + getYear(new Date(), 56));
		System.out.println("传入时间加减多少周的年份与周数:" + getWeekAndYear(new Date(), 1));
		System.out.println("获取传入年份周数的第一天:" + getFirstDayOfWeek(2020, 26));
		System.out.println("取得传入日期所在周最后一天:" + getLastDayOfWeek(2020, 26));
		System.out.println("取得传入日期所在周第一天:" + getFirstDayOfWeek(new Date()));
		System.out.println("取得传入日期所在周最后一天:" + getLastDayOfWeek(new Date()));
		System.out.println("获取指定年月的第一天:" + getFirstDayOfMonth(2020, 7));
		System.out.println("获取指定年月的第一天:" + getFirstDayOfMonth(new Date()));
		System.out.println("获取指定年月的最后一天:" + getLastDayOfMonth(2020, 7));
		System.out.println("获取指定时间的最后一天:" + getLastDayOfMonth(new Date()));
		System.out.println("*************************获取指定时间区******************************");

		System.out.println("*************************加减操作区******************************");
		System.out.println("当前时间+10天:" + addDays(10L));
		System.out.println("当前时间-10天:" + addDays(-10L));
		System.out.println("当前时间-10分钟:" + addMinutes(new Date(), -10L));
		System.out.println("当前时间+10分钟:" + addMinutes(new Date(), 10L));
		System.out.println("获取传入时间加减天数的时间,指定格式:" + getNextDayString(getTimeNow(), -1, TIME_FORMAT));
		System.out.println("获取传入时间加减天数的时间,指定格式:" + getNextDayString(new Date(), -1, DAY_FORMAT));
		System.out.println("获取传入时间加减天数的时间:" + getNextDayDate(new Date(), -1));
		System.out.println("获取传入时间加减月数的时间:" + getNextMonth(getTimeNow(), -1));
		System.out.println("获取传入时间加减月数的时间:" + getNextMonth(new Date(), -1));
		System.out.println("*************************加减操作区******************************");

		System.out.println("*************************比较区******************************");
		System.out.println("两者时间差,到天:" + getDistanceDays(getTimeNow(), getNextDayString(getTimeNow(), 1, TIME_FORMAT)));
		System.out.println(
				"两者时间差,到分:" + getDistanceMinutes(getTimeNow(), getNextDayString(getTimeNow(), 1, TIME_FORMAT)));
		System.out.println(
				"两者时间差,到秒:" + getDistanceSeconds(getTimeNow(), getNextDayString(getTimeNow(), 1, TIME_FORMAT)));
		System.out.println(
				"两者时间差,到毫秒:" + getDistanceMilliseconds(getTimeNow(), getNextDayString(getTimeNow(), 1, TIME_FORMAT)));
		System.out.println("两者时间对比:" + compareDate(getTimeNow(), getTimeNow()));
		System.out.println("两者时间对比:" + compareDate(new Date(), new Date()));
		System.out.println("返回更大的时间:" + getBiggerDate(new Date(), new Date()));
		System.out.println("*************************比较区******************************");
	}

}
