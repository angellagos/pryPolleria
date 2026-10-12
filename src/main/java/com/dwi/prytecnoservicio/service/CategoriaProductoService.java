/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.service;

import com.dwi.prytecnoservicio.dao.CategoriaProductoDAO;
import com.dwi.prytecnoservicio.factory.DAOFactory;
import com.dwi.prytecnoservicio.model.CategoriaProducto;
import java.util.List;

/**
 *
 * @author Miguel Angel
 */
public class CategoriaProductoService {
     private final CategoriaProductoDAO categoriaDAO;

    public CategoriaProductoService() {
        categoriaDAO = DAOFactory.crearCategoriaProductoDAO();
    }

    public List<CategoriaProducto> listarCategorias() {
        return categoriaDAO.listar();
    }
}
