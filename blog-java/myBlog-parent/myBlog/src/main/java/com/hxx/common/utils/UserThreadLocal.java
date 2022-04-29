package com.hxx.common.utils;

import com.hxx.dao.pojo.SysUser;

/**
 *  ThreadLocal保存用户信息
 * @author shlcm
 */
public class UserThreadLocal {

    private UserThreadLocal(){}

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }
    public static SysUser get(){
        return LOCAL.get();
    }
    public static void remove(){
        LOCAL.remove();
    }
}
