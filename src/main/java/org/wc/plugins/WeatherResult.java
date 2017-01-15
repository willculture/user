package org.wc.plugins;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WeatherResult {
  HttpWatch watch;

  public WeatherResult() {
    watch = HttpWatch.getInstance();
    watch.addReferer("http://m.weather.com.cn");
  }

  public String getCode(String city) {
    String content = watch.get("http://toy1.weather.com.cn/search?cityname=" + city);
    content = content.substring(1, content.length() - 1);
    JSONArray arr = JSONArray.fromObject(content);
    if (arr.size() == 0) {
      return "";
    }
    System.out.println(arr.toString());
    JSONObject obj = arr.getJSONObject(0);
    String ref = obj.getString("ref");
    content = ref.split("~")[0];
    if (content.matches("^\\d+[A-Z]$")) {
      content = content.substring(0, content.length() - 3);
    }
    System.out.println(content);
    return content;
  }

  /**
   * 获取天气
   * 
   * @param city
   * @return
   */
  public String getWeather(String city) {
    String code = getCode(city);
    try {
      String content =
          watch.get("http://d1.weather.com.cn/sk_2d/" + code + ".html?_=" + new Date().getTime());
      if (content.contains("{")) {
        content = content.substring(content.indexOf("{"), content.length());
        return content;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  public static void main(String[] args) {
    WeatherResult result = new WeatherResult();
    String content = result.getWeather("长沙");
    System.out.println(content);
  }


}
