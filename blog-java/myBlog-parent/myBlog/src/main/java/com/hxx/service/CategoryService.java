package com.hxx.service;

import com.hxx.common.Result;
import com.hxx.dao.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Result categoriesDetailById(Long id);
}
