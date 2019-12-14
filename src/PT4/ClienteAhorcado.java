package PT4;

import java.rmi.NotBoundException;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClienteAhorcado {
	public static void main(String[] args) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry("192.168.41.77", 2000); //Introducimos la IP y el puerto del servidor.
		InterfazAhorcado interfazAhorcado = (InterfazAhorcado) registry.lookup("Ahorcado");
		Scanner n1 = new Scanner(System.in);
		System.out.println("Juego Ahorcado");
		System.out.println("----------Reglas----------");
		System.out.println("1.El juego termina cuando cometes 8 fallos o cuando adivinas la palabra.");
		System.out.println("2.Esta permitido usar tanto mayusculas como minusculas.");
		System.out.println("3.El juego solo aceptara la introduccion de 1 letra.");
		System.out.println("4.No hay limite de tiempo.");
		System.out.println("IMPORTANTE!");
		System.out.println("5.Las palabras  no contienen numeros, en caso de que introduzcas 1, se contara como un fallo.");
		System.out.println("--------------------------");
		
		String prueba = "";
		int intentos = 0;
		String [] newWordSplit = { };

		String word = interfazAhorcado.Palabra(prueba.toLowerCase());
		System.out.println("La palabra es: " + word);
		int pruebaLetras = word.length();
		String[] contarLetras = interfazAhorcado.contarLetras(pruebaLetras, word, newWordSplit);
		
		for (int j = 0; j < contarLetras.length; j++) {
			System.out.print(contarLetras[3]+ " ");
		}
		
		while (true) {
			System.out.println("\n\nIntroduce una letra");
			String letraIntroducida = n1.next();
			
			if (letraIntroducida.length() == 1) {
				letraIntroducida = letraIntroducida.toLowerCase();
				
				intentos = interfazAhorcado.Intentos(letraIntroducida, word, intentos);
				
				if (intentos == 8) { //El servidor cuenta los fallos que vas cometiendo, en caso de que llegues a 8, pierdes y el juego se cierra.
					System.out.println("\nHas cometido " + intentos + " fallos");
					System.out.println("HAS PERDIDO!");
					System.exit(0);
				}
				contarLetras = interfazAhorcado.comprobarLetra(letraIntroducida, word, contarLetras);
				
				boolean Ganador = interfazAhorcado.Ganar(contarLetras,word); //En caso de que aciertes todas las letras, el servidor nos devuelve Ganador = true y por lo tanto entra en el if y cierra el programa.
				if (Ganador) {
					System.out.println("PALABRA ADIVINADA!");
					System.out.println("\nLa palabra era: " + word);
					System.exit(0);
				}
				
				System.out.println("\nLlevas " + intentos + " intentos!\n"); //Nos muestra por pantalla los intentos y el avance que vas haciendo con la palabra.
				for (int j = 0; j < contarLetras.length; j++) {
					System.out.print(contarLetras[j]+ " ");
				}
			} else {
				System.out.println("Debes introducir unicamente 1 letra.");
			}
			
		}
	}
}
