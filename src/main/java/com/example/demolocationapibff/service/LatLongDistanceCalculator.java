package com.example.demolocationapibff.service;

import java.math.BigDecimal;

public interface LatLongDistanceCalculator {
    BigDecimal differenceInKm(double lat1, double lon1,
                              double lat2, double lon2);
}
