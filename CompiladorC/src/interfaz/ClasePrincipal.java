package interfaz;

import gestorErrores.ErrorCompilador;
import gestorErrores.GestorDeErrores;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaAmbito;
import tablaSimbolos.TablaSimbolos;
import token.Token;

import analizadorLexico.AnalizadorLexico;
import analizadorSemantico.AnalizadorSemantico;
import analizadorSintactico.AnalizadorSintactico;

public class ClasePrincipal extends JFrame {

	
	JPanel panelPrincipal;
	JButton botonScan,botonCargar,botonCompleto,botonIniciar;
	
	JLabel infoCodigo;
	
	JTextArea  textoCodigo;
	JTextPane textoToken,textoError;
	
	ContenedorTS cuadroTS;
	
	AnalizadorLexico analizador;
	AnalizadorSintactico sintactico;
	AnalizadorSemantico semantico;
	
	TablaSimbolos TS;
	GestorDeErrores GE;
	static ArrayList<Token> listaToken;
	String ruta;
	JFileChooser seleFile; //Asi recuerda el ultimo directorio

	//Controla si se usara el texto del textoCodigo o la ruta elegida para pasar al analizador.
	boolean modoTextoEscrito; 
	
	//Controla si ya se ha iniciado el modo analisis o no.
	boolean iniciado;
	
	public ClasePrincipal(){//este código es el que lo lanza todo
		
		this.addWindowListener(new OyenteVentana());
		//inicializar componentes de la interfaz
		panelPrincipal = new JPanel(); 
		panelPrincipal.setLayout(new BorderLayout());
		
		JPanel  panelTexto = new JPanel(new GridLayout(2,2));  ;
		JPanel panelBotones= new JPanel(new GridLayout(1,2)); ;
		
		panelPrincipal.add(panelTexto,BorderLayout.CENTER);
		panelPrincipal.add(panelBotones,BorderLayout.NORTH);
		
		this.setContentPane(panelPrincipal);
		
		botonScan=new JButton("Scan -Nuevo Token-");
		botonCargar = new JButton("Cargar nuevo codigo");
		botonCompleto = new JButton("Analisis completo");
		botonIniciar = new JButton("Iniciar analisis");
		
		
		iniciado=false;
		modoTextoEscrito=true; //Empieza en modo texto para evitar errores.
		botonCompleto.setEnabled(false);
		botonScan.setEnabled(false);
		botonCargar.setEnabled(true);
		
		
		
		textoError =new JTextPane();
		textoToken =new JTextPane();
		//textoTS =new JTextArea();
		cuadroTS = new ContenedorTS();
		
		textoError.setEditable(false);
		textoToken.setEditable(false);
		//textoTS.setEditable(false);
		
		//Panel de texto: 1 label de info + 1 JScroll con el textoCodigo.
		textoCodigo =new JTextArea("int main(void);\nint main(){ \n 	int a=1; \n	int b=c; \n}");
		textoCodigo.addKeyListener(new OyenteTeclado());
		infoCodigo= new JLabel();
		JPanel panelCodigo = new JPanel(new BorderLayout());
		panelCodigo.add(infoCodigo,BorderLayout.NORTH);
		panelCodigo.add(new JScrollPane(textoCodigo),BorderLayout.CENTER);
		
		
		panelBotones.add(botonScan);
		panelBotones.add(botonCargar);
		panelBotones.add(botonCompleto);
		panelBotones.add(botonIniciar);
			
		panelTexto.add(panelCodigo);
		panelTexto.add(new JScrollPane(textoError));
		panelTexto.add(new JScrollPane(textoToken));
		panelTexto.add(cuadroTS);
		
		botonScan.addActionListener(new Oyente());
		botonCargar.addActionListener(new Oyente());
		botonCompleto.addActionListener(new Oyente());
		botonIniciar.addActionListener(new Oyente());
		seleFile = new JFileChooser();
	
	}
	public static ArrayList<Token> getListaToken(){
		return listaToken;
	}
	void actualizarTextoError(){
		//ACTUALIZACION TEXTO ERROR
		textoError.setText("");
		SimpleAttributeSet attrs2 = new SimpleAttributeSet();
		ArrayList<ErrorCompilador> listaErrores=GE.getListaErrores();
		for(int i=0; i<listaErrores.size();i++){
	
			
			if (i==(listaErrores.size()-1)){
				StyleConstants.setBold(attrs2, true);
			}
		
			try {
				textoError.getStyledDocument().insertString(textoError.getStyledDocument().getLength(), listaErrores.get(i).toString()+"\n", attrs2);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			} 
		}
	}
	
	void actualizarTextoToken(){
		//ACTUALIZACION TEXTO TOKEN
		textoToken.setText("");
		SimpleAttributeSet attrs = new SimpleAttributeSet();
		//for(Iterator<Token> it=listaToken.iterator();it.hasNext();){
		for(int i=0; i<listaToken.size();i++){
	
			
			if (i==(listaToken.size()-1)){
				StyleConstants.setBold(attrs, true);
			}
		
			try {
				textoToken.getStyledDocument().insertString(textoToken.getStyledDocument().getLength(), listaToken.get(i).toString()+"\n", attrs);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			} 
		}
	}
	
	
	/*
	void actualizarTextoTS(){
		textoTS.setText(TS.tablaReservadasToString()+TS.tablaGlobalToString()+"\n");

	}*/
	
	
	
	
	class Oyente implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (botonScan==e.getSource()){
				if (analizador!=null){
					try{
					Token nuevoToken =analizador.Scan();
					listaToken.add(nuevoToken);
					
					}catch (Exception excepcion){ excepcion.printStackTrace();}
					actualizarTextoToken();
					actualizarTextoError();
					cuadroTS.actualizar(cuadroTS.representada);
				}
				
			}
			else if (botonCargar==e.getSource()){
				seleFile.showOpenDialog(null);
				File archivoLectura= seleFile.getSelectedFile();
				if (archivoLectura!=null){ //Para el boton cancelar
					ruta =archivoLectura.getPath();
					
					Scanner lector;
					textoCodigo.setText("");
					textoToken.setText("");
					textoError.setText("");
					modoTextoEscrito=false; //Se cargara desde un archivo.
					infoCodigo.setText("Archivo cargado desde: "+ruta);
					infoCodigo.setForeground(Color.black);
					
					try {
						lector = new Scanner(archivoLectura);
						while(lector.hasNextLine())	textoCodigo.append(lector.nextLine()+"\n");
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}	
				}	
			}else if (botonCompleto==e.getSource()){
				if (analizador!=null){
					/*
					try{
						Token nuevoToken=null;	
						while(nuevoToken==null||nuevoToken.getTipo()!=Token.TipoToken.FIN){
							nuevoToken =analizador.Scan();
							listaToken.add(nuevoToken);
						}
					}catch (Exception excepcion){ excepcion.printStackTrace();}
					
					
					
					
					*/
					if(sintactico.analizar()){
						System.out.println("Perfect");
					}else{
						System.out.println("Nada");
					}
					actualizarTextoToken();
					actualizarTextoError();
					cuadroTS.actualizar(cuadroTS.representada);
					
				}
				
			}else if (botonIniciar==e.getSource()){
				if (iniciado){ //APAGA EL MODO ANALISIS
					botonIniciar.setText("Iniciar analisis");
					iniciado=false;
					botonCompleto.setEnabled(false);
					botonScan.setEnabled(false);
					botonCargar.setEnabled(true);
					textoCodigo.setEditable(true);
					
					
				}else{ //INICIA EL MODO ANALISIS
					botonIniciar.setText("Terminar analisis");
					iniciado=true;
					botonCompleto.setEnabled(true);
					botonScan.setEnabled(true);
					botonCargar.setEnabled(false);
					textoCodigo.setEditable(false);
					
					TS= new TablaSimbolos();
					GE=new GestorDeErrores();
					listaToken= new ArrayList<Token>();
					cuadroTS.setTS(TS);
					
					
					if (modoTextoEscrito){
						analizador =new AnalizadorLexico(true,textoCodigo.getText(),TS,GE);
						semantico= new AnalizadorSemantico(GE, TS);
						sintactico= new AnalizadorSintactico(analizador, GE, TS, semantico);
					}else{
						analizador = new AnalizadorLexico(false,ruta, TS,GE);
						semantico= new AnalizadorSemantico(GE, TS);
						sintactico= new AnalizadorSintactico(analizador, GE, TS, semantico);
						
					}
					
				}

				
			}
			
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	class OyenteTeclado implements KeyListener{
 

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			modoTextoEscrito=true; //Se cargara desde el texto escrito.
			infoCodigo.setText("Código escrito/modificado.");
			infoCodigo.setForeground(Color.red);
		}
		
	}
	
	
	
	
	
	class OyenteVentana implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			System.exit( 0 );
			
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			System.exit( 0 );
			
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
	}
	
	
	
	
	
	
	
}
