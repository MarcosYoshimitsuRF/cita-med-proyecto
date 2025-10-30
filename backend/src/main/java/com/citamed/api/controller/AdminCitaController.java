package com.citamed.api.controller;

import com.citamed.api.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/citas")
// Toda esta clase está protegida por la regla "/api/admin/**" en SecurityConfig
public class AdminCitaController {

    @Autowired
    private CitaService citaService; // Reutilizamos el CitaService

    /**
     * Endpoint PUT /api/admin/citas/{id}/cancelar (Punto 3.3.5)
     * Permite a un administrador cancelar cualquier cita.
     */
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarCitaAdmin(@PathVariable Integer id) {
        citaService.adminCancelarCita(id);
        return ResponseEntity.ok().build();
    }
}