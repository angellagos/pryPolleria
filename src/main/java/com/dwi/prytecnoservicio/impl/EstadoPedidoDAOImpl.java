/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.impl;

import com.dwi.prytecnoservicio.dao.EstadoPedidoDAO;
import com.dwi.prytecnoservicio.model.EstadoPedido;
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
public class EstadoPedidoDAOImpl implements EstadoPedidoDAO {

    @Override
    public List<EstadoPedido> listar() {

        List<EstadoPedido> estados = new ArrayList<>();

        String sql = """
            SELECT id_estado, nombre, descripcion
            FROM estados_pedido
            ORDER BY id_estado
            """;

        try (Connection conexion =
                    ConexionDBSingleton.getInstancia().conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                EstadoPedido estado = new EstadoPedido();

                estado.setIdEstado(rs.getInt("id_estado"));
                estado.setNombre(rs.getString("nombre"));
                estado.setDescripcion(rs.getString("descripcion"));

                estados.add(estado);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar los estados de los pedidos.", e);
        }

        return estados;
    }
}
