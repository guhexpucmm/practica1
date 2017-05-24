package edu.pucmm.programacionweb2017;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
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
    private Document document;
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

            try {
                System.out.println("Haciendo el request hacia la url: " + url);
                System.out.println("Porfavor espere...");
                document = Jsoup.connect(url).get();
                System.out.println("Request finalizado.");
            } catch (IOException e) {
                logger.debug("Error al conectarse al url.", e);
            }

            //Ciclo para presentar las opciones
            while (opt != CAMBIAR_URL) {
                dibujarMenu();

                opt = inputOption();

                switch (opt) {
                    case 'A':
                        separador();
                        acapiteA();
                        separador();
                        break;
                    case 'B':
                        separador();
                        acapiteB();
                        separador();
                        break;
                    case 'C':
                        separador();
                        acapiteC();
                        separador();
                        break;
                    case 'D':
                        separador();
                        acapiteD();
                        separador();
                        break;
                    case 'E':
                        separador();
                        acapiteE();
                        separador();
                        break;
                    case 'F':
                        separador();
                        acapiteF();
                        separador();
                        break;
                    case 'V':
                        separador();
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

    public void acapiteA() {
        System.out.println("Total de lineas: " + document.html().split("\n").length);
    }

    public void acapiteB() {
        System.out.println("Total de parrafos: " + document.getElementsByTag("p").size());
    }

    public void acapiteC() {
        System.out.println("Total de imagenes dentro de los parrafos: " + document.getElementsByTag("p").iterator().next().children().tagName("img").stream().count());
    }

    public void acapiteD() {
        System.out.println("Total de formularios: " + document.getElementsByTag("form").attr("method", "post").size());
    }

    public void acapiteE() {
        for (Element element : document.getElementsByTag("form").iterator().next().children()) {
            for (Element element1 : document.getElementsByTag("input")) {
                System.out.println(element1);
            }
        }
    }

    public void acapiteF() {

    }

    public void visualizarHtml() {
        System.out.println(document.html().toString());
    }
}
