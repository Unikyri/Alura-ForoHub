package com.alura.forohub.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para JwtUtil")
class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String testEmail = "test@example.com";
    private final String testSecret = "myTestSecretKey123456789012345678901234567890";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", testSecret);
        ReflectionTestUtils.setField(jwtUtil, "expiration", 86400L); // 24 horas en segundos
    }

    @Test
    @DisplayName("Debe generar token JWT válido")
    void testGenerateToken() {
        String token = jwtUtil.generateToken(testEmail);
        
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.split("\\.").length == 3); // JWT tiene 3 partes separadas por puntos
    }

    @Test
    @DisplayName("Debe validar token JWT correctamente")
    void testValidateToken() {
        String token = jwtUtil.generateToken(testEmail);
        String subject = jwtUtil.validateToken(token);
        
        assertEquals(testEmail, subject);
    }

    @Test
    @DisplayName("Debe lanzar excepción para token inválido")
    void testValidateInvalidToken() {
        String invalidToken = "invalid.token.here";
        
        assertThrows(RuntimeException.class, () -> {
            jwtUtil.validateToken(invalidToken);
        });
    }

    @Test
    @DisplayName("Debe extraer subject del token sin validar")
    void testGetSubjectFromToken() {
        String token = jwtUtil.generateToken(testEmail);
        String subject = jwtUtil.getSubjectFromToken(token);
        
        assertEquals(testEmail, subject);
    }

    @Test
    @DisplayName("Debe retornar null para token inválido al extraer subject")
    void testGetSubjectFromInvalidToken() {
        String invalidToken = "invalid.token.here";
        String subject = jwtUtil.getSubjectFromToken(invalidToken);
        
        assertNull(subject);
    }

    @Test
    @DisplayName("Debe verificar si el token no ha expirado")
    void testIsTokenNotExpired() {
        String token = jwtUtil.generateToken(testEmail);
        boolean isExpired = jwtUtil.isTokenExpired(token);
        
        assertFalse(isExpired);
    }

    @Test
    @DisplayName("Debe obtener tiempo de expiración")
    void testGetExpirationTime() {
        Long expiration = jwtUtil.getExpirationTime();
        
        assertEquals(86400L, expiration);
    }

    @Test
    @DisplayName("Debe obtener fecha de expiración del token")
    void testGetExpirationFromToken() {
        String token = jwtUtil.generateToken(testEmail);
        Long expiration = jwtUtil.getExpirationFromToken(token);
        
        assertNotNull(expiration);
        assertTrue(expiration > System.currentTimeMillis());
    }
}