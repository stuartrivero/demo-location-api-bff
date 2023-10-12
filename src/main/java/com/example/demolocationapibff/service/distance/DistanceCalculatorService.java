package com.example.demolocationapibff.service.distance;

import com.example.demolocationapibff.domain.Distance;
import com.example.demolocationapibff.domain.Postcode;
import com.example.demolocationapibff.domain.PostcodeSearch;
import com.example.demolocationapibff.service.database.PostcodeSearchRepository;
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
    private final PostcodeSearchRepository postcodeSearchRepository;

    public DistanceCalculatorService(PostcodesClient postcodesClient,
                                     LatLongDistanceCalculator latLongDistanceCalculator,
                                     PostcodesConfiguration configuration,
                                     PostcodeSearchRepository postcodeSearchRepository) {
        this.postcodesClient = postcodesClient;
        this.latLongDistanceCalculator = latLongDistanceCalculator;
        this.timeout = Duration.ofMillis(configuration.getTimeoutMs());
        this.postcodeSearchRepository = postcodeSearchRepository;
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
        saveSearch(postcode1, postcode2, distanceInKm);
        return new Distance(distanceInKm);
    }

    private void saveSearch(Postcode postcode1, Postcode postcode2, BigDecimal distanceInKm) {
        PostcodeSearch entity = new PostcodeSearch();
        entity.setPostcode1(postcode1.value());
        entity.setPostcode2(postcode2.value());
        entity.setDistance(distanceInKm);
        postcodeSearchRepository.save(entity);
    }

    private Mono<PostcodesDTO> getPostcodeInformation(Postcode postcode) {
        return postcodesClient.getPostcodeInformation(postcode.value())
                .onErrorMap(throwable -> new PostcodeException(format("Unable to fetch %s", postcode.value())));
    }

}
