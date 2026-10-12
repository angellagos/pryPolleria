/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.service;

import com.dwi.prytecnoservicio.model.Usuario;

/**
 *
 * @author Miguel Angel
 */
public class AutenticacionService {

    private final UsuarioService usuarioService;

    public AutenticacionService() {
        this.usuarioService = new UsuarioService();
    }

    public Usuario iniciarSesion(String correo, String password) {

        if (correo == null || correo.trim().isEmpty()
                || password == null || password.isEmpty()) {
            return null;
        }

        Usuario usuario =
                usuarioService.buscarPorCorreo(correo.trim());

        if (usuario == null) {
            return null;
        }

        if (!usuario.isActivo() || usuario.isEliminado()) {
            return null;
        }

        if (!usuario.getPassword().equals(password)) {
            return null;
        }

        return usuario;
    }
}