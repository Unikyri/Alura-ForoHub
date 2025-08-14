package com.alura.forohub.repository;

import com.alura.forohub.entity.Topico;
import com.alura.forohub.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Topico.
 */
@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    
    /**
     * Obtiene todos los tópicos ordenados por fecha de creación descendente.
     */
    Page<Topico> findAllByOrderByFechaCreacionDesc(Pageable pageable);
    
    /**
     * Obtiene los tópicos de un autor específico ordenados por fecha de creación descendente.
     */
    Page<Topico> findByAutorOrderByFechaCreacionDesc(Usuario autor, Pageable pageable);
    
    /**
     * Busca un tópico por ID con todos sus detalles cargados (autor, curso).
     */
    @Query("SELECT t FROM Topico t JOIN FETCH t.autor JOIN FETCH t.curso WHERE t.id = :id")
    Optional<Topico> findByIdWithDetails(@Param("id") Long id);
    
    /**
     * Busca un tópico por ID con todos sus detalles y respuestas cargados.
     */
    @Query("SELECT t FROM Topico t " +
           "JOIN FETCH t.autor a " +
           "JOIN FETCH a.perfil " +
           "JOIN FETCH t.curso " +
           "LEFT JOIN FETCH t.respuestas r " +
           "LEFT JOIN FETCH r.autor ra " +
           "LEFT JOIN FETCH ra.perfil " +
           "WHERE t.id = :id")
    Optional<Topico> findByIdWithFullDetails(@Param("id") Long id);
    
    /**
     * Obtiene tópicos por curso ordenados por fecha de creación descendente.
     */
    @Query("SELECT t FROM Topico t WHERE t.curso.id = :cursoId ORDER BY t.fechaCreacion DESC")
    Page<Topico> findByCursoIdOrderByFechaCreacionDesc(@Param("cursoId") Long cursoId, Pageable pageable);
    
    /**
     * Busca tópicos por título que contenga el texto dado.
     */
    @Query("SELECT t FROM Topico t WHERE LOWER(t.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')) ORDER BY t.fechaCreacion DESC")
    Page<Topico> findByTituloContainingIgnoreCaseOrderByFechaCreacionDesc(@Param("titulo") String titulo, Pageable pageable);
}