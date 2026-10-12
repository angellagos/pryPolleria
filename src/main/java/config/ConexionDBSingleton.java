package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDBSingleton {

    // OBJETO ESTÁTICO CONEXIÓN
    private static ConexionDBSingleton instancia;
    // PARÁMETROS DE CONEXIÓN
    private final String url = "jdbc:mysql://localhost:3306/bd_polleria"
            + "?useSSL=false&serverTimezone=UTC";
    private final String usuario = "root";
    private final String clave = "";

    //CONSTRUCTOR SIN PARÁMETROS
    private ConexionDBSingleton() {
    }

    //MÉTODO QUE RETORNA UN OBJETO CONEXIÓN SINGLETON (instancia)
    public static synchronized ConexionDBSingleton getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDBSingleton();
        }
        return instancia;
    }

    //MÉTODO PARA OBTENER UNA CONEXIÓN
    public Connection conectar() throws SQLException {
        //return DriverManager.getConnection(url, usuario, clave);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    url,
                    usuario,
                    clave
            );

        } catch (ClassNotFoundException e) {

            throw new SQLException(
                    "DRIVER MYSQL NO ENCONTRADO",
                    e
            );
        }
    }

}
