package com.citamed.api.security;

import com.citamed.api.model.Usuario;
import com.citamed.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Carga los detalles del usuario desde la BD usando el SP.
     * Este método es llamado por el AuthenticationManager de Spring durante el login.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1. Llamamos al SP de la Fase 1 para buscar al usuario por email
        Usuario usuario = usuarioRepository.sp_ObtenerUsuarioPorEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        // 2. Creamos la lista de "autoridades" (roles)
        // Spring Security requiere que los roles tengan el prefijo "ROLE_"
        Collection<? extends GrantedAuthority> authorities =
                Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())
                );

        // 3. Devolvemos un objeto User de Spring Security
        // Este objeto contiene la información que Spring usará para validar el password
        return new User(
                usuario.getEmail(),
                usuario.getPasswordHash(),
                usuario.isEstaActivo(), // (boolean enabled)
                true, // (boolean accountNonExpired)
                true, // (boolean credentialsNonExpired)
                true, // (boolean accountNonLocked)
                authorities
        );
    }
}