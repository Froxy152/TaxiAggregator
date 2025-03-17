package by.shestakov.ratingservice.dto.response;

import java.util.List;
import lombok.Builder;


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