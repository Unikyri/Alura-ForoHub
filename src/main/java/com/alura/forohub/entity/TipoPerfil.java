package com.alura.forohub.entity;

/**
 * Enum que representa los diferentes tipos de perfiles de usuario.
 */
public enum TipoPerfil {
    /**
     * Usuario regular del foro
     */
    USUARIO,
    
    /**
     * Moderador con permisos adicionales
     */
    MODERADOR,
    
    /**
     * Administrador con permisos completos
     */
    ADMINISTRADOR
}