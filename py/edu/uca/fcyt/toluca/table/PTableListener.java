package py.edu.uca.fcyt.toluca.table;

import java.awt.event.MouseEvent;

/** Listener de eventos en PlayTable */
interface PTableListener
{
	/**
     * Invocado cuando se hizo click en la mesa de juego
     * @param x			posici�n x de la mesa
     * @param y			posici�n y de la mesa
     * @param button	bot�n (ver MouseEvent)
     */
	void mouseClicked(int x, int y, MouseEvent e);
	
	void say(byte say);
}	