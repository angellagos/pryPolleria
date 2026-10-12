/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.impl;

import com.dwi.prytecnoservicio.dao.PedidoDAO;
import com.dwi.prytecnoservicio.model.DetallePedido;
import com.dwi.prytecnoservicio.model.Pedido;
import config.ConexionDBSingleton;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miguel Angel
 */
public class PedidoDAOImpl implements PedidoDAO {

    @Override
    public List<Pedido> listar() {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = """
            SELECT id_pedido, fecha_pedido, tipo_atencion,
                   direccion_delivery, observacion, total,
                   eliminado, id_usuario, id_mesa, id_estado
            FROM pedidos
            WHERE eliminado = 0
            ORDER BY fecha_pedido DESC
            """;

        try (Connection conexion
                = ConexionDBSingleton.getInstancia().conectar(); PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pedidos.add(mapearPedido(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar los pedidos.", e);
        }

        return pedidos;
    }

    @Override
    public Pedido buscarPorId(int idPedido) {

        String sql = """
            SELECT id_pedido, fecha_pedido, tipo_atencion,
                   direccion_delivery, observacion, total,
                   eliminado, id_usuario, id_mesa, id_estado
            FROM pedidos
            WHERE id_pedido = ? AND eliminado = 0
            """;

        try (Connection conexion
                = ConexionDBSingleton.getInstancia().conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPedido);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPedido(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar el pedido.", e);
        }

        return null;
    }

    @Override
    public int guardar(Pedido pedido) {

        String sql = """
            INSERT INTO pedidos (
                fecha_pedido, tipo_atencion, direccion_delivery,
                observacion, total, eliminado, id_mesa,
                id_usuario, id_estado
            )
            VALUES (?, ?, ?, ?, ?, 0, ?, ?, ?)
            """;

        try (Connection conexion
                = ConexionDBSingleton.getInstancia().conectar(); PreparedStatement ps = conexion.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            if (pedido.getFechaPedido() == null) {
                ps.setTimestamp(1,
                        new Timestamp(System.currentTimeMillis()));
            } else {
                ps.setTimestamp(1,
                        Timestamp.valueOf(pedido.getFechaPedido()));
            }

            ps.setString(2, pedido.getTipoAtencion());
            ps.setString(3, pedido.getDireccionDelivery());
            ps.setString(4, pedido.getObservacion());
            ps.setDouble(5,
                    pedido.getTotal() == null ? 0.0 : pedido.getTotal());

            if (pedido.getIdMesa() == null) {
                ps.setNull(6, java.sql.Types.INTEGER);
            } else {
                ps.setInt(6, pedido.getIdMesa());
            }

            ps.setInt(7, pedido.getIdUsuario());
            ps.setInt(8, pedido.getIdEstado());

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new SQLException(
                        "No se pudo registrar el pedido.");
            }

            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    return claves.getInt(1);
                }
            }

            throw new SQLException(
                    "No se pudo obtener el ID del pedido.");

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al registrar el pedido.", e);
        }
    }

    @Override
    public void actualizarEstado(int idPedido, int idEstado) {

        String sql = """
            UPDATE pedidos
            SET id_estado = ?
            WHERE id_pedido = ? AND eliminado = 0
            """;

        try (Connection conexion
                = ConexionDBSingleton.getInstancia().conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idEstado);
            ps.setInt(2, idPedido);

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException(
                        "No se encontró el pedido para actualizar.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar el estado del pedido.", e);
        }
    }

    private Pedido mapearPedido(ResultSet rs)
            throws SQLException {

        Pedido pedido = new Pedido();

        pedido.setIdPedido(rs.getInt("id_pedido"));

        Timestamp fecha = rs.getTimestamp("fecha_pedido");
        if (fecha != null) {
            pedido.setFechaPedido(fecha.toLocalDateTime());
        }

        pedido.setTipoAtencion(rs.getString("tipo_atencion"));
        pedido.setDireccionDelivery(
                rs.getString("direccion_delivery"));
        pedido.setObservacion(rs.getString("observacion"));
        pedido.setTotal(rs.getDouble("total"));
        pedido.setEliminado(rs.getBoolean("eliminado"));
        pedido.setIdUsuario(rs.getInt("id_usuario"));

        int idMesa = rs.getInt("id_mesa");
        if (rs.wasNull()) {
            pedido.setIdMesa(null);
        } else {
            pedido.setIdMesa(idMesa);
        }

        pedido.setIdEstado(rs.getInt("id_estado"));

        return pedido;
    }

    @Override
    public int guardarCompleto(
            Pedido pedido, List<DetallePedido> detalles) {

        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException(
                    "El pedido debe contener al menos un producto.");
        }

        String sqlPrecio = """
        SELECT precio
        FROM productos
        WHERE id_producto = ?
          AND disponible = 1
          AND eliminado = 0
        """;

        String sqlPedido = """
        INSERT INTO pedidos (
            fecha_pedido, tipo_atencion, direccion_delivery,
            observacion, total, eliminado, id_mesa,
            id_usuario, id_estado
        )
        VALUES (?, ?, ?, ?, ?, 0, ?, ?, ?)
        """;

        String sqlDetalle = """
        INSERT INTO detalle_pedido (
            id_pedido, id_producto, cantidad,
            precio_unitario, subtotal, observacion
        )
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conexion
                = ConexionDBSingleton.getInstancia().conectar()) {

            conexion.setAutoCommit(false);

            try {
                BigDecimal total = BigDecimal.ZERO;

                // Validar productos y calcular el total
                for (DetallePedido detalle : detalles) {

                    if (detalle == null
                            || detalle.getIdProducto() <= 0
                            || detalle.getCantidad() <= 0) {
                        throw new IllegalArgumentException(
                                "Producto o cantidad no válidos.");
                    }

                    BigDecimal precio;

                    try (PreparedStatement psPrecio
                            = conexion.prepareStatement(sqlPrecio)) {

                        psPrecio.setInt(1, detalle.getIdProducto());

                        try (ResultSet rs = psPrecio.executeQuery()) {
                            if (!rs.next()) {
                                throw new IllegalArgumentException(
                                        "Un producto no existe o no está disponible.");
                            }

                            precio = rs.getBigDecimal("precio");
                        }
                    }

                    BigDecimal subtotal = precio.multiply(
                            BigDecimal.valueOf(detalle.getCantidad()));

                    detalle.setPrecioUnitario(precio.doubleValue());
                    detalle.setSubtotal(subtotal.doubleValue());

                    total = total.add(subtotal);
                }

                pedido.setTotal(total.doubleValue());

                // Registrar la cabecera del pedido
                int idPedido;

                try (PreparedStatement psPedido
                        = conexion.prepareStatement(
                                sqlPedido,
                                java.sql.Statement.RETURN_GENERATED_KEYS)) {

                    if (pedido.getFechaPedido() == null) {
                        psPedido.setTimestamp(
                                1, new java.sql.Timestamp(
                                        System.currentTimeMillis()));
                    } else {
                        psPedido.setTimestamp(
                                1, java.sql.Timestamp.valueOf(
                                        pedido.getFechaPedido()));
                    }

                    psPedido.setString(2, pedido.getTipoAtencion());
                    psPedido.setString(3, pedido.getDireccionDelivery());
                    psPedido.setString(4, pedido.getObservacion());
                    psPedido.setBigDecimal(5, total);

                    if (pedido.getIdMesa() == null) {
                        psPedido.setNull(6, Types.INTEGER);
                    } else {
                        psPedido.setInt(6, pedido.getIdMesa());
                    }

                    psPedido.setInt(7, pedido.getIdUsuario());
                    psPedido.setInt(8, pedido.getIdEstado());

                    psPedido.executeUpdate();

                    try (ResultSet claves = psPedido.getGeneratedKeys()) {
                        if (!claves.next()) {
                            throw new SQLException(
                                    "No se pudo obtener el ID del pedido.");
                        }

                        idPedido = claves.getInt(1);
                    }
                }

                // Registrar los detalles del pedido
                try (PreparedStatement psDetalle
                        = conexion.prepareStatement(sqlDetalle)) {

                    for (DetallePedido detalle : detalles) {

                        BigDecimal precio = BigDecimal.valueOf(
                                detalle.getPrecioUnitario());

                        BigDecimal subtotal = precio.multiply(
                                BigDecimal.valueOf(detalle.getCantidad()));

                        psDetalle.setInt(1, idPedido);
                        psDetalle.setInt(2, detalle.getIdProducto());
                        psDetalle.setInt(3, detalle.getCantidad());
                        psDetalle.setBigDecimal(4, precio);
                        psDetalle.setBigDecimal(5, subtotal);
                        psDetalle.setString(6, detalle.getObservacion());

                        psDetalle.addBatch();
                    }

                    psDetalle.executeBatch();
                }

                conexion.commit();
                return idPedido;

            } catch (Exception e) {

                try {
                    conexion.rollback();
                } catch (SQLException errorRollback) {
                    e.addSuppressed(errorRollback);
                }

                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }

                throw new RuntimeException(
                        "Error al registrar el pedido completo.", e);

            } finally {
                try {
                    conexion.setAutoCommit(true);
                } catch (SQLException e) {
                    // La conexión se cerrará al salir del try-with-resources.
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error de base de datos al registrar el pedido.", e);
        }
    }
}
