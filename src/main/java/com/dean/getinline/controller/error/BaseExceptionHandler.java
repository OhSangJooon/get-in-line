package com.dean.getinline.controller.error;

import com.dean.getinline.constant.ErrorCode;
import com.dean.getinline.exception.GeneralException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * View에 대한 ControllerAdvice 전체 컨트롤러의 동작을 감시한다.
 * 파라미터로 basePackages를 지정하지 않음.
 *
 * 스코프를 넓혀 @Controller를 적용한다.
* */
@ControllerAdvice
public class BaseExceptionHandler {

    /**
     * GeneralException이 터졌을 경우
     * */
    @ExceptionHandler
    public ModelAndView general(GeneralException e) {
        ErrorCode errorCode = e.getErrorCode();


        return new ModelAndView(
                "error",
                Map.of(
                         "statusCode", errorCode.getHttpStatus().value()
                        ,"errorCode", errorCode
                        ,"message", errorCode.getMessage()
                ),
                errorCode.getHttpStatus()
        );
    }


    /**
     * 전체적으로 에러가 터진 경우 (전체 Exception)
     * */
    @ExceptionHandler
    public ModelAndView exception(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;

        return new ModelAndView(
                "error",
                Map.of(
                        "statusCode", errorCode.getHttpStatus().value(),
                        "errorCode", errorCode,
                        "message", errorCode.getMessage(e)
                ),
                errorCode.getHttpStatus()
        );
    }
}
