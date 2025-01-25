package by.shestakov.passengerservice.service.impl;

import by.shestakov.passengerservice.dto.request.PassengerDtoRequest;
import by.shestakov.passengerservice.dto.response.PassengerDtoResponse;
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

    public PassengerDtoResponse getById(Long id){
      Passenger foundPassenger = passengerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(RequestMessageConstants.NOT_FOUND_MESSAGE,id)));
        if (foundPassenger.getIsDeleted()) {
            throw new BadRequestException(String.format(RequestMessageConstants.BAD_REQUEST_MESSAGE, id));
        }
        return passengerMapper.toDto(foundPassenger);
    }

    @Transactional
    public PassengerDtoResponse create(PassengerDtoRequest passengerDtoRequest){
        if(passengerRepository.existsByEmailOrPhoneNumber(passengerDtoRequest.email(), passengerDtoRequest.phoneNumber())){
            throw new AlreadyExistsException(String.format(RequestMessageConstants.CONFLICT_MESSAGE, passengerDtoRequest.email(), passengerDtoRequest.phoneNumber()));
        }
        Passenger savedPassenger = passengerRepository.save(passengerMapper.toEntity(passengerDtoRequest));
        return passengerMapper.toDto(savedPassenger);
    }

    @Transactional
    public PassengerDtoResponse updateById(PassengerDtoRequest passengerDtoRequest, Long id){
        Passenger foundPassenger = passengerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(RequestMessageConstants.NOT_FOUND_MESSAGE, id)));
        if (foundPassenger.getIsDeleted()) {
            throw new BadRequestException(String.format(RequestMessageConstants.BAD_REQUEST_MESSAGE, id));
        }
        if(passengerRepository.existsByEmailOrPhoneNumber(passengerDtoRequest.email(), passengerDtoRequest.phoneNumber())){
            throw new AlreadyExistsException(String.format(RequestMessageConstants.CONFLICT_MESSAGE, passengerDtoRequest.email(), passengerDtoRequest.phoneNumber()));
        }
        passengerMapper.toUpdateExists(passengerDtoRequest,foundPassenger);
        passengerRepository.save(foundPassenger);
        return passengerMapper.toDto(foundPassenger);
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
