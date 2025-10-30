package com.citamed.api.service;

import com.citamed.api.dto.AdminPacienteResponseDTO;
import com.citamed.api.dto.PacienteAdminRequestDTO;
import com.citamed.api.repository.PacienteRepository;
import com.citamed.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminPacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Lógica para GET /api/admin/pacientes
     */
    @Transactional(readOnly = true)
    public List<AdminPacienteResponseDTO> listarPacientesAdmin() {

        List<Object[]> results = pacienteRepository.sp_Admin_ListarPacientes();

        // Mapear la respuesta de List<Object[]> a List<AdminPacienteResponseDTO>
        return results.stream().map(row -> {
            AdminPacienteResponseDTO dto = new AdminPacienteResponseDTO();
            dto.setId_paciente(((BigInteger) row[0]).intValue());
            dto.setId_usuario(((BigInteger) row[1]).intValue());
            dto.setDni((String) row[2]);
            dto.setNombres((String) row[3]);
            dto.setApellidos((String) row[4]);
            dto.setTelefono((String) row[5]);
            dto.setEmail((String) row[6]);
            dto.setEsta_activo(((Byte) row[7]) != 0); // Convertir TINYINT a boolean
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * Lógica para PUT /api/admin/pacientes/{id}
     */
    @Transactional
    public void actualizarPaciente(Integer idPaciente, PacienteAdminRequestDTO request) {
        pacienteRepository.sp_Admin_ActualizarPaciente(
                idPaciente,
                request.getDni(),
                request.getNombres(),
                request.getApellidos(),
                request.getTelefono()
        );
    }

    /**
     * Lógica para DELETE /api/admin/pacientes/{id} (Soft Delete)
     */
    @Transactional
    public void eliminarPaciente(Integer idPaciente) {
        // Llama al SP que hace Soft Delete en la tabla Usuarios
        usuarioRepository.sp_Admin_EliminarPaciente(idPaciente);
    }
}