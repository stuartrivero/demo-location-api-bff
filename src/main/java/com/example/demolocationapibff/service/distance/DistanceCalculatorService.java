package com.example.demolocationapibff.service.distance;

import com.example.demolocationapibff.domain.Distance;
import com.example.demolocationapibff.domain.Postcode;
import com.example.demolocationapibff.service.postcodes.PostcodeException;
import com.example.demolocationapibff.service.postcodes.PostcodesClient;
import com.example.demolocationapibff.service.postcodes.PostcodesConfiguration;
import com.example.demolocationapibff.service.postcodes.PostcodesDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import static java.lang.String.format;

@Service
public class DistanceCalculatorService {
    private final PostcodesClient postcodesClient;
    private final LatLongDistanceCalculator latLongDistanceCalculator;
    private final Duration timeout;

    public DistanceCalculatorService(PostcodesClient postcodesClient,
                                     LatLongDistanceCalculator latLongDistanceCalculator,
                                     PostcodesConfiguration configuration) {
        this.postcodesClient = postcodesClient;
        this.latLongDistanceCalculator = latLongDistanceCalculator;
        this.timeout = Duration.ofMillis(configuration.getTimeoutMs());
    }

    public Distance distanceBetween(Postcode postcode1, Postcode postcode2) {
        Flux<PostcodesDTO> flux = Flux.fromIterable(List.of(postcode1, postcode2)).
                flatMapSequential(this::getPostcodeInformation);
        List<PostcodesDTO> postcodesDTOS = flux.collectList().block(timeout);
        PostcodesDTO postcodeInformation1 = postcodesDTOS.get(0);
        PostcodesDTO postcodeInformation2 = postcodesDTOS.get(1);

        BigDecimal distanceInKm = latLongDistanceCalculator.differenceInKm(
                postcodeInformation1.result().latitude(),
                postcodeInformation1.result().longitude(),
                postcodeInformation2.result().latitude(),
                postcodeInformation2.result().longitude()
        );
        return new Distance(distanceInKm);
    }

    private Mono<PostcodesDTO> getPostcodeInformation(Postcode postcode) {
        return postcodesClient.getPostcodeInformation(postcode.value())
                .onErrorMap(throwable -> new PostcodeException(format("Unable to fetch %s", postcode.value())));
    }

}
