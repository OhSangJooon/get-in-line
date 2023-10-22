package dean.getinline.getinline.controller.error;

import dean.getinline.getinline.constant.ErrorCode;
import dean.getinline.getinline.dto.APIErrorResponse;
import dean.getinline.getinline.exception.GeneralException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * API Exception 핸들러
 * API에 대한 ControllerAdvice 전체 컨트롤러의 동작을 감시한다.
 * 모든 응답들은 ResponseBody가 추가로 붙는다.
* */
@RestControllerAdvice(annotations = RestController.class)
public class APIExceptionHandler {

    /**
     * GeneralException이 터졌을 경우
     * */
    @ExceptionHandler
    public ResponseEntity<APIErrorResponse> general(GeneralException e) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;


        return ResponseEntity
                .status(status)
                .body(APIErrorResponse.of(
                        false, errorCode, errorCode.getMessage(e)
                ));
    }


    /**
     * 전체적으로 에러가 터진 경우 (전체 Exception)
     *
     * APIExceptionHandler#exception(Exception)
     *
     * 아래 메서드가 RuntimeException을 잡아주기 떄문에 스택트레이스 로그가 잡히지 않는다.
     * */
    @ExceptionHandler
    public ResponseEntity<APIErrorResponse> exception(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(status)
                .body(APIErrorResponse.of(
                        false, errorCode, errorCode.getMessage(e)
                ));
    }
}
