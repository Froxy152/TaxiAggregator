package by.shestakov.driverservice.mapper.impl;

import by.shestakov.driverservice.dto.request.CarDtoRequest;
import by.shestakov.driverservice.dto.response.CarDtoResponse;
import by.shestakov.driverservice.entity.Car;
import by.shestakov.driverservice.mapper.CarMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarMapperImpl implements CarMapper {
    private final ModelMapper modelMapper;

    @Override
    public CarDtoResponse toDto(Car car) {

        return modelMapper.map(car, CarDtoResponse.class);
    }

    @Override
    public Car toEntity(CarDtoRequest carDtoRequest) {
        return modelMapper.map(carDtoRequest, Car.class);
    }

    @Override
    public void updateToExists(CarDtoRequest carDtoRequest, Car car) {
        modelMapper.map(carDtoRequest, car);
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Car.class, CarDtoResponse.class)
                //    .addMappings(m -> m.skip(CarDtoResponse::driverId))
                .setPostConverter(toDtoConverter());
    }

    public Converter<Car, CarDtoResponse> toDtoConverter() {
        return context -> {
            Car source = context.getSource();
            CarDtoResponse destination = context.getDestination();

            return context.getDestination();
        };
    }
}
