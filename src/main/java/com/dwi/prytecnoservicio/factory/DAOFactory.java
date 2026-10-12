package com.dwi.prytecnoservicio.factory;

import com.dwi.prytecnoservicio.ServicioDAO;
import com.dwi.prytecnoservicio.ServicioDAOImpl;
import com.dwi.prytecnoservicio.dao.CategoriaProductoDAO;
import com.dwi.prytecnoservicio.dao.DetallePedidoDAO;
import com.dwi.prytecnoservicio.dao.EstadoPedidoDAO;
import com.dwi.prytecnoservicio.dao.MesaDAO;
import com.dwi.prytecnoservicio.dao.PedidoDAO;
import com.dwi.prytecnoservicio.dao.ProductoDAO;
import com.dwi.prytecnoservicio.dao.UsuarioDAO;
import com.dwi.prytecnoservicio.impl.CategoriaProductoDAOImpl;
import com.dwi.prytecnoservicio.impl.DetallePedidoDAOImpl;
import com.dwi.prytecnoservicio.impl.EstadoPedidoDAOImpl;
import com.dwi.prytecnoservicio.impl.MesaDAOImpl;
import com.dwi.prytecnoservicio.impl.PedidoDAOImpl;
import com.dwi.prytecnoservicio.impl.ProductoDAOImpl;
import com.dwi.prytecnoservicio.impl.UsuarioDAOImpl;

public class DAOFactory {

    public static ServicioDAO crearServicioDAO() {
        return new ServicioDAOImpl();
    }

    public static ServicioDAO crearDAO(String tipo) {

        if ("SERVICIO".equalsIgnoreCase(tipo)) {
            return new ServicioDAOImpl();
        }
        return null;
    }

    public static ProductoDAO crearProductoDAO() {
        return new ProductoDAOImpl();
    }

    public static CategoriaProductoDAO crearCategoriaProductoDAO() {
        return new CategoriaProductoDAOImpl();
    }

    public static MesaDAO crearMesaDAO() {
        return new MesaDAOImpl();
    }

    public static PedidoDAO crearPedidoDAO() {
        return new PedidoDAOImpl();
    }

    public static DetallePedidoDAO crearDetallePedidoDAO() {
        return new DetallePedidoDAOImpl();
    }

    public static EstadoPedidoDAO crearEstadoPedidoDAO() {
        return new EstadoPedidoDAOImpl();
    }

    public static UsuarioDAO crearUsuarioDAO() {
        return new UsuarioDAOImpl();
    }

}
