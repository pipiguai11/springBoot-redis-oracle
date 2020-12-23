package com.lhw.redis_demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhw.redis_demo.listeners.FirstListener;
import com.lhw.redis_demo.listeners.ListenerExecuter;
import com.lhw.redis_demo.model.Person;
import com.lhw.redis_demo.model.TestEvent;
import com.lhw.redis_demo.services.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class RedisDemoApplicationTests {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private TestService testService;

    @Test
    void contextLoads() {
    }

    /**
     * 使用ObjectMapper做json和对象的转换
     * @throws JsonProcessingException
     */
    @Test
    void test1() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Person p = new Person("lin",18,"guangdong");
        String result = om.writeValueAsString(p);

        System.out.println(result);
        System.out.println("parse：" + om.readValue(result, Person.class));
    }

    /**
     *
     */
    @Test
    void test2(){
        Person p = new Person("lin",18,"guangdong");
        Person p2 = new Person();
        BeanUtils.copyProperties(p,p2);
        System.out.println(p2);
    }

    @Test
    void test3(){
        Person p = new Person("lhw",18,"aa");
        redisTemplate.opsForValue().set("lin",p);
        redisTemplate.opsForValue().set("han",p,60, TimeUnit.SECONDS);
    }

    @Test
    void test4() throws JsonProcessingException {
        String result = redisTemplate.opsForValue().get("lin").toString();
        System.out.println(result);
//        Person p2 = (Person)redisTemplate.opsForValue().get("han");
//        System.out.println(p);
//        System.out.println(p2);
    }

    @Autowired
    DataSource dataSource;

    @Test
    void connect() throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from USERTEST");
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()){
            System.out.println(rs.getString(1));
            System.out.println(rs.getString(2));
            System.out.println(rs.getString(3));
            System.out.println(rs.getString(4));
            System.out.println(rs.getInt(5));
        }
    }

    @Test
    void testListener(){
        ListenerExecuter le = new ListenerExecuter();
        //lamda表达式创建一个新的监听者
        FirstListener firstListener1 = testEvent -> System.out.println("i am listener1 , 我知道事件触发了，值为：" + testEvent.getTestNum());
        //正常逻辑创建一个监听者，同时实现其中的监听事件触发后执行的代码
        FirstListener firstListener2 = new FirstListener() {
            @Override
            public void updateEvent(TestEvent testEvent) {
                System.out.println("i am listener2 , 我也知道了" + testEvent.getTestNum());
            }
        };
        //先给事件源注册监听者，一个事件源有多个监听者，
        le.addFirstListener(firstListener1);
        le.addFirstListener(firstListener2);
        //执行数据源的执行方法，具体逻辑可自定义，其中会自动去调用监听者的触发方法
        le.innerExecuter();
    }

    @Test
    void testListener2(){
        ListenerExecuter le = new ListenerExecuter();
        FirstListener firstListener1 = testEvent -> System.out.println("i am listener1 , 我知道事件触发了，值为：" + testEvent.getTestNum());
        FirstListener firstListener2 = testEvent -> System.out.println("i am listener2 , 我也知道了" + testEvent.getTestNum());
        FirstListener firstListener3 = testEvent -> System.out.println("i am listener3 , 我也知道了" + testEvent.getTestNum());
        le.addFirstListener(firstListener1);
        le.addFirstListener(firstListener2);
        le.addFirstListener(firstListener3);
        le.innerExecuter2(new TestEvent(le,1));
        le.remoteFirstListener(firstListener3);
        le.innerExecuter();
    }

    @Test
    public void testOther(){
        System.out.println(testService.testTime(1));
        List<String> list = new ArrayList<>();
        if (list.isEmpty()){
            System.out.println("no element in this list");
        }
//        Map<String,String> map = new HashMap<>(16);
//        Set<Map.Entry<String,String>> result =  map.entrySet();
    }

}
