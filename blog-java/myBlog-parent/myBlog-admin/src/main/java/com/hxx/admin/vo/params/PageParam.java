package com.hxx.admin.vo.params;

import lombok.Data;

/**
 * @author shlcm
 */
@Data
public class PageParam {

    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}