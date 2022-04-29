package com.hxx.vo.params;

import com.hxx.dao.pojo.Category;
import com.hxx.vo.ArticleBodyVo;
import com.hxx.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @author shlcm
 */
@Data
public class ArticleParam {
    private Long id;

    private ArticleBodyVo body;

    private Category category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}