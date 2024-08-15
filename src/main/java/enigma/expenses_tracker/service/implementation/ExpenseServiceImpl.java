package enigma.expenses_tracker.service.implementation;

import enigma.expenses_tracker.model.Expenses;
import enigma.expenses_tracker.repository.ExpenseRepository;
import enigma.expenses_tracker.service.ExpenseService;
import enigma.expenses_tracker.utils.specification.ExpenseSpecification;
import enigma.expenses_tracker.model.Category;
import enigma.expenses_tracker.model.UserEntity;
import enigma.expenses_tracker.service.CategoryService;
import enigma.expenses_tracker.service.UserService;
import enigma.expenses_tracker.utils.DTO.ExpenseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryService categoryService;
    private final UserService userService;

    @Override
    public Expenses create(ExpenseDTO request, Authentication auth) {
        UserEntity user = userService.getByEmail(auth.getName());
        Category category = categoryService.getOne(request.getCategory_id());
        Expenses expenses = new Expenses();
        expenses.setUser(user);
        expenses.setCategory(category);
        expenses.setDescription(request.getDescription());
        expenses.setAmount(request.getAmount());
        expenses.setDate(request.getDate());
        return expenseRepository.save(expenses);
    }

    @Override
    public Page<Expenses> getAll(Pageable pageable, String sortBy, String order, Authentication auth) {
        UserEntity user = userService.getByEmail(auth.getName());
        Integer id = user.getId();

        if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            Specification<Expenses> specification = ExpenseSpecification.getSpecification(null, sortBy, order);
            return expenseRepository.findAll(specification, pageable);
        } else {
            Specification<Expenses> specification = ExpenseSpecification.getSpecification(id, sortBy, order);
            return expenseRepository.findAll(specification, pageable);
        }
    }

    @Override
    public Expenses getOne(Integer id, Authentication auth) {
        UserEntity user = userService.getByEmail(auth.getName());
        Optional<Expenses> todo = expenseRepository.findById(id);

        if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            return expenseRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException());
        }
        if (todo.get().getUser() == user) {
            return expenseRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException());
        } else return null;
    }

    @Override
    public List<Expenses> getExpensesByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable, String sortBy, String order, Authentication auth) {
        Page<Expenses> expenses = this.getAll(pageable, sortBy, order, auth);
        return expenses.stream()
                .filter(expense -> !expense.getDate().isBefore(startDate) && !expense.getDate().isAfter(endDate))
                .collect(Collectors.toList());
    }

    @Override
    public Expenses update(Integer id, ExpenseDTO request, Authentication auth) {
        Expenses expenses = this.getOne(id, auth);
        UserEntity user = userService.getByEmail(auth.getName());
        Category category = categoryService.getOne(request.getCategory_id());
        expenses.setUser(user);
        expenses.setCategory(category);
        expenses.setDescription(request.getDescription());
        expenses.setAmount(request.getAmount());
        expenses.setDate(request.getDate());
        return expenseRepository.save(expenses);
    }

    @Override
    public void delete(Integer id, Authentication auth) {
        UserEntity user = userService.getByEmail(auth.getName());
        Expenses expenses = this.getOne(id, auth);
        if (expenses.getUser() == user) {
            expenseRepository.deleteById(id);
        }
    }


}
