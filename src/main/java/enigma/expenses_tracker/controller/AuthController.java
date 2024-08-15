package enigma.expenses_tracker.controller;

import enigma.expenses_tracker.service.AuthService;
import enigma.expenses_tracker.utils.DTO.AuthDTO;
import enigma.expenses_tracker.utils.Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Transactional
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthDTO.RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO.LoginRequest request) {
        return Response.renderJson(
                HttpStatus.OK,
                authService.login(request)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody AuthDTO.RefreshRequest request) {
        return Response.renderJson(
                HttpStatus.OK,
                authService.refresh(request)
        );
    }


}
