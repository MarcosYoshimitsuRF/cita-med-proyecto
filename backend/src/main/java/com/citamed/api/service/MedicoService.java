package com.citamed.api.service;

import com.citamed.api.dto.MedicoPublicoResponseDTO;
import com.citamed.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    /**
     * Lógica para GET /api/medicos (Punto 3.2.2)
     */
    @Transactional(readOnly = true)
    public List<MedicoPublicoResponseDTO> getMedicosPublico() {

        // 1. Llamar al SP
        List<Object[]> results = medicoRepository.sp_ListarMedicosPublico();

        // 2. Mapear la respuesta de List<Object[]> a List<MedicoPublicoResponseDTO>
        return results.stream().map(row -> {
            MedicoPublicoResponseDTO dto = new MedicoPublicoResponseDTO();
            dto.setId_medico(((BigInteger) row[0]).intValue()); // MySQL puede devolver BigInteger
            dto.setNombres((String) row[1]);
            dto.setApellidos((String) row[2]);
            dto.setEspecialidad((String) row[3]);
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * Lógica para GET /api/medicos/{id}/disponibilidad (Punto 3.2.3)
     */
    @Transactional(readOnly = true)
    public List<String> getDisponibilidadMedico(Integer idMedico, String fecha) {
        // 1. Llamar al SP "Slot Generator"
        // El SP ya devuelve una List<String> (o List<Time> que JPA mapea a String)
        return medicoRepository.sp_ObtenerSlotsDisponibles(idMedico, fecha);
    }
}