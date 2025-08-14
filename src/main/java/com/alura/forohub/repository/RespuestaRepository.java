package com.alura.forohub.repository;

import com.alura.forohub.entity.Respuesta;
import com.alura.forohub.entity.Topico;
import com.alura.forohub.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Respuesta.
 */
@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    
    /**
     * Obtiene las respuestas de un tópico ordenadas por fecha de creación.
     */
    List<Respuesta> findByTopicoOrderByFechaCreacionAsc(Topico topico);
    
    /**
     * Obtiene las respuestas de un tópico con paginación.
     */
    Page<Respuesta> findByTopicoOrderByFechaCreacionAsc(Topico topico, Pageable pageable);
    
    /**
     * Obtiene las respuestas de un autor específico.
     */
    Page<Respuesta> findByAutorOrderByFechaCreacionDesc(Usuario autor, Pageable pageable);
    
    /**
     * Busca una respuesta por ID con autor y perfil cargados.
     */
    @Query("SELECT r FROM Respuesta r JOIN FETCH r.autor a JOIN FETCH a.perfil WHERE r.id = :id")
    Optional<Respuesta> findByIdWithAuthor(@Param("id") Long id);
    
    /**
     * Obtiene las respuestas marcadas como solución de un tópico.
     */
    List<Respuesta> findByTopicoAndSolucionTrue(Topico topico);
    
    /**
     * Cuenta el número de respuestas de un tópico.
     */
    long countByTopico(Topico topico);
    
    /**
     * Verifica si un tópico tiene respuestas marcadas como solución.
     */
    boolean existsByTopicoAndSolucionTrue(Topico topico);
}