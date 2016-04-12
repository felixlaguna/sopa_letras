package sopa.modelo;

import java.util.ArrayList;
import java.util.List;

public class Tablero {

	/**
	 * Lista de lista de celdas (matriz).
	 */
	private List<List<Celda>> celdas;
	/**
	 * Constructor del tablero.
	 * @param filas filas
	 * @param columnas columnas
	 */
	public Tablero(int filas,int columnas){
		celdas=new ArrayList<List<Celda>>(filas);
		for (int i=0;i<filas;++i){
			celdas.add(i,new ArrayList<Celda>(columnas));
			for (int j=0;j<columnas;++j){
				celdas.get(i).add(j,new Celda(i,j));
			}
		}
	}
	/**
	 * Devuelve si la celda está en el tablero.
	 * @param x fila
	 * @param y columna
	 * @return true si está, false si no
	 */
	public boolean estaEnTablero(int x, int y){
		boolean aux=(x>=0 && x < obtenerNumeroFilas() 
				&& y>=0 && y < obtenerNumeroColumnas());
		return aux;
	}
	/**
	 * Comprueba si una celda esta en el tablero.
	 * @param celda celda
	 * @return true si esta en el tablero, false si no
	 */
	public boolean estaEnTablero(Celda celda){
		return celdas.get(celda.obtenerFila()).contains(celda);
	}
	/**
	 * Consulta el número de filas.
	 * @return filas
	 */
	public int obtenerNumeroFilas(){
		return celdas.size();
	}
	/**
	 * Consulta el número de columnas.
	 * @return columnas
	 */
	public int obtenerNumeroColumnas(){
		return celdas.get(0).size();
	}
	/**
	 * Devuelve la celda en x fila e y columna del tablero.
	 * @param x fila
	 * @param y columna
	 * @return celda o null
	 */
	public Celda obtenerCelda(int x, int y){
		if (estaEnTablero(x,y)){
			return celdas.get(x).get(y);
		}else{
			return null;
		}
	}
	/**
	 * ToString.
	 */
	@Override
	public String toString(){
		String s="";
		for(int i=0;i<obtenerNumeroFilas();++i){
			s+="\t";
			for(int j=0;j<obtenerNumeroColumnas();++j){
				if (obtenerCelda(i, j).estaVacia()){
					s+="- ";
				}else{
					s+=obtenerCelda(i, j).devolverLetra()+" ";
				}
			}
			s+="\n";
			
		}
		s+="\n";
		return s;
	}
}
