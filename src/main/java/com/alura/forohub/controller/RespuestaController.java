package com.alura.forohub.controller;

import com.alura.forohub.dto.ActualizarRespuestaDTO;
import com.alura.forohub.dto.CrearRespuestaDTO;
import com.alura.forohub.dto.RespuestaDTO;
import com.alura.forohub.entity.Usuario;
import com.alura.forohub.service.AuthenticationService;
import com.alura.forohub.service.RespuestaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para la gestión de respuestas.
 */
@RestController
@RequestMapping("/respuestas")
@Tag(name = "Respuestas", description = "Endpoints para gestión de respuestas a tópicos")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/topico/{topicoId}")
    @Operation(summary = "Crear respuesta", description = "Crea una nueva respuesta para un tópico")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Respuesta creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "404", description = "Tópico no encontrado")
    })
    public ResponseEntity<RespuestaDTO> crearRespuesta(
            @Parameter(description = "ID del tópico") @PathVariable Long topicoId,
            @Valid @RequestBody CrearRespuestaDTO dto) {
        
        Usuario autor = authenticationService.obtenerUsuarioActual();
        RespuestaDTO respuesta = respuestaService.crearRespuesta(topicoId, dto, autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping("/topico/{topicoId}")
    @Operation(summary = "Listar respuestas de tópico", description = "Obtiene todas las respuestas de un tópico específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de respuestas obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "Tópico no encontrado")
    })
    public ResponseEntity<List<RespuestaDTO>> listarRespuestasPorTopico(
            @Parameter(description = "ID del tópico") @PathVariable Long topicoId) {
        
        List<RespuestaDTO> respuestas = respuestaService.listarRespuestasPorTopico(topicoId);
        return ResponseEntity.ok(respuestas);
    }

    @GetMapping("/topico/{topicoId}/paginado")
    @Operation(summary = "Listar respuestas con paginación", description = "Obtiene las respuestas de un tópico con paginación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista paginada de respuestas"),
        @ApiResponse(responseCode = "404", description = "Tópico no encontrado")
    })
    public ResponseEntity<Page<RespuestaDTO>> listarRespuestasPorTopicoPaginado(
            @Parameter(description = "ID del tópico") @PathVariable Long topicoId,
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable pageable) {
        
        Page<RespuestaDTO> respuestas = respuestaService.listarRespuestasPorTopico(topicoId, pageable);
        return ResponseEntity.ok(respuestas);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar respuesta", description = "Actualiza una respuesta existente (solo el autor)")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Respuesta actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "Sin permisos para actualizar esta respuesta"),
        @ApiResponse(responseCode = "404", description = "Respuesta no encontrada")
    })
    public ResponseEntity<RespuestaDTO> actualizarRespuesta(
            @Parameter(description = "ID de la respuesta") @PathVariable Long id,
            @Valid @RequestBody ActualizarRespuestaDTO dto) {
        
        Usuario autor = authenticationService.obtenerUsuarioActual();
        RespuestaDTO respuesta = respuestaService.actualizarRespuesta(id, dto, autor);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar respuesta", description = "Elimina una respuesta existente (solo el autor)")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Respuesta eliminada exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "Sin permisos para eliminar esta respuesta"),
        @ApiResponse(responseCode = "404", description = "Respuesta no encontrada")
    })
    public ResponseEntity<Void> eliminarRespuesta(
            @Parameter(description = "ID de la respuesta") @PathVariable Long id) {
        
        Usuario autor = authenticationService.obtenerUsuarioActual();
        respuestaService.eliminarRespuesta(id, autor);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/solucion")
    @Operation(summary = "Marcar como solución", description = "Marca una respuesta como solución (solo el autor del tópico)")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Respuesta marcada como solución"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "Sin permisos para marcar esta respuesta como solución"),
        @ApiResponse(responseCode = "404", description = "Respuesta no encontrada")
    })
    public ResponseEntity<RespuestaDTO> marcarComoSolucion(
            @Parameter(description = "ID de la respuesta") @PathVariable Long id) {
        
        Usuario usuario = authenticationService.obtenerUsuarioActual();
        RespuestaDTO respuesta = respuestaService.marcarComoSolucion(id, usuario);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}/solucion")
    @Operation(summary = "Desmarcar como solución", description = "Desmarca una respuesta como solución (solo el autor del tópico)")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Respuesta desmarcada como solución"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "Sin permisos para desmarcar esta respuesta como solución"),
        @ApiResponse(responseCode = "404", description = "Respuesta no encontrada")
    })
    public ResponseEntity<RespuestaDTO> desmarcarComoSolucion(
            @Parameter(description = "ID de la respuesta") @PathVariable Long id) {
        
        Usuario usuario = authenticationService.obtenerUsuarioActual();
        RespuestaDTO respuesta = respuestaService.desmarcarComoSolucion(id, usuario);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/mis-respuestas")
    @Operation(summary = "Listar mis respuestas", description = "Obtiene las respuestas creadas por el usuario autenticado")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de respuestas del usuario"),
        @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<Page<RespuestaDTO>> listarMisRespuestas(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Usuario autor = authenticationService.obtenerUsuarioActual();
        Page<RespuestaDTO> respuestas = respuestaService.listarRespuestasPorAutor(autor, pageable);
        return ResponseEntity.ok(respuestas);
    }
}