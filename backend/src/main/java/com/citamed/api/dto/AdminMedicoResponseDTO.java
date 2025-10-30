package com.citamed.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para el JSON de respuesta de la lista de Médicos (Admin)
@Data
@NoArgsConstructor
public class AdminMedicoResponseDTO {

    // Campos devueltos por el SP sp_Admin_ListarMedicos
    // Incluye todos los campos de la tabla Medicos + nombre_consultorio

    private Integer id_medico;
    private String nombres;
    private String apellidos;
    private String especialidad;
    private Integer id_consultorio_asignado;
    private boolean esta_activo;

    // Campo del JOIN
    private String nombre_consultorio;
}