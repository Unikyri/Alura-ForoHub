import api from './api';

export interface Topico {
  id: number;
  titulo: string;
  mensaje: string;
  fechaCreacion: string;
  fechaActualizacion?: string;
  status: 'ABIERTO' | 'CERRADO' | 'RESUELTO';
  autorNombre: string;
  cursoNombre: string;
  totalRespuestas: number;
}

export interface DetalleTopico {
  id: number;
  titulo: string;
  mensaje: string;
  fechaCreacion: string;
  fechaActualizacion: string;
  status: 'ABIERTO' | 'CERRADO' | 'RESUELTO';
  autor: {
    id: number;
    nombre: string;
    correoElectronico: string;
    fechaCreacion: string;
    perfil: {
      id: number;
      nombre: string;
      tipo: string;
      descripcion: string;
    };
  };
  curso: {
    id: number;
    nombre: string;
    categoria: string;
    descripcion: string;
  };
  respuestas: Array<{
    id: number;
    mensaje: string;
    fechaCreacion: string;
    fechaActualizacion: string;
    autor: {
      id: number;
      nombre: string;
      correoElectronico: string;
      fechaCreacion: string;
      perfil: {
        id: number;
        nombre: string;
        tipo: string;
        descripcion: string;
      };
    };
    solucion: boolean;
  }>;
}

export interface CrearTopicoRequest {
  titulo: string;
  mensaje: string;
  cursoId: number;
}

export interface ActualizarTopicoRequest {
  titulo?: string;
  mensaje?: string;
  cursoId?: number;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
}

export const topicoService = {
  async getTopicos(page = 0, size = 10): Promise<PaginatedResponse<Topico>> {
    const response = await api.get(`/topicos?page=${page}&size=${size}`);
    return response.data;
  },

  async getTopicoById(id: number): Promise<DetalleTopico> {
    const response = await api.get(`/topicos/${id}`);
    return response.data;
  },

  async crearTopico(data: CrearTopicoRequest): Promise<Topico> {
    const response = await api.post('/topicos', data);
    return response.data;
  },

  async actualizarTopico(id: number, data: ActualizarTopicoRequest): Promise<Topico> {
    const response = await api.put(`/topicos/${id}`, data);
    return response.data;
  },

  async eliminarTopico(id: number): Promise<void> {
    await api.delete(`/topicos/${id}`);
  },

  async getMisTopicos(page = 0, size = 10): Promise<PaginatedResponse<Topico>> {
    const response = await api.get(`/topicos/mis-topicos?page=${page}&size=${size}`);
    return response.data;
  },

  async buscarTopicos(query: string, page = 0, size = 10): Promise<PaginatedResponse<Topico>> {
    const response = await api.get(`/topicos/buscar?q=${encodeURIComponent(query)}&page=${page}&size=${size}`);
    return response.data;
  },

  async getTopicosPorCurso(cursoId: number, page = 0, size = 10): Promise<PaginatedResponse<Topico>> {
    const response = await api.get(`/topicos/curso/${cursoId}?page=${page}&size=${size}`);
    return response.data;
  },
};