-- Crear tabla de perfiles
CREATE TABLE perfiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    tipo VARCHAR(20) NOT NULL,
    descripcion VARCHAR(200)
);

-- Crear índices para perfiles
CREATE INDEX idx_perfil_tipo ON perfiles(tipo);

-- Crear tabla de cursos
CREATE TABLE cursos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    categoria VARCHAR(50) NOT NULL,
    descripcion VARCHAR(500),
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Crear índices para cursos
CREATE INDEX idx_curso_categoria ON cursos(categoria);
CREATE INDEX idx_curso_activo ON cursos(activo);

-- Crear tabla de usuarios
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo_electronico VARCHAR(150) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    perfil_id BIGINT NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (perfil_id) REFERENCES perfiles(id)
);

-- Crear índices para usuarios
CREATE INDEX idx_usuario_email ON usuarios(correo_electronico);
CREATE INDEX idx_usuario_activo ON usuarios(activo);
CREATE INDEX idx_usuario_perfil ON usuarios(perfil_id);

-- Crear tabla de tópicos
CREATE TABLE topicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    mensaje CLOB NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'ABIERTO',
    autor_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

-- Crear índices para tópicos
CREATE INDEX idx_topico_fecha_creacion ON topicos(fecha_creacion DESC);
CREATE INDEX idx_topico_status ON topicos(status);
CREATE INDEX idx_topico_autor ON topicos(autor_id);
CREATE INDEX idx_topico_curso ON topicos(curso_id);
CREATE INDEX idx_topico_titulo ON topicos(titulo);

-- Crear tabla de respuestas
CREATE TABLE respuestas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mensaje CLOB NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    topico_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL,
    solucion BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (topico_id) REFERENCES topicos(id) ON DELETE CASCADE,
    FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);

-- Crear índices para respuestas
CREATE INDEX idx_respuesta_topico ON respuestas(topico_id);
CREATE INDEX idx_respuesta_autor ON respuestas(autor_id);
CREATE INDEX idx_respuesta_fecha_creacion ON respuestas(fecha_creacion DESC);
CREATE INDEX idx_respuesta_solucion ON respuestas(solucion);