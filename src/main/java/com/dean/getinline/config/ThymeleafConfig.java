package com.dean.getinline.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

@RequiredArgsConstructor
@Configuration
public class ThymeleafConfig {

    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        // 템플릿 파일이 위치한 경로 설정
        templateResolver.setPrefix("/WEB-INF/templates/");
        // 템플릿 파일 확장자 설정
        templateResolver.setSuffix(".html");
        // 템플릿 모드 설정
        templateResolver.setTemplateMode("HTML");
        // 디커플 로직 설정
        templateResolver.setUseDecoupledLogic(true);
        return templateResolver;
    }

}
