package com.citamed.api.controller;

import com.citamed.api.dto.MedicoPublicoResponseDTO;
import com.citamed.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
// Toda esta clase está protegida por la regla "/api/medicos/**" en SecurityConfig
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    /**
     * Endpoint GET /api/medicos (Punto 3.2.2)
     * Lista todos los médicos activos para que el paciente pueda agendar.
     */
    @GetMapping
    public ResponseEntity<List<MedicoPublicoResponseDTO>> getMedicosPublico() {
        List<MedicoPublicoResponseDTO> medicos = medicoService.getMedicosPublico();
        return ResponseEntity.ok(medicos);
    }

    /**
     * Endpoint GET /api/medicos/{id}/disponibilidad (Punto 3.2.3)
     * ¡El endpoint clave! Obtiene los slots disponibles del "Slot Generator".
     */
    @GetMapping("/{id}/disponibilidad")
    public ResponseEntity<List<String>> getDisponibilidad(
            @PathVariable Integer id,
            @RequestParam String fecha // Recibe la fecha como parámetro (ej. ?fecha=2025-11-05)
    ) {
        List<String> slots = medicoService.getDisponibilidadMedico(id, fecha);
        return ResponseEntity.ok(slots);
    }
}