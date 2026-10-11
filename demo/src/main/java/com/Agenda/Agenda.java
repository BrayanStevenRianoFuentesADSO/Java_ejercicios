package com.Agenda;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Agenda {

    private ArrayList<Contacto> contactos;
    private static final String ARCHIVO = "agenda.dat";

    public Agenda() {
        contactos = new ArrayList<>();
    }

    public boolean agregarContacto(Contacto c) {
        if (c == null) {
            System.out.println("El contacto no puede ser nulo.");
            return false;
        }

        if (c.getNombre() == null || c.getNombre().isBlank()) {
            System.out.println("El nombre es obligatorio.");
            return false;
        }

        if (c.getTelefono() == null ||
                !c.getTelefono().matches("[0-9]{9}")) {
            System.out.println("El telefono debe tener exactamente 9 digitos.");
            return false;
        }

        if (c.getEmail() == null ||
                !c.getEmail().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            System.out.println("El email no es valido.");
            return false;
        }

        if (c.getGrupo() == null || c.getGrupo().isBlank()) {
            System.out.println("El grupo es obligatorio.");
            return false;
        }

        for (Contacto contacto : contactos) {
            if (contacto.getNombre().equalsIgnoreCase(c.getNombre().trim())
                    || contacto.getTelefono().equals(c.getTelefono())) {
                System.out.println("Error: el nombre o telefono ya existe.");
                return false;
            }
        }

        contactos.add(c);
        System.out.println("Contacto agregado correctamente.");
        return true;
    }

    public ArrayList<Contacto> buscarContacto(String nombre) {
        ArrayList<Contacto> encontrados = new ArrayList<>();

        if (nombre == null || nombre.isBlank()) {
            return encontrados;
        }

        String texto = nombre.trim().toLowerCase();

        for (Contacto contacto : contactos) {
            if (contacto.getNombre().toLowerCase().contains(texto)) {
                encontrados.add(contacto);
            }
        }

        return encontrados;
    }

    public void mostrarTodos() {
        if (contactos.isEmpty()) {
            System.out.println("La agenda esta vacia.");
            return;
        }

        for (int i = 0; i < contactos.size(); i++) {
            System.out.println("\nContacto #" + (i + 1));
            contactos.get(i).mostrarInfo();
        }
    }

    public boolean eliminarContacto(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            System.out.println("Debes ingresar un nombre.");
            return false;
        }

        for (int i = 0; i < contactos.size(); i++) {
            if (contactos.get(i).getNombre()
                    .equalsIgnoreCase(nombre.trim())) {
                contactos.remove(i);
                System.out.println("Contacto eliminado correctamente.");
                return true;
            }
        }

        System.out.println("No se encontro el contacto.");
        return false;
    }

    public void mostrarContactosPorGrupo(String grupo) {
        if (grupo == null || grupo.isBlank()) {
            System.out.println("Debes ingresar un grupo.");
            return;
        }

        boolean encontrado = false;

        for (Contacto contacto : contactos) {
            if (contacto.getGrupo().equalsIgnoreCase(grupo.trim())) {
                contacto.mostrarInfo();
                System.out.println("--------------------");
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No hay contactos en ese grupo.");
        }
    }

    public void guardarEnArchivo() {
        try (ObjectOutputStream salida = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO))) {
            salida.writeObject(contactos);
            System.out.println("Agenda guardada correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar la agenda: " + e.getMessage());
        }
    }

    public void cargarDesdeArchivo() {
        try (ObjectInputStream entrada = new ObjectInputStream(
                new FileInputStream(ARCHIVO))) {
            Object objeto = entrada.readObject();

            if (!(objeto instanceof ArrayList<?> lista)) {
                System.out.println("El archivo no contiene una lista valida.");
                return;
            }

            ArrayList<Contacto> cargados = new ArrayList<>();

            for (Object elemento : lista) {
                if (!(elemento instanceof Contacto contacto)) {
                    System.out.println("El archivo contiene datos no validos.");
                    return;
                }

                cargados.add(contacto);
            }

            contactos = cargados;

            System.out.println("Agenda cargada correctamente. Contactos: "
                    + contactos.size());

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No se pudo cargar la agenda: " + e.getMessage());
        }
    }
}