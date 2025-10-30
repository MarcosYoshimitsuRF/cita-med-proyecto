package com.citamed.api.service;

import com.citamed.api.dto.AdminMedicoResponseDTO;
import com.citamed.api.dto.MedicoRequestDTO;
import com.citamed.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminMedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    /**
     * Lógica para GET /api/admin/medicos
     */
    @Transactional(readOnly = true)
    public List<AdminMedicoResponseDTO> listarMedicosAdmin() {

        List<Object[]> results = medicoRepository.sp_Admin_ListarMedicos();

        // Mapear la respuesta de List<Object[]> a List<AdminMedicoResponseDTO>
        return results.stream().map(row -> {
            AdminMedicoResponseDTO dto = new AdminMedicoResponseDTO();
            dto.setId_medico(((BigInteger) row[0]).intValue());
            dto.setNombres((String) row[1]);
            dto.setApellidos((String) row[2]);
            dto.setEspecialidad((String) row[3]);
            dto.setId_consultorio_asignado((Integer) row[4]);
            dto.setEsta_activo(((Byte) row[5]) != 0); // Convertir TINYINT a boolean
            // row[6] (created_at) y row[7] (updated_at) se omiten
            dto.setNombre_consultorio((String) row[8]); // Campo del JOIN
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * Lógica para POST /api/admin/medicos
     */
    @Transactional
    public void crearMedico(MedicoRequestDTO request) {
        medicoRepository.sp_Admin_CrearMedico(
                request.getNombres(),
                request.getApellidos(),
                request.getEspecialidad(),
                request.getId_consultorio_asignado()
        );
    }

    /**
     * Lógica para PUT /api/admin/medicos/{id}
     */
    @Transactional
    public void actualizarMedico(Integer idMedico, MedicoRequestDTO request) {
        medicoRepository.sp_Admin_ActualizarMedico(
                idMedico,
                request.getNombres(),
                request.getApellidos(),
                request.getEspecialidad(),
                request.getId_consultorio_asignado()
        );
    }

    /**
     * Lógica para DELETE /api/admin/medicos/{id} (Soft Delete)
     */
    @Transactional
    public void eliminarMedico(Integer idMedico) {
        medicoRepository.sp_Admin_EliminarMedico(idMedico);
    }
}