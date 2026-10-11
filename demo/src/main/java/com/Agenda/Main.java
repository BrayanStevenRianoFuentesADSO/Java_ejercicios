package com.Agenda;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Agenda agenda = new Agenda();

        agenda.cargarDesdeArchivo();

        int opcion;

        do {
            System.out.println("\n===== AGENDA DE CONTACTOS =====");
            System.out.println("1. Registrar contacto");
            System.out.println("2. Buscar contacto por nombre");
            System.out.println("3. Mostrar todos los contactos");
            System.out.println("4. Eliminar contacto");
            System.out.println("5. Mostrar contactos por grupo");
            System.out.println("6. Guardar agenda");
            System.out.println("7. Cargar agenda");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opcion: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Ingrese un numero valido.");
                scanner.nextLine();
                System.out.print("Seleccione una opcion: ");
            }

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1: {
                    System.out.print("Ingrese el nombre: ");
                    String nombre = scanner.nextLine().trim();

                    System.out.print("Ingrese el telefono (9 digitos): ");
                    String telefono = scanner.nextLine().trim();

                    System.out.print("Ingrese el email: ");
                    String email = scanner.nextLine().trim();

                    System.out.print("Ingrese el grupo (Familia, Trabajo, Amigos): ");
                    String grupo = scanner.nextLine().trim();

                    Contacto nuevo = new Contacto(
                            nombre, telefono, email, grupo);

                    agenda.agregarContacto(nuevo);
                    break;
                }

                case 2: {
                    System.out.print("Ingrese el nombre o parte del nombre: ");
                    String nombreBuscar = scanner.nextLine();

                    ArrayList<Contacto> encontrados =
                            agenda.buscarContacto(nombreBuscar);

                    if (encontrados.isEmpty()) {
                        System.out.println("No se encontraron contactos.");
                    } else {
                        System.out.println("Contactos encontrados: "
                                + encontrados.size());

                        for (Contacto contacto : encontrados) {
                            System.out.println("--------------------");
                            contacto.mostrarInfo();
                        }
                    }

                    break;
                }

                case 3:
                    agenda.mostrarTodos();
                    break;

                case 4: {
                    System.out.print("Ingrese el nombre del contacto a eliminar: ");
                    String nombreEliminar = scanner.nextLine();

                    agenda.eliminarContacto(nombreEliminar);
                    break;
                }

                case 5: {
                    System.out.print("Ingrese el grupo que desea consultar: ");
                    String grupoBuscar = scanner.nextLine();

                    agenda.mostrarContactosPorGrupo(grupoBuscar);
                    break;
                }

                case 6:
                    agenda.guardarEnArchivo();
                    break;

                case 7:
                    agenda.cargarDesdeArchivo();
                    break;

                case 8:
                    agenda.guardarEnArchivo();
                    System.out.println("Saliendo de la agenda...");
                    break;

                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }

        } while (opcion != 8);

        scanner.close();
    }
}