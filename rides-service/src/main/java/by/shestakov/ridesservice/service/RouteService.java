package by.shestakov.ridesservice.service;

import by.shestakov.ridesservice.dto.response.RoutingResponse;

public interface RouteService {
    RoutingResponse createRequest(String addressTo, String addressFrom);
}
