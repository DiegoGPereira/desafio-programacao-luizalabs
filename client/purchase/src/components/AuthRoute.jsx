import React, { useEffect } from 'react';
import { isAuthenticated } from '../utils/TokenUtils';

const AuthRoute = ({ children }) => {
  useEffect(() => {
    if (!isAuthenticated()) {
      window.location.href = '/login';
    }
  }, []);

  return <>{children}</>;
};

export default AuthRoute;