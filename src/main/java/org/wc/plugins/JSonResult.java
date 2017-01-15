package org.wc.plugins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import org.apache.commons.lang3.StringEscapeUtils;
import org.wc.action.api.support.Remover;
import org.wc.config.State;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * JSonResult返回数据是经过加密处理的
 *
 * @author Administrator
 *
 */
public class JSonResult {

  Map<String, Object> data;
  Set<String> keys;

  public JSonResult() {
    data = new HashMap<String, Object>();
    keys = new HashSet<String>();
    data.put("state", "fail");
    data.put(State.CODE, State.S0001_ERROR);
  }

  public JSonResult toFail(String msg) {
    return toFail(msg, State.S0001_ERROR);
  }

  public JSonResult toFail(String msg, String code) {
    data.put("state", "fail");
    data.put("msg", msg);
    data.put(State.CODE, code);
    return this;
  }

  public JSonResult toOk(String msg) {
    return toOk(msg, State.S10000_RIGHT);
  }

  public JSonResult toOk(String msg, String code) {
    data.put("state", "ok");
    data.put("msg", msg);
    data.put(State.CODE, code);
    return this;
  }

  public JSonResult toOk() {
    return toOk("ok");
  }

  public JSonResult put(String key, Object value) {
    data.put(key, value);
    return this;
  }

  public JSonResult putData(Object value) {
    data.put("data", value);
    return this;
  }

  public Object get(String key) {
    return data.get(key);
  }

  public String toSource() {
    JsonConfig config = new JsonConfig();
    config.addIgnoreFieldAnnotation(Remover.class);
    return toResult(null, false);
  }

  public String toResult() {
    JsonConfig config = new JsonConfig();
    config.addIgnoreFieldAnnotation(Remover.class);
    return toResult(config, true);
  }

  public String toResult(JsonConfig config) {
    return toResult(config, true);
  }



  public String toResult(JsonConfig config, boolean cryp) {
//    for (String key : data.keySet()) {
//      System.out.println(key + "/" + data.get(key));
//    }

    JSONObject obj = null;
    String[] arrkey = new String[keys.size()];
    keys.toArray(arrkey);
    if (config != null) {
      config.setExcludes(arrkey);// 删除不必要得数据
      obj = JSONObject.fromObject(data, config);
    } else {
      config = new JsonConfig();
      config.setExcludes(arrkey);// 删除不必要得数据
      obj = JSONObject.fromObject(data, config);
    }



    String content = obj.toString();

    if (cryp) {
      content = StringEscapeUtils.escapeEcmaScript(content);
      String result = EncryptorUtil.encode(content);
      return result;
    }
    return content;
  }

  public JSonResult delete(String... key) {
    for (String item : key) {
      keys.add(item);
    }
    return this;
  }

}
