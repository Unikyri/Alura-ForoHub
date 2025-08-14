package com.alura.forohub.dto;

/**
 * DTO para la respuesta de autenticación con token JWT.
 */
public record TokenDTO(
    String token,
    String tipo,
    Long expiracion
) {
    public TokenDTO(String token, Long expiracion) {
        this(token, "Bearer", expiracion);
    }
}