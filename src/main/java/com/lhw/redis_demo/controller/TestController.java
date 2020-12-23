package com.lhw.redis_demo.controller;

import com.lhw.redis_demo.annotations.TestAnnotation;
import com.lhw.redis_demo.model.UserTest;
import com.lhw.redis_demo.services.TestService;
import com.lhw.redis_demo.utils.RedisUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Api(tags = "TestController",description = "测试类")
@RestController
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    RedisUtil redisUtil;

    @GetMapping("get/one/{code}")
    public String get(@PathVariable String code){
        UserTest u = testService.getUserByCode(code);
        System.out.println(u);
        return "success";
    }

    @GetMapping("get/all")
    @TestAnnotation
    public String getAll(){
        List<UserTest> userTests = testService.getAll();
        System.out.println(userTests);
        return "success";
    }

    @PostMapping("save")
    public String save(){
        UserTest u = new UserTest();
        Random random = new Random();
        int num = random.nextInt(1000);
        u.setCode(UUID.randomUUID().toString());
        u.setUserName("lhw" + num);
        u.setAge(num);
        u.setPassword("12345");
        u.setAddr("asdad");
        testService.saveUser(u);
        return "success";
    }

    @PostMapping("save/v2")
    public String save2(){
        return "success";
    }

    @PutMapping("update/{code}")
    public String update(@PathVariable String code){
        UserTest u = new UserTest();
        Random random = new Random();
        int num = random.nextInt(1000);
        u.setCode(code);
        u.setUserName("lhw" + num);
        u.setAge(num);
        u.setPassword("12345");
        u.setAddr("asdad");

        /**
         * 做更新前先判断redis中是否存在这个缓存，如果存在则删除，更新完之后再缓存进去，保证数据一致性
         *      注意，如果在缓存的时候设置了cachename的话，查询的时候也要带上，不然无法查到的，如下的user::(这个可以到redis中看看它的key就知道了)
         */
        if (!Objects.isNull(redisUtil.get("user::"+code))){
            redisUtil.del("user::"+code);
            System.out.println("删除了redis缓存");
        }
        System.out.println(testService.saveUser(u));
        return "success";
    }

    @GetMapping("message")
    public String getMessage(){
        return testService.testMessage();
    }

}
