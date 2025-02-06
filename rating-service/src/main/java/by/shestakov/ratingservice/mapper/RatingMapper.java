package by.shestakov.ratingservice.mapper;

import by.shestakov.ratingservice.dto.RatingRequest;
import by.shestakov.ratingservice.dto.RatingResponse;
import by.shestakov.ratingservice.entity.Rating;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RatingMapper {

    RatingResponse toDto(Rating rating);

    @Mapping(target = "passenger", ignore = true)
    @Mapping(target = "driver", ignore = true)
    Rating toEntity(RatingRequest ratingRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(RatingRequest ratingRequest, @MappingTarget Rating rating);
}
