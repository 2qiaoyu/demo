package com.joham.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    /**
     * 首页
     */
    @RequestMapping("/indexSite")
    public String indexSite() {
        logger.info("进入前台首页");
        return "index/index";
    }

    /**
     * 错误页
     */
    @RequestMapping("/errorPage")
    public String errorPage() {
        return "index/error_page";
    }
}
