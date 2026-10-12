package com.dwi.prytecnoservicio;

import config.ConexionDBSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAOImpl implements ServicioDAO{

        @Override
    public List<Servicio> listar() throws SQLException {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT id_servicio, nombre, descripcion FROM servicio";

        try (//Connection cn = ConexionDB.conectar();
             Connection cn = ConexionDBSingleton.getInstancia().conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
                
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Servicio(
                    rs.getInt("id_servicio"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                ));
            }
        }
        return lista;
    }

    @Override
    public Servicio buscarPorNombre(String nombre) throws SQLException {
      String sql = "SELECT id_servicio, nombre, descripcion "
               + "FROM servicio WHERE nombre = ?";

    try (Connection cn = ConexionDB.conectar();  
    //try (Connection cn = ConexionDBSingleton.getInstancia().conectar();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        ps.setString(1, nombre);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Servicio(
                    rs.getInt("id_servicio"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
            }
        }
    }
    return null;
    }   
}
