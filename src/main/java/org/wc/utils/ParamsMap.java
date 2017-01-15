package org.wc.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ParamsMap {

    Map<String, Object> map;

    public ParamsMap(){
        
    }
    
    public ParamsMap(byte[] bytes) throws UnsupportedEncodingException {
        this(new String(bytes, "utf-8")); 
    }

    public ParamsMap(String paramers) {
        map = new HashMap<String, Object>();
        if (paramers != null) {
            String[] paramarr = paramers.split("&");
            for (String param : paramarr) {
                String[] pam = param.split("=");
                if(pam.length<=1){
                    map.put(pam[0],"");
                }else{
                    map.put(pam[0], pam[1]);
                }
                
            }
        }
    }

    public <T> T get(String key) {
        return (T) map.get(key);
    }
    
    public String getString(String key){
        return  map.get(key).toString();
    }

    public Long getLong(String key) {
        return Long.parseLong(map.get(key) + "");
    }

    public Boolean getBoolean(String key) {
        return Boolean.parseBoolean(map.get(key).toString());
    }

}
