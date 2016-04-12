package sopa.modelo;
/**
 * Clase de celda.
 * @author FELIX
 *
 */
public class Celda {
	/**
	 * Letra.
	 */
	private char letra;
	/**
	 * Fila.
	 */
	private int fila;
	/**
	 * Columna.
	 */
	private int columna;
	/**
	 * Constructor de celda.
	 * @param fila fila
	 * @param columna columna
	 */
	public Celda(int fila, int columna){
		this.fila=fila;
		this.columna=columna;
	}
	/**
	 * Coloca la letra.
	 * @param letra char letra
	 */
	public void establecerLetra(char letra){
		this.letra=letra;
	}
	/**
	 * Devuelve la letra.
	 * @return char de letra
	 */
	public char devolverLetra(){
		return letra;
	}
	/**
	 * Devuelve si la celda tiene o no una letra.
	 * @return true si está vacía, false si no
	 */
	public boolean estaVacia(){
		return letra=='\u0000';
	}
	/**
	 * Consulta la fila de la celda.
	 * @return fila
	 */
	public int obtenerFila(){
		return fila;
	}
	/**
	 * Consulta la columna de la celda.
	 * @return columna
	 */
	public int obtenerColumna(){
		return columna;
	}
	/**
	 * Devuelve la letra como string.
	 * @return String de la letra
	 */
	public String obtenerString(){
		String s=new String();
		s+=letra;
		return s;
		}
}
