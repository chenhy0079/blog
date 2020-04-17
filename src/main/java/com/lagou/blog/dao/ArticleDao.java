package com.lagou.blog.dao;

import com.lagou.blog.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleDao {

    @Select("select * from t_article")
    public List<Article> queryAllByPage();
}
