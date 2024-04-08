package com.dean.getinline.config;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
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
