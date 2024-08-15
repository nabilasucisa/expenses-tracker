package enigma.expenses_tracker.utils.DTO;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class ExpenseDTO {
    private String description;

    private Long amount;

    private LocalDate date;

    private Integer category_id;
}
