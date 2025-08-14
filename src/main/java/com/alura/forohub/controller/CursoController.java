package com.alura.forohub.controller;

import com.alura.forohub.dto.CursoDTO;
import com.alura.forohub.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para la gestión de cursos.
 */
@RestController
@RequestMapping("/cursos")
@Tag(name = "Cursos", description = "Endpoints para consulta de cursos disponibles")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    @Operation(summary = "Listar cursos", description = "Obtiene todos los cursos activos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de cursos obtenida exitosamente")
    })
    public ResponseEntity<List<CursoDTO>> listarCursos() {
        List<CursoDTO> cursos = cursoService.listarCursosActivos();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/paginado")
    @Operation(summary = "Listar cursos con paginación", description = "Obtiene cursos activos con paginación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista paginada de cursos")
    })
    public ResponseEntity<Page<CursoDTO>> listarCursosPaginado(
            @PageableDefault(size = 10, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {
        
        Page<CursoDTO> cursos = cursoService.listarCursosActivos(pageable);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Listar cursos por categoría", description = "Obtiene cursos de una categoría específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de cursos de la categoría")
    })
    public ResponseEntity<List<CursoDTO>> listarCursosPorCategoria(
            @Parameter(description = "Nombre de la categoría") @PathVariable String categoria) {
        
        List<CursoDTO> cursos = cursoService.listarCursosPorCategoria(categoria);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar cursos", description = "Busca cursos por nombre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resultados de búsqueda")
    })
    public ResponseEntity<List<CursoDTO>> buscarCursos(
            @Parameter(description = "Término de búsqueda") @RequestParam String q) {
        
        List<CursoDTO> cursos = cursoService.buscarCursosPorNombre(q);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/categorias")
    @Operation(summary = "Listar categorías", description = "Obtiene todas las categorías de cursos disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de categorías")
    })
    public ResponseEntity<List<String>> listarCategorias() {
        List<String> categorias = cursoService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }
}