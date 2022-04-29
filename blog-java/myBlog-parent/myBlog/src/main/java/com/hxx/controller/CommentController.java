package com.hxx.controller;

import com.hxx.common.Result;
import com.hxx.service.CommentService;
import com.hxx.vo.params.CommentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author shlcm
 */
@RestController
@RequestMapping("comments")
public class CommentController {
    @Autowired
    private CommentService commentServiceImpl;

    @GetMapping("article/{id}")
    public Result getArticleComments(@PathVariable("id") Long articleId){
        return commentServiceImpl.getArticleComments(articleId);
    }

    /**
     * 评论
     * @param commentParams
     * @return
     */
    @PostMapping("create/change")
    public Result createArticleComments(@RequestBody CommentParams commentParams){
//        System.out.println(UserThreadLocal.get());
        return commentServiceImpl.createArticleComments(commentParams);
    }

}
