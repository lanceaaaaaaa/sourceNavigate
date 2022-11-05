package com.luban.test.mail;

import com.luban.common.EmailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class BlogApiApplicationTests {
    @Autowired
    private EmailUtil emailUtil;

    @Test
    void contextLoads() {
    }

    @Test
    void sendStringEmail() {
        // 测试文本邮件发送（无附件）
        String to = "1351012740@qq.com"; // 这是个假邮箱，写成你自己的邮箱地址就可以
        String title = "文本邮件发送测试";
        String content = "文本邮件发送测试";
        emailUtil.sendMessage(to, title, content);
    }

    @Test
    void sendFileEmail() {
        // 测试单个附件邮件发送
        String to = "135472099@qq.com"; // 这是个假邮箱，写成你自己的邮箱地址就可以
        String title = "单个附件邮件发送测试";
        String content = "单个附件邮件发送测试";
        File file = new File("D:\\GCH\\Typora\\Linux中常用的查看系统相关信息命令.md");
        emailUtil.sendMessageCarryFile(to, title, content, file);
    }

    @Test
    void sendFilesEmail() {
        // 测试多个附件邮件发送
        String to = "135472099@qq.com"; // 这是个假邮箱，写成你自己的邮箱地址就可以
        String title = "多个附件邮件发送测试";
        String content = "多个附件邮件发送测试";
        File[] files = new File[2];
        files[0] = new File("C:\\Users\\root\\Desktop\\配置邮箱\\1.png");
        files[1] = new File("C:\\Users\\root\\Desktop\\配置邮箱\\2.png");
    }

}