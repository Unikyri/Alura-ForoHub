package com.alura.forohub.dto;

/**
 * DTO para información de curso.
 */
public record CursoDTO(
    Long id,
    String nombre,
    String categoria,
    String descripcion
) {}