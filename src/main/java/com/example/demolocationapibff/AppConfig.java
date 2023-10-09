package com.example.demolocationapibff;

import com.example.demolocationapibff.service.postcodes.PostcodesIoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class AppConfig {
    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .baseUrl("api.postcodes.io")
                .build();
    }

    @Bean
    PostcodesIoService postcodesClient(WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
                        .build();
        return httpServiceProxyFactory.createClient(PostcodesIoService.class);
    }
}
