package com.sigev.main;

import com.sigev.dao.ProductoDAO;
import com.sigev.modelo.Producto;
import java.io.PrintStream;
import java.util.List;

/**
 * Clase principal de prueba para verificar el CRUD de productos.
 * Sigue el estándar definido en la evidencia GA7-220501096-AA1-EV02.
 *
 * Aprendiz: Faber Stiven Londoño Gil
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // Fuerza la consola a mostrar acentos correctamente (UTF-8)
        System.setOut(new PrintStream(System.out, true, "UTF-8"));

        ProductoDAO productoDAO = new ProductoDAO();

        // 1. Insertar un producto de prueba
        Producto nuevoProducto = new Producto("Martillo", "Herramientas", 25000.0, 10);
        boolean insertado = productoDAO.insertarProducto(nuevoProducto);
        System.out.println("¿Producto insertado?: " + insertado);

        // 2. Consultar todos los productos
        System.out.println("\n--- Lista de productos ---");
        List<Producto> productos = productoDAO.consultarProductos();
        for (Producto p : productos) {
            System.out.println("Código: " + p.getCodigo()
                + " | Nombre: " + p.getNombre()
                + " | Categoría: " + p.getCategoria()
                + " | Precio: " + p.getPrecio()
                + " | Stock: " + p.getStock());
        }

        // 3. Consultar producto por código
        Producto encontrado = productoDAO.consultarProductoPorCodigo(1);
        System.out.println("\nProducto encontrado: " +
            (encontrado != null ? encontrado.getNombre() : "No existe"));

        // 4. Actualizar producto
        if (encontrado != null) {
            encontrado.setPrecio(23000.0);
            encontrado.setStock(15);
            boolean actualizado = productoDAO.actualizarProducto(encontrado);
            System.out.println("¿Producto actualizado?: " + actualizado);
        }

        // 5. Eliminar producto
        boolean eliminado = productoDAO.eliminarProducto(1);
        System.out.println("¿Producto eliminado?: " + eliminado);
    }
}