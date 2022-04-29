package com.hxx.vo;

import lombok.Data;

/**
 * @author shlcm
 */
@Data
public class ArticleBodyVo {
    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}
