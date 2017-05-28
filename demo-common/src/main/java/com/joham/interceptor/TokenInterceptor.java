package com.joham.interceptor;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.joham.annotation.RepeatSubmitValidate;

public class TokenInterceptor extends HandlerInterceptorAdapter {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            if (request.getSession(false) == null) {
                return true;
            }
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmitValidate annotation = method.getAnnotation(RepeatSubmitValidate.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.create();
                if (needSaveSession) {
                    log.debug("创建token");
                    request.getSession(false).setAttribute("token", UUID.randomUUID().toString());
                }
                boolean needRemoveSession = annotation.destroy();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        log.debug("重复提交");
                        StringBuilder builderHead = new StringBuilder();
                        StringBuilder builderTip = new StringBuilder();
                        StringBuilder builderFoot = new StringBuilder();
                        String noLogin = " <div class='modal fade' id='loginFail'>";
                        noLogin += "<div class='modal-dialog'>";
                        noLogin += " <div class='modal-content'>";
                        noLogin += " <div class='modal-header'>";
                        noLogin += " <h4 class='modal-title'>重复提交</h4>";
                        noLogin += "</div>";
                        noLogin += " <div class='modal-body'>";
                        noLogin += " <p>请勿重复提交!</p>";
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
                                + "$('#tologin').click(function(){top.location.href='index.htm'});"
                                + "}");
                        builderFoot.append("</script>");
                        response.setContentType("text/html;charset=utf-8");
                        PrintWriter out;
                        out = response.getWriter();
                        builderTip.append(noLogin);
                        out.print(builderHead.append(builderTip).append(builderFoot).toString());
                        out.close();
                        return false;
                    }
                    request.getSession(false).removeAttribute("token");
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(false).getAttribute("token");
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}
