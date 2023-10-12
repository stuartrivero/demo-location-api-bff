package com.example.demolocationapibff.controller;

import com.example.demolocationapibff.domain.Distance;
import com.example.demolocationapibff.domain.Postcode;
import com.example.demolocationapibff.domain.PostcodeValidator;
import com.example.demolocationapibff.service.distance.DistanceCalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DistanceController.class)
@Import({PostcodeValidator.class})
class DistanceControllerTest {

    @MockBean
    DistanceCalculatorService service;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void badRequestWhenFirstIsNotAPostcode() throws Exception {
        this.mockMvc.perform(get("/distance/calculator?postcode1=ME1203TN&postcode2=W1A 1AA"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void badRequestWhenSecondIsNotAPostcode() throws Exception {
        this.mockMvc.perform(get("/distance/calculator?postcode1=ME1 3TN&postcode2=W1A 1A"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void canCalculateTheDistance() throws Exception {
        when(service.distanceBetween(new Postcode("ME1 3TN"), new Postcode("W1A 1AA"))).thenReturn(new Distance(BigDecimal.valueOf(101)));
        this.mockMvc.perform(get("/distance/calculator?postcode1=ME1 3TN&postcode2=W1A 1AA"))
                .andExpect(status().isOk())
                .andExpect(content().string("""
                        {"km":101.00}\
                        """));
    }
}