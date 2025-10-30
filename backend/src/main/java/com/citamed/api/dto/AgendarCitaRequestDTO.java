package com.citamed.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

// DTO para el JSON de solicitud de agendamiento de cita
@Data
@NoArgsConstructor
public class AgendarCitaRequestDTO {

    // El id_paciente se obtendrá del token, no viene en el request

    private Integer id_medico;
    private LocalDateTime fecha_hora;
}