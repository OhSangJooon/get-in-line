package com.dean.getinline;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
* SpringBootTest 어노테이션의 속성중 WebEnvironment는
 * MOCK, RANDOM_PORT, DEFINED_PORT, NONE 네가지가 존재
 * <p>즉 애플리케이션 컨텍스트의 웹 환경을 결정해준다. </p>
 * <ul>
 *     <li>MOCK(기본값) : mock serviet, embedded server 동작 X</li>
 *     <li>RANDOM_PORT : 랜덤 포트, embedded server 동작</li>
 *     <li>DEFINED_PORT : 포트지정, embedded server 동작</li>
 *     <li>NONE : 웹 환경 구성 안함, embedded server 동작 X</li>
 * </ul>
 *
 * <p>Slice Test (Auto-configured Test) : 스프링 애플리케이션에서 내가 필요한 일부분의 자동 설정만 불러오는 방법</p>
 * <p> Ex) DataJdbcTest, WebMvcTest 등</p>
 *
 * @WebMvcTest : 스프링 MVC 컨트롤러 레이어를 Slice Test 할때 사용
 *              MockMvc  빈을 자동 설정하고 테스트에 사용
 *              로드할 컨트롤러 클래스 지정 가능 (기본동작 : 전체 컨트롤러 로드)
* */
@Disabled("다른 테스트의 속도 향상을 위해 비활성화")
@DisplayName("스프링 부트 기본 테스트")
@SpringBootTest
class GetInLineApplicationTests {

    @Test
    void contextLoads() {
    }

}
