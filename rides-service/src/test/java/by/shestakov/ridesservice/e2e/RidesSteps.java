package by.shestakov.ridesservice.e2e;

import by.shestakov.ridesservice.dto.request.RideRequest;
import by.shestakov.ridesservice.dto.request.RideStatusRequest;
import by.shestakov.ridesservice.dto.request.RideUpdateRequest;
import io.restassured.response.Response;

public class RidesSteps {

    private RideRequest rideRequest;
    private Response response;
    private RideStatusRequest rideStatusRequest;
    private RideUpdateRequest rideUpdateRequest;
}
