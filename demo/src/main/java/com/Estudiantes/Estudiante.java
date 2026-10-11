package com.Estudiantes;

import java.util.ArrayList;

public class Estudiante {

    private String nombre;
    private ArrayList<Asignatura> asignaturas;
    private double promedio;
    private int asistencia;

    public Estudiante(String nombre, int asistencia) {
        this.nombre = nombre;
        this.asistencia = asistencia;
        this.asignaturas = new ArrayList<>();
        this.promedio = 0;
    }

    public void agregarAsignatura(Asignatura asignatura) {
        asignaturas.add(asignatura);
    }

    public void calcularPromedio() {
        double suma = 0;

        if (asignaturas.isEmpty()) {
            promedio = 0;
            return;
        }

        for (Asignatura asignatura : asignaturas) {
            suma += asignatura.getNotaFinal();
        }

        promedio = suma / asignaturas.size();
    }

    public boolean aprobo() {
        return promedio >= 6 && asistencia < 10;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPromedio() {
        return promedio;
    }

    public int getAsistencia() {
        return asistencia;
    }

    public ArrayList<Asignatura> getAsignaturas() {
        return asignaturas;
    }
}