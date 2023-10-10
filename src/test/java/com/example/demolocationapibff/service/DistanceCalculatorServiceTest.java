package com.example.demolocationapibff.service;

import com.example.demolocationapibff.domain.Distance;
import com.example.demolocationapibff.domain.Postcode;
import com.example.demolocationapibff.service.distance.DistanceCalculatorService;
import com.example.demolocationapibff.service.distance.LatLongDistanceCalculator;
import com.example.demolocationapibff.service.postcodes.PostcodeException;
import com.example.demolocationapibff.service.postcodes.PostcodesClient;
import com.example.demolocationapibff.service.postcodes.PostcodesDTO;
import com.example.demolocationapibff.service.postcodes.ResultDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DistanceCalculatorServiceTest {

    public static final Postcode POSTCODE_1 = new Postcode("ME1 4FL");
    public static final Postcode POSTCODE_2 = new Postcode("GH1 1UL");
    @Mock
    PostcodesClient postcodesClient;

    @Mock
    LatLongDistanceCalculator latLongDistanceCalculator;

    @Test
    void returnsADistance() {
        when(postcodesClient.getPostcodeInformation(POSTCODE_1.value())).thenReturn(Mono.just(new PostcodesDTO(200, new ResultDTO(1.0, 2.0), null)));
        when(postcodesClient.getPostcodeInformation(POSTCODE_2.value())).thenReturn(Mono.just(new PostcodesDTO(200, new ResultDTO(3.0, 4.0), null)));

        when(latLongDistanceCalculator.differenceInKm(1.0, 2.0, 3.0, 4.0)).thenReturn(BigDecimal.valueOf(33));

        DistanceCalculatorService service = new DistanceCalculatorService(postcodesClient, latLongDistanceCalculator);

        assertEquals(new Distance(BigDecimal.valueOf(33)), service.distanceBetween(POSTCODE_1, POSTCODE_2));

        verify(latLongDistanceCalculator).differenceInKm(1.0, 2.0, 3.0, 4.0);
    }

    @Test
    void throwsAnExceptionWhenPostcode1NotFound() {
        Postcode postcode1 = new Postcode("ME1 4FL");
        Postcode postcode2 = new Postcode("GH1 1UL");
        when(postcodesClient.getPostcodeInformation(postcode1.value())).thenReturn(Mono.error(WebClientResponseException.create(404, "Invalid postcode", null, null, null)));

        DistanceCalculatorService service = new DistanceCalculatorService(postcodesClient, latLongDistanceCalculator);

        assertThrows(PostcodeException.class, ()-> service.distanceBetween(postcode1, postcode2));

        verifyNoInteractions(latLongDistanceCalculator);
    }

    @Test
    void throwsAnExceptionWhenPostcode2Unknown() {
        Postcode postcode1 = new Postcode("ME1 4FL");
        Postcode postcode2 = new Postcode("GH1 1UL");
        when(postcodesClient.getPostcodeInformation(postcode1.value())).thenReturn(Mono.just(new PostcodesDTO(200, new ResultDTO(1.0, 2.0), null)));
        when(postcodesClient.getPostcodeInformation(postcode1.value())).thenReturn(Mono.error(WebClientResponseException.create(404, "Invalid postcode", null, null, null)));

        DistanceCalculatorService service = new DistanceCalculatorService(postcodesClient, latLongDistanceCalculator);

        assertThrows(PostcodeException.class, ()-> service.distanceBetween(postcode1, postcode2));

        verifyNoInteractions(latLongDistanceCalculator);
    }
}