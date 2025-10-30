package com.citamed.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para el JSON de solicitud y respuesta de Consultorios
@Data
@NoArgsConstructor
public class ConsultorioDTO {

    // Usado para la respuesta
    private Integer id_consultorio;

    // Usado para solicitud y respuesta
    private String nombre;
    private String ubicacion;

    // Usado para la respuesta
    private boolean esta_activo;
}