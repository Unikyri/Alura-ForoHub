package com.alura.forohub.service;

import com.alura.forohub.dto.EstadisticasDTO;
import com.alura.forohub.entity.StatusTopico;
import com.alura.forohub.repository.CursoRepository;
import com.alura.forohub.repository.RespuestaRepository;
import com.alura.forohub.repository.TopicoRepository;
import com.alura.forohub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para obtener estadísticas del foro.
 */
@Service
public class EstadisticasService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    /**
     * Obtiene estadísticas generales del foro.
     */
    @Transactional(readOnly = true)
    public EstadisticasDTO obtenerEstadisticas() {
        Long totalTopicos = topicoRepository.count();
        Long totalRespuestas = respuestaRepository.count();
        Long totalUsuarios = usuarioRepository.count();
        Long totalCursos = cursoRepository.count();
        
        // Contar tópicos por status (esto requeriría métodos adicionales en el repositorio)
        Long topicosResueltos = contarTopicosPorStatus(StatusTopico.RESUELTO);
        Long topicosAbiertos = contarTopicosPorStatus(StatusTopico.ABIERTO);

        return new EstadisticasDTO(
            totalTopicos,
            totalRespuestas,
            totalUsuarios,
            totalCursos,
            topicosResueltos,
            topicosAbiertos
        );
    }

    private Long contarTopicosPorStatus(StatusTopico status) {
        // Por simplicidad, retornamos 0. En una implementación real,
        // agregaríamos métodos al repositorio para contar por status
        return 0L;
    }
}