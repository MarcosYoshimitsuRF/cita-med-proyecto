package com.citamed.api.service;

import com.citamed.api.dto.AgendarCitaRequestDTO;
import com.citamed.api.dto.MisCitasResponseDTO;
import com.citamed.api.model.Paciente;
import com.citamed.api.repository.CitaRepository;
import com.citamed.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    /**
     * Obtiene el ID del Paciente desde el email del token (JWT Subject).
     * Esta es la función central de SEGREGACIÓN DE DATOS.
     */
    private Integer getPacienteIdFromEmail(String email) {
        return pacienteRepository.findByUsuarioEmail(email)
                .map(Paciente::getId_paciente)
                .orElseThrow(() -> new AccessDeniedException("Acceso denegado: No se encontró paciente asociado a este usuario."));
    }

    /**
     * Lógica para GET /api/citas/mis-citas (Punto 3.1.2)
     */
    @Transactional(readOnly = true)
    public List<MisCitasResponseDTO> getMisCitas(String email) {
        // 1. Obtener ID del paciente (Segregación de datos)
        Integer id_paciente = getPacienteIdFromEmail(email);

        // 2. Llamar al SP
        List<Object[]> results = citaRepository.sp_ObtenerCitasPorPaciente(id_paciente);

        // 3. Mapear la respuesta de List<Object[]> a List<MisCitasResponseDTO>
        return results.stream().map(row -> {
            MisCitasResponseDTO dto = new MisCitasResponseDTO();
            dto.setId_cita(((BigInteger) row[0]).intValue()); // MySQL puede devolver BigInteger
            dto.setFecha_hora(((Timestamp) row[1]).toLocalDateTime()); // Convertir Timestamp a LocalDateTime
            dto.setEstado((String) row[2]);
            dto.setMedico_nombres((String) row[3]);
            dto.setMedico_apellidos((String) row[4]);
            dto.setEspecialidad((String) row[5]);
            dto.setConsultorio_nombre((String) row[6]);
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * Lógica para PUT /api/citas/{id}/cancelar (Punto 3.1.3)
     */
    @Transactional
    public void cancelarCitaPaciente(Integer idCita, String email) {
        // 1. Obtener ID del paciente (Segregación de datos)
        Integer id_paciente = getPacienteIdFromEmail(email);

        // 2. Llamar al SP
        // El SP sp_CancelarCitaPaciente tiene seguridad interna (WHERE id_paciente = ?),
        // por lo que el paciente solo puede cancelar sus propias citas.
        citaRepository.sp_CancelarCitaPaciente(idCita, id_paciente);
    }

    /**
     * Lógica para POST /api/citas (Punto 3.1.4)
     */
    @Transactional
    public void agendarCita(AgendarCitaRequestDTO request, String email) {
        // 1. Obtener ID del paciente (Segregación de datos)
        Integer id_paciente = getPacienteIdFromEmail(email);

        // 2. Llamar al SP
        // Inyectamos el id_paciente del token, ignorando cualquier otro valor.
        citaRepository.sp_AgendarCita(
                id_paciente,
                request.getId_medico(),
                request.getFecha_hora()
        );
    }

    // --- NUEVO MÉTODO AÑADIDO (Requerido para Paso 3.3.5) ---

    /**
     * Lógica para PUT /api/admin/citas/{id}/cancelar (Punto 3.3.5)
     */
    @Transactional
    public void adminCancelarCita(Integer idCita) {
        // Llama al SP de admin, que no requiere validación de paciente
        citaRepository.sp_Admin_CancelarCita(idCita);
    }
}