
package com.dwi.prytecnoservicio.test;

import com.dwi.prytecnoservicio.dao.ProductoDAO;
import com.dwi.prytecnoservicio.factory.DAOFactory;
import com.dwi.prytecnoservicio.model.Producto;
import java.util.List;


public class ProductoTest {
    public static void main(String[] args) {

        ProductoDAO productoDAO = DAOFactory.crearProductoDAO();

        List<Producto> productos = productoDAO.listar();

        System.out.println("===== PRODUCTOS DE LA POLLERÍA =====");

        for (Producto producto : productos) {
            System.out.println(
                "ID: " + producto.getIdProducto()
                + " | Nombre: " + producto.getNombre()
                + " | Precio: S/ " + producto.getPrecio()
                + " | Categoría: "
                + producto.getCategoria().getNombre()
            );
        }

        System.out.println("Total de productos: " + productos.size());
    }
}
