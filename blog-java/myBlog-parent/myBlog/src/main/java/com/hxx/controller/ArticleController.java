package com.hxx.controller;

import com.hxx.common.Result;
import com.hxx.common.aop.Cache;
import com.hxx.common.aop.LogAnnotation;
import com.hxx.service.ArticleService;
import com.hxx.service.TagService;
import com.hxx.vo.params.ArticleParam;
import com.hxx.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author shlcm
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService TagServiceImpl;
/**
 * 首页文章列表
 */
    @PostMapping
    /**
     * aop日志自定义注解
     */
    @LogAnnotation(module = "文章",operation = "获取文章列表")
   public Result listArticle(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
   }

    @Cache(expire = 5 * 60 * 1000,name = "hot_article")
    @GetMapping("/hotTags")
    public Result hotTags(){
        int limit = 4;
        return TagServiceImpl.getHotTags(limit);
   }

    @PostMapping("/hot")
    public Result hotArticle(){
        int limit = 3;
        return articleService.getHotArticles(limit);
   }

   @PostMapping("/new")
    public Result newArticle(){
        int limit = 5;
        return articleService.getNewArticle(limit);
   }

   @PostMapping("/listArchives")
    public Result listArchives(){
        return articleService.getListArchives();
   }

   @PostMapping("/view/{id}")
    public Result ViewArticleInfo(@PathVariable("id") Long id){
        return articleService.ViewArticleInfo(id);
   }

    @PostMapping("/{id}")
    public Result WriteArticle(@PathVariable("id") Long id){
        return articleService.ViewArticleInfo(id);
    }

   @GetMapping("allTags")
    public Result findAllTags(){
        return TagServiceImpl.findAllTags();
   }

    @GetMapping("tagsDetail")
    public Result findAllDetailTags(){
        return TagServiceImpl.findAllTags();
    }

    @GetMapping("tagDetail/{id}")
    public Result findDetailById(@PathVariable("id") Long id){
        return TagServiceImpl.findDetailById(id);
    }

    /**
     * 写文章
     */
    @PostMapping("/publish")
    public Result writeArticle(@RequestBody ArticleParam articleParam){
        return articleService.writeArticle(articleParam);
    }
}
