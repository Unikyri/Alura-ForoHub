import React, { useState } from 'react';
import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  Box,
  IconButton,
  Menu,
  MenuItem,
  Avatar,
  Divider,
  InputBase,
  alpha,
  useTheme,
  useMediaQuery,
  Drawer,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
} from '@mui/material';
import {
  Search as SearchIcon,
  AccountCircle,
  Add as AddIcon,
  Home as HomeIcon,
  Forum as ForumIcon,
  Person as PersonIcon,
  ExitToApp as LogoutIcon,
  Menu as MenuIcon,
  Close as CloseIcon,
} from '@mui/icons-material';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { motion } from 'framer-motion';
import toast from 'react-hot-toast';

import { useAuthStore } from '../../store/authStore';

const Navbar: React.FC = () => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('md'));
  const navigate = useNavigate();
  const location = useLocation();
  const { user, isAuthenticated, logout } = useAuthStore();

  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [mobileOpen, setMobileOpen] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');

  const handleProfileMenuOpen = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    logout();
    handleMenuClose();
    toast.success('Sesión cerrada exitosamente');
    navigate('/');
  };

  const handleSearch = (event: React.FormEvent) => {
    event.preventDefault();
    if (searchQuery.trim()) {
      navigate(`/topicos?search=${encodeURIComponent(searchQuery.trim())}`);
      setSearchQuery('');
    }
  };

  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen);
  };

  const menuItems = [
    { text: 'Inicio', icon: <HomeIcon />, path: '/' },
    { text: 'Tópicos', icon: <ForumIcon />, path: '/topicos' },
  ];

  const authenticatedMenuItems = [
    { text: 'Crear Tópico', icon: <AddIcon />, path: '/crear-topico' },
    { text: 'Mis Tópicos', icon: <ForumIcon />, path: '/mis-topicos' },
    { text: 'Mi Perfil', icon: <PersonIcon />, path: '/perfil' },
  ];

  const drawer = (
    <Box sx={{ width: 250 }}>
      <Box sx={{ p: 2, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <Typography variant="h6" sx={{ fontWeight: 'bold', color: 'primary.main' }}>
          ForoHub
        </Typography>
        <IconButton onClick={handleDrawerToggle}>
          <CloseIcon />
        </IconButton>
      </Box>
      <Divider />
      <List>
        {menuItems.map((item) => (
          <ListItem
            key={item.text}
            component={Link}
            to={item.path}
            onClick={handleDrawerToggle}
            sx={{
              color: location.pathname === item.path ? 'primary.main' : 'text.primary',
              bgcolor: location.pathname === item.path ? alpha(theme.palette.primary.main, 0.1) : 'transparent',
            }}
          >
            <ListItemIcon sx={{ color: 'inherit' }}>
              {item.icon}
            </ListItemIcon>
            <ListItemText primary={item.text} />
          </ListItem>
        ))}
        {isAuthenticated && (
          <>
            <Divider sx={{ my: 1 }} />
            {authenticatedMenuItems.map((item) => (
              <ListItem
                key={item.text}
                component={Link}
                to={item.path}
                onClick={handleDrawerToggle}
                sx={{
                  color: location.pathname === item.path ? 'primary.main' : 'text.primary',
                  bgcolor: location.pathname === item.path ? alpha(theme.palette.primary.main, 0.1) : 'transparent',
                }}
              >
                <ListItemIcon sx={{ color: 'inherit' }}>
                  {item.icon}
                </ListItemIcon>
                <ListItemText primary={item.text} />
              </ListItem>
            ))}
            <Divider sx={{ my: 1 }} />
            <ListItem onClick={handleLogout} sx={{ color: 'error.main' }}>
              <ListItemIcon sx={{ color: 'inherit' }}>
                <LogoutIcon />
              </ListItemIcon>
              <ListItemText primary="Cerrar Sesión" />
            </ListItem>
          </>
        )}
      </List>
    </Box>
  );

  return (
    <>
      <AppBar position="sticky" elevation={0}>
        <Toolbar>
          {isMobile && (
            <IconButton
              color="inherit"
              aria-label="open drawer"
              edge="start"
              onClick={handleDrawerToggle}
              sx={{ mr: 2 }}
            >
              <MenuIcon />
            </IconButton>
          )}

          {/* Logo */}
          <motion.div
            whileHover={{ scale: 1.05 }}
            whileTap={{ scale: 0.95 }}
          >
            <Typography
              variant="h6"
              component={Link}
              to="/"
              sx={{
                fontWeight: 'bold',
                textDecoration: 'none',
                color: 'inherit',
                mr: 4,
                background: 'linear-gradient(45deg, #1976d2, #42a5f5)',
                backgroundClip: 'text',
                WebkitBackgroundClip: 'text',
                WebkitTextFillColor: 'transparent',
              }}
            >
              ForoHub
            </Typography>
          </motion.div>

          {/* Navigation Links - Desktop */}
          {!isMobile && (
            <Box sx={{ display: 'flex', gap: 2, mr: 'auto' }}>
              {menuItems.map((item) => (
                <Button
                  key={item.text}
                  component={Link}
                  to={item.path}
                  color="inherit"
                  startIcon={item.icon}
                  sx={{
                    color: location.pathname === item.path ? 'primary.main' : 'text.primary',
                    fontWeight: location.pathname === item.path ? 600 : 400,
                    '&:hover': {
                      bgcolor: alpha(theme.palette.primary.main, 0.1),
                    },
                  }}
                >
                  {item.text}
                </Button>
              ))}
            </Box>
          )}

          <Box sx={{ flexGrow: 1 }} />

          {/* Search Bar */}
          {!isMobile && (
            <Box
              component="form"
              onSubmit={handleSearch}
              sx={{
                position: 'relative',
                borderRadius: 2,
                backgroundColor: alpha(theme.palette.common.white, 0.15),
                '&:hover': {
                  backgroundColor: alpha(theme.palette.common.white, 0.25),
                },
                marginRight: 2,
                width: '300px',
              }}
            >
              <Box
                sx={{
                  padding: theme.spacing(0, 2),
                  height: '100%',
                  position: 'absolute',
                  pointerEvents: 'none',
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                }}
              >
                <SearchIcon />
              </Box>
              <InputBase
                placeholder="Buscar tópicos..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                sx={{
                  color: 'inherit',
                  width: '100%',
                  '& .MuiInputBase-input': {
                    padding: theme.spacing(1, 1, 1, 0),
                    paddingLeft: `calc(1em + ${theme.spacing(4)})`,
                  },
                }}
              />
            </Box>
          )}

          {/* Auth Buttons */}
          {isAuthenticated ? (
            <>
              {!isMobile && (
                <Button
                  component={Link}
                  to="/crear-topico"
                  variant="contained"
                  startIcon={<AddIcon />}
                  sx={{ mr: 2 }}
                >
                  Crear Tópico
                </Button>
              )}
              <IconButton
                size="large"
                edge="end"
                aria-label="account of current user"
                aria-haspopup="true"
                onClick={handleProfileMenuOpen}
                color="inherit"
              >
                <Avatar sx={{ width: 32, height: 32, bgcolor: 'primary.main' }}>
                  {user?.nombre?.charAt(0).toUpperCase()}
                </Avatar>
              </IconButton>
            </>
          ) : (
            <Box sx={{ display: 'flex', gap: 1 }}>
              <Button
                component={Link}
                to="/login"
                color="inherit"
                variant="outlined"
                sx={{ borderColor: 'currentColor' }}
              >
                Iniciar Sesión
              </Button>
              <Button
                component={Link}
                to="/register"
                variant="contained"
                sx={{ bgcolor: 'primary.main', color: 'white' }}
              >
                Registrarse
              </Button>
            </Box>
          )}
        </Toolbar>
      </AppBar>

      {/* Profile Menu */}
      <Menu
        anchorEl={anchorEl}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        keepMounted
        transformOrigin={{
          vertical: 'top',
          horizontal: 'right',
        }}
        open={Boolean(anchorEl)}
        onClose={handleMenuClose}
        PaperProps={{
          sx: {
            mt: 1,
            minWidth: 200,
            borderRadius: 2,
            boxShadow: '0px 8px 30px rgba(0, 0, 0, 0.12)',
          },
        }}
      >
        <Box sx={{ px: 2, py: 1 }}>
          <Typography variant="subtitle2" sx={{ fontWeight: 600 }}>
            {user?.nombre}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            {user?.correoElectronico}
          </Typography>
        </Box>
        <Divider />
        <MenuItem component={Link} to="/mis-topicos" onClick={handleMenuClose}>
          <ForumIcon sx={{ mr: 2 }} />
          Mis Tópicos
        </MenuItem>
        <MenuItem component={Link} to="/perfil" onClick={handleMenuClose}>
          <PersonIcon sx={{ mr: 2 }} />
          Mi Perfil
        </MenuItem>
        <Divider />
        <MenuItem onClick={handleLogout} sx={{ color: 'error.main' }}>
          <LogoutIcon sx={{ mr: 2 }} />
          Cerrar Sesión
        </MenuItem>
      </Menu>

      {/* Mobile Drawer */}
      <Drawer
        variant="temporary"
        open={mobileOpen}
        onClose={handleDrawerToggle}
        ModalProps={{
          keepMounted: true,
        }}
        sx={{
          display: { xs: 'block', md: 'none' },
          '& .MuiDrawer-paper': { boxSizing: 'border-box', width: 250 },
        }}
      >
        {drawer}
      </Drawer>
    </>
  );
};

export default Navbar;