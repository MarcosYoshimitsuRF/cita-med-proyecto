package com.citamed.api.controller;

import com.citamed.api.dto.AdminPacienteResponseDTO;
import com.citamed.api.dto.PacienteAdminRequestDTO;
import com.citamed.api.service.AdminPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/pacientes")
// Toda esta clase está protegida por la regla "/api/admin/**" en SecurityConfig
public class AdminPacienteController {

    @Autowired
    private AdminPacienteService adminPacienteService;

    /**
     * Endpoint GET /api/admin/pacientes
     * Lista todos los pacientes para el administrador.
     */
    @GetMapping
    public ResponseEntity<List<AdminPacienteResponseDTO>> listarPacientes() {
        return ResponseEntity.ok(adminPacienteService.listarPacientesAdmin());
    }

    /**
     * Endpoint PUT /api/admin/pacientes/{id}
     * Actualiza un paciente existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarPaciente(
            @PathVariable Integer id,
            @RequestBody PacienteAdminRequestDTO request) {
        adminPacienteService.actualizarPaciente(id, request);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint DELETE /api/admin/pacientes/{id}
     * Elimina (Soft Delete) un paciente.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Integer id) {
        adminPacienteService.eliminarPaciente(id);
        return ResponseEntity.ok().build();
    }
}