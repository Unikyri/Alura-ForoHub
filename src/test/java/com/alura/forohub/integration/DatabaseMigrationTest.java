package com.alura.forohub.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DatabaseMigrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testTablesCreated() {
        // Verificar que las tablas fueron creadas
        assertDoesNotThrow(() -> {
            jdbcTemplate.queryForObject("SELECT COUNT(*) FROM perfiles", Integer.class);
            jdbcTemplate.queryForObject("SELECT COUNT(*) FROM cursos", Integer.class);
            jdbcTemplate.queryForObject("SELECT COUNT(*) FROM usuarios", Integer.class);
            jdbcTemplate.queryForObject("SELECT COUNT(*) FROM topicos", Integer.class);
            jdbcTemplate.queryForObject("SELECT COUNT(*) FROM respuestas", Integer.class);
        });
    }

    @Test
    void testInitialDataInserted() {
        // Verificar que los datos iniciales fueron insertados
        Integer perfilesCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM perfiles", Integer.class);
        Integer cursosCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM cursos", Integer.class);
        Integer usuariosCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM usuarios", Integer.class);

        assertNotNull(perfilesCount);
        assertNotNull(cursosCount);
        assertNotNull(usuariosCount);
        
        assertTrue(perfilesCount >= 3, "Debe haber al menos 3 perfiles");
        assertTrue(cursosCount >= 10, "Debe haber al menos 10 cursos");
        assertTrue(usuariosCount >= 1, "Debe haber al menos 1 usuario");
    }

    @Test
    void testPerfilesData() {
        // Verificar que los perfiles específicos existen
        Integer usuarioCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM perfiles WHERE nombre = 'Usuario' AND tipo = 'USUARIO'", 
            Integer.class
        );
        Integer moderadorCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM perfiles WHERE nombre = 'Moderador' AND tipo = 'MODERADOR'", 
            Integer.class
        );
        Integer adminCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM perfiles WHERE nombre = 'Administrador' AND tipo = 'ADMINISTRADOR'", 
            Integer.class
        );

        assertEquals(1, usuarioCount);
        assertEquals(1, moderadorCount);
        assertEquals(1, adminCount);
    }

    @Test
    void testCursosData() {
        // Verificar que algunos cursos específicos existen
        Integer javaCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM cursos WHERE nombre = 'Java Orientado a Objetos'", 
            Integer.class
        );
        Integer springCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM cursos WHERE nombre = 'Spring Framework'", 
            Integer.class
        );

        assertEquals(1, javaCount);
        assertEquals(1, springCount);
    }

    @Test
    void testUsuarioAdminExists() {
        // Verificar que el usuario administrador existe
        Integer adminCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM usuarios WHERE correo_electronico = 'admin@forohub.com'", 
            Integer.class
        );

        assertEquals(1, adminCount);
    }

    @Test
    void testForeignKeyConstraints() {
        // Verificar que las foreign keys están configuradas correctamente
        assertDoesNotThrow(() -> {
            // Intentar insertar un usuario con perfil_id inválido debería fallar
            try {
                jdbcTemplate.update(
                    "INSERT INTO usuarios (nombre, correo_electronico, contrasena, perfil_id) VALUES (?, ?, ?, ?)",
                    "Test User", "test@test.com", "password", 999L
                );
                fail("Debería haber fallado por foreign key constraint");
            } catch (Exception e) {
                // Se esperaba esta excepción
                assertTrue(e.getMessage().contains("foreign key constraint") || 
                          e.getMessage().contains("constraint"));
            }
        });
    }
}