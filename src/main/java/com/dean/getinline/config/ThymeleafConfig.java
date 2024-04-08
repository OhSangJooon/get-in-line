package com.dean.getinline.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

@RequiredArgsConstructor
@Configuration
public class ThymeleafConfig {

    // 타임리프 디커플 로직을 활성화 하기 위한 Bean
    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        // 디커플 로직 설정
        templateResolver.setUseDecoupledLogic(false);
        return templateResolver;
    }

}
