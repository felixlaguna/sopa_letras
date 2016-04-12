package sopa.textui;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import sopa.control.Arbitro;
/**
 * Clase de interfaz en texti de la sopa de letras.
 * @author FELIX
 *
 */
public class Sopa {
	/**
	 * Nombre del fichero de diccionario.
	 */
	final static String FILE_NAME = "./dic.txt";
	/**
	 * Encoding del diccionario.
	 */
	final static Charset ENCODING = StandardCharsets.UTF_8;
	/**
	 * Lineas a introducir en el tablero.
	 */
	private static List<String> lines;
	/**
	 * Máximo de palabras a introducir en el tablero.
	 */
	final static int MAX=10;
	/**
	 * Main.
	 * @param args argumentos como array de strings.
	 */
	public static void main(String[] args)throws IOException{
		Arbitro arbitro=new Arbitro(10,10);
		lines=readSmallTextFile(FILE_NAME);
		arbitro.colocarPalabras(lines, MAX);
		System.out.println(arbitro.obtenerTablero().toString());
		arbitro.rellenar();
		System.out.println(arbitro.obtenerTablero().toString());
	}
	/**
	 * Devuelve una lista de las palabras en un fichero.
	 * @param aFileName nombre del fichero
	 * @return List de String
	 * @throws IOException
	 */
	private static List<String> readSmallTextFile(String aFileName)throws IOException{
		Path path = Paths.get(aFileName);
		return Files.readAllLines(path, ENCODING);
	}
}
