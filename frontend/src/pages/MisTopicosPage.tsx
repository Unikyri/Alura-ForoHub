import React, { useState } from 'react';
import {
  Box,
  Container,
  Typography,
  Grid,
  Card,
  CardContent,
  Chip,
  Button,
  Pagination,
  CircularProgress,
  Alert,
  Tabs,
  Tab,
} from '@mui/material';
import {
  Add as AddIcon,
  Edit,
  Delete,
  Visibility,
  CheckCircle,
  QuestionAnswer,
  Lock,
  AccessTime,
} from '@mui/icons-material';
import { Link as RouterLink } from 'react-router-dom';
import { motion } from 'framer-motion';
import { useQuery } from 'react-query';
import toast from 'react-hot-toast';

import { topicoService, Topico } from '../services/topicoService';

interface TabPanelProps {
  children?: React.ReactNode;
  index: number;
  value: number;
}

function TabPanel(props: TabPanelProps) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && <Box sx={{ py: 3 }}>{children}</Box>}
    </div>
  );
}

const MisTopicosPage: React.FC = () => {
  const [page, setPage] = useState(0);
  const [tabValue, setTabValue] = useState(0);

  const { data: topicosData, isLoading, error, refetch } = useQuery(
    ['mis-topicos', page],
    () => topicoService.getMisTopicos(page),
    {
      keepPreviousData: true,
    }
  );

  const handleDelete = async (id: number, titulo: string) => {
    if (!window.confirm(`¿Estás seguro de que quieres eliminar "${titulo}"?`)) {
      return;
    }

    try {
      await topicoService.eliminarTopico(id);
      toast.success('Tópico eliminado exitosamente');
      refetch();
    } catch (error) {
      toast.error('Error al eliminar el tópico');
    }
  };

  const handlePageChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value - 1);
  };

  const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
    setTabValue(newValue);
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
    return new Date(dateString).toLocaleDateString('es-ES', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
    });
  };

  const filterTopicos = (topicos: Topico[], status?: string) => {
    if (!status) return topicos;
    return topicos.filter(topico => topico.status === status);
  };

  const allTopicos = topicosData?.content || [];
  const abiertos = filterTopicos(allTopicos, 'ABIERTO');
  const resueltos = filterTopicos(allTopicos, 'RESUELTO');
  const cerrados = filterTopicos(allTopicos, 'CERRADO');

  const getTopicosForTab = (tabIndex: number) => {
    switch (tabIndex) {
      case 1: return abiertos;
      case 2: return resueltos;
      case 3: return cerrados;
      default: return allTopicos;
    }
  };

  const renderTopicoCard = (topico: Topico, index: number) => (
    <Grid item xs={12} key={topico.id}>
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6, delay: index * 0.1 }}
      >
        <Card
          sx={{
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
              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 1, alignItems: 'flex-end' }}>
                <Chip
                  label={topico.status}
                  color={getStatusColor(topico.status) as any}
                  size="small"
                />
                <Box sx={{ display: 'flex', gap: 1 }}>
                  <Button
                    component={RouterLink}
                    to={`/topicos/${topico.id}`}
                    variant="outlined"
                    size="small"
                    startIcon={<Visibility />}
                  >
                    Ver
                  </Button>
                  <Button
                    variant="outlined"
                    size="small"
                    startIcon={<Edit />}
                    disabled
                  >
                    Editar
                  </Button>
                  <Button
                    variant="outlined"
                    color="error"
                    size="small"
                    startIcon={<Delete />}
                    onClick={() => handleDelete(topico.id, topico.titulo)}
                  >
                    Eliminar
                  </Button>
                </Box>
              </Box>
            </Box>
          </CardContent>
        </Card>
      </motion.div>
    </Grid>
  );

  return (
    <Container maxWidth="lg">
      {/* Header */}
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
              Mis Tópicos
            </Typography>
            <Typography variant="h6" color="text.secondary">
              Gestiona todos los tópicos que has creado
            </Typography>
          </Box>
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
        </Box>
      </motion.div>

      {/* Tabs */}
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6, delay: 0.1 }}
      >
        <Box sx={{ borderBottom: 1, borderColor: 'divider', mb: 3 }}>
          <Tabs value={tabValue} onChange={handleTabChange}>
            <Tab label={`Todos (${allTopicos.length})`} />
            <Tab label={`Abiertos (${abiertos.length})`} />
            <Tab label={`Resueltos (${resueltos.length})`} />
            <Tab label={`Cerrados (${cerrados.length})`} />
          </Tabs>
        </Box>
      </motion.div>

      {/* Content */}
      {isLoading ? (
        <Box sx={{ display: 'flex', justifyContent: 'center', py: 8 }}>
          <CircularProgress size={48} />
        </Box>
      ) : error ? (
        <Alert severity="error" sx={{ mb: 4 }}>
          Error al cargar tus tópicos. Por favor, inténtalo de nuevo.
        </Alert>
      ) : (
        <>
          {[0, 1, 2, 3].map((tabIndex) => (
            <TabPanel key={tabIndex} value={tabValue} index={tabIndex}>
              <Grid container spacing={3}>
                {getTopicosForTab(tabIndex).map((topico, index) =>
                  renderTopicoCard(topico, index)
                )}
              </Grid>

              {/* Empty State */}
              {getTopicosForTab(tabIndex).length === 0 && (
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
                    <Box sx={{ fontSize: '4rem', mb: 2 }}>
                      {tabIndex === 0 ? '📝' : tabIndex === 1 ? '❓' : tabIndex === 2 ? '✅' : '🔒'}
                    </Box>
                    <Typography variant="h5" sx={{ fontWeight: 'bold', mb: 2 }}>
                      {tabIndex === 0 && 'No has creado tópicos aún'}
                      {tabIndex === 1 && 'No tienes tópicos abiertos'}
                      {tabIndex === 2 && 'No tienes tópicos resueltos'}
                      {tabIndex === 3 && 'No tienes tópicos cerrados'}
                    </Typography>
                    <Typography variant="body1" color="text.secondary" sx={{ mb: 4 }}>
                      {tabIndex === 0 && 'Crea tu primer tópico y comienza a participar en la comunidad'}
                      {tabIndex === 1 && 'Los tópicos abiertos aparecerán aquí'}
                      {tabIndex === 2 && 'Los tópicos con solución aparecerán aquí'}
                      {tabIndex === 3 && 'Los tópicos cerrados aparecerán aquí'}
                    </Typography>
                    {tabIndex === 0 && (
                      <Button
                        component={RouterLink}
                        to="/crear-topico"
                        variant="contained"
                        startIcon={<AddIcon />}
                        size="large"
                      >
                        Crear Mi Primer Tópico
                      </Button>
                    )}
                  </Box>
                </motion.div>
              )}
            </TabPanel>
          ))}

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
        </>
      )}
    </Container>
  );
};

export default MisTopicosPage;