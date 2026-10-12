/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.service;

import com.dwi.prytecnoservicio.dao.ProductoDAO;
import com.dwi.prytecnoservicio.factory.DAOFactory;
import com.dwi.prytecnoservicio.model.Producto;
import java.util.List;

/**
 *
 * @author Miguel Angel
 */
public class ProductoService {

    private final ProductoDAO productoDAO;

    public ProductoService() {
        this.productoDAO = DAOFactory.crearProductoDAO();
    }

    public List<Producto> listarProductos() {
        return productoDAO.listar();
    }

    public Producto buscarProductoPorId(int idProducto) {
        if (idProducto <= 0) {
            throw new IllegalArgumentException(
                    "El ID del producto debe ser mayor que cero."
            );
        }

        return productoDAO.buscarPorId(idProducto);
    }

    public void guardarProducto(Producto producto) {

        if (producto == null) {
            throw new IllegalArgumentException(
                    "El producto no puede ser nulo."
            );
        }

        if (producto.getNombre() == null
                || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre del producto es obligatorio."
            );
        }

        if (producto.getPrecio() == null
                || producto.getPrecio() <= 0) {
            throw new IllegalArgumentException(
                    "El precio debe ser mayor que cero."
            );
        }

        if (producto.getCategoria() == null
                || producto.getCategoria().getIdCategoria() <= 0) {
            throw new IllegalArgumentException(
                    "Debes seleccionar una categoría."
            );
        }

        productoDAO.guardar(producto);
    }

    public void actualizarProducto(Producto producto) {

        if (producto == null) {
            throw new IllegalArgumentException(
                    "El producto no puede ser nulo."
            );
        }

        if (producto.getIdProducto() <= 0) {
            throw new IllegalArgumentException(
                    "El ID del producto no es válido."
            );
        }

        if (producto.getNombre() == null
                || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre del producto es obligatorio."
            );
        }

        if (producto.getPrecio() == null
                || producto.getPrecio() <= 0) {
            throw new IllegalArgumentException(
                    "El precio debe ser mayor que cero."
            );
        }

        if (producto.getCategoria() == null
                || producto.getCategoria().getIdCategoria() <= 0) {
            throw new IllegalArgumentException(
                    "Debes seleccionar una categoría."
            );
        }

        productoDAO.actualizar(producto);
    }

    public void eliminarProducto(int idProducto) {

        if (idProducto <= 0) {
            throw new IllegalArgumentException(
                    "El ID del producto debe ser mayor que cero."
            );
        }

        Producto producto = productoDAO.buscarPorId(idProducto);

        if (producto == null) {
            throw new IllegalArgumentException(
                    "El producto no existe o ya fue eliminado."
            );
        }

        productoDAO.eliminar(idProducto);
    }

}
