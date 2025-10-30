package com.citamed.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Time;

// DTO para el JSON de solicitud de CREAR/ACTUALIZAR Horario
@Data
@NoArgsConstructor
public class HorarioRequestDTO {

    // Coincide con los parámetros del SP sp_Admin_CrearHorario

    private Integer id_medico;
    private Byte dia_semana; // 1=Lunes, 7=Domingo
    private Time hora_inicio;
    private Time hora_fin;
}