package org.wc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具
 * 
 * @author tonway
 * @date 2013-2013-4-23-下午2:20:38
 */
public class TimeUtils {

	public static String format(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String lastdate = sdf.format(date);
		return lastdate;

	}
	/**
	 * 字符串转时间
	 * @param time
	 * @param format
	 * @return
	 */
	public static Date format(String time,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String[] split(Date date) {
		String time = format(date, "yyyy-MM-dd HH:mm:ss");
		return split(time);
	}

	/**
	 * 分割时间格式为2013-04-12 22:22:22 ==》 [2013,04,12,22,22,22]
	 * 
	 * @author tonway
	 * @date 2013-2013-4-23-下午2:30:54
	 * @param time
	 * @return
	 */
	public static String[] split(String time) {
		String[] datetime = time.split(" ");
		String[] dates = datetime[0].split("-");
		String[] times = datetime[1].split(":");
		return arrjoin(dates, times);
	}

	/**
	 * 数组拼接
	 * 
	 * @author tonway
	 * @date 2013-2013-4-23-下午2:30:44
	 * @param strs
	 * @return
	 */
	private static String[] arrjoin(String[]... strs) {
		int size = 0;
		for (String[] str : strs) {
			size += str.length;
		}
		String[] joins = new String[size];
		int count = 0;
		for (int i = 0; i < strs.length; i++) {
			for (int j = 0; j < strs[i].length; j++) {
				joins[count] = strs[i][j];
				count++;
			}
		}

		return joins;

	}

}
