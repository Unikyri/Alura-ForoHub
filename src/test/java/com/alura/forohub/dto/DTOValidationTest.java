package com.alura.forohub.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de validación para DTOs")
class DTOValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("RegistroUsuarioDTO - Validación exitosa")
    void testRegistroUsuarioDTOValidacionExitosa() {
        RegistroUsuarioDTO dto = new RegistroUsuarioDTO(
            "Juan Pérez",
            "juan@example.com",
            "password123"
        );

        Set<ConstraintViolation<RegistroUsuarioDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("RegistroUsuarioDTO - Nombre vacío")
    void testRegistroUsuarioDTONombreVacio() {
        RegistroUsuarioDTO dto = new RegistroUsuarioDTO(
            "",
            "juan@example.com",
            "password123"
        );

        Set<ConstraintViolation<RegistroUsuarioDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("nombre es obligatorio")));
    }

    @Test
    @DisplayName("RegistroUsuarioDTO - Email inválido")
    void testRegistroUsuarioDTOEmailInvalido() {
        RegistroUsuarioDTO dto = new RegistroUsuarioDTO(
            "Juan Pérez",
            "email-invalido",
            "password123"
        );

        Set<ConstraintViolation<RegistroUsuarioDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("formato válido")));
    }

    @Test
    @DisplayName("RegistroUsuarioDTO - Contraseña muy corta")
    void testRegistroUsuarioDTOContrasenaCorta() {
        RegistroUsuarioDTO dto = new RegistroUsuarioDTO(
            "Juan Pérez",
            "juan@example.com",
            "123"
        );

        Set<ConstraintViolation<RegistroUsuarioDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("entre 6 y 100 caracteres")));
    }

    @Test
    @DisplayName("LoginDTO - Validación exitosa")
    void testLoginDTOValidacionExitosa() {
        LoginDTO dto = new LoginDTO("juan@example.com", "password123");

        Set<ConstraintViolation<LoginDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("CrearTopicoDTO - Validación exitosa")
    void testCrearTopicoDTOValidacionExitosa() {
        CrearTopicoDTO dto = new CrearTopicoDTO(
            "Título del tópico",
            "Mensaje del tópico",
            1L
        );

        Set<ConstraintViolation<CrearTopicoDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("CrearTopicoDTO - Título muy largo")
    void testCrearTopicoDTOTituloLargo() {
        String tituloLargo = "a".repeat(201);
        CrearTopicoDTO dto = new CrearTopicoDTO(
            tituloLargo,
            "Mensaje del tópico",
            1L
        );

        Set<ConstraintViolation<CrearTopicoDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("no puede exceder 200 caracteres")));
    }

    @Test
    @DisplayName("CrearTopicoDTO - Curso nulo")
    void testCrearTopicoDTOCursoNulo() {
        CrearTopicoDTO dto = new CrearTopicoDTO(
            "Título del tópico",
            "Mensaje del tópico",
            null
        );

        Set<ConstraintViolation<CrearTopicoDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("curso es obligatorio")));
    }

    @Test
    @DisplayName("CrearRespuestaDTO - Validación exitosa")
    void testCrearRespuestaDTOValidacionExitosa() {
        CrearRespuestaDTO dto = new CrearRespuestaDTO("Mensaje de la respuesta");

        Set<ConstraintViolation<CrearRespuestaDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("CrearRespuestaDTO - Mensaje muy largo")
    void testCrearRespuestaDTOMensajeLargo() {
        String mensajeLargo = "a".repeat(2001);
        CrearRespuestaDTO dto = new CrearRespuestaDTO(mensajeLargo);

        Set<ConstraintViolation<CrearRespuestaDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("no puede exceder 2000 caracteres")));
    }
}