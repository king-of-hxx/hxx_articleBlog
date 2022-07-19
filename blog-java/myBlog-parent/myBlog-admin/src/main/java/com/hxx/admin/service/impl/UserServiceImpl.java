package com.hxx.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxx.admin.common.Result;
import com.hxx.admin.dao.mapper.UserMapper;
import com.hxx.admin.dao.pojo.User;
import com.hxx.admin.service.UserService;
import com.hxx.admin.vo.UserPageVo;
import com.hxx.admin.vo.params.PageParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result getAllUserList(PageParam pageParam) {
        Page<User> page = new Page(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pageParam.getQueryString())){
            lambdaQueryWrapper.like(User::getAccount,pageParam.getQueryString());
        }
        Page<User> userPage = userMapper.selectPage(page,lambdaQueryWrapper);
        UserPageVo<User> userPageVo = new UserPageVo<>();
        userPageVo.setUserList(userPage.getRecords());
        userPageVo.setTotal(userPage.getTotal());
        return Result.success(userPageVo);
    }

    @Override
    public Result updateUserInfo(User user) {
        User user1 = this.userMapper.selectById(user.getId());
        if (!user.getPassword().equals(user1.getPassword())){
            String password = DigestUtils.md5Hex(user.getPassword());
            user.setPassword(password);
        }
        this.userMapper.updateById(user);
        return Result.success(null);
    }

    @Override
    public Result deleteUser(Long id) {
        this.userMapper.deleteById(id);
        return Result.success(null);
    }
}
