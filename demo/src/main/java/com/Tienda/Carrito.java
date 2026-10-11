
package com.Tienda;

import java.util.ArrayList;

public class Carrito {

    private ArrayList<Producto> productos;
    private ArrayList<Integer> cantidades;
    private Cliente cliente;
    private Inventario inventario;

    private static final double MONTO_DESCUENTO = 1000.0;
    private static final double PORCENTAJE_DESCUENTO = 0.10;

    public Carrito(Cliente cliente, Inventario inventario) {
        this.cliente = cliente;
        this.inventario = inventario;
        productos = new ArrayList<>();
        cantidades = new ArrayList<>();
    }

    public boolean agregarProducto(Producto producto, int cantidad) {

        if (!inventario.hayStock(producto, cantidad)) {
            return false;
        }

        if (!inventario.disminuirStock(producto, cantidad)) {
            return false;
        }

        for (int i = 0; i < productos.size(); i++) {

            if (productos.get(i) == producto) {
                cantidades.set(
                    i,
                    cantidades.get(i) + cantidad
                );

                return true;
            }
        }

        productos.add(producto);
        cantidades.add(cantidad);

        return true;
    }

    public double calcularSubtotal() {

        double subtotal = 0;

        for (int i = 0; i < productos.size(); i++) {
            subtotal += productos.get(i).getPrecio()
                    * cantidades.get(i);
        }

        return subtotal;
    }

    public double calcularDescuento() {

        double subtotal = calcularSubtotal();

        if (subtotal > MONTO_DESCUENTO) {
            return subtotal * PORCENTAJE_DESCUENTO;
        }

        return 0;
    }

    public double calcularTotal() {
        return calcularSubtotal() - calcularDescuento();
    }

    public void mostrarFactura() {

        System.out.println("\n========== FACTURA ==========");

        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Email: " + cliente.getEmail());

        System.out.println("------------------------------");

        for (int i = 0; i < productos.size(); i++) {

            Producto producto = productos.get(i);
            int cantidad = cantidades.get(i);
            double subtotalProducto =
                    producto.getPrecio() * cantidad;

            System.out.printf(
                "%s (%s) | Cantidad: %d | Subtotal: $%.2f%n",
                producto.getNombre(),
                producto.getCategoria(),
                cantidad,
                subtotalProducto
            );

            if (producto instanceof ProductoAlimenticio) {
                ProductoAlimenticio alimenticio =
                        (ProductoAlimenticio) producto;

                System.out.println(
                    "  Fecha de caducidad: "
                    + alimenticio.getFechaCaducidad()
                );

            } else if (producto instanceof ProductoElectronico) {
                ProductoElectronico electronico =
                        (ProductoElectronico) producto;

                System.out.println(
                    "  Garantia: "
                    + electronico.getMesesGarantia()
                    + " meses"
                );
            }
        }

        System.out.println("------------------------------");

        System.out.printf(
            "Subtotal: $%.2f%n",
            calcularSubtotal()
        );

        System.out.printf(
            "Descuento: -$%.2f%n",
            calcularDescuento()
        );

        System.out.printf(
            "TOTAL A PAGAR: $%.2f%n",
            calcularTotal()
        );

        System.out.println("==============================");
    }
}
