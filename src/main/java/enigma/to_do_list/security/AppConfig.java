package enigma.to_do_list.security;

import enigma.to_do_list.model.UserEntity;
import enigma.to_do_list.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final UserRepository repository;

    @Bean
    // UserDetailService is a built-in interface in Spring Security that is used to retrieve user-related data
    public UserDetailsService userDetailsService() {
        // Find a specific user by the username, if it's not found throw exception massage "User not found"
        return email -> {
            UserEntity user = repository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singleton(user.getRoles())
            );
        };
//        return email -> (UserDetails) repository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProviderBcrypt() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManagerBcrypt(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    // Encode the password using BCrypt for hashing the passwords
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
