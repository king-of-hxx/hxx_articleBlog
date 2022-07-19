package com.hxx.admin.service;

import com.hxx.admin.common.Result;
import com.hxx.admin.dao.pojo.Permission;
import com.hxx.admin.vo.params.PageParam;

public interface PermissionService {
    Result listPermission(PageParam pageParam);

    Result add(Permission permission);

    Result update(Permission permission);

    Result delete(Long id);
}
