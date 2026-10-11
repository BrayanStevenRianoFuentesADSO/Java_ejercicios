
package com.Tienda;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // 1. Login simulado.
        System.out.println("====== INICIO DE SESION ======");

        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese su email: ");
        String email = scanner.nextLine();

        Cliente cliente = new Cliente(nombre, email);

        System.out.println(
            "\nBienvenido/a, " + cliente.getNombre()
        );

        // 2. Crear inventario y catálogo.
        Inventario inventario = new Inventario();

        inventario.agregarProducto(
            new ProductoElectronico(
                "Teclado", 80000, 10, "Electronica", 12
            )
        );

        inventario.agregarProducto(
            new ProductoElectronico(
                "Mouse", 45000, 15, "Electronica", 12
            )
        );

        inventario.agregarProducto(
            new ProductoElectronico(
                "Monitor", 650000, 5, "Electronica", 24
            )
        );

        inventario.agregarProducto(
            new ProductoAlimenticio(
                "Leche", 4500, 20, "Alimentos", "20/12/2026"
            )
        );

        inventario.agregarProducto(
            new ProductoAlimenticio(
                "Galletas", 2500, 30, "Alimentos", "15/01/2027"
            )
        );

        inventario.agregarProducto(
            new Producto(
                "Cuaderno", 5000, 25, "Papeleria"
            )
        );

        // 3. Crear carrito asociado al cliente e inventario.
        Carrito carrito = new Carrito(cliente, inventario);

        int opcion;

        while (true) {

            System.out.println("\n====== TIENDA ONLINE ======");
            System.out.println("1. Ver catalogo por categoria");
            System.out.println("2. Comprar producto");
            System.out.println("3. Ver factura y finalizar");
            System.out.println("4. Salir sin finalizar");
            System.out.print("Seleccione una opcion: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Debe ingresar un numero.");
                scanner.next();
                continue;
            }

            opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    mostrarCatalogoPorCategoria(
                        scanner, inventario
                    );
                    break;

                case 2:
                    comprarProducto(
                        scanner, inventario, carrito
                    );
                    break;

                case 3:
                    carrito.mostrarFactura();
                    scanner.close();
                    return;

                case 4:
                    System.out.println(
                        "Gracias por visitar la tienda."
                    );
                    scanner.close();
                    return;

                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    public static String solicitarCategoria(Scanner scanner) {

        System.out.println("\nCategorias disponibles:");
        System.out.println("1. Electronica");
        System.out.println("2. Alimentos");
        System.out.println("3. Papeleria");
        System.out.println("4. Todas");
        System.out.print("Seleccione una categoria: ");

        if (!scanner.hasNextInt()) {
            scanner.next();
            return null;
        }

        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                return "Electronica";
            case 2:
                return "Alimentos";
            case 3:
                return "Papeleria";
            case 4:
                return "Todas";
            default:
                return null;
        }
    }

    public static ArrayList<Producto> filtrarCatalogo(
            Inventario inventario, String categoria) {

        ArrayList<Producto> filtrados = new ArrayList<>();

        for (Producto producto : inventario.getCatalogo()) {

            if (categoria.equalsIgnoreCase("Todas")
                    || producto.getCategoria()
                    .equalsIgnoreCase(categoria)) {

                filtrados.add(producto);
            }
        }

        return filtrados;
    }

    public static void mostrarCatalogoPorCategoria(
            Scanner scanner, Inventario inventario) {

        String categoria = solicitarCategoria(scanner);

        if (categoria == null) {
            System.out.println("Categoria no valida.");
            return;
        }

        ArrayList<Producto> productos =
                filtrarCatalogo(inventario, categoria);

        System.out.println("\n====== CATALOGO ======");

        if (productos.isEmpty()) {
            System.out.println("No hay productos en esta categoria.");
            return;
        }

        for (int i = 0; i < productos.size(); i++) {

            Producto producto = productos.get(i);

            System.out.printf(
                "%d. %s | Categoria: %s | Precio: $%.2f | Stock: %d%n",
                i + 1,
                producto.getNombre(),
                producto.getCategoria(),
                producto.getPrecio(),
                producto.getStock()
            );
        }
    }

    public static void comprarProducto(
            Scanner scanner, Inventario inventario, Carrito carrito) {

        String categoria = solicitarCategoria(scanner);

        if (categoria == null) {
            System.out.println("Categoria no valida.");
            return;
        }

        ArrayList<Producto> productos =
                filtrarCatalogo(inventario, categoria);

        if (productos.isEmpty()) {
            System.out.println("No hay productos en esa categoria.");
            return;
        }

        System.out.println("\n====== PRODUCTOS DISPONIBLES ======");

        for (int i = 0; i < productos.size(); i++) {

            Producto producto = productos.get(i);

            System.out.printf(
                "%d. %s | Precio: $%.2f | Stock: %d%n",
                i + 1,
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock()
            );
        }

        System.out.print("Seleccione el producto: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Debe ingresar un numero.");
            scanner.next();
            return;
        }

        int indice = scanner.nextInt() - 1;

        if (indice < 0 || indice >= productos.size()) {
            System.out.println("Producto no valido.");
            return;
        }

        Producto productoSeleccionado = productos.get(indice);

        System.out.print("Cantidad que desea comprar: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Cantidad no valida.");
            scanner.next();
            return;
        }

        int cantidad = scanner.nextInt();

        if (carrito.agregarProducto(
                productoSeleccionado, cantidad)) {

            System.out.println(
                "Producto agregado correctamente al carrito."
            );

        } else {

            System.out.println(
                "No se pudo agregar. Verifique la cantidad y el stock."
            );
        }
    }
}
