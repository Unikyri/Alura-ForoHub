package com.alura.forohub.dto;

import jakarta.validation.constraints.Size;

/**
 * DTO para la actualización de respuestas existentes.
 */
public record ActualizarRespuestaDTO(
    @Size(max = 2000, message = "El mensaje no puede exceder 2000 caracteres")
    String mensaje
) {}