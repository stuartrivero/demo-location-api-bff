package com.example.demolocationapibff;

import com.example.demolocationapibff.domain.Distance;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DistanceIntTest {

    @Autowired
    private WebTestClient webClient;

    private static MockWebServer mockWebServer;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("postcodes-api.base-url", () -> mockWebServer.url("/").url().toString());
    }

    @BeforeAll
    static void setupMockWebServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @Test
    public void successfullyReturnsDistance() {

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(SampleData.postcode1())
        );

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(SampleData.postcode2())
        );
        var body = webClient.get().uri("/distance/calculator?postcode1=ME1 3TN&postcode2=W1A 1AA")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Distance.class).returnResult();

        assertEquals(BigDecimal.valueOf(5.37), body.getResponseBody().km());
    }

    @Test
    public void returnsErrorWhenPostcodeInvalid() {

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(404)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(SampleData.postcodeInvalid())
        );

        var body = webClient.get().uri("/distance/calculator?postcode1=ME1 3TN&postcode2=W1A 1AA")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class).returnResult();

        assertTrue(requireNonNull(body.getResponseBody()).contains("Unable to fetch"));
    }

}
