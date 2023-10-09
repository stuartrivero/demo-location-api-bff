package com.example.demolocationapibff.service;

import com.example.demolocationapibff.domain.Distance;
import com.example.demolocationapibff.domain.Postcode;
import com.example.demolocationapibff.service.distance.DistanceCalculatorService;
import com.example.demolocationapibff.service.distance.LatLongDistanceCalculator;
import com.example.demolocationapibff.service.postcodes.PostcodesDTO;
import com.example.demolocationapibff.service.postcodes.PostcodesIoService;
import com.example.demolocationapibff.service.postcodes.ResultDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DistanceCalculatorServiceTest {

    public static final Postcode POSTCODE_1 = new Postcode("ME1 4FL");
    public static final Postcode POSTCODE_2 = new Postcode("GH1 1UL");
    @Mock
    PostcodesIoService postcodesIoService;

    @Mock
    LatLongDistanceCalculator latLongDistanceCalculator;

    @Test
    void returnsADistance() {
        when(postcodesIoService.getPostcodeInformation(POSTCODE_1.value())).thenReturn(new PostcodesDTO(200, new ResultDTO(1.0, 2.0), null));
        when(postcodesIoService.getPostcodeInformation(POSTCODE_2.value())).thenReturn(new PostcodesDTO(200, new ResultDTO(3.0, 4.0), null));

        when(latLongDistanceCalculator.differenceInKm(1.0, 2.0, 3.0, 4.0)).thenReturn(BigDecimal.valueOf(33));

        DistanceCalculatorService service = new DistanceCalculatorService(postcodesIoService, latLongDistanceCalculator);

        assertEquals(new Distance(BigDecimal.valueOf(33)), service.distanceBetween(POSTCODE_1, POSTCODE_2));

        verify(latLongDistanceCalculator).differenceInKm(1.0, 2.0, 3.0, 4.0);
    }

    @Test
    void returnsADistanceFailsWhenPostcode1Unknown() {
        Postcode postcode1 = new Postcode("ME1 4FL");
        Postcode postcode2 = new Postcode("GH1 1UL");
        when(postcodesIoService.getPostcodeInformation(postcode1.value())).thenReturn(new PostcodesDTO(404, null, "Invalid postcode"));
        when(postcodesIoService.getPostcodeInformation(postcode2.value())).thenReturn(new PostcodesDTO(200, new ResultDTO(3.0, 4.0), null));

        DistanceCalculatorService service = new DistanceCalculatorService(postcodesIoService, latLongDistanceCalculator);

        assertThrows(IllegalArgumentException.class, ()-> service.distanceBetween(postcode1, postcode2));

        verifyNoInteractions(latLongDistanceCalculator);
    }

    @Test
    void returnsADistanceFailsWhenPostcode2Unknown() {
        Postcode postcode1 = new Postcode("ME1 4FL");
        Postcode postcode2 = new Postcode("GH1 1UL");
        when(postcodesIoService.getPostcodeInformation(postcode1.value())).thenReturn(new PostcodesDTO(200, new ResultDTO(1.0, 2.0), null));
        when(postcodesIoService.getPostcodeInformation(postcode2.value())).thenReturn(new PostcodesDTO(404, null,  "Invalid postcode"));

        DistanceCalculatorService service = new DistanceCalculatorService(postcodesIoService, latLongDistanceCalculator);

        assertThrows(IllegalArgumentException.class, ()-> service.distanceBetween(postcode1, postcode2));

        verifyNoInteractions(latLongDistanceCalculator);
    }
}