package org.wc.config;

/**
 * JSON返回数据说明 <br />
 * state 这个值是属于全局设置，两个值:ok和fail <br />
 * data 成功后，返回的数据，放在data里 <br />
 * msg 这个是传递处理该操作后，返回的相关消息数据 <br />
 * 
 * @author Administrator
 * 
 */
public class State {

	public static final String OK = "ok";
	public static final String FAIL = "fail";
	public static final String DATA = "data";
	public static final String MSG = "msg";
	public static final String STATE = "state";

	// 分页
	public static final String PAGE_PAGE = "page_page";
	public static final String PAGE_TOTALE = "page_totale";

	// 编码
	public static final String CODE = "code";// 代码
	public static final String S0001_ERROR = "00001"; // 默认错误
	public static final String S0002_LOGIN = "00002"; // 需要登录操作
	public static final String S0003_PERMISSION = "00003"; // 权限不足
	public static final String S10000_RIGHT = "10000";// 默认正确
  public static final String S10001_DELETE = "10001"; //删除

}
