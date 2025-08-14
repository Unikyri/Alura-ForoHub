import api from './api';

export interface Curso {
  id: number;
  nombre: string;
  categoria: string;
  descripcion: string;
}

export const cursoService = {
  async getCursos(): Promise<Curso[]> {
    const response = await api.get('/cursos');
    return response.data;
  },

  async getCursosPorCategoria(categoria: string): Promise<Curso[]> {
    const response = await api.get(`/cursos/categoria/${categoria}`);
    return response.data;
  },

  async buscarCursos(query: string): Promise<Curso[]> {
    const response = await api.get(`/cursos/buscar?q=${encodeURIComponent(query)}`);
    return response.data;
  },

  async getCategorias(): Promise<string[]> {
    const response = await api.get('/cursos/categorias');
    return response.data;
  },
};