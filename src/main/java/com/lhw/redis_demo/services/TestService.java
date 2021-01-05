package com.lhw.redis_demo.services;

import com.lhw.redis_demo.dao.TestRepository;
import com.lhw.redis_demo.model.UserTest;
import com.lhw.redis_demo.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TestService {

    @Autowired
    TestRepository testRepository;

    @Autowired
    RedisUtil redisUtil;

    @Cacheable(cacheNames = {"user"},key = "#code")
    public UserTest getUserByCode(String code){
        System.out.println("执行了查询操作");
        return testRepository.findById(code).orElse(null);
    }

    @Cacheable(value = "allUserInfo")
    public List<UserTest> getAll(){
        System.out.println("执行了查询操作");
        return testRepository.findAll();
    }

    @Cacheable(cacheNames = {"user"},key = "#p0.code")
    public UserTest saveUser(UserTest u){
        System.out.println("执行了保存操作");
        return testRepository.save(u);
    }

    public String testMessage(){
        return "result";
    }

    /**
     * 同时在两个分组（test和test2）中缓存这个方法的返回值
     * 以num作为key，格式为test::num和test2::num
     * @param num
     * @return
     */
    @Cacheable(cacheNames = {"test","test2"}, key = "#p0")
    public String testTime(int num){
        return LocalDate.now().lengthOfYear() + " " + LocalDate.now().lengthOfMonth();
    }

    public void saveListData(List data){

        redisUtil.lSet("list",data,60000);
        System.out.println("保存list");
    }

}
