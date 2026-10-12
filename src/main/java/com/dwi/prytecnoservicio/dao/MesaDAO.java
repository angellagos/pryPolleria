/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.dao;

import com.dwi.prytecnoservicio.model.Mesa;
import java.util.List;

/**
 *
 * @author Miguel Angel
 */
public interface MesaDAO {

    List<Mesa> listar();

    Mesa buscarPorId(int idMesa);

    void guardar(Mesa mesa);

    void actualizar(Mesa mesa);

    void eliminar(int idMesa);

}
