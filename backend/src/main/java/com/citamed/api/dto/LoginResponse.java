package com.citamed.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// DTO para el JSON de respuesta de login
@Data
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class LoginResponse {
    private String jwt;
}