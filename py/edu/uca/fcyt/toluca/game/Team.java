/*
 * Team.java
 *
 * Created on 3 de marzo de 2003, 10:45 PM
 */
package py.edu.uca.fcyt.toluca.game;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.game.*;

import java.util.*;

/**
 *
 * @authors  Julio Rey || Christian Benitez
 */
public class Team {
    
    /** Creates a new instance of Team */
    protected int points=0;
    protected int numberOfPlayers=0;
    protected String name;
    protected LinkedList playersList = new LinkedList();
    public Team() { //constructor
        numberOfPlayers = 0;
    }
    /*definir un nombre al equipo*/
    public Team(String name){
        this.name = name; 
    }
    /*insertar players*/
    public void addPlayer(TrucoPlayer pl){
        numberOfPlayers++;
        playersList.add(pl);
    }
    /*obtener el player numero x*/
    public TrucoPlayer getPlayerNumber (int numberOfPlayer){
        System.out.println(numberOfPlayers);
        return ((TrucoPlayer)playersList.get(numberOfPlayer));
    }
    /*retorna la cantidad de jugadores que tiene el equipo*/
    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }
    /*puntos del equipo*/
    public int getPoints(){
        return points;
    }
    public boolean isPlayerTeam(TrucoPlayer pl){
    	for (int i=0; i<playersList.size(); i++){
    		if ((Player)(playersList.get(i))==pl)
    			return true;
    	}
    	return false;
    }
    public String getName(){
    	return name;
    }
    public void setPoints(int pts){
        points = pts;
    }
}
