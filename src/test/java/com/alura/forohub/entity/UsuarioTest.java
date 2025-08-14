package com.alura.forohub.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private Usuario usuario;
    private Perfil perfil;

    @BeforeEach
    void setUp() {
        perfil = new Perfil("Usuario", TipoPerfil.USUARIO, "Perfil básico");
        usuario = new Usuario("Juan Pérez", "juan@example.com", "password123", perfil);
    }

    @Test
    void testConstructorConParametros() {
        assertEquals("Juan Pérez", usuario.getNombre());
        assertEquals("juan@example.com", usuario.getCorreoElectronico());
        assertEquals("password123", usuario.getContrasena());
        assertEquals(perfil, usuario.getPerfil());
        assertTrue(usuario.getActivo());
        assertNotNull(usuario.getTopicos());
        assertNotNull(usuario.getRespuestas());
        assertTrue(usuario.getTopicos().isEmpty());
        assertTrue(usuario.getRespuestas().isEmpty());
    }

    @Test
    void testConstructorVacio() {
        Usuario usuarioVacio = new Usuario();
        assertNull(usuarioVacio.getNombre());
        assertNull(usuarioVacio.getCorreoElectronico());
        assertNull(usuarioVacio.getContrasena());
        assertNull(usuarioVacio.getPerfil());
        // El campo activo se inicializa por defecto en true
        assertTrue(usuarioVacio.getActivo());
    }

    @Test
    void testAddTopico() {
        Curso curso = new Curso("Java", "Programación");
        Topico topico = new Topico("Título", "Mensaje", usuario, curso);
        
        usuario.addTopico(topico);
        
        assertTrue(usuario.getTopicos().contains(topico));
        assertEquals(usuario, topico.getAutor());
    }

    @Test
    void testRemoveTopico() {
        Curso curso = new Curso("Java", "Programación");
        Topico topico = new Topico("Título", "Mensaje", usuario, curso);
        
        usuario.addTopico(topico);
        usuario.removeTopico(topico);
        
        assertFalse(usuario.getTopicos().contains(topico));
        assertNull(topico.getAutor());
    }

    @Test
    void testAddRespuesta() {
        Curso curso = new Curso("Java", "Programación");
        Topico topico = new Topico("Título", "Mensaje", usuario, curso);
        Respuesta respuesta = new Respuesta("Respuesta", topico, usuario);
        
        usuario.addRespuesta(respuesta);
        
        assertTrue(usuario.getRespuestas().contains(respuesta));
        assertEquals(usuario, respuesta.getAutor());
    }

    @Test
    void testRemoveRespuesta() {
        Curso curso = new Curso("Java", "Programación");
        Topico topico = new Topico("Título", "Mensaje", usuario, curso);
        Respuesta respuesta = new Respuesta("Respuesta", topico, usuario);
        
        usuario.addRespuesta(respuesta);
        usuario.removeRespuesta(respuesta);
        
        assertFalse(usuario.getRespuestas().contains(respuesta));
        assertNull(respuesta.getAutor());
    }

    @Test
    void testEquals() {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        
        Usuario usuario2 = new Usuario();
        usuario2.setId(1L);
        
        Usuario usuario3 = new Usuario();
        usuario3.setId(2L);
        
        assertEquals(usuario1, usuario2);
        assertNotEquals(usuario1, usuario3);
        assertNotEquals(usuario1, null);
        assertNotEquals(usuario1, "string");
    }

    @Test
    void testHashCode() {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        
        Usuario usuario2 = new Usuario();
        usuario2.setId(1L);
        
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
    }

    @Test
    void testToString() {
        usuario.setId(1L);
        String toString = usuario.toString();
        
        assertTrue(toString.contains("Usuario{"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("nombre='Juan Pérez'"));
        assertTrue(toString.contains("correoElectronico='juan@example.com'"));
        assertTrue(toString.contains("perfil=Usuario"));
        assertTrue(toString.contains("activo=true"));
    }
}