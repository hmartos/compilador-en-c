package interfaz;



import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.JTableHeader;

import tablaSimbolos.TablaAmbito;
import tablaSimbolos.TablaSimbolos;

public class ContenedorTS extends JPanel {
		
		JPanel panelLinks;
		JScrollPane panelTabla;
		
		JTable tabla;
		
		JButton botonGlobal;
		JButton botonActual;
		JButton botonContenedora;
		JButton botonReservadas;
		
		ArrayList<JButton> botonesLink;
		TablaAmbito representada;
		TablaSimbolos TS;
		JPanel contIntermedio;
		
		public ContenedorTS() {
			
			OyenteTS o=new OyenteTS();
			
			botonGlobal =new JButton("Global");
			botonGlobal.addActionListener(o);
			botonActual =new JButton("Actual");
			botonActual.addActionListener(o);
			botonContenedora =new JButton("Contenedora");
			botonContenedora.addActionListener(o);
			botonReservadas =new JButton("Pal Res");
			botonReservadas.addActionListener(o);
			
			
			
			this.setLayout(new BorderLayout());
			
			JPanel panelNorte= new JPanel(new GridLayout(2,1));
			this.add(panelNorte, BorderLayout.NORTH);
			
			JPanel panelBotGen= new JPanel(new GridLayout(1,4));
			panelLinks=new  JPanel( new BorderLayout());
			panelNorte.add(panelBotGen);
			panelNorte.add(panelLinks);
			
			panelBotGen.add(botonGlobal);
			panelBotGen.add(botonActual);
			panelBotGen.add(botonContenedora);
			panelBotGen.add(botonReservadas);
		
			this. add(panelTabla= new JScrollPane(contIntermedio=new JPanel()),BorderLayout.CENTER);
			
		}
		
		public void setTS(TablaSimbolos TS){
			this.TS=TS;
			representada=TS.getGlobal();
			actualizar(representada);
		}
		
		public void actualizar(TablaAmbito repr){
			if (repr!=null){
				representada=repr;
				botonesLink= new ArrayList<JButton>();
				OyenteTS o=new OyenteTS();
				panelLinks.removeAll();
				for (int i=0;i<representada.contenido.size();i++){
					botonesLink.add(new JButton("link "+i));
					botonesLink.get(i).addActionListener(o);
					panelLinks.add(botonesLink.get(i),BorderLayout.NORTH);
				}
				
				//tabla= new JTable(representada.tabla.size(), 2);
				
				
				int i=0; //representada.tabla.size()
				//panelTabla.removeAll();
				contIntermedio.removeAll();
				contIntermedio.setLayout( new GridLayout(representada.tabla.size(),2));
				
				for(Iterator<String> it= representada.tabla.keySet().iterator();it.hasNext();){
					String lex=it.next();
					contIntermedio.add(new JLabel(lex.toString())); // El lexema
					contIntermedio.add(new JLabel(representada.tabla.get(lex).toString())); // Los atributos
					i++;
				}
				
				repaint();
				
				panelTabla.setViewportView(contIntermedio);
				panelTabla.setLayout(new ScrollPaneLayout());
				panelTabla.repaint();
				
				updateUI();
				 //CON TABLAS POR HACER
				/*
				
				String[] titulos={"Lexema" ,"Atributos"};
				
				int i=0; //representada.tabla.size()
				Object [][] datos= new Object[100][2];
				for(Iterator<String> it= representada.tabla.keySet().iterator();it.hasNext();){
					String lex=it.next();
					datos[i][0]=lex.toString(); // El lexema
					datos[i][1]=representada.tabla.get(lex).toString(); // Los atributos
					i++;
				}
				this.remove(panelTabla);
				repaint();
				tabla=new JTable(datos, titulos);
				panelTabla= new JScrollPane(tabla);
				tabla.setFillsViewportHeight(true);
				//panelTabla.add(new JLabel("asasa"));
				this.add(panelTabla,BorderLayout.CENTER);
				
				panelTabla.repaint();
				tabla.repaint();
				panelTabla.setVisible(true);
				tabla.setEnabled(true);
				tabla.
				tabla.setShowVerticalLines(true);
				repaint();*/
			}
		}
		
		
		
		class OyenteTS implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if (TS!=null){
					if (botonGlobal==e.getSource()) actualizar(TS.getGlobal());
					else if (botonActual==e.getSource()) actualizar(TS.getActual());
					else if (botonReservadas==e.getSource()) actualizar(TS.getReservadas());
					else if (botonContenedora==e.getSource()) actualizar(representada.contenedor);
					else{
						for(int i=0;i<botonesLink.size();i++){
							if(botonesLink.get(i)==e.getSource())actualizar(representada.contenido.get(i));
						}
					}
				
				
				}
			}
				
			
			
		}
		
		
	
}
