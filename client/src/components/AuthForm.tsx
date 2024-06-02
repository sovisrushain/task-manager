import React, { useState } from 'react';

interface AuthFormProps {
  onSubmit: (username: string, password: string) => void;
  title: string;
}

const AuthForm: React.FC<AuthFormProps> = ({ onSubmit, title }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(username, password);
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>{title}</h2>
      <div>
        <label>Username</label>
        <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
      </div>
      <div>
        <label>Password</label>
        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
      </div>
      <button type="submit">Submit</button>
    </form>
  );
};

export default AuthForm;
