package com.citamed.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// DTO (Data Transfer Object) para el JSON de solicitud de login
// Usa Lombok para generar getters, setters y constructor
@Data
@NoArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}