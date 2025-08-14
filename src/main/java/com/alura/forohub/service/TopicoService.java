package com.alura.forohub.service;

import com.alura.forohub.dto.*;
import com.alura.forohub.entity.Curso;
import com.alura.forohub.entity.Topico;
import com.alura.forohub.entity.Usuario;
import com.alura.forohub.mapper.TopicoMapper;
import com.alura.forohub.repository.CursoRepository;
import com.alura.forohub.repository.TopicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para la gestión de tópicos.
 */
@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TopicoMapper topicoMapper;

    /**
     * Lista todos los tópicos con paginación.
     */
    @Transactional(readOnly = true)
    public Page<TopicoResponseDTO> listarTopicos(Pageable pageable) {
        return topicoRepository.findAllByOrderByFechaCreacionDesc(pageable)
            .map(topicoMapper::toResponseDTO);
    }

    /**
     * Obtiene un tópico por ID con todos sus detalles.
     */
    @Transactional(readOnly = true)
    public DetalleTopicoDTO obtenerTopicoPorId(Long id) {
        Topico topico = topicoRepository.findByIdWithFullDetails(id)
            .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado con ID: " + id));
        
        return topicoMapper.toDetalleDTO(topico);
    }

    /**
     * Crea un nuevo tópico.
     */
    @Transactional
    public TopicoResponseDTO crearTopico(CrearTopicoDTO dto, Usuario autor) {
        // Validar que el curso existe
        Curso curso = cursoRepository.findById(dto.cursoId())
            .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado con ID: " + dto.cursoId()));

        if (!curso.getActivo()) {
            throw new IllegalArgumentException("El curso no está activo");
        }

        // Crear el tópico
        Topico topico = new Topico(dto.titulo(), dto.mensaje(), autor, curso);
        topico = topicoRepository.save(topico);

        return topicoMapper.toResponseDTO(topico);
    }

    /**
     * Actualiza un tópico existente.
     */
    @Transactional
    public TopicoResponseDTO actualizarTopico(Long id, ActualizarTopicoDTO dto, Usuario autor) {
        Topico topico = topicoRepository.findByIdWithDetails(id)
            .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado con ID: " + id));

        // Verificar que el usuario es el autor del tópico
        if (!topico.getAutor().getId().equals(autor.getId())) {
            throw new AccessDeniedException("No tienes permisos para actualizar este tópico");
        }

        // Actualizar campos si se proporcionan
        if (dto.titulo() != null && !dto.titulo().trim().isEmpty()) {
            topico.setTitulo(dto.titulo().trim());
        }

        if (dto.mensaje() != null && !dto.mensaje().trim().isEmpty()) {
            topico.setMensaje(dto.mensaje().trim());
        }

        if (dto.cursoId() != null) {
            Curso curso = cursoRepository.findById(dto.cursoId())
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado con ID: " + dto.cursoId()));
            
            if (!curso.getActivo()) {
                throw new IllegalArgumentException("El curso no está activo");
            }
            
            topico.setCurso(curso);
        }

        topico = topicoRepository.save(topico);
        return topicoMapper.toResponseDTO(topico);
    }

    /**
     * Elimina un tópico.
     */
    @Transactional
    public void eliminarTopico(Long id, Usuario autor) {
        Topico topico = topicoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado con ID: " + id));

        // Verificar que el usuario es el autor del tópico
        if (!topico.getAutor().getId().equals(autor.getId())) {
            throw new AccessDeniedException("No tienes permisos para eliminar este tópico");
        }

        topicoRepository.delete(topico);
    }

    /**
     * Lista los tópicos de un autor específico.
     */
    @Transactional(readOnly = true)
    public Page<TopicoResponseDTO> listarTopicosPorAutor(Usuario autor, Pageable pageable) {
        return topicoRepository.findByAutorOrderByFechaCreacionDesc(autor, pageable)
            .map(topicoMapper::toResponseDTO);
    }

    /**
     * Busca tópicos por título.
     */
    @Transactional(readOnly = true)
    public Page<TopicoResponseDTO> buscarTopicosPorTitulo(String titulo, Pageable pageable) {
        return topicoRepository.findByTituloContainingIgnoreCaseOrderByFechaCreacionDesc(titulo, pageable)
            .map(topicoMapper::toResponseDTO);
    }

    /**
     * Lista tópicos por curso.
     */
    @Transactional(readOnly = true)
    public Page<TopicoResponseDTO> listarTopicosPorCurso(Long cursoId, Pageable pageable) {
        // Verificar que el curso existe
        if (!cursoRepository.existsById(cursoId)) {
            throw new EntityNotFoundException("Curso no encontrado con ID: " + cursoId);
        }

        return topicoRepository.findByCursoIdOrderByFechaCreacionDesc(cursoId, pageable)
            .map(topicoMapper::toResponseDTO);
    }
}