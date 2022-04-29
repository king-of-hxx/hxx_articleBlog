package com.hxx.service;

import com.hxx.common.Result;
import com.hxx.vo.params.CommentParams;

/**
 * @author shlcm
 */

public interface CommentService {
    Result getArticleComments(Long id);

    Result createArticleComments(CommentParams commentParams);
}
