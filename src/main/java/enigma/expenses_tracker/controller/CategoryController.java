package enigma.expenses_tracker.controller;

import enigma.expenses_tracker.utils.PageResponse;
import enigma.expenses_tracker.utils.Response;
import enigma.expenses_tracker.model.Category;
import enigma.expenses_tracker.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Category request) {
        return Response.renderJson(
                HttpStatus.CREATED,
                categoryService.create(request)
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(size = 10) Pageable pageable,
                                    @RequestParam(required = false) String name ) {
        Page<Category> cat = categoryService.getAll(pageable, name);
        PageResponse<Category> result = new PageResponse<>(cat);
        return Response.renderJson(
                HttpStatus.OK,
                result
        );
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getOne(@PathVariable Integer id) {
        return Response.renderJson(
                HttpStatus.FOUND,
                categoryService.getOne(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Category request,
                                    @PathVariable Integer id) {
        return Response.renderJson(
                HttpStatus.OK,
                categoryService.update(request, id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return Response.renderJson1(
                HttpStatus.OK
        );
    }
}
