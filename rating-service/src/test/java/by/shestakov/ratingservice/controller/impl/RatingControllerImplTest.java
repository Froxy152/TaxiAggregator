package by.shestakov.ratingservice.controller.impl;

import static by.shestakov.ratingservice.constant.TestConstant.DEFAULT_ADDRESS;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_COMMENTARY_DTO;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_DRIVER_ID;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_DRIVER_INVALID_ID;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_ID;
import static by.shestakov.ratingservice.constant.TestConstant.TEST_INVALID_ID;
import static by.shestakov.ratingservice.constant.TestConstant.averageRatingResponse;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingDriverRequest;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingDriverResponse;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingPassengerRequest;
import static by.shestakov.ratingservice.constant.TestConstant.defaultRatingPassengerResponse;
import by.shestakov.ratingservice.dto.request.CommentaryDto;
import by.shestakov.ratingservice.dto.request.RatingRequest;
import by.shestakov.ratingservice.dto.response.AverageRatingResponse;
import by.shestakov.ratingservice.dto.response.PageResponse;
import by.shestakov.ratingservice.dto.response.RatingResponse;
import by.shestakov.ratingservice.exception.DataNotFoundException;
import by.shestakov.ratingservice.exception.GlobalExceptionHandler;
import by.shestakov.ratingservice.exception.OnlyOneCommentOnRideException;
import by.shestakov.ratingservice.service.RatingService;
import by.shestakov.ratingservice.util.ExceptionMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class RatingControllerImplTest {

    private MockMvc mockMvc;

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingControllerImpl ratingController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getAllRatings_200() throws Exception {
        int offset = 0;
        int limit = 1;
        RatingResponse response = defaultRatingDriverResponse();

        when(ratingService.getAllReviews(offset, limit)).thenReturn(new PageResponse<>(offset, limit, 1, 1, "", List.of(response)));

        mockMvc.perform(get(DEFAULT_ADDRESS)
                        .queryParam("offset", String.valueOf(offset))
                        .queryParam("limit", String.valueOf(limit)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void getAverageRating_200() throws Exception {
        Long driverId = TEST_DRIVER_ID;
        int limit = 2;
        AverageRatingResponse response = averageRatingResponse();

        when(ratingService.getResultForDriverWithLimit(driverId, limit)).thenReturn(response);

        mockMvc.perform(get(DEFAULT_ADDRESS + "/{driverId}", driverId)
                        .queryParam("limit", String.valueOf(limit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.average").value(response.average()));
    }

    @Test
    void getAverageRating_DriverNotFound_404() throws Exception {
        Long driverId = TEST_DRIVER_INVALID_ID;
        int limit = 2;
        AverageRatingResponse response = averageRatingResponse();

        when(ratingService.getResultForDriverWithLimit(driverId, limit)).thenThrow(new DataNotFoundException());

        mockMvc.perform(get(DEFAULT_ADDRESS + "/{driverId}", driverId)
                        .queryParam("limit", String.valueOf(limit)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message").value(ExceptionMessage.NOT_FOUND_MESSAGE));
    }

    @Test
    void createNewReview_RatedByDriver_201() throws Exception {
        RatingRequest request = defaultRatingDriverRequest();
        RatingResponse expectedResponse = defaultRatingDriverResponse();

        when(ratingService.addNewReviewOnRide(request)).thenReturn(expectedResponse);

        mockMvc.perform(post(DEFAULT_ADDRESS)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ratedBy").value(expectedResponse.ratedBy().getValue()));
    }

    @Test
    void createNewReview_RatedByPassenger_201() throws Exception {
        RatingRequest request = defaultRatingPassengerRequest();
        RatingResponse expectedResponse = defaultRatingPassengerResponse();

        when(ratingService.addNewReviewOnRide(request)).thenReturn(expectedResponse);

        mockMvc.perform(post(DEFAULT_ADDRESS)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ratedBy").value(expectedResponse.ratedBy().getValue()));
    }

    @Test
    void createNewReview_UserCanSendOneReviewOnRide_409() throws Exception {
        RatingRequest request = defaultRatingDriverRequest();

        when(ratingService.addNewReviewOnRide(request)).thenThrow(new OnlyOneCommentOnRideException());

        mockMvc.perform(post(DEFAULT_ADDRESS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors.message").value(ExceptionMessage.ONLY_ONE_MESSAGE));
    }

    @Test
    void updateCommentUnderReview_200() throws Exception {
        String id = TEST_ID;
        CommentaryDto request = TEST_COMMENTARY_DTO;
        RatingResponse response = defaultRatingDriverResponse();

        when(ratingService.changeCommentUnderReview(id, request)).thenReturn(response);

        mockMvc.perform(patch(DEFAULT_ADDRESS + "/{id}", id)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.commentary").value(response.commentary()));
    }

    @Test
    void updateCommentUnderReview_404() throws Exception {
        String id = TEST_INVALID_ID;
        CommentaryDto request = TEST_COMMENTARY_DTO;

        when(ratingService.changeCommentUnderReview(id, request)).thenThrow(new DataNotFoundException());

        mockMvc.perform(patch(DEFAULT_ADDRESS + "/{id}", id)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message").value(ExceptionMessage.NOT_FOUND_MESSAGE));
    }
}