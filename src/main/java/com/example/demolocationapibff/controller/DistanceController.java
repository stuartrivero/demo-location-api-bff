package com.example.demolocationapibff.controller;


import com.example.demolocationapibff.domain.Distance;
import com.example.demolocationapibff.domain.Postcode;
import com.example.demolocationapibff.domain.PostcodeSearch;
import com.example.demolocationapibff.service.database.PostcodeSearchRepository;
import com.example.demolocationapibff.service.distance.DistanceCalculatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/distance")
public class DistanceController {

    private final DistanceCalculatorService distanceCalculatorService;
    private final PostcodeSearchRepository postcodeSearchRepository;

    public DistanceController(DistanceCalculatorService distanceCalculatorService, PostcodeSearchRepository postcodeSearchRepository) {
        this.distanceCalculatorService = distanceCalculatorService;
        this.postcodeSearchRepository = postcodeSearchRepository;
    }

    @GetMapping("/calculator")
    public Distance between(@RequestParam Postcode postcode1, @RequestParam Postcode postcode2) {

        return distanceCalculatorService.distanceBetween(postcode1, postcode2);
    }

    @GetMapping("/history")
    public List<PostcodeSearch> getAllSearches() {
        return postcodeSearchRepository.findAll();
    }
}
