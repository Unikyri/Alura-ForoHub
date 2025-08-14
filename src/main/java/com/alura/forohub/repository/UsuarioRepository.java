package com.alura.forohub.repository;

import com.alura.forohub.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Usuario.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca un usuario por su correo electrónico.
     */
    Optional<Usuario> findByCorreoElectronico(String correoElectronico);
    
    /**
     * Verifica si existe un usuario con el correo electrónico dado.
     */
    boolean existsByCorreoElectronico(String correoElectronico);
    
    /**
     * Busca un usuario por correo electrónico con su perfil cargado.
     */
    @Query("SELECT u FROM Usuario u JOIN FETCH u.perfil WHERE u.correoElectronico = :correoElectronico")
    Optional<Usuario> findByCorreoElectronicoWithPerfil(@Param("correoElectronico") String correoElectronico);
    
    /**
     * Busca un usuario por ID con su perfil cargado.
     */
    @Query("SELECT u FROM Usuario u JOIN FETCH u.perfil WHERE u.id = :id")
    Optional<Usuario> findByIdWithPerfil(@Param("id") Long id);
}