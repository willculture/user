package org.wc.plugins;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpWatch {

  HttpClient client;

  Map<String, String> data;

  private HttpWatch() {
    client = HttpClients.createDefault();
    data = new HashMap<>();
  }

  public static HttpWatch getInstance() {
    return new HttpWatch();
  }

  public void addReferer(String value) {
    data.put("Referer", value);
  }

  public String get(String url) {
    return get(url, "utf-8");
  }

  /**
   * get请求
   * 
   * @param url
   * @return
   */
  public String get(String url, String charset) {

    HttpGet get = new HttpGet(url);
    Set<String> keys = data.keySet();
    for (String key : keys) {
      get.addHeader(key, data.get(key));
    }

    try {
      HttpResponse response = client.execute(get);
      String content = EntityUtils.toString(response.getEntity(), charset);
      return content;
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return "";

  }

}
