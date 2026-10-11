
package com.example;

import java.util.ArrayList;

public class Banco {

    private final ArrayList<CuentaBancaria> cuentas = new ArrayList<>();

    public void agregarCuenta(CuentaBancaria cuenta) {
        if (cuenta == null) {
            return;
        }

        if (buscarCuenta(cuenta.getTitular()) != null) {
            System.out.println("Ya existe una cuenta con ese titular.");
            return;
        }

        cuentas.add(cuenta);
    }

    public CuentaBancaria buscarCuenta(String titular) {
        if (titular == null || titular.isBlank()) {
            return null;
        }

        for (CuentaBancaria cuenta : cuentas) {
            if (cuenta.getTitular().equalsIgnoreCase(titular.trim())) {
                return cuenta;
            }
        }

        return null;
    }

    public boolean validarPin(String titular, String pin) {
        CuentaBancaria cuenta = buscarCuenta(titular);

        return cuenta != null && cuenta.validarPin(pin);
    }

    public void mostrarCuentas() {
        System.out.println("\n===== CUENTAS REGISTRADAS =====");

        for (CuentaBancaria cuenta : cuentas) {
            System.out.println(
                    cuenta.getTitular() + " - " + cuenta.getTipoCuenta()
            );
        }
    }
}
