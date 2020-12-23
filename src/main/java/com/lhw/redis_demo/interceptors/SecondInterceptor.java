package com.lhw.redis_demo.interceptors;

import com.lhw.redis_demo.constans.GlobalConstans;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 继承HandlerInterceptorAdapter，然后复写其中的三个方法
 *      拦截器的实现原理是通过jdk的动态代理的方式实现的
 */
public class SecondInterceptor extends HandlerInterceptorAdapter {

    public SecondInterceptor(){
        System.out.println("拦截器2开始初始化");
    }

    long start = System.currentTimeMillis();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器2前置---------------------------");
        System.out.println("url: " + request.getRequestURL());
        String path = request.getServletPath();
        start = System.currentTimeMillis();
        //这里可以做一层判断，看看是否需要拦截
        if (path.matches(GlobalConstans.NO_INTERCEPTOR_PATH)) {
            //不需要的拦截直接过
            return true;
        } else {
            // 这写你拦截需要干的事儿，比如取缓存，SESSION，权限判断等
            System.out.println("====================================");
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("处理中--------------------------");
        System.out.println("uri : " + request.getRequestURI());
        System.out.println("---------------------------------------------------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("拦截器2后置--------------------------");
        System.out.println("执行时间：" + (System.currentTimeMillis()-start));
    }

}
