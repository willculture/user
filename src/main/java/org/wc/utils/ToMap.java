package org.wc.utils;

import java.util.HashMap;
import java.util.Map;

public class ToMap {

  Map<String, Object> map;

  public ToMap() {
    map = new HashMap<>();
  }

  public static ToMap $() {
    return new ToMap();
  }

  public ToMap put(String key, Object value) {
    map.put(key, value);
    return this;
  }

  public Map<String, Object> toMap() {
    return this.map;
  }
}
