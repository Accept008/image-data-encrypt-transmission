package com.example.example.component.aes.business;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AesImageServiceTest {
    @Autowired
    AesImageService aesImageService;


    @Test
    void encrypt() {
        aesImageService.encrypt("C:/Users/mingcheng_chen/Desktop/lbxx.jpg", "C:/Users/mingcheng_chen/Desktop/encrypt/", "example");
    }

    @Test
    void decrypt() {
        aesImageService.decrypt("C:/Users/mingcheng_chen/Desktop/encrypt/example.jpg", "C:/Users/mingcheng_chen/Desktop/decrypt/", "example");
    }
}