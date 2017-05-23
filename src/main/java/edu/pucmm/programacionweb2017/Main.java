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
public class Main {
    //Log para mostrar logs en la consola
    private static final Logger logger = LogManager.getLogger();
    //Constante prefijo para formar la URL
    private static final String PREFIX = "https://www.";
    //Constante para cambiar el url
    private static final char CAMBIAR_URL = 'Q';
    //Constante para la opcion salir
    private static final char SALIR = 'Z';

    public static void main(String[] args) {
        logger.info("Iniciando programa.");
        logica();
    }

    //Funcion para dibujar el menu
    public static void dibujarMenu() {
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
    public static void separador() {
        System.out.println("===========================================================");
    }

    //Funcion que me devuelve un char, la opcion seleccionada.
    public static char inputOption() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite opcion > ");
        return scanner.next().charAt(0);
    }

    //Funcion que me devuelve la URL digitada por el usuario.
    public static String inputURL() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite solo el nombre y el dominio de la pagina: ");
        System.out.print("> ");
        return PREFIX + scanner.next();
    }

    public static void logica() {
        String url;
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
                        acapiteA(url);
                        separador();
                        break;
                    case 'B':
                        acapiteB(url);
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
                        visualizarHtml(url);
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

    public static InputStreamReader getHttpResponseContent(String url) {
        try {
            //Creo las instancias para mandar un request al URL digitado
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            //Mando el request
            HttpResponse httpResponse = httpClient.execute(httpGet);

            return new InputStreamReader(httpResponse.getEntity().getContent());
        } catch (IOException e) {
            logger.debug("Error en el response del http request.", e);
            return null;
        }
    }

    public static void acapiteA(String url) {
        //Creo una instancia de buffered reader para leer cada linea del request
        BufferedReader rd = new BufferedReader(getHttpResponseContent(url));

        //Imprimo el resultado
        System.out.println("El numero total de lineas es: " + rd.lines().count());
    }

    public static void acapiteB(String url) {
        //Creo una instancia de buffered reader para leer cada linea del request
        BufferedReader rd = new BufferedReader(getHttpResponseContent(url));

        System.out.println("La cantidad total de parrafos (p) es: " + rd.lines().filter(s -> s.contains("<p>")).count());
    }

    public static void acapiteC() {

    }

    public static void acapiteD() {

    }

    public static void acapiteE() {

    }

    public static void acapiteF() {

    }

    public static void visualizarHtml(String url) {
        //Creo una instancia de buffered reader para leer cada linea del request
        BufferedReader rd = new BufferedReader(getHttpResponseContent(url));

        rd.lines().forEach(s -> System.out.println(s));
    }
}