package com.example.demolocationapibff.controller;


import com.example.demolocationapibff.domain.Distance;
import com.example.demolocationapibff.domain.Postcode;
import com.example.demolocationapibff.service.distance.DistanceCalculatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/distance")
public class DistanceController {

    private final DistanceCalculatorService distanceCalculatorService;

    public DistanceController(DistanceCalculatorService distanceCalculatorService) {
        this.distanceCalculatorService = distanceCalculatorService;
    }

    @GetMapping("/calculator")
    public Distance between(@RequestParam Postcode postcode1, @RequestParam Postcode postcode2) {

        return distanceCalculatorService.distanceBetween(postcode1, postcode2);
    }
}
