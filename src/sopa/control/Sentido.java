package sopa.control;

/**
 * Sentido del movimiento.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena</a>
 * @author <a href="mailto:rredondo@ubu.es">Raquel Redondo</a>
 * @version 1.0 20151003
 */
public enum Sentido {
	/** Horizontal.
	 */
	DERECHA(0, 1), 
	
	/** Horizontal, izquierda. */
	 IZQUIERDA(0, -1),

	/** Vertical. */
	ARRIBA(-1, 0), 
	
	/** Abajo. */
	ABAJO(1, 0),

	/** Diagonal SO NE, hacia arriba. */
	DIAGONAL_SO_NE_ARRIBA(-1, 1),

	/** Diagonal SO NE, hacia arriba. */
	DIAGONAL_SO_NE_ABAJO(1, -1),

	/** Diagonal NO SE hacia arriba. */
	DIAGONAL_NO_SE_ARRIBA(-1, -1), 
	
	/** Diagonal NO SE hacia abajo. */
	DIAGONAL_NO_SE_ABAJO(1, 1);
	
	/**
	 * Constructor.
	 * 
	 * @param desplFila
	 *            desplazamiento en las filas
	 * @param desplColumna
	 *            desplazamiento en columnas
	 */
	private Sentido(int desplFila, int desplColumna) {
		this.desplazamientoFila = desplFila;
		this.desplazamientoColumna = desplColumna;
	}

	/**
	 * Desplazamiento en filas.
	 */
	private int desplazamientoFila;

	/**
	 * Desplazamiento en columnas.
	 */
	private int desplazamientoColumna;

	/**
	 * Obtiene el desplazamiento en filas.
	 * 
	 * @return desplazamiento en filas
	 */
	public int obtenerDesplazamientoFila() {
		return this.desplazamientoFila;
	}

	/**
	 * Obtiene el desplazamiento en columnas.
	 * 
	 * @return desplazamiento en columnas
	 */
	public int obtenerDesplazamientoColumna() {
		return this.desplazamientoColumna;
	}
}
