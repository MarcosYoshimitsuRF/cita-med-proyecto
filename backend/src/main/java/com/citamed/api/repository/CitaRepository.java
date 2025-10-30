package com.citamed.api.repository;

import com.citamed.api.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    // (Punto 1.4.1) SP para agendar cita
    @Procedure(name = "sp_AgendarCita")
    void sp_AgendarCita(
            @Param("p_id_paciente") Integer p_id_paciente,
            @Param("p_id_medico") Integer p_id_medico,
            @Param("p_fecha_hora") LocalDateTime p_fecha_hora
    );

    // (Punto 1.4.2) SP para obtener citas por paciente
    // Usamos @Query porque el SP devuelve una proyección
    @Query(value = "CALL sp_ObtenerCitasPorPaciente(:p_id_paciente)", nativeQuery = true)
    List<Object[]> sp_ObtenerCitasPorPaciente(@Param("p_id_paciente") Integer p_id_paciente);

    // (Punto 1.4.3) SP para cancelar cita (Paciente)
    @Procedure(name = "sp_CancelarCitaPaciente")
    void sp_CancelarCitaPaciente(
            @Param("p_id_cita") Integer p_id_cita,
            @Param("p_id_paciente") Integer p_id_paciente
    );

    // (Punto 1.4.5) SP para cancelar cita (Admin)
    @Procedure(name = "sp_Admin_CancelarCita")
    void sp_Admin_CancelarCita(@Param("p_id_cita") Integer p_id_cita);
}