/*
 * TrucoListener.java
 *
 * Created on 5 de marzo de 2003, 03:20 PM
 */

package py.edu.uca.fcyt.toluca.event;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.game.*;

/**
 *
 * @author  Julio Rey || Christian Benitez
 */
public interface TrucoListener {
    
    void play(TrucoEvent event);
    void turn (TrucoEvent event);
    void endOfHand(TrucoEvent event);
    void cardsDeal(TrucoEvent event);
    void handStarted(TrucoEvent event);
    void gameStarted(TrucoEvent event);
    void endOfGame(TrucoEvent event);
    Player getAssociatedPlayer();
}
