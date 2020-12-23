package com.lhw.redis_demo.filters;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;

/**
 * 第一种配置过滤器的方式，通过实现Filter接口
 *      然后通过配置一个FilterRegistrationBean实现Bean注入容器，然后就可以用了，具体的可以看看FilterConfig类
 *
 *  注意：
 *      其实过滤器的原理是通过函数回调的方式实现过滤的
 *      可以看到doFilter方法中存在一个FilterChain形参，这个形参其实是一个ApplicationFilterChain对象
 *      查看ApplicationFilterChain对象可以看到，其中也存在一个doFilter方法，会拿到所有的Filter，遍历获取每一个Filter，然后调用它的doFilter方法，同时将自身作为形参传入进去
 *      在我们自定义的过滤器处理逻辑执行完毕后，会回调这个ApplicationFilterChain对象的doFilter方法。
 *      从而实现过滤器嵌套生效
 *
 *  我们这个过滤器的定义只需要实现Filter类即可，生效的化可以直接通过@Component注解将其注入到容器中
 *      但是这种方式是针对全局的，也就是所有的路径都会做过滤
 *  还有一种生效方式是通过配置类使之生效，通过向容器中注入一个FilterRegistrationBean对象，并指定过滤路径
 *
 */
@Component
@Order(1)
public class FirstFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("-------------------------------");
        System.out.println("My First Filter Initing........");
        System.out.println("-------------------------------");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        String uri = request.getRequestURI();
        StringBuffer url = request.getRequestURL();
        System.out.println("过滤器1开始");
        System.out.println("uri：" + uri);
        System.out.println("url：" + url);

        //获取表单form-data请求中所有具有name属性的表单对象的参数（post）
        /**
         * http://localhost:8900/redisDemo/get/one/c9a3d98a-fde9-4b78-ab04-9c1c326b5874?name=lhw
         * 这个请求会将name这个参数打印出来（params-name : lhw）
         */
        Enumeration<String> em = request.getParameterNames();
        while (em.hasMoreElements()){
            String name = em.nextElement();
            //通过request.getParameter()方法获取表单控件的value值.
            String value = request.getParameter(name);
            System.out.println("params-" + name + " : " + value);
        }

        //这个可以获取到流对象，然后将其打印出来
        ServletInputStream servletInputStream = request.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(servletInputStream));
        StringBuffer sb = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null){
            sb.append(line);
        }
        System.out.println("inputString : " + sb);

        long start = System.currentTimeMillis();
        filterChain.doFilter(request,response);
        System.out.println("执行结束");
        System.out.println("耗时：" + (System.currentTimeMillis()-start));
    }

    @Override
    public void destroy() {
        System.out.println("------------------------------------");
        System.out.println("My First Filter Destorying");
        System.out.println("------------------------------------");
    }

}
