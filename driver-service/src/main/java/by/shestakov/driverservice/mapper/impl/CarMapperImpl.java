package by.shestakov.driverservice.mapper.impl;

import by.shestakov.driverservice.dto.CarDto;
import by.shestakov.driverservice.entity.Car;
import by.shestakov.driverservice.mapper.CarMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarMapperImpl implements CarMapper {
    private final ModelMapper modelMapper;

    @Override
    public CarDto toDto(Car car) {

        return modelMapper.map(car, CarDto.class);
    }

    @Override
    public Car toEntity(CarDto carDto) {
        return modelMapper.map(carDto, Car.class);
    }

    @Override
    public void updateToExists(CarDto carDto, Car car) {
        modelMapper.map(carDto, car);
    }
}
