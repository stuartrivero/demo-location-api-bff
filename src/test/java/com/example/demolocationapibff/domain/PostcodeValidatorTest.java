package com.example.demolocationapibff.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PostcodeValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "AA9A 9AA",
            "A9A 9AA",
            "A9 9AA",
            "A99 9AA",
            "AA9 9AA",
            "AA99 9AA",
            "RH28EY",
    })
    void validPostcodes(String postcode){
            assertTrue(new PostcodeValidator().isValid(postcode));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "AA9A 9AAA",
            "A9A 9AAA",
            "A9 9AAA",
            "A99 9AAA",
            "AA9 9AAA",
            "AA99 9AAA",
            "AA99 9A",
            "rh2 8ey",
            "sm58ee",
            "sm5 3er",
    })
    void invalidPostcodes(String postcode){
            assertFalse(new PostcodeValidator().isValid(postcode));
    }
    @ParameterizedTest
    @ValueSource(strings = {
            "ASCN 1ZZ",
            "BBND 1ZZ",
            "BIQQ 1ZZ",
            "FIQQ 1ZZ",
            "GX11 1AA",
            "PCRN 1ZZ",
            "SIQQ 1ZZ",
            "STHL 1ZZ",
            "TDCU 1ZZ",
            "TKCA 1ZZ",
    })
    void crownDependencyValidPostcodes(String postcode){
            assertTrue(new PostcodeValidator().isValid(postcode));
    }


}