package com.example.demolocationapibff.service.postcodes;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

@HttpExchange(url = "/postcodes", accept = "application/json", contentType = "application/json")
public interface PostcodesClient {
    @GetExchange("/{postcode}")
    Mono<PostcodesDTO> getPostcodeInformation(@PathVariable String postcode);

}