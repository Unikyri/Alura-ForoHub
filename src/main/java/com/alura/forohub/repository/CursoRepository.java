package com.alura.forohub.repository;

import com.alura.forohub.entity.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Curso.
 */
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    
    /**
     * Obtiene todos los cursos activos.
     */
    List<Curso> findByActivoTrueOrderByNombre();
    
    /**
     * Obtiene cursos por categoría.
     */
    List<Curso> findByCategoriaAndActivoTrueOrderByNombre(String categoria);
    
    /**
     * Busca un curso por nombre.
     */
    Optional<Curso> findByNombre(String nombre);
    
    /**
     * Verifica si existe un curso con el nombre dado.
     */
    boolean existsByNombre(String nombre);
    
    /**
     * Obtiene cursos activos con paginación.
     */
    Page<Curso> findByActivoTrueOrderByNombre(Pageable pageable);
    
    /**
     * Busca cursos por nombre que contenga el texto dado.
     */
    @Query("SELECT c FROM Curso c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND c.activo = true ORDER BY c.nombre")
    List<Curso> findByNombreContainingIgnoreCaseAndActivoTrue(@Param("nombre") String nombre);
    
    /**
     * Obtiene todas las categorías distintas de cursos activos.
     */
    @Query("SELECT DISTINCT c.categoria FROM Curso c WHERE c.activo = true ORDER BY c.categoria")
    List<String> findDistinctCategoriasByActivoTrue();
}