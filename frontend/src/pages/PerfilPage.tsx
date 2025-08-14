import React from 'react';
import {
  Box,
  Container,
  Typography,
  Card,
  CardContent,
  Avatar,
  Chip,
  Grid,
  Divider,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Button,
} from '@mui/material';
import {
  Person,
  Email,
  CalendarToday,
  School,
  Forum,
  QuestionAnswer,
  CheckCircle,
  Edit,
} from '@mui/icons-material';
import { motion } from 'framer-motion';

import { useAuthStore } from '../store/authStore';

const PerfilPage: React.FC = () => {
  const { user } = useAuthStore();

  if (!user) {
    return null;
  }

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('es-ES', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
    });
  };

  const getPerfilColor = (tipo: string) => {
    switch (tipo) {
      case 'ADMINISTRADOR':
        return 'error';
      case 'MODERADOR':
        return 'warning';
      default:
        return 'primary';
    }
  };

  // Datos mock para estadísticas (en producción vendrían del backend)
  const stats = {
    topicosCreados: 12,
    respuestasEscritas: 45,
    solucionesAceptadas: 8,
    reputacion: 156,
  };

  const achievements = [
    { name: 'Primer Tópico', description: 'Creaste tu primer tópico', earned: true },
    { name: 'Colaborador', description: 'Escribiste 10 respuestas', earned: true },
    { name: 'Solucionador', description: 'Tuviste 5 respuestas marcadas como solución', earned: true },
    { name: 'Experto', description: 'Alcanzaste 100 puntos de reputación', earned: true },
    { name: 'Mentor', description: 'Ayudaste a 50 estudiantes', earned: false },
  ];

  return (
    <Container maxWidth="lg">
      {/* Header */}
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6 }}
      >
        <Box sx={{ mb: 4 }}>
          <Typography
            variant="h3"
            sx={{ fontWeight: 'bold', mb: 1, color: 'text.primary' }}
          >
            Mi Perfil
          </Typography>
          <Typography variant="h6" color="text.secondary">
            Gestiona tu información personal y revisa tu actividad
          </Typography>
        </Box>
      </motion.div>

      <Grid container spacing={4}>
        {/* Profile Info */}
        <Grid item xs={12} md={4}>
          <motion.div
            initial={{ opacity: 0, x: -20 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ duration: 0.6, delay: 0.1 }}
          >
            <Card sx={{ borderRadius: 3, mb: 3 }}>
              <CardContent sx={{ p: 4, textAlign: 'center' }}>
                <Avatar
                  sx={{
                    width: 100,
                    height: 100,
                    mx: 'auto',
                    mb: 2,
                    bgcolor: 'primary.main',
                    fontSize: '2.5rem',
                  }}
                >
                  {user.nombre.charAt(0).toUpperCase()}
                </Avatar>
                <Typography variant="h5" sx={{ fontWeight: 'bold', mb: 1 }}>
                  {user.nombre}
                </Typography>
                <Chip
                  label={user.perfil.nombre}
                  color={getPerfilColor(user.perfil.tipo) as any}
                  sx={{ mb: 2 }}
                />
                <Typography variant="body2" color="text.secondary" sx={{ mb: 3 }}>
                  {user.perfil.descripcion}
                </Typography>
                <Button
                  variant="outlined"
                  startIcon={<Edit />}
                  fullWidth
                  disabled
                >
                  Editar Perfil
                </Button>
              </CardContent>
            </Card>
          </motion.div>

          {/* Contact Info */}
          <motion.div
            initial={{ opacity: 0, x: -20 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ duration: 0.6, delay: 0.2 }}
          >
            <Card sx={{ borderRadius: 3 }}>
              <CardContent sx={{ p: 3 }}>
                <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 2 }}>
                  Información de Contacto
                </Typography>
                <List disablePadding>
                  <ListItem disablePadding sx={{ mb: 1 }}>
                    <ListItemIcon sx={{ minWidth: 40 }}>
                      <Email color="primary" />
                    </ListItemIcon>
                    <ListItemText
                      primary="Correo Electrónico"
                      secondary={user.correoElectronico}
                    />
                  </ListItem>
                  <ListItem disablePadding>
                    <ListItemIcon sx={{ minWidth: 40 }}>
                      <CalendarToday color="primary" />
                    </ListItemIcon>
                    <ListItemText
                      primary="Miembro desde"
                      secondary={formatDate(user.fechaCreacion)}
                    />
                  </ListItem>
                </List>
              </CardContent>
            </Card>
          </motion.div>
        </Grid>

        {/* Stats and Activity */}
        <Grid item xs={12} md={8}>
          {/* Stats Cards */}
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.6, delay: 0.1 }}
          >
            <Grid container spacing={3} sx={{ mb: 4 }}>
              <Grid item xs={6} md={3}>
                <Card sx={{ borderRadius: 3, textAlign: 'center' }}>
                  <CardContent sx={{ p: 3 }}>
                    <Forum sx={{ fontSize: 40, color: 'primary.main', mb: 1 }} />
                    <Typography variant="h4" sx={{ fontWeight: 'bold', mb: 1 }}>
                      {stats.topicosCreados}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      Tópicos Creados
                    </Typography>
                  </CardContent>
                </Card>
              </Grid>
              <Grid item xs={6} md={3}>
                <Card sx={{ borderRadius: 3, textAlign: 'center' }}>
                  <CardContent sx={{ p: 3 }}>
                    <QuestionAnswer sx={{ fontSize: 40, color: 'secondary.main', mb: 1 }} />
                    <Typography variant="h4" sx={{ fontWeight: 'bold', mb: 1 }}>
                      {stats.respuestasEscritas}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      Respuestas
                    </Typography>
                  </CardContent>
                </Card>
              </Grid>
              <Grid item xs={6} md={3}>
                <Card sx={{ borderRadius: 3, textAlign: 'center' }}>
                  <CardContent sx={{ p: 3 }}>
                    <CheckCircle sx={{ fontSize: 40, color: 'success.main', mb: 1 }} />
                    <Typography variant="h4" sx={{ fontWeight: 'bold', mb: 1 }}>
                      {stats.solucionesAceptadas}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      Soluciones
                    </Typography>
                  </CardContent>
                </Card>
              </Grid>
              <Grid item xs={6} md={3}>
                <Card sx={{ borderRadius: 3, textAlign: 'center' }}>
                  <CardContent sx={{ p: 3 }}>
                    <School sx={{ fontSize: 40, color: 'warning.main', mb: 1 }} />
                    <Typography variant="h4" sx={{ fontWeight: 'bold', mb: 1 }}>
                      {stats.reputacion}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      Reputación
                    </Typography>
                  </CardContent>
                </Card>
              </Grid>
            </Grid>
          </motion.div>

          {/* Achievements */}
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.6, delay: 0.2 }}
          >
            <Card sx={{ borderRadius: 3, mb: 4 }}>
              <CardContent sx={{ p: 4 }}>
                <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 3 }}>
                  🏆 Logros
                </Typography>
                <Grid container spacing={2}>
                  {achievements.map((achievement, index) => (
                    <Grid item xs={12} sm={6} key={index}>
                      <Box
                        sx={{
                          p: 2,
                          borderRadius: 2,
                          border: '1px solid',
                          borderColor: achievement.earned ? 'success.main' : 'grey.300',
                          bgcolor: achievement.earned ? 'success.50' : 'grey.50',
                          opacity: achievement.earned ? 1 : 0.6,
                          display: 'flex',
                          alignItems: 'center',
                          gap: 2,
                        }}
                      >
                        <Box
                          sx={{
                            width: 40,
                            height: 40,
                            borderRadius: '50%',
                            bgcolor: achievement.earned ? 'success.main' : 'grey.400',
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                            color: 'white',
                            fontSize: '1.2rem',
                          }}
                        >
                          {achievement.earned ? '✓' : '?'}
                        </Box>
                        <Box>
                          <Typography variant="subtitle2" sx={{ fontWeight: 'bold' }}>
                            {achievement.name}
                          </Typography>
                          <Typography variant="caption" color="text.secondary">
                            {achievement.description}
                          </Typography>
                        </Box>
                      </Box>
                    </Grid>
                  ))}
                </Grid>
              </CardContent>
            </Card>
          </motion.div>

          {/* Recent Activity */}
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.6, delay: 0.3 }}
          >
            <Card sx={{ borderRadius: 3 }}>
              <CardContent sx={{ p: 4 }}>
                <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 3 }}>
                  📈 Actividad Reciente
                </Typography>
                <Box
                  sx={{
                    textAlign: 'center',
                    py: 4,
                    color: 'text.secondary',
                  }}
                >
                  <Box sx={{ fontSize: '3rem', mb: 2 }}>📊</Box>
                  <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 1 }}>
                    Próximamente
                  </Typography>
                  <Typography variant="body2">
                    Aquí podrás ver tu actividad reciente en la plataforma
                  </Typography>
                </Box>
              </CardContent>
            </Card>
          </motion.div>
        </Grid>
      </Grid>
    </Container>
  );
};

export default PerfilPage;