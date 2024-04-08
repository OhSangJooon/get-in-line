package com.dean.getinline.dto;

import com.dean.getinline.constant.EventStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public record EventRequest(
        @NotNull @Positive Long placeId,
        @NotBlank String eventName,
        @NotNull EventStatus eventStatus,
        @NotNull LocalDateTime eventStartDateTime,
        @NotNull LocalDateTime eventEndDateTime,
        @NotNull @PositiveOrZero Integer currentNumberOfPeople,
        @NotNull @Positive Integer capacity,
        String memo
) {
    public static EventRequest of(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new EventRequest(
                placeId,
                eventName,
                eventStatus,
                eventStartDateTime,
                eventEndDateTime,
                currentNumberOfPeople,
                capacity,
                memo
        );
    }

    public EventDTO toDTO() {
        return EventDTO.of(
                null,
                this.placeId(),
                this.eventName(),
                this.eventStatus(),
                this.eventStartDateTime(),
                this.eventEndDateTime(),
                this.currentNumberOfPeople(),
                this.capacity(),
                this.memo(),
                null,
                null
        );
    }
}
