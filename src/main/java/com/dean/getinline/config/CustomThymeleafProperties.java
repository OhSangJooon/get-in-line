package com.dean.getinline.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("spring.custom.thymeleaf")
public class CustomThymeleafProperties {
    /**
     * Thymeleaf 3 Decoupled Logic 활성화
     */
    private final boolean decoupledLogic;
}
