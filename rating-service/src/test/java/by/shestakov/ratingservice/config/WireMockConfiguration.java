package by.shestakov.ratingservice.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;


public class WireMockConfiguration {

    public static void getDriverMock(WireMockServer wireMockServer) throws Exception {
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/drivers/1"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)));
    }

    public static void getDriverNotFoundMock(WireMockServer wireMockServer) throws Exception {
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/drivers/999"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"message\": \"Driver not found\"}")));

    }

    public static void getPassengerMock(WireMockServer wireMockServer) throws Exception {
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/passengers/1"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)));
    }

    public static void getPassengerNotFoundMock(WireMockServer wireMockServer) throws Exception {
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/passengers/999"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(404)
                        .withBody("{\"message\": \"Passenger not found\"}")));
    }

    public static void getRide(WireMockServer wireMockServer) throws Exception {
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/rides/67cea3597960c17aa8048ab7"))
                .willReturn(aResponse()
                        .withStatus(200)));
    }

    public static void getRideNotFound(WireMockServer wireMockServer) throws Exception {
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/rides/not_found"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"message\" : \"Data not found\"}")));
    }




}
