import React, { useState, useEffect } from 'react';
import { Task } from '../types/task';

interface TaskFormProps {
  onSubmit: (task: Task) => void;
  task?: Task;
}

const TaskForm: React.FC<TaskFormProps> = ({ onSubmit, task }) => {
  const [title, setTitle] = useState(task ? task.title : '');
  const [description, setDescription] = useState(task ? task.description : '');
  const [status, setStatus] = useState(task ? task.status : false);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const newTask: Task = { id: task ? task.id : '', title, description, status };
    onSubmit(newTask);
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>{task ? 'Edit Task' : 'Add Task'}</h2>
      <div>
        <label>Title</label>
        <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} />
      </div>
      <div>
        <label>Description</label>
        <input type="text" value={description} onChange={(e) => setDescription(e.target.value)} />
      </div>
      <div>
        <label>Status</label>
        <input type="checkbox" checked={status} onChange={(e) => setStatus(e.target.checked)} />
      </div>
      <button type="submit">{task ? 'Update' : 'Add'}</button>
    </form>
  );
};

export default TaskForm;
