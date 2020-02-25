package adstech.vn.com.payroll.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTimeUtil {
	private static final String TIME_FORMAT = "hh:mm:ss";
	
	public static Date stringToTime(String time) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(TIME_FORMAT);
		return df.parse(time);
	}
	
	public static Date getFirstDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return c.getTime();
	}
	
	public static Date getLastDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return c.getTime();
	}
	
	public static int countWorkingDay(List<Integer> workdays) {
		Date currentDate = new Date();
		Date firsDate = getFirstDateOfMonth(currentDate);
		Calendar c = Calendar.getInstance();
		c.setTime(firsDate);
		int result = 0;
		while (c.getTime().before(currentDate)) {
			if(workdays.contains(c.get(Calendar.DAY_OF_WEEK))) {
				result++;
			}
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return result;
	}
	
	public static int countWorkingDayOfMonth(List<Integer> workdays) {
		Date currentDate = new Date();
		Date firsDate = getFirstDateOfMonth(currentDate);
		Date lastDate = getLastDateOfMonth(currentDate);
		Calendar c = Calendar.getInstance();
		c.setTime(firsDate);
		int result = 0;
		while (c.getTime().before(lastDate)) {
			if(workdays.contains(c.get(Calendar.DAY_OF_WEEK))) {
				result++;
			}
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return result;
	}
}
