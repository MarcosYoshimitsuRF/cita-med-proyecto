package com.citamed.api.repository;

import com.citamed.api.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    //SP para listar pacientes (Admin)
    // Usamos @Query porque el SP devuelve una proyección con campos de 2 tablas
    @Query(value = "CALL sp_Admin_ListarPacientes()", nativeQuery = true)
    List<Object[]> sp_Admin_ListarPacientes(); // Se mapeará a un DTO en el Service

    //SP para actualizar paciente (Admin)
    @Procedure(name = "sp_Admin_ActualizarPaciente")
    void sp_Admin_ActualizarPaciente(
            @Param("p_id_paciente") Integer p_id_paciente,
            @Param("p_dni") String p_dni,
            @Param("p_nombres") String p_nombres,
            @Param("p_apellidos") String p_apellidos,
            @Param("p_telefono") String p_telefono
    );
}