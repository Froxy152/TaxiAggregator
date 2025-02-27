package by.shestakov.ratingservice.dto.response;

public record PassengerResponse(
        Long id,
        String name,
        String lastName,
        String email,
        String phoneNumber,
        Boolean isDeleted
) {
}
