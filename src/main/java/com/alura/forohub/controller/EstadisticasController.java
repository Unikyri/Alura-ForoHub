package com.alura.forohub.controller;

import com.alura.forohub.dto.EstadisticasDTO;
import com.alura.forohub.service.EstadisticasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para estadísticas del foro.
 */
@RestController
@RequestMapping("/estadisticas")
@Tag(name = "Estadísticas", description = "Endpoints para obtener estadísticas del foro")
public class EstadisticasController {

    @Autowired
    private EstadisticasService estadisticasService;

    @GetMapping
    @Operation(summary = "Obtener estadísticas", description = "Obtiene estadísticas generales del foro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente")
    })
    public ResponseEntity<EstadisticasDTO> obtenerEstadisticas() {
        EstadisticasDTO estadisticas = estadisticasService.obtenerEstadisticas();
        return ResponseEntity.ok(estadisticas);
    }
}