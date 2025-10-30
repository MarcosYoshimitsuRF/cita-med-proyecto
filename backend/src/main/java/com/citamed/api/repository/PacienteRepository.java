package com.citamed.api.repository;

import com.citamed.api.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // Importar

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    // (Punto 1.6.3) SP para listar pacientes (Admin)
    @Query(value = "CALL sp_Admin_ListarPacientes()", nativeQuery = true)
    List<Object[]> sp_Admin_ListarPacientes();

    // (Punto 1.6.3) SP para actualizar paciente (Admin)
    @Procedure(name = "sp_Admin_ActualizarPaciente")
    void sp_Admin_ActualizarPaciente(
            @Param("p_id_paciente") Integer p_id_paciente,
            @Param("p_dni") String p_dni,
            @Param("p_nombres") String p_nombres,
            @Param("p_apellidos") String p_apellidos,
            @Param("p_telefono") String p_telefono
    );

    // --- NUEVO MÉTODO AÑADIDO (Requerido para Paso 3.1) ---
    /**
     * Busca un Paciente usando el email de su tabla Usuario asociada.
     * Esencial para la segregación de datos (obtener id_paciente desde el JWT).
     * Esto es un Query Method de JPA, no un SP.
     */
    Optional<Paciente> findByUsuarioEmail(String email);
}