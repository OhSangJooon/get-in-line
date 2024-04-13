package com.dean.getinline.service;

import com.dean.getinline.constant.ErrorCode;
import com.dean.getinline.dto.EventDto;
import com.dean.getinline.repository.EventRepository;
import com.dean.getinline.constant.EventStatus;
import com.dean.getinline.exception.GeneralException;
import com.querydsl.core.types.Predicate;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
//    private final EntityManager entityManager; // 하이버네이트를 직접 사용하여 트랜잭션을 수동 켜고 닫기 하는 방법 그러나 결합도가 높아진다.

    public List<EventDto> getEvents(Predicate predicate) {
        try {
            return StreamSupport.stream(eventRepository.findAll(predicate).spliterator(), false)
                    .map(EventDto::of)
                    .toList();
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    /**
     * @param placeId 장소 ID
     * @param eventName 이벤트 이름
     * @param eventStatus 이벤트 상태
     * @param eventStartDateTime 시작시간
     * @param eventEndDateTime 종료시간
     * @return List
     * */
    public List<EventDto> getEvents(Long placeId, String eventName, EventStatus eventStatus,
                                    LocalDateTime eventStartDateTime, LocalDateTime eventEndDateTime) {
        try {
            return null;
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public Optional<EventDto> getEvent(Long eventId) {
        try {
            return eventRepository.findById(eventId).map(EventDto::of);
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean createEvent(EventDto eventDTO) {
//        entityManager.getTransaction().begin();
        try {
            if (eventDTO == null) {
                return false;
            }

            eventRepository.save(eventDTO.toEntity());
//            entityManager.getTransaction().commit();
            return true;
        }
        catch (Exception e) {
//            entityManager.getTransaction().rollback();
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean modifyEvent(Long eventId, EventDto dto) {
        try {
            if (eventId == null || dto == null) {
                return false;
            }

            eventRepository.findById(eventId)
                    .ifPresent(event -> eventRepository.save(dto.updateEntity(event)));

            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean removeEvent(Long eventId) {
        try {
            if (eventId == null) {
                return false;
            }

            eventRepository.deleteById(eventId);
            return true;
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }
}
