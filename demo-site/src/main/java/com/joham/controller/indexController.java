package com.joham.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 *
 * @author qiaoyu
 */

@Controller
@Slf4j
public class IndexController {

    /**
     * 首页
     */
    @RequestMapping("/indexSite")
    public String indexSite() {
        log.info("进入前台首页");
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
