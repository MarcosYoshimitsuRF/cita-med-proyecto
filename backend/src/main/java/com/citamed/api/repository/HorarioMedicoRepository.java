package com.citamed.api.repository;

import com.citamed.api.model.HorarioMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface HorarioMedicoRepository extends JpaRepository<HorarioMedico, Integer> {

    // (Punto 1.6.2) SP para obtener horarios por médico (Admin)
    @Query(value = "CALL sp_Admin_ObtenerHorariosPorMedico(:p_id_medico)", nativeQuery = true)
    List<HorarioMedico> sp_Admin_ObtenerHorariosPorMedico(@Param("p_id_medico") Integer p_id_medico);

    // (Punto 1.6.2) SP para crear/actualizar horario (Admin)
    @Procedure(name = "sp_Admin_CrearHorario")
    void sp_Admin_CrearHorario(
            @Param("p_id_medico") Integer p_id_medico,
            @Param("p_dia_semana") Byte p_dia_semana,
            @Param("p_hora_inicio") Time p_hora_inicio,
            @Param("p_hora_fin") Time p_hora_fin
    );

    // (Punto 1.6.2) SP para eliminar horario (Admin)
    @Procedure(name = "sp_Admin_EliminarHorario")
    void sp_Admin_EliminarHorario(@Param("p_id_horario") Integer p_id_horario);
}