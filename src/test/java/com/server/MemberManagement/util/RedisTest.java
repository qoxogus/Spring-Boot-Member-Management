package com.server.MemberManagement.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {

    private final RedisUtil redisUtil;

    @Autowired
    public RedisTest(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Test
    public void redisTest() {
        redisUtil.deleteData("Member-Management");
        redisUtil.setData("Member-Management", "Member-Management-test");
        System.out.println(redisUtil.getData("Member-Management"));
        Assertions.assertThat(redisUtil.getData("Member-Management")).isEqualTo("Member-Management-test");
    }



}

