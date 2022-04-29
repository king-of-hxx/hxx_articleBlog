package com.hxx.service;

import com.hxx.common.Result;
import com.hxx.vo.params.ArticleParam;
import com.hxx.vo.params.PageParams;

/**
 * @author shlcm
 */
public interface ArticleService {
    Result getHotArticles(int limit);

    Result listArticle(PageParams pageParams);

    Result getNewArticle(int limit);

    Result getListArchives();

    Result ViewArticleInfo(Long id);

    Result writeArticle(ArticleParam articleParam);
}
