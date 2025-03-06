package by.shestakov.ridesservice.dto.response;

public record PassengerResponse(
        Long id,
        String name,
        String lastName,
        String email,
        String phoneNumber
) {
}
