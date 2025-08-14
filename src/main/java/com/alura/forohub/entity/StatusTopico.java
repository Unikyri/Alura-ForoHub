package com.alura.forohub.entity;

/**
 * Enum que representa los posibles estados de un tópico en el foro.
 */
public enum StatusTopico {
    /**
     * Tópico abierto, acepta nuevas respuestas
     */
    ABIERTO,
    
    /**
     * Tópico cerrado, no acepta nuevas respuestas
     */
    CERRADO,
    
    /**
     * Tópico resuelto, tiene una respuesta marcada como solución
     */
    RESUELTO
}