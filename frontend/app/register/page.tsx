"use client"; // Obligatorio para usar hooks, formularios y eventos

import Link from "next/link";
import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";

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
import api from "@/lib/api"; // Importamos el cliente API

// 1. Definimos el esquema de validación con Zod
// Coincide con el DTO 'RegisterRequest' y el SP 'sp_RegistrarPaciente'
const formSchema = z.object({
  nombres: z.string().min(1, { message: "El nombre es requerido." }),
  apellidos: z.string().min(1, { message: "El apellido es requerido." }),
  dni: z.string().length(8, { message: "El DNI debe tener 8 dígitos." }),
  telefono: z.string().min(1, { message: "El teléfono es requerido." }),
  email: z.string().email({ message: "Por favor ingrese un email válido." }),
  password: z.string().min(6, { message: "La contraseña debe tener al menos 6 caracteres." }),
});

export default function RegisterPage() {
  const router = useRouter();

  // 2. Configuración del formulario
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      nombres: "",
      apellidos: "",
      dni: "",
      telefono: "",
      email: "",
      password: "",
    },
  });

  // 3. Lógica de envío (Punto 4.3.4)
  async function onSubmit(values: z.infer<typeof formSchema>) {
    try {
      // 3.1. Llamar al endpoint POST /api/auth/register
      await api.post('/auth/register', values);

      // 3.2. Si el registro es exitoso, redirigir al Login
      // Opcional: Mostrar un toast de "¡Registro exitoso!"
      router.push("/login");

    } catch (error) {
      console.error("Error en el registro:", error);
      // Asumimos que el error es por email o DNI duplicado
      form.setError("root", { message: "Este email o DNI ya está en uso." });
    }
  }

  // 4. Diseño del formulario (usando Shadcn)
  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100 py-12">
      <Card className="w-[450px]">
        <CardHeader>
          <CardTitle>Crear una cuenta</CardTitle>
          <CardDescription>Ingrese sus datos para registrarse en Cita-Med.</CardDescription>
        </CardHeader>
        <CardContent>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
              
              {/* Nombres y Apellidos en una fila */}
              <div className="grid grid-cols-2 gap-4">
                <FormField
                  control={form.control}
                  name="nombres"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Nombres</FormLabel>
                      <FormControl>
                        <Input placeholder="Juan" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="apellidos"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Apellidos</FormLabel>
                      <FormControl>
                        <Input placeholder="Pérez" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>

              {/* DNI y Teléfono en una fila */}
              <div className="grid grid-cols-2 gap-4">
                <FormField
                  control={form.control}
                  name="dni"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>DNI</FormLabel>
                      <FormControl>
                        <Input placeholder="12345678" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="telefono"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Teléfono</FormLabel>
                      <FormControl>
                        <Input placeholder="987654321" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>

              {/* Email */}
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

              {/* Password */}
              <FormField
                control={form.control}
                name="password"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Contraseña</FormLabel>
                    <FormControl>
                      <Input type="password" placeholder="••••••••" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              {/* Contenedor para el error de registro (root) */}
              {form.formState.errors.root && (
                <p className="text-sm font-medium text-destructive">
                  {form.formState.errors.root.message}
                </p>
              )}

              <Button type="submit" className="w-full" disabled={form.formState.isSubmitting}>
                {form.formState.isSubmitting ? "Creando cuenta..." : "Crear cuenta"}
              </Button>
            </form>
          </Form>
        </CardContent>
        <CardFooter className="flex justify-center">
          <p className="text-sm text-gray-600">
            ¿Ya tienes cuenta?&nbsp;
            <Link href="/login" className="font-medium text-blue-600 hover:underline">
              Inicia sesión aquí
            </Link>
          </p>
        </CardFooter>
      </Card>
    </div>
  );
}