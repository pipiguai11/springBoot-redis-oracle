package com.lhw.redis_demo.config;

import com.lhw.redis_demo.filters.FirstFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


//@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean registeFilter(){
        FilterRegistrationBean register = new FilterRegistrationBean();
        register.setFilter(new FirstFilter());
        register.setName("myFirstFilter");
        register.setOrder(1);
        List<String> patterns = new ArrayList<>();
        patterns.add("/get/**");    //做过滤，对于所有的uri路径做判断，路径是get/开头的就会过滤到
        register.setUrlPatterns(patterns);
        return register;
    }

}
