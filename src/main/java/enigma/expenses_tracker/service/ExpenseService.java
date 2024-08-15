package enigma.expenses_tracker.service;

import enigma.expenses_tracker.model.Expenses;
import enigma.expenses_tracker.utils.DTO.ExpenseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    Expenses create(ExpenseDTO request, Authentication authentication);
    Page<Expenses> getAll(Pageable pageable, String sortBy, String order, Authentication authentication);
    Expenses getOne(Integer id, Authentication authentication);
    List<Expenses> getExpensesByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable, String sortBy, String order, Authentication auth);
    Expenses update(Integer id, ExpenseDTO request, Authentication authentication);
    void delete(Integer id, Authentication authentication);
}
