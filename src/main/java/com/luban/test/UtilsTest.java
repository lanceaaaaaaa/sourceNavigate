package com.luban.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;
import java.util.UUID;

public class UtilsTest {

    @Test
    public void test1(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println(encoder.encode("!Aa15627276321"));
    }

    @Test
    public void test2(){
        Random random = new Random();
        System.out.println(Integer.toString(0 + random.nextInt(9000)));
    }
}
