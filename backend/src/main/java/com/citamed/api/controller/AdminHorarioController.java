package com.citamed.api.controller;

import com.citamed.api.dto.HorarioRequestDTO;
import com.citamed.api.model.HorarioMedico;
import com.citamed.api.service.AdminHorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/horarios")
// Toda esta clase está protegida por la regla "/api/admin/**" en SecurityConfig
public class AdminHorarioController {

    @Autowired
    private AdminHorarioService adminHorarioService;

    /**
     * Endpoint GET /api/admin/horarios/medico/{idMedico}
     * Lista todos los horarios de un médico específico.
     */
    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<List<HorarioMedico>> listarHorariosPorMedico(@PathVariable Integer idMedico) {
        return ResponseEntity.ok(adminHorarioService.listarHorariosPorMedico(idMedico));
    }

    /**
     * Endpoint POST /api/admin/horarios
     * Crea o actualiza un horario (Upsert).
     */
    @PostMapping
    public ResponseEntity<Void> crearHorario(@RequestBody HorarioRequestDTO request) {
        adminHorarioService.crearHorario(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Endpoint DELETE /api/admin/horarios/{id}
     * Elimina (Hard Delete) un horario específico.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHorario(@PathVariable Integer id) {
        adminHorarioService.eliminarHorario(id);
        return ResponseEntity.ok().build();
    }
}