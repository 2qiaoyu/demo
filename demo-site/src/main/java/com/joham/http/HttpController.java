package com.joham.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HttpController {

    @RequestMapping("getHttp")
    public void getHttp(HttpServletRequest request) {
        System.out.println("客户提交的方式" + request.getMethod());
        System.out.println("使用的协议" + request.getProtocol());
        System.out.println("请求的客户端地址" + request.getRequestURI());
        System.out.println("请求的客户端地址全" + request.getRequestURL());
        System.out.println("请求的ip地址" + request.getRemoteAddr());
        System.out.println("请求端口号" + request.getServerPort());
        System.out.println("服务器名称" + request.getServerName());
        System.out.println("客户端主机名" + request.getRemoteHost());
        System.out.println("请求脚本文件路径" + request.getServletPath());
        System.out.println("http协议host" + request.getHeader("host"));
        System.out.println("http协议User-Agent" + request.getHeader("user-agent"));
        System.out.println("http协议accept-language" + request.getHeader("accept-language"));
    }
}
