package dean.getinline.getinline.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 파라미터로 테스트하고자 하는 컨트롤러 클래스를 작성한다.
 * */
@WebMvcTest(BaseController.class)
class BaseControllerTest {

    private final MockMvc mvc;

    public BaseControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    /**
     * 테스트 목표 : localhost:8080/ 를 호출 (Get) 하면 index 페이지가 호출되어야 한다.
     * MockMvc 사용 (@AutoConfigureMockMvc) 어노테이션을 사용함면
     * MockMvc를 Autowired 바로 주입 받을 수 있다.
     * */
    @DisplayName("[view] [GET] 기본 페이지 요청")
    @Test
    void givenNothing_whenRequestingRootPage_thenReturnsIndexPage() throws Exception{
        // Given

        // When & Then
        mvc.perform(get("/"))   // andExpect로 다양한 검수할 과정 정의
                .andExpect(status().isOk())    // http status를 검수한다.
//                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))   // view이기 때문에 contentType는 Text/html
//                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))   // view이기 때문에 contentType는 Text/html
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))   // view이기 때문에 contentType는 Text/html
                .andExpect(content().string(containsString("This is default page.")))   // 실제 들어가있는 내용 검수
                .andExpect(view().name("index"))   // 매핑 테스트 진행
                .andDo(print());    // 테스트 결과를 출력


    }
}