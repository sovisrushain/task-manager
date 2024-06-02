import axios from 'axios';

const API_URL = 'http://localhost:8082/api/tasks';

export const getTasks = async (token: string) => {
  const response = await axios.get(API_URL, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
  return response.data;
};

export const addTask = async (token: string, task: { title: string; description: string; status: boolean }) => {
  const response = await axios.post(API_URL, task, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
  return response.data;
};

export const updateTask = async (token: string, id: string, task: { title: string; description: string; status: boolean }) => {
  const response = await axios.put(`${API_URL}/${id}`, task, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
  return response.data;
};

export const deleteTask = async (token: string, id: string) => {
  const response = await axios.delete(`${API_URL}/${id}`, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
  return response.data;
};
