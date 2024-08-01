package enigma.to_do_list.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import java.util.List;

@Getter
@Setter
public class PageResponse<T>{
    private List<T> items;
    private Long totalItems;
    private Integer currentPage;
    private Integer totalPages;

    public PageResponse(Page<T> page) {
        this.items = page.getContent();
        this.totalItems = page.getTotalElements();
        this.currentPage = page.getNumber();
        this.totalPages = page.getTotalPages();
    }
}