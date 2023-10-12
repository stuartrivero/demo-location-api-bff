package com.example.demolocationapibff.controller;

import com.example.demolocationapibff.domain.Postcode;
import com.example.demolocationapibff.domain.PostcodeValidator;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StringToPostcodeConverter implements Converter<String, Postcode> {

    @Override
    public Postcode convert(String candidatePostcode) {
        if (!new PostcodeValidator().isValid(candidatePostcode)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "postcode1 is not a valid postcode: " + candidatePostcode);
        }
        return new Postcode(candidatePostcode);
    }
}
