package enigma.expenses_tracker.service.implementation;

import enigma.expenses_tracker.repository.CategoryRepository;
import enigma.expenses_tracker.service.CategoryService;
import enigma.expenses_tracker.utils.specification.CategorySpecification;
import enigma.expenses_tracker.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category create(Category request) {
        return categoryRepository.save(request);
    }

    @Override
    public Page<Category> getAll(Pageable pageable, String name) {
        Specification<Category> specification = CategorySpecification.getSpecification(name);
        return categoryRepository.findAll(specification, pageable);
    }

    @Override
    public Category getOne(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category with id " + id + " not found"));
    }

    @Override
    public Category update(Category request, Integer id) {
        Category category = this.getOne(id);
        category.setName(request.getName());
        return categoryRepository.save(request);
    }

    @Override
    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }
}
