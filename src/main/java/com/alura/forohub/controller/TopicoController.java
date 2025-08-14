package com.alura.forohub.controller;

import com.alura.forohub.dto.*;
import com.alura.forohub.entity.Usuario;
import com.alura.forohub.service.AuthenticationService;
import com.alura.forohub.service.TopicoService;
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

/**
 * Controlador para la gestión de tópicos.
 */
@RestController
@RequestMapping("/topicos")
@Tag(name = "Tópicos", description = "Endpoints para gestión de tópicos del foro")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping
    @Operation(summary = "Listar tópicos", description = "Obtiene una lista paginada de todos los tópicos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tópicos obtenida exitosamente")
    })
    public ResponseEntity<Page<TopicoResponseDTO>> listarTopicos(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Page<TopicoResponseDTO> topicos = topicoService.listarTopicos(pageable);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tópico por ID", description = "Obtiene los detalles completos de un tópico específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tópico encontrado"),
        @ApiResponse(responseCode = "404", description = "Tópico no encontrado")
    })
    public ResponseEntity<DetalleTopicoDTO> obtenerTopico(
            @Parameter(description = "ID del tópico") @PathVariable Long id) {
        
        DetalleTopicoDTO topico = topicoService.obtenerTopicoPorId(id);
        return ResponseEntity.ok(topico);
    }

    @PostMapping
    @Operation(summary = "Crear tópico", description = "Crea un nuevo tópico en el foro")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tópico creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    public ResponseEntity<TopicoResponseDTO> crearTopico(@Valid @RequestBody CrearTopicoDTO dto) {
        Usuario autor = authenticationService.obtenerUsuarioActual();
        TopicoResponseDTO topico = topicoService.crearTopico(dto, autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(topico);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tópico", description = "Actualiza un tópico existente (solo el autor)")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tópico actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "Sin permisos para actualizar este tópico"),
        @ApiResponse(responseCode = "404", description = "Tópico no encontrado")
    })
    public ResponseEntity<TopicoResponseDTO> actualizarTopico(
            @Parameter(description = "ID del tópico") @PathVariable Long id,
            @Valid @RequestBody ActualizarTopicoDTO dto) {
        
        Usuario autor = authenticationService.obtenerUsuarioActual();
        TopicoResponseDTO topico = topicoService.actualizarTopico(id, dto, autor);
        return ResponseEntity.ok(topico);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tópico", description = "Elimina un tópico existente (solo el autor)")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tópico eliminado exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "Sin permisos para eliminar este tópico"),
        @ApiResponse(responseCode = "404", description = "Tópico no encontrado")
    })
    public ResponseEntity<Void> eliminarTopico(
            @Parameter(description = "ID del tópico") @PathVariable Long id) {
        
        Usuario autor = authenticationService.obtenerUsuarioActual();
        topicoService.eliminarTopico(id, autor);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mis-topicos")
    @Operation(summary = "Listar mis tópicos", description = "Obtiene los tópicos creados por el usuario autenticado")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tópicos del usuario"),
        @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<Page<TopicoResponseDTO>> listarMisTopicos(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Usuario autor = authenticationService.obtenerUsuarioActual();
        Page<TopicoResponseDTO> topicos = topicoService.listarTopicosPorAutor(autor, pageable);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar tópicos", description = "Busca tópicos por título")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resultados de búsqueda")
    })
    public ResponseEntity<Page<TopicoResponseDTO>> buscarTopicos(
            @Parameter(description = "Término de búsqueda") @RequestParam String q,
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Page<TopicoResponseDTO> topicos = topicoService.buscarTopicosPorTitulo(q, pageable);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/curso/{cursoId}")
    @Operation(summary = "Listar tópicos por curso", description = "Obtiene los tópicos de un curso específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tópicos del curso"),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    public ResponseEntity<Page<TopicoResponseDTO>> listarTopicosPorCurso(
            @Parameter(description = "ID del curso") @PathVariable Long cursoId,
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Page<TopicoResponseDTO> topicos = topicoService.listarTopicosPorCurso(cursoId, pageable);
        return ResponseEntity.ok(topicos);
    }
}