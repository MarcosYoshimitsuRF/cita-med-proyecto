package com.citamed.api.config;

import com.citamed.api.model.Usuario;
import com.citamed.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * (Punto 3.4.1)
 * Se ejecuta al iniciar la aplicación para insertar datos de prueba
 * si la base de datos está vacía.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // (Punto 3.4.2) Verificar si el admin ya existe
        if (usuarioRepository.sp_ObtenerUsuarioPorEmail("admin@cmed.com").isEmpty()) {

            System.out.println(">>> Base de datos vacía. Ejecutando Data Seeder...");

            // (Punto 3.4.3) Crear 1 Usuario ADMIN
            // Usamos .save() directo ya que el SP es solo para Pacientes
            Usuario admin = new Usuario();
            admin.setEmail("admin@cmed.com");
            admin.setPasswordHash(passwordEncoder.encode("admin123")); // pass: admin123
            admin.setRol(Usuario.Rol.ADMIN);
            admin.setEstaActivo(true);
            usuarioRepository.save(admin);

            // (Punto 3.4.3) Crear 2 Usuarios PACIENTE
            // Usamos el SP sp_RegistrarPaciente

            // Paciente 1
            usuarioRepository.sp_RegistrarPaciente(
                    "paciente1@gmail.com",
                    passwordEncoder.encode("paciente123"), // pass: paciente123
                    "11111111", // DNI
                    "Paciente", // Nombres
                    "Uno",      // Apellidos
                    "999111111" // Teléfono
            );

            // Paciente 2
            usuarioRepository.sp_RegistrarPaciente(
                    "paciente2@gmail.com",
                    passwordEncoder.encode("paciente123"), // pass: paciente123
                    "22222222", // DNI
                    "Paciente", // Nombres
                    "Dos",      // Apellidos
                    "999222222" // Teléfono
            );

            System.out.println(">>> Data Seeder completado. (1 Admin, 2 Pacientes creados)");
        }
    }
}