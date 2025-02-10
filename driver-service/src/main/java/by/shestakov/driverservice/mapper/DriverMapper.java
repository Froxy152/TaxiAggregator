package by.shestakov.driverservice.mapper;

import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.request.DriverUpdateRequest;
import by.shestakov.driverservice.dto.response.DriverResponse;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.entity.Gender;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = CarMapper.class)
public interface DriverMapper {
    @Mapping(target = "cars", source = "cars")
    DriverResponse toDto(Driver driver);

    @Mapping(target = "cars", ignore = true)
    Driver toEntity(DriverRequest driverRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cars", ignore = true)
    void updateToExists(DriverUpdateRequest driverUpdateRequest, @MappingTarget Driver driver);

    default Gender map(Integer code) {
        return Gender.fromCode(code);
    }

    default Gender map(String value) {
        return Gender.fromValue(value);
    }
}
