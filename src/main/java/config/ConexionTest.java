package config;

import java.sql.Connection;
import java.sql.SQLException;

public class ConexionTest {

    public static void main(String[] args) throws SQLException {
        //INVOCA MÉTODO DE COMPONENTE DE CONEXIÓN A BD
        //Connection cn = ConexionDB.conectar();
        try (Connection cn
                = ConexionDBSingleton.getInstancia().conectar()) {

            System.out.println("CONEXION CON EXITO");

        } catch (SQLException e) {
            System.out.println("ERROR DE CONEXION");
            e.printStackTrace();
        }
    }

}
