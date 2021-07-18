package com.example.example.component.aes.business;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * AES CBC图片加密
 * 注：目标图片所在文件夹允许不存在
 *
 * @author jiacheng.chen
 */
@Slf4j
@Service
public class AesImageService {
    @Autowired
    private AesService aesService;

    /**
     * 加密图片
     *
     * @param sourceImagePath   源图片地址 eg: /home/chen/data/img/icon.png
     * @param targetImageFolder 目标图片存储文件夹 eg: /home/chen/data/img/encrypt/
     * @param targetName        目标图片文件名 eg: example
     */
    public void encrypt(String sourceImagePath, String targetImageFolder, String targetName) {
        try {
            // 读取图片文件内容
            File imageFile = new File(sourceImagePath);
            String extName = getExtName(imageFile.getName());
            if (StringUtils.isEmpty(extName)) {
                throw new RuntimeException("The source image file extName is null ...");
            }
            byte[] imageBytes = FileUtils.readFileToByteArray(imageFile);

            // 加密图片文件内容
            byte[] plainBytes = aesService.encrypt(imageBytes);

            // 生成加密图片
            StringBuilder targetImagePath = new StringBuilder().append(targetImageFolder).append(targetName).append(extName);
            FileUtils.writeByteArrayToFile(new File(targetImagePath.toString()), plainBytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("encrypt image error ...");
        }
    }

    /**
     * 解密图片
     *
     * @param encryptImagePath  加密图片地址 eg: /home/chen/data/img/icon.png
     * @param targetImageFolder 目标图片存储文件夹 eg: /home/chen/data/img/encrypt/
     * @param targetName        目标图片文件名 eg: example
     */
    public void decrypt(String encryptImagePath, String targetImageFolder, String targetName) {
        try {
            // 读取图片文件内容
            File encryptImgFile = new File(encryptImagePath);
            String extName = getExtName(encryptImgFile.getName());
            if (StringUtils.isEmpty(extName)) {
                throw new RuntimeException("The encrypt image file extName is null ...");
            }
            byte[] imageBytes = FileUtils.readFileToByteArray(encryptImgFile);

            // 解密图片文件内容
            byte[] plainBytes = aesService.decrypt(imageBytes);

            // 生成解密图片
            StringBuilder targetImagePath = new StringBuilder().append(targetImageFolder).append(targetName).append(extName);
            FileUtils.writeByteArrayToFile(new File(targetImagePath.toString()), plainBytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("decrypt image error ...");
        }
    }

    /**
     * 获取文件扩展名
     * 注: 可能为null
     *
     * @param fileName 文件名 eg: icon.png
     * @return 后缀名 eg: eg: .png
     */
    private static String getExtName(String fileName) {
        int index = fileName.lastIndexOf(".");

        if (index == -1) {
            return null;
        }
        String result = fileName.substring(index);
        return result;
    }
}