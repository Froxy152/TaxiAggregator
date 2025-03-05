package by.shestakov.driverservice.mapper;

import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.request.DriverUpdateRequest;
import by.shestakov.driverservice.dto.response.DriverResponse;
import by.shestakov.driverservice.entity.Car;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.entity.Gender;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DriverMapper {
    @Mapping(target = "cars", source = "cars", qualifiedByName = "mapCarIds")
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

    @Named("mapCarIds")
    default Set<Long> mapCarIds(Set<Car> cars) {
        if (cars == null) {
            return new HashSet<>();
        }
        return cars.stream().map(Car::getId).collect(Collectors.toSet());
    }
}
