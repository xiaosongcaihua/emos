package com.emos.wx.common.util;

import cn.hutool.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String,Object> {
    public R() {
        put("code", HttpStatus.HTTP_OK);
        put("msg", "success");
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static R ok() {
        return new R();
    }
    public static R ok(Object msg) {
        R r = new R();
        r.put("msg" , msg);
        return r;
    }

    public static R ok(Map<String,Object> map){
        R r=new R();
        r.putAll(map);
        return r;
    }

    public static R error(int code,String msg){
        R r=new R();
        r.put("code",code);
        r.put("msg",msg);
        return r;
    }
    public static R error(String msg){
        return error(HttpStatus.HTTP_INTERNAL_ERROR,msg);
    }
    public static R error(){
        return error(HttpStatus.HTTP_INTERNAL_ERROR,"未知异常，请联系管理员");
    }
}
