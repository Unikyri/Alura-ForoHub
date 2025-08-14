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
  Person,
  Email,
  Lock,
  Visibility,
  VisibilityOff,
  PersonAdd as RegisterIcon,
} from '@mui/icons-material';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { motion } from 'framer-motion';
import toast from 'react-hot-toast';

import { useAuthStore } from '../store/authStore';
import { authService, RegisterRequest } from '../services/authService';

const RegisterPage: React.FC = () => {
  const navigate = useNavigate();
  const { login } = useAuthStore();
  
  const [showPassword, setShowPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const {
    register,
    handleSubmit,
    formState: { errors },
    watch,
  } = useForm<RegisterRequest & { confirmarContrasena: string }>();

  const password = watch('contrasena');

  const onSubmit = async (data: RegisterRequest & { confirmarContrasena: string }) => {
    setIsLoading(true);
    setError(null);

    try {
      const { confirmarContrasena, ...registerData } = data;
      const response = await authService.register(registerData);
      
      // Crear un usuario mock para el store
      const mockUser = {
        id: 1,
        nombre: data.nombre,
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
      toast.success('¡Cuenta creada exitosamente! Bienvenido a ForoHub');
      navigate('/');
    } catch (error: any) {
      setError(error.response?.data?.mensaje || 'Error al crear la cuenta');
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
                background: 'linear-gradient(135deg, #9c27b0 0%, #e1bee7 100%)',
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
                <RegisterIcon sx={{ fontSize: 48, mb: 2 }} />
              </motion.div>
              <Typography variant="h4" sx={{ fontWeight: 'bold', mb: 1 }}>
                Únete a ForoHub
              </Typography>
              <Typography variant="body1" sx={{ opacity: 0.9 }}>
                Crea tu cuenta y comienza a aprender
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
                    label="Nombre Completo"
                    margin="normal"
                    {...register('nombre', {
                      required: 'El nombre es obligatorio',
                      minLength: {
                        value: 2,
                        message: 'El nombre debe tener al menos 2 caracteres',
                      },
                      maxLength: {
                        value: 100,
                        message: 'El nombre no puede exceder 100 caracteres',
                      },
                    })}
                    error={!!errors.nombre}
                    helperText={errors.nombre?.message}
                    InputProps={{
                      startAdornment: (
                        <InputAdornment position="start">
                          <Person color="action" />
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
                  transition={{ delay: 0.5, duration: 0.5 }}
                >
                  <TextField
                    fullWidth
                    label="Contraseña"
                    type={showPassword ? 'text' : 'password'}
                    margin="normal"
                    {...register('contrasena', {
                      required: 'La contraseña es obligatoria',
                      minLength: {
                        value: 6,
                        message: 'La contraseña debe tener al menos 6 caracteres',
                      },
                      maxLength: {
                        value: 100,
                        message: 'La contraseña no puede exceder 100 caracteres',
                      },
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
                  initial={{ opacity: 0, x: -20 }}
                  animate={{ opacity: 1, x: 0 }}
                  transition={{ delay: 0.6, duration: 0.5 }}
                >
                  <TextField
                    fullWidth
                    label="Confirmar Contraseña"
                    type={showPassword ? 'text' : 'password'}
                    margin="normal"
                    {...register('confirmarContrasena', {
                      required: 'Confirma tu contraseña',
                      validate: (value) =>
                        value === password || 'Las contraseñas no coinciden',
                    })}
                    error={!!errors.confirmarContrasena}
                    helperText={errors.confirmarContrasena?.message}
                    InputProps={{
                      startAdornment: (
                        <InputAdornment position="start">
                          <Lock color="action" />
                        </InputAdornment>
                      ),
                    }}
                  />
                </motion.div>

                <motion.div
                  initial={{ opacity: 0, y: 20 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ delay: 0.7, duration: 0.5 }}
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
                      background: 'linear-gradient(135deg, #9c27b0 0%, #e1bee7 100%)',
                      '&:hover': {
                        background: 'linear-gradient(135deg, #7b1fa2 0%, #9c27b0 100%)',
                      },
                    }}
                  >
                    {isLoading ? (
                      <CircularProgress size={24} color="inherit" />
                    ) : (
                      'Crear Cuenta'
                    )}
                  </Button>
                </motion.div>

                <motion.div
                  initial={{ opacity: 0 }}
                  animate={{ opacity: 1 }}
                  transition={{ delay: 0.8, duration: 0.5 }}
                >
                  <Box sx={{ textAlign: 'center' }}>
                    <Typography variant="body2" color="text.secondary">
                      ¿Ya tienes una cuenta?{' '}
                      <Link
                        component={RouterLink}
                        to="/login"
                        sx={{
                          color: 'secondary.main',
                          textDecoration: 'none',
                          fontWeight: 600,
                          '&:hover': {
                            textDecoration: 'underline',
                          },
                        }}
                      >
                        Inicia sesión aquí
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

export default RegisterPage;