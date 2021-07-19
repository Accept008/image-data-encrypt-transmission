package com.example.example.image.controller;

import com.example.example.image.dto.ImageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author jiacheng.chen
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    /**
     * 图片分页列表
     *
     * @return
     */
    @GetMapping("/imageList")
    public List<ImageResponse> pageImageList() {
        ImageResponse one = new ImageResponse();
        one.setId(UUID.randomUUID().toString());
        one.setName("蜡笔小新");
        one.setUrl("http://127.0.0.1/example.jpg");
        one.setRemark("-");

        List<ImageResponse> imageResponses = Arrays.asList(one);
        return imageResponses;
    }

}
