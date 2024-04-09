package com.dean.getinline.controller.api;

import com.dean.getinline.dto.APIDataResponse;
import com.dean.getinline.dto.EventRequest;
import com.dean.getinline.constant.EventStatus;
import com.dean.getinline.dto.EventResponse;
import com.dean.getinline.service.EventService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
//@RequestMapping("/api")
//@Validated // 컨트롤러의 validate를 체크하기 위해서는 이 어노테이션을 사용해야 한다.
//@RestController
public class ApiEventController {

    private final EventService eventService;

    @GetMapping("/events")
    public APIDataResponse<List<EventResponse>> getEvents(
            @Positive Long placeId, // 양수 검사
            @Size(min = 2)String eventName,
            EventStatus eventStatus,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartDateTime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndDateTime
    ) {
       List<EventResponse> response = eventService
               .getEvents(placeId, eventName, eventStatus, eventStartDateTime, eventEndDateTime)
               .stream().map(EventResponse::from).toList();

       return APIDataResponse.of(response);
    }

    /**
     * @Valid 어노테이션은 Data Object (DTO)의 유효성을 검사하기 위해 사용한다.
     * @Validated가 필요하지 않고, 검증에 걸리면
     * MethodArgumentNotValidException을 던진다
     * ResponseEntityExceptionHandler 지원 가능
     * */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events")
    public APIDataResponse<String> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        boolean result = eventService.createEvent(eventRequest.toDTO());

        return APIDataResponse.of(Boolean.toString(result));
    }

    @GetMapping("/events/{eventId}")
    public APIDataResponse<EventResponse> getEvent(@Positive @PathVariable Long eventId) {
        EventResponse eventResponse = EventResponse.from(eventService.getEvent(eventId).orElse(null));

        return APIDataResponse.of(eventResponse);
    }

    @PutMapping("/events/{eventId}")
    public APIDataResponse<String> modifyEvent(
            @Positive @PathVariable Long eventId,
            @Valid @RequestBody EventRequest eventRequest
    ) {
        boolean result = eventService.modifyEvent(eventId, eventRequest.toDTO());
        return APIDataResponse.of(Boolean.toString(result));
    }

    @DeleteMapping("/events/{eventId}")
    public APIDataResponse<String> removeEvent(@Positive @PathVariable Long eventId) {
        boolean result = eventService.removeEvent(eventId);

        return APIDataResponse.of(Boolean.toString(result));
    }
}
