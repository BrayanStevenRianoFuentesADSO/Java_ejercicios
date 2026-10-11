
package com.Tienda;

import java.util.ArrayList;

public class Inventario {

    private ArrayList<Producto> catalogo;

    public Inventario() {
        catalogo = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        if (producto != null) {
            catalogo.add(producto);
        }
    }

    public ArrayList<Producto> getCatalogo() {
        return catalogo;
    }

    public boolean hayStock(Producto producto, int cantidad) {
        if (producto == null || cantidad <= 0) {
            return false;
        }

        return catalogo.contains(producto)
                && producto.getStock() >= cantidad;
    }

    public boolean disminuirStock(Producto producto, int cantidad) {
        if (!hayStock(producto, cantidad)) {
            return false;
        }

        return producto.disminuirStock(cantidad);
    }
}
