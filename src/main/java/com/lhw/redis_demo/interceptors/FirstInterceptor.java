package com.lhw.redis_demo.interceptors;

import com.lhw.redis_demo.annotations.TestAnnotation;
import com.lhw.redis_demo.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 继承HandlerInterceptorAdapter，然后复写其中的三个方法
 *      拦截器的实现原理是通过jdk的动态代理的方式实现的
 */
public class FirstInterceptor extends HandlerInterceptorAdapter {

    long start = System.currentTimeMillis();

    @Autowired
    TestService testService;

    public FirstInterceptor(){
        System.out.println("拦截器1开始初始化了");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器1前置，准备输出URL");
        System.out.println("------url------ : " + request.getRequestURL());

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        System.out.println("--method name-- : " + method.getName());
        System.out.println("-----class----- : " + method.getDeclaringClass());

        TestAnnotation annotation = method.getAnnotation(TestAnnotation.class);
        if (annotation != null){
            System.out.println("方法上存在TestAnnotation注解");
        }

        start = System.currentTimeMillis();

        System.out.println(testService.testMessage());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("处理中，准备输出URI");
        System.out.println("------uri------ : " + request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("拦截器1后置，准备输出处理时间");
        System.out.println("执行时间：" + (System.currentTimeMillis()-start));
        System.out.println("--------------------------------------");
    }

}
