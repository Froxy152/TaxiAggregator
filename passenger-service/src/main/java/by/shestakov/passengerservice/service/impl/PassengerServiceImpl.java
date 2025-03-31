package by.shestakov.passengerservice.service.impl;

import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.request.UpdatePassengerRequest;
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
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private final PageMapper pageMapper;

    @Override
    public PageResponse<PassengerResponse> getAllPassengers(Integer offset, Integer limit) {
        log.debug("Entering in getAllPassengers method. Offset: {}, Limit: {}", offset, limit);
        Page<PassengerResponse> passengerPageDto = passengerRepository
                .findAllByIsDeletedFalse(PageRequest.of(offset, limit))
                .map(passengerMapper::toDto);

        log.info("getAllPassengers: Returns all passengers. Passengers: {}", passengerPageDto);
        return pageMapper.toDto(passengerPageDto);
    }

    @Override
    public PassengerResponse getPassengerById(Long id) {
        log.debug("Entering in getPassengerById method. PassengerId: {}", id);
        Passenger foundPassenger = passengerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new PassengerNotFoundException(
                        String.format(ExceptionConstants.NOT_FOUND_MESSAGE, id)));

        log.info("getPassengerById: Passenger successfully found. Passenger: {}", foundPassenger);
        return passengerMapper.toDto(foundPassenger);
    }

    @Override
    @Transactional
    public PassengerResponse createPassenger(PassengerRequest passengerRequest) {
        log.debug("Entering in createPassenger method. PassengerRequest: {}", passengerRequest);
        String email = passengerRequest.email();
        String phoneNumber = passengerRequest.phoneNumber();
        if (passengerRepository.existsByEmailOrPhoneNumber(email, phoneNumber)) {
            log.error("createPassenger: Passenger already exists with Email or Phone number." +
                            " Email: {}, Phone number: {}", passengerRequest.email(), passengerRequest.phoneNumber());
            throw new PassengerAlreadyExistsException(
                    ExceptionConstants.CONFLICT_MESSAGE.formatted(email, phoneNumber));
        }

        Passenger savedPassenger = passengerMapper.toEntity(passengerRequest);
        savedPassenger.setRating(BigDecimal.valueOf(0.0));

        passengerRepository.save(savedPassenger);
        log.info("createPassenger: Passenger successfully created. Passenger: {}", savedPassenger);
        return passengerMapper.toDto(savedPassenger);
    }

    @Override
    @Transactional
    public PassengerResponse updatePassengerById(UpdatePassengerRequest updatePassengerRequest, Long id) {
        log.debug("Entering in updatePassengerById method. UpdatePassengerRequest: {}, PassengerId: {}",
                updatePassengerRequest, id);

        Passenger foundPassenger = passengerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new PassengerNotFoundException(
                        String.format(ExceptionConstants.NOT_FOUND_MESSAGE, id)));
        String email = updatePassengerRequest.email();
        String phoneNumber = updatePassengerRequest.phoneNumber();

        if (passengerRepository.existsByEmail(email)) {
            log.error("updatePassengerById: Passenger email already exists. Email: {}", email);
            throw new PassengerAlreadyExistsException(
                    ExceptionConstants.CONFLICT_DATA_ALREADY_REGISTERED.formatted(email));
        }

        if (passengerRepository.existsByPhoneNumber(phoneNumber)) {
            log.error("updatePassengerById: Passenger phone number already exists. Phone number: {}", phoneNumber);
            throw new PassengerAlreadyExistsException(
                    ExceptionConstants.CONFLICT_DATA_ALREADY_REGISTERED.formatted(phoneNumber));
        }

        passengerMapper.update(updatePassengerRequest, foundPassenger);
        passengerRepository.save(foundPassenger);

        log.info("updatePassengerById: Passenger successfully updated. PassengerId: {}", id);
        return passengerMapper.toDto(foundPassenger);
    }

    @Override
    @Transactional
    public void softDeletePassenger(Long id) {
        log.debug("Entering softDeletePassenger method. PassengerId: {}", id);
        Passenger foundPassenger = passengerRepository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new PassengerNotFoundException(
                String.format(ExceptionConstants.NOT_FOUND_MESSAGE, id)));

        foundPassenger.setIsDeleted(true);

        log.info("softDeletePassenger: Passenger successfully deleted. PassengerId: {}", id);
        passengerRepository.save(foundPassenger);
    }
}