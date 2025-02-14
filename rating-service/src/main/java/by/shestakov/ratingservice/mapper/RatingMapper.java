package by.shestakov.ratingservice.mapper;

import by.shestakov.ratingservice.dto.request.RatingRequest;
import by.shestakov.ratingservice.dto.response.RatingResponse;
import by.shestakov.ratingservice.entity.Rating;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

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
