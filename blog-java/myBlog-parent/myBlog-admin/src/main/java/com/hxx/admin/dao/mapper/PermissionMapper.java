package com.hxx.admin.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxx.admin.dao.pojo.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shlcm
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> findPermissionsByAdminId(Long adminId);
}
