package com.dwi.prytecnoservicio;

import config.ConexionDBSingleton;

public class PruebaSingleton {

    public static void main(String[] args) {

        ConexionDBSingleton c1 = ConexionDBSingleton.getInstancia();

        ConexionDBSingleton c2 = ConexionDBSingleton.getInstancia();

        System.out.println(c1 == c2);
    }
}

