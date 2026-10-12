package com.dwi.prytecnoservicio.dao;

import com.dwi.prytecnoservicio.model.Producto;
import java.util.List;

public interface ProductoDAO {

    List<Producto> listar();

    Producto buscarPorId(int idProducto);
    
    void guardar(Producto producto);
    
    void actualizar(Producto producto);
    
    void eliminar(int idProducto);
}
