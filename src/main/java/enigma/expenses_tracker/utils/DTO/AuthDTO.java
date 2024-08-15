package enigma.expenses_tracker.utils.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor

public class AuthDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterRequest {
        @NotBlank(message = "Email is required!")
        private String email;

        @NotBlank(message = "Username is required!")
        private String username;

        @NotBlank(message = "Password is required!")
        private String password;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterResponse {
        private Integer id;
        private String email;
        private String username;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest {
        @NotBlank(message = "Email is required!")
        private String email;

        @NotBlank(message = "Password is required!")
        private String password;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RefreshRequest {
        @NotBlank(message = "Refresh Token is required!")
        private String refresh_token;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthenticationResponse {
        private String accessToken;
        private String refreshToken;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RefreshResponse {
        private String accessToken;
    }



}