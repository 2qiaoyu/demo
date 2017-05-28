package com.joham.interceptor;


import com.joham.admin.bean.Admin;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by joham on 2015/7/31.
 */
public class URLInterceptor extends HandlerInterceptorAdapter {
    String[] nofilters = new String[]{
            "/login.htm", "/toRegister.htm", "/checkUserName.htm", "/toSuccess.htm", "/patchca.htm", "/patchcaSession.htm", "/tologin.htm"
    };

    /**
     * This implementation always returns <code>true</code>.
     * 在请求处理前拦截URL 进行相应处理
     *
     * @param request
     * @param response
     * @param handler
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        boolean isFilter = true;
        for (String url : nofilters) {
            if (url.equals(path)) {
                isFilter = false;
                break;
            }
        }
        StringBuilder builderHead = new StringBuilder();
        StringBuilder builderTip = new StringBuilder();
        StringBuilder builderFoot = new StringBuilder();
        String noLogin = " <div class='modal fade' id='loginFail'>";
        noLogin += "<div class='modal-dialog'>";
        noLogin += " <div class='modal-content'>";
        noLogin += " <div class='modal-header'>";
        noLogin += " <h4 class='modal-title'>登录失效</h4>";
        noLogin += "</div>";
        noLogin += " <div class='modal-body'>";
        noLogin += " <p>对不起！您的登录状态已经失效，需要重新登录。</p>";
        noLogin += " </div>";
        noLogin += " <div class='modal-footer'>";
        noLogin += " <button type='button' class='btn btn-primary' id='tologin'>确定</button>";
        noLogin += " </div>";
        noLogin += " </div>";
        noLogin += " </div>";
        noLogin += " </div>";
        builderHead.append("<head>" + "<link href='css/bootstrap.min.css' rel='stylesheet'>" + "<script src='js/jquery.min.js'></script>" + "<script src='js/bootstrap.min.js'></script>" + "</head>" + "<html> <body>");
        builderFoot.append("</body></html><script type=\"text/javascript\">   ");
        builderFoot.append("window.onload=function(){   $('#loginFail').modal('show');"
                + "$('#tologin').click(function(){top.location.href='tologin.htm'});"
                + "}");
        builderFoot.append("</script>");
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out;
        if (isFilter) {
            if (admin == null) {
                try {
                    out = response.getWriter();
                    builderTip.append(noLogin);
                    out.print(builderHead.append(builderTip).append(builderFoot).toString());
                    out.close();
                } catch (IOException e) {
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * This implementation is empty.
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * This implementation is empty.
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
