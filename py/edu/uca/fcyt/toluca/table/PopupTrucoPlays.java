/*
 * PopupTrucoPlays.java
 *
 * Created on March 27, 2003, 10:22 PM
 */

package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.game.TrucoPlay;

/**
 *
 * @author  Owner
 */
class PopupTrucoPlays extends BasePopupMenu 
{
    /** Creates a new instance of PopupTrucoPlays */
    public PopupTrucoPlays(PTableListener ptListener) 
    {
        add(new PopupActions(ptListener, TrucoPlay.ENVIDO, "Envido"));
        add(new PopupActions(ptListener, TrucoPlay.REAL_ENVIDO, "Real Envido"));
        add(new PopupActions(ptListener, TrucoPlay.FALTA_ENVIDO, "Falta Envido"));
        add(new PopupActions(ptListener, TrucoPlay.FLOR, "Flor"));
        add(new PopupActions(ptListener, TrucoPlay.TRUCO, "Truco"));
        add(new PopupActions(ptListener, TrucoPlay.QUIERO, "Quiero"));
        add(new PopupActions(ptListener, TrucoPlay.RETRUCO, "Quiero Retruco"));
        add(new PopupActions(ptListener, TrucoPlay.VALE_CUATRO, "Quiero Vale 4"));
        add(new PopupActions(ptListener, TrucoPlay.NO_QUIERO, "No quiero"));
        add(new PopupActions(ptListener, TrucoPlay.CANTO_ENVIDO, "Cantar puntos"));
        add(new PopupActions(ptListener, TrucoPlay.PASO_ENVIDO, "Paso envido"));
    }
}




