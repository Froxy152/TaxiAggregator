package by.shestakov.driverservice.mapper;

import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.response.DriverResponse;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.entity.Gender;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = CarMapper.class)
public interface DriverMapper {
    @Mapping(target = "cars", source = "cars")
    public DriverResponse toDto(Driver driver);

    @Mapping(target = "cars", ignore = true)
    public Driver toEntity(DriverRequest driverRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cars", ignore = true)
    public void updateToExists(DriverRequest driverRequest, @MappingTarget Driver driver);

    default Gender map(Integer value) {
        return Gender.fromValue(value);
    }
}
