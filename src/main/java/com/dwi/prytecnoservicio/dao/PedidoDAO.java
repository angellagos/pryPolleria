/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.dao;

import com.dwi.prytecnoservicio.model.DetallePedido;
import com.dwi.prytecnoservicio.model.Pedido;
import java.util.List;

/**
 *
 * @author Miguel Angel
 */
public interface PedidoDAO {

    List<Pedido> listar();

    Pedido buscarPorId(int idPedido);

    int guardar(Pedido pedido);

    void actualizarEstado(int idPedido, int idEstado);

    int guardarCompleto(Pedido pedido, List<DetallePedido> detalles);
}
