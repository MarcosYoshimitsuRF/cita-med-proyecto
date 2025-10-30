package com.citamed.api.service;

import com.citamed.api.dto.ConsultorioDTO;
import com.citamed.api.model.Consultorio;
import com.citamed.api.repository.ConsultorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminConsultorioService {

    @Autowired
    private ConsultorioRepository consultorioRepository;

    /**
     * Lógica para GET /api/admin/consultorios
     */
    @Transactional(readOnly = true)
    public List<ConsultorioDTO> listarConsultorios() {

        // 1. Llamar al SP
        List<Consultorio> consultorios = consultorioRepository.sp_Admin_ListarConsultorios();

        // 2. Mapear la entidad Consultorio a ConsultorioDTO
        return consultorios.stream().map(consultorio -> {
            ConsultorioDTO dto = new ConsultorioDTO();
            dto.setId_consultorio(consultorio.getId_consultorio());
            dto.setNombre(consultorio.getNombre());
            dto.setUbicacion(consultorio.getUbicacion());
            dto.setEsta_activo(consultorio.isEstaActivo());
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * Lógica para POST /api/admin/consultorios
     */
    @Transactional
    public void crearConsultorio(ConsultorioDTO request) {
        consultorioRepository.sp_Admin_CrearConsultorio(
                request.getNombre(),
                request.getUbicacion()
        );
    }

    /**
     * Lógica para PUT /api/admin/consultorios/{id}
     */
    @Transactional
    public void actualizarConsultorio(Integer idConsultorio, ConsultorioDTO request) {
        consultorioRepository.sp_Admin_ActualizarConsultorio(
                idConsultorio,
                request.getNombre(),
                request.getUbicacion()
        );
    }

    /**
     * Lógica para DELETE /api/admin/consultorios/{id} (Soft Delete)
     */
    @Transactional
    public void eliminarConsultorio(Integer idConsultorio) {
        consultorioRepository.sp_Admin_EliminarConsultorio(idConsultorio);
    }
}