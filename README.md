# ForoHub API

ForoHub es una API REST para una plataforma de foro digital inspirada en las necesidades de los estudiantes de Alura. La API permite a los usuarios crear, leer, actualizar y eliminar tópicos, así como interactuar con respuestas, usuarios y cursos.

## Características

- 🔐 Autenticación segura con JWT
- 📝 Gestión completa de tópicos y respuestas
- 👥 Sistema de usuarios y perfiles
- 🏷️ Categorización por cursos
- 🔒 Seguridad OWASP ASVS Level 1
- 🐳 Containerizado con Docker
- 📊 Documentación API con Swagger/OpenAPI

## Tecnologías

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** con JWT
- **Spring Data JPA**
- **MySQL 8.0**
- **Flyway** para migraciones
- **Docker & Docker Compose**
- **Maven**

## Requisitos Previos

- Java 17 o superior
- Docker y Docker Compose
- Maven 3.6+ (o usar el wrapper incluido)

## Instalación y Ejecución

### Opción 1: Con Docker (Recomendado)

1. Clonar el repositorio
2. Ejecutar con Docker Compose:

```bash
docker-compose up -d
```

La aplicación estará disponible en `http://localhost:8080/api`

### Opción 2: Desarrollo Local

1. Iniciar solo la base de datos:

```bash
docker-compose -f docker-compose.dev.yml up -d
```

2. Ejecutar la aplicación:

```bash
./mvnw spring-boot:run
```

### Opción 3: Compilación Manual

```bash
./mvnw clean package
java -jar target/foro-hub-*.jar
```

## Documentación API

Una vez ejecutada la aplicación, la documentación Swagger estará disponible en:
- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api/v3/api-docs

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/alura/forohub/
│   │   ├── controller/     # Controladores REST
│   │   ├── service/        # Lógica de negocio
│   │   ├── repository/     # Acceso a datos
│   │   ├── entity/         # Entidades JPA
│   │   ├── dto/           # Data Transfer Objects
│   │   ├── config/        # Configuraciones
│   │   ├── security/      # Configuración de seguridad
│   │   └── exception/     # Manejo de excepciones
│   └── resources/
│       ├── application.yml
│       └── db/migration/  # Scripts Flyway
└── test/                  # Tests unitarios e integración
```

## Testing

```bash
# Ejecutar todos los tests
./mvnw test

# Ejecutar tests con cobertura
./mvnw test jacoco:report
```

## Variables de Entorno

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `DB_USERNAME` | Usuario de base de datos | `forohub` |
| `DB_PASSWORD` | Contraseña de base de datos | `forohub123` |
| `JWT_SECRET` | Clave secreta para JWT | `mySecretKey...` |

## Estado del Proyecto

🚧 **En Desarrollo** - Implementando funcionalidades según especificación

### Tareas Completadas
- [x] **Tarea 1**: Configuración inicial del proyecto ✅
  - Proyecto Spring Boot 3.2.0 con Java 17
  - Maven configurado con todas las dependencias
  - Docker y Docker Compose configurados
  - Estructura de paquetes por capas
  - Perfiles de configuración (dev, test, docker)
  - Tests básicos funcionando
  - Aplicación ejecutándose correctamente

- [x] **Tarea 2**: Modelos de entidades y base de datos ✅
  - Entidades JPA (Usuario, Perfil, Curso, Topico, Respuesta) con anotaciones y relaciones
  - Enums (StatusTopico, TipoPerfil) para type safety
  - Scripts Flyway para creación de schema con índices apropiados
  - Tests unitarios para validación de entidades y mapeo de relaciones

- [x] **Tarea 3**: DTOs y validación ✅
  - DTOs de autenticación (RegistroUsuarioDTO, LoginDTO, TokenDTO) con validaciones
  - DTOs de gestión de tópicos (CrearTopicoDTO, TopicoResponseDTO, DetalleTopicoDTO, ActualizarTopicoDTO)
  - DTOs de respuestas (CrearRespuestaDTO, RespuestaDTO, ActualizarRespuestaDTO)
  - DTOs de error (ErrorResponse) para manejo consistente de errores
  - Tests unitarios para validación de reglas de DTOs

- [x] **Tarea 4**: Capa de repositorio ✅
  - Interfaces de repositorio (UsuarioRepository, TopicoRepository, RespuestaRepository, CursoRepository, PerfilRepository)
  - Métodos de consulta personalizados con JOIN FETCH para optimización
  - Tests de repositorio usando @DataJpaTest con datos de prueba
  - Configuración de pool de conexiones y configuraciones JPA

- [x] **Tarea 5**: Configuración de seguridad JWT ✅
  - Configuración Spring Security con filtro de autenticación JWT
  - Utilidad JWT para generación, validación y parsing de tokens
  - Configuración de seguridad con reglas de acceso a endpoints y CORS
  - Configuración de codificación de contraseñas usando BCrypt
  - Tests unitarios para operaciones JWT y configuración de seguridad

- [x] **Tarea 6**: Servicio de autenticación y endpoints ✅
  - AuthenticationService con lógica de registro y login de usuarios
  - Registro de usuarios con validación de unicidad de email y hash de contraseñas
  - Funcionalidad de login con validación de credenciales y generación de tokens JWT
  - AuthController con endpoints /auth/register, /auth/login y /auth/validate
  - Tests de integración para flujos de autenticación incluyendo escenarios de error

- [x] **Tarea 7**: Servicio de gestión de tópicos ✅
  - TopicoService con lógica de negocio para operaciones CRUD y verificaciones de autorización
  - Creación de tópicos con validación de curso y asignación de autor
  - Listado de tópicos con paginación y ordenamiento por fecha de creación
  - Obtención de detalles de tópico con carga lazy de respuestas
  - Actualización y eliminación de tópicos con validación de propiedad
  - Tests unitarios completos para la lógica de negocio del servicio

- [x] **Tarea 8**: Endpoints REST de tópicos ✅
  - TopicoController con todos los endpoints CRUD (/topicos)
  - GET /topicos con paginación y parámetros de ordenamiento
  - POST /topicos con autenticación y validación
  - GET /topicos/{id} para detalles de tópico
  - PUT /topicos/{id} y DELETE /topicos/{id} con verificaciones de autorización
  - Endpoints adicionales: /mis-topicos, /buscar, /curso/{cursoId}
  - Tests de integración para todos los endpoints incluyendo escenarios de seguridad

- [x] **Tarea 11**: Manejo global de excepciones ✅
  - GlobalExceptionHandler con @RestControllerAdvice para manejo centralizado de errores
  - Manejadores para errores de validación, autenticación y autorización
  - Manejadores para entidades no encontradas, violaciones de integridad de datos y excepciones generales
  - Formato consistente de respuesta de error sin exposición de información sensible
  - Configuración de logging para eventos de seguridad y errores sin exposición de PII

- [x] **Tarea 12**: Gestión de cursos y configuración inicial ✅
  - CursoService para operaciones de gestión de cursos
  - Endpoints básicos de cursos para listar cursos disponibles
  - Scripts Flyway de migración para datos iniciales (cursos, perfiles por defecto)
  - Configuración OpenAPI/Swagger para documentación de la API

- [x] **Tarea 9**: Funcionalidad de gestión de respuestas ✅
  - RespuestaService con lógica de negocio para operaciones CRUD de respuestas
  - Creación de respuestas con validación de tópico y asignación de autor
  - Listado de respuestas por tópico con ordenamiento por fecha de creación
  - Actualización y eliminación de respuestas con validación de propiedad
  - Funcionalidad de marcar/desmarcar respuestas como solución restringida a autores de tópicos
  - Tests unitarios completos para la lógica de negocio del servicio

- [x] **Tarea 10**: Endpoints REST de respuestas ✅
  - RespuestaController con todos los endpoints CRUD para respuestas
  - POST /respuestas/topico/{id} para crear respuestas con autenticación
  - GET /respuestas/topico/{id} para listar respuestas de un tópico
  - PUT /respuestas/{id} y DELETE /respuestas/{id} con verificaciones de autorización
  - PATCH /respuestas/{id}/solucion para marcar/desmarcar soluciones
  - Endpoint /mis-respuestas para listar respuestas del usuario autenticado
  - Tests de integración para todos los endpoints de respuestas

### Estado del Proyecto: ✅ **COMPLETADO**

🎉 **¡Implementación Exitosa!** La API ForoHub está completamente funcional con todas las características especificadas:

#### 🔧 **Funcionalidades Implementadas:**
- ✅ **Autenticación JWT completa** con registro y login
- ✅ **Gestión completa de tópicos** (CRUD con autorización)
- ✅ **Sistema de respuestas** con marcado de soluciones
- ✅ **Gestión de cursos** y categorización
- ✅ **Manejo global de excepciones** con respuestas consistentes
- ✅ **Documentación API** con Swagger/OpenAPI
- ✅ **Testing integral** (unitario, integración, repositorio)
- ✅ **Seguridad OWASP ASVS Level 1** implementada

#### 🚀 **Características Técnicas:**
- **Spring Boot 3.2.0** con Java 17
- **Spring Security** con JWT
- **Spring Data JPA** con MySQL
- **Flyway** para migraciones de BD
- **Docker** y Docker Compose
- **Testing** con H2 in-memory
- **Validación** Bean Validation
- **Documentación** OpenAPI 3.0

## Contribución

1. Fork del proyecto
2. Crear rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.