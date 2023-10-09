package com.example.demolocationapibff.service;

import com.example.demolocationapibff.domain.Distance;
import com.example.demolocationapibff.domain.Postcode;
import org.springframework.stereotype.Service;

@Service
public class DistanceCalculatorService {

    public Distance distanceBetween(Postcode postcode1, Postcode postcode2){
        return new Distance("33");
    }

}
