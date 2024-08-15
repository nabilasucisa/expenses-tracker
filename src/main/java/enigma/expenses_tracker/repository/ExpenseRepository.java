package enigma.expenses_tracker.repository;

import enigma.expenses_tracker.model.Expenses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expenses, Integer>, JpaSpecificationExecutor<Expenses> {
    Page<Expenses> findAllByUserId(Specification specification, Pageable pageable, Integer id);

    @Query(value = "SELECT SUM(amount) FROM expenses WHERE user_id = :userId", nativeQuery = true)
    Double getTotalAmountSpentByUser(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM expenses WHERE category_id = :categoryId", nativeQuery = true)
    List<Expenses> findExpensesByCategory(@Param("categoryId") Long categoryId);

}
