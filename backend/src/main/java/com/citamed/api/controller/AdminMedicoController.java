package com.citamed.api.controller;

import com.citamed.api.dto.AdminMedicoResponseDTO;
import com.citamed.api.dto.MedicoRequestDTO;
import com.citamed.api.service.AdminMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/medicos")
// Toda esta clase está protegida por la regla "/api/admin/**" en SecurityConfig
public class AdminMedicoController {

    @Autowired
    private AdminMedicoService adminMedicoService;

    /**
     * Endpoint GET /api/admin/medicos
     * Lista todos los médicos para el administrador.
     */
    @GetMapping
    public ResponseEntity<List<AdminMedicoResponseDTO>> listarMedicos() {
        return ResponseEntity.ok(adminMedicoService.listarMedicosAdmin());
    }

    /**
     * Endpoint POST /api/admin/medicos
     * Crea un nuevo médico.
     */
    @PostMapping
    public ResponseEntity<Void> crearMedico(@RequestBody MedicoRequestDTO request) {
        adminMedicoService.crearMedico(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Endpoint PUT /api/admin/medicos/{id}
     * Actualiza un médico existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarMedico(
            @PathVariable Integer id,
            @RequestBody MedicoRequestDTO request) {
        adminMedicoService.actualizarMedico(id, request);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint DELETE /api/admin/medicos/{id}
     * Elimina (Soft Delete) un médico.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMedico(@PathVariable Integer id) {
        adminMedicoService.eliminarMedico(id);
        return ResponseEntity.ok().build();
    }
}