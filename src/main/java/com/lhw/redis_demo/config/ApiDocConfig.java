package com.lhw.redis_demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

@Configuration
@EnableOpenApi
//添加如下注解无效，不是这个方法无效，而是默认会自动添加一个swagger配置，需要手动在配置文件中关闭
//@ConditionalOnProperty(name = "swagger.enabled",havingValue = "true")
public class ApiDocConfig {

    @Value("${swagger.enabled}")
    private boolean enabled;

    @Bean
    public Docket adminApi(){
        return new Docket(DocumentationType.OAS_30)
                .enable(enabled)
                .groupName("Admin API")
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .paths(paths())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

    private Predicate<String> paths(){
        return PathSelectors.regex("^/(?!error).*$");
    }

    private ApiInfo apiInfo(){
        Contact contact = new Contact("LHW", "https://github.com/DistX", "953522392@qq.com");
        return new ApiInfoBuilder()
                .title("测试系统")
                .description("后台API文档")
                .contact(contact)
                .version("1.0")
                .build();
    }
}
