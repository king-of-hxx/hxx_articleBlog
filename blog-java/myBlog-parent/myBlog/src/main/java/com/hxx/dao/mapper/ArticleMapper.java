package com.hxx.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxx.dao.pojo.Article;
import com.hxx.vo.ArchivesVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shlcm
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    List<ArchivesVo> getListArchives();

    IPage<Article> listArticle(Page<Article> page, Long categoryId, Long tagId, String year, String month);
}
