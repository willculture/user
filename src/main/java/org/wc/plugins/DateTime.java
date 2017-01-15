package org.wc.plugins;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime {

  Calendar calc;
  int week[] = new int[] {7, 1, 2, 3, 4, 5, 6};

  public DateTime() {
    calc = Calendar.getInstance();
  }

  public DateTime(Date date) {
    calc = Calendar.getInstance();
    calc.setTime(date);
  }

  public int getWeek() {
    int index = calc.get(Calendar.DAY_OF_WEEK) - 1;
    return week[index];
  }

  public int getDay() {
    return calc.get(Calendar.DAY_OF_MONTH);
  }

  public int getMonth() {
    return calc.get(Calendar.MONTH) + 1;
  }

  public int getYear() {
    return calc.get(Calendar.YEAR);
  }

  public int getHour() {
    return calc.get(Calendar.HOUR_OF_DAY);
  }

  public int getMinu() {
    return calc.get(Calendar.MINUTE);
  }

  public int getSecond() {
    return calc.get(Calendar.SECOND);
  }
  
  public String getTime(String sp) {
    return getHour() + sp + getMinu() + sp + getSecond(); 
  }
  
  public long getTime() {
     int day = getDay();
     String daystr = day+"";
     if(day < 10) {
       daystr = "0"+day;
     }
     return Long.parseLong(getYear() + "" + getMonth() + "" + daystr + "");
  }

  public static String format(Date date, String pattern) {
    SimpleDateFormat format = new SimpleDateFormat(pattern);
    return format.format(date);
  }

  public static String format(long date, String pattern) {
    SimpleDateFormat format = new SimpleDateFormat(pattern);
    return format.format(date);
  }
  
  
  
  public static void main(String[] args) {
    DateTime time = new DateTime();
    System.out.println(time.getWeek());
    System.out.println(time.getDay());
    System.out.println(time.getMonth());
    System.out.println(time.getYear());

    System.out.println(time.getHour());
    System.out.println(time.getMinu());
    System.out.println(time.getSecond());
    System.out.println(time.getTime());
  }

}
