package com.alura.forohub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para la creación de nuevos tópicos.
 */
public record CrearTopicoDTO(
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    String titulo,
    
    @NotBlank(message = "El mensaje es obligatorio")
    @Size(max = 2000, message = "El mensaje no puede exceder 2000 caracteres")
    String mensaje,
    
    @NotNull(message = "El curso es obligatorio")
    Long cursoId
) {}