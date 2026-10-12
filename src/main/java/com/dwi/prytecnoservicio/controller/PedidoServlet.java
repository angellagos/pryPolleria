/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.controller;

import com.dwi.prytecnoservicio.model.DetallePedido;
import com.dwi.prytecnoservicio.model.EstadoPedido;
import com.dwi.prytecnoservicio.model.Mesa;
import com.dwi.prytecnoservicio.model.Pedido;
import com.dwi.prytecnoservicio.model.Producto;
import com.dwi.prytecnoservicio.service.EstadoPedidoService;
import com.dwi.prytecnoservicio.service.MesaService;
import com.dwi.prytecnoservicio.service.PedidoService;
import com.dwi.prytecnoservicio.service.ProductoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miguel Angel
 */
@WebServlet("/pedidos")
public class PedidoServlet extends HttpServlet {

    private PedidoService pedidoService;
    private ProductoService productoService;
    private MesaService mesaService;
    private EstadoPedidoService estadoPedidoService;

    @Override
    public void init() throws ServletException {
        pedidoService = new PedidoService();
        productoService = new ProductoService();
        mesaService = new MesaService();
        estadoPedidoService = new EstadoPedidoService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<Pedido> pedidos = pedidoService.listarPedidos();

        request.setAttribute("pedidos", pedidos);

        List<Producto> productos = productoService.listarProductos();
        List<Mesa> mesas = mesaService.listarMesas();
        List<EstadoPedido> estados = estadoPedidoService.listarEstados();

        request.setAttribute("productos", productos);
        request.setAttribute("mesas", mesas);
        request.setAttribute("estados", estados);

        request.getRequestDispatcher(
                "/WEB-INF/views/pedidos.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // Verificar la sesión del usuario
            HttpSession sesion = request.getSession(false);

            if (sesion == null
                    || sesion.getAttribute("idUsuario") == null) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Debes iniciar sesión para registrar un pedido.");
                return;
            }

            int idUsuario = Integer.parseInt(
                    sesion.getAttribute("idUsuario").toString());

            // Datos generales
            String tipoAtencion
                    = request.getParameter("tipoAtencion");

            String idMesaTexto = request.getParameter("idMesa");

            String direccionDelivery
                    = request.getParameter("direccionDelivery");

            String observacion
                    = request.getParameter("observacion");

            Pedido pedido = new Pedido();

            pedido.setTipoAtencion(tipoAtencion);
            pedido.setDireccionDelivery(direccionDelivery);
            pedido.setObservacion(observacion);
            pedido.setIdUsuario(idUsuario);
            pedido.setFechaPedido(java.time.LocalDateTime.now());

            if ("MESA".equals(tipoAtencion)) {
                if (idMesaTexto == null || idMesaTexto.isBlank()) {
                    throw new IllegalArgumentException(
                            "Debes seleccionar una mesa.");
                }

                pedido.setIdMesa(Integer.parseInt(idMesaTexto));
            } else {
                pedido.setIdMesa(null);
            }

            // Obtener el estado inicial desde la base de datos
            int idEstadoPendiente = estadoPedidoService
                    .listarEstados()
                    .stream()
                    .filter(e -> "PENDIENTE".equals(e.getNombre()))
                    .mapToInt(EstadoPedido::getIdEstado)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                    "No se encontró el estado PENDIENTE."));

            pedido.setIdEstado(idEstadoPendiente);

            // Recuperar los productos seleccionados
            String[] idsProducto
                    = request.getParameterValues("idProducto");

            String[] cantidades
                    = request.getParameterValues("cantidad");

            if (idsProducto == null || cantidades == null
                    || idsProducto.length != cantidades.length
                    || idsProducto.length == 0) {
                throw new IllegalArgumentException(
                        "Debes agregar al menos un producto.");
            }

            ArrayList<DetallePedido> detalles = new ArrayList<>();

            for (int i = 0; i < idsProducto.length; i++) {

                int idProducto = Integer.parseInt(idsProducto[i]);
                int cantidad = Integer.parseInt(cantidades[i]);

                DetallePedido detalle = new DetallePedido();
                detalle.setIdProducto(idProducto);
                detalle.setCantidad(cantidad);

                detalles.add(detalle);
            }

            // Registrar cabecera y detalles en una transacción
            int idPedido = pedidoService.guardarPedidoCompleto(
                    pedido, detalles);

            response.sendRedirect(
                    request.getContextPath()
                    + "/pedidos?registro=exito&idPedido=" + idPedido);

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Revisa los números ingresados.");

        } catch (IllegalArgumentException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage());
        }
    }
}
