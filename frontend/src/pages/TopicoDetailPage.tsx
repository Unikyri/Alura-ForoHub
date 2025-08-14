import React from 'react';
import {
  Box,
  Container,
  Typography,
  Card,
  CardContent,
  Chip,
  Avatar,
  Button,
  Divider,
  Alert,
  CircularProgress,
} from '@mui/material';
import {
  AccessTime,
  Edit,
  Delete,
  CheckCircle,
  QuestionAnswer,
  ArrowBack,
} from '@mui/icons-material';
import { useParams, Link as RouterLink, useNavigate } from 'react-router-dom';
import { motion } from 'framer-motion';
import { useQuery } from 'react-query';
import toast from 'react-hot-toast';

import { topicoService } from '../services/topicoService';
import { useAuthStore } from '../store/authStore';

const TopicoDetailPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { user, isAuthenticated } = useAuthStore();

  const { data: topico, isLoading, error } = useQuery(
    ['topico', id],
    () => topicoService.getTopicoById(Number(id)),
    {
      enabled: !!id,
    }
  );

  const handleDelete = async () => {
    if (!topico || !window.confirm('¿Estás seguro de que quieres eliminar este tópico?')) {
      return;
    }

    try {
      await topicoService.eliminarTopico(topico.id);
      toast.success('Tópico eliminado exitosamente');
      navigate('/topicos');
    } catch (error) {
      toast.error('Error al eliminar el tópico');
    }
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('es-ES', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'RESUELTO':
        return 'success';
      case 'CERRADO':
        return 'default';
      default:
        return 'primary';
    }
  };

  if (isLoading) {
    return (
      <Container maxWidth="lg">
        <Box sx={{ display: 'flex', justifyContent: 'center', py: 8 }}>
          <CircularProgress size={48} />
        </Box>
      </Container>
    );
  }

  if (error || !topico) {
    return (
      <Container maxWidth="lg">
        <Alert severity="error" sx={{ mt: 4 }}>
          Error al cargar el tópico. Por favor, inténtalo de nuevo.
        </Alert>
      </Container>
    );
  }

  const isAuthor = isAuthenticated && user?.id === topico.autor.id;

  return (
    <Container maxWidth="lg">
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

      {/* Topic Header */}
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6 }}
      >
        <Card sx={{ mb: 4, borderRadius: 3 }}>
          <CardContent sx={{ p: 4 }}>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', mb: 3 }}>
              <Box sx={{ flex: 1, mr: 2 }}>
                <Box sx={{ display: 'flex', alignItems: 'center', gap: 2, mb: 2 }}>
                  <Chip
                    label={topico.status}
                    color={getStatusColor(topico.status) as any}
                    icon={topico.status === 'RESUELTO' ? <CheckCircle /> : <QuestionAnswer />}
                  />
                  <Chip
                    label={topico.curso.categoria}
                    variant="outlined"
                    size="small"
                  />
                </Box>
                <Typography
                  variant="h4"
                  sx={{ fontWeight: 'bold', mb: 2, lineHeight: 1.3 }}
                >
                  {topico.titulo}
                </Typography>
                <Box sx={{ display: 'flex', alignItems: 'center', gap: 3, mb: 3 }}>
                  <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                    <Avatar sx={{ width: 32, height: 32 }}>
                      {topico.autor.nombre.charAt(0).toUpperCase()}
                    </Avatar>
                    <Box>
                      <Typography variant="subtitle2" sx={{ fontWeight: 600 }}>
                        {topico.autor.nombre}
                      </Typography>
                      <Typography variant="caption" color="text.secondary">
                        {topico.autor.perfil.nombre}
                      </Typography>
                    </Box>
                  </Box>
                  <Box sx={{ display: 'flex', alignItems: 'center', gap: 0.5 }}>
                    <AccessTime sx={{ fontSize: 16, color: 'text.secondary' }} />
                    <Typography variant="body2" color="text.secondary">
                      {formatDate(topico.fechaCreacion)}
                    </Typography>
                  </Box>
                  <Chip
                    label={topico.curso.nombre}
                    size="small"
                    sx={{
                      bgcolor: 'primary.50',
                      color: 'primary.main',
                      fontWeight: 500,
                    }}
                  />
                </Box>
              </Box>
              {isAuthor && (
                <Box sx={{ display: 'flex', gap: 1 }}>
                  <Button
                    variant="outlined"
                    startIcon={<Edit />}
                    size="small"
                  >
                    Editar
                  </Button>
                  <Button
                    variant="outlined"
                    color="error"
                    startIcon={<Delete />}
                    size="small"
                    onClick={handleDelete}
                  >
                    Eliminar
                  </Button>
                </Box>
              )}
            </Box>
            
            <Divider sx={{ mb: 3 }} />
            
            <Typography
              variant="body1"
              sx={{
                lineHeight: 1.7,
                whiteSpace: 'pre-wrap',
                color: 'text.primary',
              }}
            >
              {topico.mensaje}
            </Typography>
          </CardContent>
        </Card>
      </motion.div>

      {/* Responses Section */}
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6, delay: 0.2 }}
      >
        <Box sx={{ mb: 3 }}>
          <Typography variant="h5" sx={{ fontWeight: 'bold', mb: 2 }}>
            Respuestas ({topico.respuestas.length})
          </Typography>
        </Box>

        {topico.respuestas.length === 0 ? (
          <Card sx={{ borderRadius: 3 }}>
            <CardContent sx={{ p: 4, textAlign: 'center' }}>
              <Box sx={{ fontSize: '3rem', mb: 2 }}>💬</Box>
              <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 1 }}>
                Aún no hay respuestas
              </Typography>
              <Typography variant="body2" color="text.secondary" sx={{ mb: 3 }}>
                Sé el primero en responder a este tópico
              </Typography>
              {isAuthenticated && (
                <Button variant="contained" size="large">
                  Escribir Respuesta
                </Button>
              )}
            </CardContent>
          </Card>
        ) : (
          <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
            {topico.respuestas.map((respuesta, index) => (
              <motion.div
                key={respuesta.id}
                initial={{ opacity: 0, x: -20 }}
                animate={{ opacity: 1, x: 0 }}
                transition={{ duration: 0.5, delay: index * 0.1 }}
              >
                <Card
                  sx={{
                    borderRadius: 3,
                    border: respuesta.solucion ? '2px solid' : '1px solid',
                    borderColor: respuesta.solucion ? 'success.main' : 'divider',
                    bgcolor: respuesta.solucion ? 'success.50' : 'background.paper',
                  }}
                >
                  <CardContent sx={{ p: 3 }}>
                    <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', mb: 2 }}>
                      <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
                        <Avatar sx={{ width: 32, height: 32 }}>
                          {respuesta.autor.nombre.charAt(0).toUpperCase()}
                        </Avatar>
                        <Box>
                          <Typography variant="subtitle2" sx={{ fontWeight: 600 }}>
                            {respuesta.autor.nombre}
                          </Typography>
                          <Typography variant="caption" color="text.secondary">
                            {formatDate(respuesta.fechaCreacion)}
                          </Typography>
                        </Box>
                        {respuesta.solucion && (
                          <Chip
                            label="Solución"
                            color="success"
                            size="small"
                            icon={<CheckCircle />}
                          />
                        )}
                      </Box>
                      {isAuthor && !respuesta.solucion && (
                        <Button
                          variant="outlined"
                          color="success"
                          size="small"
                          startIcon={<CheckCircle />}
                        >
                          Marcar como Solución
                        </Button>
                      )}
                    </Box>
                    <Typography
                      variant="body1"
                      sx={{
                        lineHeight: 1.7,
                        whiteSpace: 'pre-wrap',
                        color: 'text.primary',
                      }}
                    >
                      {respuesta.mensaje}
                    </Typography>
                  </CardContent>
                </Card>
              </motion.div>
            ))}
          </Box>
        )}
      </motion.div>

      {/* Add Response Section */}
      {isAuthenticated && topico.status === 'ABIERTO' && (
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.6, delay: 0.4 }}
        >
          <Card sx={{ mt: 4, borderRadius: 3 }}>
            <CardContent sx={{ p: 4 }}>
              <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 3 }}>
                Agregar Respuesta
              </Typography>
              <Alert severity="info" sx={{ mb: 3 }}>
                Esta funcionalidad estará disponible próximamente. Por ahora puedes explorar los tópicos existentes.
              </Alert>
            </CardContent>
          </Card>
        </motion.div>
      )}
    </Container>
  );
};

export default TopicoDetailPage;