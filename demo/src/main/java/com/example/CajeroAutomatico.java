
package com.example;

import java.util.Scanner;

public class CajeroAutomatico {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Banco banco = new Banco();

        // Crear cuentas de prueba
        banco.agregarCuenta(
                new CuentaAhorro("Juan Perez", 1000000, "1234")
        );

        banco.agregarCuenta(
                new CuentaCorriente("Maria Lopez", 500000, "5678")
        );

        banco.agregarCuenta(
                new CuentaBancaria("Carlos Ruiz", 300000, "9012")
        );

        System.out.println("===== BIENVENIDO AL CAJERO =====");

        // Mostrar titulares disponibles para la simulacion
        banco.mostrarCuentas();

        CuentaBancaria cuentaActual = null;
        int intentos = 0;

        // Inicio de sesion: maximo tres intentos
        while (intentos < 3 && cuentaActual == null) {

            System.out.print("\nIngrese su titular: ");
            String titular = scanner.nextLine().trim();

            CuentaBancaria cuentaEncontrada = banco.buscarCuenta(titular);

            if (cuentaEncontrada == null) {
                intentos++;
                System.out.println("No existe una cuenta con ese titular.");
            } else {
                System.out.print("Ingrese su PIN: ");
                String pin = scanner.nextLine();

                if (banco.validarPin(titular, pin)) {
                    cuentaActual = cuentaEncontrada;
                    System.out.println("\nAcceso concedido.");
                    System.out.println(
                            "Bienvenido/a, " + cuentaActual.getTitular()
                    );
                    System.out.println(
                            "Tipo de cuenta: " + cuentaActual.getTipoCuenta()
                    );
                } else {
                    intentos++;
                    System.out.println("PIN incorrecto.");
                }
            }

            if (cuentaActual == null) {
                System.out.println(
                        "Intentos restantes: " + (3 - intentos)
                );
            }
        }

        if (cuentaActual == null) {
            System.out.println("Acceso bloqueado por seguridad.");
            scanner.close();
            return;
        }

        int opcion = 0;

        // Menu principal
        while (opcion != 6) {

            System.out.println("\n===== MENU DEL CAJERO =====");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Retirar dinero");
            System.out.println("3. Depositar dinero");
            System.out.println("4. Transferir dinero");
            System.out.println("5. Consultar movimientos");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese una opcion numerica valida.");
                continue;
            }

            switch (opcion) {

                case 1:
                    cuentaActual.consultarSaldo();
                    break;

                case 2:
                    System.out.print("Monto a retirar: ");

                    try {
                        double monto = Double.parseDouble(
                                scanner.nextLine().trim()
                        );
                        cuentaActual.retirar(monto);
                    } catch (NumberFormatException e) {
                        System.out.println("Ingrese un monto valido.");
                    }
                    break;

                case 3:
                    System.out.print("Monto a depositar: ");

                    try {
                        double monto = Double.parseDouble(
                                scanner.nextLine().trim()
                        );
                        cuentaActual.depositar(monto);
                    } catch (NumberFormatException e) {
                        System.out.println("Ingrese un monto valido.");
                    }
                    break;

                case 4:
                    System.out.print("Titular de la cuenta destino: ");
                    String titularDestino = scanner.nextLine().trim();

                    CuentaBancaria cuentaDestino =
                            banco.buscarCuenta(titularDestino);

                    if (cuentaDestino == null) {
                        System.out.println("La cuenta destino no existe.");
                        break;
                    }

                    System.out.print("Monto a transferir: ");

                    try {
                        double monto = Double.parseDouble(
                                scanner.nextLine().trim()
                        );

                        cuentaActual.transferir(cuentaDestino, monto);

                    } catch (NumberFormatException e) {
                        System.out.println("Ingrese un monto valido.");
                    }
                    break;

                case 5:
                    cuentaActual.consultarMovimientos();
                    break;

                case 6:
                    System.out.println("Gracias por utilizar el cajero.");
                    break;

                default:
                    System.out.println("Seleccione una opcion del 1 al 6.");
            }
        }

        scanner.close();
    }
}
