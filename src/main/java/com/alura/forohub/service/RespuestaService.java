package com.alura.forohub.service;

import com.alura.forohub.dto.ActualizarRespuestaDTO;
import com.alura.forohub.dto.CrearRespuestaDTO;
import com.alura.forohub.dto.RespuestaDTO;
import com.alura.forohub.entity.Respuesta;
import com.alura.forohub.entity.StatusTopico;
import com.alura.forohub.entity.Topico;
import com.alura.forohub.entity.Usuario;
import com.alura.forohub.mapper.RespuestaMapper;
import com.alura.forohub.repository.RespuestaRepository;
import com.alura.forohub.repository.TopicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de respuestas.
 */
@Service
public class RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private RespuestaMapper respuestaMapper;

    /**
     * Crea una nueva respuesta para un tópico.
     */
    @Transactional
    public RespuestaDTO crearRespuesta(Long topicoId, CrearRespuestaDTO dto, Usuario autor) {
        Topico topico = topicoRepository.findById(topicoId)
            .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado con ID: " + topicoId));

        // Verificar que el tópico esté abierto
        if (topico.getStatus() == StatusTopico.CERRADO) {
            throw new IllegalArgumentException("No se pueden agregar respuestas a un tópico cerrado");
        }

        // Crear la respuesta
        Respuesta respuesta = new Respuesta(dto.mensaje(), topico, autor);
        respuesta = respuestaRepository.save(respuesta);

        return respuestaMapper.toDTO(respuesta);
    }

    /**
     * Obtiene las respuestas de un tópico.
     */
    @Transactional(readOnly = true)
    public List<RespuestaDTO> listarRespuestasPorTopico(Long topicoId) {
        Topico topico = topicoRepository.findById(topicoId)
            .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado con ID: " + topicoId));

        return respuestaRepository.findByTopicoOrderByFechaCreacionAsc(topico).stream()
            .map(respuestaMapper::toDTO)
            .collect(Collectors.toList());
    }

    /**
     * Obtiene las respuestas de un tópico con paginación.
     */
    @Transactional(readOnly = true)
    public Page<RespuestaDTO> listarRespuestasPorTopico(Long topicoId, Pageable pageable) {
        Topico topico = topicoRepository.findById(topicoId)
            .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado con ID: " + topicoId));

        return respuestaRepository.findByTopicoOrderByFechaCreacionAsc(topico, pageable)
            .map(respuestaMapper::toDTO);
    }

    /**
     * Actualiza una respuesta existente.
     */
    @Transactional
    public RespuestaDTO actualizarRespuesta(Long respuestaId, ActualizarRespuestaDTO dto, Usuario autor) {
        Respuesta respuesta = respuestaRepository.findByIdWithAuthor(respuestaId)
            .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada con ID: " + respuestaId));

        // Verificar que el usuario es el autor de la respuesta
        if (!respuesta.getAutor().getId().equals(autor.getId())) {
            throw new AccessDeniedException("No tienes permisos para actualizar esta respuesta");
        }

        // Actualizar el mensaje si se proporciona
        if (dto.mensaje() != null && !dto.mensaje().trim().isEmpty()) {
            respuesta.setMensaje(dto.mensaje().trim());
        }

        respuesta = respuestaRepository.save(respuesta);
        return respuestaMapper.toDTO(respuesta);
    }

    /**
     * Elimina una respuesta.
     */
    @Transactional
    public void eliminarRespuesta(Long respuestaId, Usuario autor) {
        Respuesta respuesta = respuestaRepository.findById(respuestaId)
            .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada con ID: " + respuestaId));

        // Verificar que el usuario es el autor de la respuesta
        if (!respuesta.getAutor().getId().equals(autor.getId())) {
            throw new AccessDeniedException("No tienes permisos para eliminar esta respuesta");
        }

        respuestaRepository.delete(respuesta);
    }

    /**
     * Marca una respuesta como solución.
     */
    @Transactional
    public RespuestaDTO marcarComoSolucion(Long respuestaId, Usuario usuario) {
        Respuesta respuesta = respuestaRepository.findByIdWithAuthor(respuestaId)
            .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada con ID: " + respuestaId));

        // Solo el autor del tópico puede marcar respuestas como solución
        if (!respuesta.getTopico().getAutor().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("Solo el autor del tópico puede marcar respuestas como solución");
        }

        // Desmarcar otras respuestas como solución en el mismo tópico
        List<Respuesta> respuestasSolucion = respuestaRepository.findByTopicoAndSolucionTrue(respuesta.getTopico());
        respuestasSolucion.forEach(r -> r.setSolucion(false));
        respuestaRepository.saveAll(respuestasSolucion);

        // Marcar esta respuesta como solución
        respuesta.setSolucion(true);
        respuesta = respuestaRepository.save(respuesta);

        // Actualizar el status del tópico a RESUELTO
        Topico topico = respuesta.getTopico();
        topico.setStatus(StatusTopico.RESUELTO);
        topicoRepository.save(topico);

        return respuestaMapper.toDTO(respuesta);
    }

    /**
     * Desmarca una respuesta como solución.
     */
    @Transactional
    public RespuestaDTO desmarcarComoSolucion(Long respuestaId, Usuario usuario) {
        Respuesta respuesta = respuestaRepository.findByIdWithAuthor(respuestaId)
            .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada con ID: " + respuestaId));

        // Solo el autor del tópico puede desmarcar respuestas como solución
        if (!respuesta.getTopico().getAutor().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("Solo el autor del tópico puede desmarcar respuestas como solución");
        }

        // Desmarcar como solución
        respuesta.setSolucion(false);
        respuesta = respuestaRepository.save(respuesta);

        // Si no hay más respuestas marcadas como solución, cambiar el status del tópico a ABIERTO
        boolean tieneSolucion = respuestaRepository.existsByTopicoAndSolucionTrue(respuesta.getTopico());
        if (!tieneSolucion) {
            Topico topico = respuesta.getTopico();
            topico.setStatus(StatusTopico.ABIERTO);
            topicoRepository.save(topico);
        }

        return respuestaMapper.toDTO(respuesta);
    }

    /**
     * Lista las respuestas de un autor específico.
     */
    @Transactional(readOnly = true)
    public Page<RespuestaDTO> listarRespuestasPorAutor(Usuario autor, Pageable pageable) {
        return respuestaRepository.findByAutorOrderByFechaCreacionDesc(autor, pageable)
            .map(respuestaMapper::toDTO);
    }
}