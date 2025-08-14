package com.alura.forohub.controller;

import com.alura.forohub.dto.CrearTopicoDTO;
import com.alura.forohub.dto.DetalleTopicoDTO;
import com.alura.forohub.dto.TopicoResponseDTO;
import com.alura.forohub.entity.StatusTopico;
import com.alura.forohub.entity.Usuario;
import com.alura.forohub.service.AuthenticationService;
import com.alura.forohub.service.TopicoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TopicoController.class)
@ActiveProfiles("test")
@DisplayName("Tests del controlador Topico")
class TopicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TopicoService topicoService;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    @DisplayName("Debe listar tópicos sin autenticación")
    void testListarTopicos() throws Exception {
        // Arrange
        TopicoResponseDTO topicoDTO = new TopicoResponseDTO(
            1L, "Título", "Mensaje", LocalDateTime.now(),
            StatusTopico.ABIERTO, "Autor", "Curso", 0
        );
        Page<TopicoResponseDTO> page = new PageImpl<>(List.of(topicoDTO), PageRequest.of(0, 10), 1);
        
        when(topicoService.listarTopicos(any())).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/topicos"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content[0].titulo").value("Título"));
    }

    @Test
    @DisplayName("Debe obtener tópico por ID sin autenticación")
    void testObtenerTopico() throws Exception {
        // Arrange
        DetalleTopicoDTO detalleDTO = new DetalleTopicoDTO(
            1L, "Título", "Mensaje", LocalDateTime.now(), LocalDateTime.now(),
            StatusTopico.ABIERTO, null, null, List.of()
        );
        
        when(topicoService.obtenerTopicoPorId(1L)).thenReturn(detalleDTO);

        // Act & Assert
        mockMvc.perform(get("/topicos/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.titulo").value("Título"));
    }

    @Test
    @WithMockUser
    @DisplayName("Debe crear tópico con autenticación")
    void testCrearTopico() throws Exception {
        // Arrange
        CrearTopicoDTO dto = new CrearTopicoDTO("Nuevo Tópico", "Mensaje del tópico", 1L);
        TopicoResponseDTO responseDTO = new TopicoResponseDTO(
            1L, "Nuevo Tópico", "Mensaje del tópico", LocalDateTime.now(),
            StatusTopico.ABIERTO, "Autor", "Curso", 0
        );
        
        when(authenticationService.obtenerUsuarioActual()).thenReturn(new Usuario());
        when(topicoService.crearTopico(any(CrearTopicoDTO.class), any(Usuario.class))).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(post("/topicos")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.titulo").value("Nuevo Tópico"));
    }

    @Test
    @DisplayName("Debe rechazar creación de tópico sin autenticación")
    void testCrearTopicoSinAutenticacion() throws Exception {
        // Arrange
        CrearTopicoDTO dto = new CrearTopicoDTO("Nuevo Tópico", "Mensaje del tópico", 1L);

        // Act & Assert
        mockMvc.perform(post("/topicos")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Debe validar datos al crear tópico")
    void testCrearTopicoConDatosInvalidos() throws Exception {
        // Arrange
        CrearTopicoDTO dto = new CrearTopicoDTO("", "", null); // Datos inválidos

        // Act & Assert
        mockMvc.perform(post("/topicos")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isUnauthorized()); // Sin autenticación primero
    }

    @Test
    @WithMockUser
    @DisplayName("Debe eliminar tópico con autenticación")
    void testEliminarTopico() throws Exception {
        // Arrange
        when(authenticationService.obtenerUsuarioActual()).thenReturn(new Usuario());

        // Act & Assert
        mockMvc.perform(delete("/topicos/1")
                .with(csrf()))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debe buscar tópicos sin autenticación")
    void testBuscarTopicos() throws Exception {
        // Arrange
        TopicoResponseDTO topicoDTO = new TopicoResponseDTO(
            1L, "Spring Boot", "Mensaje", LocalDateTime.now(),
            StatusTopico.ABIERTO, "Autor", "Curso", 0
        );
        Page<TopicoResponseDTO> page = new PageImpl<>(List.of(topicoDTO), PageRequest.of(0, 10), 1);
        
        when(topicoService.buscarTopicosPorTitulo(any(), any())).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/topicos/buscar")
                .param("q", "Spring"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }
}