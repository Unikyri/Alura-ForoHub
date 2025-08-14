package com.alura.forohub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para la creación de nuevas respuestas.
 */
public record CrearRespuestaDTO(
    @NotBlank(message = "El mensaje es obligatorio")
    @Size(max = 2000, message = "El mensaje no puede exceder 2000 caracteres")
    String mensaje
) {}