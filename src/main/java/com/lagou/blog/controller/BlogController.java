package com.lagou.blog.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.blog.pojo.Article;
import com.lagou.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @RequestMapping("/get")
    @ResponseBody
    public PageInfo<Article> getInfo(int pageNum,int pageSize){

        return blogService.queryByPage(pageNum,pageSize);

    };


    @RequestMapping("/getInfos")
    public ModelAndView getInfoByTf(@RequestParam(required=false,defaultValue = "1") int pageNum, @RequestParam(required=false,defaultValue = "2") int pageSize){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("infos",blogService.queryByPage(pageNum,pageSize));

        modelAndView.setViewName("index");

        return modelAndView;

    };
}
