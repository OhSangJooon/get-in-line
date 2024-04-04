package dean.getinline.getinline.dto;

import dean.getinline.getinline.constant.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 제네릭을 사용해 Type Safety 하게 사용할 수 있다.
 * */
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class APIDataResponse<T> extends APIErrorResponse{
    private final T data;

    private APIDataResponse(T data) {
        super(true, ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.data = data;
    }

    public static <T> APIDataResponse<T> of(T data) {
        return new APIDataResponse<>(data);
    }

    public static <T> APIDataResponse<T> empty() {
        return new APIDataResponse<>(null);
    }

}
