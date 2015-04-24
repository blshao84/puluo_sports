package com.puluo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class TimeUtils {
	public static String formatDate(Date dt) {
		if (dt == null)
			return "";
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		f.setTimeZone(TimeZone.getDefault());
		String format;
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -10);
		try {
			format = f.format(dt);

		} catch (Exception e) {
			@SuppressWarnings("static-access")
			java.util.Date defDay = c.getInstance().getTime();
			format = f.format(defDay);
		}
		return format;
	}

	public static String formatDate(DateTime dt) {
		if (dt == null)
			return "";
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		f.setTimeZone(TimeZone.getDefault());
		String format;
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -10);
		try {
			format = f.format(dt.toDate());

		} catch (Exception e) {
			@SuppressWarnings("static-access")
			java.util.Date defDay = c.getInstance().getTime();
			format = f.format(defDay);
		}
		return format;
	}

	/**
	 * format: yyyy-mm-dd
	 * @param dt
	 * @return
	 */
	public static DateTime parseDate(String dt) {
		String[] dates = dt.split("-");
		if (dates.length == 3) {
			String yearString = dates[0];
			String monthString = dates[1];
			String dayString = dates[2];
			int year = Integer.parseInt(yearString);
			int month = Integer.parseInt(monthString);
			int day = Integer.parseInt(dayString);
			int hour = 0;
			int min = 0;
			int sec = 0;
			return new DateTime(year, month, day, hour, min, sec);
		} else
			return null;
	}

	/**
	 * format: yyyy-mm-dd hh:mm:ss
	 * @param dt
	 * @return
	 */
	public static DateTime parseDateTime(String dt) {
		String[] dateTime = dt.split(" ");
		if (dateTime.length == 2) {
			String dateString = dateTime[0];
			String timeString = dateTime[1];
			String[] dates = dateString.split("-");
			String[] times = timeString.split(":");
			if (dates.length == 3 && times.length == 3) {
				String yearString = dates[0];
				String monthString = dates[1];
				String dayString = dates[2];
				String hourString = times[0];
				String minString = times[1];
				String secString = times[2];

				int year = Integer.parseInt(yearString);
				int month = Integer.parseInt(monthString);
				int day = Integer.parseInt(dayString);
				int hour = Integer.parseInt(hourString);
				int min = Integer.parseInt(minString);
				int sec = Integer.parseInt(secString);

				return new DateTime(year, month, day, hour, min, sec);
			} else
				return null;
		} else
			return null;
	}

	public static LocalDate parseLocalDate(String dt) {
		String[] dateTime = dt.split(" ");
		if (dateTime.length == 2) {
			String dateString = dateTime[0];
			String timeString = dateTime[1];
			String[] dates = dateString.split("-");
			String[] times = timeString.split(":");
			if (dates.length == 3 && times.length == 3) {
				String yearString = dates[0];
				String monthString = dates[1];
				String dayString = dates[2];

				int year = Integer.parseInt(yearString);
				int month = Integer.parseInt(monthString);
				int day = Integer.parseInt(dayString);

				return new LocalDate(year, month, day);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static String formatBirthday(DateTime dt) {
		if (dt == null)
			return "";
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String format;
		try {
			format = f.format(dt.toDate());
			return format;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static long dateTime2Millis(DateTime dt) {
		// return ((dt == null) ? DateTime.now().getMillis() : dt.getMillis());
		return ((dt == null) ? 0 : dt.getMillis());
	}

}
