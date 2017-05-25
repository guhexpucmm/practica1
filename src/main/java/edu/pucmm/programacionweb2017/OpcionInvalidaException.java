package edu.pucmm.programacionweb2017;

/**
 * Created by gusta on 23-May-17.
 */
public class OpcionInvalidaException extends Exception {
    //Excepcion para la opcion invalida
    public OpcionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
