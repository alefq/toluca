/*
 * TolucaAgent.java
 *
 * Created on May 23, 2003, 7:58 PM
 */

package py.edu.uca.fcyt.toluca.ai;

import py.edu.uca.fcyt.toluca.event.*;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;
import py.edu.uca.fcyt.game.Player;


/**
 *
 * @author  aalliana
 */
public class TolucaAgent extends TrucoPlayer implements TrucoListener {
    
    /** Creates a new instance of TolucaAgent */
    public TolucaAgent(String nombre) {
        super(nombre);
    }
    
    public void cardsDeal(TrucoEvent event) {
    }
    
    public void endOfGame(TrucoEvent event) {
    }
    
    public void endOfHand(TrucoEvent event) {
    }
    
    public void gameStarted(TrucoEvent event) {
    }
    
    public Player getAssociatedPlayer() {
        return ( Player) this;
    }
    
    public void handStarted(TrucoEvent event) {
    }
    
    public void play(TrucoEvent event) {
    }
    
    public void turn(TrucoEvent event) {
    }
    
}
