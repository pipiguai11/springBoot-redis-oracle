package com.lhw.redis_demo.model;

import java.util.EventObject;

public class TestEvent extends EventObject {

    private Object source;
    private int testNum;
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public TestEvent(Object source,int testNum) {
        super(source);  //调用父类的构造器，其中做了一层判断，判断是否存在事件源，不存在则抛出异常
        this.source = source;
        this.testNum = testNum;
    }

    public int getTestNum(){
        return testNum;
    }

}
