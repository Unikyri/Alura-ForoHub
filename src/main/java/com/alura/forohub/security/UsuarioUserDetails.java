package com.alura.forohub.security;

import com.alura.forohub.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Implementación de UserDetails para la entidad Usuario.
 */
public class UsuarioUserDetails implements UserDetails {

    private final Usuario usuario;

    public UsuarioUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convertir el tipo de perfil a autoridad de Spring Security
        String authority = "ROLE_" + usuario.getPerfil().getTipo().name();
        return List.of(new SimpleGrantedAuthority(authority));
    }

    @Override
    public String getPassword() {
        return usuario.getContrasena();
    }

    @Override
    public String getUsername() {
        return usuario.getCorreoElectronico();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usuario.getActivo();
    }

    /**
     * Obtiene la entidad Usuario completa.
     */
    public Usuario getUsuario() {
        return usuario;
    }
}