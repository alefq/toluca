/*
 * PopupTrucoPlays.java
 *
 * Created on March 27, 2003, 10:22 PM
 */

package py.edu.uca.fcyt.toluca.table;

/**
 *
 * @author  Owner
 */
class PopupTrucoPlays extends BasePopupMenu 
{
    /** Creates a new instance of PopupTrucoPlays */
    public PopupTrucoPlays(PTableListener ptListener) 
    {
        add(new PopupActions(ptListener, 0, "Envido"));
        add(new PopupActions(ptListener, 0, "Real Envido"));
        add(new PopupActions(ptListener, 0, "Falta Envido"));
        add(new PopupActions(ptListener, 0, "Flor"));
        add(new PopupActions(ptListener, 0, "Truco"));
        add(new PopupActions(ptListener, 0, "Quiero"));
        add(new PopupActions(ptListener, 0, "Quiero Retruco"));
        add(new PopupActions(ptListener, 0, "Quiero Vale 4"));
    }
}
