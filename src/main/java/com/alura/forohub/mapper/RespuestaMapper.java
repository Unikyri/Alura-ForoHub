package com.alura.forohub.mapper;

import com.alura.forohub.dto.PerfilDTO;
import com.alura.forohub.dto.RespuestaDTO;
import com.alura.forohub.dto.UsuarioDTO;
import com.alura.forohub.entity.Respuesta;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre entidades Respuesta y DTOs.
 */
@Component
public class RespuestaMapper {

    public RespuestaDTO toDTO(Respuesta respuesta) {
        UsuarioDTO autorDTO = new UsuarioDTO(
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
        );

        return new RespuestaDTO(
            respuesta.getId(),
            respuesta.getMensaje(),
            respuesta.getFechaCreacion(),
            respuesta.getFechaActualizacion(),
            autorDTO,
            respuesta.getSolucion()
        );
    }
}