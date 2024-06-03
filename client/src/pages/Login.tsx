import React from 'react';
import { useNavigate } from 'react-router-dom';
import AuthForm from '../components/AuthForm';
import { login } from '../services/authService';

const Login: React.FC = () => {
  const navigate = useNavigate();

  const handleLogin = async (username: string, password: string) => {
    try {
      const data = await login(username, password);
      localStorage.setItem('token', data.accessToken);
      navigate('/tasks');
    } catch (error) {
      console.error('Login failed', error);
    }
  };

  return <AuthForm onSubmit={handleLogin} title="Login" />;
};

export default Login;
