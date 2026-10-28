package com.sigev.main;

import com.sigev.dao.ProductoDAO;
import com.sigev.dao.VentaDAO;
import com.sigev.modelo.Producto;
import com.sigev.modelo.Venta;
import java.util.Date;
import java.util.List;

/**
 * Prueba de los módulos Productos y Ventas.
 * Todas las operaciones se hacen sobre un producto de prueba que se
 * inserta al inicio y se elimina al final, para no tocar los datos reales.
 */
public class Main {

    public static void main(String[] args) {

        ProductoDAO productoDAO = new ProductoDAO();
        VentaDAO ventaDAO = new VentaDAO();

        // ================= MÓDULO PRODUCTOS =================
        // 1. Insertar un producto de prueba (el DAO le asigna el código generado)
        Producto prueba = new Producto("Destornillador estrella 6\"", "Herramientas", 9500.0, 25);
        boolean insertado = productoDAO.insertarProducto(prueba);
        System.out.println("¿Producto insertado?: " + insertado + " | Código: " + prueba.getCodigo());
        if (!insertado) {
            return;
        }
        int codigoPrueba = prueba.getCodigo();

        // 2. Listar todos los productos
        System.out.println("\n--- Lista de productos ---");
        List<Producto> productos = productoDAO.consultarProductos();
        for (Producto p : productos) {
            System.out.println(p);
        }

        // 3. Consultar el producto de prueba por código
        Producto encontrado = productoDAO.consultarProductoPorCodigo(codigoPrueba);
        System.out.println("\nProducto encontrado: " +
                (encontrado != null ? encontrado.getNombre() : "No existe"));

        // 4. Actualizar el producto de prueba
        if (encontrado != null) {
            encontrado.setPrecio(23000.0);
            encontrado.setStock(15);
            boolean actualizado = productoDAO.actualizarProducto(encontrado);
            System.out.println("¿Producto actualizado?: " + actualizado);
        }

        // ================= MÓDULO VENTAS =================
        // Se vende el mismo producto de prueba
        Producto productoParaVenta = productoDAO.consultarProductoPorCodigo(codigoPrueba);
        int stockAntes = productoParaVenta.getStock();

        System.out.println("\n--- Prueba módulo Ventas ---");
        System.out.println("Producto seleccionado: " + productoParaVenta.getNombre()
                + " | Stock antes: " + stockAntes);

        // 1. Registrar una venta de 2 unidades
        int cantidadVendida = 2;
        double totalVenta = productoParaVenta.getPrecio() * cantidadVendida;
        Venta nuevaVenta = new Venta(codigoPrueba, cantidadVendida, new Date(), totalVenta);
        boolean ventaRegistrada = ventaDAO.registrarVenta(nuevaVenta);
        System.out.println("¿Venta registrada?: " + ventaRegistrada);

        // 2. Descontar el stock del producto vendido
        productoParaVenta.setStock(stockAntes - cantidadVendida);
        boolean stockActualizado = productoDAO.actualizarProducto(productoParaVenta);
        System.out.println("¿Stock actualizado tras la venta?: " + stockActualizado);
        System.out.println("Stock después: " + productoParaVenta.getStock());

        // 3. Listar todas las ventas registradas
        System.out.println("\n--- Lista de ventas ---");
        List<Venta> ventas = ventaDAO.consultarVentas();
        for (Venta v : ventas) {
            System.out.println(v);
        }

        // 4. Eliminar las ventas del producto de prueba
        for (Venta v : ventas) {
            if (v.getCodigoProducto() == codigoPrueba) {
                boolean ventaEliminada = ventaDAO.eliminarVenta(v.getIdVenta());
                System.out.println("\n¿Venta " + v.getIdVenta() + " eliminada?: " + ventaEliminada);
            }
        }

        // ================= FIN PRUEBAS =================
        // 5. Eliminar el producto de prueba (al final, cuando ya no tiene ventas)
        boolean eliminado = productoDAO.eliminarProducto(codigoPrueba);
        System.out.println("\n¿Producto eliminado?: " + eliminado);
    }
}
