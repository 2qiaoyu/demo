package com.joham.index;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

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
    @RequestMapping("/")
    public String indexSite(HttpServletResponse response) {
        log.info("进入前台首页");
        //三秒刷新一次
//        response.setHeader("refresh", "3");
        //三秒跳转
//        response.setHeader("refresh", "3;URL=geHttp");
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
