package com.lhw.redis_demo.config;

import com.lhw.redis_demo.interceptors.FirstInterceptor;
import com.lhw.redis_demo.interceptors.SecondInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 自定义的拦截器配置类
 *      注意一定要添加@Configuration注解，保证项目启动时这个类会被配置进容器中，这样才能注册拦截器并使之生效
 */
//@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * 在这里手动注入bean时有原因的
     *      如果我们需要在拦截器中调用service服务的话，直接注入是拿不到service对象的
     *      因为加载顺序的问题，拦截器是在springContext加载之前就加载好了，而bean又是被spring管理的
     *      所以，如果我们想要在拦截器中注入service服务bean的话，就必须保证这个bean已经存在了
     *      这时，我们可以在注册拦截器之前，手动注入一个拦截器bean
     * @return
     */
    @Bean
    public FirstInterceptor firstInterceptor(){
        return new FirstInterceptor();
    }

    /**
     * 复写addInterceptors方法，并注册我们自定义的拦截器进去
     *      自定义拦截器的同时设置拦截路径
     *
     *     注意了：下面的order设置的不是初始化的顺序，而是执行拦截的顺序
     *          在控制台中看到的初始化的打印信息不要误会，去调一下接口触发一下拦截器就可以看到效果了（前提是同时触发两个拦截器）
     *
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(new FirstInterceptor()).addPathPatterns("/get/**");
//        registry.addInterceptor(new SecondInterceptor()).addPathPatterns("/save").order(0);

        registry.addInterceptor(firstInterceptor()).addPathPatterns("/get/**").order(1);
        registry.addInterceptor(new SecondInterceptor()).addPathPatterns("/get/**").order(0);  //如果这里不带右面的通配符，则只会拦截这一个请求路径，order用于指定加载顺序

    }

}
