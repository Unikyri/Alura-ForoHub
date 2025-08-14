package com.alura.forohub.service;

import com.alura.forohub.dto.CrearRespuestaDTO;
import com.alura.forohub.dto.RespuestaDTO;
import com.alura.forohub.entity.*;
import com.alura.forohub.mapper.RespuestaMapper;
import com.alura.forohub.repository.RespuestaRepository;
import com.alura.forohub.repository.TopicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del servicio Respuesta")
class RespuestaServiceTest {

    @Mock
    private RespuestaRepository respuestaRepository;

    @Mock
    private TopicoRepository topicoRepository;

    @Mock
    private RespuestaMapper respuestaMapper;

    @InjectMocks
    private RespuestaService respuestaService;

    private Usuario autor;
    private Usuario autorTopico;
    private Curso curso;
    private Topico topico;
    private Respuesta respuesta;
    private Perfil perfil;

    @BeforeEach
    void setUp() {
        perfil = new Perfil("Usuario", TipoPerfil.USUARIO, "Perfil básico");
        perfil.setId(1L);

        autor = new Usuario("Juan Pérez", "juan@example.com", "password123", perfil);
        autor.setId(1L);

        autorTopico = new Usuario("María García", "maria@example.com", "password123", perfil);
        autorTopico.setId(2L);

        curso = new Curso("Java Básico", "Programación", "Curso de Java");
        curso.setId(1L);

        topico = new Topico("¿Cómo usar Spring Boot?", "Necesito ayuda con Spring Boot", autorTopico, curso);
        topico.setId(1L);
        topico.setStatus(StatusTopico.ABIERTO);

        respuesta = new Respuesta("Esta es mi respuesta", topico, autor);
        respuesta.setId(1L);
    }

    @Test
    @DisplayName("Debe crear respuesta exitosamente")
    void testCrearRespuesta() {
        // Arrange
        CrearRespuestaDTO dto = new CrearRespuestaDTO("Nueva respuesta");
        RespuestaDTO responseDTO = mock(RespuestaDTO.class);

        when(topicoRepository.findById(1L)).thenReturn(Optional.of(topico));
        when(respuestaRepository.save(any(Respuesta.class))).thenReturn(respuesta);
        when(respuestaMapper.toDTO(respuesta)).thenReturn(responseDTO);

        // Act
        RespuestaDTO resultado = respuestaService.crearRespuesta(1L, dto, autor);

        // Assert
        assertNotNull(resultado);
        verify(topicoRepository).findById(1L);
        verify(respuestaRepository).save(any(Respuesta.class));
        verify(respuestaMapper).toDTO(respuesta);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando tópico no existe al crear respuesta")
    void testCrearRespuestaConTopicoInexistente() {
        // Arrange
        CrearRespuestaDTO dto = new CrearRespuestaDTO("Nueva respuesta");
        when(topicoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class,
            () -> respuestaService.crearRespuesta(999L, dto, autor)
        );

        assertEquals("Tópico no encontrado con ID: 999", exception.getMessage());
        verify(topicoRepository).findById(999L);
        verifyNoInteractions(respuestaRepository);
        verifyNoInteractions(respuestaMapper);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando tópico está cerrado")
    void testCrearRespuestaEnTopicoCerrado() {
        // Arrange
        CrearRespuestaDTO dto = new CrearRespuestaDTO("Nueva respuesta");
        topico.setStatus(StatusTopico.CERRADO);
        when(topicoRepository.findById(1L)).thenReturn(Optional.of(topico));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> respuestaService.crearRespuesta(1L, dto, autor)
        );

        assertEquals("No se pueden agregar respuestas a un tópico cerrado", exception.getMessage());
        verify(topicoRepository).findById(1L);
        verifyNoInteractions(respuestaRepository);
        verifyNoInteractions(respuestaMapper);
    }

    @Test
    @DisplayName("Debe listar respuestas por tópico")
    void testListarRespuestasPorTopico() {
        // Arrange
        List<Respuesta> respuestas = List.of(respuesta);
        RespuestaDTO responseDTO = mock(RespuestaDTO.class);

        when(topicoRepository.findById(1L)).thenReturn(Optional.of(topico));
        when(respuestaRepository.findByTopicoOrderByFechaCreacionAsc(topico)).thenReturn(respuestas);
        when(respuestaMapper.toDTO(respuesta)).thenReturn(responseDTO);

        // Act
        List<RespuestaDTO> resultado = respuestaService.listarRespuestasPorTopico(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(topicoRepository).findById(1L);
        verify(respuestaRepository).findByTopicoOrderByFechaCreacionAsc(topico);
        verify(respuestaMapper).toDTO(respuesta);
    }

    @Test
    @DisplayName("Debe marcar respuesta como solución")
    void testMarcarComoSolucion() {
        // Arrange
        RespuestaDTO responseDTO = mock(RespuestaDTO.class);
        
        when(respuestaRepository.findByIdWithAuthor(1L)).thenReturn(Optional.of(respuesta));
        when(respuestaRepository.findByTopicoAndSolucionTrue(topico)).thenReturn(new ArrayList<>());
        when(respuestaRepository.save(respuesta)).thenReturn(respuesta);
        when(topicoRepository.save(topico)).thenReturn(topico);
        when(respuestaMapper.toDTO(respuesta)).thenReturn(responseDTO);

        // Act
        RespuestaDTO resultado = respuestaService.marcarComoSolucion(1L, autorTopico);

        // Assert
        assertNotNull(resultado);
        assertTrue(respuesta.getSolucion());
        assertEquals(StatusTopico.RESUELTO, topico.getStatus());
        verify(respuestaRepository).findByIdWithAuthor(1L);
        verify(respuestaRepository).save(respuesta);
        verify(topicoRepository).save(topico);
        verify(respuestaMapper).toDTO(respuesta);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando usuario no es autor del tópico al marcar solución")
    void testMarcarComoSolucionSinPermisos() {
        // Arrange
        when(respuestaRepository.findByIdWithAuthor(1L)).thenReturn(Optional.of(respuesta));

        // Act & Assert
        AccessDeniedException exception = assertThrows(
            AccessDeniedException.class,
            () -> respuestaService.marcarComoSolucion(1L, autor) // autor no es el autor del tópico
        );

        assertEquals("Solo el autor del tópico puede marcar respuestas como solución", exception.getMessage());
        verify(respuestaRepository).findByIdWithAuthor(1L);
        verify(respuestaRepository, never()).save(any());
        verify(topicoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar respuesta cuando el usuario es el autor")
    void testEliminarRespuesta() {
        // Arrange
        when(respuestaRepository.findById(1L)).thenReturn(Optional.of(respuesta));

        // Act
        respuestaService.eliminarRespuesta(1L, autor);

        // Assert
        verify(respuestaRepository).findById(1L);
        verify(respuestaRepository).delete(respuesta);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando usuario no es el autor al eliminar")
    void testEliminarRespuestaSinPermisos() {
        // Arrange
        Usuario otroUsuario = new Usuario("Otro Usuario", "otro@example.com", "password", perfil);
        otroUsuario.setId(3L);
        
        when(respuestaRepository.findById(1L)).thenReturn(Optional.of(respuesta));

        // Act & Assert
        AccessDeniedException exception = assertThrows(
            AccessDeniedException.class,
            () -> respuestaService.eliminarRespuesta(1L, otroUsuario)
        );

        assertEquals("No tienes permisos para eliminar esta respuesta", exception.getMessage());
        verify(respuestaRepository).findById(1L);
        verify(respuestaRepository, never()).delete(any());
    }
}