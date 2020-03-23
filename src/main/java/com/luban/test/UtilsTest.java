package com.luban.test;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UtilsTest {

    @Test
    public void test1(){
        String uuid = UUID.randomUUID().toString().replace("-","");
        System.out.println(uuid);
    }

}
