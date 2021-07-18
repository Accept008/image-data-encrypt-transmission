package com.example.example.component.aes;

import com.example.example.component.aes.business.AesService;
import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AES 配置类
 *
 * @author jiachen.chen
 **/
@Data
@Configuration
@EnableConfigurationProperties({AesProperties.class})
public class AesConfiguration {
    private final AesProperties aesProperties;

    @Bean
    public AesService getAesService() {
        return new AesService(aesProperties);
    }
}
