package com.dwi.prytecnoservicio.impl;

import com.dwi.prytecnoservicio.dao.ProductoDAO;
import com.dwi.prytecnoservicio.model.CategoriaProducto;
import com.dwi.prytecnoservicio.model.Producto;
import config.ConexionDBSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();

        String sql = """
            SELECT p.id_producto, p.nombre, p.descripcion,
                   p.precio, p.disponible,
                   c.id_categoria, c.nombre AS categoria
            FROM productos p
            INNER JOIN categorias_productos c
                ON p.id_categoria = c.id_categoria
            WHERE p.eliminado = 0
              AND c.eliminado = 0
            ORDER BY p.nombre
            """;

        try (Connection conexion
                = ConexionDBSingleton.getInstancia().conectar(); PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CategoriaProducto categoria = new CategoriaProducto();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNombre(rs.getString("categoria"));

                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setDisponible(rs.getBoolean("disponible"));
                producto.setEliminado(false);
                producto.setCategoria(categoria);

                productos.add(producto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar los productos.", e
            );
        }

        return productos;
    }

    @Override
    public Producto buscarPorId(int idProducto) {
        String sql = """
            SELECT p.id_producto, p.nombre, p.descripcion,
                   p.precio, p.disponible,
                   c.id_categoria, c.nombre AS categoria
            FROM productos p
            INNER JOIN categorias_productos c
                ON p.id_categoria = c.id_categoria
            WHERE p.id_producto = ?
              AND p.eliminado = 0
              AND c.eliminado = 0
            """;

        try (Connection conexion
                = ConexionDBSingleton.getInstancia().conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idProducto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CategoriaProducto categoria
                            = new CategoriaProducto();

                    categoria.setIdCategoria(
                            rs.getInt("id_categoria")
                    );
                    categoria.setNombre(
                            rs.getString("categoria")
                    );

                    Producto producto = new Producto();
                    producto.setIdProducto(
                            rs.getInt("id_producto")
                    );
                    producto.setNombre(rs.getString("nombre"));
                    producto.setDescripcion(
                            rs.getString("descripcion")
                    );
                    producto.setPrecio(rs.getDouble("precio"));
                    producto.setDisponible(
                            rs.getBoolean("disponible")
                    );
                    producto.setEliminado(false);
                    producto.setCategoria(categoria);

                    return producto;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar el producto.", e
            );
        }

        return null;
    }

    @Override
    public void guardar(Producto producto) {

        String sql = """
        INSERT INTO productos
            (nombre, descripcion, precio, disponible,
             eliminado, id_categoria)
        VALUES (?, ?, ?, ?, 0, ?)
        """;

        try (Connection conexion
                = ConexionDBSingleton.getInstancia().conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setBoolean(4, producto.isDisponible());
            ps.setInt(5, producto.getCategoria().getIdCategoria());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al registrar el producto.", e
            );
        }
    }

    @Override
    public void actualizar(Producto producto) {

        String sql = """
        UPDATE productos
        SET nombre = ?,
            descripcion = ?,
            precio = ?,
            disponible = ?,
            id_categoria = ?
        WHERE id_producto = ?
          AND eliminado = 0
        """;

        try (Connection conexion
                = ConexionDBSingleton.getInstancia().conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setBoolean(4, producto.isDisponible());
            ps.setInt(5, producto.getCategoria().getIdCategoria());
            ps.setInt(6, producto.getIdProducto());

            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas == 0) {
                throw new RuntimeException(
                        "No se encontró el producto o está eliminado."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar el producto.", e
            );
        }
    }

    @Override
    public void eliminar(int idProducto) {

        String sql = """
        UPDATE productos
        SET eliminado = 1
        WHERE id_producto = ?
          AND eliminado = 0
        """;

        try (Connection conexion
                = ConexionDBSingleton.getInstancia().conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idProducto);

            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas == 0) {
                throw new RuntimeException(
                        "No se encontró el producto o ya fue eliminado."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar el producto.", e
            );
        }
    }
}
