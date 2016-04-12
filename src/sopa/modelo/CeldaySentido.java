package sopa.modelo;

import sopa.control.Sentido;
/**
 * Estructura de datos para almacenar celda y sentido.
 * @author FELIX
 *
 */
public class CeldaySentido {
	/**
	 * Celda.
	 */
	private Celda celda;
	/**
	 * Sentido
	 */
	private Sentido sentido;
	/**
	 * Constructor de celda.
	 * @param celda celda
	 * @param sentido sentido
	 */
	public CeldaySentido(Celda celda,Sentido sentido){
		this.celda=celda;
		this.sentido=sentido;
	}
	/**
	 * Devuelve la celda.
	 * @return celda
	 */
	public Celda obtenerCelda(){
		return celda;
	}
	/**
	 * Devuelve el sentido.
	 * @return sentido
	 */
	public Sentido obtenerSentido(){
		return sentido;
	}
}
