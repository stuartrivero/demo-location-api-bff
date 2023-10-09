package com.example.demolocationapibff.domain;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Distance(BigDecimal km) {

    @JsonGetter("km")
    public BigDecimal getKm(){
        return km.setScale(2, RoundingMode.DOWN);
    }

}
