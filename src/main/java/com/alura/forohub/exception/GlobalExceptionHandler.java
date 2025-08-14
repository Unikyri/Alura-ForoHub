package com.alura.forohub.exception;

import com.alura.forohub.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manejador global de excepciones para la aplicación.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Maneja errores de validación de campos.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });

        logger.warn("Errores de validación: {}", errores);

        ErrorResponse errorResponse = new ErrorResponse(
            "VALIDATION_ERROR",
            "Errores de validación en los datos enviados",
            errores
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Maneja violaciones de restricciones de validación.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errores = ex.getConstraintViolations().stream()
            .collect(Collectors.toMap(
                violation -> violation.getPropertyPath().toString(),
                ConstraintViolation::getMessage
            ));

        logger.warn("Violaciones de restricciones: {}", errores);

        ErrorResponse errorResponse = new ErrorResponse(
            "CONSTRAINT_VIOLATION",
            "Violación de restricciones de validación",
            errores
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Maneja entidades no encontradas.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        logger.warn("Entidad no encontrada: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
            "ENTITY_NOT_FOUND",
            ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Maneja errores de acceso denegado.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        logger.warn("Acceso denegado: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
            "ACCESS_DENIED",
            "No tienes permisos para realizar esta operación"
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    /**
     * Maneja errores de autenticación.
     */
    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException ex) {
        logger.warn("Error de autenticación: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
            "AUTHENTICATION_ERROR",
            "Credenciales inválidas o token expirado"
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    /**
     * Maneja argumentos ilegales.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        logger.warn("Argumento ilegal: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
            "INVALID_ARGUMENT",
            ex.getMessage()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Maneja violaciones de integridad de datos.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        logger.error("Violación de integridad de datos", ex);

        String mensaje = "Error de integridad de datos";
        String codigo = "DATA_INTEGRITY_ERROR";

        // Detectar tipos específicos de violaciones
        if (ex.getMessage() != null) {
            if (ex.getMessage().contains("Duplicate entry")) {
                mensaje = "Ya existe un registro con estos datos";
                codigo = "DUPLICATE_ENTRY";
            } else if (ex.getMessage().contains("foreign key constraint")) {
                mensaje = "No se puede completar la operación debido a referencias existentes";
                codigo = "FOREIGN_KEY_CONSTRAINT";
            }
        }

        ErrorResponse errorResponse = new ErrorResponse(codigo, mensaje);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /**
     * Maneja errores de tiempo de ejecución.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {
        logger.error("Error de tiempo de ejecución en {}: {}", request.getDescription(false), ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
            "RUNTIME_ERROR",
            "Ha ocurrido un error interno. Por favor, inténtalo de nuevo más tarde."
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Maneja cualquier otra excepción no capturada.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        logger.error("Error no manejado en {}: {}", request.getDescription(false), ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
            "INTERNAL_ERROR",
            "Ha ocurrido un error interno del servidor"
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}