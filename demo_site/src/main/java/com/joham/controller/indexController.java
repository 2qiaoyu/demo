package com.joham.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2015/12/9.
 */
@Controller
public class indexController {
    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index/index");
    }
}
