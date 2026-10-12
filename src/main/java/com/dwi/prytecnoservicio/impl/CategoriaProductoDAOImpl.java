/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.impl;

import com.dwi.prytecnoservicio.dao.CategoriaProductoDAO;
import com.dwi.prytecnoservicio.model.CategoriaProducto;
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
public class CategoriaProductoDAOImpl implements CategoriaProductoDAO {

    @Override
    public List<CategoriaProducto> listar() {
        List<CategoriaProducto> categorias = new ArrayList<>();

        String sql = """
            SELECT id_categoria, nombre, descripcion, eliminado
            FROM categorias_productos
            WHERE eliminado = 0
            ORDER BY nombre
            """;

        try (Connection conexion
                = ConexionDBSingleton.getInstancia().conectar(); PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CategoriaProducto categoria
                        = new CategoriaProducto();

                categoria.setIdCategoria(
                        rs.getInt("id_categoria")
                );
                categoria.setNombre(rs.getString("nombre"));
                categoria.setDescripcion(
                        rs.getString("descripcion")
                );
                categoria.setEliminado(
                        rs.getBoolean("eliminado")
                );

                categorias.add(categoria);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar las categorías.", e
            );
        }

        return categorias;
    }
}
