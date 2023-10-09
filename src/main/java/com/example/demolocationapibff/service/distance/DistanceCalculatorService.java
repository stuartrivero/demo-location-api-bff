package com.example.demolocationapibff.service.distance;

import com.example.demolocationapibff.domain.Distance;
import com.example.demolocationapibff.domain.Postcode;
import com.example.demolocationapibff.service.postcodes.PostcodeException;
import com.example.demolocationapibff.service.postcodes.PostcodesClient;
import com.example.demolocationapibff.service.postcodes.PostcodesDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientException;

import java.math.BigDecimal;

import static java.lang.String.format;

@Service
public class DistanceCalculatorService {

    private final PostcodesClient postcodesClient;

    private final LatLongDistanceCalculator latLongDistanceCalculator;

    public DistanceCalculatorService(PostcodesClient postcodesClient, LatLongDistanceCalculator latLongDistanceCalculator) {
        this.postcodesClient = postcodesClient;
        this.latLongDistanceCalculator = latLongDistanceCalculator;
    }

    public Distance distanceBetween(Postcode postcode1, Postcode postcode2) {
        PostcodesDTO postcodeInformation1 = getPostcodeInformation(postcode1);
        PostcodesDTO postcodeInformation2 = getPostcodeInformation(postcode2);

        BigDecimal distanceInKm = latLongDistanceCalculator.differenceInKm(
                postcodeInformation1.result().latitude(),
                postcodeInformation1.result().longitude(),
                postcodeInformation2.result().latitude(),
                postcodeInformation2.result().longitude()
        );
        return new Distance(distanceInKm);
    }

    private PostcodesDTO getPostcodeInformation(Postcode postcode) {
        try {
            return postcodesClient.getPostcodeInformation(postcode.value());
        } catch (WebClientException e) {
            throw new PostcodeException(format("Unable to fetch %s", postcode.value()));
        }
    }

}
