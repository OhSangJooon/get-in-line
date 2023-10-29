package dean.getinline.getinline.controller.error;

import dean.getinline.getinline.constant.ErrorCode;
import dean.getinline.getinline.dto.APIErrorResponse;
import dean.getinline.getinline.exception.GeneralException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ModelAndView general(GeneralException e, HttpServletResponse response) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;


        return new ModelAndView(
                "error",
                Map.of(
                         "statusCode", status.value()
                        ,"errorCode", errorCode
                        ,"message", errorCode.getMessage(e)
                ),
                status
        );
    }


    /**
     * 전체적으로 에러가 터진 경우 (전체 Exception)
     * */
    @ExceptionHandler
    public ModelAndView exception(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ModelAndView(
                "error",
                Map.of(
                        "statusCode", status.value()
                        ,"errorCode", errorCode
                        ,"message", errorCode.getMessage(e)
                ),
                status
        );
    }
}
