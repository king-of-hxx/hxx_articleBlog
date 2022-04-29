package com.hxx.service;

import com.hxx.common.Result;
import com.hxx.dao.pojo.SysUser;

/**
 * @author shlcm
 */
public interface SysUserService {
    SysUser findSysUserById(Long authorId);

    Result getCurrentUserInfo(String token);
}
