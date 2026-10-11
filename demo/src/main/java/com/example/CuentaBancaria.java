
package com.example;

public class CuentaBancaria extends Cuenta {

    public CuentaBancaria(String titular, double saldoInicial, String pin) {
        super(titular, saldoInicial, pin);
    }

    @Override
    public boolean depositar(double monto) {
        if (!Double.isFinite(monto) || monto <= 0) {
            System.out.println("El monto debe ser mayor que cero.");
            return false;
        }

        saldo += monto;

        registrarMovimiento(
                String.format("Deposito: +$%.2f", monto)
        );

        System.out.println("Deposito realizado correctamente.");
        return true;
    }

    @Override
    public boolean retirar(double monto) {
        if (!Double.isFinite(monto) || monto <= 0) {
            System.out.println("El monto debe ser mayor que cero.");
            return false;
        }

        if (monto > saldo) {
            System.out.println("Saldo insuficiente.");
            return false;
        }

        saldo -= monto;

        registrarMovimiento(
                String.format("Retiro: -$%.2f", monto)
        );

        System.out.println("Retiro realizado correctamente.");
        return true;
    }

    public boolean transferir(CuentaBancaria cuentaDestino, double monto) {
        if (cuentaDestino == null) {
            System.out.println("La cuenta destino no existe.");
            return false;
        }

        if (cuentaDestino == this) {
            System.out.println("No puedes transferir a tu propia cuenta.");
            return false;
        }

        if (!Double.isFinite(monto) || monto <= 0) {
            System.out.println("El monto debe ser mayor que cero.");
            return false;
        }

        if (monto > saldo) {
            System.out.println("Saldo insuficiente para transferir.");
            return false;
        }

        // Retirar utiliza el comportamiento del tipo de cuenta de origen.
        if (!retirar(monto)) {
            return false;
        }

        // Depositar utiliza el comportamiento del tipo de cuenta destino.
        if (!cuentaDestino.depositar(monto)) {
            // Revertir el retiro si el deposito falla.
            saldo += monto;
            registrarMovimiento(
                    String.format("Reversion de transferencia: +$%.2f", monto)
            );
            return false;
        }

        registrarMovimiento(
                String.format(
                        "Transferencia enviada a %s: -$%.2f",
                        cuentaDestino.getTitular(), monto
                )
        );

        System.out.println("Transferencia realizada correctamente.");
        return true;
    }

    @Override
    public String getTipoCuenta() {
        return "Bancaria";
    }
}
