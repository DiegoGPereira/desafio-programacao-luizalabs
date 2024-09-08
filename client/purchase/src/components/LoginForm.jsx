import React, { useState } from 'react';
import axios from 'axios';
import { setToken } from '../utils/TokenUtils';

const LoginForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [status, setStatus] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/v1/auth/login', {
                username,
                password
            });
            const token = response.data.jwt;
            setToken(token)
            window.location.href = '/';
        } catch (error) {
            if (error.response) {
                setStatus(`Login falhou: ${error.response.data.message || error.response.data}`);
            } else if (error.request) {
                setStatus('Login falhou: Sem resposta do servidor ' + error.message);
            } else {
                setStatus('Login falhou: ' + error.message);
            }
        }
    };

    return (
        <form onSubmit={handleSubmit} className="max-w-md mx-auto">
            <input
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                placeholder="Username"
                className="w-full p-2 mb-4 border rounded"
            />
            <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Password"
                className="w-full p-2 mb-4 border rounded"
            />
            <button type="submit" className="w-full p-2 bg-blue-500 text-white rounded">
                Login
            </button>
            {status && <p className="mt-4 text-sm text-red-600 font-bold">{status}</p>}
        </form>
    );
};

export default LoginForm;
