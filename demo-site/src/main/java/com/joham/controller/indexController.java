package com.joham.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/indexSite")
    public ModelAndView index() {
        logger.info("进入前台首页");
        return new ModelAndView("index/index");
    }
}
