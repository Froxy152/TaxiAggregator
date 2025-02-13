package by.shestakov.ratingservice.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PageResponse<T>(
        int offset,

        int limit,

        int totalPages,

        long totalElements,

        String sort,

        List<T> values
) {
}