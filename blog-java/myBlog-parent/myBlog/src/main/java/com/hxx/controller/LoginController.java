package com.hxx.controller;

import com.hxx.common.Result;
import com.hxx.service.LoginService;
import com.hxx.vo.params.LoginParams;
import com.hxx.vo.params.RegisterParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author shlcm
 */

@RestController
public class LoginController {

    @Autowired
    private LoginService loginServiceImpl;

    @PostMapping("/login")
    public Result login(@RequestBody LoginParams loginParams){
        return loginServiceImpl.getLogin(loginParams);
    }
    @GetMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token){
        return loginServiceImpl.logout(token);
    }
    @PostMapping("/register")
    public Result register(@RequestBody RegisterParams registerParams){
        return loginServiceImpl.register(registerParams);
    }
}
