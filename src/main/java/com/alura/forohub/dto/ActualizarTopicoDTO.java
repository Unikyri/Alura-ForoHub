package com.alura.forohub.dto;

import jakarta.validation.constraints.Size;

/**
 * DTO para la actualización de tópicos existentes.
 */
public record ActualizarTopicoDTO(
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    String titulo,
    
    @Size(max = 2000, message = "El mensaje no puede exceder 2000 caracteres")
    String mensaje,
    
    Long cursoId
) {}