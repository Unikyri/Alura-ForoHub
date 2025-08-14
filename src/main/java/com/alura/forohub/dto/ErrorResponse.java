package com.alura.forohub.dto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO para respuestas de error consistentes.
 */
public record ErrorResponse(
    String codigo,
    String mensaje,
    LocalDateTime timestamp,
    Map<String, String> detalles
) {
    public ErrorResponse(String codigo, String mensaje) {
        this(codigo, mensaje, LocalDateTime.now(), Map.of());
    }
    
    public ErrorResponse(String codigo, String mensaje, Map<String, String> detalles) {
        this(codigo, mensaje, LocalDateTime.now(), detalles);
    }
}