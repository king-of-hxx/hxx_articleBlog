package com.hxx.admin.controller;

import com.hxx.admin.common.Result;
import com.hxx.admin.dao.pojo.Permission;
import com.hxx.admin.service.AdminService;
import com.hxx.admin.service.PermissionService;
import com.hxx.admin.vo.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author shlcm
 */
@RequestMapping("admin")
@RestController
public class AdminController {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private AdminService adminServiceImpl;

    @PostMapping("permission/permissionList")
    public Result permissionList(@RequestBody PageParam pageParam){
        return permissionService.listPermission(pageParam);
    }

    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }

    @GetMapping("user/userInfo")
    public Result getCurrentUser(Principal principal){
        return Result.success(principal.getName());
    }
}
