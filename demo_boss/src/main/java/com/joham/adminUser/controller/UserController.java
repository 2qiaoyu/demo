/*
 * Copyright 2013 joham, Inc.All rights reserved.
 * joham PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.joham.adminUser.controller;

import com.joham.adminUser.bean.User;
import com.joham.adminUser.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
	/**
	 * 登录验证
	 * @param username
	 * @param password
	 * @param response
	 * @throws java.io.UnsupportedEncodingException
	 */
	
	@RequestMapping("/login")
	@ResponseBody
	public int checkLogin(HttpServletRequest request,HttpServletResponse response,String username,String password,String type) throws UnsupportedEncodingException{
		User user=null;
		user=userService.checkLogin(username, password);
		if(user!=null){
			request.getSession().setAttribute("adminUser", user);
			if (type.equals("0")) {
                Cookie cookie = new Cookie("_npstore_username",
                        URLEncoder.encode(username, "utf-8"));
                // 设置七天
                cookie.setMaxAge(7 * 24 * 3600);
                response.addCookie(cookie);
            } else if (type.equals("1")) {
                Cookie nameCookie = new Cookie("_npstore_username",
                        URLEncoder.encode(username, "utf-8"));
                // 设置七天
                nameCookie.setMaxAge(7 * 24 * 3600);
                response.addCookie(nameCookie);

                Cookie pwdCookie = new Cookie("_npstore_pwd",
                        URLEncoder.encode(password, "utf-8"));
                // 设置七天
                pwdCookie.setMaxAge(7 * 24 * 3600);
                response.addCookie(pwdCookie);
            }
			return 1;
		}else{
			return 0;
		}
	}
	
	@RequestMapping("/toRegister")
	public ModelAndView toRegister(){
		return new ModelAndView("jsp/register");
	}
	
	@RequestMapping("customer/toLogin")
	public ModelAndView toLogin(HttpServletRequest request,String url) throws UnsupportedEncodingException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
        String urlEmp = url;
        String preUrl = request.getHeader("Referer");
        if (preUrl != null) {
            String strRegex = "^((https|http|ftp|rtsp|mms)?://)"
            // ftp的user@
                    + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?"
                    // IP形式的URL- 199.194.52.184
                    // 允许IP和DOMAIN（域名）
                    + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" + "|"
                    // 域名- www.
                    + "([0-9a-z_!~*'()-]+\\.)*"
                    // 二级域名
                    + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\."
                    // first level domain- .com or .museum
                    + "[a-z]{2,6}|"
                    // 测试用 : 本地localhost.
                    + "([0-9a-z][0-9a-z-]{0,61}))"
                    // 端口- :80
                    + "(:[0-9]{1,4})?" +
                    // 项目名称
                    request.getContextPath()+"/";
            urlEmp = preUrl.replaceFirst(strRegex, "");
        } else {
            preUrl = (String) request.getSession().getAttribute("preferUrl");
            preUrl = preUrl == null ? urlEmp :preUrl;
            if (preUrl != null) {
                urlEmp = preUrl;
            } else {
                urlEmp = "index.html";
            }
        }
        if (urlEmp.indexOf("register") != -1 || urlEmp.indexOf("success") != -1 || urlEmp.length() == 0 || urlEmp.indexOf("updatesucess") != -1 ) {
            urlEmp = "index.html";
        }
        if (urlEmp.indexOf("validateidentity") != -1 || urlEmp.indexOf("reirectpem") != -1) {
            urlEmp = "customer/securitycenter.html";
        } 
        if (urlEmp.indexOf(".html") == -1) {
            urlEmp = urlEmp + ".html";
        }
        setResultMap(request, resultMap, urlEmp);
		return new ModelAndView("login").addAllObjects(resultMap);
	}
	
	public void setResultMap(HttpServletRequest request,
            Map<String, Object> resultMap, String url)
            throws UnsupportedEncodingException {
        String username = "";
        // 读取cookie中的信息
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie != null
                        && "_npstore_username".equals(cookie.getName())) {
                    username = URLDecoder.decode(cookie.getValue(), "utf-8");
                    break;
                }
            }
        }
        resultMap.put("username", username);
        resultMap.put("url", url);
    }
	@RequestMapping("/toSuccess")
	public ModelAndView toSuccess(){
		return new ModelAndView("jsp/RegisterSuccess");
	}
	
	@RequestMapping("/addUser")
	public ModelAndView addUser(HttpServletRequest request,String username,String password){
		User user=new User();
		user.setUserName(username);
		user.setPassword(password);
		user.setDelflag("0");
		userService.addUser(user);
		return new ModelAndView(new RedirectView(request.getContextPath()+"/success.html"));
	}
	@RequestMapping("/checkUserName")
	public ModelAndView checkUserName(HttpServletRequest request,HttpServletResponse response,String username){
		User user=null;
		PrintWriter pw=null;
		try{
			pw=response.getWriter();
			user=userService.CheckUserName(username);
			if(user!=null){
				pw.print(1);
			}else{
				pw.print(0);
			}
		}catch(IOException e){
		}finally{
			if(pw!=null){
				pw.close();
			}
		}
		return null;
	}
	@RequestMapping("/toUpdatePwd")
	public ModelAndView toUpdatePwd(){
		return new ModelAndView("jsp/updatePwd");
	}
	@RequestMapping("/updatePwd")
	public ModelAndView modifyPwd(HttpServletRequest request,String userId,String newPassword){
		User user=null;
		user=userService.findUser(userId);
		user.setPassword(newPassword);
		userService.modifyPwd(user);
		return new ModelAndView(new RedirectView(request.getContextPath()+"/login.html"));
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request){
		request.getSession().removeAttribute("adminUser");
		return new ModelAndView(new RedirectView(request.getContextPath()+"/login.html"));
	}
	
	private UserService userService;
	

	public UserService getUserService() {
		return userService;
	}
	@Resource(name="UserService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
