package com.hxx.admin.vo;

import lombok.Data;

import java.util.List;

@Data
public class PermissionPageVo<T> {

    private List<T> list;

    private Long total;
}