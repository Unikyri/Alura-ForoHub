import React from 'react';
import {
  Box,
  Container,
  Typography,
  Link,
  Grid,
  IconButton,
  Divider,
} from '@mui/material';
import {
  GitHub,
  LinkedIn,
  Twitter,
  Email,
  Favorite,
} from '@mui/icons-material';
import { motion } from 'framer-motion';

const Footer: React.FC = () => {
  const currentYear = new Date().getFullYear();

  return (
    <Box
      component="footer"
      sx={{
        bgcolor: 'grey.900',
        color: 'white',
        py: 6,
        mt: 'auto',
      }}
    >
      <Container maxWidth="lg">
        <Grid container spacing={4}>
          {/* Brand Section */}
          <Grid item xs={12} md={4}>
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.6 }}
              viewport={{ once: true }}
            >
              <Typography
                variant="h5"
                sx={{
                  fontWeight: 'bold',
                  mb: 2,
                  background: 'linear-gradient(45deg, #42a5f5, #64b5f6)',
                  backgroundClip: 'text',
                  WebkitBackgroundClip: 'text',
                  WebkitTextFillColor: 'transparent',
                }}
              >
                ForoHub
              </Typography>
              <Typography variant="body2" sx={{ mb: 3, color: 'grey.400' }}>
                La plataforma de foro digital que conecta a estudiantes de Alura
                para compartir conocimiento, resolver dudas y crecer juntos en
                el mundo de la tecnología.
              </Typography>
              <Box sx={{ display: 'flex', gap: 1 }}>
                <IconButton
                  component="a"
                  href="https://github.com"
                  target="_blank"
                  rel="noopener noreferrer"
                  sx={{ color: 'grey.400', '&:hover': { color: 'primary.main' } }}
                >
                  <GitHub />
                </IconButton>
                <IconButton
                  component="a"
                  href="https://linkedin.com"
                  target="_blank"
                  rel="noopener noreferrer"
                  sx={{ color: 'grey.400', '&:hover': { color: 'primary.main' } }}
                >
                  <LinkedIn />
                </IconButton>
                <IconButton
                  component="a"
                  href="https://twitter.com"
                  target="_blank"
                  rel="noopener noreferrer"
                  sx={{ color: 'grey.400', '&:hover': { color: 'primary.main' } }}
                >
                  <Twitter />
                </IconButton>
                <IconButton
                  component="a"
                  href="mailto:support@forohub.com"
                  sx={{ color: 'grey.400', '&:hover': { color: 'primary.main' } }}
                >
                  <Email />
                </IconButton>
              </Box>
            </motion.div>
          </Grid>

          {/* Quick Links */}
          <Grid item xs={12} sm={6} md={2}>
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.6, delay: 0.1 }}
              viewport={{ once: true }}
            >
              <Typography variant="h6" sx={{ fontWeight: 600, mb: 2 }}>
                Navegación
              </Typography>
              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 1 }}>
                <Link
                  href="/"
                  color="grey.400"
                  underline="hover"
                  sx={{ '&:hover': { color: 'primary.main' } }}
                >
                  Inicio
                </Link>
                <Link
                  href="/topicos"
                  color="grey.400"
                  underline="hover"
                  sx={{ '&:hover': { color: 'primary.main' } }}
                >
                  Tópicos
                </Link>
                <Link
                  href="/crear-topico"
                  color="grey.400"
                  underline="hover"
                  sx={{ '&:hover': { color: 'primary.main' } }}
                >
                  Crear Tópico
                </Link>
              </Box>
            </motion.div>
          </Grid>

          {/* Categories */}
          <Grid item xs={12} sm={6} md={3}>
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.6, delay: 0.2 }}
              viewport={{ once: true }}
            >
              <Typography variant="h6" sx={{ fontWeight: 600, mb: 2 }}>
                Categorías
              </Typography>
              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 1 }}>
                <Link
                  href="/topicos?categoria=Programación"
                  color="grey.400"
                  underline="hover"
                  sx={{ '&:hover': { color: 'primary.main' } }}
                >
                  Programación
                </Link>
                <Link
                  href="/topicos?categoria=Frontend"
                  color="grey.400"
                  underline="hover"
                  sx={{ '&:hover': { color: 'primary.main' } }}
                >
                  Frontend
                </Link>
                <Link
                  href="/topicos?categoria=Data Science"
                  color="grey.400"
                  underline="hover"
                  sx={{ '&:hover': { color: 'primary.main' } }}
                >
                  Data Science
                </Link>
                <Link
                  href="/topicos?categoria=DevOps"
                  color="grey.400"
                  underline="hover"
                  sx={{ '&:hover': { color: 'primary.main' } }}
                >
                  DevOps
                </Link>
              </Box>
            </motion.div>
          </Grid>

          {/* Support */}
          <Grid item xs={12} md={3}>
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.6, delay: 0.3 }}
              viewport={{ once: true }}
            >
              <Typography variant="h6" sx={{ fontWeight: 600, mb: 2 }}>
                Soporte
              </Typography>
              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 1 }}>
                <Link
                  href="/ayuda"
                  color="grey.400"
                  underline="hover"
                  sx={{ '&:hover': { color: 'primary.main' } }}
                >
                  Centro de Ayuda
                </Link>
                <Link
                  href="/contacto"
                  color="grey.400"
                  underline="hover"
                  sx={{ '&:hover': { color: 'primary.main' } }}
                >
                  Contacto
                </Link>
                <Link
                  href="/terminos"
                  color="grey.400"
                  underline="hover"
                  sx={{ '&:hover': { color: 'primary.main' } }}
                >
                  Términos de Uso
                </Link>
                <Link
                  href="/privacidad"
                  color="grey.400"
                  underline="hover"
                  sx={{ '&:hover': { color: 'primary.main' } }}
                >
                  Privacidad
                </Link>
              </Box>
            </motion.div>
          </Grid>
        </Grid>

        <Divider sx={{ my: 4, borderColor: 'grey.800' }} />

        {/* Bottom Section */}
        <motion.div
          initial={{ opacity: 0 }}
          whileInView={{ opacity: 1 }}
          transition={{ duration: 0.6, delay: 0.4 }}
          viewport={{ once: true }}
        >
          <Box
            sx={{
              display: 'flex',
              flexDirection: { xs: 'column', sm: 'row' },
              justifyContent: 'space-between',
              alignItems: 'center',
              gap: 2,
            }}
          >
            <Typography variant="body2" color="grey.400">
              © {currentYear} ForoHub. Todos los derechos reservados.
            </Typography>
            <Box
              sx={{
                display: 'flex',
                alignItems: 'center',
                gap: 1,
                color: 'grey.400',
              }}
            >
              <Typography variant="body2">
                Hecho con
              </Typography>
              <Favorite sx={{ fontSize: 16, color: 'error.main' }} />
              <Typography variant="body2">
                para la comunidad Alura
              </Typography>
            </Box>
          </Box>
        </motion.div>
      </Container>
    </Box>
  );
};

export default Footer;