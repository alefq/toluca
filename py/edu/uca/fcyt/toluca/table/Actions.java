package py.edu.uca.fcyt.toluca.table;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;

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