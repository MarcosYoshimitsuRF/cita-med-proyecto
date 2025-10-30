package com.citamed.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para el JSON de solicitud de CREAR y ACTUALIZAR Médico
@Data
@NoArgsConstructor
public class MedicoRequestDTO {

    // Coincide con los parámetros de los SPs
    // sp_Admin_CrearMedico y sp_Admin_ActualizarMedico

    private String nombres;
    private String apellidos;
    private String especialidad;
    private Integer id_consultorio_asignado; // Puede ser nulo
}