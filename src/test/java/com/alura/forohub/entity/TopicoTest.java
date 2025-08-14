package com.alura.forohub.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TopicoTest {

    private Topico topico;
    private Usuario autor;
    private Curso curso;
    private Perfil perfil;

    @BeforeEach
    void setUp() {
        perfil = new Perfil("Usuario", TipoPerfil.USUARIO);
        autor = new Usuario("Juan Pérez", "juan@example.com", "password123", perfil);
        curso = new Curso("Java", "Programación");
        topico = new Topico("¿Cómo usar Spring Boot?", "Necesito ayuda con Spring Boot", autor, curso);
    }

    @Test
    void testConstructorConParametros() {
        assertEquals("¿Cómo usar Spring Boot?", topico.getTitulo());
        assertEquals("Necesito ayuda con Spring Boot", topico.getMensaje());
        assertEquals(autor, topico.getAutor());
        assertEquals(curso, topico.getCurso());
        assertEquals(StatusTopico.ABIERTO, topico.getStatus());
        assertNotNull(topico.getRespuestas());
        assertTrue(topico.getRespuestas().isEmpty());
    }

    @Test
    void testConstructorVacio() {
        Topico topicoVacio = new Topico();
        assertNull(topicoVacio.getTitulo());
        assertNull(topicoVacio.getMensaje());
        assertNull(topicoVacio.getAutor());
        assertNull(topicoVacio.getCurso());
        assertEquals(StatusTopico.ABIERTO, topicoVacio.getStatus());
    }

    @Test
    void testAddRespuesta() {
        Respuesta respuesta = new Respuesta("Esta es una respuesta", topico, autor);
        
        topico.addRespuesta(respuesta);
        
        assertTrue(topico.getRespuestas().contains(respuesta));
        assertEquals(topico, respuesta.getTopico());
        assertEquals(1, topico.getTotalRespuestas());
    }

    @Test
    void testRemoveRespuesta() {
        Respuesta respuesta = new Respuesta("Esta es una respuesta", topico, autor);
        
        topico.addRespuesta(respuesta);
        topico.removeRespuesta(respuesta);
        
        assertFalse(topico.getRespuestas().contains(respuesta));
        assertNull(respuesta.getTopico());
        assertEquals(0, topico.getTotalRespuestas());
    }

    @Test
    void testTieneRespuestaSolucion() {
        Respuesta respuesta1 = new Respuesta("Respuesta normal", topico, autor);
        Respuesta respuesta2 = new Respuesta("Respuesta solución", topico, autor);
        
        topico.addRespuesta(respuesta1);
        topico.addRespuesta(respuesta2);
        
        assertFalse(topico.tieneRespuestaSolucion());
        
        respuesta2.marcarComoSolucion();
        assertTrue(topico.tieneRespuestaSolucion());
    }

    @Test
    void testMarcarComoResuelto() {
        topico.marcarComoResuelto();
        assertEquals(StatusTopico.RESUELTO, topico.getStatus());
    }

    @Test
    void testCerrar() {
        topico.cerrar();
        assertEquals(StatusTopico.CERRADO, topico.getStatus());
    }

    @Test
    void testAbrir() {
        topico.cerrar();
        topico.abrir();
        assertEquals(StatusTopico.ABIERTO, topico.getStatus());
    }

    @Test
    void testEquals() {
        Topico topico1 = new Topico();
        topico1.setId(1L);
        
        Topico topico2 = new Topico();
        topico2.setId(1L);
        
        Topico topico3 = new Topico();
        topico3.setId(2L);
        
        assertEquals(topico1, topico2);
        assertNotEquals(topico1, topico3);
        assertNotEquals(topico1, null);
        assertNotEquals(topico1, "string");
    }

    @Test
    void testHashCode() {
        Topico topico1 = new Topico();
        topico1.setId(1L);
        
        Topico topico2 = new Topico();
        topico2.setId(1L);
        
        assertEquals(topico1.hashCode(), topico2.hashCode());
    }

    @Test
    void testToString() {
        topico.setId(1L);
        String toString = topico.toString();
        
        assertTrue(toString.contains("Topico{"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("titulo='¿Cómo usar Spring Boot?'"));
        assertTrue(toString.contains("status=ABIERTO"));
        assertTrue(toString.contains("autor=Juan Pérez"));
        assertTrue(toString.contains("curso=Java"));
        assertTrue(toString.contains("totalRespuestas=0"));
    }
}