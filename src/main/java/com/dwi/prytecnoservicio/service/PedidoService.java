/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.service;

import com.dwi.prytecnoservicio.dao.PedidoDAO;
import com.dwi.prytecnoservicio.factory.DAOFactory;
import com.dwi.prytecnoservicio.model.DetallePedido;
import com.dwi.prytecnoservicio.model.Pedido;
import java.util.List;

/**
 *
 * @author Miguel Angel
 */
public class PedidoService {

    private final PedidoDAO pedidoDAO;

    public PedidoService() {
        pedidoDAO = DAOFactory.crearPedidoDAO();
    }

    public List<Pedido> listarPedidos() {
        return pedidoDAO.listar();
    }

    public Pedido buscarPedidoPorId(int idPedido) {

        if (idPedido <= 0) {
            throw new IllegalArgumentException(
                    "El ID del pedido debe ser mayor que cero.");
        }

        return pedidoDAO.buscarPorId(idPedido);
    }

    public int guardarPedido(Pedido pedido) {

        if (pedido == null) {
            throw new IllegalArgumentException(
                    "El pedido no puede ser nulo.");
        }

        if (pedido.getTipoAtencion() == null
                || pedido.getTipoAtencion().isBlank()) {
            throw new IllegalArgumentException(
                    "Debes indicar el tipo de atención.");
        }

        String tipo = pedido.getTipoAtencion();

        if (!tipo.equals("MESA")
                && !tipo.equals("PARA_LLEVAR")
                && !tipo.equals("DELIVERY")) {
            throw new IllegalArgumentException(
                    "El tipo de atención no es válido.");
        }

        if (pedido.getIdUsuario() <= 0) {
            throw new IllegalArgumentException(
                    "Debes indicar un usuario válido.");
        }

        if (pedido.getIdEstado() <= 0) {
            throw new IllegalArgumentException(
                    "Debes indicar un estado válido.");
        }

        if ("MESA".equals(tipo)) {
            if (pedido.getIdMesa() == null
                    || pedido.getIdMesa() <= 0) {
                throw new IllegalArgumentException(
                        "Debes seleccionar una mesa.");
            }
        } else {
            pedido.setIdMesa(null);
        }

        if ("DELIVERY".equals(tipo)) {
            if (pedido.getDireccionDelivery() == null
                    || pedido.getDireccionDelivery().isBlank()) {
                throw new IllegalArgumentException(
                        "Debes indicar la dirección de delivery.");
            }
        }

        if (pedido.getTotal() == null
                || pedido.getTotal() < 0) {
            throw new IllegalArgumentException(
                    "El total del pedido no puede ser negativo.");
        }

        return pedidoDAO.guardar(pedido);
    }

    public void actualizarEstado(int idPedido, int idEstado) {

        if (idPedido <= 0) {
            throw new IllegalArgumentException(
                    "El ID del pedido debe ser mayor que cero.");
        }

        if (idEstado <= 0) {
            throw new IllegalArgumentException(
                    "El ID del estado debe ser mayor que cero.");
        }

        if (pedidoDAO.buscarPorId(idPedido) == null) {
            throw new IllegalArgumentException(
                    "No se encontró el pedido.");
        }

        pedidoDAO.actualizarEstado(idPedido, idEstado);
    }

    public int guardarPedidoCompleto(
            Pedido pedido, List<DetallePedido> detalles) {

        if (pedido == null) {
            throw new IllegalArgumentException(
                    "El pedido no puede ser nulo.");
        }

        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException(
                    "Debes agregar al menos un producto al pedido.");
        }

        if (pedido.getTipoAtencion() == null
                || pedido.getTipoAtencion().isBlank()) {
            throw new IllegalArgumentException(
                    "Debes indicar el tipo de atención.");
        }

        String tipo = pedido.getTipoAtencion();

        if (!tipo.equals("MESA")
                && !tipo.equals("PARA_LLEVAR")
                && !tipo.equals("DELIVERY")) {
            throw new IllegalArgumentException(
                    "El tipo de atención no es válido.");
        }

        if (pedido.getIdUsuario() <= 0) {
            throw new IllegalArgumentException(
                    "Debes indicar un usuario válido.");
        }

        if (pedido.getIdEstado() <= 0) {
            throw new IllegalArgumentException(
                    "Debes indicar un estado válido.");
        }

        if ("MESA".equals(tipo)) {
            if (pedido.getIdMesa() == null
                    || pedido.getIdMesa() <= 0) {
                throw new IllegalArgumentException(
                        "Debes seleccionar una mesa.");
            }
        } else {
            pedido.setIdMesa(null);
        }

        if ("DELIVERY".equals(tipo)) {
            if (pedido.getDireccionDelivery() == null
                    || pedido.getDireccionDelivery().isBlank()) {
                throw new IllegalArgumentException(
                        "Debes indicar la dirección de delivery.");
            }
        }

        for (DetallePedido detalle : detalles) {

            if (detalle == null
                    || detalle.getIdProducto() <= 0
                    || detalle.getCantidad() <= 0) {
                throw new IllegalArgumentException(
                        "Verifica los productos y sus cantidades.");
            }
        }

        return pedidoDAO.guardarCompleto(pedido, detalles);
    }
}
