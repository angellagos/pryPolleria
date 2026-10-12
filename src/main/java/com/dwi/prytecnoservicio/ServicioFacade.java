package com.dwi.prytecnoservicio;

import com.dwi.prytecnoservicio.factory.DAOFactory;
import java.sql.SQLException;

public class ServicioFacade {
    //ACCESO A LA FUNCIONALIDAD DAO
    private final ServicioDAO servicioDAO;

    //CONSTRUCTOR
    public ServicioFacade() {
//        this.servicioDAO = new ServicioDAOImpl();
        this.servicioDAO = DAOFactory.crearServicioDAO();

    }
    //METODO FUNCIONAL DE ServicioFacade
    public Servicio consultarServicio(String nombre)
            throws SQLException {
        return servicioDAO.buscarPorNombre(nombre);
    }
}

