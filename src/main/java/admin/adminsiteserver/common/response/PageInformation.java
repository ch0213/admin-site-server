package admin.adminsiteserver.common.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
public class PageInformation {
    private int currentPage;
    private int totalPages;
    private int numberOfElements;
    private int pageSize;
    private int totalElements;

    public PageInformation(int currentPage, int totalPages, int numberOfElements, int pageSize, int totalElements) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.numberOfElements = numberOfElements;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }

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
