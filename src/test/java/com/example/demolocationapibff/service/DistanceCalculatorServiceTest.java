package com.example.demolocationapibff.service;

import com.example.demolocationapibff.domain.Distance;
import com.example.demolocationapibff.domain.Postcode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DistanceCalculatorServiceTest {

        @Test
        void returnsADistance(){
            DistanceCalculatorService service = new DistanceCalculatorService();
            assertEquals(new Distance("33"), service.distanceBetween(new Postcode("ME1 4FL"), new Postcode("GH1 1UL")));
        }
}