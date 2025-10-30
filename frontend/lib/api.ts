import axios from 'axios';
import { useAuthStore } from '../store/useAuthStore';

// 1. Definimos la URL base de nuestro backend Spring Boot
const API_BASE_URL = 'http://localhost:8080/api';

// 2. Creamos la instancia de axios
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 3. Configuramos el interceptor de peticiones
api.interceptors.request.use(
  (config) => {
    // CORRECCIÓN: Usamos el store importado directamente
    // No se necesita 'require()'
    const token = useAuthStore.getState().token;

    if (token && config.url !== '/auth/login' && config.url !== '/auth/register') {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;