package dean.getinline.getinline.controller.api;

import dean.getinline.getinline.constant.ErrorCode;
import dean.getinline.getinline.constant.PlaceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(APIPlaceController.class)
class APIPlaceControllerTest {
    private final MockMvc mvc;

    public APIPlaceControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[API] [GET] 장소 리스트 조회")
    @Test
    void givenNothing_whenRequestingPlaces_thenReturnsListOfPlacesInStandardResponse() throws Exception {
        // Given

        /**
         * jsonPath : Json을 검사한다.
         * */

        // When & Then
        mvc.perform(get("/api/places"))   // andExpect로 다양한 검수할 과정 정의
                .andExpect(status().isOk())     // 200으로 떨어지는가
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))   // MediaType 가 Application_JSON으로 나오는가
                .andExpect(jsonPath("$.data").isArray())                // Body의 데이터가 Array 형태인가
                .andExpect(jsonPath("$.data[0].placeType").value(PlaceType.COMMON.name()))  // 아래로는 데이터의 첫번째의 필드들이 충족되는가
                .andExpect(jsonPath("$.data[0].placeName").value("랄라배드민턴장"))
                .andExpect(jsonPath("$.data[0].address").value("서울시 강남구 강남대로 1234"))
                .andExpect(jsonPath("$.data[0].phoneNumber").value("010-1234-5678"))
                .andExpect(jsonPath("$.data[0].capacity").value(30))
                .andExpect(jsonPath("$.data[0].memo").value("신장개업"))
                .andExpect(jsonPath("$.success").value(true))       // 에러 메시지가 있는가? 커스텀한 APIDataResponse를 리턴하기 떄문에 존재한다.
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));

    }

    /**
     * 표준응답에 장소 데이터를 넣어서 보내준다.
     * */
    @DisplayName("[API] [GET] 단일 장소 조회 - 장소 있는 경우")
    @Test
    void givenPlaceANdItsId_whenRequestingPlace_thenReturnsPlaceInStandardResponse() throws Exception {
        // Given
        int placeId = 1;    // PathVariable이기 때문에 정수 1번 생성

        // When & Then
        mvc.perform(get("/api/places/" + placeId))   // andExpect로 다양한 검수할 과정 정의
                .andExpect(status().isOk())     // 200으로 떨어지는가
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))   // MediaType 가 Application_JSON으로 나오는가
                .andExpect(jsonPath("$.data").isMap())                //  Body의 data가 Map인가??
                .andExpect(jsonPath("$.data.placeType").value(PlaceType.COMMON.name()))  // 아래로는 데이터의 첫번째의 필드들이 충족되는가
                .andExpect(jsonPath("$.data.placeName").value("랄라배드민턴장"))
                .andExpect(jsonPath("$.data.address").value("서울시 강남구 강남대로 1234"))
                .andExpect(jsonPath("$.data.phoneNumber").value("010-1234-5678"))
                .andExpect(jsonPath("$.data.capacity").value(30))
                .andExpect(jsonPath("$.data.memo").value("신장개업"))
                .andExpect(jsonPath("$.success").value(true))       // 에러 메시지가 있는가? 커스텀한 APIDataResponse를 리턴하기 떄문에 존재한다.
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));

    }

    @DisplayName("[API] [GET] 단일 장소 조회 - 장소 없는 경우")
    @Test
    void givenPlacedId_whenRequestingPlace_thenReturnsEmptyStandardResponse() throws Exception {
        // Given
        int placeId = 2;    // PathVariable이기 때문에 정수 1번 생성

        // When & Then
        mvc.perform(get("/api/places/" + placeId))   // andExpect로 다양한 검수할 과정 정의
                .andExpect(status().isOk())     // 200으로 떨어지는가
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))   // MediaType 가 Application_JSON으로 나오는가
                .andExpect(jsonPath("$.data").isEmpty())                // Body의 data가 비어있는가?
                .andExpect(jsonPath("$.success").value(true))       // 에러 메시지가 있는가? 커스텀한 APIDataResponse를 리턴하기 떄문에 존재한다.
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));

    }
}