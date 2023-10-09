package com.example.demolocationapibff.controller;

import com.example.demolocationapibff.domain.PostcodeValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DistanceController.class)
@Import(PostcodeValidator.class)
class DistanceControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Test
    public void badRequestWhenFirstIsNotAPostcode() throws Exception {
        this.mockMvc.perform(get("/distance/calculator?postcode1=ME1203TN&postcode2=W1A 1AA"))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void badRequestWhenSecondIsNotAPostcode() throws Exception {
        this.mockMvc.perform(get("/distance/calculator?postcode1=ME1 3TN&postcode2=W1A1AA"))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void canAdd() throws Exception {
        this.mockMvc.perform(get("/distance/calculator?postcode1=ME1 3TN&postcode2=W1A 1AA"))
                .andExpect(status().isOk())
                .andExpect(content().string("""
                        {"km":"33"}\
                        """))
        ;
    }
}