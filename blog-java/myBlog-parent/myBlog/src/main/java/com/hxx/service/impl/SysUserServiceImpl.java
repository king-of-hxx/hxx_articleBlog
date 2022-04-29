package com.hxx.service.impl;

import com.alibaba.fastjson.JSON;
import com.hxx.common.ErrorCode;
import com.hxx.common.Result;
import com.hxx.common.utils.JWTUtils;
import com.hxx.dao.mapper.SysUserMapper;
import com.hxx.dao.pojo.SysUser;
import com.hxx.service.SysUserService;
import com.hxx.vo.LoginUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author shlcm
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 将返回的实体类copy至vo对象
     * @param currentUserInfo
     * @return
     */
    public LoginUserVo copy(SysUser currentUserInfo){
        LoginUserVo loginUserVo = new LoginUserVo();
        BeanUtils.copyProperties(currentUserInfo,loginUserVo);
        return loginUserVo;
    }

    @Override
    public SysUser findSysUserById(Long authorId) {
        SysUser sysUser = sysUserMapper.selectById(authorId);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("不详");
        }
        return sysUser;
    }

    @Override
    public Result getCurrentUserInfo(String token) {
        Map<String, Object> currentUserMap = JWTUtils.checkToken(token);
        if(currentUserMap == null){
            return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
        }
        String currentUserString = (String) redisTemplate.opsForValue().get("Token_" + token);
        if(StringUtils.isBlank(currentUserString)){
            return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
        }
        SysUser currentUserInfo = JSON.parseObject(currentUserString, SysUser.class);
        return Result.success(copy(currentUserInfo));
    }
}
