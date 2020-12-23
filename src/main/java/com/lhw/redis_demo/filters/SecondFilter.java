package com.lhw.redis_demo.filters;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  第二种配置过滤器的方式，通过注解@WebFilter
 *      urlPatterns设置过滤路径，filterName配置过滤器名
 *          过滤路径指的是URI中的路径
 *      过滤器的初始化顺序是按照名称倒序启动的
 *
 *      用了这个注解，必须要在启动类中开启才能生效
 *          在启动类中添加注解@ServletComponentScan("com.lhw.redis_demo.filters")
 */
//@WebFilter(urlPatterns = "/save" , filterName = "secondFilter")
//@Component
//@Order(2)
public class SecondFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("-------------------------------");
        System.out.println("My Second Filter Initing........");
        System.out.println("-------------------------------");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String uri = request.getRequestURI();
        StringBuffer url = request.getRequestURL();
        System.out.println("过滤器2开始");
        System.out.println("uri:" + uri);
        System.out.println("url:" + url);
        long start = System.currentTimeMillis();
        filterChain.doFilter(request,response);
        System.out.println("耗时：" + (System.currentTimeMillis()-start));
    }

    @Override
    public void destroy() {
        System.out.println("------------------------------------");
        System.out.println("My Second Filter Destorying");
        System.out.println("------------------------------------");
    }

}
