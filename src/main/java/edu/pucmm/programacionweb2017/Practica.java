package edu.pucmm.programacionweb2017;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by gusta on 23-May-17.
 */
public class Practica {
    //Log para mostrar logs en la consola
    private final Logger logger = LogManager.getLogger();
    //Constante prefijo para formar la URL
    private final String PREFIX = "http://";
    //Constante para cambiar el url
    private final char CAMBIAR_URL = 'Q';
    //Constante para la opcion salir
    private final char SALIR = 'Z';
    //Instancia documento para extraer el archivo html
    private Document document;
    //Variable url para guardar el url digitado
    private String url;

    public Practica() {
        logica();
    }

    //Funcion para dibujar el menu
    private void dibujarMenu() {
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
    private void separador() {
        System.out.println("===========================================================");
    }

    //Funcion que me devuelve un char, la opcion seleccionada.
    private char inputOption() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite opcion > ");
        return scanner.next().toUpperCase().charAt(0);
    }

    //Funcion que me devuelve la URL digitada por el usuario.
    private String inputURL() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite solo el nombre y el dominio de la pagina: ");
        System.out.print("> ");
        return PREFIX + scanner.next();
    }

    private void logica() {
        char opt = 0;

        while (opt != SALIR) {
            try {
                separador();
                //Resetear la opcion
                opt = 0;

                //Pedir la URL del usuario
                url = inputURL();
                System.out.println("Pagina digitada: " + url);

                System.out.println("Haciendo el request hacia la url: " + url);
                System.out.println("Porfavor espere...");
                //Extraigo el contenido del request hacia el documento
                document = Jsoup.connect(url).get();
                System.out.println("Request finalizado.");

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
                                logger.debug("Error al introducir una opcion.", e.getMessage());
                            }
                            break;
                    }
                }
            } catch (IOException e) {
                logger.debug("Error al conectarse al url. Intente de nuevo o con otra url.", e.getMessage());
            }
        }
    }

    //Indicar la cantidad de lineas del recurso retornado.
    private void acapiteA() {
        System.out.println("Total de lineas: " + document.html().split("\n").length);
    }

    //Indicar la cantidad de párrafos (p) que contiene el documento HTML .
    private void acapiteB() {
        try {
            System.out.println("Total de parrafos: " + document.getElementsByTag("p").size());
        } catch (NoSuchElementException e) {
            logger.debug("Error. Elemento no existe.", e.getMessage());
        }
    }

    //Indicar la cantidad de imágenes (img) dentro de los párrafos que
    //contiene el archivo HTML.
    private void acapiteC() {
        try {
            int[] cant = {0};

            document.getElementsByTag("p").forEach(element -> {
                element.getElementsByTag("img").forEach(element1 -> {
                    cant[0]++;
                });
            });

            System.out.println("Total de imagenes dentro de los parrafos: " + cant[0]);
        } catch (NoSuchElementException e) {
            logger.debug("Error. Elemento no existe.", e.getMessage());
        }
    }

    //indicar la cantidad de formularios (form) que contiene el HTML por
    //categorizando por el método implementado POST o GET.
    private void acapiteD() {
        try {
            int[] sizePost = {0};
            int[] sizeGet = {0};

            document.getElementsByTag("form").forEach(element -> {
                element.getElementsByAttributeValue("method", "post").forEach(element1 -> {
                    sizePost[0]++;
                });
            });

            document.getElementsByTag("form").forEach(element -> {
                element.getElementsByAttributeValue("method", "get").forEach(element1 -> {
                    sizeGet[0]++;
                });
            });

            System.out.println("Total de formularios (POST): " + sizePost[0]);
            System.out.println("Total de formularios (GET): " + sizeGet[0]);
        } catch (NoSuchElementException e) {
            logger.debug("Error. Elemento no existe.", e.getMessage());
        }
    }

    //Para cada formulario mostrar los campos del tipo input y su
    //respectivo tipo que contiene en el documento HTML.
    private void acapiteE() {
        try {
            for (Element element : document.getElementsByTag("form")) {
                System.out.println(element);
            }
        } catch (NoSuchElementException e) {
            logger.debug("Error. Elemento no existe.", e.getMessage());
        }
    }

    //Para cada formulario parseado, identificar que el metodo de envío
    //del formulario sea por utilizando el método POST y enviar una
    //petición al servidor, con el parámetro llamado asignatura y valor
    //practica1 y mostrar la respuesta por la salida estandar.
    private void acapiteF() {
        Connection.Response response = null;
        Document doc = null;
        Map<String, String> parametros = new HashMap<>();

        try {
            for (Element element : document.getElementsByTag("form")) {
                String absURL = element.absUrl("action");

                if (element.attr("method").equals("post")) {
                    parametros.put("asignatura", "practica1");

                    doc = Jsoup.connect(absURL)
                            .method(Connection.Method.POST)
                            .data(parametros)
                            .post();

                    System.out.println(doc.outerHtml());
                }
            }
        } catch (IOException e) {
            logger.debug("Error al intentar mandar la peticion al servidor " + url, e.getMessage());
        }
    }

    //Metodo para visualizar el contenido del documento en html
    private void visualizarHtml() {
        System.out.println(document.html().toString());
    }
}