package dean.getinline.getinline.controller;

import dean.getinline.getinline.exception.GeneralException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
* 에러페이지를 넘겨줄때 화이트 라벨 에러페이지가 아닌
* body 형태로 넘겨주기 위해서는
* 클라이언트가 Request 요청을 보낼때 헤더에 Accept (응답을 받을 수 있는 형태)를 설정해야 한다.
* 브라우저는 기본값 : text/html
*
* @ExceptionHandler에 파라미터로 특정 Exception을 넣어주면 특정 예외만 처리 가능하다.
* Ex) @ExceptionHandler(RuntimeException.class, IOException.class)
*
* @ControllerAdvice : VIEW 예외처리시 사용
* @RestControllerAdvice : API 예외처리시 사용
*   - ExceptionHandler를 모아서 글로벌하게 적용할 때 쓰는 어노테이션
*
* @ControllerAdvice(basePackages = "dean.getinline.getinline.controller")
* @ControllerAdvice(basePackageClasses = BaseController.class)    // 해당 패키지를 대표하는 클래스 아무거나 사용해도 된다.
*                   annotations : 적용 범위를 특정 애노테이션을 사용한 컨트롤러로 지정
*
* ResponseEntityExceptionHandler : 스프링 MVC 에서 내부적으로 발생하는 예외들을 처리하는 클래스
*
* */

@Controller
public class BaseController {

    @GetMapping("/")
    public String root() throws Exception {
//        throw new GeneralException("Test!!");
        return "index";
        /*
        index라는 파일명의 위치를 찾지 못한다 (루트 경로를 못찾는다.)
        그때는 main 경로 안의 webapp 이라는 경로를 추가해 그 위치에 추가하면 된다.
        (스프링부트는 내장 톰캣이라 톰캣이 JSP라고 인식)

        프로젝트 구조 -> 모듈 -> 웹 리소스 디렉토리 -> 루트경로 지정
        application.properties에 설정 추가 (spring.mvc.view.suffix=html)

        위 과정을 생략하고 간단한 방법은
        템플릿 엔진을 추가하고 AutoConfiguration을 사용한다

        타임리프를 사용하면 리소스 -> templates가 루트가 된다.
        */
    }
}
