import ProtectedRoute from "@/components/auth/ProtectedRoute";

/**
 * (Punto 4.4.2)
 * Este es el Layout de Ruta para todas las páginas del paciente.
 * Envuelve a sus 'children' (las páginas del dashboard) con el 
 * componente de protección, exigiéndole el rol "PACIENTE".
 */
export default function DashboardLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <ProtectedRoute role="PACIENTE">
      {/* Aquí se puede añadir un Navbar o Sidebar 
        específico para el Paciente en la Fase 5 
      */}
      {children}
    </ProtectedRoute>
  );
}