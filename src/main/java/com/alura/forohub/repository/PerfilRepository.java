package com.alura.forohub.repository;

import com.alura.forohub.entity.Perfil;
import com.alura.forohub.entity.TipoPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Perfil.
 */
@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    
    /**
     * Busca un perfil por nombre.
     */
    Optional<Perfil> findByNombre(String nombre);
    
    /**
     * Busca perfiles por tipo.
     */
    List<Perfil> findByTipo(TipoPerfil tipo);
    
    /**
     * Verifica si existe un perfil con el nombre dado.
     */
    boolean existsByNombre(String nombre);
    
    /**
     * Obtiene el primer perfil por tipo.
     */
    Optional<Perfil> findFirstByTipo(TipoPerfil tipo);
}