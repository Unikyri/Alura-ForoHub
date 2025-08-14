package com.alura.forohub.repository;

import com.alura.forohub.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests del repositorio Topico")
@ActiveProfiles("test")
class TopicoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TopicoRepository topicoRepository;

    private Usuario autor;
    private Curso curso;
    private Topico topico;

    @BeforeEach
    void setUp() {
        // Crear perfil
        Perfil perfil = new Perfil("Usuario", TipoPerfil.USUARIO);
        entityManager.persistAndFlush(perfil);

        // Crear autor
        autor = new Usuario("Juan Pérez", "juan@example.com", "password123", perfil);
        entityManager.persistAndFlush(autor);

        // Crear curso
        curso = new Curso("Java Básico", "Programación", "Curso de Java para principiantes");
        entityManager.persistAndFlush(curso);

        // Crear tópico
        topico = new Topico("¿Cómo usar Spring Boot?", "Necesito ayuda con Spring Boot", autor, curso);
        entityManager.persistAndFlush(topico);
    }

    @Test
    @DisplayName("Debe obtener tópicos ordenados por fecha de creación descendente")
    void testFindAllByOrderByFechaCreacionDesc() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Topico> resultado = topicoRepository.findAllByOrderByFechaCreacionDesc(pageable);
        
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.getTotalElements());
        assertEquals("¿Cómo usar Spring Boot?", resultado.getContent().get(0).getTitulo());
    }

    @Test
    @DisplayName("Debe obtener tópicos por autor")
    void testFindByAutorOrderByFechaCreacionDesc() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Topico> resultado = topicoRepository.findByAutorOrderByFechaCreacionDesc(autor, pageable);
        
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.getTotalElements());
        assertEquals(autor.getId(), resultado.getContent().get(0).getAutor().getId());
    }

    @Test
    @DisplayName("Debe encontrar tópico con detalles cargados")
    void testFindByIdWithDetails() {
        Optional<Topico> resultado = topicoRepository.findByIdWithDetails(topico.getId());
        
        assertTrue(resultado.isPresent());
        Topico topicoEncontrado = resultado.get();
        assertNotNull(topicoEncontrado.getAutor());
        assertNotNull(topicoEncontrado.getCurso());
        assertEquals("Juan Pérez", topicoEncontrado.getAutor().getNombre());
        assertEquals("Java Básico", topicoEncontrado.getCurso().getNombre());
    }

    @Test
    @DisplayName("Debe obtener tópicos por curso")
    void testFindByCursoIdOrderByFechaCreacionDesc() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Topico> resultado = topicoRepository.findByCursoIdOrderByFechaCreacionDesc(curso.getId(), pageable);
        
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.getTotalElements());
        assertEquals(curso.getId(), resultado.getContent().get(0).getCurso().getId());
    }

    @Test
    @DisplayName("Debe buscar tópicos por título")
    void testFindByTituloContainingIgnoreCaseOrderByFechaCreacionDesc() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Topico> resultado = topicoRepository.findByTituloContainingIgnoreCaseOrderByFechaCreacionDesc("spring", pageable);
        
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.getTotalElements());
        assertTrue(resultado.getContent().get(0).getTitulo().toLowerCase().contains("spring"));
    }
}