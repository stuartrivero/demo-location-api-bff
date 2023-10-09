package com.example.demolocationapibff.service.postcodes;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/postcodes", accept = "application/json", contentType = "application/json")
public interface PostcodesIoService {
    @GetExchange("/{postcode}")
    PostcodesDTO getPostcodeInformation(@PathVariable String postcode);

}