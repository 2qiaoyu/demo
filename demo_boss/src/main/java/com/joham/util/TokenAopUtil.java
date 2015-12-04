package com.joham.util;


import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by joham on 2015/7/31.
 */

//声明这是一个组件
@Component
//声明这是一个切面bean
@Aspect
public class TokenAopUtil {
    @Autowired
    private HttpServletRequest request;

    String[] urls = new String[]{
            "/test.htm"
    };

    //配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
    @Pointcut("execution(* com.ningpai.*.controller.*.*(..))")
    private void pointCut(){

    }

    /**
     * 配置前置通知,使用在方法aspect()上注册的切入点
     */
    @Before("pointCut()")
    public void before(){
        String path = request.getServletPath();
        for(String url : urls){
            if(url.equals(path)){
                System.out.println("没有权限");
                throw new RuntimeException("没有权限");
            }
        }
        System.out.println("前置通知");
    }

    /**
     * 配置后置通知,使用在方法aspect()上注册的切入点
     */
    @After("pointCut()")
    public void after(){
        System.out.println("后置通知");
    }
}
