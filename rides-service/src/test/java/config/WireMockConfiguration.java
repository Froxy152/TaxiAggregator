package config;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import by.shestakov.ridesservice.dto.response.DriverResponse;
import by.shestakov.ridesservice.dto.response.PassengerResponse;
import by.shestakov.ridesservice.dto.response.RoutingResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;


public class WireMockConfiguration {

    public static void getDriverMock(WireMockServer wireMockServer,
                                     ObjectMapper objectMapper,
                                     DriverResponse response) throws Exception {
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/drivers/1"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))));
    }

    public static void getDriverNotFoundMock(WireMockServer wireMockServer) throws Exception {
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/drivers/999"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"message\": \"Driver not found\"}")));

    }

    public static void getPassengerMock(WireMockServer wireMockServer,
                                        ObjectMapper objectMapper,
                                        PassengerResponse response) throws Exception {
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/passengers/1"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))));
    }

    public static void getPassengerNotFoundMock(WireMockServer wireMockServer) throws Exception {
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/passengers/999"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(404)
                        .withBody("{\"message\": \"Passenger not found\"}")));
    }

    public static void getRouteMock(WireMockServer wireMockServer,
                                    ObjectMapper objectMapper,
                                    RoutingResponse response) throws Exception {
        wireMockServer.stubFor(get(urlPathEqualTo("//graphhopper.com/api/route"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(response))));
    }
    public static void error(WireMockServer wireMockServer) throws Exception {
        wireMockServer.stubFor(get(urlPathEqualTo("/api/v1/driver/9999"))
                .willReturn(serverError()));
    }



}
