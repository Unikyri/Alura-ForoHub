package com.alura.forohub.service;

import com.alura.forohub.dto.LoginDTO;
import com.alura.forohub.dto.RegistroUsuarioDTO;
import com.alura.forohub.dto.TokenDTO;
import com.alura.forohub.entity.Perfil;
import com.alura.forohub.entity.TipoPerfil;
import com.alura.forohub.entity.Usuario;
import com.alura.forohub.repository.PerfilRepository;
import com.alura.forohub.repository.UsuarioRepository;
import com.alura.forohub.security.JwtUtil;
import com.alura.forohub.security.UsuarioUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para manejo de autenticación y registro de usuarios.
 */
@Service
public class AuthenticationService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Registra un nuevo usuario en el sistema.
     */
    @Transactional
    public TokenDTO registrarUsuario(RegistroUsuarioDTO dto) {
        // Verificar si el email ya existe
        if (usuarioRepository.existsByCorreoElectronico(dto.correoElectronico())) {
            throw new IllegalArgumentException("Ya existe un usuario con este correo electrónico");
        }

        // Obtener perfil por defecto (USUARIO)
        Perfil perfilUsuario = perfilRepository.findFirstByTipo(TipoPerfil.USUARIO)
            .orElseThrow(() -> new RuntimeException("Perfil de usuario no encontrado"));

        // Crear nuevo usuario
        Usuario nuevoUsuario = new Usuario(
            dto.nombre(),
            dto.correoElectronico(),
            passwordEncoder.encode(dto.contrasena()),
            perfilUsuario
        );

        // Guardar usuario
        usuarioRepository.save(nuevoUsuario);

        // Generar token JWT
        String token = jwtUtil.generateToken(dto.correoElectronico());
        Long expiration = System.currentTimeMillis() + (jwtUtil.getExpirationTime() * 1000);

        return new TokenDTO(token, expiration);
    }

    /**
     * Autentica un usuario y genera un token JWT.
     */
    public TokenDTO autenticarUsuario(LoginDTO dto) {
        try {
            // Autenticar credenciales
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.correoElectronico(), dto.contrasena())
            );

            // Verificar que el usuario esté activo
            Usuario usuario = usuarioRepository.findByCorreoElectronico(dto.correoElectronico())
                .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));

            if (!usuario.getActivo()) {
                throw new BadCredentialsException("Usuario inactivo");
            }

            // Generar token JWT
            String token = jwtUtil.generateToken(dto.correoElectronico());
            Long expiration = System.currentTimeMillis() + (jwtUtil.getExpirationTime() * 1000);

            return new TokenDTO(token, expiration);

        } catch (Exception e) {
            throw new BadCredentialsException("Credenciales inválidas");
        }
    }

    /**
     * Valida un token JWT.
     */
    public void validarToken(String token) {
        jwtUtil.validateToken(token);
    }

    /**
     * Obtiene el usuario actualmente autenticado.
     */
    public Usuario obtenerUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }

        if (authentication.getPrincipal() instanceof UsuarioUserDetails userDetails) {
            return userDetails.getUsuario();
        }

        throw new RuntimeException("Principal no es del tipo esperado");
    }
}