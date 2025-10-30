package com.citamed.api.repository;

import com.citamed.api.model.Consultorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultorioRepository extends JpaRepository<Consultorio, Integer> {

    //SP para listar consultorios (Admin)
    @Query(value = "CALL sp_Admin_ListarConsultorios()", nativeQuery = true)
    List<Consultorio> sp_Admin_ListarConsultorios();

    //SP para crear consultorio (Admin)
    @Procedure(name = "sp_Admin_CrearConsultorio")
    void sp_Admin_CrearConsultorio(
            @Param("p_nombre") String p_nombre,
            @Param("p_ubicacion") String p_ubicacion
    );

    //SP para actualizar consultorio (Admin)
    @Procedure(name = "sp_Admin_ActualizarConsultorio")
    void sp_Admin_ActualizarConsultorio(
            @Param("p_id_consultorio") Integer p_id_consultorio,
            @Param("p_nombre") String p_nombre,
            @Param("p_ubicacion") String p_ubicacion
    );

    //SP para eliminar consultorio (Soft Delete)
    @Procedure(name = "sp_Admin_EliminarConsultorio")
    void sp_Admin_EliminarConsultorio(@Param("p_id_consultorio") Integer p_id_consultorio);
}