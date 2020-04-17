package com.lagou.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.blog.dao.ArticleDao;
import com.lagou.blog.pojo.Article;
import com.lagou.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public PageInfo<Article> queryByPage(int pageNum,int pageSizes) {

        PageHelper.startPage(pageNum,pageSizes);

        List<Article> articles = articleDao.queryAllByPage();

        PageInfo<Article> articlePageInfo = new PageInfo<>(articles);

        return articlePageInfo;
    }
}
