package enigma.to_do_list.utils.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import enigma.to_do_list.model.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String name;

    private String username;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // supaya tidak di-return di response API
    private String password;

    private String roles;
}
