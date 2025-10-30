package com.citamed.api.controller;

import com.citamed.api.dto.ConsultorioDTO;
import com.citamed.api.service.AdminConsultorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/consultorios")
// Toda esta clase está protegida por la regla "/api/admin/**" en SecurityConfig
public class AdminConsultorioController {

    @Autowired
    private AdminConsultorioService adminConsultorioService;

    /**
     * Endpoint GET /api/admin/consultorios
     * Lista todos los consultorios para el administrador.
     */
    @GetMapping
    public ResponseEntity<List<ConsultorioDTO>> listarConsultorios() {
        return ResponseEntity.ok(adminConsultorioService.listarConsultorios());
    }

    /**
     * Endpoint POST /api/admin/consultorios
     * Crea un nuevo consultorio.
     */
    @PostMapping
    public ResponseEntity<Void> crearConsultorio(@RequestBody ConsultorioDTO request) {
        adminConsultorioService.crearConsultorio(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Endpoint PUT /api/admin/consultorios/{id}
     * Actualiza un consultorio existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarConsultorio(
            @PathVariable Integer id,
            @RequestBody ConsultorioDTO request) {
        adminConsultorioService.actualizarConsultorio(id, request);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint DELETE /api/admin/consultorios/{id}
     * Elimina (Soft Delete) un consultorio.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConsultorio(@PathVariable Integer id) {
        adminConsultorioService.eliminarConsultorio(id);
        return ResponseEntity.ok().build();
    }
}