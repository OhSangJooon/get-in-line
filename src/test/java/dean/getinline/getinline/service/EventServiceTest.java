package dean.getinline.getinline.service;

import dean.getinline.getinline.constant.EventStatus;
import dean.getinline.getinline.dto.EventDTO;
import dean.getinline.getinline.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

//@SpringBootTest // SpringBootTest 의 경우 스프링 컨테이너 전체를 띄우기 때문에 속도가 느려짐
class EventServiceTest {

    @InjectMocks
    private EventService sut; // System Under Test

    @BeforeEach
    void setUp() {
        sut = new EventService(null);
    }

    @Mock
    private EventRepository eventRepository;

    @DisplayName("검색조건 없이 이벤트 검색하면, 전체 결과를 출력하여 보여준다.")
    @Test
    void givenNothing_whenSearchingEvents_thenReturnEntireEventList() {
        // Given

        // When
        List<EventDTO> list = sut.getEvents(null,null,null,null,null);

        // Then
        assertThat(list).isNotNull();

    }

    @DisplayName("검색조건과 함께 검색하면, 검색된 결과를 출력하여 보여준다.")
    @Test
    void givenSearchParams_whenSearchingEvents_thenReturnEntireEventList() {
        // Given
        Long placeId = 1L;
        String eventName = "오전 운동";
        EventStatus eventStatus = EventStatus.OPENED;
        LocalDateTime eventStartDateTime = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        LocalDateTime eventEndDateTime = LocalDateTime.of(2021, 1, 2, 0, 0, 0);

        // When
        List<EventDTO> list = sut.getEvents(placeId,eventName,eventStatus,eventStartDateTime,eventEndDateTime);

        // Then
        assertThat(list)
                .hasSize(1)
                .allSatisfy(event -> { // 파라미터로 조건을 넣으면 해당 조건을 모두 만족시키는지 테스트 람다로 작성
                    assertThat(event)
                            .hasFieldOrPropertyWithValue("placeId", placeId)
                            .hasFieldOrPropertyWithValue("eventName", eventName)
                            .hasFieldOrPropertyWithValue("eventStatus", eventStatus);
                    assertThat(event.eventStartDateTime()).isAfterOrEqualTo(eventStartDateTime); // 검색한 시간이 더 뒤에 있거나 똑같은지 체크
                    assertThat(event.eventStartDateTime()).isBeforeOrEqualTo(eventEndDateTime); // 검색한 시간이 더 앞에 있거나 똑같은지 체크
                });
    }
    @DisplayName("이벤트 ID로 존재하는 이벤트를 조회하면, 해당 이벤트 정보를 출력하여 보여준다.")
    @Test
    void givenEventId_whenSearchingExistingEvent_thenReturnsEvent() {
        // Given
        long eventId = 1L;
        EventDTO eventDTO = createEventDTO(1L, "오전 운동", true);
//        given(eventRepository.findEvent(eventId)).willReturn(Optional.of(eventDTO));

        // When
        Optional<EventDTO> result = sut.getEvent(eventId);

        // Then
        assertThat(result).hasValue(eventDTO);
//        verify(eventRepository).findEvent(eventId);
    }

    @DisplayName("이벤트 ID로 이벤트를 조회하면, 빈 정보를 출력하여 보여준다.")
    @Test
    void givenEventId_whenSearchingNonexistentEvent_thenReturnsEmptyOptional() {
        // Given
        long eventId = 2L;
//        given(eventRepository.findEvent(eventId)).willReturn(Optional.empty());

        // When
        Optional<EventDTO> result = sut.getEvent(eventId);

        // Then
        assertThat(result).isEmpty();
//        verify(eventRepository).findEvent(eventId);
    }

    @DisplayName("이벤트 정보를 주면, 이벤트를 생성하고 결과를 true 로 보여준다.")
    @Test
    void givenEvent_whenCreating_thenCreatesEventAndReturnsTrue() {
        // Given
        EventDTO dto = createEventDTO(1L, "오후 운동", false);
//        given(eventRepository.insertEvent(dto)).willReturn(true);

        // When
        boolean result = sut.createEvent(dto);

        // Then
        assertThat(result).isTrue();
//        verify(eventRepository).insertEvent(dto);
    }

    @DisplayName("이벤트 정보를 주지 않으면, 생성 중단하고 결과를 false 로 보여준다.")
    @Test
    void givenNothing_whenCreating_thenAbortCreatingAndReturnsFalse() {
        // Given
        given(eventRepository.insertEvent(null)).willReturn(false);

        // When
        boolean result = sut.createEvent(null);

        // Then
        assertThat(result).isFalse();
        verify(eventRepository).insertEvent(null);
    }

    @DisplayName("이벤트 ID와 정보를 주면, 이벤트 정보를 변경하고 결과를 true 로 보여준다.")
    @Test
    void givenEventIdAndItsInfo_whenModifying_thenModifiesEventAndReturnsTrue() {
        // Given
        long eventId = 1L;
        EventDTO dto = createEventDTO(1L, "오후 운동", false);
        given(eventRepository.updateEvent(eventId, dto)).willReturn(true);

        // When
        boolean result = sut.modifyEvent(eventId, dto);

        // Then
        assertThat(result).isTrue();
        verify(eventRepository).updateEvent(eventId, dto);
    }

    @DisplayName("이벤트 ID를 주지 않으면, 이벤트 정보 변경 중단하고 결과를 false 로 보여준다.")
    @Test
    void givenNoEventId_whenModifying_thenAbortModifyingAndReturnsFalse() {
        // Given
        EventDTO dto = createEventDTO(1L, "오후 운동", false);
        given(eventRepository.updateEvent(null, dto)).willReturn(false);

        // When
        boolean result = sut.modifyEvent(null, dto);

        // Then
        assertThat(result).isFalse();
        verify(eventRepository).updateEvent(null, dto);
    }

    @DisplayName("이벤트 ID만 주고 변경할 정보를 주지 않으면, 이벤트 정보 변경 중단하고 결과를 false 로 보여준다.")
    @Test
    void givenEventIdOnly_whenModifying_thenAbortModifyingAndReturnsFalse() {
        // Given
        long eventId = 1L;
        given(eventRepository.updateEvent(eventId, null)).willReturn(false);

        // When
        boolean result = sut.modifyEvent(eventId, null);

        // Then
        assertThat(result).isFalse();
        verify(eventRepository).updateEvent(eventId, null);
    }

    @DisplayName("이벤트 ID를 주면, 이벤트 정보를 삭제하고 결과를 true 로 보여준다.")
    @Test
    void givenEventId_whenDeleting_thenDeletesEventAndReturnsTrue() {
        // Given
        long eventId = 1L;
//        given(eventRepository.deleteEvent(eventId)).willReturn(true);

        // When
        boolean result = sut.removeEvent(eventId);

        // Then
        assertThat(result).isTrue();
//        verify(eventRepository).deleteEvent(eventId);
    }

    @DisplayName("이벤트 ID를 주지 않으면, 삭제 중단하고 결과를 false 로 보여준다.")
    @Test
    void givenNothing_whenDeleting_thenAbortsDeletingAndReturnsFalse() {
        // Given
//        given(eventRepository.deleteEvent(null)).willReturn(false);

        // When
        boolean result = sut.removeEvent(null);

        // Then
        assertThat(result).isFalse();
//        verify(eventRepository).deleteEvent(null);
    }


    private EventDTO createEventDTO(long placeId, String eventName, boolean isMorning) {
        String hourStart = isMorning ? "09" : "13";
        String hourEnd = isMorning ? "12" : "16";

        return createEventDTO(
                placeId,
                eventName,
                EventStatus.OPENED,
                LocalDateTime.parse("2021-01-01T%s:00:00".formatted(hourStart)),
                LocalDateTime.parse("2021-01-01T%s:00:00".formatted(hourEnd))
        );
    }

    private EventDTO createEventDTO(
            long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime
    ) {
        return EventDTO.of(
                placeId,
                eventName,
                eventStatus,
                eventStartDateTime,
                eventEndDateTime,
                0,
                24,
                "마스크 꼭 착용하세요",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}