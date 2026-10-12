/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.service;

import com.dwi.prytecnoservicio.dao.EstadoPedidoDAO;
import com.dwi.prytecnoservicio.factory.DAOFactory;
import com.dwi.prytecnoservicio.model.EstadoPedido;
import java.util.List;

/**
 *
 * @author Miguel Angel
 */
public class EstadoPedidoService {

    private final EstadoPedidoDAO estadoPedidoDAO;

    public EstadoPedidoService() {
        estadoPedidoDAO
                = DAOFactory.crearEstadoPedidoDAO();
    }

    public List<EstadoPedido> listarEstados() {
        return estadoPedidoDAO.listar();
    }
}
