package py.edu.uca.fcyt.toluca;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;


public class RankingTableModel extends DefaultTableModel{

//	DefaultTableModel  ;
	static	Object[] TableHeader = {"Nivel","Jugador","Puntaje" };
	static	Object[][] TableData = {{ " ", " "," " }};


	RankingTableModel(){
		super(TableData,TableHeader);
	}

	void addPlayer(String Nombre, int Rating){

	  if( filaDelJugador(Nombre)<0){
	  	Object[] fila = {"....", Nombre, new Integer(Rating) };
	  	addRow(fila);
	  } else {
	   	System.out.println("El player que quiere agregarse ya existe!");
	  }
	}

	void removeplayer(String Nombre){
	  int fila = filaDelJugador(Nombre);
	  if( fila>=0)
	   	removeRow(fila);
	  else
	 	System.out.println("El player que quiere eliminarse no ya existe!");
	}

	void modifyplayer(String Nombre, int Rating){
		int fila = filaDelJugador(Nombre);
		if (fila>=0){
			setValueAt(new Integer(Rating),fila,2);
		}
	}
        
	int filaDelJugador(String Nombre){

		String aux = new String();
		for(int i=0; i< getRowCount(); i++){
			aux = (String)  getValueAt(i,1);
			if(Nombre.equals(aux))
				return i;
		}
		return -1; //si no encontro el jugador en ninguna fila
	}
}


