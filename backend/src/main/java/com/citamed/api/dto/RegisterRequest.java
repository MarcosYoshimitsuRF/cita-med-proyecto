package com.citamed.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para el JSON de solicitud de registro
// Coincide con los parámetros del SP sp_RegistrarPaciente
@Data
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String dni;
    private String nombres;
    private String apellidos;
    private String telefono;
}