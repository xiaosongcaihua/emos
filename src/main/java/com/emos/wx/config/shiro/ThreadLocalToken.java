package com.emos.wx.config.shiro;

import org.springframework.stereotype.Component;
@Component
public class ThreadLocalToken {
    private final ThreadLocal<String> local = new ThreadLocal<>();

    public void setToken(String token) {
        local.set(token);
    }

    public String getToken() {
        return (String) local.get();
    }

    public void clear() {
        local.remove();
    }
}