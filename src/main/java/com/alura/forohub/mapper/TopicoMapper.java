package com.alura.forohub.mapper;

import com.alura.forohub.dto.*;
import com.alura.forohub.entity.Topico;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre entidades Topico y DTOs.
 */
@Component
public class TopicoMapper {

    public TopicoResponseDTO toResponseDTO(Topico topico) {
        return new TopicoResponseDTO(
            topico.getId(),
            topico.getTitulo(),
            topico.getMensaje(),
            topico.getFechaCreacion(),
            topico.getStatus(),
            topico.getAutor().getNombre(),
            topico.getCurso().getNombre(),
            topico.getTotalRespuestas()
        );
    }

    public DetalleTopicoDTO toDetalleDTO(Topico topico) {
        UsuarioDTO autorDTO = new UsuarioDTO(
            topico.getAutor().getId(),
            topico.getAutor().getNombre(),
            topico.getAutor().getCorreoElectronico(),
            topico.getAutor().getFechaCreacion(),
            new PerfilDTO(
                topico.getAutor().getPerfil().getId(),
                topico.getAutor().getPerfil().getNombre(),
                topico.getAutor().getPerfil().getTipo(),
                topico.getAutor().getPerfil().getDescripcion()
            )
        );

        CursoDTO cursoDTO = new CursoDTO(
            topico.getCurso().getId(),
            topico.getCurso().getNombre(),
            topico.getCurso().getCategoria(),
            topico.getCurso().getDescripcion()
        );

        List<RespuestaDTO> respuestasDTO = topico.getRespuestas().stream()
            .map(respuesta -> new RespuestaDTO(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getFechaActualizacion(),
                new UsuarioDTO(
                    respuesta.getAutor().getId(),
                    respuesta.getAutor().getNombre(),
                    respuesta.getAutor().getCorreoElectronico(),
                    respuesta.getAutor().getFechaCreacion(),
                    new PerfilDTO(
                        respuesta.getAutor().getPerfil().getId(),
                        respuesta.getAutor().getPerfil().getNombre(),
                        respuesta.getAutor().getPerfil().getTipo(),
                        respuesta.getAutor().getPerfil().getDescripcion()
                    )
                ),
                respuesta.getSolucion()
            ))
            .collect(Collectors.toList());

        return new DetalleTopicoDTO(
            topico.getId(),
            topico.getTitulo(),
            topico.getMensaje(),
            topico.getFechaCreacion(),
            topico.getFechaActualizacion(),
            topico.getStatus(),
            autorDTO,
            cursoDTO,
            respuestasDTO
        );
    }
}