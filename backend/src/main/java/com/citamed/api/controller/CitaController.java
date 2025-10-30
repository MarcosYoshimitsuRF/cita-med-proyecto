package com.citamed.api.controller;

import com.citamed.api.dto.AgendarCitaRequestDTO;
import com.citamed.api.dto.MisCitasResponseDTO;
import com.citamed.api.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
// Toda esta clase está protegida por la regla "/api/citas/**" en SecurityConfig
public class CitaController {

    @Autowired
    private CitaService citaService;

    /**
     * Endpoint GET /api/citas/mis-citas (Punto 3.1.2)
     * Obtiene el historial de citas del paciente autenticado.
     */
    @GetMapping("/mis-citas")
    public ResponseEntity<List<MisCitasResponseDTO>> getMisCitas(Authentication authentication) {
        // Spring Security nos provee el objeto Authentication
        String email = authentication.getName(); // Extraemos el email (subject) del JWT
        List<MisCitasResponseDTO> citas = citaService.getMisCitas(email);
        return ResponseEntity.ok(citas);
    }

    /**
     * Endpoint PUT /api/citas/{id}/cancelar (Punto 3.1.3)
     * Cancela una cita propia del paciente autenticado.
     */
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarCita(@PathVariable Integer id, Authentication authentication) {
        String email = authentication.getName();
        citaService.cancelarCitaPaciente(id, email);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint POST /api/citas (Punto 3.1.4)
     * Agenda una nueva cita para el paciente autenticado.
     */
    @PostMapping
    public ResponseEntity<Void> agendarCita(@RequestBody AgendarCitaRequestDTO request, Authentication authentication) {
        String email = authentication.getName();
        citaService.agendarCita(request, email);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}