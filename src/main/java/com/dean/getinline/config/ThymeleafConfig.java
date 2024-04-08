package com.dean.getinline.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

@RequiredArgsConstructor
@Configuration
public class ThymeleafConfig {

    private final CustomThymeleafProperties customThymeleafProperties;
    // 타임리프 디커플 로직을 활성화 하기 위한 Bean
    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(SpringResourceTemplateResolver defaultTemplateResolver) {
        defaultTemplateResolver.setUseDecoupledLogic(customThymeleafProperties.isDecoupledLogic());

        return defaultTemplateResolver;
    }

}
