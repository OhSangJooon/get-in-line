package dean.getinline.getinline.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController implements ErrorController {

    @GetMapping("/")
    public String root() {
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

    @RequestMapping("/error")
    public String error() {
        return "error";
    }
}
