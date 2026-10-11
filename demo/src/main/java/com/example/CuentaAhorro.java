
package com.example;

public class CuentaAhorro extends CuentaBancaria {

    private static final double INTERES = 0.01;

    public CuentaAhorro(String titular, double saldoInicial, String pin) {
        super(titular, saldoInicial, pin);
    }

    @Override
    public boolean depositar(double monto) {
        if (!Double.isFinite(monto) || monto <= 0) {
            System.out.println("El monto debe ser mayor que cero.");
            return false;
        }

        double interes = monto * INTERES;
        double total = monto + interes;

        saldo += total;

        registrarMovimiento(
                String.format(
                        "Deposito de ahorro: +$%.2f | Interes: +$%.2f",
                        monto, interes
                )
        );

        System.out.printf("Deposito: $%.2f%n", monto);
        System.out.printf("Interes ganado: $%.2f%n", interes);
        System.out.printf("Total agregado: $%.2f%n", total);

        return true;
    }

    @Override
    public String getTipoCuenta() {
        return "Ahorros";
    }
}

