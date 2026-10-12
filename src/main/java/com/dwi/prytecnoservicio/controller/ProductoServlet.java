/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.controller;

import com.dwi.prytecnoservicio.model.CategoriaProducto;
import com.dwi.prytecnoservicio.model.Producto;
import com.dwi.prytecnoservicio.service.CategoriaProductoService;
import com.dwi.prytecnoservicio.service.ProductoService;
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
@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    private ProductoService productoService;
    private CategoriaProductoService categoriaService;

    @Override
    public void init() throws ServletException {
        productoService = new ProductoService();
        categoriaService = new CategoriaProductoService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String accion = request.getParameter("accion");

        

        if ("editar".equals(accion)) {

            String idTexto = request.getParameter("id");

            try {
                int id = Integer.parseInt(idTexto);

                Producto producto
                        = productoService.buscarProductoPorId(id);

                if (producto == null) {
                    response.sendError(
                            HttpServletResponse.SC_NOT_FOUND,
                            "Producto no encontrado."
                    );
                    return;
                }

                request.setAttribute("producto", producto);
                request.setAttribute(
                        "categorias",
                        categoriaService.listarCategorias()
                );

                request.getRequestDispatcher(
                        "/WEB-INF/views/editarProducto.jsp"
                ).forward(request, response);

            } catch (NumberFormatException e) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "El ID del producto no es válido."
                );
            }

            return;
        }

        request.setAttribute(
                "productos",
                productoService.listarProductos()
        );

        request.setAttribute(
                "categorias",
                categoriaService.listarCategorias()
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/productos.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        if ("eliminar".equals(accion)) {

            String idTexto = request.getParameter("idProducto");

            try {
                int idProducto = Integer.parseInt(idTexto);

                productoService.eliminarProducto(idProducto);

                response.sendRedirect(
                        request.getContextPath()
                        + "/productos?eliminacion=exito"
                );

            } catch (NumberFormatException e) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "El ID del producto no es válido."
                );
            } catch (IllegalArgumentException e) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        e.getMessage()
                );
            }

            return;
        }

        try {
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            String precioTexto = request.getParameter("precio");
            String categoriaTexto = request.getParameter("idCategoria");

            if (nombre == null || precioTexto == null
                    || categoriaTexto == null) {
                throw new IllegalArgumentException(
                        "Completa los campos obligatorios."
                );
            }

            double precio = Double.parseDouble(precioTexto);
            int idCategoria = Integer.parseInt(categoriaTexto);

            CategoriaProducto categoria = new CategoriaProducto();
            categoria.setIdCategoria(idCategoria);

            Producto producto = new Producto();
            producto.setNombre(nombre.trim());
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setCategoria(categoria);

            if ("actualizar".equals(accion)) {

                String idTexto = request.getParameter("idProducto");
                int idProducto = Integer.parseInt(idTexto);

                String disponibleTexto
                        = request.getParameter("disponible");

                boolean disponible
                        = Boolean.parseBoolean(disponibleTexto);

                producto.setIdProducto(idProducto);
                producto.setDisponible(disponible);

                productoService.actualizarProducto(producto);

                response.sendRedirect(
                        request.getContextPath() + "/productos?actualizacion=exito"
                );

            } else {

                producto.setDisponible(true);
                producto.setEliminado(false);

                productoService.guardarProducto(producto);

                response.sendRedirect(
                        request.getContextPath() + "/productos?registro=exito"
                );
            }

        } catch (IllegalArgumentException e) {

            request.setAttribute("error", e.getMessage());
            request.setAttribute(
                    "productos", productoService.listarProductos()
            );
            request.setAttribute(
                    "categorias", categoriaService.listarCategorias()
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/productos.jsp"
            ).forward(request, response);
        }
    }
}
