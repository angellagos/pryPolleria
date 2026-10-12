/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.service;

import com.dwi.prytecnoservicio.dao.UsuarioDAO;
import com.dwi.prytecnoservicio.factory.DAOFactory;
import com.dwi.prytecnoservicio.model.Usuario;

/**
 *
 * @author Miguel Angel
 */
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = DAOFactory.crearUsuarioDAO();
    }

    public Usuario buscarPorCorreo(String correo) {

        if (correo == null || correo.trim().isEmpty()) {
            return null;
        }

        return usuarioDAO.buscarPorCorreo(correo.trim());
    }
}
