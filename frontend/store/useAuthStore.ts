import { create } from 'zustand';
import { persist, createJSONStorage } from 'zustand/middleware';

// 1. Definimos la interfaz del estado y las acciones
interface AuthState {
  token: string | null;
  email: string | null;
  role: string | null;
  isAuthenticated: boolean;
  setAuth: (token: string, email: string, role: string) => void;
  clearAuth: () => void;
}

// 2. Creamos el store (Punto 4.2.2)
export const useAuthStore = create(
  // 3. Usamos el middleware 'persist' para guardar en localStorage
  persist<AuthState>(
    (set) => ({
      // --- Estado Inicial ---
      token: null,
      email: null,
      role: null,
      isAuthenticated: false,

      // --- Acciones (Mutadores) ---

      /**
       * Guarda el token, email y rol en el estado al iniciar sesión.
       */
      setAuth: (token: string, email: string, role: string) => {
        set({
          token,
          email,
          role,
          isAuthenticated: true,
        });
      },

      /**
       * Limpia el estado al cerrar sesión.
       */
      clearAuth: () => {
        set({
          token: null,
          email: null,
          role: null,
          isAuthenticated: false,
        });
      },
    }),
    {
      // --- Configuración de Persistencia ---
      name: 'auth-storage', // Nombre de la clave en localStorage
      storage: createJSONStorage(() => localStorage), // Usar localStorage
    }
  )
);