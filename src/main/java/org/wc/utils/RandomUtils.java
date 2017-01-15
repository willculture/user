package org.wc.utils;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

public class RandomUtils {

  /**
   * 获取随机的8个字符
   * 
   * @return
   */
  public static String random() {
    String uuid = UUID.randomUUID().toString();
    String md5 = DigestUtils.md5Hex(uuid);
    int len = md5.length();
    int start = new Random().nextInt(len);
    if ((len - start) < 8) {
      start = len - 8;
    }

    md5 = md5.substring(start, start + 8);

    return md5;
  }

}
