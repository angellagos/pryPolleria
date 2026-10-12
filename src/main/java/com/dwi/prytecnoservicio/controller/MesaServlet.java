/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.controller;

import com.dwi.prytecnoservicio.model.Mesa;
import com.dwi.prytecnoservicio.service.MesaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Miguel Angel
 */
@WebServlet("/mesas")
public class MesaServlet extends HttpServlet {

    private MesaService mesaService;

    @Override
    public void init() throws ServletException {
        mesaService = new MesaService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        List<Mesa> mesas = mesaService.listarMesas();

        request.setAttribute("mesas", mesas);

        request.getRequestDispatcher(
                "/WEB-INF/views/mesas.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String accion = request.getParameter("accion");

            // Eliminar una mesa
            if ("eliminar".equals(accion)) {

                int idMesa = Integer.parseInt(
                        request.getParameter("idMesa"));

                mesaService.eliminarMesa(idMesa);

                response.sendRedirect(
                        request.getContextPath()
                        + "/mesas?eliminacion=exito");

                return;
            }

            // Actualizar número y capacidad de una mesa
            if ("actualizarDatos".equals(accion)) {

                int idMesa = Integer.parseInt(
                        request.getParameter("idMesa"));

                int numero = Integer.parseInt(
                        request.getParameter("numero"));

                int capacidad = Integer.parseInt(
                        request.getParameter("capacidad"));

                Mesa mesa = mesaService.buscarMesaPorId(idMesa);

                if (mesa == null) {
                    response.sendError(
                            HttpServletResponse.SC_NOT_FOUND,
                            "No se encontró la mesa.");
                    return;
                }

                mesa.setNumero(numero);
                mesa.setCapacidad(capacidad);

                mesaService.actualizarMesa(mesa);

                response.sendRedirect(
                        request.getContextPath()
                        + "/mesas?edicion=exito");

                return;
            }

            // Actualizar el estado de una mesa
            if ("actualizarEstado".equals(accion)) {

                int idMesa = Integer.parseInt(
                        request.getParameter("idMesa"));

                String estado = request.getParameter("estado");

                Mesa mesa = mesaService.buscarMesaPorId(idMesa);

                if (mesa == null) {
                    response.sendError(
                            HttpServletResponse.SC_NOT_FOUND,
                            "No se encontró la mesa.");
                    return;
                }

                mesa.setEstado(estado);
                mesaService.actualizarMesa(mesa);

                response.sendRedirect(
                        request.getContextPath()
                        + "/mesas?actualizacion=exito");

                return;
            }

            // Registrar una mesa nueva
            int numero = Integer.parseInt(
                    request.getParameter("numero"));

            int capacidad = Integer.parseInt(
                    request.getParameter("capacidad"));

            Mesa mesa = new Mesa();
            mesa.setNumero(numero);
            mesa.setCapacidad(capacidad);
            mesa.setEstado("LIBRE");
            mesa.setEliminado(false);

            mesaService.guardarMesa(mesa);

            response.sendRedirect(
                    request.getContextPath()
                    + "/mesas?registro=exito");

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "El ID, número y capacidad deben ser valores numéricos.");

        } catch (IllegalArgumentException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage());
        }
    }
}
