package sopa.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sopa.modelo.*;
import sopa.control.*;

/**
 * Clase de interfaz gráfixa de la sopa de letras.
 * @author FELIX
 *
 */
public class SopaGUI {
	/**
	 * Tamaño del tablero cuadrado.
	 */
	private static int N=10;
	/**
	 * Lista de los botones
	 */
	private final List<Cell> list = new ArrayList<Cell>();
	/**
	 * Primer botón pulsado.
	 */
	private Cell botonAc1=null;
	/**
	 * Segundo botón pulsado.
	 */
	private Cell botonAc2=null;
	/**
	 * Arbitro.
	 */
	private static Arbitro arbitro;
	/**
	 * Lineas a introducir en la sopa de letras.
	 */
	private static List<String> lines=new ArrayList<String>();
	/**
	 * Lista de celdas con palabras correctas.
	 */
	private List<Cell> guardadas=new ArrayList<Cell>();
	/**
	 * Nombre del fichero de las palabras.
	 */
	final static String FILE_NAME = "./dic.txt";
	/**
	 * Encoding del diccionario.
	 */
	final static Charset ENCODING = StandardCharsets.UTF_8;
	/**
	 * Número máximo de palabras.
	 */
	final static int MAX=10;
	/**
	 * Label de las palabras
	 */
	private static JLabel labelPal;
	/**
	 * Devuelve el boton asociado a unas coordenadas.
	 * @param r fila
	 * @param c columna
	 * @return botón de tipo Cell
	 */
	private Cell getGridButton(int r, int c) {
		int index = r * N + c;
		return list.get(index);
	}
	/**
	 * Crea cada botón.
	 * @param row fila
	 * @param col columna
	 * @return Botón creado de tipo Cell
	 */
	private Cell createGridButton(final int row, final int col) {
		final Cell b = new Cell(Color.WHITE,arbitro.obtenerTablero().obtenerCelda(row, col).obtenerString(),row,col);
		b.addActionListener(new ActionListener() {
			/**
			 * Escucha de acciones.
			 * @param e AccionEvent
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (botonAc1==null){
					botonAc1=b;
					b.toColor(Color.GREEN);
				}else if (botonAc2==null){
					botonAc2=b;
					Celda origen=arbitro.obtenerTablero().obtenerCelda(botonAc1.getRow(), botonAc1.getCol());
					Celda destino=arbitro.obtenerTablero().obtenerCelda(botonAc2.getRow(), botonAc2.getCol());
					Sentido sentido=arbitro.obtenerSentido(origen,destino);


					if (sentido!=null){
						int x=origen.obtenerFila();
						int y=origen.obtenerColumna();
						boolean guardar=false;
						System.out.println(arbitro.returnPalabra(origen, destino));
						if (arbitro.palabraEnTablero(arbitro.returnPalabra(origen, destino))){
							guardar=true;
							arbitro.eliminaPalabra(arbitro.returnPalabra(origen, destino));
							labelPal.setText(arbitro.palabrastoString());
						}else{
							b.toColor(Color.RED);
						}
						while (x!=destino.obtenerFila()+sentido.obtenerDesplazamientoFila()||y!=destino.obtenerColumna()+sentido.obtenerDesplazamientoColumna()){
							Cell aux = SopaGUI.this.getGridButton(x, y);
							x+=sentido.obtenerDesplazamientoFila();
							y+=sentido.obtenerDesplazamientoColumna();
							if (guardar){
								guardadas.add(aux);
							}

						}
					}else{
						b.toColor(Color.RED);
					}


				}else{
					botonAc1=null;
					botonAc2=null;

					botonAc1=b;
					b.toColor(Color.GREEN);
				}
				clear();
			}
		});
		return b;
	}
	/**
	 * Crea el jpanel con el layout de botones.
	 * @return Jpanel creado.
	 */
	private JPanel createGridPanel() {
		JPanel p = new JPanel(new GridLayout(N, N));
		for (int i = 0; i < N * N; i++) {
			int row = i / N;
			int col = i % N;
			Cell gb = createGridButton(row, col);
			list.add(gb);
			p.add(gb);
		}
		return p;
	}
	/**
	 * Muestra la aplicación final.
	 */
	private void display() {
		JFrame f = new JFrame("Sopa de letras");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(createGridPanel());
		JPanel panel = new JPanel();
		labelPal.setFont(new Font("Arial",Font.PLAIN,20));
		panel.add(labelPal);
		f.getContentPane().add(panel, BorderLayout.SOUTH);
		JPanel panelAr=new JPanel(new GridLayout(0,2));
		JButton sol=new JButton("Solución");
		sol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (Celda c: arbitro.obtenerSolucion()){
					guardadas.add(SopaGUI.this.getGridButton(c.obtenerFila(), c.obtenerColumna()));
				}
				clear();
			}
		});
		panelAr.add(sol);
		JButton reset=new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				f.dispose();
				inicializar();
				
			}
		});
		panelAr.add(reset);
		f.getContentPane().add(panelAr, BorderLayout.NORTH);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	/**
	 * Main.
	 * @param args argumentos como array de strings.
	 */
	public static void main(String[] args) {
		inicializar();
	}
	/**
	 * Clase interna de cell, un tipo de botón jButton.
	 * @author FELIX
	 *
	 */
	public class Cell extends JButton {
		/**
		 * Necesaria.
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * Fila.
		 */
		private int row;
		/**
		 * Columna.
		 */
		private int col;
		/**
		 * Constructor de cada cell.
		 * @param background color de fondo
		 * @param s cadena de texto en el botón
		 * @param row fila
		 * @param col columna
		 */
		public Cell(Color background,String s,int row, int col) {
			super(s);
			this.row=row;
			this.col=col;
			setContentAreaFilled(false);
			setBorderPainted(true);
			setBackground(background);
			setOpaque(true);
			setFont(new Font("Arial",Font.PLAIN,20));

		}
		/**
		 * Devuelve el tamaño preferido.
		 */
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(70, 70);
		}
		/**
		 * Cambia de color el botón.
		 * @param color color a cambiar
		 */
		public void toColor(Color color){
			setBackground(color);
		}
		/**
		 * Devuelve la fila.
		 * @return fila
		 */
		public int getRow(){
			return row;
		}
		/**
		 * Devuelve la columna.
		 * @return columna
		 */
		public int getCol(){
			return col;
		}
	}
	/**
	 * Añade palabras al array lines.
	 */
	private static void inicializarPalabras(){
		lines.add("opel");
		lines.add("honda");
		lines.add("renault");
		lines.add("cadillac");
		lines.add("citroen");
		lines.add("ferrari");
		lines.add("fiat");
		lines.add("ford");
		lines.add("hyundai");
		lines.add("nissan");
		lines.add("peugeout");
		
	}
	/**
	 * Aplica el color blanco a las celdas que no tengan palabras ganadoras o que no se estén usando.
	 */
	private void clear(){
		for (int i=0;i<N;++i){
			for (int j=0;j<N;++j){
				Cell aux = SopaGUI.this.getGridButton(i, j);
				if (guardadas.contains(aux)){
					aux.toColor(Color.ORANGE);
				}else if (aux.equals(botonAc1)){
					aux.toColor(Color.GREEN);;
				}else if (aux.equals(botonAc2)){
					;
				}else{
					aux.toColor(Color.WHITE);
				}
			}
		}
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
	/**
	 * Tamaño máximo de las palabras.
	 * @param palabras lista de palabras
	 * @return tamaño máx
	 */
	private static int maxSizeString(List<String> palabras){
		int tamano=0;
		for (String s:palabras){
			if (tamano<s.length()){
				tamano=s.length();
			}
		}
		return tamano;
	}
	/**
	 * Inicializa los valores.
	 */
	private static void inicializar(){
		File f = new File(FILE_NAME);
		if(f.exists() && !f.isDirectory()) { 
			try {
				lines=readSmallTextFile(FILE_NAME);
			} catch (IOException e) {
				// Excepción sin declarar.
				e.printStackTrace();
			}
		}else{
			inicializarPalabras();
		}
		N=maxSizeString(lines)+4;
		arbitro=new Arbitro(N,N);
		arbitro.colocarPalabras(lines, MAX);
		System.out.println(arbitro.obtenerTablero().toString());
		arbitro.rellenar();
		labelPal=new JLabel(arbitro.palabrastoString());
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new SopaGUI().display();
			}
		});
	}
}
