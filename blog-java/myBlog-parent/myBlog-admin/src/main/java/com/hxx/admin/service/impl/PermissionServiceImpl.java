package com.hxx.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxx.admin.common.Result;
import com.hxx.admin.dao.mapper.PermissionMapper;
import com.hxx.admin.dao.pojo.Permission;
import com.hxx.admin.service.PermissionService;
import com.hxx.admin.vo.PermissionPageVo;
import com.hxx.admin.vo.params.PageParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shlcm
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Result listPermission(PageParam pageParam) {
        Page<Permission> page = new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<Permission> LambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pageParam.getQueryString())){
            LambdaQueryWrapper.eq(Permission::getName,pageParam.getQueryString());
        }
        Page<Permission> permissionPage = permissionMapper.selectPage(page, LambdaQueryWrapper);
        PermissionPageVo<Permission> PageResult = new PermissionPageVo<>();
        PageResult.setList(permissionPage.getRecords());
        PageResult.setTotal(permissionPage.getTotal());
        return Result.success(PageResult);
    }

    @Override
    public Result add(Permission permission) {
        this.permissionMapper.insert(permission);
        return Result.success(null);
    }

    @Override
    public Result update(Permission permission) {
        this.permissionMapper.updateById(permission);
        return Result.success(null);
    }

    @Override
    public Result delete(Long id) {
        this.permissionMapper.deleteById(id);
        return Result.success(null);
    }
}
