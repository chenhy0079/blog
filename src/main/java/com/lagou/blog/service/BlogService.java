package com.lagou.blog.service;

import com.github.pagehelper.PageInfo;
import com.lagou.blog.pojo.Article;

public interface BlogService {

    public PageInfo<Article> queryByPage(int pageNum,int pageSizes);
}
