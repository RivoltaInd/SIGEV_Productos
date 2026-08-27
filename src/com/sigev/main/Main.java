package com.sigev.main;

import com.sigev.dao.ProductoDAO;
import com.sigev.dao.VentaDAO;
import com.sigev.modelo.Producto;
import com.sigev.modelo.Venta;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ProductoDAO productoDAO = new ProductoDAO();
        VentaDAO ventaDAO = new VentaDAO();

        // ================= MÓDULO PRODUCTOS =================
        // 1. Insertar un nuevo producto
        Producto nuevo = new Producto("Destornillador estrella 6\"", "Herramientas", 9500.0, 25);
        boolean insertado = productoDAO.insertarProducto(nuevo);
        System.out.println("¿Producto insertado?: " + insertado);

        // 2. Listar todos los productos
        System.out.println("\n--- Lista de productos ---");
        List<Producto> productos = productoDAO.consultarProductos();
        for (Producto p : productos) {
            System.out.println(p);
        }

        // 3. Consultar producto por código (dinámico)
        int codigoPrueba = productos.get(0).getCodigo();
        Producto encontrado = productoDAO.consultarProductoPorCodigo(codigoPrueba);
        System.out.println("\nProducto encontrado: " +
                (encontrado != null ? encontrado.getNombre() : "No existe"));

        // 4. Actualizar producto
        if (encontrado != null) {
            encontrado.setPrecio(23000.0);
            encontrado.setStock(15);
            boolean actualizado = productoDAO.actualizarProducto(encontrado);
            System.out.println("¿Producto actualizado?: " + actualizado);
        }

        // ================= MÓDULO VENTAS =================
        // Usamos un producto real y con stock disponible para la venta
        List<Producto> productosDisponibles = productoDAO.consultarProductos();
        Producto productoParaVenta = productosDisponibles.get(0);
        int stockAntes = productoParaVenta.getStock();

        System.out.println("\n--- Prueba módulo Ventas ---");
        System.out.println("Producto seleccionado: " + productoParaVenta.getNombre()
                + " | Stock antes: " + stockAntes);

        // 1. Registrar una venta de 2 unidades
        int cantidadVendida = 2;
        double totalVenta = productoParaVenta.getPrecio() * cantidadVendida;
        Venta nuevaVenta = new Venta(productoParaVenta.getCodigo(), cantidadVendida, new Date(), totalVenta);
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

        // 4. Eliminar la última venta registrada (dinámico)
        int idVentaAEliminar = ventas.get(ventas.size() - 1).getIdVenta();
        boolean ventaEliminada = ventaDAO.eliminarVenta(idVentaAEliminar);
        System.out.println("\n¿Venta eliminada?: " + ventaEliminada);

        // ================= FIN PRUEBAS PRODUCTOS =================
        // 5. Eliminar producto (dinámico) - al final, para no afectar la venta
        boolean eliminado = productoDAO.eliminarProducto(codigoPrueba);
        System.out.println("\n¿Producto eliminado?: " + eliminado);
    }
}