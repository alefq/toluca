/*
 * TrucoPlayer.java
 *
 * Created on 5 de marzo de 2003, 02:55 PM
 */

package py.edu.uca.fcyt.toluca.game;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.game.*;



/** Clase que representa a un jugador de Truco.
 * @author Julio Rey || Christian Benitez
 */
public class TrucoPlayer extends Player{
    
    /** Constructor de un TrucoPlayer con su nombre identificador.
     * @param name String que se asignará como nombre identificador del TrucoPlayer.
     */
    /*public TrucoPlayer(){
    }*/
    public TrucoPlayer (String name) {
        this.name = name;
    }
    /** Constructor de un TrucoPlayer.
     */    
    public TrucoPlayer (){
        super();
    }
    
    public TrucoPlayer(String name, int rating) {
        super (name, rating);
    
    }
}
