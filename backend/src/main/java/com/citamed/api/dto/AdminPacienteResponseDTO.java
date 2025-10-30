package com.citamed.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para el JSON de respuesta de la lista de Pacientes (Admin)
@Data
@NoArgsConstructor
public class AdminPacienteResponseDTO {

    // Campos devueltos por el SP sp_Admin_ListarPacientes

    private Integer id_paciente;
    private Integer id_usuario;
    private String dni;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String email;
    private boolean esta_activo;
}