package com.alura.forohub.service;

import com.alura.forohub.dto.CrearTopicoDTO;
import com.alura.forohub.dto.DetalleTopicoDTO;
import com.alura.forohub.dto.TopicoResponseDTO;
import com.alura.forohub.entity.*;
import com.alura.forohub.mapper.TopicoMapper;
import com.alura.forohub.repository.CursoRepository;
import com.alura.forohub.repository.TopicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del servicio Topico")
class TopicoServiceTest {

    @Mock
    private TopicoRepository topicoRepository;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private TopicoMapper topicoMapper;

    @InjectMocks
    private TopicoService topicoService;

    private Usuario autor;
    private Curso curso;
    private Topico topico;
    private Perfil perfil;

    @BeforeEach
    void setUp() {
        perfil = new Perfil("Usuario", TipoPerfil.USUARIO, "Perfil básico");
        perfil.setId(1L);

        autor = new Usuario("Juan Pérez", "juan@example.com", "password123", perfil);
        autor.setId(1L);

        curso = new Curso("Java Básico", "Programación", "Curso de Java");
        curso.setId(1L);

        topico = new Topico("¿Cómo usar Spring Boot?", "Necesito ayuda con Spring Boot", autor, curso);
        topico.setId(1L);
        topico.setRespuestas(new ArrayList<>());
    }

    @Test
    @DisplayName("Debe listar tópicos con paginación")
    void testListarTopicos() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Topico> topicos = List.of(topico);
        Page<Topico> pageTopicos = new PageImpl<>(topicos, pageable, 1);
        
        TopicoResponseDTO responseDTO = new TopicoResponseDTO(
            1L, "¿Cómo usar Spring Boot?", "Necesito ayuda con Spring Boot",
            LocalDateTime.now(), StatusTopico.ABIERTO, "Juan Pérez", "Java Básico", 0
        );

        when(topicoRepository.findAllByOrderByFechaCreacionDesc(pageable)).thenReturn(pageTopicos);
        when(topicoMapper.toResponseDTO(topico)).thenReturn(responseDTO);

        // Act
        Page<TopicoResponseDTO> resultado = topicoService.listarTopicos(pageable);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("¿Cómo usar Spring Boot?", resultado.getContent().get(0).titulo());
        verify(topicoRepository).findAllByOrderByFechaCreacionDesc(pageable);
        verify(topicoMapper).toResponseDTO(topico);
    }

    @Test
    @DisplayName("Debe obtener tópico por ID con detalles")
    void testObtenerTopicoPorId() {
        // Arrange
        Long topicoId = 1L;
        DetalleTopicoDTO detalleDTO = mock(DetalleTopicoDTO.class);

        when(topicoRepository.findByIdWithFullDetails(topicoId)).thenReturn(Optional.of(topico));
        when(topicoMapper.toDetalleDTO(topico)).thenReturn(detalleDTO);

        // Act
        DetalleTopicoDTO resultado = topicoService.obtenerTopicoPorId(topicoId);

        // Assert
        assertNotNull(resultado);
        verify(topicoRepository).findByIdWithFullDetails(topicoId);
        verify(topicoMapper).toDetalleDTO(topico);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando tópico no existe")
    void testObtenerTopicoPorIdNoEncontrado() {
        // Arrange
        Long topicoId = 999L;
        when(topicoRepository.findByIdWithFullDetails(topicoId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class,
            () -> topicoService.obtenerTopicoPorId(topicoId)
        );

        assertEquals("Tópico no encontrado con ID: 999", exception.getMessage());
        verify(topicoRepository).findByIdWithFullDetails(topicoId);
        verifyNoInteractions(topicoMapper);
    }

    @Test
    @DisplayName("Debe crear tópico exitosamente")
    void testCrearTopico() {
        // Arrange
        CrearTopicoDTO dto = new CrearTopicoDTO("Nuevo tópico", "Mensaje del tópico", 1L);
        TopicoResponseDTO responseDTO = new TopicoResponseDTO(
            1L, "Nuevo tópico", "Mensaje del tópico",
            LocalDateTime.now(), StatusTopico.ABIERTO, "Juan Pérez", "Java Básico", 0
        );

        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(topicoRepository.save(any(Topico.class))).thenReturn(topico);
        when(topicoMapper.toResponseDTO(topico)).thenReturn(responseDTO);

        // Act
        TopicoResponseDTO resultado = topicoService.crearTopico(dto, autor);

        // Assert
        assertNotNull(resultado);
        assertEquals("Nuevo tópico", resultado.titulo());
        verify(cursoRepository).findById(1L);
        verify(topicoRepository).save(any(Topico.class));
        verify(topicoMapper).toResponseDTO(topico);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando curso no existe al crear tópico")
    void testCrearTopicoConCursoInexistente() {
        // Arrange
        CrearTopicoDTO dto = new CrearTopicoDTO("Nuevo tópico", "Mensaje del tópico", 999L);
        when(cursoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class,
            () -> topicoService.crearTopico(dto, autor)
        );

        assertEquals("Curso no encontrado con ID: 999", exception.getMessage());
        verify(cursoRepository).findById(999L);
        verifyNoInteractions(topicoRepository);
        verifyNoInteractions(topicoMapper);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando curso no está activo")
    void testCrearTopicoConCursoInactivo() {
        // Arrange
        CrearTopicoDTO dto = new CrearTopicoDTO("Nuevo tópico", "Mensaje del tópico", 1L);
        curso.setActivo(false);
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> topicoService.crearTopico(dto, autor)
        );

        assertEquals("El curso no está activo", exception.getMessage());
        verify(cursoRepository).findById(1L);
        verifyNoInteractions(topicoRepository);
        verifyNoInteractions(topicoMapper);
    }

    @Test
    @DisplayName("Debe eliminar tópico cuando el usuario es el autor")
    void testEliminarTopico() {
        // Arrange
        Long topicoId = 1L;
        when(topicoRepository.findById(topicoId)).thenReturn(Optional.of(topico));

        // Act
        topicoService.eliminarTopico(topicoId, autor);

        // Assert
        verify(topicoRepository).findById(topicoId);
        verify(topicoRepository).delete(topico);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando usuario no es el autor al eliminar")
    void testEliminarTopicoSinPermisos() {
        // Arrange
        Long topicoId = 1L;
        Usuario otroUsuario = new Usuario("Otro Usuario", "otro@example.com", "password", perfil);
        otroUsuario.setId(2L);
        
        when(topicoRepository.findById(topicoId)).thenReturn(Optional.of(topico));

        // Act & Assert
        AccessDeniedException exception = assertThrows(
            AccessDeniedException.class,
            () -> topicoService.eliminarTopico(topicoId, otroUsuario)
        );

        assertEquals("No tienes permisos para eliminar este tópico", exception.getMessage());
        verify(topicoRepository).findById(topicoId);
        verify(topicoRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Debe listar tópicos por autor")
    void testListarTopicosPorAutor() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Topico> topicos = List.of(topico);
        Page<Topico> pageTopicos = new PageImpl<>(topicos, pageable, 1);
        
        TopicoResponseDTO responseDTO = new TopicoResponseDTO(
            1L, "¿Cómo usar Spring Boot?", "Necesito ayuda con Spring Boot",
            LocalDateTime.now(), StatusTopico.ABIERTO, "Juan Pérez", "Java Básico", 0
        );

        when(topicoRepository.findByAutorOrderByFechaCreacionDesc(autor, pageable)).thenReturn(pageTopicos);
        when(topicoMapper.toResponseDTO(topico)).thenReturn(responseDTO);

        // Act
        Page<TopicoResponseDTO> resultado = topicoService.listarTopicosPorAutor(autor, pageable);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        verify(topicoRepository).findByAutorOrderByFechaCreacionDesc(autor, pageable);
        verify(topicoMapper).toResponseDTO(topico);
    }
}