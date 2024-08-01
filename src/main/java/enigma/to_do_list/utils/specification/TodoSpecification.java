package enigma.to_do_list.utils.specification;

import enigma.to_do_list.model.Todo;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoSpecification {
    public static Specification<Todo> getSpecification(Integer userId, String status, String sortBy, String order) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null && !status.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

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
