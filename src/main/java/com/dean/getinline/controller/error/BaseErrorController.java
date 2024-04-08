package com.dean.getinline.controller.error;

import com.dean.getinline.constant.ErrorCode;
import com.dean.getinline.dto.APIErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


/**
 * 공통에러 페이지
 * ErrorController 인터페이스를 구현해서 작성했다.
 * 에러페이지를 커스텀 해서 사용하기 위해서는 ErrorController 인터페이스를 구현해야 한다.
 * */
@Controller
public class BaseErrorController implements ErrorController {

    /**
     * 공통 에러 페이지로 커스텀한 GeneralException으로 잡지 못한 경우
     * 기본 에러 컨트롤러를 타게 된다.
     * 위는 Html을 리턴하고 아래는 ResponseEntity를 리턴한다.
     * */
    @RequestMapping(path = "/error", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletResponse response) {
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;

        return new ModelAndView("error",
                Map.of(
                        "statusCode", status.value()
                        ,"errorCode", errorCode
                        ,"message", errorCode.getMessage(status.getReasonPhrase())
                ),
                status
        );
    }

    @RequestMapping("/error")
    public ResponseEntity<APIErrorResponse> error(HttpServletResponse response) {
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;

        return ResponseEntity
                .status(status)
                .body(APIErrorResponse.of(false, errorCode));
    }
}
