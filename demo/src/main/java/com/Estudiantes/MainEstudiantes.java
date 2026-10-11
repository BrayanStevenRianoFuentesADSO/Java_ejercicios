package com.Estudiantes;

import java.util.Scanner;

public class MainEstudiantes {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Curso curso = new Curso();

        System.out.print("Cantidad de estudiantes: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < cantidad; i++) {

            System.out.println("\nEstudiante " + (i + 1));

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Numero de faltas: ");
            int faltas = scanner.nextInt();
            scanner.nextLine();

            Estudiante estudiante = new Estudiante(nombre, faltas);

            System.out.print("Cantidad de asignaturas: ");
            int cantidadAsignaturas = scanner.nextInt();
            scanner.nextLine();

            for (int j = 0; j < cantidadAsignaturas; j++) {

                System.out.print("Nombre de la asignatura: ");
                String nombreAsignatura = scanner.nextLine();

                System.out.print("Nota final: ");
                double nota = scanner.nextDouble();
                scanner.nextLine();

                estudiante.agregarAsignatura(
                        new Asignatura(nombreAsignatura, nota));
            }

            estudiante.calcularPromedio();
            curso.agregarEstudiante(estudiante);
        }

        System.out.println("\n===== ESTADISTICAS =====");
        System.out.println("Promedio general: " + curso.calcularPromedioGeneral());
        System.out.println("Aprobados: " + curso.contarAprobados());
        System.out.println("Reprobados: " + curso.contarReprobados());

        Estudiante mejor = curso.obtenerMejorEstudiante();
        Estudiante peor = curso.obtenerPeorEstudiante();

        if (mejor != null && peor != null) {
            System.out.println("Mejor estudiante: " + mejor.getNombre());
            System.out.println("Peor estudiante: " + peor.getNombre());
        }

        System.out.print("\nNombre del estudiante que desea buscar: ");
        String nombreBuscar = scanner.nextLine();

        Estudiante encontrado = curso.buscarEstudiante(nombreBuscar);

        if (encontrado != null) {
            System.out.println("Encontrado: " + encontrado.getNombre());
            System.out.println("Promedio: " + encontrado.getPromedio());
        } else {
            System.out.println("Estudiante no encontrado.");
        }

        System.out.print("\n¿Desea modificar una nota? (si/no): ");
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("si")) {

            System.out.print("Nombre del estudiante: ");
            String nombreEstudiante = scanner.nextLine();

            System.out.print("Nombre de la asignatura: ");
            String nombreAsignatura = scanner.nextLine();

            System.out.print("Nueva nota: ");
            double nuevaNota = scanner.nextDouble();
            scanner.nextLine();

            boolean modificado = curso.modificarNota(
                    nombreEstudiante,
                    nombreAsignatura,
                    nuevaNota);

            if (modificado) {
                System.out.println("Nota modificada correctamente.");
            } else {
                System.out.println("Estudiante o asignatura no encontrados.");
            }
        }

        System.out.print("\nNombre del archivo de reporte (ej. reporte.txt): ");
        String archivo = scanner.nextLine();

        curso.guardarReporte(archivo);

        scanner.close();
    }
}