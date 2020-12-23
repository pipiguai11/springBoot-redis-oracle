package com.lhw.redis_demo.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "USERTEST")
public class UserTest {

    @Id
    @Column(name = "CODE")
    private String code;
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ADDR")
    private String addr;
    @Column(name = "AGE")
    private int age;

}
