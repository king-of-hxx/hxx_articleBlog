package com.hxx.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxx.dao.pojo.SysUser;
import org.springframework.stereotype.Repository;

/**
 * @author shlcm
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    SysUser findSysUserByAccount(String account);
}
