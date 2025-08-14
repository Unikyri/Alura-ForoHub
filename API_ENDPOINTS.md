# ForoHub API - Endpoints Disponibles

## 🔐 Autenticación

### POST /api/auth/register
Registra un nuevo usuario en el sistema.
```json
{
  "nombre": "Juan Pérez",
  "correoElectronico": "juan@example.com",
  "contrasena": "password123"
}
```

### POST /api/auth/login
Autentica un usuario y retorna un token JWT.
```json
{
  "correoElectronico": "juan@example.com",
  "contrasena": "password123"
}
```

### POST /api/auth/validate
Valida un token JWT (requiere header Authorization: Bearer {token}).

---

## 📝 Tópicos

### GET /api/topicos
Lista todos los tópicos con paginación (público).
- Parámetros: `page`, `size`, `sort`

### GET /api/topicos/{id}
Obtiene los detalles completos de un tópico (público).

### POST /api/topicos
Crea un nuevo tópico (requiere autenticación).
```json
{
  "titulo": "¿Cómo usar Spring Boot?",
  "mensaje": "Necesito ayuda con Spring Boot",
  "cursoId": 1
}
```

### PUT /api/topicos/{id}
Actualiza un tópico existente (solo el autor).
```json
{
  "titulo": "Nuevo título",
  "mensaje": "Nuevo mensaje",
  "cursoId": 2
}
```

### DELETE /api/topicos/{id}
Elimina un tópico (solo el autor).

### GET /api/topicos/mis-topicos
Lista los tópicos del usuario autenticado.

### GET /api/topicos/buscar?q={término}
Busca tópicos por título.

### GET /api/topicos/curso/{cursoId}
Lista tópicos de un curso específico.

---

## 💬 Respuestas

### POST /api/respuestas/topico/{topicoId}
Crea una nueva respuesta para un tópico (requiere autenticación).
```json
{
  "mensaje": "Esta es mi respuesta al tópico"
}
```

### GET /api/respuestas/topico/{topicoId}
Lista todas las respuestas de un tópico (público).

### GET /api/respuestas/topico/{topicoId}/paginado
Lista respuestas con paginación.

### PUT /api/respuestas/{id}
Actualiza una respuesta (solo el autor).
```json
{
  "mensaje": "Mensaje actualizado"
}
```

### DELETE /api/respuestas/{id}
Elimina una respuesta (solo el autor).

### PATCH /api/respuestas/{id}/solucion
Marca una respuesta como solución (solo el autor del tópico).

### DELETE /api/respuestas/{id}/solucion
Desmarca una respuesta como solución (solo el autor del tópico).

### GET /api/respuestas/mis-respuestas
Lista las respuestas del usuario autenticado.

---

## 📚 Cursos

### GET /api/cursos
Lista todos los cursos activos.

### GET /api/cursos/paginado
Lista cursos con paginación.

### GET /api/cursos/categoria/{categoria}
Lista cursos de una categoría específica.

### GET /api/cursos/buscar?q={término}
Busca cursos por nombre.

### GET /api/cursos/categorias
Lista todas las categorías disponibles.

---

## 📊 Estadísticas

### GET /api/estadisticas
Obtiene estadísticas generales del foro (público).

---

## 📖 Documentación

### GET /api/swagger-ui.html
Interfaz de Swagger UI para explorar la API.

### GET /api/v3/api-docs
Especificación OpenAPI en formato JSON.

---

## 🔒 Autenticación

Para endpoints que requieren autenticación, incluir el header:
```
Authorization: Bearer {jwt_token}
```

## 📄 Formato de Respuestas de Error

```json
{
  "codigo": "ERROR_CODE",
  "mensaje": "Descripción del error",
  "timestamp": "2025-08-13T22:30:00",
  "detalles": {
    "campo": "mensaje de error específico"
  }
}
```

## 🚀 Códigos de Estado HTTP

- `200` - OK
- `201` - Created
- `204` - No Content
- `400` - Bad Request
- `401` - Unauthorized
- `403` - Forbidden
- `404` - Not Found
- `409` - Conflict
- `500` - Internal Server Error