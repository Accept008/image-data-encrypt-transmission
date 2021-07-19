package com.example.example.image.dto;

import lombok.Data;

/**
 * 图片响应参数
 *
 * @author jiachen.chen
 **/
@Data
public class ImageResponse {

    private String id;

    private String name;

    private String url;

    private String remark;
}
