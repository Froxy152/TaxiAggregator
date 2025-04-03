package by.shestakov.ridesservice.dto.response;

import java.io.Serializable;

public record PassengerResponse(
        Long id,
        String name,
        String lastName,
        String email,
        String phoneNumber
) implements Serializable {
}
