package edu.pucmm.programacionweb2017;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by gusta on 23-May-17.
 */
public class Practica {
    //Log para mostrar logs en la consola
    private final Logger logger = LogManager.getLogger();
    //Constante prefijo para formar la URL
    private final String PREFIX = "https://www.";
    //Constante para cambiar el url
    private final char CAMBIAR_URL = 'Q';
    //Constante para la opcion salir
    private final char SALIR = 'Z';
    private String url;

    public Practica() {
        logica();
    }

    //Funcion para dibujar el menu
    public void dibujarMenu() {
        System.out.println("###########[/programacionweb2017/practica1]###########");
        System.out.println("#Menu de opciones                                    #");
        System.out.println("#[A] -> Acapite A                                    #");
        System.out.println("#[B] -> Acapite B                                    #");
        System.out.println("#[C] -> Acapite C                                    #");
        System.out.println("#[D] -> Acapite D                                    #");
        System.out.println("#[E] -> Acapite E                                    #");
        System.out.println("#[F] -> Acapite F                                    #");
        System.out.println("#[V] -> Visualizar el archivo HTML                   #");
        System.out.println("#[Q] -> Cambiar URL                                  #");
        System.out.println("#[Z] -> Salir del programa                           #");
        System.out.println("######################################################");
    }

    //Funcion para dibujar un separador
    public void separador() {
        System.out.println("===========================================================");
    }

    //Funcion que me devuelve un char, la opcion seleccionada.
    public char inputOption() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite opcion > ");
        return scanner.next().toUpperCase().charAt(0);
    }

    //Funcion que me devuelve la URL digitada por el usuario.
    public String inputURL() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite solo el nombre y el dominio de la pagina: ");
        System.out.print("> ");
        return PREFIX + scanner.next();
    }

    public void logica() {
        char opt = 0;

        while (opt != SALIR) {
            separador();
            //Resetear la opcion
            opt = 0;

            //Pedir la URL del usuario
            url = inputURL();
            System.out.println("Pagina digitada: " + url);

            //Ciclo para presentar las opciones
            while (opt != CAMBIAR_URL) {
                dibujarMenu();

                opt = inputOption();

                switch (opt) {
                    case 'A':
                        acapiteA();
                        separador();
                        break;
                    case 'B':
                        acapiteB();
                        separador();
                        break;
                    case 'C':
                        acapiteC();
                        separador();
                        break;
                    case 'D':
                        acapiteD();
                        separador();
                        break;
                    case 'E':
                        acapiteE();
                        separador();
                        break;
                    case 'F':
                        acapiteF();
                        separador();
                        break;
                    case 'V':
                        visualizarHtml();
                        separador();
                    case 'Q':
                        break;
                    case 'Z':
                        //Salir del programa
                        logger.info("Programa terminado.");
                        System.exit(0);
                        break;
                    default:
                        try {
                            throw new OpcionInvalidaException("Opcion invalida. Opciones validas -> [A-B-C-D-E-F-Q-Z]");
                        } catch (OpcionInvalidaException e) {
                            logger.debug("Error al introducir una opcion.", e);
                        }
                        break;
                }
            }
        }
    }

    //Creo una instancia de buffered reader para leer cada linea del request
    public BufferedReader getHttpResponseContent(String url) {
        try {
            System.out.println("**********CARGANDO EL REQUEST, FAVOR ESPERAR...*********");
            //Creo las instancias para mandar un request al URL digitado
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            //Mando el request
            HttpResponse httpResponse = httpClient.execute(httpGet);

            System.out.println("***********REQUEST CARGADO...**********");
            return new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        } catch (IOException e) {
            logger.debug("Error en el response del http request.", e);
            return null;
        }
    }

    public void acapiteA() {
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = getHttpResponseContent(url);

            //Imprimo el resultado utilizando streams
            System.out.println("El numero total de lineas es: " + bufferedReader.lines().count());

            bufferedReader.close();
        } catch (IOException e) {
            logger.debug("Error al cerrar el buffered reader.", e);
        }
    }

    public void acapiteB() {
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = getHttpResponseContent(url);

            //Imprimo el resutado utilizando streams
            System.out.println("La cantidad total de parrafos (p) es: " + bufferedReader.lines().filter(s -> s.contains("<p>")).count());

            bufferedReader.close();
        } catch (IOException e) {
            logger.debug("Error al cerrar el buffered reader.", e);
        }
    }

    public void acapiteC() {
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = getHttpResponseContent(url);

            //todo Quede en el acapite C

            //Imprimo el resutado utilizando streams
            System.out.println("La cantidad total de imagenes (img) dentro de los parrafos (p) es: ");
            bufferedReader.close();
        } catch (IOException e) {
            logger.debug("Error al cerrar el buffered reader.", e);
        }
    }

    public void acapiteD() {

    }

    public void acapiteE() {

    }

    public void acapiteF() {

    }

    public void visualizarHtml() {
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = getHttpResponseContent(url);

            //Imprimo el documento HTML del request utilizando streams
            bufferedReader.lines().forEach(s -> System.out.println(s));

            bufferedReader.close();
        } catch (IOException e) {
            logger.debug("Error al cerrar el buffered reader.", e);
        }
    }
}
