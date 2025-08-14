package com.alura.forohub.dto;

import java.time.LocalDateTime;

/**
 * DTO para información básica de usuario.
 */
public record UsuarioDTO(
    Long id,
    String nombre,
    String correoElectronico,
    LocalDateTime fechaCreacion,
    PerfilDTO perfil
) {}