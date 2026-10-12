/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.impl;

import com.dwi.prytecnoservicio.dao.MesaDAO;
import com.dwi.prytecnoservicio.model.Mesa;
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
public class MesaDAOImpl implements MesaDAO {

    @Override
    public List<Mesa> listar() {
        List<Mesa> mesas = new ArrayList<>();

        String sql = """
            SELECT id_mesa, numero, capacidad, estado, eliminado
            FROM mesas
            WHERE eliminado = 0
            ORDER BY numero
            """;

        try (Connection conexion
                = ConexionDBSingleton.getInstancia().conectar(); PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Mesa mesa = new Mesa();

                mesa.setIdMesa(rs.getInt("id_mesa"));
                mesa.setNumero(rs.getInt("numero"));
                mesa.setCapacidad(rs.getInt("capacidad"));
                mesa.setEstado(rs.getString("estado"));
                mesa.setEliminado(rs.getBoolean("eliminado"));

                mesas.add(mesa);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar las mesas.", e
            );
        }

        return mesas;
    }

    @Override
    public Mesa buscarPorId(int idMesa) {
        String sql = """
            SELECT id_mesa, numero, capacidad, estado, eliminado
            FROM mesas
            WHERE id_mesa = ?
              AND eliminado = 0
            """;

        try (Connection conexion
                = ConexionDBSingleton.getInstancia().conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idMesa);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Mesa mesa = new Mesa();

                    mesa.setIdMesa(rs.getInt("id_mesa"));
                    mesa.setNumero(rs.getInt("numero"));
                    mesa.setCapacidad(rs.getInt("capacidad"));
                    mesa.setEstado(rs.getString("estado"));
                    mesa.setEliminado(rs.getBoolean("eliminado"));

                    return mesa;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar la mesa.", e
            );
        }

        return null;
    }

    @Override
    public void guardar(Mesa mesa) {
        String sql = """
            INSERT INTO mesas (numero, capacidad, estado, eliminado)
            VALUES (?, ?, ?, 0)
            """;

        try (Connection conexion
                = ConexionDBSingleton.getInstancia().conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, mesa.getNumero());
            ps.setInt(2, mesa.getCapacidad());
            ps.setString(3, mesa.getEstado());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al registrar la mesa.", e
            );
        }
    }

    @Override
    public void actualizar(Mesa mesa) {
        String sql = """
            UPDATE mesas
            SET numero = ?, capacidad = ?, estado = ?
            WHERE id_mesa = ?
              AND eliminado = 0
            """;

        try (Connection conexion
                = ConexionDBSingleton.getInstancia().conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, mesa.getNumero());
            ps.setInt(2, mesa.getCapacidad());
            ps.setString(3, mesa.getEstado());
            ps.setInt(4, mesa.getIdMesa());

            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas == 0) {
                throw new RuntimeException(
                        "No se encontró la mesa o está eliminada."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar la mesa.", e
            );
        }
    }

    @Override
    public void eliminar(int idMesa) {

        String sql = """
        UPDATE mesas
        SET eliminado = 1
        WHERE id_mesa = ? AND eliminado = 0
        """;

        try (Connection conexion
                = ConexionDBSingleton.getInstancia().conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idMesa);

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException(
                        "No se encontró la mesa o ya fue eliminada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar la mesa.", e);
        }
    }
}
