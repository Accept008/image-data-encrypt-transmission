package com.example.example.component.aes;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * AES 属性类
 *
 * @author jiacheng.chen
 */
@Data
@ConfigurationProperties(prefix = "aes")
public class AesProperties {

    private String key;

    /**
     * IV must be 16 bytes long
     */
    private String iv;
}
