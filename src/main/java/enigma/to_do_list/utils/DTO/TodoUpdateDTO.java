package enigma.to_do_list.utils.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class TodoUpdateDTO {

    private String title;

    private String description;

    private LocalDate dueDate;

    private String status;
}
