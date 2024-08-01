package enigma.to_do_list.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final UserSecurity userSecurity;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disabling csrf
                .csrf().disable()
                // Setting up endpoints authorizations
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints -> Permit All for auth endpoints (register and login feature)
                        .requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll()

                        // Task Controller
                        .requestMatchers(HttpMethod.POST, "/api/todos").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/todos").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/todos/*").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/todo/tasks/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/todo/tasks/*").authenticated()

                        // User endpoints
                        .requestMatchers(HttpMethod.POST, "/api/admin/users").access(adminSuperadminAuthorizationManager())
                        .requestMatchers(HttpMethod.POST, "/api/admin/super-admin").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/admin/users").access(adminSuperadminAuthorizationManager())
                        .requestMatchers(HttpMethod.GET, "/api/admin/users/{id}").access(adminSuperadminAuthorizationManager())
                        .requestMatchers(HttpMethod.PATCH, "/api/admin/users/{id}/role").access(adminSuperadminAuthorizationManager())

                        // Any other request
                        .anyRequest().authenticated()
                )
                // Configure session management to be stateless
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Set up a custom authentication provider and add authentication filter
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean AuthorizationManager<RequestAuthorizationContext> adminSuperadminAuthorizationManager() {
        AuthorizationManager<RequestAuthorizationContext> adminAuth = AuthorityAuthorizationManager.hasAuthority("ROLE_ADMIN");
        AuthorizationManager<RequestAuthorizationContext> userAuth = AuthorityAuthorizationManager.hasAuthority("ROLE_USER");
        AuthorizationManager<RequestAuthorizationContext> superadmin = AuthorityAuthorizationManager.hasAuthority("ROLE_SUPER_ADMIN");
        return (authentication, context) -> {
            if (superadmin.check(authentication, context).isGranted()) {
                return new AuthorizationDecision(true);
            } else if (adminAuth.check(authentication, context).isGranted()) {
                return new AuthorizationDecision(true);
            } else {
                return new AuthorizationDecision(false);
            }
        };
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> userAuthorizationManager() {
        AuthorizationManager<RequestAuthorizationContext> adminAuth = AuthorityAuthorizationManager.hasAuthority("ROLE_ADMIN");
        AuthorizationManager<RequestAuthorizationContext> userAuth = AuthorityAuthorizationManager.hasAuthority("ROLE_USER");
        AuthorizationManager<RequestAuthorizationContext> superadmin = AuthorityAuthorizationManager.hasAuthority("ROLE_SUPER_ADMIN");

        return (authentication, context) -> {
            if (superadmin.check(authentication, context).isGranted()) {
                return new AuthorizationDecision(true);
            } else {
                try {
                    Integer userId = Integer.valueOf(context.getVariables().get("id"));

                    if (userAuth.check(authentication, context).isGranted() && userSecurity.hasUserId(authentication.get(), userId)) {
                        return new AuthorizationDecision(true);
                    }

                    if (adminAuth.check(authentication, context).isGranted() && userSecurity.hasUserId(authentication.get(), userId)) {
                        return new AuthorizationDecision(true);
                    }

                    return new AuthorizationDecision(false);
                } catch (NumberFormatException e) {
                    return new AuthorizationDecision(false);
                }
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Add your frontend URL here
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
