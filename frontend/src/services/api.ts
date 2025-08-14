import axios from 'axios';
import toast from 'react-hot-toast';
import { useAuthStore } from '../store/authStore';

// Configuración base de Axios
const api = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor para agregar token de autorización
api.interceptors.request.use(
  (config) => {
    const token = useAuthStore.getState().token;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Interceptor para manejo de respuestas y errores
api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    const { response } = error;
    
    if (response?.status === 401) {
      // Token expirado o inválido
      useAuthStore.getState().logout();
      toast.error('Sesión expirada. Por favor, inicia sesión nuevamente.');
      window.location.href = '/login';
    } else if (response?.status === 403) {
      toast.error('No tienes permisos para realizar esta acción.');
    } else if (response?.status === 404) {
      toast.error('Recurso no encontrado.');
    } else if (response?.status >= 500) {
      toast.error('Error del servidor. Por favor, inténtalo más tarde.');
    } else if (response?.data?.mensaje) {
      toast.error(response.data.mensaje);
    } else {
      toast.error('Ha ocurrido un error inesperado.');
    }
    
    return Promise.reject(error);
  }
);

export default api;