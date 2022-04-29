package com.hxx.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxx.dao.pojo.Tag;
import com.hxx.vo.TagVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shlcm
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> findTagId(Long tagId);

    List<Integer> findHotTagById(int limit);

    List<TagVo> findHotTagByTagId(List<Integer> tagIds);
}
