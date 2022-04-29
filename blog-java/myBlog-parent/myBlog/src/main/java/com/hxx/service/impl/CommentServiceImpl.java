package com.hxx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hxx.common.Result;
import com.hxx.common.utils.UserThreadLocal;
import com.hxx.dao.mapper.ArticleMapper;
import com.hxx.dao.mapper.CommentMapper;
import com.hxx.dao.mapper.SysUserMapper;
import com.hxx.dao.pojo.Article;
import com.hxx.dao.pojo.Comment;
import com.hxx.dao.pojo.SysUser;
import com.hxx.service.CommentService;
import com.hxx.service.ThreadService;
import com.hxx.vo.CommentVo;
import com.hxx.vo.UserVo;
import com.hxx.vo.params.CommentParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shlcm
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ThreadService threadService;

    @Override
    public Result getArticleComments(Long id) {
        Article article = articleMapper.selectById(id);
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getArticleId,article.getId());
        lambdaQueryWrapper.eq(Comment::getLevel,1);
        List<Comment> commentList = this.commentMapper.selectList(lambdaQueryWrapper);
        List<CommentVo> commentVoList = copyList(commentList);
        return Result.success(commentVoList);
    }

    /**
     * 新增评论
     * @param commentParams
     * @return
     */
    @Override
    public Result createArticleComments(CommentParams commentParams) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setAuthorId(sysUser.getId());
        comment.setArticleId(commentParams.getArticleId());
        comment.setContent(commentParams.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        if(commentParams.getParentId() == null || commentParams.getParentId() == 0){
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(commentParams.getParentId() == null ? 0 : commentParams.getParentId());
        comment.setToUid(commentParams.getToUserId() == null ? 0 :commentParams.getToUserId());
        commentMapper.insert(comment);
        if(comment.getLevel() == 1){
            threadService.updateArticleCommentCounts(articleMapper,comment.getArticleId());
        }
        return Result.success(null);
    }

    private List<CommentVo> copyList(List<Comment> commentList) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : commentList){
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //获取用户信息
        Long authorId = comment.getAuthorId();
        SysUser sysUser = this.sysUserMapper.selectById(authorId);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        commentVo.setAuthor(userVo);
        //获取二级评论set到commentVo对象中
        Long id = comment.getId();
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getParentId,id);
        lambdaQueryWrapper.eq(Comment::getLevel,2);
        List<Comment> childrenCommentList = commentMapper.selectList(lambdaQueryWrapper);
        commentVo.setChildrens(copyList(childrenCommentList));
        if(comment.getLevel() > 1){
            Long toUid = comment.getToUid();
            SysUser toUser = sysUserMapper.selectById(toUid);
            UserVo toUserVo = new UserVo();
            BeanUtils.copyProperties(toUser,toUserVo);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }
}
