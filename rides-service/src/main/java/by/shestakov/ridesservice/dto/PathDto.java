package by.shestakov.ridesservice.dto;

import java.io.Serializable;

public record PathDto(
        Double distance,
        Integer time
) implements Serializable {
}
