package com.example.demolocationapibff;

import com.example.demolocationapibff.controller.StringToPostcodeConverter;
import com.example.demolocationapibff.service.postcodes.PostcodesClient;
import com.example.demolocationapibff.service.postcodes.PostcodesConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.format.FormatterRegistry;

@Configuration
@EnableConfigurationProperties
public class AppConfig implements WebMvcConfigurer {
    @Bean
    WebClient webClient(PostcodesConfiguration postcodesConfiguration) {
        return WebClient.builder()
                .baseUrl(postcodesConfiguration.getBaseUrl())
                .build();
    }

    @Bean
    PostcodesClient postcodesClient(WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
                        .build();
        return httpServiceProxyFactory.createClient(PostcodesClient.class);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToPostcodeConverter());
    }
}
