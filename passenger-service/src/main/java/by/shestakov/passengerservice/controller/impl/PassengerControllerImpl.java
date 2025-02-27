package by.shestakov.passengerservice.controller.impl;

import by.shestakov.passengerservice.controller.ControllerAction;
import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.response.PageResponse;
import by.shestakov.passengerservice.dto.response.PassengerResponse;
import by.shestakov.passengerservice.service.PassengerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/passengers")
public class PassengerControllerImpl implements ControllerAction {

    private final PassengerService passengerService;

    @GetMapping
    public ResponseEntity<PageResponse<PassengerResponse>> getAll(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "5") @Min(1) Integer limit) {
        return new ResponseEntity<>(passengerService.getAllPassengers(offset, limit),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getById(@PathVariable Long id) {
        PassengerResponse findPassenger = passengerService.getPassengerById(id);
        return new ResponseEntity<>(findPassenger,
                HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<PassengerResponse> create(@RequestBody @Valid PassengerRequest passengerRequest) {
        return new ResponseEntity<>(passengerService.createPassenger(passengerRequest),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponse> update(@RequestBody @Valid PassengerRequest passengerRequest,
                                                    @PathVariable Long id) {
        return new ResponseEntity<>(passengerService.updatePassengerById(passengerRequest, id),
                HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<PassengerResponse> delete(@PathVariable Long id) {
        passengerService.softDeletePassenger(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}