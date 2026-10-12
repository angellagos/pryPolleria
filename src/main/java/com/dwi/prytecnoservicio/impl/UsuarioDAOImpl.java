/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.impl;

import com.dwi.prytecnoservicio.dao.UsuarioDAO;
import com.dwi.prytecnoservicio.model.Usuario;
import config.ConexionDBSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Miguel Angel
 */
public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public Usuario buscarPorCorreo(String correo) {

        String sql = """
            SELECT id_usuario, nombre, apellido, correo,
                   password, activo, eliminado, id_rol
            FROM usuarios
            WHERE correo = ?
              AND eliminado = 0
            """;

        try (Connection con =
                    ConexionDBSingleton.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Usuario usuario = new Usuario();

                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setActivo(rs.getBoolean("activo"));
                    usuario.setEliminado(rs.getBoolean("eliminado"));
                    usuario.setIdRol(rs.getInt("id_rol"));

                    return usuario;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error al buscar el usuario por correo.", e
            );
        }

        return null;
    }
}