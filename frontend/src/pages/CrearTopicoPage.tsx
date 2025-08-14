import React, { useState } from 'react';
import {
  Box,
  Container,
  Typography,
  Card,
  CardContent,
  TextField,
  Button,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Alert,
  CircularProgress,
} from '@mui/material';
import { ArrowBack, Save } from '@mui/icons-material';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import { useForm, Controller } from 'react-hook-form';
import { motion } from 'framer-motion';
import { useQuery, useMutation } from 'react-query';
import toast from 'react-hot-toast';

import { topicoService, CrearTopicoRequest } from '../services/topicoService';
import { cursoService } from '../services/cursoService';

const CrearTopicoPage: React.FC = () => {
  const navigate = useNavigate();
  const [isSubmitting, setIsSubmitting] = useState(false);

  const {
    control,
    register,
    handleSubmit,
    formState: { errors },
    watch,
  } = useForm<CrearTopicoRequest>();

  const { data: cursos, isLoading: cursosLoading } = useQuery('cursos', cursoService.getCursos);

  const createTopicoMutation = useMutation(topicoService.crearTopico, {
    onSuccess: (data) => {
      toast.success('¡Tópico creado exitosamente!');
      navigate(`/topicos/${data.id}`);
    },
    onError: (error: any) => {
      toast.error(error.response?.data?.mensaje || 'Error al crear el tópico');
    },
  });

  const onSubmit = async (data: CrearTopicoRequest) => {
    setIsSubmitting(true);
    try {
      await createTopicoMutation.mutateAsync(data);
    } finally {
      setIsSubmitting(false);
    }
  };

  const titulo = watch('titulo', '');
  const mensaje = watch('mensaje', '');

  return (
    <Container maxWidth="md">
      {/* Back Button */}
      <motion.div
        initial={{ opacity: 0, x: -20 }}
        animate={{ opacity: 1, x: 0 }}
        transition={{ duration: 0.5 }}
      >
        <Button
          component={RouterLink}
          to="/topicos"
          startIcon={<ArrowBack />}
          sx={{ mb: 3 }}
        >
          Volver a Tópicos
        </Button>
      </motion.div>

      {/* Header */}
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6 }}
      >
        <Box sx={{ mb: 4, textAlign: 'center' }}>
          <Typography
            variant="h3"
            sx={{ fontWeight: 'bold', mb: 2, color: 'text.primary' }}
          >
            Crear Nuevo Tópico
          </Typography>
          <Typography variant="h6" color="text.secondary">
            Comparte tu pregunta o duda con la comunidad
          </Typography>
        </Box>
      </motion.div>

      {/* Form */}
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6, delay: 0.2 }}
      >
        <Card sx={{ borderRadius: 3, boxShadow: 3 }}>
          <CardContent sx={{ p: 4 }}>
            <Box component="form" onSubmit={handleSubmit(onSubmit)}>
              {/* Título */}
              <TextField
                fullWidth
                label="Título del Tópico"
                placeholder="Escribe un título claro y descriptivo..."
                margin="normal"
                {...register('titulo', {
                  required: 'El título es obligatorio',
                  minLength: {
                    value: 10,
                    message: 'El título debe tener al menos 10 caracteres',
                  },
                  maxLength: {
                    value: 200,
                    message: 'El título no puede exceder 200 caracteres',
                  },
                })}
                error={!!errors.titulo}
                helperText={
                  errors.titulo?.message || 
                  `${titulo.length}/200 caracteres`
                }
                sx={{ mb: 3 }}
              />

              {/* Curso */}
              <FormControl fullWidth margin="normal" sx={{ mb: 3 }}>
                <InputLabel>Curso Relacionado</InputLabel>
                <Controller
                  name="cursoId"
                  control={control}
                  rules={{ required: 'Selecciona un curso' }}
                  render={({ field }) => (
                    <Select
                      {...field}
                      label="Curso Relacionado"
                      error={!!errors.cursoId}
                      disabled={cursosLoading}
                    >
                      {cursos?.map((curso) => (
                        <MenuItem key={curso.id} value={curso.id}>
                          <Box>
                            <Typography variant="body1">{curso.nombre}</Typography>
                            <Typography variant="caption" color="text.secondary">
                              {curso.categoria}
                            </Typography>
                          </Box>
                        </MenuItem>
                      ))}
                    </Select>
                  )}
                />
                {errors.cursoId && (
                  <Typography variant="caption" color="error" sx={{ mt: 0.5, ml: 2 }}>
                    {errors.cursoId.message}
                  </Typography>
                )}
              </FormControl>

              {/* Mensaje */}
              <TextField
                fullWidth
                label="Descripción del Problema"
                placeholder="Describe tu duda o problema en detalle. Incluye código si es necesario, pasos que has intentado, y cualquier información relevante..."
                multiline
                rows={8}
                margin="normal"
                {...register('mensaje', {
                  required: 'La descripción es obligatoria',
                  minLength: {
                    value: 20,
                    message: 'La descripción debe tener al menos 20 caracteres',
                  },
                  maxLength: {
                    value: 2000,
                    message: 'La descripción no puede exceder 2000 caracteres',
                  },
                })}
                error={!!errors.mensaje}
                helperText={
                  errors.mensaje?.message || 
                  `${mensaje.length}/2000 caracteres`
                }
                sx={{ mb: 3 }}
              />

              {/* Tips */}
              <Alert severity="info" sx={{ mb: 4 }}>
                <Typography variant="subtitle2" sx={{ fontWeight: 'bold', mb: 1 }}>
                  💡 Tips para un buen tópico:
                </Typography>
                <Box component="ul" sx={{ m: 0, pl: 2 }}>
                  <li>Usa un título claro y específico</li>
                  <li>Describe el problema paso a paso</li>
                  <li>Incluye código relevante si aplica</li>
                  <li>Menciona qué has intentado hacer</li>
                  <li>Especifica el error exacto que recibes</li>
                </Box>
              </Alert>

              {/* Submit Button */}
              <Box sx={{ display: 'flex', gap: 2, justifyContent: 'flex-end' }}>
                <Button
                  component={RouterLink}
                  to="/topicos"
                  variant="outlined"
                  size="large"
                  disabled={isSubmitting}
                >
                  Cancelar
                </Button>
                <Button
                  type="submit"
                  variant="contained"
                  size="large"
                  startIcon={isSubmitting ? <CircularProgress size={20} /> : <Save />}
                  disabled={isSubmitting}
                  sx={{ px: 4 }}
                >
                  {isSubmitting ? 'Creando...' : 'Crear Tópico'}
                </Button>
              </Box>
            </Box>
          </CardContent>
        </Card>
      </motion.div>

      {/* Preview Section */}
      {(titulo || mensaje) && (
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.6, delay: 0.4 }}
        >
          <Card sx={{ mt: 4, borderRadius: 3, border: '1px solid', borderColor: 'divider' }}>
            <CardContent sx={{ p: 4 }}>
              <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 3 }}>
                Vista Previa
              </Typography>
              {titulo && (
                <Typography
                  variant="h5"
                  sx={{ fontWeight: 'bold', mb: 2, color: 'text.primary' }}
                >
                  {titulo}
                </Typography>
              )}
              {mensaje && (
                <Typography
                  variant="body1"
                  sx={{
                    lineHeight: 1.7,
                    whiteSpace: 'pre-wrap',
                    color: 'text.secondary',
                  }}
                >
                  {mensaje}
                </Typography>
              )}
            </CardContent>
          </Card>
        </motion.div>
      )}
    </Container>
  );
};

export default CrearTopicoPage;