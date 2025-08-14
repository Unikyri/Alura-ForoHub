package com.alura.forohub.dto;

import com.alura.forohub.entity.StatusTopico;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para el detalle completo de un tópico.
 */
public record DetalleTopicoDTO(
    Long id,
    String titulo,
    String mensaje,
    LocalDateTime fechaCreacion,
    LocalDateTime fechaActualizacion,
    StatusTopico status,
    UsuarioDTO autor,
    CursoDTO curso,
    List<RespuestaDTO> respuestas
) {}