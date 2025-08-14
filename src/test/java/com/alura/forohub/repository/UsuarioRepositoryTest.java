package com.alura.forohub.repository;

import com.alura.forohub.entity.Perfil;
import com.alura.forohub.entity.TipoPerfil;
import com.alura.forohub.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests del repositorio Usuario")
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Perfil perfilUsuario;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Crear perfil de prueba
        perfilUsuario = new Perfil("Usuario", TipoPerfil.USUARIO, "Perfil básico");
        entityManager.persistAndFlush(perfilUsuario);

        // Crear usuario de prueba
        usuario = new Usuario("Juan Pérez", "juan@example.com", "password123", perfilUsuario);
        entityManager.persistAndFlush(usuario);
    }

    @Test
    @DisplayName("Debe encontrar usuario por correo electrónico")
    void testFindByCorreoElectronico() {
        Optional<Usuario> resultado = usuarioRepository.findByCorreoElectronico("juan@example.com");
        
        assertTrue(resultado.isPresent());
        assertEquals("Juan Pérez", resultado.get().getNombre());
        assertEquals("juan@example.com", resultado.get().getCorreoElectronico());
    }

    @Test
    @DisplayName("Debe retornar vacío si no encuentra usuario por correo")
    void testFindByCorreoElectronicoNoEncontrado() {
        Optional<Usuario> resultado = usuarioRepository.findByCorreoElectronico("noexiste@example.com");
        
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Debe verificar existencia de usuario por correo electrónico")
    void testExistsByCorreoElectronico() {
        assertTrue(usuarioRepository.existsByCorreoElectronico("juan@example.com"));
        assertFalse(usuarioRepository.existsByCorreoElectronico("noexiste@example.com"));
    }

    @Test
    @DisplayName("Debe encontrar usuario con perfil cargado")
    void testFindByCorreoElectronicoWithPerfil() {
        Optional<Usuario> resultado = usuarioRepository.findByCorreoElectronicoWithPerfil("juan@example.com");
        
        assertTrue(resultado.isPresent());
        Usuario usuarioEncontrado = resultado.get();
        assertNotNull(usuarioEncontrado.getPerfil());
        assertEquals("Usuario", usuarioEncontrado.getPerfil().getNombre());
        assertEquals(TipoPerfil.USUARIO, usuarioEncontrado.getPerfil().getTipo());
    }

    @Test
    @DisplayName("Debe encontrar usuario por ID con perfil cargado")
    void testFindByIdWithPerfil() {
        Optional<Usuario> resultado = usuarioRepository.findByIdWithPerfil(usuario.getId());
        
        assertTrue(resultado.isPresent());
        Usuario usuarioEncontrado = resultado.get();
        assertNotNull(usuarioEncontrado.getPerfil());
        assertEquals("Usuario", usuarioEncontrado.getPerfil().getNombre());
    }
}