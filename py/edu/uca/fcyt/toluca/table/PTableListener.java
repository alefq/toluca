package py.edu.uca.fcyt.toluca.table;

import java.awt.event.*;

/** Listener de eventos en PlayTable */
interface PTableListener
{
	public void mouseClicked(float x, float y, MouseEvent e);
	
	public void say(int say);
}	