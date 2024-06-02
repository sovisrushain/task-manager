import React from 'react';
import { useNavigate } from 'react-router-dom';
import AuthForm from '../components/AuthForm';
import { register } from '../services/authService';

const Register: React.FC = () => {
  const navigate = useNavigate();

  const handleRegister = async (username: string, password: string) => {
    try {
      await register(username, password);
      navigate('/login');
    } catch (error) {
      console.error('Registration failed', error);
    }
  };

  return <AuthForm onSubmit={handleRegister} title="Register" />;
};

export default Register;
