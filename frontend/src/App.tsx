import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { Box } from '@mui/material';
import { motion, AnimatePresence } from 'framer-motion';

import { useAuthStore } from './store/authStore';
import Layout from './components/Layout/Layout';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import TopicosPage from './pages/TopicosPage';
import TopicoDetailPage from './pages/TopicoDetailPage';
import CrearTopicoPage from './pages/CrearTopicoPage';
import MisTopicosPage from './pages/MisTopicosPage';
import PerfilPage from './pages/PerfilPage';
import ProtectedRoute from './components/Auth/ProtectedRoute';

const App: React.FC = () => {
  const { isAuthenticated } = useAuthStore();

  return (
    <Box sx={{ minHeight: '100vh', bgcolor: 'background.default' }}>
      <AnimatePresence mode="wait">
        <Routes>
          {/* Rutas públicas */}
          <Route path="/" element={<Layout />}>
            <Route index element={<HomePage />} />
            <Route path="topicos" element={<TopicosPage />} />
            <Route path="topicos/:id" element={<TopicoDetailPage />} />
          </Route>

          {/* Rutas de autenticación */}
          <Route
            path="/login"
            element={
              isAuthenticated ? (
                <Navigate to="/" replace />
              ) : (
                <motion.div
                  initial={{ opacity: 0, y: 20 }}
                  animate={{ opacity: 1, y: 0 }}
                  exit={{ opacity: 0, y: -20 }}
                  transition={{ duration: 0.3 }}
                >
                  <LoginPage />
                </motion.div>
              )
            }
          />
          <Route
            path="/register"
            element={
              isAuthenticated ? (
                <Navigate to="/" replace />
              ) : (
                <motion.div
                  initial={{ opacity: 0, y: 20 }}
                  animate={{ opacity: 1, y: 0 }}
                  exit={{ opacity: 0, y: -20 }}
                  transition={{ duration: 0.3 }}
                >
                  <RegisterPage />
                </motion.div>
              )
            }
          />

          {/* Rutas protegidas */}
          <Route
            path="/crear-topico"
            element={
              <ProtectedRoute>
                <Layout>
                  <CrearTopicoPage />
                </Layout>
              </ProtectedRoute>
            }
          />
          <Route
            path="/mis-topicos"
            element={
              <ProtectedRoute>
                <Layout>
                  <MisTopicosPage />
                </Layout>
              </ProtectedRoute>
            }
          />
          <Route
            path="/perfil"
            element={
              <ProtectedRoute>
                <Layout>
                  <PerfilPage />
                </Layout>
              </ProtectedRoute>
            }
          />

          {/* Ruta 404 */}
          <Route
            path="*"
            element={
              <Layout>
                <Box
                  sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    justifyContent: 'center',
                    minHeight: '60vh',
                    textAlign: 'center',
                  }}
                >
                  <motion.div
                    initial={{ opacity: 0, scale: 0.8 }}
                    animate={{ opacity: 1, scale: 1 }}
                    transition={{ duration: 0.5 }}
                  >
                    <Box sx={{ fontSize: '6rem', mb: 2 }}>🔍</Box>
                    <Box sx={{ fontSize: '2rem', fontWeight: 'bold', mb: 1 }}>
                      Página no encontrada
                    </Box>
                    <Box sx={{ color: 'text.secondary', mb: 3 }}>
                      La página que buscas no existe o ha sido movida.
                    </Box>
                  </motion.div>
                </Box>
              </Layout>
            }
          />
        </Routes>
      </AnimatePresence>
    </Box>
  );
};

export default App;