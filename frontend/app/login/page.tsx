"use client"; // Obligatorio para usar hooks, formularios y eventos

import Link from "next/link";
import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { jwtDecode } from "jwt-decode"; // Importamos el decodificador

import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { useAuthStore } from "@/store/useAuthStore"; // Importamos el store
import api from "@/lib/api"; // Importamos el cliente API

// 1. Definimos el esquema de validación con Zod
const formSchema = z.object({
  email: z.string().email({ message: "Por favor ingrese un email válido." }),
  password: z.string().min(1, { message: "La contraseña es requerida." }),
});

// Interface para el payload del JWT (basado en JwtIssuer.java)
interface JwtPayload {
  sub: string; // Subject (email)
  scope: string; // Roles (ej. "ROLE_ADMIN")
  iat: number;
  exp: number;
}

export default function LoginPage() {
  const router = useRouter();
  const { setAuth } = useAuthStore(); // Obtenemos la acción de nuestro store

  // 2. Configuración del formulario
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  // 3. Lógica de envío (Punto 4.3.3)
  async function onSubmit(values: z.infer<typeof formSchema>) {
    try {
      // 3.1. Llamar al endpoint POST /api/auth/login
      const response = await api.post('/auth/login', values);
      const { jwt } = response.data;

      // 3.2. Decodificar el JWT
      const decodedToken = jwtDecode<JwtPayload>(jwt);
      const email = decodedToken.sub;
      // Extraemos el rol (ej. "ROLE_ADMIN" -> "ADMIN")
      const role = decodedToken.scope.replace("ROLE_", ""); 

      // 3.3. Guardar en el store de Zustand
      setAuth(jwt, email, role);

      // 3.4. Redirigir según el rol
      if (role === "ADMIN") {
        router.push("/admin/dashboard");
      } else if (role === "PACIENTE") {
        router.push("/dashboard");
      }

    } catch (error) {
      console.error("Error en el login:", error);
      // Aquí se podría añadir un toast o mensaje de error
      form.setError("root", { message: "Email o contraseña incorrectos." });
    }
  }

  // 4. Diseño del formulario (Punto 4.3.2)
  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <Card className="w-[400px]">
        <CardHeader>
          <CardTitle>Iniciar Sesión</CardTitle>
          <CardDescription>Bienvenido a Cita-Med.</CardDescription>
        </CardHeader>
        <CardContent>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
              <FormField
                control={form.control}
                name="email"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Email</FormLabel>
                    <FormControl>
                      <Input placeholder="usuario@mail.com" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="password"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Contraseña</FormLabel>
                    <FormControl>
                      <Input type="password" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              {form.formState.errors.root && (
                <p className="text-sm font-medium text-destructive">
                  {form.formState.errors.root.message}
                </p>
              )}
              <Button type="submit" className="w-full" disabled={form.formState.isSubmitting}>
                {form.formState.isSubmitting ? "Ingresando..." : "Ingresar"}
              </Button>
            </form>
          </Form>
        </CardContent>
        <CardFooter className="flex justify-center">
          <p className="text-sm text-gray-600">
            ¿No tienes cuenta?&nbsp;
            <Link href="/register" className="font-medium text-blue-600 hover:underline">
              Regístrate aquí
            </Link>
          </p>
        </CardFooter>
      </Card>
    </div>
  );
}