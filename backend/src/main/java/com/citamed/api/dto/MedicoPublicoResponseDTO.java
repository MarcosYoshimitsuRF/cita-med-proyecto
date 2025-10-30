package com.citamed.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para el JSON de respuesta de la lista pública de médicos
@Data
@NoArgsConstructor
public class MedicoPublicoResponseDTO {

    // Campos devueltos por el SP sp_ListarMedicosPublico
    private Integer id_medico;
    private String nombres;
    private String apellidos;
    private String especialidad;
}