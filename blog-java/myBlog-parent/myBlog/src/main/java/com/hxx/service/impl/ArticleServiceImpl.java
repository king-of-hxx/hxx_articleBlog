package com.hxx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxx.common.Result;
import com.hxx.common.utils.UserThreadLocal;
import com.hxx.dao.mapper.ArticleBodyMapper;
import com.hxx.dao.mapper.ArticleMapper;
import com.hxx.dao.mapper.ArticleTagMapper;
import com.hxx.dao.mapper.CategoryMapper;
import com.hxx.dao.pojo.*;
import com.hxx.service.ArticleService;
import com.hxx.service.SysUserService;
import com.hxx.service.TagService;
import com.hxx.service.ThreadService;
import com.hxx.vo.*;
import com.hxx.vo.params.ArticleParam;
import com.hxx.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.hxx.common.utils.DateFormatUtil.timeStamp2Date;


/**
 * @author shlcm
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private SysUserService sysUserServiceImpl;
    @Autowired
    private TagService tagServiceImpl;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ThreadService threadService;
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public Result getHotArticles(int limit) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Article::getViewCounts);
        lambdaQueryWrapper.select(Article::getId,Article::getTitle,Article::getViewCounts);
        lambdaQueryWrapper.last("limit " + limit);
        List<Article> HotArticles = articleMapper.selectList(lambdaQueryWrapper);
        Long limits = Long.valueOf(limit);
        return Result.success(copyList(HotArticles,limits,false,false));
    }

    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(page,pageParams.getCategoryId(),pageParams.getTagId(),pageParams.getYear(),pageParams.getMonth());
        long total = articleIPage.getTotal();
        return Result.success(copyList(articleIPage.getRecords(),total,true,true));
    }


//    @Override
//    public Result listArticle(PageParams pageParams) {
//        /**
//         * 分页查询 article数据表
//         */
//        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        /**
//         *         //是否置顶
//         *         queryWrapper.orderByDesc(Article::getWeight);
//         *         //按时间排序
//         *         queryWrapper.orderByDesc(Article::getCreateDate);
//         */
//        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
//        //查询文章的参数 加上分类id，判断不为空 加上分类条件
//        if (pageParams.getCategoryId() != null) {
//            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
//        }
//        List<Long> articleIdList = new ArrayList<>();
//        if(pageParams.getTagId() != null){
//            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
//            List<ArticleTag> articleTagList = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//            for(ArticleTag articleTag : articleTagList){
//                articleIdList.add(articleTag.getArticleId());
//            }
//            System.out.println(articleIdList);
//            if (articleIdList.size()>0){
//                queryWrapper.in(Article::getId,articleIdList);
//            }
//        }
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//        List<Article> records = articlePage.getRecords();
//        List<ArticleVo> articleVoList = copyList(records,true,true);
//        return Result.success(articleVoList);
//    }

    @Override
    public Result getNewArticle(int limit) {
        LambdaQueryWrapper<Article> newArticleQueryWrapper = new LambdaQueryWrapper<>();
        newArticleQueryWrapper.orderByDesc(Article::getCreateDate);
        newArticleQueryWrapper.select(Article::getId,Article::getTitle,Article::getCreateDate);
        newArticleQueryWrapper.last("limit " + limit);
        List<Article> newArticles = articleMapper.selectList(newArticleQueryWrapper);
        Long limits = Long.valueOf(limit);
        return Result.success(copyList(newArticles,limits,false,false));
    }

    @Override
    public Result getListArchives() {
        List<ArchivesVo> ArchivesVo = articleMapper.getListArchives();
        return Result.success(ArchivesVo);
    }

    /**
     * 文章点进去详情查看
     * @return
     */
    @Override
    public Result ViewArticleInfo(Long id) {
        Article article = articleMapper.selectById(id);
        Long bodyId = article.getBodyId();
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        articleVo.setBody(articleBodyVo);
        SysUser sysUser = sysUserServiceImpl.findSysUserById(article.getAuthorId());
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        articleVo.setAuthor(userVo);
        articleVo.setAvatar(sysUserServiceImpl.findSysUserById(article.getAuthorId()).getAvatar());
        articleVo.setCreateDate(timeStamp2Date(String.valueOf(sysUserServiceImpl.findSysUserById(article.getAuthorId()).getCreateDate())));
        articleVo.setTags(tagServiceImpl.findTagId(article.getId()));
        Category category = categoryMapper.selectById(article.getCategoryId());
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        articleVo.setCategory(categoryVo);
        //使用线程池更新阅读次数
        threadService.updateArticleViewCounts(article,articleMapper);
        return Result.success(articleVo);
    }

    /**
     * 写文章
     * @param articleParam
     * @return
     */
    @Override
    @Transactional
    public Result writeArticle(ArticleParam articleParam) {
        SysUser sysUser = UserThreadLocal.get();
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setViewCounts(0);
        article.setWeight(Article.Article_Common);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCategoryId(articleParam.getCategory().getId());
        this.articleMapper.insert(article);
        List<TagVo> tagVoList = articleParam.getTags();
        for (TagVo tagVo : tagVoList){
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(article.getId());
            articleTag.setTagId(tagVo.getId());
            articleTagMapper.insert(articleTag);
        }
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId());
        return Result.success(articleVo);
    }

    private List<ArticleVo> copyList(List<Article> records,Long total, Boolean isAuthor, Boolean isTags) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records){
            articleVoList.add(copy(record,total, isAuthor, isTags));
        }
        return articleVoList;
    }
    private ArticleVo copy(Article article,Long total, Boolean isAuthor, Boolean isTags){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        if (isAuthor){
            SysUser author = sysUserServiceImpl.findSysUserById(article.getAuthorId());
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(author,userVo);
            articleVo.setAuthor(userVo);
        }
        if (isTags){
            List<TagVo> tags = tagServiceImpl.findTagId(article.getId());
            articleVo.setTags(tags);
//            System.out.println(tags);
        }
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
//        articleVo.setCreateDate(timeStamp2Date(String.valueOf(article.getCreateDate())));
        articleVo.setTotal(total);
        return articleVo;
    }
}
