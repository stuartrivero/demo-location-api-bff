package com.example.demolocationapibff.controller;

import com.example.demolocationapibff.domain.Distance;
import com.example.demolocationapibff.domain.Postcode;
import com.example.demolocationapibff.domain.PostcodeSearch;
import com.example.demolocationapibff.domain.PostcodeValidator;
import com.example.demolocationapibff.service.database.PostcodeSearchRepository;
import com.example.demolocationapibff.service.distance.DistanceCalculatorService;
import com.example.demolocationapibff.service.postcodes.PostcodesConfiguration;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DistanceController.class)
@Import({PostcodeValidator.class, PostcodesConfiguration.class})
class DistanceControllerTest {

    public static final Postcode POSTCODE_1 = new Postcode("ME1 3TN");
    public static final Postcode POSTCODE_2 = new Postcode("W1A 1AA");
    public static final Postcode INVALID_POSTCODE = new Postcode("W1A 1A");


    @MockBean
    DistanceCalculatorService service;

    @MockBean
    PostcodeSearchRepository postcodeSearchRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void badRequestWhenFirstIsNotAPostcode() throws Exception {
        this.mockMvc.perform(get(distanceCalculatorUrl(INVALID_POSTCODE, POSTCODE_2)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void badRequestWhenSecondIsNotAPostcode() throws Exception {
        this.mockMvc.perform(get(distanceCalculatorUrl(POSTCODE_1, INVALID_POSTCODE)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void canCalculateTheDistance() throws Exception {
        when(service.distanceBetween(POSTCODE_1, POSTCODE_2)).thenReturn(new Distance(BigDecimal.valueOf(101)));
        this.mockMvc.perform(get(distanceCalculatorUrl(POSTCODE_1, POSTCODE_2)))
                .andExpect(status().isOk())
                .andExpect(content().string("""
                        {"km":101.00}\
                        """));
    }

    @NotNull
    private static String distanceCalculatorUrl(Postcode postcode1, Postcode postcode2) {
        return "/distance/calculator?postcode1=" + postcode1.value() + "&postcode2=" + postcode2.value();
    }

    @Test
    public void canSeeHistoryOfSearch() throws Exception {
        when(postcodeSearchRepository.findAll()).thenReturn(List.of(PostcodeSearch.from(POSTCODE_1, POSTCODE_2, new BigDecimal("101"))));
        when(service.distanceBetween(POSTCODE_1, POSTCODE_2)).thenReturn(new Distance(BigDecimal.valueOf(101)));
        this.mockMvc.perform(get(distanceCalculatorUrl(POSTCODE_1, POSTCODE_2)))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/distance/history"))
                .andExpect(status().isOk())
                .andExpect(content().string("""
                        [{"id":0,"postcode1":"ME1 3TN","postcode2":"W1A 1AA","distance":101}]\
                        """));

    }
}