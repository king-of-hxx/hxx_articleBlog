package com.hxx.service;

import com.hxx.common.Result;
import com.hxx.vo.params.LoginParams;
import com.hxx.vo.params.RegisterParams;

public interface LoginService {
    Result getLogin(LoginParams loginParams);
    Result logout(String token);
    Result register(RegisterParams registerParams);
}
