package com.dean.getinline.repository;


import com.dean.getinline.domain.Event;
import com.dean.getinline.dto.EventDTO;
import com.dean.getinline.constant.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// TODO: 인스턴스 생성 편의를 위해 임시로 default 사용. repository layer 구현이 완성되면 삭제할 것
public interface EventRepository extends JpaRepository<Event, Long> {

    // default로 구현을 해놓게 되면 EventRepository를 빈에 등록할때 구현 로직을 작성 할 필요가 없다.
    default List<EventDTO> findEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime
    ) { return List.of(); }
    default Optional<EventDTO> findEvent(Long eventId) { return Optional.empty(); }
    default boolean insertEvent(EventDTO eventDTO) { return false; }
    default boolean updateEvent(Long eventId, EventDTO dto) { return false; }
    default boolean deleteEvent(Long eventId) { return false; }

}
