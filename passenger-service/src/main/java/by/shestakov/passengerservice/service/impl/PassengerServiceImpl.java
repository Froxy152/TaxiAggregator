package by.shestakov.passengerservice.service.impl;

import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.response.PageResponse;
import by.shestakov.passengerservice.dto.response.PassengerResponse;
import by.shestakov.passengerservice.entity.Passenger;
import by.shestakov.passengerservice.exception.PassengerAlreadyExistsException;
import by.shestakov.passengerservice.exception.PassengerNotFoundException;
import by.shestakov.passengerservice.mapper.PageMapper;
import by.shestakov.passengerservice.mapper.PassengerMapper;
import by.shestakov.passengerservice.repository.PassengerRepository;
import by.shestakov.passengerservice.service.PassengerService;
import by.shestakov.passengerservice.util.ExceptionConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private final PageMapper pageMapper;

    @Override
    public PageResponse<PassengerResponse> getAllPassengers(Integer offset, Integer limit) {
        Page<PassengerResponse> passengerPageDto = passengerRepository
                .findALlByIsDeletedFalse(PageRequest.of(offset, limit))
                .map(passengerMapper::toDto);

        return pageMapper.toDto(passengerPageDto);
    }

    @Override
    public PassengerResponse getPassengerById(Long id) {
        Passenger foundPassenger = passengerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new PassengerNotFoundException(
                        String.format(ExceptionConstants.NOT_FOUND_MESSAGE, id)));

        return passengerMapper.toDto(foundPassenger);
    }

    @Override
    @Transactional
    public PassengerResponse createPassenger(PassengerRequest passengerRequest) {
        if (passengerRepository.existsByEmailOrPhoneNumber(passengerRequest.email(), passengerRequest.phoneNumber())) {
            throw new PassengerAlreadyExistsException(
                    String.format(ExceptionConstants.CONFLICT_MESSAGE, passengerRequest.email(), passengerRequest.phoneNumber()));
        }
        Passenger savedPassenger = passengerMapper.toEntity(passengerRequest);
        passengerRepository.save(savedPassenger);

        return passengerMapper.toDto(savedPassenger);
    }

    @Override
    @Transactional
    public PassengerResponse updatePassengerById(PassengerRequest passengerRequest, Long id) {
        Passenger foundPassenger = passengerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new PassengerNotFoundException(
                        String.format(ExceptionConstants.NOT_FOUND_MESSAGE, id)));
        if (passengerRepository.existsByEmailOrPhoneNumber(passengerRequest.email(), passengerRequest.phoneNumber())) {
            throw new PassengerAlreadyExistsException(
                    String.format(ExceptionConstants.CONFLICT_MESSAGE, passengerRequest.email(), passengerRequest.phoneNumber()));
        }
        passengerMapper.toUpdateExists(passengerRequest, foundPassenger);
        passengerRepository.save(foundPassenger);

        return passengerMapper.toDto(foundPassenger);
    }

    @Override
    @Transactional
    public void softDeletePassenger(Long id) {
        Passenger foundPassenger = passengerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new PassengerNotFoundException((
                        String.format(ExceptionConstants.NOT_FOUND_MESSAGE, id))));
        foundPassenger.setIsDeleted(true);

        passengerRepository.save(foundPassenger);
    }
}
