package edu.pucmm.programacionweb2017;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by gusta on 23-May-17.
 */
public class Main {
    //Log para mostrar logs en la consola
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("Iniciando programa.");
        Practica practica = new Practica();
    }
}