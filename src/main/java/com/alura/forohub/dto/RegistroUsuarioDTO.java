package com.alura.forohub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para el registro de nuevos usuarios.
 */
public record RegistroUsuarioDTO(
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    String nombre,
    
    @Email(message = "El correo electrónico debe tener un formato válido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Size(max = 150, message = "El correo electrónico no puede exceder 150 caracteres")
    String correoElectronico,
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    String contrasena
) {}