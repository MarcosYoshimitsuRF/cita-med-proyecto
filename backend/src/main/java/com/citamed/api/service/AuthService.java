package com.citamed.api.service;

import com.citamed.api.dto.LoginRequest;
import com.citamed.api.dto.LoginResponse;
import com.citamed.api.dto.RegisterRequest;
import com.citamed.api.repository.UsuarioRepository;
import com.citamed.api.security.JwtIssuer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtIssuer jwtIssuer;

    /**
     * Lógica de negocio para el Login (Punto 2.5.3)
     */
    public LoginResponse attemptLogin(LoginRequest request) {

        // 1. Llama al AuthenticationManager (definido en SecurityConfig)
        // Este manager usará UserDetailsServiceImpl para buscar al usuario
        // y Bcrypt para comparar las contraseñas.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 2. Si la autenticación es exitosa, se guarda en el contexto
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Se genera el token JWT usando nuestro JwtIssuer
        String jwt = jwtIssuer.issueToken(authentication);

        // 4. Se devuelve la respuesta
        return new LoginResponse(jwt);
    }

    /**
     * Lógica de negocio para el Registro (Punto 2.5.2)
     */
    @Transactional // Asegura que la llamada al SP sea transaccional
    public void registerUser(RegisterRequest request) {

        // 1. Hashear el password antes de enviarlo al SP
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // 2. Llamar al SP de la Fase 1
        usuarioRepository.sp_RegistrarPaciente(
                request.getEmail(),
                hashedPassword,
                request.getDni(),
                request.getNombres(),
                request.getApellidos(),
                request.getTelefono()
        );
    }
}