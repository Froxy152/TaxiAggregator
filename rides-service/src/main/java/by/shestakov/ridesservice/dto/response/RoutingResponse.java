package by.shestakov.ridesservice.dto.response;

import by.shestakov.ridesservice.dto.PathDto;
import java.io.Serializable;
import java.util.List;

public record RoutingResponse(
        List<PathDto> paths
) implements Serializable {
}
