package com.example.demolocationapibff.service.distance;

import com.example.demolocationapibff.domain.Distance;
import com.example.demolocationapibff.domain.Postcode;
import com.example.demolocationapibff.service.postcodes.PostcodesDTO;
import com.example.demolocationapibff.service.postcodes.PostcodesClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DistanceCalculatorService {

    private final PostcodesClient postcodesClient;

    private final LatLongDistanceCalculator latLongDistanceCalculator;

    public DistanceCalculatorService(PostcodesClient postcodesClient, LatLongDistanceCalculator latLongDistanceCalculator) {
        this.postcodesClient = postcodesClient;
        this.latLongDistanceCalculator = latLongDistanceCalculator;
    }

    public Distance distanceBetween(Postcode postcode1, Postcode postcode2){
        PostcodesDTO postcodeInformation = postcodesClient.getPostcodeInformation(postcode1.value());
        PostcodesDTO postcodeInformation2 = postcodesClient.getPostcodeInformation(postcode2.value());

        if(postcodeInformation.status() != 200 ){
            throw new IllegalArgumentException(postcodeInformation.error());
        }

        if(postcodeInformation2.status() != 200 ){
            throw new IllegalArgumentException(postcodeInformation2.error());
        }

        BigDecimal distanceInKm = latLongDistanceCalculator.differenceInKm(
                postcodeInformation.result().latitude(),
                postcodeInformation.result().longitude(),
                postcodeInformation2.result().latitude(),
                postcodeInformation2.result().longitude()
        );
        return new Distance(distanceInKm);
    }

}
