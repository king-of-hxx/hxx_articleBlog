package com.hxx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hxx.dao.mapper.TagMapper;
import com.hxx.dao.pojo.Tag;
import com.hxx.service.TagService;
import com.hxx.common.Result;
import com.hxx.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shlcm
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList){
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }
    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }
    @Override
    public List<TagVo> findTagId(Long tagId) {
        List<Tag> tags = tagMapper.findTagId(tagId);
        return copyList(tags);
    }

    @Override
    public Result getHotTags(int limit) {
        List<Integer> tagIdList = tagMapper.findHotTagById(limit);
        List<TagVo> HotTagVOList =  tagMapper.findHotTagByTagId(tagIdList);
        return Result.success(HotTagVOList);
    }

    @Override
    public Result findAllTags() {
        List<Tag> allTagList = this.tagMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(allTagList);
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        TagVo copy = copy(tag);
        return Result.success(copy);
    }
}
