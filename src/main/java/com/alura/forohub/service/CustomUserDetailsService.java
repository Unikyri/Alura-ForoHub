package com.alura.forohub.service;

import com.alura.forohub.repository.UsuarioRepository;
import com.alura.forohub.security.UsuarioUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio personalizado para cargar detalles de usuario para Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correoElectronico) throws UsernameNotFoundException {
        return usuarioRepository.findByCorreoElectronicoWithPerfil(correoElectronico)
            .map(UsuarioUserDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correoElectronico));
    }
}