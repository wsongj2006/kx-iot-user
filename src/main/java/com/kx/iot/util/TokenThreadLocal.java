package com.kx.iot.util;

public class TokenThreadLocal {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setToken(String token) {
        threadLocal.set(token);
    }

    public static String getToken() {
        return threadLocal.get();
    }
}
