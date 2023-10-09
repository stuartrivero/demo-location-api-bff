package com.example.demolocationapibff.controller;


import com.example.demolocationapibff.domain.Distance;
import com.example.demolocationapibff.domain.PostcodeValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/distance")
public class DistanceController {


    private final PostcodeValidator postcodeValidator;

    public DistanceController(PostcodeValidator postcodeValidator) {
        this.postcodeValidator = postcodeValidator;
    }

    @GetMapping("/calculator")
    public Distance between(@RequestParam String postcode1, @RequestParam String postcode2 ){
        //TODO move this to a validation constraint
        if(!postcodeValidator.isValid(postcode1)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "postcode1 is not a valid postcode: "+postcode1);
        }
        if(!postcodeValidator.isValid(postcode2)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "postcode2 is not a valid postcode: "+postcode2);
        }
        return new Distance("33");
    }
}
