/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.dao;

import com.dwi.prytecnoservicio.model.Usuario;

/**
 *
 * @author Miguel Angel
 */
public interface UsuarioDAO {

    Usuario buscarPorCorreo(String correo);
}
