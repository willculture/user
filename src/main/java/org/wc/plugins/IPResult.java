package org.wc.plugins;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;

import net.sf.json.JSONObject;

public class IPResult {

  private IPResult() {

  }

  public static IPResult getInstance() {
    return new IPResult();
  }

  /**
   * 获取当前网络ip
   * 
   * @param request
   * @return
   */
  public String getIpAddr(HttpServletRequest request) {
    String ipAddress = request.getHeader("x-forwarded-for");
    if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getHeader("Proxy-Client-IP");
    }
    if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getRemoteAddr();
      if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
        // 根据网卡取本机配置的IP
        InetAddress inet = null;
        try {
          inet = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
          e.printStackTrace();
        }
        ipAddress = inet.getHostAddress();
      }
    }
    // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
    if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length() = 15
      if (ipAddress.indexOf(",") > 0) {
        ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
      }
    }
    return ipAddress;
  }

  public String getCity(String ip) {
    HttpWatch wath = HttpWatch.getInstance();
    String content =
        wath.get("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=" + ip);
    content = StringEscapeUtils.unescapeEcmaScript(content);
    JSonResult result = new JSonResult();
    if (content.matches("^\\{.+\\}$")) {
      result.putData(JSONObject.fromObject(content));
      return result.toOk().toSource();
    }
    return result.toFail("").toSource();
  }

  public static void main(String[] args) {
    String city = IPResult.getInstance().getCity("127.0.0.1");
    System.out.println(city);
  }

}
