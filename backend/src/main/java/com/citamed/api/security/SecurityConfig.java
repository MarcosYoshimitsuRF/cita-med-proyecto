package com.citamed.api.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    // 1. Define el encriptador de contraseñas (Bcrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Define el AuthenticationManager (el "cerebro" del login)
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Usa nuestra clase
        authProvider.setPasswordEncoder(passwordEncoder());   // Usa Bcrypt
        return new ProviderManager(authProvider);
    }

    // 3. Define la cadena de filtros de seguridad (reglas HTTP)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Deshabilitamos CSRF (común en APIs REST)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API sin estado
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas (Login y Registro)
                        .requestMatchers("/api/auth/**").permitAll()

                        // Rutas de Paciente
                        .requestMatchers("/api/citas/**").hasRole("PACIENTE")
                        .requestMatchers("/api/medicos/**").hasRole("PACIENTE")

                        // Rutas de Administrador
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // Cualquier otra petición debe estar autenticada
                        .anyRequest().authenticated()
                )
                // 4. Configura la API como un "Servidor de Recursos" OAuth2
                // Esto activa la validación automática de tokens JWT en CADA petición
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(jwtDecoder()))
                )
                .build();
    }

    // 5. Bean para DECODIFICAR (validar) tokens JWT
    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    // 6. Bean para CODIFICAR (crear) tokens JWT
    // (Lo usará nuestro JwtIssuer)
    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtSecret.getBytes()));
    }

    // 7. (Opcional pero recomendado) Convertidor para leer nuestros roles "scope"
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // Le decimos que busque los roles en el claim "scope" (como lo definimos en JwtIssuer)
        grantedAuthoritiesConverter.setAuthoritiesClaimName("scope");
        // Le decimos que quite el prefijo "ROLE_" (que añadimos en UserDetailsServiceImpl)
        // para que Spring use hasRole("ADMIN") en lugar de hasRole("ROLE_ADMIN")
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}