package com.lhw.redis_demo.dao;

import com.lhw.redis_demo.model.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<UserTest,String> {

}
