import ProtectedRoute from "@/components/auth/ProtectedRoute";

/**
 * (Punto 4.4.1)
 * Este es el Layout de Ruta para todas las páginas de administrador.
 * Envuelve a sus 'children' (las páginas de admin) con el 
 * componente de protección, exigiéndole el rol "ADMIN".
 */
export default function AdminLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <ProtectedRoute role="ADMIN">
      {/* Aquí se puede añadir un Navbar o Sidebar 
        específico para el Admin en la Fase 5 
      */}
      {children}
    </ProtectedRoute>
  );
}