/*
 * TrucoTeam.java
 *
 * Created on 5 de marzo de 2003, 02:58 PM
 */

package py.edu.uca.fcyt.toluca.game;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.game.*;

/**
 *
 * @author  Julio Rey
 */
public class TrucoTeam extends Team{
    
    /** Creates a new instance of TrucoTeam */
    public TrucoTeam(String name) {
        super(name);
    }
    public TrucoTeam(){
    }
    public int getNumberOfPlayer(TrucoPlayer pl){
        for (int i=0; i<playersList.size(); i++){
    		if ((Player)(playersList.get(i))==pl)
    			return i;
    	}
    	return -1;
    }
    
}
