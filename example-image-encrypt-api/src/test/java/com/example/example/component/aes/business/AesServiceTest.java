package com.example.example.component.aes.business;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AesServiceTest {
    @Autowired
    AesService aesService;

    @Test
    void encrypt() throws Exception {
        String context = "hello world";
        System.out.println("明文 => " + context);

        byte[] originByte = context.getBytes("UTF-8");
        String encryptText = aesService.byteToHex(aesService.encrypt(originByte));
        System.out.println("加密结果(十六进制字符串) => " + encryptText);
    }

    @Test
    void decrypt() throws Exception {
        String context = "hello world";
        System.out.println("明文 => " + context);

        byte[] originByte = context.getBytes("UTF-8");
        String encryptText = aesService.byteToHex(aesService.encrypt(originByte));
        System.out.println("加密结果(十六进制字符串) => " + encryptText);

        byte[] encryptByte = aesService.hexToByte(encryptText);
        String decryptText = new String(aesService.decrypt(encryptByte));
        System.out.println("解密 => " + decryptText);
    }



}