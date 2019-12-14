package PT4;

public interface InterfazAhorcado extends java.rmi.Remote {
	public String [] comprobarLetra(String a, String b, String [] resolviendoPalabra) throws java.rmi.RemoteException;
	public String Palabra(String a) throws java.rmi.RemoteException;
	public int Intentos(String letra, String palabra, int intentos) throws java.rmi.RemoteException;
	public String[] contarLetras(int a, String b, String [] newWordSplit) throws java.rmi.RemoteException;
	public boolean Ganar(String[] resolviendoPalabra, String palabra) throws java.rmi.RemoteException;
}
