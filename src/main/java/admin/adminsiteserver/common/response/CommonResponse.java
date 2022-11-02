package admin.adminsiteserver.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class CommonResponse<T> {

    private T data;
    private PageInformation pageInfo;
    private String message;

    public static <T> CommonResponse<T> of(T data, PageInformation pageInfo, String message) {
        return new CommonResponse<>(data, pageInfo, message);
    }

    public static <T> CommonResponse<T> of(T data, String message) {
        return new CommonResponse<>(data, null, message);
    }

    public static <T> CommonResponse<T> from(String message) {
        return new CommonResponse<>(null, null, message);
    }
}
