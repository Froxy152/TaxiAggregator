package by.shestakov.passengerservice.service.impl;

import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.response.PassengerResponse;
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

    public PassengerResponse getById(Long id){
      Passenger foundPassenger = passengerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(RequestMessageConstants.NOT_FOUND_MESSAGE,id)));
        if (foundPassenger.getIsDeleted()) {
            throw new BadRequestException(String.format(RequestMessageConstants.BAD_REQUEST_MESSAGE, id));
        }
        return passengerMapper.toDto(foundPassenger);
    }

    @Transactional
    public PassengerResponse create(PassengerRequest passengerRequest){
        if(passengerRepository.existsByEmailOrPhoneNumber(passengerRequest.email(), passengerRequest.phoneNumber())){
            throw new AlreadyExistsException(String.format(RequestMessageConstants.CONFLICT_MESSAGE, passengerRequest.email(), passengerRequest.phoneNumber()));
        }
        Passenger savedPassenger = passengerMapper.toEntity(passengerRequest);
        passengerRepository.save(savedPassenger);

        return passengerMapper.toDto(savedPassenger);
    }

    @Transactional
    public PassengerResponse updateById(PassengerRequest passengerRequest, Long id){
        Passenger foundPassenger = passengerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(RequestMessageConstants.NOT_FOUND_MESSAGE, id)));
        if (foundPassenger.getIsDeleted()) {
            throw new BadRequestException(String.format(RequestMessageConstants.BAD_REQUEST_MESSAGE, id));
        }
        if(passengerRepository.existsByEmailOrPhoneNumber(passengerRequest.email(), passengerRequest.phoneNumber())){
            throw new AlreadyExistsException(String.format(RequestMessageConstants.CONFLICT_MESSAGE, passengerRequest.email(), passengerRequest.phoneNumber()));
        }
        passengerMapper.toUpdateExists(passengerRequest,foundPassenger);
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
