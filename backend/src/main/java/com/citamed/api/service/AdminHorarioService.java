package com.citamed.api.service;

import com.citamed.api.dto.HorarioRequestDTO;
import com.citamed.api.model.HorarioMedico;
import com.citamed.api.repository.HorarioMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminHorarioService {

    @Autowired
    private HorarioMedicoRepository horarioMedicoRepository;

    /**
     * Lógica para GET /api/admin/horarios/medico/{idMedico}
     */
    @Transactional(readOnly = true)
    public List<HorarioMedico> listarHorariosPorMedico(Integer idMedico) {
        // El SP devuelve la entidad completa, no se necesita mapeo de DTO
        return horarioMedicoRepository.sp_Admin_ObtenerHorariosPorMedico(idMedico);
    }

    /**
     * Lógica para POST /api/admin/horarios (Upsert)
     */
    @Transactional
    public void crearHorario(HorarioRequestDTO request) {
        // Llama al SP de Upsert (INSERT ... ON DUPLICATE KEY UPDATE)
        horarioMedicoRepository.sp_Admin_CrearHorario(
                request.getId_medico(),
                request.getDia_semana(),
                request.getHora_inicio(),
                request.getHora_fin()
        );
    }

    /**
     * Lógica para DELETE /api/admin/horarios/{id}
     */
    @Transactional
    public void eliminarHorario(Integer idHorario) {
        horarioMedicoRepository.sp_Admin_EliminarHorario(idHorario);
    }
}