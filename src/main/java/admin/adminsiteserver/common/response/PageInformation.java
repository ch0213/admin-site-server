package admin.adminsiteserver.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class PageInformation {
    private int currentPage;
    private int totalPages;
    private int numberOfElements;
    private int pageSize;
    private int totalElements;

    public static <T> PageInformation from(Page<T> page) {
        return new PageInformation(
                page.getNumber(),
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.getSize(),
                (int) page.getTotalElements()
        );
    }
}
