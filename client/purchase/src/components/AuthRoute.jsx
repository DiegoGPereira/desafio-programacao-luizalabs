import React from 'react';
import { isAuthenticated } from '../utils/TokenUtils';

const AuthRoute = ({ children }) => {
  if (!isAuthenticated()) {
    alert('Login necessário para acessar esta página.');
    window.location.href = '/login';
  }

  return <>{children}</>;
};

export default AuthRoute;