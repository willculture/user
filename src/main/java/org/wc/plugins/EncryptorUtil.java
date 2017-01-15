/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wc.plugins;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;
import org.wc.config.State;

/**
 * 数据加密和解密处理工具
 * 
 * @author Administrator
 */
public class EncryptorUtil {

	public static String encode(String src) {
		char[] bys = src.toCharArray();

		char[] nbys = new char[bys.length];
		for (int i = 0; i < bys.length; i++) {
			int key = (int) bys[i];
			nbys[i] = (char) (key - 19860913);
		}
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = "";
		try {
			encryptedText = encoder.encodeToString(new String(nbys).getBytes("utf-8"));
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(EncryptorUtil.class.getName()).log(Level.SEVERE, null, ex);
		}
		return encryptedText;
	}

	public static String decode(String src) {
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(src);
		src = new String(encryptedTextByte);
		char[] bys = src.toCharArray();
		char[] nbys = new char[bys.length];
		for (int i = 0; i < bys.length; i++) {
			int key = (int) bys[i];
			nbys[i] = (char) (key + 19860913);
		}
		return new String(nbys);
	}

	// public static void main(String[] args) throws
	// UnsupportedEncodingException {
	//
	// Map<String, Object> map = new HashMap<>();
	// map.put("state", State.FAIL);
	// map.put("msg", "账号或密码错误");
	//
	// String abc = EncryptorUtil.encode(JSONObject.fromObject(map).toString());
	// System.out.println(abc);
	// String result = EncryptorUtil.decode(abc);
	// System.out.println(result);
	// }
}
