package com.dwi.prytecnoservicio;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ConsultaServlet", urlPatterns = {"/ConsultaServlet"})
public class ConsultaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //PARÁMETROS RECIBIDOS
        String nombre = request.getParameter("nombre");
        String servicio = request.getParameter("servicio");
        String tipoAtencion = request.getParameter("tipoAtencion");

        //ConsultaServlet DEPENDE DEL Facade PARA ESTA OPERACIÓN
        ServicioFacade facade = new ServicioFacade();
        Servicio servicioEncontrado = null;
        try {
            servicioEncontrado = facade.consultarServicio(servicio);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        //MENSAJE DE SERVICIO ENCONTRADO
        String mensaje;
        if (servicioEncontrado != null) {
            mensaje = "Consulta registrada para el servicio: "
                    + servicioEncontrado.getNombre();
        } else {
            mensaje = "El servicio solicitado no se encuentra disponible.";
        }
        
        // CREA ATRIBUTOS PARA VISUALIZAR
        request.setAttribute("mensajeConsulta", mensaje);
        request.setAttribute("nombreConsulta", nombre);
        request.setAttribute("servicioConsulta", servicio);
        request.setAttribute("tipoAtencion", tipoAtencion);
        
 // NUEVO: volver a cargar los servicios
    try {

        ServicioDAO dao = new ServicioDAOImpl();

        List<Servicio> servicios = dao.listar();

        request.setAttribute(
                "servicios",
                servicios
        );

    } catch (SQLException ex) {

        ex.printStackTrace();

        request.setAttribute(
                "errorServicios",
                "No fue posible recuperar los servicios."
        );
    }       
        
        // DERIVA ATRIBUTOS HACIA COMPONENTE Vista
        request.getRequestDispatcher("/index.jsp")
                .forward(request, response);

    }
}
