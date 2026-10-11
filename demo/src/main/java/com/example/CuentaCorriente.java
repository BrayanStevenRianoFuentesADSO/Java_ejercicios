
package com.example;

public class CuentaCorriente extends CuentaBancaria {

    private static final double LIMITE_SOBREGIRO = 200000;

    public CuentaCorriente(String titular, double saldoInicial, String pin) {
        super(titular, saldoInicial, pin);
    }

    @Override
    public boolean retirar(double monto) {
        if (!Double.isFinite(monto) || monto <= 0) {
            System.out.println("El monto debe ser mayor que cero.");
            return false;
        }

        if (monto > saldo + LIMITE_SOBREGIRO) {
            System.out.println("Superaste el limite de sobregiro.");
            return false;
        }

        saldo -= monto;

        registrarMovimiento(
                String.format("Retiro: -$%.2f", monto)
        );

        System.out.println("Retiro realizado correctamente.");
        return true;
    }

    @Override
    public String getTipoCuenta() {
        return "Corriente";
    }
}
