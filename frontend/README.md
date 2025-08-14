# ForoHub Frontend

Frontend moderno y responsivo para ForoHub, construido con React, TypeScript y Material-UI.

## 🚀 Características

- **Diseño Moderno**: Interfaz limpia y atractiva con Material-UI
- **Responsive**: Optimizado para dispositivos móviles y desktop
- **Animaciones**: Transiciones suaves con Framer Motion
- **TypeScript**: Tipado estático para mejor desarrollo
- **Estado Global**: Gestión de estado con Zustand
- **Autenticación**: Sistema completo de login/registro
- **Tema Personalizado**: Colores y estilos consistentes
- **UX Optimizada**: Diseño centrado en la experiencia del usuario

## 🛠️ Tecnologías

- **React 18** - Biblioteca de UI
- **TypeScript** - Tipado estático
- **Material-UI v5** - Componentes de UI
- **Framer Motion** - Animaciones
- **React Router v6** - Navegación
- **React Query** - Gestión de estado del servidor
- **Zustand** - Gestión de estado global
- **Axios** - Cliente HTTP
- **React Hook Form** - Manejo de formularios
- **React Hot Toast** - Notificaciones

## 📦 Instalación

1. **Instalar dependencias**:
```bash
cd frontend
npm install
```

2. **Configurar variables de entorno**:
```bash
# Crear archivo .env.local
REACT_APP_API_URL=http://localhost:8080/api
```

3. **Iniciar el servidor de desarrollo**:
```bash
npm start
```

La aplicación estará disponible en `http://localhost:3000`

## 🏗️ Scripts Disponibles

- `npm start` - Inicia el servidor de desarrollo
- `npm build` - Construye la aplicación para producción
- `npm test` - Ejecuta los tests
- `npm run eject` - Expone la configuración de webpack

## 📁 Estructura del Proyecto

```
src/
├── components/          # Componentes reutilizables
│   ├── Auth/           # Componentes de autenticación
│   └── Layout/         # Componentes de layout
├── pages/              # Páginas de la aplicación
├── services/           # Servicios de API
├── store/              # Estado global (Zustand)
├── theme/              # Configuración del tema
├── types/              # Tipos de TypeScript
└── utils/              # Utilidades
```

## 🎨 Diseño y UX

### Paleta de Colores
- **Primario**: Azul (#1976d2) - Confianza y profesionalismo
- **Secundario**: Púrpura (#9c27b0) - Creatividad e innovación
- **Éxito**: Verde (#4caf50) - Logros y confirmaciones
- **Advertencia**: Naranja (#ff9800) - Atención y precaución
- **Error**: Rojo (#f44336) - Errores y alertas

### Principios de Diseño
1. **Claridad**: Información clara y fácil de entender
2. **Consistencia**: Patrones de diseño uniformes
3. **Accesibilidad**: Diseño inclusivo para todos los usuarios
4. **Feedback**: Respuestas visuales a las acciones del usuario
5. **Eficiencia**: Flujos de trabajo optimizados

### Animaciones
- **Micro-interacciones**: Hover effects y transiciones suaves
- **Page transitions**: Animaciones entre páginas
- **Loading states**: Indicadores de carga atractivos
- **Scroll animations**: Elementos que aparecen al hacer scroll

## 🔐 Autenticación

El sistema de autenticación incluye:
- Registro de nuevos usuarios
- Login con email y contraseña
- Gestión de tokens JWT
- Rutas protegidas
- Persistencia de sesión

## 📱 Responsive Design

La aplicación está optimizada para:
- **Mobile**: 320px - 768px
- **Tablet**: 768px - 1024px
- **Desktop**: 1024px+

## 🚀 Funcionalidades

### Páginas Principales
- **Home**: Página de inicio con información de la plataforma
- **Tópicos**: Lista de todos los tópicos con búsqueda y filtros
- **Detalle de Tópico**: Vista completa del tópico con respuestas
- **Crear Tópico**: Formulario para crear nuevos tópicos
- **Mis Tópicos**: Gestión de tópicos del usuario
- **Perfil**: Información y estadísticas del usuario

### Características UX
- **Búsqueda en tiempo real**: Filtros dinámicos
- **Paginación**: Navegación eficiente de contenido
- **Estados de carga**: Feedback visual durante operaciones
- **Notificaciones**: Mensajes de éxito y error
- **Navegación intuitiva**: Menús claros y accesibles

## 🎯 Próximas Mejoras

- [ ] Modo oscuro
- [ ] Notificaciones push
- [ ] Chat en tiempo real
- [ ] Editor de código integrado
- [ ] Sistema de reputación visual
- [ ] Gamificación avanzada
- [ ] PWA (Progressive Web App)
- [ ] Internacionalización (i18n)

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](../LICENSE) para detalles.

---

Desarrollado con ❤️ para la comunidad de Alura