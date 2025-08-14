import React, { useState } from 'react';
import {
  Box,
  Container,
  Typography,
  Grid,
  Card,
  CardContent,
  Chip,
  Avatar,
  Button,
  TextField,
  InputAdornment,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  Pagination,
  CircularProgress,
  Alert,
} from '@mui/material';
import {
  Search as SearchIcon,
  Add as AddIcon,
  AccessTime,
  QuestionAnswer,
  CheckCircle,
  Lock,
} from '@mui/icons-material';
import { Link as RouterLink, useSearchParams } from 'react-router-dom';
import { motion } from 'framer-motion';
import { useQuery } from 'react-query';

import { topicoService, Topico } from '../services/topicoService';
import { cursoService } from '../services/cursoService';
import { useAuthStore } from '../store/authStore';

const TopicosPage: React.FC = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const { isAuthenticated } = useAuthStore();
  
  const [page, setPage] = useState(0);
  const [searchQuery, setSearchQuery] = useState(searchParams.get('search') || '');
  const [selectedCategory, setSelectedCategory] = useState('');

  const { data: topicosData, isLoading, error } = useQuery(
    ['topicos', page, searchQuery],
    () => {
      if (searchQuery) {
        return topicoService.buscarTopicos(searchQuery, page);
      }
      return topicoService.getTopicos(page);
    },
    {
      keepPreviousData: true,
    }
  );

  const { data: categorias } = useQuery('categorias', cursoService.getCategorias);

  const handleSearch = (event: React.FormEvent) => {
    event.preventDefault();
    setPage(0);
    if (searchQuery) {
      setSearchParams({ search: searchQuery });
    } else {
      setSearchParams({});
    }
  };

  const handlePageChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value - 1);
  };

  const getStatusIcon = (status: string) => {
    switch (status) {
      case 'RESUELTO':
        return <CheckCircle sx={{ color: 'success.main', fontSize: 20 }} />;
      case 'CERRADO':
        return <Lock sx={{ color: 'grey.500', fontSize: 20 }} />;
      default:
        return <QuestionAnswer sx={{ color: 'primary.main', fontSize: 20 }} />;
    }
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

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    const now = new Date();
    const diffInHours = Math.floor((now.getTime() - date.getTime()) / (1000 * 60 * 60));
    
    if (diffInHours < 1) return 'Hace menos de 1 hora';
    if (diffInHours < 24) return `Hace ${diffInHours} horas`;
    if (diffInHours < 48) return 'Hace 1 día';
    return `Hace ${Math.floor(diffInHours / 24)} días`;
  };

  return (
    <Container maxWidth="lg">
      {/* Header */}
      <Box sx={{ mb: 4 }}>
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.6 }}
        >
          <Box
            sx={{
              display: 'flex',
              justifyContent: 'space-between',
              alignItems: { xs: 'flex-start', md: 'center' },
              flexDirection: { xs: 'column', md: 'row' },
              gap: 2,
              mb: 4,
            }}
          >
            <Box>
              <Typography
                variant="h3"
                sx={{ fontWeight: 'bold', mb: 1, color: 'text.primary' }}
              >
                Tópicos de la Comunidad
              </Typography>
              <Typography variant="h6" color="text.secondary">
                Explora las discusiones más recientes y encuentra respuestas a tus dudas
              </Typography>
            </Box>
            {isAuthenticated && (
              <Button
                component={RouterLink}
                to="/crear-topico"
                variant="contained"
                startIcon={<AddIcon />}
                size="large"
                sx={{
                  px: 3,
                  py: 1.5,
                  borderRadius: 3,
                  '&:hover': {
                    transform: 'translateY(-2px)',
                  },
                }}
              >
                Crear Tópico
              </Button>
            )}
          </Box>
        </motion.div>

        {/* Search and Filters */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.6, delay: 0.1 }}
        >
          <Card sx={{ p: 3, mb: 4, borderRadius: 3 }}>
            <Box
              component="form"
              onSubmit={handleSearch}
              sx={{
                display: 'flex',
                gap: 2,
                flexDirection: { xs: 'column', md: 'row' },
                alignItems: 'center',
              }}
            >
              <TextField
                fullWidth
                placeholder="Buscar tópicos..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <SearchIcon />
                    </InputAdornment>
                  ),
                }}
                sx={{ flex: 1 }}
              />
              <FormControl sx={{ minWidth: 200 }}>
                <InputLabel>Categoría</InputLabel>
                <Select
                  value={selectedCategory}
                  label="Categoría"
                  onChange={(e) => setSelectedCategory(e.target.value)}
                >
                  <MenuItem value="">Todas las categorías</MenuItem>
                  {categorias?.map((categoria) => (
                    <MenuItem key={categoria} value={categoria}>
                      {categoria}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
              <Button
                type="submit"
                variant="contained"
                sx={{ px: 4, py: 1.5 }}
              >
                Buscar
              </Button>
            </Box>
          </Card>
        </motion.div>
      </Box>

      {/* Content */}
      {isLoading ? (
        <Box sx={{ display: 'flex', justifyContent: 'center', py: 8 }}>
          <CircularProgress size={48} />
        </Box>
      ) : error ? (
        <Alert severity="error" sx={{ mb: 4 }}>
          Error al cargar los tópicos. Por favor, inténtalo de nuevo.
        </Alert>
      ) : (
        <>
          {/* Results Info */}
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ duration: 0.6, delay: 0.2 }}
          >
            <Box sx={{ mb: 3 }}>
              <Typography variant="body1" color="text.secondary">
                {topicosData?.totalElements || 0} tópicos encontrados
                {searchQuery && ` para "${searchQuery}"`}
              </Typography>
            </Box>
          </motion.div>

          {/* Topics List */}
          <Grid container spacing={3}>
            {topicosData?.content.map((topico: Topico, index: number) => (
              <Grid item xs={12} key={topico.id}>
                <motion.div
                  initial={{ opacity: 0, y: 20 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ duration: 0.6, delay: index * 0.1 }}
                >
                  <Card
                    component={RouterLink}
                    to={`/topicos/${topico.id}`}
                    sx={{
                      textDecoration: 'none',
                      borderRadius: 3,
                      border: '1px solid',
                      borderColor: 'divider',
                      '&:hover': {
                        transform: 'translateY(-2px)',
                        boxShadow: (theme) => theme.shadows[8],
                        borderColor: 'primary.main',
                      },
                      transition: 'all 0.3s ease',
                    }}
                  >
                    <CardContent sx={{ p: 3 }}>
                      <Box
                        sx={{
                          display: 'flex',
                          justifyContent: 'space-between',
                          alignItems: 'flex-start',
                          mb: 2,
                        }}
                      >
                        <Box sx={{ flex: 1, mr: 2 }}>
                          <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
                            {getStatusIcon(topico.status)}
                            <Typography
                              variant="h6"
                              sx={{
                                fontWeight: 'bold',
                                ml: 1,
                                color: 'text.primary',
                                lineHeight: 1.3,
                              }}
                            >
                              {topico.titulo}
                            </Typography>
                          </Box>
                          <Typography
                            variant="body2"
                            color="text.secondary"
                            sx={{
                              mb: 2,
                              display: '-webkit-box',
                              WebkitLineClamp: 2,
                              WebkitBoxOrient: 'vertical',
                              overflow: 'hidden',
                              lineHeight: 1.5,
                            }}
                          >
                            {topico.mensaje}
                          </Typography>
                          <Box
                            sx={{
                              display: 'flex',
                              alignItems: 'center',
                              gap: 2,
                              flexWrap: 'wrap',
                            }}
                          >
                            <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                              <Avatar sx={{ width: 24, height: 24, fontSize: '0.75rem' }}>
                                {topico.autorNombre.charAt(0).toUpperCase()}
                              </Avatar>
                              <Typography variant="body2" color="text.secondary">
                                {topico.autorNombre}
                              </Typography>
                            </Box>
                            <Box sx={{ display: 'flex', alignItems: 'center', gap: 0.5 }}>
                              <AccessTime sx={{ fontSize: 16, color: 'text.secondary' }} />
                              <Typography variant="body2" color="text.secondary">
                                {formatDate(topico.fechaCreacion)}
                              </Typography>
                            </Box>
                            <Chip
                              label={topico.cursoNombre}
                              size="small"
                              sx={{
                                bgcolor: 'primary.50',
                                color: 'primary.main',
                                fontWeight: 500,
                              }}
                            />
                          </Box>
                        </Box>
                        <Box sx={{ textAlign: 'right' }}>
                          <Chip
                            label={topico.status}
                            color={getStatusColor(topico.status) as any}
                            size="small"
                            sx={{ mb: 1 }}
                          />
                          <Typography
                            variant="body2"
                            color="text.secondary"
                            sx={{ display: 'flex', alignItems: 'center', gap: 0.5 }}
                          >
                            <QuestionAnswer sx={{ fontSize: 16 }} />
                            {topico.totalRespuestas} respuestas
                          </Typography>
                        </Box>
                      </Box>
                    </CardContent>
                  </Card>
                </motion.div>
              </Grid>
            ))}
          </Grid>

          {/* Pagination */}
          {topicosData && topicosData.totalPages > 1 && (
            <motion.div
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              transition={{ duration: 0.6, delay: 0.3 }}
            >
              <Box sx={{ display: 'flex', justifyContent: 'center', mt: 6 }}>
                <Pagination
                  count={topicosData.totalPages}
                  page={page + 1}
                  onChange={handlePageChange}
                  color="primary"
                  size="large"
                  sx={{
                    '& .MuiPaginationItem-root': {
                      borderRadius: 2,
                    },
                  }}
                />
              </Box>
            </motion.div>
          )}

          {/* Empty State */}
          {topicosData?.content.length === 0 && (
            <motion.div
              initial={{ opacity: 0, scale: 0.8 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ duration: 0.6 }}
            >
              <Box
                sx={{
                  textAlign: 'center',
                  py: 8,
                }}
              >
                <Box sx={{ fontSize: '4rem', mb: 2 }}>🔍</Box>
                <Typography variant="h5" sx={{ fontWeight: 'bold', mb: 2 }}>
                  No se encontraron tópicos
                </Typography>
                <Typography variant="body1" color="text.secondary" sx={{ mb: 4 }}>
                  {searchQuery
                    ? `No hay tópicos que coincidan con "${searchQuery}"`
                    : 'Aún no hay tópicos en esta categoría'}
                </Typography>
                {isAuthenticated && (
                  <Button
                    component={RouterLink}
                    to="/crear-topico"
                    variant="contained"
                    startIcon={<AddIcon />}
                    size="large"
                  >
                    Crear el Primer Tópico
                  </Button>
                )}
              </Box>
            </motion.div>
          )}
        </>
      )}
    </Container>
  );
};

export default TopicosPage;