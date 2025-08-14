package com.alura.forohub.dto;

import com.alura.forohub.entity.TipoPerfil;

/**
 * DTO para información de perfil de usuario.
 */
public record PerfilDTO(
    Long id,
    String nombre,
    TipoPerfil tipo,
    String descripcion
) {}