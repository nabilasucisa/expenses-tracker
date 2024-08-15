package enigma.expenses_tracker.controller;

import enigma.expenses_tracker.model.Expenses;
import enigma.expenses_tracker.service.ExpenseService;
import enigma.expenses_tracker.utils.PageResponse;
import enigma.expenses_tracker.utils.Response;
import enigma.expenses_tracker.utils.DTO.ExpenseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody ExpenseDTO request, Authentication authentication) {
        return Response.renderJson(
                HttpStatus.CREATED,
                expenseService.create(request, authentication)
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(size = 10) Pageable pageable,
                                    @RequestParam(required = false) String status,
                                    @RequestParam(defaultValue = "dueDate", required = false) String sortBy,
                                    @RequestParam(required = false) String order,
                                    Authentication authentication) {
        Page<Expenses> task = expenseService.getAll(pageable, sortBy, order, authentication);
        PageResponse<Expenses> result = new PageResponse<>(task);
        return Response.renderJson(
                HttpStatus.OK,
                result
        );
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getOne(@PathVariable Integer id, Authentication authentication) {
        return Response.renderJson(
                HttpStatus.OK,
                expenseService.getOne(id, authentication)
        );
    }

    @GetMapping("/date-range")
    private ResponseEntity<?> getByDateRange(@PageableDefault(size = 10) Pageable pageable,
                                             @RequestParam(defaultValue = "date", required = false) String sortBy,
                                             @RequestParam(required = false) String order,
                                             @RequestBody LocalDate startDate,
                                             @RequestBody LocalDate endDate,
                                             Authentication authentication) {
        return Response.renderJson(
                HttpStatus.OK,
                expenseService.getExpensesByDateRange(startDate, endDate, pageable, sortBy, order, authentication)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody ExpenseDTO request,
                                    @PathVariable Integer id,
                                    Authentication authentication) {
        return Response.renderJson(
                HttpStatus.OK,
                expenseService.update(id, request, authentication)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id, Authentication authentication) {
        expenseService.delete(id, authentication);
        return Response.renderJson1(
                HttpStatus.NO_CONTENT
        );
    }
}
