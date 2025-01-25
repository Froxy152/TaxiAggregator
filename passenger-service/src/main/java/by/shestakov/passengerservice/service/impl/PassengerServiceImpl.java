package by.shestakov.passengerservice.service.impl;

import by.shestakov.passengerservice.dto.PassengerDto;
import by.shestakov.passengerservice.entity.Passenger;
import by.shestakov.passengerservice.exception.AlreadyExistsException;
import by.shestakov.passengerservice.exception.BadRequestException;
import by.shestakov.passengerservice.exception.NotFoundException;
import by.shestakov.passengerservice.mapper.PassengerMapper;
import by.shestakov.passengerservice.repository.PassengerRepository;
import by.shestakov.passengerservice.service.PassengerService;
import by.shestakov.passengerservice.util.RequestMessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    public PassengerDto getById(Long id){
      Passenger foundPassenger = passengerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(RequestMessageConstants.NOT_FOUND_MESSAGE,id)));
        if (foundPassenger.getIsDeleted()) {
            throw new BadRequestException(String.format(RequestMessageConstants.BAD_REQUEST_MESSAGE, id));
        }
        return passengerMapper.toDto(foundPassenger);
    }

    @Transactional
    public PassengerDto create(PassengerDto passengerDto){
        if(passengerRepository.existsByEmailOrPhoneNumber(passengerDto.email(),passengerDto.phoneNumber())){
            throw new AlreadyExistsException(String.format(RequestMessageConstants.CONFLICT_MESSAGE,passengerDto.email(),passengerDto.phoneNumber()));
        }
        passengerRepository.save(passengerMapper.toEntity(passengerDto));
        return passengerDto;
    }

    @Transactional
    public PassengerDto updateById(PassengerDto passengerDto, Long id){
        Passenger foundPassenger = passengerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(RequestMessageConstants.NOT_FOUND_MESSAGE, id)));
        if (foundPassenger.getIsDeleted()) {
            throw new BadRequestException(String.format(RequestMessageConstants.BAD_REQUEST_MESSAGE, id));
        }
        if(passengerRepository.existsByEmailOrPhoneNumber(passengerDto.email(),passengerDto.phoneNumber())){
            throw new AlreadyExistsException(String.format(RequestMessageConstants.CONFLICT_MESSAGE,passengerDto.email(),passengerDto.phoneNumber()));
        }
        passengerMapper.toUpdateExists(passengerDto,foundPassenger);
        passengerRepository.save(foundPassenger);
        return passengerDto;
    }

    @Transactional
    public void delete(Long id){
        Passenger foundPassenger = passengerRepository.findById(id).orElseThrow(() ->
                new NotFoundException((String.format(RequestMessageConstants.NOT_FOUND_MESSAGE,id))));
        if(foundPassenger.getIsDeleted()){
            throw new BadRequestException(String.format(RequestMessageConstants.BAD_REQUEST_MESSAGE,id));
        }
        foundPassenger.setIsDeleted(true);
        passengerRepository.save(foundPassenger);
    }
}
