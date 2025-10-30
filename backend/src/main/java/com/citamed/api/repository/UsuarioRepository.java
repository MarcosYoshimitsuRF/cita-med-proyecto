package com.citamed.api.repository;

import com.citamed.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    //SP para obtener usuario por email (Login)
    @Query(value = "CALL sp_ObtenerUsuarioPorEmail(:p_email)", nativeQuery = true)
    Optional<Usuario> sp_ObtenerUsuarioPorEmail(@Param("p_email") String p_email);

    // SP para registrar un nuevo paciente
    @Procedure(name = "sp_RegistrarPaciente")
    void sp_RegistrarPaciente(
            @Param("p_email") String p_email,
            @Param("p_password_hash") String p_password_hash,
            @Param("p_dni") String p_dni,
            @Param("p_nombres") String p_nombres,
            @Param("p_apellidos") String p_apellidos,
            @Param("p_telefono") String p_telefono
    );

    //SP para eliminar paciente (Soft Delete)
    @Procedure(name = "sp_Admin_EliminarPaciente")
    void sp_Admin_EliminarPaciente(@Param("p_id_paciente") Integer p_id_paciente);
}