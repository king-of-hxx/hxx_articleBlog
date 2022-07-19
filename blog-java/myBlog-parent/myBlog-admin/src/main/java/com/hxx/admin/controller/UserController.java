package com.hxx.admin.controller;

import com.hxx.admin.common.Result;
import com.hxx.admin.dao.pojo.User;
import com.hxx.admin.service.UserService;
import com.hxx.admin.vo.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class UserController {

    @Autowired
    private UserService userServiceImpl;

    @PostMapping("userManage/userList")
    public Result getAllUserList(@RequestBody PageParam pageParam){
        return userServiceImpl.getAllUserList(pageParam);
    }
    @PostMapping("userManage/update")
    public Result updateUserInfo(@RequestBody User user){
        return userServiceImpl.updateUserInfo(user);
    }
    @GetMapping("userManage/delete/{id}")
    public Result deleteUser(@PathVariable Long id){
        return userServiceImpl.deleteUser(id);
    }
}
