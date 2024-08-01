package enigma.to_do_list.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class WebResponse<T> {
    private String status;
    private T body;

    @JsonIgnore
    private String message;
}
