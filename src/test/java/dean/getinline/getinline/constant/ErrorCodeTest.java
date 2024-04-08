package dean.getinline.getinline.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ErrorCodeTest {
    @ParameterizedTest(name = "[{index}] {0} ===> {1}")
    // @MethodSource에는 메서드 이름을 지정할 수 있다. @MethodSource("givenExceptionWithMessage_whenGettingMessage_thenReturnsMessage")
    @MethodSource // 입력 소스로 메서드를 사용하겠다
    @DisplayName("예외를 받으면, 예외 메시지가 포함된 메시지 출력")
    void givenExceptionWithMessage_whenGettingMessage_thenReturnsMessage(ErrorCode sut, String expected) {
        // Given
        Exception e = new Exception("This is test message.");

        // When
        String actual = sut.getMessage(e);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    /*
    메서드 명을 일치 시키며 스트림을 사용한다.
    arguments에 있는 스트림의 매개변수를 담아서 @ParameterizedTest와 @MethodSource를 사용하면
    아규먼트의 수 만큼 테스트를 실행한다.
    그리고 테스트하려는 메서드의 시그니쳐를 arguments의 타입과 같이 맞춰준다.
    */
    static Stream<Arguments> givenExceptionWithMessage_whenGettingMessage_thenReturnsMessage() {
        return Stream.of(
                arguments(ErrorCode.OK, "Ok - This is test message."),
                arguments(ErrorCode.BAD_REQUEST, "Bad request - This is test message."),
                arguments(ErrorCode.SPRING_BAD_REQUEST, "Spring-detected bad request - This is test message."),
                arguments(ErrorCode.VALIDATION_ERROR, "Validation error - This is test message."),
                arguments(ErrorCode.INTERNAL_ERROR, "Internal error - This is test message."),
                arguments(ErrorCode.SPRING_INTERNAL_ERROR, "Spring-detected internal error - This is test message."),
                arguments(ErrorCode.DATA_ACCESS_ERROR, "Data access error - This is test message.")
        );
    }

    @ParameterizedTest(name = "[{index}] \"{0}\" ===> \"{1}\"")
    @MethodSource
    @DisplayName("에러 메시지를 받으면, 해당 에러 메시지로 출력")
    void givenMessage_whenGettingMessage_thenReturnsMessage(String input, String expected) {
        // Given

        // When
        String actual = ErrorCode.INTERNAL_ERROR.getMessage(input);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> givenMessage_whenGettingMessage_thenReturnsMessage() {
        return Stream.of(
                arguments(null, ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("", ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("   ", ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("This is test message.", "This is test message.")
        );
    }

    @DisplayName("toString() 호출 포맷")
    @Test
    void givenErrorCode_whenToString_thenReturnsSimplifiedToString() {
        // Given

        // When
        String result = ErrorCode.INTERNAL_ERROR.toString();

        // Then
        assertThat(result).isEqualTo("INTERNAL_ERROR (20000)");
    }
}