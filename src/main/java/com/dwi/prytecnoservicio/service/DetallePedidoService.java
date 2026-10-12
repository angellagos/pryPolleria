/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.service;

import com.dwi.prytecnoservicio.dao.DetallePedidoDAO;
import com.dwi.prytecnoservicio.factory.DAOFactory;
import com.dwi.prytecnoservicio.model.DetallePedido;
import java.util.List;

/**
 *
 * @author Miguel Angel
 */
public class DetallePedidoService {

    private final DetallePedidoDAO detallePedidoDAO;

    public DetallePedidoService() {
        detallePedidoDAO =
                DAOFactory.crearDetallePedidoDAO();
    }

    public List<DetallePedido> listarPorPedido(int idPedido) {

        if (idPedido <= 0) {
            throw new IllegalArgumentException(
                    "El ID del pedido debe ser mayor que cero.");
        }

        return detallePedidoDAO.listarPorPedido(idPedido);
    }

    public void guardarDetalles(
            int idPedido, List<DetallePedido> detalles) {

        if (idPedido <= 0) {
            throw new IllegalArgumentException(
                    "El ID del pedido debe ser mayor que cero.");
        }

        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException(
                    "Debes agregar al menos un producto al pedido.");
        }

        for (DetallePedido detalle : detalles) {

            if (detalle == null) {
                throw new IllegalArgumentException(
                        "El detalle del pedido no puede ser nulo.");
            }

            if (detalle.getIdProducto() <= 0) {
                throw new IllegalArgumentException(
                        "Debes seleccionar un producto válido.");
            }

            if (detalle.getCantidad() <= 0) {
                throw new IllegalArgumentException(
                        "La cantidad debe ser mayor que cero.");
            }

            if (detalle.getPrecioUnitario() == null
                    || detalle.getPrecioUnitario() < 0) {
                throw new IllegalArgumentException(
                        "El precio unitario no es válido.");
            }
        }

        detallePedidoDAO.guardar(idPedido, detalles);
    }
}