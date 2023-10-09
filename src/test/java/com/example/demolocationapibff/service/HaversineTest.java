package com.example.demolocationapibff.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HaversineTest {

    public static Stream<Arguments> provideLatLongs() {
        return Stream.of(
                Arguments.of( 51.510357,-0.116773,  38.889931, -77.009003,"5897.658288856053"),
                Arguments.of( 47.6788206, -122.3271205, 47.6788206, -122.5271205,"14.97319048158622")
        );
    }

    @ParameterizedTest
    @MethodSource("provideLatLongs")
    void distanceCalculation(double lat1, double lon1, double lat2, double lon2, String expectedDist){
        Haversine haversine = new Haversine();

        BigDecimal differenceInKm = haversine.differenceInKm(lat1, lon1, lat2, lon2);
        assertEquals(new BigDecimal(expectedDist), differenceInKm);
    }

}