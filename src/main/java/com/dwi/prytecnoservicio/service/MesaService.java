/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dwi.prytecnoservicio.service;

import com.dwi.prytecnoservicio.dao.MesaDAO;
import com.dwi.prytecnoservicio.factory.DAOFactory;
import com.dwi.prytecnoservicio.model.Mesa;
import java.util.List;

/**
 *
 * @author Miguel Angel
 */
public class MesaService {

    private final MesaDAO mesaDAO;

    public MesaService() {
        mesaDAO = DAOFactory.crearMesaDAO();
    }

    public List<Mesa> listarMesas() {
        return mesaDAO.listar();
    }

    public Mesa buscarMesaPorId(int idMesa) {
        if (idMesa <= 0) {
            throw new IllegalArgumentException(
                    "El ID de la mesa debe ser mayor que cero."
            );
        }

        return mesaDAO.buscarPorId(idMesa);
    }

    public void guardarMesa(Mesa mesa) {
        if (mesa == null) {
            throw new IllegalArgumentException(
                    "La mesa no puede ser nula."
            );
        }

        if (mesa.getNumero() <= 0) {
            throw new IllegalArgumentException(
                    "El número de mesa debe ser mayor que cero."
            );
        }

        if (mesa.getCapacidad() <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser mayor que cero."
            );
        }

        validarEstado(mesa.getEstado());

        mesaDAO.guardar(mesa);
    }

    public void actualizarMesa(Mesa mesa) {
        if (mesa == null || mesa.getIdMesa() <= 0) {
            throw new IllegalArgumentException(
                    "La mesa o su ID no son válidos."
            );
        }

        if (mesa.getNumero() <= 0 || mesa.getCapacidad() <= 0) {
            throw new IllegalArgumentException(
                    "El número y la capacidad deben ser mayores que cero."
            );
        }

        validarEstado(mesa.getEstado());

        mesaDAO.actualizar(mesa);
    }

    private void validarEstado(String estado) {
        if (estado == null
                || !(estado.equals("LIBRE")
                || estado.equals("OCUPADA")
                || estado.equals("INACTIVA"))) {
            throw new IllegalArgumentException(
                    "El estado debe ser LIBRE, OCUPADA o INACTIVA."
            );
        }
    }

    public void eliminarMesa(int idMesa) {

        if (idMesa <= 0) {
            throw new IllegalArgumentException(
                    "El ID de la mesa debe ser mayor que cero.");
        }

        Mesa mesa = mesaDAO.buscarPorId(idMesa);

        if (mesa == null) {
            throw new IllegalArgumentException(
                    "No se encontró la mesa que deseas eliminar.");
        }

        mesaDAO.eliminar(idMesa);
    }
}
