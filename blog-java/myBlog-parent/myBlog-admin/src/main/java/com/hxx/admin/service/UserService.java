package com.hxx.admin.service;

import com.hxx.admin.common.Result;
import com.hxx.admin.dao.pojo.User;
import com.hxx.admin.vo.params.PageParam;

public interface UserService {
    Result getAllUserList(PageParam pageParam);

    Result updateUserInfo(User user);

    Result deleteUser(Long id);
}
