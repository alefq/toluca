package py.edu.uca.fcyt.toluca.table;

/*
 * @(#)Truco.java 1.0 03/03/17
 *
 * You can modify the template of this file in the
 * directory ..\JCreator\Templates\Template_1\Project_Name.java
 *
 * You can also create your own project template by making a new
 * folder in the directory ..\JCreator\Template\. Use the other
 * templates as examples.
 *
 */

import py.edu.uca.fcyt.toluca.statusGame.*;
import py.edu.uca.fcyt.toluca.game.*;


import java.awt.*;
import java.awt.event.*;
class Truco {
	
	public Truco() {
	}

	public static void main(String args[]) {
		byte x=2,y=1;
                TrucoStatusTable xx;
                TrucoCard[] cc;
                xx=new TrucoStatusTable((byte)4);
                System.out.println("Starting Truco...");
                           //quitar desde aca
                cc=xx.getPlayerCards((byte)1);
                xx.jugarCarta((byte)1,cc[2]);
                xx.imprimirEstado((byte)0);
                //estado[0].statusPrint();


                
                
	}
}
