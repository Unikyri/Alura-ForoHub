import React from 'react';
import { Box, Container } from '@mui/material';
import { Outlet } from 'react-router-dom';

import Navbar from './Navbar';
import Footer from './Footer';

interface LayoutProps {
  children?: React.ReactNode;
}

const Layout: React.FC<LayoutProps> = ({ children }) => {
  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <Navbar />
      
      <Box component="main" sx={{ flex: 1, py: 3 }}>
        <Container maxWidth="lg">
          {children || <Outlet />}
        </Container>
      </Box>
      
      <Footer />
    </Box>
  );
};

export default Layout;