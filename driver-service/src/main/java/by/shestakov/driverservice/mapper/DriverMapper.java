package by.shestakov.driverservice.mapper;

import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.response.DriverResponse;
import by.shestakov.driverservice.entity.Driver;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DriverMapper {
    public DriverResponse toDto(Driver driver);


    @Mapping(target = "cars", ignore = true)
    public Driver toEntity(DriverRequest driverRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cars", ignore = true)
    public void updateToExists(DriverRequest driverRequest, @MappingTarget Driver driver);
}
