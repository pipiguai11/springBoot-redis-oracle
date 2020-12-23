package com.lhw.redis_demo.listeners;

import com.lhw.redis_demo.model.TestEvent;

import java.util.ArrayList;
import java.util.List;

public class ListenerExecuter {

    private List<FirstListener> firstListeners = new ArrayList<>();

    public void addFirstListener(FirstListener firstListener){
        this.firstListeners.add(firstListener);
    }

    public void remoteFirstListener(FirstListener firstListener){
        this.firstListeners.remove(firstListener);
    }

    public void innerExecuter(){
        for (int i=0 ; i<5 ; i++){
            System.out.println("我是testNum，我的值为：" + i);
            int finalI = i;
            firstListeners.forEach(f -> f.updateEvent(new TestEvent(this,finalI)));
            System.out.println();
            System.out.println("---------------------------------------");
        }
    }

    public void innerExecuter2(TestEvent testEvent){
        firstListeners.forEach(f -> f.updateEvent(testEvent));
    }

}
