package com.hxx.controller;


import com.hxx.common.Result;
import com.hxx.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shlcm
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private SysUserService sysUserServiceImpl;

    @GetMapping("/currentUser")
    public Result getCurrentUserInfo(@RequestHeader("Authorization") String token){
        return sysUserServiceImpl.getCurrentUserInfo(token);
    }
}
