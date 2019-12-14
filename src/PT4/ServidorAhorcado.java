package PT4;

import java.io.File;


import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ServidorAhorcado {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		Remote remote = UnicastRemoteObject.exportObject(new InterfazAhorcado() {

			public String Palabra(String palabra) throws RemoteException { //Escogemos la palabra aleatoriamente del archivo. En mi caso, esta en el workspace.
				try {
					Scanner lector = new Scanner(new File("Palabras.txt")); //Aqui ponemos la ruta del archivo que contiene las palabras.
					int selectPalabra = (int) (Math.random() * 100); //Seleccionamos una palabara aleatoriamente.

					for (int j = 0; j <= selectPalabra; j++) {
						if (j == selectPalabra) {
							palabra = lector.nextLine();
						} else {
							lector.nextLine();
						}
					}
					lector.close();
				} catch (Exception e) {
					System.out.println("Se ha producido un error: " + e);
				}
				return palabra;
			};

			public int Intentos(String letra, String palabra, int intentos) throws RemoteException { //Cuenta los intentos en caso que falle el usuario con la letra.
				if(!palabra.contains(letra)) {
					intentos++;
				}
				return intentos;
			};
			
			public boolean Ganar(String [] resolviendoPalabra, String palabra) throws RemoteException { //Si acierta todas las palabras le indica al cliente que debe cerrar el programa.
				boolean Ganar = false;
				String progreso = "";
				for (int j = 0; j < resolviendoPalabra.length; j++) {
					progreso = progreso + resolviendoPalabra[j];
				}
				if (progreso.equals(palabra)) {
					Ganar = true;
				}
				return Ganar;
			};

			public String [] contarLetras(int a, String palabra, String [] newWordSplit) throws RemoteException { //Contamos las letras que tiene la palabra escogida aleatoriamente y le mandamos al cliente un string con el numero de guiones que letras tiene la palabra.
				a = palabra.length();
				palabra = "";
				for (int j = 0; j < a; j ++) {
					palabra = palabra + "_ ";
					newWordSplit = palabra.split(" ");
				}
				return newWordSplit;
			};

			@Override
			public String[] comprobarLetra(String letra, String palabra, String [] resolviendoPalabra) throws RemoteException { //Comprobamos si la letra que ha introducido el usuario esta en la palabra, si es asi, modificamos lo que le mostramos al usuario.
				if(palabra.contains(letra)) {
					String [] palabraSeparada = palabra.split("");
					for (int j = 0; j < palabra.length(); j++) {
						if (letra.equalsIgnoreCase(palabraSeparada[j])) {
							resolviendoPalabra[j] = resolviendoPalabra[j].replace("_", letra);
						}
					}
				}
				return resolviendoPalabra;
			};
		}, 0);

		Registry registry = LocateRegistry.createRegistry(2000); //Abrimos un puerto, en este caso, el puerto 2000.
		System.out.println("Servidor escuchando en el puerto " + String.valueOf(2000));
		
		registry.bind("Ahorcado", remote);
		
	}

}
