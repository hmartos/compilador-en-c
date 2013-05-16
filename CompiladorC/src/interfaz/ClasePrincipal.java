package interfaz;

import gestorErrores.ErrorCompilador;
import gestorErrores.GestorDeErrores;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import codigoFinal.CodigoFinal;
import codigoIntermadio.InstruccionIntermedio;

import tablaSimbolos.EntradaTabla;
import tablaSimbolos.TablaAmbito;
import tablaSimbolos.TablaSimbolos;
import token.Token;

import analizadorLexico.AnalizadorLexico;
import analizadorSemantico.AnalizadorSemantico;
import analizadorSintactico.AnalizadorSintactico;

public class ClasePrincipal extends JFrame {

	
	JPanel panelPrincipal;
	JButton botonCargar,botonCompleto,botonIniciar,botonWENS,botonGuardar,botonENS;
	
	JLabel infoCodigo;
	
	JTextArea  textoCodigo;
	JTextPane texto3Dir,textoError,textoFinal;
	JPanel num3Dir,numFinal,numCodigo;
	
	ContenedorTS cuadroTS;
	
	AnalizadorLexico analizador;
	AnalizadorSintactico sintactico;
	AnalizadorSemantico semantico;
	CodigoFinal codFinal;
	
	TablaSimbolos TS;
	GestorDeErrores GE;
	static ArrayList<Token> listaToken;
	String ruta;
	JFileChooser seleFile; //Asi recuerda el ultimo directorio
	JFileChooser guardarFile;
	
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
		

		JPanel panelSalida=new JPanel();
		BoxLayout blSalida= new BoxLayout(panelSalida,BoxLayout.X_AXIS);

		panelSalida.setLayout(blSalida);
		
		JPanel panelBotones= new JPanel(new GridLayout(1,2)); ;
		
		panelPrincipal.add(panelTexto,BorderLayout.CENTER);
		panelPrincipal.add(panelBotones,BorderLayout.NORTH);
		
		
		this.setContentPane(panelPrincipal);
		
		
		botonCargar = new JButton("Cargar nuevo codigo");
		botonCompleto = new JButton("Analisis completo");
		botonIniciar = new JButton("Iniciar analisis");
		botonWENS = new JButton("WENS2001");
		botonENS = new JButton("ENS2001 (consola)");
		botonGuardar=new JButton("Guardar código final");
		
		iniciado=false;
		modoTextoEscrito=true; //Empieza en modo texto para evitar errores.
		botonCompleto.setEnabled(false);
		
		botonCargar.setEnabled(true);
		
		
		

		textoError =new JTextPane();
		texto3Dir =new JTextPane();
		textoFinal =new JTextPane();
		
		
		

		
		num3Dir=new JPanel();
		numFinal=new JPanel();
		numCodigo=new JPanel();
		BoxLayout bl3Dir = new BoxLayout(num3Dir,BoxLayout.Y_AXIS);
		BoxLayout blFinal = new BoxLayout(numFinal,BoxLayout.Y_AXIS);
		BoxLayout blCodigo = new BoxLayout(numCodigo,BoxLayout.Y_AXIS);

		num3Dir.setLayout(bl3Dir);
		numFinal.setLayout(blFinal);
		numCodigo.setLayout(blCodigo);
		

		
		//textoTS =new JTextArea();
		cuadroTS = new ContenedorTS();
		
		textoError.setEditable(false);
		texto3Dir.setEditable(false);
		textoFinal.setEditable(true);
		//textoTS.setEditable(false);
		
		//Panel de texto: 1 label de info + 1 JScroll con el textoCodigo.
		textoCodigo =new JTextArea("int fun(int);\nint main(void);\nint fun(int i){\n}\nint main(){ \n 	int a=1; \n	int b=c; \n}");
		textoCodigo.addKeyListener(new OyenteTeclado());
		infoCodigo= new JLabel();
		JPanel panelCodigo = new JPanel(new BorderLayout());
		panelCodigo.add(infoCodigo,BorderLayout.NORTH);
		
		
		JPanel cont2=new JPanel(new GridLayout(1,1));
		
		JPanel cont=new JPanel (new BorderLayout());
		
		cont.add(numCodigo, BorderLayout.WEST);
		cont.add(textoCodigo,BorderLayout.CENTER);
		cont2.add(cont);
		panelCodigo.add(new JScrollPane(cont2),BorderLayout.CENTER);
	//	panelCodigo.add(cont2,BorderLayout.CENTER);
		
		
		//actualziar lineas numCodigo
		numCodigo.removeAll();
		for (int i=0;i<textoCodigo.getLineCount();i++){
			numCodigo.add(new JLabel(String.valueOf(i+1)+" "));
		}
		
		
		JPanel panel3Dir= new JPanel(new BorderLayout());
		JPanel panelFinal= new JPanel(new BorderLayout());
		panel3Dir.add(texto3Dir,BorderLayout.CENTER);
		panel3Dir.add(num3Dir,BorderLayout.WEST);
		panelFinal.add(textoFinal,BorderLayout.CENTER);
		panelFinal.add(numFinal,BorderLayout.WEST);
		
		panelSalida.add(panel3Dir,Component.TOP_ALIGNMENT);
		panelSalida.add(panelFinal,Component.TOP_ALIGNMENT);
		
		panelBotones.add(botonCargar);
		panelBotones.add(botonCompleto);
		panelBotones.add(botonIniciar);
		panelBotones.add(botonWENS);
		panelBotones.add(botonENS);
		panelBotones.add(botonGuardar);
			
		panelTexto.add(panelCodigo);
		panelTexto.add(new JScrollPane(textoError));
		panelTexto.add(new JScrollPane(panelSalida));
		panelTexto.add(cuadroTS);
		
		
		botonCargar.addActionListener(new Oyente());
		botonCompleto.addActionListener(new Oyente());
		botonIniciar.addActionListener(new Oyente());
		botonWENS.addActionListener(new Oyente());
		botonENS.addActionListener(new Oyente());
		botonGuardar.addActionListener(new Oyente());
		
		seleFile = new JFileChooser();
		guardarFile = new JFileChooser();
	
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
	
	
	void actualizarTexto3Dir(){
		//ACTUALIZACION TEXTO ERROR
		texto3Dir.setText("");
		SimpleAttributeSet attrs2 = new SimpleAttributeSet();
		ArrayList<InstruccionIntermedio> lista3Dir=semantico.getCi().getLista();
		
		num3Dir.removeAll();
		for(int i=0; i<lista3Dir.size();i++){
	
			
			num3Dir.add(new JLabel(String.valueOf(i+1)+" "));
		
			try {
				texto3Dir.getStyledDocument().insertString(texto3Dir.getStyledDocument().getLength(), lista3Dir.get(i).toString().replaceAll("[\n\r]","")+"\n", attrs2);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			} 
		}
	}

	void actualizarTextoFinal(){
		//ACTUALIZACION TEXTO ERROR
		textoFinal.setText("");
		SimpleAttributeSet attrs2 = new SimpleAttributeSet();
		ArrayList<InstruccionIntermedio> lista3Dir=semantico.getCi().getLista();
		codFinal=new CodigoFinal(TS,lista3Dir);
		codFinal.generarCodigoFinal();
		ArrayList<String> listaFinal = codFinal.getSalida(); 
		
		numFinal.removeAll();
		
		for(int i=0; i<listaFinal.size();i++){
	
			
			numFinal.add(new JLabel(String.valueOf(i+1)+" "));
		
			try {
				textoFinal.getStyledDocument().insertString(textoFinal.getStyledDocument().getLength(), listaFinal.get(i).toString().replaceAll("[\n\r]","")+"\n", attrs2);
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
			 if (botonCargar==e.getSource()){
				seleFile.showOpenDialog(null);
				File archivoLectura= seleFile.getSelectedFile();
				if (archivoLectura!=null){ //Para el boton cancelar
					ruta =archivoLectura.getPath();
					
					Scanner lector;
					textoCodigo.setText("");
					texto3Dir.setText("");
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
					
					//actualizar lineas.
					numCodigo.removeAll();
					for (int i=1;i<textoCodigo.getLineCount();i++){
						numCodigo.add(new JLabel(String.valueOf(i+1)+" "));
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
	
					actualizarTextoError();
					actualizarTexto3Dir();
					actualizarTextoFinal();
					cuadroTS.actualizar(cuadroTS.representada);
					
				}
				
			}else if (botonIniciar==e.getSource()){
				if (iniciado){ //APAGA EL MODO ANALISIS
					botonIniciar.setText("Iniciar analisis");
					iniciado=false;
					botonCompleto.setEnabled(false);
	
					botonCargar.setEnabled(true);
					textoCodigo.setEditable(true);
					
					
				}else{ //INICIA EL MODO ANALISIS
					botonIniciar.setText("Terminar analisis");
					iniciado=true;
					botonCompleto.setEnabled(true);

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

				
			}else if (botonWENS==e.getSource()){
				try {
					String cmd = "winens.exe"; 
					Runtime.getRuntime().exec(cmd); 
				} catch (IOException ioe) {
					System.out.println (ioe);
				}
				
			}else if (botonENS==e.getSource()){
				try {
					
		
					
					File archivoEscritura= new File("compiladorC.ens");
					
					try {
						PrintWriter pw = new PrintWriter(archivoEscritura);
						pw.append(textoFinal.getText());
						pw.close();
						
						
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					Runtime.getRuntime().exec("ejecutar.bat"); 
				} catch (IOException ioe) {
					System.out.println (ioe);
				}
				
			}else if (botonGuardar==e.getSource()){
				guardarFile.showSaveDialog(null);
				File archivoEscritura= guardarFile.getSelectedFile();
				
				try {
					PrintWriter pw = new PrintWriter(archivoEscritura);
					pw.append(textoFinal.getText());
					pw.close();
					
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
			numCodigo.removeAll();
			for (int i=1;i<textoCodigo.getLineCount();i++){
				numCodigo.add(new JLabel(String.valueOf(i+1)+" "));
			}
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
