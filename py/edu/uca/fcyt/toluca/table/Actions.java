package py.edu.uca.fcyt.toluca.table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;

class Actions extends JButton
{
	//Constructor	
	public Actions(JButton[] buttons, ActionListener actListener) 
	{	 

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i].setPreferredSize(new Dimension(100, 22));
			buttons[i].setMaximumSize(new Dimension(100, 22));
			buttons[i].addActionListener(actListener);
			add(buttons[i]);
		}
		
		setBorder(null);
	}
		
}