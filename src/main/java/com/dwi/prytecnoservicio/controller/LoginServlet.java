/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.controller;

import com.dwi.prytecnoservicio.model.Usuario;
import com.dwi.prytecnoservicio.service.AutenticacionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author Miguel Angel
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private AutenticacionService authService;

    @Override
    public void init() throws ServletException {
        authService = new AutenticacionService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                "/WEB-INF/views/login.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        Usuario usuario
                = authService.iniciarSesion(correo, password);

        if (usuario == null) {
            request.setAttribute(
                    "error",
                    "Correo o contraseña incorrectos, o cuenta inactiva."
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/login.jsp"
            ).forward(request, response);

            return;
        }

        HttpSession sesion = request.getSession(true);

        sesion.setAttribute("idUsuario", usuario.getIdUsuario());
        sesion.setAttribute("nombreUsuario", usuario.getNombre());
        sesion.setAttribute("idRol", usuario.getIdRol());

        response.sendRedirect(
                request.getContextPath() + "/pedidos"
        );
    }
}
