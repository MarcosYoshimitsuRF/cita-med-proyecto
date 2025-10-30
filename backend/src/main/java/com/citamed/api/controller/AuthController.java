package com.citamed.api.controller;

import com.citamed.api.dto.LoginRequest;
import com.citamed.api.dto.LoginResponse;
import com.citamed.api.dto.RegisterRequest;
import com.citamed.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // Ruta base pública
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Endpoint de Login (Punto 2.5.3)
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // Llama al servicio para intentar el login
        LoginResponse response = authService.attemptLogin(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint de Registro (Punto 2.5.2)
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // Devuelve un 201 Created
    public void register(@RequestBody RegisterRequest request) {
        // Llama al servicio para registrar al usuario
        authService.registerUser(request);
    }
}