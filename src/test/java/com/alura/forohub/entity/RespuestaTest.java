package com.alura.forohub.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RespuestaTest {

    private Respuesta respuesta;
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
        respuesta = new Respuesta("Puedes empezar con Spring Initializr", topico, autor);
    }

    @Test
    void testConstructorConParametros() {
        assertEquals("Puedes empezar con Spring Initializr", respuesta.getMensaje());
        assertEquals(topico, respuesta.getTopico());
        assertEquals(autor, respuesta.getAutor());
        assertFalse(respuesta.getSolucion());
    }

    @Test
    void testConstructorVacio() {
        Respuesta respuestaVacia = new Respuesta();
        assertNull(respuestaVacia.getMensaje());
        assertNull(respuestaVacia.getTopico());
        assertNull(respuestaVacia.getAutor());
        assertFalse(respuestaVacia.getSolucion());
    }

    @Test
    void testMarcarComoSolucion() {
        respuesta.marcarComoSolucion();
        assertTrue(respuesta.getSolucion());
        assertEquals(StatusTopico.RESUELTO, topico.getStatus());
    }

    @Test
    void testDesmarcarComoSolucion() {
        respuesta.marcarComoSolucion();
        respuesta.desmarcarComoSolucion();
        assertFalse(respuesta.getSolucion());
    }

    @Test
    void testSetSolucionTrue() {
        respuesta.setSolucion(true);
        assertTrue(respuesta.getSolucion());
        assertEquals(StatusTopico.RESUELTO, topico.getStatus());
    }

    @Test
    void testSetSolucionFalse() {
        respuesta.setSolucion(true);
        respuesta.setSolucion(false);
        assertFalse(respuesta.getSolucion());
    }

    @Test
    void testSetSolucionConTopicoNull() {
        Respuesta respuestaSinTopico = new Respuesta();
        respuestaSinTopico.setSolucion(true);
        assertTrue(respuestaSinTopico.getSolucion());
        // No debe lanzar excepción aunque topico sea null
    }

    @Test
    void testEquals() {
        Respuesta respuesta1 = new Respuesta();
        respuesta1.setId(1L);
        
        Respuesta respuesta2 = new Respuesta();
        respuesta2.setId(1L);
        
        Respuesta respuesta3 = new Respuesta();
        respuesta3.setId(2L);
        
        assertEquals(respuesta1, respuesta2);
        assertNotEquals(respuesta1, respuesta3);
        assertNotEquals(respuesta1, null);
        assertNotEquals(respuesta1, "string");
    }

    @Test
    void testHashCode() {
        Respuesta respuesta1 = new Respuesta();
        respuesta1.setId(1L);
        
        Respuesta respuesta2 = new Respuesta();
        respuesta2.setId(1L);
        
        assertEquals(respuesta1.hashCode(), respuesta2.hashCode());
    }

    @Test
    void testToString() {
        respuesta.setId(1L);
        String toString = respuesta.toString();
        
        assertTrue(toString.contains("Respuesta{"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("autor=Juan Pérez"));
        assertTrue(toString.contains("topico=¿Cómo usar Spring Boot?"));
        assertTrue(toString.contains("solucion=false"));
    }
}