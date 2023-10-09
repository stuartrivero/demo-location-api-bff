package com.example.demolocationapibff.domain;

import org.springframework.stereotype.Component;

@Component
public class PostcodeValidator {

    //This is not a real validation - it would need  to be changed to be real life
    public boolean isValid(String postcode) {
        return postcode.length() < 8 && postcode.contains(" ");
    }
}
