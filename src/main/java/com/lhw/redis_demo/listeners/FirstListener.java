package com.lhw.redis_demo.listeners;

import com.lhw.redis_demo.model.TestEvent;

import java.util.EventListener;

public interface FirstListener extends EventListener {

    void updateEvent(TestEvent testEvent);

}
