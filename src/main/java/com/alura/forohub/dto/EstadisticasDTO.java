package com.alura.forohub.dto;

/**
 * DTO para estadísticas generales del foro.
 */
public record EstadisticasDTO(
    Long totalTopicos,
    Long totalRespuestas,
    Long totalUsuarios,
    Long totalCursos,
    Long topicosResueltos,
    Long topicosAbiertos
) {}