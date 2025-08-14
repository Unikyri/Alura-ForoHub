package com.alura.forohub.config;

import com.alura.forohub.entity.Curso;
import com.alura.forohub.entity.Perfil;
import com.alura.forohub.entity.TipoPerfil;
import com.alura.forohub.entity.Usuario;
import com.alura.forohub.repository.CursoRepository;
import com.alura.forohub.repository.PerfilRepository;
import com.alura.forohub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Componente para inicializar datos por defecto en la base de datos.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializePerfiles();
        initializeCursos();
        initializeAdminUser();
    }

    private void initializePerfiles() {
        if (perfilRepository.count() == 0) {
            Perfil usuario = new Perfil("Usuario", TipoPerfil.USUARIO, "Perfil básico de usuario del foro");
            Perfil moderador = new Perfil("Moderador", TipoPerfil.MODERADOR, "Perfil con permisos de moderación");
            Perfil administrador = new Perfil("Administrador", TipoPerfil.ADMINISTRADOR, "Perfil con permisos administrativos completos");

            perfilRepository.save(usuario);
            perfilRepository.save(moderador);
            perfilRepository.save(administrador);
        }
    }

    private void initializeCursos() {
        if (cursoRepository.count() == 0) {
            Curso curso1 = new Curso("Java Orientado a Objetos", "Programación", "Curso completo de Java con enfoque en programación orientada a objetos");
            curso1.setActivo(true);
            cursoRepository.save(curso1);

            Curso curso2 = new Curso("Spring Framework", "Programación", "Desarrollo de aplicaciones web con Spring Boot y Spring MVC");
            curso2.setActivo(true);
            cursoRepository.save(curso2);

            Curso curso3 = new Curso("JavaScript para Web", "Frontend", "Desarrollo frontend moderno con JavaScript, HTML y CSS");
            curso3.setActivo(true);
            cursoRepository.save(curso3);

            Curso curso4 = new Curso("React: Desarrollando con JavaScript", "Frontend", "Creación de interfaces de usuario con React");
            curso4.setActivo(true);
            cursoRepository.save(curso4);

            Curso curso5 = new Curso("Python para Data Science", "Data Science", "Análisis de datos y machine learning con Python");
            curso5.setActivo(true);
            cursoRepository.save(curso5);

            Curso curso6 = new Curso("SQL con MySQL", "Base de Datos", "Gestión de bases de datos relacionales con MySQL");
            curso6.setActivo(true);
            cursoRepository.save(curso6);

            Curso curso7 = new Curso("Git y GitHub", "DevOps", "Control de versiones y colaboración en proyectos de software");
            curso7.setActivo(true);
            cursoRepository.save(curso7);

            Curso curso8 = new Curso("Docker: Creando containers", "DevOps", "Containerización de aplicaciones con Docker");
            curso8.setActivo(true);
            cursoRepository.save(curso8);

            Curso curso9 = new Curso("Lógica de Programación", "Fundamentos", "Conceptos básicos de programación y algoritmos");
            curso9.setActivo(true);
            cursoRepository.save(curso9);

            Curso curso10 = new Curso("HTML y CSS", "Frontend", "Fundamentos del desarrollo web con HTML5 y CSS3");
            curso10.setActivo(true);
            cursoRepository.save(curso10);
        }
    }

    private void initializeAdminUser() {
        if (usuarioRepository.count() == 0) {
            Perfil adminPerfil = perfilRepository.findFirstByTipo(TipoPerfil.ADMINISTRADOR)
                    .orElseThrow(() -> new RuntimeException("Perfil de administrador no encontrado"));

            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setCorreoElectronico("admin@forohub.com");
            admin.setContrasena(passwordEncoder.encode("admin123"));
            admin.setPerfil(adminPerfil);
            admin.setActivo(true);
            admin.setFechaCreacion(LocalDateTime.now());

            usuarioRepository.save(admin);
        }
    }
}