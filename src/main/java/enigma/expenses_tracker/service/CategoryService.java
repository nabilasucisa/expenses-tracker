package enigma.expenses_tracker.service;

import enigma.expenses_tracker.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Category create(Category request);
    Page<Category> getAll(Pageable pageable, String name);
    Category getOne(Integer id);
    Category update(Category request, Integer id);
    void delete(Integer id);
}
