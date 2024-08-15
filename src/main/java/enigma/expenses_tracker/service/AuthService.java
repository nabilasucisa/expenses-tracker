package enigma.expenses_tracker.service;

import enigma.expenses_tracker.utils.DTO.AuthDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> register(AuthDTO.RegisterRequest request);
    AuthDTO.AuthenticationResponse login(AuthDTO.LoginRequest request);
    AuthDTO.RefreshResponse refresh(AuthDTO.RefreshRequest request);
}
