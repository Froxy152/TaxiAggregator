package by.shestakov.ratingservice.service.impl;

import static by.shestakov.ratingservice.constant.TestConstant.TEST_COMMENTARY;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_COMMENTARY_DTO;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_DRIVER_ID;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_DRIVER_INVALID_ID;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_ID;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_INVALID_ID;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_RIDE_ID;
import static by.shestakov.ratingservice.constant.TestConstant.averageRatingResponse;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingByDriver;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingDriverRequest;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingDriverResponse;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingPassengerRequest;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingPassengerResponse;
import by.shestakov.ratingservice.dto.response.PageResponse;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.shestakov.ratingservice.config.KafkaConfig;
import by.shestakov.ratingservice.dto.request.CommentaryDto;
import by.shestakov.ratingservice.dto.request.RatingRequest;
import by.shestakov.ratingservice.dto.response.AverageRatingResponse;
import by.shestakov.ratingservice.dto.response.RatingResponse;
import by.shestakov.ratingservice.entity.RatedBy;
import by.shestakov.ratingservice.entity.Rating;
import by.shestakov.ratingservice.exception.DataNotFoundException;
import by.shestakov.ratingservice.exception.FeignClientNotFoundDataException;
import by.shestakov.ratingservice.exception.OnlyOneCommentOnRideException;
import by.shestakov.ratingservice.feign.DriverClient;
import by.shestakov.ratingservice.feign.PassengerClient;
import by.shestakov.ratingservice.feign.RideClient;
import by.shestakov.ratingservice.kafka.KafkaProducer;
import by.shestakov.ratingservice.mapper.PageMapper;
import by.shestakov.ratingservice.mapper.RatingMapper;
import by.shestakov.ratingservice.repository.RatingRepository;
import by.shestakov.ratingservice.util.ExceptionMessage;
import feign.FeignException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;


@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Mock
    private KafkaProducer kafkaProducer;

    @Mock
    private KafkaConfig kafkaConfig;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RatingMapper ratingMapper;

    @Mock
    private PageMapper pageMapper;

    @Mock
    private PassengerClient passengerClient;

    @Mock
    private DriverClient driverClient;

    @Mock
    private RideClient rideClient;

    @Test
    void getAllReviews() {
        int offset = 0;
        int limit = 1;

        List<Rating> ratingList = List.of(defaultRatingByDriver());
        Page<Rating> ratingPage = new PageImpl<>(ratingList, PageRequest.of(offset, limit), ratingList.size());
        List<RatingResponse> responseList = List.of(defaultRatingDriverResponse());
        Page<RatingResponse> ratingResponsePage = new PageImpl<>(responseList, PageRequest.of(offset, limit), responseList.size());
        PageResponse<RatingResponse> expectedResponse = new PageResponse<>(offset, limit, 1, responseList.size(), "", responseList);


        when(ratingRepository.findAll(PageRequest.of(offset, limit))).thenReturn(ratingPage);
        when(ratingMapper.toDto(any(Rating.class))).thenReturn(defaultRatingDriverResponse());
        when(pageMapper.toDto(ratingResponsePage)).thenReturn(expectedResponse);

        PageResponse<RatingResponse> response = ratingService.getAllReviews(offset, limit);

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(ratingRepository).findAll(PageRequest.of(offset, limit));
        verify(ratingMapper, times(ratingList.size())).toDto(any(Rating.class));
        verify(pageMapper).toDto(ratingResponsePage);
    }

    @Test
    void addNewReviewOnRide_ReturnsValidResponse() {
        String rideId = TEST_RIDE_ID;
        RatingRequest request = defaultRatingDriverRequest();
        Rating rating = defaultRatingByDriver();
        RatingResponse expectedResponse = defaultRatingDriverResponse();

        when(ratingRepository.existsByRideId(rideId)).thenReturn(false);
        when(ratingMapper.toEntity(request)).thenReturn(rating);
        when(ratingMapper.toDto(rating)).thenReturn(expectedResponse);
        when(ratingRepository.findAverageRatingByPassengerId(request.driverId())).thenReturn(averageRatingResponse());

        RatingResponse response = ratingService.addNewReviewOnRide(request);

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(ratingRepository).existsByRideId(rideId);
        verify(ratingMapper).toEntity(request);
        verify(ratingMapper).toDto(rating);
    }

    @Test
    void addNewReviewOnRideByPassenger_ReturnsValidResponse() {
        String rideId = TEST_RIDE_ID;
        RatingRequest request = defaultRatingPassengerRequest();
        Rating rating = defaultRatingByDriver();
        RatingResponse expectedResponse = defaultRatingPassengerResponse();

        when(ratingRepository.existsByRideId(rideId)).thenReturn(false);
        when(ratingMapper.toEntity(request)).thenReturn(rating);
        when(ratingMapper.toDto(rating)).thenReturn(expectedResponse);
        when(ratingRepository.findAverageRatingByDriverId(request.driverId())).thenReturn(averageRatingResponse());

        RatingResponse response = ratingService.addNewReviewOnRide(request);

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(ratingRepository).existsByRideId(rideId);
        verify(ratingMapper).toEntity(request);
        verify(ratingMapper).toDto(rating);
    }

    @Test
    void addNewReviewOnRide_UserCanSendOnReviewOnRide_ThrowException() {
        String rideId = TEST_RIDE_ID;
        RatingRequest request = defaultRatingDriverRequest();

        when(ratingRepository.existsByRideId(rideId)).thenReturn(true);

        OnlyOneCommentOnRideException exception = assertThrows(
                OnlyOneCommentOnRideException.class,
                () -> ratingService.addNewReviewOnRide(request)
        );

        assertEquals(ExceptionMessage.ONLY_ONE_MESSAGE, exception.getMessage());

        verify(ratingRepository).existsByRideId(rideId);
    }

    @Test
    void changeCommentUnderReview() {
        String id = TEST_ID;
        CommentaryDto request = TEST_COMMENTARY_DTO;
        Rating rating = defaultRatingByDriver();
        RatingResponse expectedResponse = defaultRatingDriverResponse();

        when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));
        when(ratingMapper.toDto(rating)).thenReturn(expectedResponse);

        RatingResponse response = ratingService.changeCommentUnderReview(id, request);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.commentary(), response.commentary());

        verify(ratingRepository).findById(id);
        verify(ratingRepository).save(rating);
        verify(ratingMapper).toDto(rating);
    }

    @Test
    void changeCommentUnderReview_ReviewNotFound() {
        String id = TEST_INVALID_ID;
        CommentaryDto request = TEST_COMMENTARY_DTO;

        when(ratingRepository.findById(id)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(
                DataNotFoundException.class,
                () -> ratingService.changeCommentUnderReview(id, request)
        );

        assertEquals(ExceptionMessage.NOT_FOUND_MESSAGE, exception.getMessage());

        verify(ratingRepository).findById(id);
    }

    @Test
    void getResultForDriverWithLimit() {
        Long driverId = TEST_DRIVER_ID;
        int limit = 1;

        when(ratingRepository.existsByDriverId(driverId)).thenReturn(true);
        when(ratingRepository.findAverageRatingByDriverIdByLimit(driverId, limit)).thenReturn(averageRatingResponse());

        AverageRatingResponse response = ratingService.getResultForDriverWithLimit(driverId, limit);

        assertNotNull(response);
        assertEquals(averageRatingResponse(), response);
    }

    @Test
    void getResultForDriverWithLimit_DriverNotFound() {
        Long driverId = TEST_DRIVER_INVALID_ID;
        int limit = 1;

        when(ratingRepository.existsByDriverId(driverId)).thenReturn(false);

        DataNotFoundException exception = assertThrows(
                DataNotFoundException.class,
                () -> ratingService.getResultForDriverWithLimit(driverId, limit)
        );


        assertEquals(ExceptionMessage.NOT_FOUND_MESSAGE, exception.getMessage());
    }
}