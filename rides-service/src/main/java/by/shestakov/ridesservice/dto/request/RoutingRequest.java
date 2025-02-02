package by.shestakov.ridesservice.dto.request;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Builder
public record RoutingRequest(
    List<String> points,

    String key,
    Boolean calc_point

    ) {
}
