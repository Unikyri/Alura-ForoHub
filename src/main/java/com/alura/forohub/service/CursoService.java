package com.alura.forohub.service;

import com.alura.forohub.dto.CursoDTO;
import com.alura.forohub.entity.Curso;
import com.alura.forohub.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de cursos.
 */
@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    /**
     * Lista todos los cursos activos.
     */
    @Transactional(readOnly = true)
    public List<CursoDTO> listarCursosActivos() {
        return cursoRepository.findByActivoTrueOrderByNombre().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    /**
     * Lista cursos activos con paginación.
     */
    @Transactional(readOnly = true)
    public Page<CursoDTO> listarCursosActivos(Pageable pageable) {
        return cursoRepository.findByActivoTrueOrderByNombre(pageable)
            .map(this::toDTO);
    }

    /**
     * Lista cursos por categoría.
     */
    @Transactional(readOnly = true)
    public List<CursoDTO> listarCursosPorCategoria(String categoria) {
        return cursoRepository.findByCategoriaAndActivoTrueOrderByNombre(categoria).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    /**
     * Busca cursos por nombre.
     */
    @Transactional(readOnly = true)
    public List<CursoDTO> buscarCursosPorNombre(String nombre) {
        return cursoRepository.findByNombreContainingIgnoreCaseAndActivoTrue(nombre).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    /**
     * Obtiene todas las categorías disponibles.
     */
    @Transactional(readOnly = true)
    public List<String> listarCategorias() {
        return cursoRepository.findDistinctCategoriasByActivoTrue();
    }

    /**
     * Convierte una entidad Curso a DTO.
     */
    private CursoDTO toDTO(Curso curso) {
        return new CursoDTO(
            curso.getId(),
            curso.getNombre(),
            curso.getCategoria(),
            curso.getDescripcion()
        );
    }
}