
package com.Notas;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Alumno> alumnos = new ArrayList<>();

        int n = leerEntero(sc, "Ingrese la cantidad de alumnos: ");

        while (n <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            n = leerEntero(sc, "Ingrese la cantidad de alumnos: ");
        }

        double sumaNotasOriginales = 0;

        for (int i = 0; i < n; i++) {

            System.out.println("\nAlumno " + (i + 1));

            System.out.print("Nombre: ");
            String nombre = sc.nextLine();

            double parcial1 = leerNota(sc, "Nota del parcial 1 (0 a 10): ");
            double parcial2 = leerNota(sc, "Nota del parcial 2 (0 a 10): ");
            double examen = leerNota(sc, "Nota del examen final (0 a 10): ");

            Alumno alumno = new Alumno(nombre, parcial1, parcial2, examen);
            alumnos.add(alumno);

            sumaNotasOriginales += alumno.calcularNotaOriginal();
        }

        double promedioOriginal = sumaNotasOriginales / alumnos.size();
        double notaMasAlta = 0;

        for (Alumno alumno : alumnos) {
            if (alumno.getNotaOriginal() > notaMasAlta) {
                notaMasAlta = alumno.getNotaOriginal();
            }
        }

        System.out.printf("%nPromedio original del curso: %.2f%n", promedioOriginal);
        System.out.printf("Nota mas alta del curso: %.2f%n", notaMasAlta);

        int reprobadosAntes = 0;

        for (Alumno alumno : alumnos) {
            if (!alumno.estaAprobado()) {
                reprobadosAntes++;
            }
        }

        if (promedioOriginal < 6) {
            double curva = 10 - notaMasAlta;

            System.out.printf("Se aplica una curva de %.2f puntos.%n", curva);

            for (Alumno alumno : alumnos) {
                alumno.aplicarCurva(curva);
            }
        } else {
            System.out.println("No se aplica curva porque el promedio es igual o mayor a 6.");
        }

        int aprobadosGraciasCurva = 0;
        double sumaNotasFinales = 0;

        for (Alumno alumno : alumnos) {
            sumaNotasFinales += alumno.getNotaFinal();

            if (promedioOriginal < 6
                    && alumno.getNotaOriginal() < 6
                    && alumno.estaAprobado()) {
                aprobadosGraciasCurva++;
            }
        }

        double mediaFinal = sumaNotasFinales / alumnos.size();
        double sumaDiferenciasCuadradas = 0;

        for (Alumno alumno : alumnos) {
            double diferencia = alumno.getNotaFinal() - mediaFinal;
            sumaDiferenciasCuadradas += diferencia * diferencia;
        }

        double desviacionEstandar = Math.sqrt(
                sumaDiferenciasCuadradas / alumnos.size()
        );

        System.out.println("\n========== RESULTADOS ==========");

        for (Alumno alumno : alumnos) {
            System.out.println("\nNombre: " + alumno.getNombre());
            System.out.printf("Parcial 1: %.2f%n", alumno.getNotaParcial1());
            System.out.printf("Parcial 2: %.2f%n", alumno.getNotaParcial2());
            System.out.printf("Examen final: %.2f%n", alumno.getExamenFinal());
            System.out.printf("Nota original ponderada: %.2f%n", alumno.getNotaOriginal());
            System.out.printf("Nota final: %.2f%n", alumno.getNotaFinal());
            System.out.println("Estado: " + (alumno.estaAprobado() ? "APROBADO" : "REPROBADO"));
        }

        System.out.println("\n========== ESTADISTICAS ==========");
        System.out.printf("Media original: %.2f%n", promedioOriginal);
        System.out.printf("Media final: %.2f%n", mediaFinal);
        System.out.printf("Desviacion estandar final: %.2f%n", desviacionEstandar);
        System.out.println("Reprobados antes de la curva: " + reprobadosAntes);
        System.out.println("Alumnos que aprobaron gracias a la curva: " + aprobadosGraciasCurva);

        sc.close();
    }

    public static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int valor = sc.nextInt();
                sc.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un numero entero.");
                sc.nextLine();
            }
        }
    }

    public static double leerNota(Scanner sc, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                double nota = sc.nextDouble();
                sc.nextLine();

                if (nota >= 0 && nota <= 10) {
                    return nota;
                }

                System.out.println("La nota debe estar entre 0 y 10.");

            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un valor numerico.");
                sc.nextLine();
            }
        }
    }
}
