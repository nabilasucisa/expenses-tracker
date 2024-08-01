package enigma.to_do_list.service;

import enigma.to_do_list.model.UserEntity;
import enigma.to_do_list.utils.DTO.AuthDTO;
import enigma.to_do_list.utils.DTO.AuthDTO.AuthenticationResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> register(AuthDTO.RegisterRequest request);
    AuthenticationResponse login(AuthDTO.LoginRequest request);
    AuthDTO.RefreshResponse refresh(AuthDTO.RefreshRequest request);
}
