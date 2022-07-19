package com.hxx.admin.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserPageVo<T> {
    private List<T> userList;
    private Long total;
}
