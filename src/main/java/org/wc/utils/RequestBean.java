package org.wc.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.wc.plugins.XSSFilter;

/**
 * 实体处理类
 *
 * @author jason
 *
 */
public class RequestBean {

  static boolean hasKey(String key, String keylist) {
    String[] lists = keylist.split(",");
    for (String item : lists) {
      if (key.equals(item)) {
        return true;
      }
    }

    return false;
  }

  /**
   */
  public static <T> T getBean(HttpServletRequest request, Class<T> currentclass, String... args) {
    return getBean(request, "", currentclass, args);
  }

  /**
   * 自动处理请求里的数据，赋值给实体类
   * 
   * @param request
   * @param currentclass
   * @param nofilter 不需要过滤的数据， 格式key1, key2, key3 以逗号隔开
   * @return
   */
  public static <T> T getBean(HttpServletRequest request, String nofilter, Class<T> currentclass,
      String... args) {
    T obj = null;
    try {
      Map<String, String[]> map = request.getParameterMap();
      Enumeration<String> keys = request.getParameterNames();

      obj = (T) currentclass.newInstance();
      Method[] methods = currentclass.getDeclaredMethods();

      while (keys.hasMoreElements()) {
        String key = keys.nextElement();
        String[] objs = map.get(key);
        // 过滤数据
        if (!hasKey(key, nofilter)) {
          for (int i = 0; i < objs.length; i++) {
            objs[i] = XSSFilter.filter(objs[i]);
          }
        }
        //
        for (int i = 0; i < methods.length; i++) {
          String methename = methods[i].getName().replace("_", "").toLowerCase();

          if (!filter(args, methename)) {
            if (("set" + key.toLowerCase()).equalsIgnoreCase(methename)
                && methename.indexOf("get") == -1) {
              String arg = objs[0];

              Type ptype = methods[i].getGenericParameterTypes()[0];

              if (ptype == int.class) {// int
                                       // 类型
                if (arg == "") {
                  arg = "0";
                }
                methods[i].invoke(obj, Integer.parseInt(arg));
              } else if (ptype == short.class) {
                if (arg == "") {
                  arg = "0";
                }
                methods[i].invoke(obj, Short.parseShort(arg));
              }

              else if ((ptype == Long.class) || (ptype == long.class)) {// long类型
                if (arg == "") {
                  arg = "0";
                }
                methods[i].invoke(obj, Long.parseLong(arg));
              } else if ((ptype == double.class) || (ptype == Double.class)) {// long类型
                if (arg == "") {
                  arg = "0";
                }
                methods[i].invoke(obj, Double.parseDouble(arg));
              } else if (ptype == Date.class) {// 日期
                if (arg == null) {
                  methods[i].invoke(obj, new Date());
                } else {
                  SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                  methods[i].invoke(obj, sf.parse(arg));
                }

              } else if (methods[i].getGenericParameterTypes()[0] == boolean.class) {// 布尔类型
                methods[i].invoke(obj, Boolean.parseBoolean(arg));
              } else { // 如果不是字符串类型, 则需要记性转换

                if (ptype != String.class && ptype != Date.class) {

                  if (ptype.getTypeName().contains("java.util.Set")) { // 字段为Set类型
                    System.out.println(ptype.getTypeName());
                    String[] types = ptype.getTypeName().split("[><]");
                    Object subobj = Class.forName(types[1]).newInstance();
                    if (arg.trim().length() > 0) {
                      System.out.println(arg);
                      String[] split = arg.split("\\|");
                      String acol = split[0];
                      String aval = split[1];
                      Method m = subobj.getClass().getMethod("set" + acol, Long.class);
                      m.invoke(subobj, Long.parseLong(aval));
                    }
                    Set set = new HashSet();
                    set.add(subobj);

                    methods[i].invoke(obj, set);

                  } else {
                    Object subobj = Class.forName(ptype.getTypeName()).newInstance();
                    if (arg.trim().length() > 0) {
                      String[] split = arg.split("\\|");
                      String acol = split[0];
                      String aval = split[1];
                      Method m = subobj.getClass().getMethod("set" + acol, Long.class);
                      m.invoke(subobj, Long.parseLong(aval));
                      methods[i].invoke(obj, subobj);
                    }
                  }

                  //

                  //

                } else {
                  methods[i].invoke(obj, arg);
                }
              }

            }
          }
        }

      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return obj;
  }

  public static boolean filter(String[] args, String methodname) {
    boolean b = false;

    for (int i = 0; i < args.length; i++) {
      String arg = args[i].toLowerCase();
      if (methodname.equals("set" + arg)) {
        b = true;
      }
    }
    return b;
  }

  /**
   * 复制数据
   *
   * @param source
   * @param target
   */
  public static void copy(Object source, Object target) {
    try {
      Class<?> classes = source.getClass();
      Method[] methods = classes.getDeclaredMethods();
      for (int i = 0; i < methods.length; i++) {
        Method method = methods[i];
        String methodname = method.getName();
        if (methodname.indexOf("get") >= 0 || methodname.startsWith("is")) {
          Object arg = method.invoke(source);
          if (arg != null) {
            String newname = methodname.substring(3);
            if (methodname.startsWith("is")) {
              newname = methodname.substring(2);
            }
            newname = StringUtils.capitalize(newname);

            Method mth = classes.getDeclaredMethod("set" + newname, method.getReturnType());
            mth.invoke(target, arg);
          }

        }

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 处理分页数字
   */

  public static int page(HttpServletRequest request, String param) {
    int page = 1;
    String newparam = request.getParameter(param);
    try {
      page = Integer.parseInt(newparam);
    } catch (Exception e) {
      page = 1;
    }
    return page;
  }

  public static Map<String, String> tranmap(Map<String, String[]> map) {
    Map<String, String> newmap = new HashMap<String, String>();
    for (String key : map.keySet()) {
      newmap.put(key, map.get(key)[0]);
    }
    return newmap;
  }

}
