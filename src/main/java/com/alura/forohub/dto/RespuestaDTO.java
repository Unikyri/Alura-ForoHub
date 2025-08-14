package com.alura.forohub.dto;

import java.time.LocalDateTime;

/**
 * DTO para información de respuesta.
 */
public record RespuestaDTO(
    Long id,
    String mensaje,
    LocalDateTime fechaCreacion,
    LocalDateTime fechaActualizacion,
    UsuarioDTO autor,
    Boolean solucion
) {}