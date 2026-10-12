/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.impl;

import com.dwi.prytecnoservicio.dao.DetallePedidoDAO;
import com.dwi.prytecnoservicio.model.DetallePedido;
import config.ConexionDBSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miguel Angel
 */
public class DetallePedidoDAOImpl implements DetallePedidoDAO {

    @Override
    public List<DetallePedido> listarPorPedido(int idPedido) {

        List<DetallePedido> detalles = new ArrayList<>();

        String sql = """
            SELECT id_detalle, id_pedido, id_producto,
                   cantidad, precio_unitario, subtotal, observacion
            FROM detalle_pedido
            WHERE id_pedido = ?
            ORDER BY id_detalle
            """;

        try (Connection conexion =
                    ConexionDBSingleton.getInstancia().conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPedido);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    DetallePedido detalle = new DetallePedido();

                    detalle.setIdDetalle(
                            rs.getInt("id_detalle"));

                    detalle.setIdPedido(
                            rs.getInt("id_pedido"));

                    detalle.setIdProducto(
                            rs.getInt("id_producto"));

                    detalle.setCantidad(
                            rs.getInt("cantidad"));

                    detalle.setPrecioUnitario(
                            rs.getDouble("precio_unitario"));

                    detalle.setSubtotal(
                            rs.getDouble("subtotal"));

                    detalle.setObservacion(
                            rs.getString("observacion"));

                    detalles.add(detalle);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al consultar los detalles del pedido.", e);
        }

        return detalles;
    }

    @Override
    public void guardar(
            int idPedido, List<DetallePedido> detalles) {

        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException(
                    "El pedido debe contener al menos un producto.");
        }

        String sql = """
            INSERT INTO detalle_pedido (
                id_pedido, id_producto, cantidad,
                precio_unitario, subtotal, observacion
            )
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conexion =
                    ConexionDBSingleton.getInstancia().conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            for (DetallePedido detalle : detalles) {

                ps.setInt(1, idPedido);
                ps.setInt(2, detalle.getIdProducto());
                ps.setInt(3, detalle.getCantidad());
                ps.setDouble(4, detalle.getPrecioUnitario());

                double subtotal = detalle.getCantidad()
                        * detalle.getPrecioUnitario();

                ps.setDouble(5, subtotal);
                ps.setString(6, detalle.getObservacion());

                ps.addBatch();
            }

            ps.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al guardar los productos del pedido.", e);
        }
    }
}
