package com.alura.forohub.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Utilidad para manejo de tokens JWT.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:3600}")
    private Long expiration;

    /**
     * Genera un token JWT para el usuario dado.
     */
    public String generateToken(String correoElectronico) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("forohub-api")
                    .withSubject(correoElectronico)
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error al generar token JWT", exception);
        }
    }

    /**
     * Valida un token JWT y retorna el subject (correo electrónico).
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT jwt = JWT.require(algorithm)
                    .withIssuer("forohub-api")
                    .build()
                    .verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido", exception);
        }
    }

    /**
     * Extrae el correo electrónico del token sin validar.
     */
    public String getSubjectFromToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Verifica si el token ha expirado.
     */
    public boolean isTokenExpired(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getExpiresAt().before(java.util.Date.from(Instant.now()));
        } catch (Exception exception) {
            return true;
        }
    }

    /**
     * Obtiene la fecha de expiración del token.
     */
    public Long getExpirationFromToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getExpiresAt().getTime();
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Genera la fecha de expiración para el token.
     */
    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusSeconds(expiration).toInstant(ZoneOffset.UTC);
    }

    /**
     * Obtiene el tiempo de expiración en segundos.
     */
    public Long getExpirationTime() {
        return expiration;
    }
}