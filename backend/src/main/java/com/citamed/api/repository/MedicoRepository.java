package com.citamed.api.repository;

import com.citamed.api.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer> {

    // (Punto 1.4.4) SP para listar médicos (Público/Paciente)
    // Usamos @Query porque el SP devuelve una proyección
    @Query(value = "CALL sp_ListarMedicosPublico()", nativeQuery = true)
    List<Object[]> sp_ListarMedicosPublico(); // Se mapeará a DTO en Service

    // (Punto 1.5.1) SP para obtener slots (Slot Generator)
    // Usamos @Query porque el SP devuelve una lista de Time
    @Query(value = "CALL sp_ObtenerSlotsDisponibles(:p_id_medico, :p_fecha)", nativeQuery = true)
    List<String> sp_ObtenerSlotsDisponibles(
            @Param("p_id_medico") Integer p_id_medico,
            @Param("p_fecha") String p_fecha // Formato YYYY-MM-DD
    );

    // (Punto 1.6.1) SP para listar médicos (Admin)
    // Usamos @Query porque el SP devuelve una proyección
    @Query(value = "CALL sp_Admin_ListarMedicos()", nativeQuery = true)
    List<Object[]> sp_Admin_ListarMedicos(); // Se mapeará a DTO en Service

    // (Punto 1.6.1) SP para crear médico (Admin)
    @Procedure(name = "sp_Admin_CrearMedico")
    void sp_Admin_CrearMedico(
            @Param("p_nombres") String p_nombres,
            @Param("p_apellidos") String p_apellidos,
            @Param("p_especialidad") String p_especialidad,
            @Param("p_id_consultorio_asignado") Integer p_id_consultorio_asignado
    );

    // (Punto 1.6.1) SP para actualizar médico (Admin)
    @Procedure(name = "sp_Admin_ActualizarMedico")
    void sp_Admin_ActualizarMedico(
            @Param("p_id_medico") Integer p_id_medico,
            @Param("p_nombres") String p_nombres,
            @Param("p_apellidos") String p_apellidos,
            @Param("p_especialidad") String p_especialidad,
            @Param("p_id_consultorio_asignado") Integer p_id_consultorio_asignado
    );

    // (Punto 1.6.1) SP para eliminar médico (Soft Delete)
    @Procedure(name = "sp_Admin_EliminarMedico")
    void sp_Admin_EliminarMedico(@Param("p_id_medico") Integer p_id_medico);
}