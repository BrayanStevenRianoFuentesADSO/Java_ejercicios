
package com.Notas;

public class Alumno {

    private String nombre;
    private double notaParcial1;
    private double notaParcial2;
    private double examenFinal;
    private double notaOriginal;
    private double notaFinal;

    public Alumno(String nombre, double notaParcial1, double notaParcial2, double examenFinal) {
        this.nombre = nombre;
        this.notaParcial1 = notaParcial1;
        this.notaParcial2 = notaParcial2;
        this.examenFinal = examenFinal;
        this.notaOriginal = calcularNotaOriginal();
        this.notaFinal = notaOriginal;
    }

    public double calcularNotaOriginal() {
        return notaParcial1 * 0.30
                + notaParcial2 * 0.30
                + examenFinal * 0.40;
    }

    public void aplicarCurvaPorcentual(double porcentaje) {
        notaFinal = Math.min(notaFinal + notaFinal * porcentaje / 100, 10);
    }

    public void aplicarCurva(double puntos) {
        notaFinal = Math.min(notaFinal + puntos, 10);
    }

    public boolean estaAprobado() {
        return notaFinal >= 6;
    }

    public String getNombre() {
        return nombre;
    }

    public double getNotaParcial1() {
        return notaParcial1;
    }

    public double getNotaParcial2() {
        return notaParcial2;
    }

    public double getExamenFinal() {
        return examenFinal;
    }

    public double getNotaOriginal() {
        return notaOriginal;
    }

    public double getNotaFinal() {
        return notaFinal;
    }
}
