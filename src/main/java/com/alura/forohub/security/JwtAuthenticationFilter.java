package com.alura.forohub.security;

import com.alura.forohub.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticación JWT que se ejecuta en cada request.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String correoElectronico = null;

        // Extraer token del header Authorization
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                correoElectronico = jwtUtil.validateToken(token);
            } catch (Exception e) {
                logger.warn("Token JWT inválido: " + e.getMessage());
            }
        }

        // Si tenemos un correo válido y no hay autenticación previa
        if (correoElectronico != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // Buscar usuario en la base de datos
            usuarioRepository.findByCorreoElectronicoWithPerfil(correoElectronico)
                .ifPresent(usuario -> {
                    if (usuario.getActivo()) {
                        // Crear UserDetails personalizado
                        UserDetails userDetails = new UsuarioUserDetails(usuario);
                        
                        // Crear token de autenticación
                        UsernamePasswordAuthenticationToken authToken = 
                            new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        
                        // Establecer autenticación en el contexto de seguridad
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                });
        }

        filterChain.doFilter(request, response);
    }
}