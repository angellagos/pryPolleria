package com.dwi.prytecnoservicio.factory;

public class FormaFactory {

    public Forma crear(String tipo) {
        if (tipo == null) {
            return null;
        }

        if (tipo.equalsIgnoreCase("CIRCULO")) {
            return new Circulo();
        }

        if (tipo.equalsIgnoreCase("RECTANGULO")) {
            return new Rectangulo();
        }

        return null;
    }
}
