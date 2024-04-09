package com.dean.getinline.controller.error;

import com.dean.getinline.constant.ErrorCode;
import com.dean.getinline.dto.APIErrorResponse;
import com.dean.getinline.exception.GeneralException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * API Exception 핸들러
 * API에 대한 ControllerAdvice 전체 컨트롤러의 동작을 감시한다.
 * 모든 응답들은 ResponseBody가 추가로 붙는다.
 *
 * 특정한 어노테이션 (RestController) 가 달린 클래스 (API)만 제어하는 에러 핸들러
 *
 * 추가로 스프링 MVC의 예외도 처리하기 위해
 * ResponseEntityExceptionHandler 를 구현해서 처리했다.
* */
@RestControllerAdvice(annotations = {RestController.class, RepositoryRestController.class})
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        return handleExceptionInternal(e, ErrorCode.VALIDATION_ERROR, HttpHeaders.EMPTY, request);
    }

    /**
     * GeneralException이 터졌을 경우
     * */
    @ExceptionHandler
    public ResponseEntity<Object> general(GeneralException e, WebRequest request) {
        return handleExceptionInternal(e, e.getErrorCode(), HttpHeaders.EMPTY, request);
    }

    /**
     * 전체적으로 에러가 터진 경우 (전체 Exception)
     *
     * APIExceptionHandler#exception(Exception)
     *
     * 아래 메서드가 RuntimeException을 잡아주기 떄문에 스택트레이스 로그가 잡히지 않는다.
     * */
    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        return handleExceptionInternal(e, ErrorCode.INTERNAL_ERROR, HttpHeaders.EMPTY, request);
    }

    /**
     * ResponseEntityExceptionHandler의 경우 스프링 MVC의 에러를 예외처리하는데
     * 이때는 body가 null로 리턴되기 떄문에 같은 동작을 하되 body가 리턴하도록 재정의 한다.
     * handleExceptionInternal를 재정의 후 super를 호출하면 body가 출력된다.
     * -> 결국 super의 Internal을 호출한다.
    * */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return handleExceptionInternal(ex, ErrorCode.valueOf((HttpStatus) statusCode), headers, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorCode errorCode, HttpHeaders headers, WebRequest request) {
        return super.handleExceptionInternal(
                e,
                APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
                headers,
                errorCode.getHttpStatus(),
                request
        );
    }
}
