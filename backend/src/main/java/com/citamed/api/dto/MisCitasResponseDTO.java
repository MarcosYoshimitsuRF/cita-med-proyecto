package com.citamed.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

// DTO para el JSON de respuesta del historial de citas
@Data
@NoArgsConstructor
public class MisCitasResponseDTO {

    // Campos devueltos por el SP sp_ObtenerCitasPorPaciente
    private Integer id_cita;
    private LocalDateTime fecha_hora;
    private String estado;
    private String medico_nombres;
    private String medico_apellidos;
    private String especialidad;
    private String consultorio_nombre;
}