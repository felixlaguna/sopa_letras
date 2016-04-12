package sopa.control;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import sopa.modelo.Celda;
import sopa.modelo.CeldaySentido;
import sopa.modelo.Tablero;
/**
 * Arbitro de la sopa de letras.
 * @author FELIX
 *
 */
public class Arbitro {
	/**
	 * Tablero.
	 */
	private Tablero tablero;
	/**
	 * Lista de palabras introducidas en el tablero.
	 */
	private List<String> palabras=new ArrayList<String>();
	/**
	 * Lista de celdas con la solucion.
	 */
	private List<Celda> solucion=new ArrayList<Celda>();
	/**
	 * Constructor del árbitro.
	 * @param filas filas
	 * @param columnas columnas
	 */
	public Arbitro(int filas, int columnas){
		tablero=new Tablero(filas,columnas);
	}
	/**
	 * Devuelve si una palarba cabe en una celda, en un sentido.
	 * @param palabra palabra
	 * @param celda celda
	 * @param sentido sentido
	 * @return true si cabe, false si no
	 */
	private boolean palabraCabe(String palabra,Celda celda, Sentido sentido){
		boolean resultado=true;
		int x=celda.obtenerFila();
		int y=celda.obtenerColumna();
		int contador=0;
		resultado&=(x+(sentido.obtenerDesplazamientoFila()*palabra.length())<tablero.obtenerNumeroFilas());
		resultado&=(y+(sentido.obtenerDesplazamientoColumna()*palabra.length())<tablero.obtenerNumeroColumnas());
		resultado&=(x+(sentido.obtenerDesplazamientoFila()*palabra.length())>=0);
		resultado&=(y+(sentido.obtenerDesplazamientoColumna()*palabra.length())>=0);
		if (resultado){
			while (contador<palabra.length()){
				resultado&=(tablero.obtenerCelda(x, y).estaVacia()||tablero.obtenerCelda(x, y).devolverLetra()==palabra.charAt(contador));
				x+=sentido.obtenerDesplazamientoFila();
				y+=sentido.obtenerDesplazamientoColumna();
				contador++;
			}
		}
		return resultado;
	}
	/**
	 * Devuelve las celdas y sentidos en donde cabe una palabra.
	 * @param palabra palabra
	 * @return Lista de CeldaySentido
	 */
	private List<CeldaySentido> palabraCabeTotal(String palabra){
		List<CeldaySentido> resultado=new ArrayList<CeldaySentido>();
		for (int i=0;i<tablero.obtenerNumeroFilas();++i){
			for (int j=0;j<tablero.obtenerNumeroFilas();++j){
				for (Sentido s: Sentido.values()){
					if (palabraCabe(palabra,tablero.obtenerCelda(i, j),s)){
						resultado.add(new CeldaySentido(tablero.obtenerCelda(i, j),s));
					}
				}
			}
		}
		return resultado;
	}
	/**
	 * Coloca una palabra en el tablero.
	 * @param palabra palabra
	 * @return true si se puede colocar, false si no.
	 */
	public boolean colocarPalabra(String palabra){
		boolean resultado=true;
		CeldaySentido celdaysentido=celdaAleatoria(palabra);
		if (celdaysentido==null){
			resultado=false;
		}else{
			Celda celda=celdaysentido.obtenerCelda();
			Sentido sentido=celdaysentido.obtenerSentido();
			int contador=0;
			int x=celda.obtenerFila();
			int y=celda.obtenerColumna();
			while (contador<palabra.length()){
				tablero.obtenerCelda(x, y).establecerLetra(palabra.charAt(contador));
				x+=sentido.obtenerDesplazamientoFila();
				y+=sentido.obtenerDesplazamientoColumna();
				contador++;
			}
		}
		palabras.add(palabra);
		return resultado;
	}
	/**
	 * Devuelve una celdaysentido aleatoria de las posiciones de una palabra
	 * @param palabra palabra
	 * @return celdaysentido aleatoria
	 */
	private CeldaySentido celdaAleatoria(String palabra){
		List<CeldaySentido> posiciones=palabraCabeTotal(palabra);
		int pos=0;
		if (posiciones.isEmpty()){
			return null;
		}
		pos=ThreadLocalRandom.current().nextInt(0,posiciones.size());
		return posiciones.get(pos);
	}
	/**
	 * Devuelve el tablero.
	 * @return tablero
	 */
	public Tablero obtenerTablero(){
		return tablero;
	}
	/**
	 * Rellena el tablero con letras aleatorias, menos las palabras que ya estén colocadas.
	 */
	public void rellenar(){
		for (int i=0;i<tablero.obtenerNumeroFilas();++i){
			for (int j=0;j<tablero.obtenerNumeroColumnas();++j){
				if (tablero.obtenerCelda(i, j).estaVacia()){
					int pos=ThreadLocalRandom.current().nextInt(1,27);
					tablero.obtenerCelda(i, j).establecerLetra((char)(pos + 96));
				}else{
					solucion.add(tablero.obtenerCelda(i, j));
				}
			}
		}
	}
	/**
	 * Coloca palabras en el tablero.
	 * @param palabras lista de palabras.
	 * @param MAX máximo de palabras a colocar
	 * @return true si las puede colocar todas, false si no
	 */
	public boolean colocarPalabras(List<String> palabras, int MAX){
		boolean resultado=true;
		int contador=0;
		for (String s: palabras){
			if (contador<MAX){
				resultado&=colocarPalabra(s);
				contador++;
			}
		}
		return resultado;
	}
	/**
	 * Devuelve si una palabra está en el tablero.
	 * @param palabra palabra
	 * @return true si está, false si no
	 */
	public boolean palabraEnTablero(String palabra){
		boolean resultado=true;
		resultado=!(palabra==null);
		resultado&=palabras.size()>0;
		resultado&=palabras.contains(palabra);
		return resultado;
	}
	/**
	 * Devuelve la palabra entre celdas, siempre que esté en un sentido válido.
	 * @param origen celda de origen
	 * @param destino celda de destino
	 * @return String de la palabra
	 */
	public String returnPalabra(Celda origen,Celda destino){
		Sentido sentido=obtenerSentido(origen,destino);
		String resultado;
		if (sentido==null){
			resultado=null;
		}else{
			resultado=new String();
			int x=origen.obtenerFila();
			int y=origen.obtenerColumna();
			while (x!=destino.obtenerFila()+sentido.obtenerDesplazamientoFila()||y!=destino.obtenerColumna()+sentido.obtenerDesplazamientoColumna()){
				resultado+=tablero.obtenerCelda(x, y).devolverLetra();
				x+=sentido.obtenerDesplazamientoFila();
				y+=sentido.obtenerDesplazamientoColumna();
			}
		}
		return resultado;
		
	}
	/**
	 * Elimina una palabra
	 * @param palabra palabra
	 */
	public void eliminaPalabra(String palabra){
		if (palabras.contains(palabra)){
			palabras.remove(palabras.indexOf(palabra));
		}
	}
	public String palabrastoString(){
		String s=new String();
		for (String str:palabras){
			s+=str;
			s+="  ";
		}
		return s;
	}
	/**
	 * Obtiene un sentido a partir de origen y destino
	 * @param origen celda de origen
	 * @param destino celda de destino
	 * @return Sentido de las celdas, null si no hay sentido válido.
	 */
	public Sentido obtenerSentido(Celda origen,Celda destino){
		Sentido sentido=null;
		int x=destino.obtenerFila();
		int y=destino.obtenerColumna();
		x=x-origen.obtenerFila();
		y=y-origen.obtenerColumna();
		if (x==0){
			if (y!=0){
				y/=Math.abs(y);
				if (y==1){
					sentido=Sentido.DERECHA;
				}else if (y==-1){
					sentido=Sentido.IZQUIERDA;
				}
			}
		}else if (y==0){
			if (x!=0){
				x/=Math.abs(x);
				if (x==1){
					sentido=Sentido.ABAJO;
				}else if (x==-1){
					sentido=Sentido.ARRIBA;
				}
			}
		}else if (Math.abs(x)==Math.abs(y)){
			if (x>0){
				if (y>0){
					sentido=Sentido.DIAGONAL_NO_SE_ABAJO;
				}else if (y<0){
					sentido=Sentido.DIAGONAL_SO_NE_ABAJO;
				}
			}else if (x<0){
				if (y>0){
					sentido=Sentido.DIAGONAL_SO_NE_ARRIBA;
				}else if (y<0){
					sentido=Sentido.DIAGONAL_NO_SE_ARRIBA;
				}
			}
		}
		return sentido;
	}
	/**
	 * Devuelve la solucion.
	 * @return Lista de celdas
	 */
	public List<Celda> obtenerSolucion(){
		return solucion;
	}
}
