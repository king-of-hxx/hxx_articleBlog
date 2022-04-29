package com.hxx.service;

import com.hxx.common.Result;
import com.hxx.vo.TagVo;

import java.util.List;

/**
 * @author shlcm
 */
public interface TagService {
    List<TagVo> findTagId(Long id);

    Result getHotTags(int limit);

    Result findAllTags();

    Result findDetailById(Long id);
}
