package com.alura.forohub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para el login de usuarios.
 */
public record LoginDTO(
    @Email(message = "El correo electrónico debe tener un formato válido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    String correoElectronico,
    
    @NotBlank(message = "La contraseña es obligatoria")
    String contrasena
) {}