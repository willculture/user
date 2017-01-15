package org.wc.utils;

import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 用来处理request的请求参数
 * 
 * @author Administrator
 *
 */
public class SpringRequest {

	public SpringRequest(HttpServletRequest req) {

		 
		try {
			HttpServletRequestWrapper request = new HttpServletRequestWrapper(req);
 
			map = new HashMap<String, Object>();
			if (request.getMethod().toLowerCase().equals("post")) {
				BufferedReader reader = request.getReader();
				String paramers = reader.readLine();
				if (paramers != null) {
					String[] paramarr = paramers.split("&");
					for (String param : paramarr) {
						String[] pam = param.split("=");
						map.put(pam[0], pam[1]);
					}
				}

			} else {
				Enumeration<String> keys = request.getParameterNames();
				while (keys.hasMoreElements()) {
					String key = keys.nextElement();
					map.put(key, request.getParameterValues(key));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	Map<String, Object> map;

	public <T> T get(String key) {
		return (T) map.get(key);
	}

}
