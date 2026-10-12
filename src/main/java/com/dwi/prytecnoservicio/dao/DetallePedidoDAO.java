/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.dao;

import com.dwi.prytecnoservicio.model.DetallePedido;
import java.util.List;

/**
 *
 * @author Miguel Angel
 */
public interface DetallePedidoDAO {

    List<DetallePedido> listarPorPedido(int idPedido);

    void guardar(int idPedido, List<DetallePedido> detalles);
}
