package com.Estudiantes;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Curso {

    private ArrayList<Estudiante> estudiantes;

    public Curso() {
        estudiantes = new ArrayList<>();
    }

    public void agregarEstudiante(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }

    public double calcularPromedioGeneral() {
        if (estudiantes.isEmpty()) {
            return 0;
        }

        double suma = 0;

        for (Estudiante estudiante : estudiantes) {
            estudiante.calcularPromedio();
            suma += estudiante.getPromedio();
        }

        return suma / estudiantes.size();
    }

    public int contarAprobados() {
        int aprobados = 0;

        for (Estudiante estudiante : estudiantes) {
            estudiante.calcularPromedio();

            if (estudiante.aprobo()) {
                aprobados++;
            }
        }

        return aprobados;
    }

    public int contarReprobados() {
        return estudiantes.size() - contarAprobados();
    }

    public Estudiante obtenerMejorEstudiante() {
        if (estudiantes.isEmpty()) {
            return null;
        }

        Estudiante mejor = estudiantes.get(0);

        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getPromedio() > mejor.getPromedio()) {
                mejor = estudiante;
            }
        }

        return mejor;
    }

    public Estudiante obtenerPeorEstudiante() {
        if (estudiantes.isEmpty()) {
            return null;
        }

        Estudiante peor = estudiantes.get(0);

        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getPromedio() < peor.getPromedio()) {
                peor = estudiante;
            }
        }

        return peor;
    }

    public Estudiante buscarEstudiante(String nombre) {
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getNombre().equalsIgnoreCase(nombre)) {
                return estudiante;
            }
        }

        return null;
    }

    public boolean modificarNota(
            String nombreEstudiante,
            String nombreAsignatura,
            double nuevaNota) {

        Estudiante estudiante = buscarEstudiante(nombreEstudiante);

        if (estudiante == null) {
            return false;
        }

        for (Asignatura asignatura : estudiante.getAsignaturas()) {
            if (asignatura.getNombre().equalsIgnoreCase(nombreAsignatura)) {
                asignatura.setNotaFinal(nuevaNota);
                estudiante.calcularPromedio();
                return true;
            }
        }

        return false;
    }

    public void guardarReporte(String nombreArchivo) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(nombreArchivo))) {

            escritor.println("===== REPORTE DEL CURSO =====");

            for (Estudiante estudiante : estudiantes) {
                estudiante.calcularPromedio();

                escritor.println("\nEstudiante: " + estudiante.getNombre());
                escritor.println("Faltas: " + estudiante.getAsistencia());

                for (Asignatura asignatura : estudiante.getAsignaturas()) {
                    escritor.println(
                            asignatura.getNombre() + ": "
                            + asignatura.getNotaFinal());
                }

                escritor.println("Promedio: " + estudiante.getPromedio());
                escritor.println(
                        "Estado: " + (estudiante.aprobo() ? "Aprobado" : "Reprobado"));
            }

            escritor.println("\n===== ESTADISTICAS =====");
            escritor.println("Promedio general: " + calcularPromedioGeneral());
            escritor.println("Aprobados: " + contarAprobados());
            escritor.println("Reprobados: " + contarReprobados());

            Estudiante mejor = obtenerMejorEstudiante();
            Estudiante peor = obtenerPeorEstudiante();

            if (mejor != null && peor != null) {
                escritor.println("Mejor estudiante: " + mejor.getNombre());
                escritor.println("Peor estudiante: " + peor.getNombre());
            }

            System.out.println("Reporte guardado correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar el reporte: " + e.getMessage());
        }
    }
}
