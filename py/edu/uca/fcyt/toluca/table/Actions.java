package py.edu.uca.fcyt.toluca.table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;

class Actions extends JButton
{
	JButton[] botones = new JButton[9];
	
	//Constructor	
	public Actions()
	{	 
		//Componentes: Botones que aparecen		
		botones[0] = new JButton("Envido");
		botones[1] = new JButton("Real Envido");
		botones[2] = new JButton("Falta Envido");
		botones[3] = new JButton("¡Flor!");
		botones[4] = new JButton("Truco");
		botones[5] = new JButton("Retruco");
		botones[6] = new JButton("Vale 4");
		botones[7] = new JButton("Quiero");
		botones[8] = new JButton("No quiero");

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		for (int i = 0; i < botones.length; i++)
		{
			botones[i].setPreferredSize(new Dimension(100, 22));
			botones[i].setMaximumSize(new Dimension(100, 22));
			add(botones[i]);
		}
		
		setBorder(null);
	}
		
}