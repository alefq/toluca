/*
 * PopupTrucoPlays.java
 *
 * Created on March 27, 2003, 10:22 PM
 */

package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.game.TrucoPlay;
import java.util.*;

/**
 *
 * @author  Owner
 */
class PopupTrucoPlays extends BasePopupMenu 
{
    /** 
     * Crea una nueva instancia de PopupTrucoPlays
     * @param ptListener 	Listener de eventos de mesa de juego
     * @param aPlays		Vector de Bytes de las jugadas habilitadas
     */
    public PopupTrucoPlays(PTableListener ptListener, Vector aPlays)
    {
    	Byte play;
    	String name;
    	for (int i = 0; i < aPlays.size(); i++)
    	{
    		play = (Byte) aPlays.get(i);
    		name = (String) Table.pNames.get(play);
    		add(new PopupActions(ptListener, play.byteValue(), name));
    	}
    }
}




