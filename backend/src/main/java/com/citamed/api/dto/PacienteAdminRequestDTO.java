package com.citamed.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para el JSON de solicitud de ACTUALIZAR Paciente (Admin)
@Data
@NoArgsConstructor
public class PacienteAdminRequestDTO {

    // Coincide con los parámetros del SP sp_Admin_ActualizarPaciente

    private String dni;
    private String nombres;
    private String apellidos;
    private String telefono;
}