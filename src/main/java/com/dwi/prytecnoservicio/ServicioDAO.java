package com.dwi.prytecnoservicio;

import java.sql.SQLException;
import java.util.List;

public interface ServicioDAO {
    List<Servicio> listar() throws SQLException;
    
    Servicio buscarPorNombre(String nombre) throws SQLException;
}
