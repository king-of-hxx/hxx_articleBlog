package com.hxx.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hxx.common.ErrorCode;
import com.hxx.common.Result;
import com.hxx.common.utils.JWTUtils;
import com.hxx.dao.mapper.SysUserMapper;
import com.hxx.dao.pojo.SysUser;
import com.hxx.service.LoginService;
import com.hxx.vo.params.LoginParams;
import com.hxx.vo.params.RegisterParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author shlcm
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Result getLogin(LoginParams loginParams) {
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
           return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password);
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getAccount,account);
        lambdaQueryWrapper.eq(SysUser::getPassword,password);
        lambdaQueryWrapper.select(SysUser::getId,SysUser::getAccount,SysUser::getAvatar,SysUser::getNickname);
        lambdaQueryWrapper.last("limit " + 1);
        SysUser loginUserInfo = sysUserMapper.selectOne(lambdaQueryWrapper);
        if(loginUserInfo == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(loginUserInfo.getId());
        redisTemplate.opsForValue().set("Token_"+token, JSON.toJSONString(loginUserInfo),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("Token_"+token);
        return Result.success(null);
    }

    @Override
    public Result register(RegisterParams registerParams) {
        String account = registerParams.getAccount();
        String password = registerParams.getPassword();
        String nickname = registerParams.getNickname();
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password) ||StringUtils.isBlank(nickname)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password);
        SysUser sysUser = sysUserMapper.findSysUserByAccount(account);
        if(sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setAccount(account);
        sysUser.setAdmin(1);
        sysUser.setPassword(password);
        sysUser.setNickname(nickname);
        sysUser.setAvatar("girl.jpg");
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserMapper.insert(sysUser);
        return Result.success(null);
    }
}
