package by.shestakov.ridesservice.dto.request;

import java.util.List;
import lombok.Builder;


@Builder
public record RoutingRequest(
    List<String> points,
    String key,
    Boolean calcPoint) {
}
