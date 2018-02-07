package org.fbcmd4j;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fbcmd4j.utils.Utils;
import facebook4j.Facebook;
import facebook4j.FacebookException;

public class Main {
	static final Logger logger = LogManager.getLogger(Main.class);
	private static final String APP_VERSION = "v1.0";

	public static void main(String[] args) {
		logger.info("Iniciando app");
		Facebook facebook = null;
		
		int seleccion;
		try (Scanner scanner = new Scanner(System.in)){
			while(true){
				
				// Inicio Menu
				System.out.format("Bienvenido a cliente Facebook version: %s\n\n", APP_VERSION);
				System.out.println("Seleccione la opcion deseada: ");
				System.out.println("(0) Iniciar Sesión");
				System.out.println("(1) Newsfeed");
				System.out.println("(2) Wall");
				System.out.println("(3) Publicar estado");
				System.out.println("(4) Publicar link");
				System.out.println("(5) Salir");
				System.out.println("\nPor favor ingrese una opción: ");
				// Fin de Menu
				try {
					seleccion = scanner.nextInt();
			
					switch(seleccion){
						case 0:
							facebook = Utils.generate();
							break;
							
						case 1: 
							 Utils.postFeed(facebook.getFeed(), scanner, "neewsFeed");
							break;
						
						case 2:
							Utils.postFeed(facebook.getPosts(), scanner, "wall");
							break;
							
						case 3:
							System.out.println("Escribe un estado: ");
							String estado = scanner.nextLine();
							facebook.postStatusMessage(estado);
							break;
							
						case 4:
							System.out.println("Escribe un link: ");
							String link = scanner.nextLine();
							System.out.println("y el mensaje que lo acompaña: ");
							String message= scanner.nextLine();
							facebook.postLink(new URL(link),message);
							break;
							
						case 5:
							System.exit(0);
						default:
							logger.error("Opción inválida");
							break;
					}
				} 
				
				catch (InputMismatchException ex){
					System.out.println("Ocurrió un errror, favor de revisar log.");
					logger.error("La opción es inválida. %s. \n", ex.getClass());
					scanner.next();} catch (FacebookException e) {

					logger.error(e);
			} 
				catch (MalformedURLException e) {

						logger.error(e);
				} 
			}
		}	
	}
}