-- Insertar perfiles por defecto
INSERT INTO perfiles (nombre, tipo, descripcion) VALUES
('Usuario', 'USUARIO', 'Perfil básico de usuario del foro'),
('Moderador', 'MODERADOR', 'Perfil con permisos de moderación'),
('Administrador', 'ADMINISTRADOR', 'Perfil con permisos administrativos completos');

-- Insertar cursos por defecto
INSERT INTO cursos (nombre, categoria, descripcion) VALUES
('Java Orientado a Objetos', 'Programación', 'Curso completo de Java con enfoque en programación orientada a objetos'),
('Spring Framework', 'Programación', 'Desarrollo de aplicaciones web con Spring Boot y Spring MVC'),
('JavaScript para Web', 'Frontend', 'Desarrollo frontend moderno con JavaScript, HTML y CSS'),
('React: Desarrollando con JavaScript', 'Frontend', 'Creación de interfaces de usuario con React'),
('Python para Data Science', 'Data Science', 'Análisis de datos y machine learning con Python'),
('SQL con MySQL', 'Base de Datos', 'Gestión de bases de datos relacionales con MySQL'),
('Git y GitHub', 'DevOps', 'Control de versiones y colaboración en proyectos de software'),
('Docker: Creando containers', 'DevOps', 'Containerización de aplicaciones con Docker'),
('Lógica de Programación', 'Fundamentos', 'Conceptos básicos de programación y algoritmos'),
('HTML y CSS', 'Frontend', 'Fundamentos del desarrollo web con HTML5 y CSS3');

-- Insertar usuario administrador por defecto
-- Contraseña: admin123 (debe ser hasheada en producción)
INSERT INTO usuarios (nombre, correo_electronico, contrasena, perfil_id) VALUES
('Administrador', 'admin@forohub.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFe5rtGcEKtUWHvjXNfkE.i', 3);