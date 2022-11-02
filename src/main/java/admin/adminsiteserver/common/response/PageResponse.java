package admin.adminsiteserver.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class PageResponse<T> {
    private T data;
    private PageInformation pageInformation;

    public static <T> PageResponse<T> of(T data, PageInformation pageInformation) {
        return new PageResponse<>(data, pageInformation);
    }
}
