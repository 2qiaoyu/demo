package com.joham.admin.controller;

import com.joham.admin.bean.Admin;
import com.joham.admin.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class AdminController {
    /**
     * 登录验证
     *
     * @param username
     * @param password
     */
    @RequestMapping("/login")
    @ResponseBody
    public int checkLogin(HttpServletRequest request, String username, String password, String captcha) {
        //判断验证码是否正确
        if (captcha == null || "".equals(captcha)) {
            //验证码为空
            return 2;
        } else {
            if (captcha.equals(request.getSession().getAttribute("patcha"))) {
                //验证码正确
                int result = adminService.checkLogin(request, username, password);
                if (result == 1) {
                    //登陆成功
                    return 1;
                } else {
                    //密码错误或者用户名不存在
                    return 3;
                }
            } else {
                //验证码错误
                return 2;
            }
        }
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    /**
     * 退出登录
     *
     * @return ModelAndView
     */
    @RequestMapping("/tologin")
    public ModelAndView tologin(HttpServletRequest request) {
        return new ModelAndView("jsp/signin");
    }

    @RequestMapping("/checkUserName")
    public ModelAndView checkUserName(HttpServletRequest request, HttpServletResponse response, String username) {
        Admin admin = null;
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            admin = adminService.checkUserName(username);
            if (admin != null) {
                pw.print(1);
            } else {
                pw.print(0);
            }
        } catch (IOException e) {
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        return null;
    }

    @RequestMapping("/toUpdatePwd")
    public ModelAndView toUpdatePwd() {
        return new ModelAndView("jsp/updatePwd");
    }

    @RequestMapping("/updatePwd")
    public ModelAndView modifyPwd(HttpServletRequest request, String userId, String newPassword) {
        Admin admin = null;
        admin = adminService.findUser(userId);
        admin.setPassword(newPassword);
        adminService.modifyPwd(admin);
        return new ModelAndView(new RedirectView(request.getContextPath() + "/login.html"));
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().removeAttribute("admin");
        return new ModelAndView("jsp/signin");
    }

    @Resource(name = "AdminService")
    private AdminService adminService;
}
