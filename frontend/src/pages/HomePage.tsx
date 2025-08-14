import React from 'react';
import {
  Box,
  Container,
  Typography,
  Button,
  Grid,
  Card,
  CardContent,
  Avatar,
  Chip,
  Paper,
  useTheme,
  alpha,
} from '@mui/material';
import {
  Forum,
  People,
  School,
  TrendingUp,
  QuestionAnswer,
  EmojiObjects,
  Speed,
  Security,
  ArrowForward,
} from '@mui/icons-material';
import { Link as RouterLink } from 'react-router-dom';
import { motion } from 'framer-motion';

import { useAuthStore } from '../store/authStore';

const HomePage: React.FC = () => {
  const theme = useTheme();
  const { isAuthenticated } = useAuthStore();

  const features = [
    {
      icon: <Forum sx={{ fontSize: 40 }} />,
      title: 'Discusiones Dinámicas',
      description: 'Participa en conversaciones enriquecedoras sobre tecnología y programación.',
      color: theme.palette.primary.main,
    },
    {
      icon: <People sx={{ fontSize: 40 }} />,
      title: 'Comunidad Activa',
      description: 'Conecta con estudiantes y profesionales de toda Latinoamérica.',
      color: theme.palette.secondary.main,
    },
    {
      icon: <EmojiObjects sx={{ fontSize: 40 }} />,
      title: 'Soluciones Colaborativas',
      description: 'Encuentra respuestas y comparte tu conocimiento con la comunidad.',
      color: theme.palette.success.main,
    },
    {
      icon: <Speed sx={{ fontSize: 40 }} />,
      title: 'Respuestas Rápidas',
      description: 'Obtén ayuda inmediata de expertos y compañeros de estudio.',
      color: theme.palette.warning.main,
    },
  ];

  const stats = [
    { number: '10K+', label: 'Estudiantes Activos', icon: <People /> },
    { number: '5K+', label: 'Tópicos Resueltos', icon: <QuestionAnswer /> },
    { number: '50+', label: 'Cursos Disponibles', icon: <School /> },
    { number: '95%', label: 'Satisfacción', icon: <TrendingUp /> },
  ];

  const testimonials = [
    {
      name: 'María González',
      role: 'Desarrolladora Frontend',
      avatar: 'MG',
      comment: 'ForoHub me ayudó a resolver dudas complejas de React. La comunidad es increíble.',
      course: 'React Avanzado',
    },
    {
      name: 'Carlos Rodríguez',
      role: 'Data Scientist',
      avatar: 'CR',
      comment: 'Encontré soluciones a problemas de Python que no podía resolver solo. ¡Excelente plataforma!',
      course: 'Python para Data Science',
    },
    {
      name: 'Ana Silva',
      role: 'Backend Developer',
      avatar: 'AS',
      comment: 'La calidad de las respuestas y la rapidez de la comunidad superaron mis expectativas.',
      course: 'Spring Boot',
    },
  ];

  return (
    <Box>
      {/* Hero Section */}
      <Box
        sx={{
          background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
          color: 'white',
          py: { xs: 8, md: 12 },
          position: 'relative',
          overflow: 'hidden',
        }}
      >
        <Container maxWidth="lg">
          <Grid container spacing={4} alignItems="center">
            <Grid item xs={12} md={6}>
              <motion.div
                initial={{ opacity: 0, x: -50 }}
                animate={{ opacity: 1, x: 0 }}
                transition={{ duration: 0.8 }}
              >
                <Typography
                  variant="h2"
                  sx={{
                    fontWeight: 'bold',
                    mb: 3,
                    fontSize: { xs: '2.5rem', md: '3.5rem' },
                    lineHeight: 1.2,
                  }}
                >
                  Aprende, Comparte y{' '}
                  <Box
                    component="span"
                    sx={{
                      background: 'linear-gradient(45deg, #FFD700, #FFA500)',
                      backgroundClip: 'text',
                      WebkitBackgroundClip: 'text',
                      WebkitTextFillColor: 'transparent',
                    }}
                  >
                    Crece
                  </Box>{' '}
                  Juntos
                </Typography>
                <Typography
                  variant="h5"
                  sx={{
                    mb: 4,
                    opacity: 0.9,
                    fontWeight: 400,
                    lineHeight: 1.6,
                  }}
                >
                  La plataforma de foro donde los estudiantes de Alura se conectan
                  para resolver dudas, compartir conocimiento y acelerar su aprendizaje.
                </Typography>
                <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap' }}>
                  {!isAuthenticated ? (
                    <>
                      <Button
                        component={RouterLink}
                        to="/register"
                        variant="contained"
                        size="large"
                        sx={{
                          bgcolor: 'white',
                          color: 'primary.main',
                          px: 4,
                          py: 1.5,
                          '&:hover': {
                            bgcolor: alpha('#ffffff', 0.9),
                            transform: 'translateY(-2px)',
                          },
                        }}
                      >
                        Únete Gratis
                      </Button>
                      <Button
                        component={RouterLink}
                        to="/topicos"
                        variant="outlined"
                        size="large"
                        endIcon={<ArrowForward />}
                        sx={{
                          borderColor: 'white',
                          color: 'white',
                          px: 4,
                          py: 1.5,
                          '&:hover': {
                            borderColor: 'white',
                            bgcolor: alpha('#ffffff', 0.1),
                          },
                        }}
                      >
                        Explorar Tópicos
                      </Button>
                    </>
                  ) : (
                    <>
                      <Button
                        component={RouterLink}
                        to="/crear-topico"
                        variant="contained"
                        size="large"
                        sx={{
                          bgcolor: 'white',
                          color: 'primary.main',
                          px: 4,
                          py: 1.5,
                          '&:hover': {
                            bgcolor: alpha('#ffffff', 0.9),
                            transform: 'translateY(-2px)',
                          },
                        }}
                      >
                        Crear Tópico
                      </Button>
                      <Button
                        component={RouterLink}
                        to="/mis-topicos"
                        variant="outlined"
                        size="large"
                        endIcon={<ArrowForward />}
                        sx={{
                          borderColor: 'white',
                          color: 'white',
                          px: 4,
                          py: 1.5,
                          '&:hover': {
                            borderColor: 'white',
                            bgcolor: alpha('#ffffff', 0.1),
                          },
                        }}
                      >
                        Mis Tópicos
                      </Button>
                    </>
                  )}
                </Box>
              </motion.div>
            </Grid>
            <Grid item xs={12} md={6}>
              <motion.div
                initial={{ opacity: 0, x: 50 }}
                animate={{ opacity: 1, x: 0 }}
                transition={{ duration: 0.8, delay: 0.2 }}
              >
                <Box
                  sx={{
                    position: 'relative',
                    textAlign: 'center',
                    '&::before': {
                      content: '""',
                      position: 'absolute',
                      top: '50%',
                      left: '50%',
                      transform: 'translate(-50%, -50%)',
                      width: '300px',
                      height: '300px',
                      background: 'radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 70%)',
                      borderRadius: '50%',
                      animation: 'pulse 3s ease-in-out infinite',
                    },
                  }}
                >
                  <Box sx={{ fontSize: '12rem', lineHeight: 1 }}>🚀</Box>
                </Box>
              </motion.div>
            </Grid>
          </Grid>
        </Container>
      </Box>

      {/* Stats Section */}
      <Box sx={{ py: 6, bgcolor: 'background.paper' }}>
        <Container maxWidth="lg">
          <Grid container spacing={4}>
            {stats.map((stat, index) => (
              <Grid item xs={6} md={3} key={index}>
                <motion.div
                  initial={{ opacity: 0, y: 30 }}
                  whileInView={{ opacity: 1, y: 0 }}
                  transition={{ duration: 0.6, delay: index * 0.1 }}
                  viewport={{ once: true }}
                >
                  <Paper
                    sx={{
                      p: 3,
                      textAlign: 'center',
                      borderRadius: 3,
                      border: '1px solid',
                      borderColor: 'divider',
                      '&:hover': {
                        transform: 'translateY(-4px)',
                        boxShadow: theme.shadows[8],
                      },
                      transition: 'all 0.3s ease',
                    }}
                  >
                    <Box sx={{ color: 'primary.main', mb: 1 }}>
                      {stat.icon}
                    </Box>
                    <Typography variant="h4" sx={{ fontWeight: 'bold', mb: 1 }}>
                      {stat.number}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      {stat.label}
                    </Typography>
                  </Paper>
                </motion.div>
              </Grid>
            ))}
          </Grid>
        </Container>
      </Box>

      {/* Features Section */}
      <Box sx={{ py: 8, bgcolor: 'grey.50' }}>
        <Container maxWidth="lg">
          <motion.div
            initial={{ opacity: 0, y: 30 }}
            whileInView={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8 }}
            viewport={{ once: true }}
          >
            <Typography
              variant="h3"
              sx={{
                textAlign: 'center',
                fontWeight: 'bold',
                mb: 2,
                color: 'text.primary',
              }}
            >
              ¿Por qué elegir ForoHub?
            </Typography>
            <Typography
              variant="h6"
              sx={{
                textAlign: 'center',
                color: 'text.secondary',
                mb: 6,
                maxWidth: '600px',
                mx: 'auto',
              }}
            >
              Descubre las características que hacen de ForoHub la mejor plataforma
              para el aprendizaje colaborativo
            </Typography>
          </motion.div>

          <Grid container spacing={4}>
            {features.map((feature, index) => (
              <Grid item xs={12} md={6} key={index}>
                <motion.div
                  initial={{ opacity: 0, y: 30 }}
                  whileInView={{ opacity: 1, y: 0 }}
                  transition={{ duration: 0.6, delay: index * 0.1 }}
                  viewport={{ once: true }}
                >
                  <Card
                    sx={{
                      height: '100%',
                      borderRadius: 3,
                      border: '1px solid',
                      borderColor: 'divider',
                      '&:hover': {
                        transform: 'translateY(-4px)',
                        boxShadow: theme.shadows[12],
                      },
                      transition: 'all 0.3s ease',
                    }}
                  >
                    <CardContent sx={{ p: 4 }}>
                      <Box
                        sx={{
                          display: 'flex',
                          alignItems: 'center',
                          mb: 3,
                        }}
                      >
                        <Box
                          sx={{
                            p: 2,
                            borderRadius: 2,
                            bgcolor: alpha(feature.color, 0.1),
                            color: feature.color,
                            mr: 3,
                          }}
                        >
                          {feature.icon}
                        </Box>
                        <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
                          {feature.title}
                        </Typography>
                      </Box>
                      <Typography
                        variant="body1"
                        color="text.secondary"
                        sx={{ lineHeight: 1.7 }}
                      >
                        {feature.description}
                      </Typography>
                    </CardContent>
                  </Card>
                </motion.div>
              </Grid>
            ))}
          </Grid>
        </Container>
      </Box>

      {/* Testimonials Section */}
      <Box sx={{ py: 8, bgcolor: 'background.paper' }}>
        <Container maxWidth="lg">
          <motion.div
            initial={{ opacity: 0, y: 30 }}
            whileInView={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8 }}
            viewport={{ once: true }}
          >
            <Typography
              variant="h3"
              sx={{
                textAlign: 'center',
                fontWeight: 'bold',
                mb: 2,
                color: 'text.primary',
              }}
            >
              Lo que dicen nuestros estudiantes
            </Typography>
            <Typography
              variant="h6"
              sx={{
                textAlign: 'center',
                color: 'text.secondary',
                mb: 6,
                maxWidth: '600px',
                mx: 'auto',
              }}
            >
              Historias reales de estudiantes que han transformado su aprendizaje
              con ForoHub
            </Typography>
          </motion.div>

          <Grid container spacing={4}>
            {testimonials.map((testimonial, index) => (
              <Grid item xs={12} md={4} key={index}>
                <motion.div
                  initial={{ opacity: 0, y: 30 }}
                  whileInView={{ opacity: 1, y: 0 }}
                  transition={{ duration: 0.6, delay: index * 0.1 }}
                  viewport={{ once: true }}
                >
                  <Card
                    sx={{
                      height: '100%',
                      borderRadius: 3,
                      border: '1px solid',
                      borderColor: 'divider',
                      '&:hover': {
                        transform: 'translateY(-4px)',
                        boxShadow: theme.shadows[8],
                      },
                      transition: 'all 0.3s ease',
                    }}
                  >
                    <CardContent sx={{ p: 4 }}>
                      <Typography
                        variant="body1"
                        sx={{
                          mb: 3,
                          fontStyle: 'italic',
                          lineHeight: 1.7,
                          color: 'text.secondary',
                        }}
                      >
                        "{testimonial.comment}"
                      </Typography>
                      <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                        <Avatar
                          sx={{
                            bgcolor: 'primary.main',
                            mr: 2,
                            width: 48,
                            height: 48,
                          }}
                        >
                          {testimonial.avatar}
                        </Avatar>
                        <Box>
                          <Typography variant="subtitle1" sx={{ fontWeight: 'bold' }}>
                            {testimonial.name}
                          </Typography>
                          <Typography variant="body2" color="text.secondary">
                            {testimonial.role}
                          </Typography>
                        </Box>
                      </Box>
                      <Chip
                        label={testimonial.course}
                        size="small"
                        sx={{
                          bgcolor: alpha(theme.palette.primary.main, 0.1),
                          color: 'primary.main',
                        }}
                      />
                    </CardContent>
                  </Card>
                </motion.div>
              </Grid>
            ))}
          </Grid>
        </Container>
      </Box>

      {/* CTA Section */}
      <Box
        sx={{
          py: 8,
          background: 'linear-gradient(135deg, #1976d2 0%, #42a5f5 100%)',
          color: 'white',
        }}
      >
        <Container maxWidth="md">
          <motion.div
            initial={{ opacity: 0, y: 30 }}
            whileInView={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8 }}
            viewport={{ once: true }}
          >
            <Box sx={{ textAlign: 'center' }}>
              <Security sx={{ fontSize: 60, mb: 3, opacity: 0.9 }} />
              <Typography
                variant="h3"
                sx={{ fontWeight: 'bold', mb: 3 }}
              >
                ¿Listo para comenzar?
              </Typography>
              <Typography
                variant="h6"
                sx={{ mb: 4, opacity: 0.9, lineHeight: 1.6 }}
              >
                Únete a miles de estudiantes que ya están acelerando su aprendizaje
                con ForoHub. Es gratis y siempre lo será.
              </Typography>
              {!isAuthenticated && (
                <Button
                  component={RouterLink}
                  to="/register"
                  variant="contained"
                  size="large"
                  sx={{
                    bgcolor: 'white',
                    color: 'primary.main',
                    px: 6,
                    py: 2,
                    fontSize: '1.1rem',
                    '&:hover': {
                      bgcolor: alpha('#ffffff', 0.9),
                      transform: 'translateY(-2px)',
                    },
                  }}
                >
                  Crear Cuenta Gratis
                </Button>
              )}
            </Box>
          </motion.div>
        </Container>
      </Box>
    </Box>
  );
};

export default HomePage;