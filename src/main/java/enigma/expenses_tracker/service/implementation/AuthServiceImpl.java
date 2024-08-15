package enigma.expenses_tracker.service.implementation;

import enigma.expenses_tracker.model.UserEntity;
import enigma.expenses_tracker.repository.UserRepository;
import enigma.expenses_tracker.service.AuthService;
import enigma.expenses_tracker.utils.DTO.AuthDTO;
import enigma.expenses_tracker.utils.Response;
import enigma.expenses_tracker.model.Role;
import enigma.expenses_tracker.security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> register(AuthDTO.RegisterRequest request) {
        if (repository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("User with this username already exists");
        }
        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Role.ROLE_USER)
                .createdAt(LocalDate.now())
                .build();

        UserEntity savedUser = repository.save(user);
        return Response.renderJson(
                HttpStatus.CREATED,
                savedUser
        );
    }

    @Override
    public AuthDTO.AuthenticationResponse login(AuthDTO.LoginRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String accessToken = jwtService.generateAccessToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            return AuthDTO.AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password!");
        }
    }

    @Override
    public AuthDTO.RefreshResponse refresh(AuthDTO.RefreshRequest request) {
        if (jwtService.validateRefreshToken(request.getRefresh_token())) {
            String username = jwtService.extractUsername(request.getRefresh_token());
            UserDetails userDetails = repository.findByUsername(username)
                    .map(user -> User.withUsername(user.getUsername())
                            .password(user.getPassword())
                            .authorities(user.getRoles())
                            .build())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            String accessToken = jwtService.generateAccessToken(userDetails);
            return AuthDTO.RefreshResponse.builder()
                    .accessToken(accessToken)
                    .build();
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }
}

