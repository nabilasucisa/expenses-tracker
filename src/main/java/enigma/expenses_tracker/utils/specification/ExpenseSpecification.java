package enigma.expenses_tracker.utils.specification;

import enigma.expenses_tracker.model.Expenses;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ExpenseSpecification {
    public static Specification<Expenses> getSpecification(Integer userId, String sortBy, String order) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));
            }

            query.where(predicates.toArray(new Predicate[0]));

            if (sortBy != null && !sortBy.isEmpty()) {
                if ("desc".equalsIgnoreCase(order)) {
                    query.orderBy(criteriaBuilder.desc(root.get(sortBy)));
                } else {
                    query.orderBy(criteriaBuilder.asc(root.get(sortBy)));
                }
            }

            return query.getRestriction();
        };
    }
}
