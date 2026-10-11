
package com.example;

import java.util.ArrayList;

public abstract class Cuenta {

    protected String titular;
    protected double saldo;
    protected String pin;

    private final ArrayList<String> movimientos = new ArrayList<>();

    public Cuenta(String titular, double saldoInicial, String pin) {
        this.titular = titular;
        this.saldo = saldoInicial;
        this.pin = pin;

        registrarMovimiento(
                "Apertura de cuenta: saldo inicial $" 
                + String.format("%.2f", saldoInicial)
        );
    }

    public abstract boolean depositar(double monto);

    public abstract boolean retirar(double monto);

    public String getTitular() {
        return titular;
    }

    public boolean validarPin(String pinIngresado) {
        return pin.equals(pinIngresado);
    }

    public void consultarSaldo() {
        System.out.printf("Saldo disponible: $%.2f%n", saldo);
    }

    public double getSaldo() {
        return saldo;
    }

    protected void registrarMovimiento(String descripcion) {
        // Guardar solamente los ultimos cinco movimientos
        if (movimientos.size() == 5) {
            movimientos.remove(0);
        }

        movimientos.add(descripcion);
    }

    public void consultarMovimientos() {
        System.out.println("\n===== HISTORIAL DE MOVIMIENTOS =====");

        if (movimientos.isEmpty()) {
            System.out.println("No hay movimientos registrados.");
            return;
        }

        for (int i = movimientos.size() - 1; i >= 0; i--) {
            System.out.println(movimientos.get(i));
        }
    }

    public abstract String getTipoCuenta();
}
