package com.hxx.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hxx.dao.mapper.ArticleMapper;
import com.hxx.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author shlcm
 */
@Component
public class ThreadService {
    @Async("taskExecutor")
    public void updateArticleViewCounts(Article article, ArticleMapper articleMapper){
        int currentViewCounts = article.getViewCounts();
        Article updateArticleViewCounts = new Article();
        updateArticleViewCounts.setViewCounts(currentViewCounts+1);
        LambdaUpdateWrapper<Article> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Article::getId,article.getId());
        lambdaUpdateWrapper.eq(Article::getViewCounts,currentViewCounts);
        articleMapper.update(updateArticleViewCounts,lambdaUpdateWrapper);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async("taskExecutor")
    public void updateArticleCommentCounts(ArticleMapper articleMapper, Long articleId) {
        Article article = articleMapper.selectById(articleId);
        Integer currentCommentCounts = article.getCommentCounts();
        article.setCommentCounts(currentCommentCounts+1);
        LambdaUpdateWrapper<Article> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Article::getId,articleId);
        lambdaUpdateWrapper.eq(Article::getCommentCounts,currentCommentCounts);
        articleMapper.update(article,lambdaUpdateWrapper);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
