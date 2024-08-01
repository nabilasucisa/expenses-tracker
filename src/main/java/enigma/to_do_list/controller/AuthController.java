package enigma.to_do_list.controller;

import enigma.to_do_list.service.AuthService;
import enigma.to_do_list.utils.DTO.AuthDTO;
import enigma.to_do_list.utils.Response;
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
