package by.shestakov.passenger_service.service;

import by.shestakov.passenger_service.dto.PassengerDto;
import by.shestakov.passenger_service.entity.Passenger;
import by.shestakov.passenger_service.exception.AlreadyExistsException;
import by.shestakov.passenger_service.exception.BadRequestException;
import by.shestakov.passenger_service.exception.NotFoundException;
import by.shestakov.passenger_service.mapper.PassengerMapper;
import by.shestakov.passenger_service.repository.PassengerRepository;
import by.shestakov.passenger_service.util.RequestMessageConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    public PassengerDto getById(Long id){
      Passenger foundPassenger = passengerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(RequestMessageConstants.NOT_FOUND_MESSAGE,id)));
        if (foundPassenger.is_deleted()) {
            throw new BadRequestException(String.format(RequestMessageConstants.BAD_REQUEST_MESSAGE, id));
        }
        return passengerMapper.toDto(foundPassenger);
    }

    @Transactional
    public PassengerDto create(PassengerDto passengerDto){
        if(passengerRepository.existsByEmailOrPhoneNumber(passengerDto.getEmail(),passengerDto.getPhoneNumber())){
            throw new AlreadyExistsException(String.format(RequestMessageConstants.CONFLICT_MESSAGE,passengerDto.getEmail(),passengerDto.getPhoneNumber()));
        }
        passengerRepository.save(passengerMapper.toEntity(passengerDto));
        return passengerDto;
    }

    @Transactional
    public PassengerDto updateByID(PassengerDto passengerDto, Long id){
        Passenger foundPassenger = passengerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(RequestMessageConstants.NOT_FOUND_MESSAGE, id)));
        if (foundPassenger.is_deleted()) {
            throw new BadRequestException(String.format(RequestMessageConstants.BAD_REQUEST_MESSAGE, id));
        }
        if(passengerRepository.existsByEmailOrPhoneNumber(passengerDto.getEmail(),passengerDto.getPhoneNumber())){
            throw new AlreadyExistsException(String.format(RequestMessageConstants.CONFLICT_MESSAGE,passengerDto.getEmail(),passengerDto.getPhoneNumber()));
        }
        passengerMapper.toUpdateExists(passengerDto,foundPassenger);
        passengerRepository.save(foundPassenger);
        return passengerDto;
    }

    @Transactional
    public void delete(long id){
        Passenger foundPassenger = passengerRepository.findById(id).orElseThrow(() ->
                new NotFoundException((String.format(RequestMessageConstants.NOT_FOUND_MESSAGE,id))));
        if(foundPassenger.is_deleted()){
            throw new BadRequestException(String.format(RequestMessageConstants.BAD_REQUEST_MESSAGE,id));
        }
        foundPassenger.set_deleted(true);
        passengerRepository.save(foundPassenger);
    }
}
