package admin.adminsiteserver.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class PageInfo {
    private int currentPage;
    private int totalPages;
    private int numberOfElements;
    private int pageSize;
    private int totalElements;

    public static <T> PageInfo from(Page<T> page) {
        return new PageInfo(
                page.getNumber(),
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.getSize(),
                (int) page.getTotalElements()
        );
    }
}
