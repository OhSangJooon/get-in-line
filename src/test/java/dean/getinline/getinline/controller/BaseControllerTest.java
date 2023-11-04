package dean.getinline.getinline.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@AutoConfigureMockMvc
@SpringBootTest
class BaseControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    /*@BeforeTestMethod
    public void setUp() {
        this.mvc = webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }*/

    /**
     * �׽�Ʈ ��ǥ : localhost:8080/ �� ȣ�� (Get) �ϸ� index �������� ȣ��Ǿ�� �Ѵ�.
     * MockMvc ��� (@AutoConfigureMockMvc) ������̼��� ����Ը�
     * MockMvc�� Autowired �ٷ� ���� ���� �� �ִ�.
     * */
    @DisplayName("[view] [GET] �⺻ ������ ��û")
    @Test
    void testRoot() throws Exception{
        // Given

        // When & Then
        mvc.perform(get("/"))   // andExpect�� �پ��� �˼��� ���� ����
                .andExpect(status().isOk())    // http status�� �˼��Ѵ�.
//                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))   // view�̱� ������ contentType�� Text/html
//                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))   // view�̱� ������ contentType�� Text/html
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))   // view�̱� ������ contentType�� Text/html
                .andExpect(content().string(containsString("Default Page!!!")))   // ���� ���ִ� ���� �˼�
                .andExpect(view().name("index"))   // ���� �׽�Ʈ ����
                .andDo(print());    // �׽�Ʈ ����� ���


    }
}