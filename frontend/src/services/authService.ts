import api from './api';

export interface LoginRequest {
  correoElectronico: string;
  contrasena: string;
}

export interface RegisterRequest {
  nombre: string;
  correoElectronico: string;
  contrasena: string;
}

export interface AuthResponse {
  token: string;
  tipo: string;
  expiracion: number;
}

export const authService = {
  async login(credentials: LoginRequest): Promise<AuthResponse> {
    const response = await api.post('/auth/login', credentials);
    return response.data;
  },

  async register(userData: RegisterRequest): Promise<AuthResponse> {
    const response = await api.post('/auth/register', userData);
    return response.data;
  },

  async validateToken(): Promise<boolean> {
    try {
      await api.post('/auth/validate');
      return true;
    } catch {
      return false;
    }
  },

  async getCurrentUser() {
    // En una implementación real, esto vendría del backend
    // Por ahora, decodificamos la información del token o la obtenemos del store
    const token = localStorage.getItem('forohub-auth');
    if (token) {
      const parsed = JSON.parse(token);
      return parsed.state?.user || null;
    }
    return null;
  },
};