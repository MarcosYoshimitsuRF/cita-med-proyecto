"use client";

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { useAuthStore } from '@/store/useAuthStore';
import { Skeleton } from '@/components/ui/skeleton';

// 1. Definimos los props que recibirá el componente
interface ProtectedRouteProps {
  children: React.ReactNode;
  role: "ADMIN" | "PACIENTE";
}

export default function ProtectedRoute({ children, role }: ProtectedRouteProps) {
  const router = useRouter();
  const { isAuthenticated, role: userRole } = useAuthStore();

  // 2. Estado de Carga
  const [isVerifying, setIsVerifying] = useState(true);

  useEffect(() => {
    // 3. Lógica de Verificación
    const verifyAccess = async () => {
      if (!isAuthenticated || userRole !== role) {
        // Si no está autenticado o el rol no coincide, redirigimos
        router.push('/login');
      } else {
        // Si está autenticado y el rol coincide, dejamos de verificar
        setIsVerifying(false);
      }
    };

    verifyAccess();
  }, [isAuthenticated, userRole, role, router]);

  // 4. Estado de Carga
  if (isVerifying) {
    return (
      <div className="flex flex-col space-y-3 p-4">
        <Skeleton className="h-[20px] w-[250px] rounded-lg" />
        <Skeleton className="h-4 w-[200px]" />
      </div>
    );
  }

  // 5. Si la verificación es exitosa, muestra el contenido
  return <>{children}</>;
}