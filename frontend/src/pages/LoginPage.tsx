import React, { useState } from 'react';
import {
  Box,
  Card,
  CardContent,
  TextField,
  Button,
  Typography,
  Link,
  Container,
  InputAdornment,
  IconButton,
  Alert,
  CircularProgress,
} from '@mui/material';
import {
  Email,
  Lock,
  Visibility,
  VisibilityOff,
  Login as LoginIcon,
} from '@mui/icons-material';
import { Link as RouterLink, useNavigate, useLocation } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { motion } from 'framer-motion';
import toast from 'react-hot-toast';

import { useAuthStore } from '../store/authStore';
import { authService, LoginRequest } from '../services/authService';

const LoginPage: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { login } = useAuthStore();
  
  const [showPassword, setShowPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginRequest>();

  const from = location.state?.from?.pathname || '/';

  const onSubmit = async (data: LoginRequest) => {
    setIsLoading(true);
    setError(null);

    try {
      const response = await authService.login(data);
      
      // Crear un usuario mock para el store (en producción vendría del backend)
      const mockUser = {
        id: 1,
        nombre: 'Usuario Demo',
        correoElectronico: data.correoElectronico,
        fechaCreacion: new Date().toISOString(),
        perfil: {
          id: 1,
          nombre: 'Usuario',
          tipo: 'USUARIO' as const,
          descripcion: 'Perfil básico de usuario',
        },
      };

      login(response.token, mockUser);
      toast.success('¡Bienvenido de vuelta!');
      navigate(from, { replace: true });
    } catch (error: any) {
      setError(error.response?.data?.mensaje || 'Error al iniciar sesión');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Box
      sx={{
        minHeight: '100vh',
        background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        py: 3,
      }}
    >
      <Container maxWidth="sm">
        <motion.div
          initial={{ opacity: 0, y: 50 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.6 }}
        >
          <Card
            sx={{
              borderRadius: 4,
              boxShadow: '0px 20px 60px rgba(0, 0, 0, 0.15)',
              overflow: 'hidden',
            }}
          >
            <Box
              sx={{
                background: 'linear-gradient(135deg, #1976d2 0%, #42a5f5 100%)',
                color: 'white',
                py: 4,
                textAlign: 'center',
              }}
            >
              <motion.div
                initial={{ scale: 0 }}
                animate={{ scale: 1 }}
                transition={{ delay: 0.2, type: 'spring', stiffness: 200 }}
              >
                <LoginIcon sx={{ fontSize: 48, mb: 2 }} />
              </motion.div>
              <Typography variant="h4" sx={{ fontWeight: 'bold', mb: 1 }}>
                Bienvenido
              </Typography>
              <Typography variant="body1" sx={{ opacity: 0.9 }}>
                Inicia sesión en ForoHub
              </Typography>
            </Box>

            <CardContent sx={{ p: 4 }}>
              {error && (
                <motion.div
                  initial={{ opacity: 0, x: -20 }}
                  animate={{ opacity: 1, x: 0 }}
                  transition={{ duration: 0.3 }}
                >
                  <Alert severity="error" sx={{ mb: 3, borderRadius: 2 }}>
                    {error}
                  </Alert>
                </motion.div>
              )}

              <Box component="form" onSubmit={handleSubmit(onSubmit)}>
                <motion.div
                  initial={{ opacity: 0, x: -20 }}
                  animate={{ opacity: 1, x: 0 }}
                  transition={{ delay: 0.3, duration: 0.5 }}
                >
                  <TextField
                    fullWidth
                    label="Correo Electrónico"
                    type="email"
                    margin="normal"
                    {...register('correoElectronico', {
                      required: 'El correo electrónico es obligatorio',
                      pattern: {
                        value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                        message: 'Correo electrónico inválido',
                      },
                    })}
                    error={!!errors.correoElectronico}
                    helperText={errors.correoElectronico?.message}
                    InputProps={{
                      startAdornment: (
                        <InputAdornment position="start">
                          <Email color="action" />
                        </InputAdornment>
                      ),
                    }}
                  />
                </motion.div>

                <motion.div
                  initial={{ opacity: 0, x: -20 }}
                  animate={{ opacity: 1, x: 0 }}
                  transition={{ delay: 0.4, duration: 0.5 }}
                >
                  <TextField
                    fullWidth
                    label="Contraseña"
                    type={showPassword ? 'text' : 'password'}
                    margin="normal"
                    {...register('contrasena', {
                      required: 'La contraseña es obligatoria',
                    })}
                    error={!!errors.contrasena}
                    helperText={errors.contrasena?.message}
                    InputProps={{
                      startAdornment: (
                        <InputAdornment position="start">
                          <Lock color="action" />
                        </InputAdornment>
                      ),
                      endAdornment: (
                        <InputAdornment position="end">
                          <IconButton
                            onClick={() => setShowPassword(!showPassword)}
                            edge="end"
                          >
                            {showPassword ? <VisibilityOff /> : <Visibility />}
                          </IconButton>
                        </InputAdornment>
                      ),
                    }}
                  />
                </motion.div>

                <motion.div
                  initial={{ opacity: 0, y: 20 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ delay: 0.5, duration: 0.5 }}
                >
                  <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    size="large"
                    disabled={isLoading}
                    sx={{
                      mt: 3,
                      mb: 2,
                      py: 1.5,
                      background: 'linear-gradient(135deg, #1976d2 0%, #42a5f5 100%)',
                      '&:hover': {
                        background: 'linear-gradient(135deg, #1565c0 0%, #1976d2 100%)',
                      },
                    }}
                  >
                    {isLoading ? (
                      <CircularProgress size={24} color="inherit" />
                    ) : (
                      'Iniciar Sesión'
                    )}
                  </Button>
                </motion.div>

                <motion.div
                  initial={{ opacity: 0 }}
                  animate={{ opacity: 1 }}
                  transition={{ delay: 0.6, duration: 0.5 }}
                >
                  <Box sx={{ textAlign: 'center' }}>
                    <Typography variant="body2" color="text.secondary">
                      ¿No tienes una cuenta?{' '}
                      <Link
                        component={RouterLink}
                        to="/register"
                        sx={{
                          color: 'primary.main',
                          textDecoration: 'none',
                          fontWeight: 600,
                          '&:hover': {
                            textDecoration: 'underline',
                          },
                        }}
                      >
                        Regístrate aquí
                      </Link>
                    </Typography>
                  </Box>
                </motion.div>
              </Box>
            </CardContent>
          </Card>
        </motion.div>
      </Container>
    </Box>
  );
};

export default LoginPage;