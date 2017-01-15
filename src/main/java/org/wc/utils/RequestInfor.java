package org.wc.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * request相关信息
 * 
 * @author Administrator
 *
 */
public class RequestInfor {

	/**
	 * 请求地址
	 */
	private String url;

	/**
	 * 请求HOST
	 */
	private String host;

	/**
	 * 请求端口
	 */
	private int port;

	/**
	 * 请求方法
	 */
	private String method;

	/**
	 * 请求参数
	 */
	private String queryString;

	/**
	 * 用户IP
	 */
	private String uip;
	/**
	 * 用户浏览器
	 */
	private String ubrowser;

	/**
	 * 用户浏览器版本
	 */
	private String ubrowserVersion;

	/**
	 * 用户访问时间
	 */
	private Date uvisitedTime;

	/**
	 * 用户系统
	 */
	private String uos;
	/**
	 * 用户设备
	 */
	private String udevice;

	/**
	 * 用户来路
	 */
	private String referer;
	/**
	 * 用户浏览器引擎
	 */
	private String ubeng;

	public RequestInfor(HttpServletRequest req) {
		UserAgent agent = UserAgent.parseUserAgentString(req.getHeader("User-Agent"));
		OperatingSystem os = agent.getOperatingSystem();
		Browser browser = agent.getBrowser();

		// 获取request
		host = req.getLocalAddr();
		port = req.getLocalPort();
		method = req.getMethod();
		url = req.getRequestURL().toString();
		queryString = req.getQueryString();
		uip = req.getRemoteHost();
		uvisitedTime = new Date();
		referer = req.getHeader("Referer");
		// 获取UserAgent

		if (agent != null) {
			ubrowser = browser.getName();
			if (agent.getBrowserVersion() != null) {
				ubrowserVersion = agent.getBrowserVersion().getVersion();
			}
			uos = os.getName();
			if (os.getDeviceType() != null) {
				udevice = os.getDeviceType().getName();
			}
			if (browser.getRenderingEngine() != null) {
				ubeng = browser.getRenderingEngine().name();
			}
		}

	}

	public String getUrl() {
		return url;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getMethod() {
		return method;
	}

	public String getQueryString() {
		return queryString;
	}

	public String getUip() {
		return uip;
	}

	public String getUbrowser() {
		return ubrowser;
	}

	public String getUbrowserVersion() {
		return ubrowserVersion;
	}

	public Date getUvisitedTime() {
		return uvisitedTime;
	}

	public String getUos() {
		return uos;
	}

	public String getUdevice() {
		return udevice;
	}

	public String getReferer() {
		return referer;
	}

	public String getUbeng() {
		return ubeng;
	}

}
