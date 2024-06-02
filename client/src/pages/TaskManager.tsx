import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import TaskForm from '../components/TaskForm';
import TaskList from '../components/TaskList';
import { getTasks, addTask, updateTask, deleteTask } from '../services/taskService';
import { Task } from '../types/task';

const TaskManager: React.FC = () => {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [editingTask, setEditingTask] = useState<Task | null>(
    null);
    const navigate = useNavigate();
  
    useEffect(() => {
      const fetchTasks = async () => {
        try {
          const token = localStorage.getItem('token');
          if (!token) {
            navigate('/login');
            return;
          }
          const data = await getTasks(token);
          setTasks(data);
        } catch (error) {
          console.error('Failed to fetch tasks', error);
        }
      };
  
      fetchTasks();
    }, [navigate]);
  
    const handleAddTask = async (task: Task) => {
      try {
        const token = localStorage.getItem('token');
        if (!token) return;
  
        const newTask = await addTask(token, task);
        setTasks([...tasks, newTask]);
      } catch (error) {
        console.error('Failed to add task', error);
      }
    };
  
    const handleUpdateTask = async (task: Task) => {
      try {
        const token = localStorage.getItem('token');
        if (!token) return;
  
        const updatedTask = await updateTask(token, task.id, task);
        setTasks(tasks.map(t => (t.id === task.id ? updatedTask : t)));
        setEditingTask(null);
      } catch (error) {
        console.error('Failed to update task', error);
      }
    };
  
    const handleDeleteTask = async (id: string) => {
      try {
        const token = localStorage.getItem('token');
        if (!token) return;
  
        await deleteTask(token, id);
        setTasks(tasks.filter(task => task.id !== id));
      } catch (error) {
        console.error('Failed to delete task', error);
      }
    };
  
    const handleEditTask = (task: Task) => {
      setEditingTask(task);
    };
  
    const handleLogout = () => {
      localStorage.removeItem('token');
      navigate('/login');
    };
  
    return (
      <div>
        <button onClick={handleLogout}>Logout</button>
        {editingTask ? (
          <TaskForm onSubmit={handleUpdateTask} task={editingTask} />
        ) : (
          <TaskForm onSubmit={handleAddTask} />
        )}
        <TaskList tasks={tasks} onEdit={handleEditTask} onDelete={handleDeleteTask} />
      </div>
    );
  };
  
  export default TaskManager;