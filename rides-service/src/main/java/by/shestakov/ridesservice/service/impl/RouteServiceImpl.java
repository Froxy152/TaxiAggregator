package by.shestakov.ridesservice.service.impl;

import by.shestakov.ridesservice.dto.request.RoutingRequest;
import by.shestakov.ridesservice.dto.response.RoutingResponse;
import by.shestakov.ridesservice.feign.RoutingFeign;
import by.shestakov.ridesservice.service.RouteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RouteServiceImpl implements RouteService {

    private final RoutingFeign routingFeign;
    @Value("${api.key}")
    private String key;

    @Override
    public RoutingResponse createRequest(String addressFrom, String addressDestination) {
        RoutingRequest request = RoutingRequest.builder()
                .points(List.of(addressFrom, addressDestination))
                .calcPoint(false)
                .key(key)
                .build();

        return routingFeign.requestDistance(request.points(), request.calcPoint(), request.key());
    }
}
