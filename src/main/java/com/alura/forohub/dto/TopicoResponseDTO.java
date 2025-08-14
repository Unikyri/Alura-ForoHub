package com.alura.forohub.dto;

import com.alura.forohub.entity.StatusTopico;
import java.time.LocalDateTime;

/**
 * DTO para la respuesta de tópicos en listados.
 */
public record TopicoResponseDTO(
    Long id,
    String titulo,
    String mensaje,
    LocalDateTime fechaCreacion,
    StatusTopico status,
    String autorNombre,
    String cursoNombre,
    Integer totalRespuestas
) {}