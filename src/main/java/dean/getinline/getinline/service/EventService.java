package dean.getinline.getinline.service;

import dean.getinline.getinline.constant.ErrorCode;
import dean.getinline.getinline.constant.EventStatus;
import dean.getinline.getinline.dto.EventDTO;
import dean.getinline.getinline.exception.GeneralException;
import dean.getinline.getinline.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventService {

    // 인터페이스의 경우 스프링이 Bean 으로 생성할 수 없기 떄문에 config를 만들어 주어야한다.
    private final EventRepository eventRepository;

    /**
     * @param placeId 장소 ID
     * @param eventName 이벤트 이름
     * @param eventStatus 이벤트 상태
     * @param eventStartDateTime 시작시간
     * @param eventEndDateTime 종료시간
     * @return List
     * */
    public List<EventDTO> getEvents(Long placeId, String eventName, EventStatus eventStatus,
                                    LocalDateTime eventStartDateTime, LocalDateTime eventEndDateTime) {
        try {
            return eventRepository.findEvents(placeId, eventName, eventStatus,
                    eventStartDateTime, eventEndDateTime);
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public Optional<EventDTO> getEvent(Long eventId) {
        try {
            return eventRepository.findEvent(eventId);
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean createEvent(EventDTO eventDTO) {
        try {
            return eventRepository.insertEvent(eventDTO);
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean modifyEvent(Long eventId, EventDTO dto) {
        try {
            return eventRepository.updateEvent(eventId, dto);
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean removeEvent(Long eventId) {
        try {
            return eventRepository.deleteEvent(eventId);
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }
}
