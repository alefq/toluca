/*
 * Fila.java
 *
 * Created on 3 de junio de 2003, 10:47 AM
 */

package py.edu.uca.fcyt.toluca;


import java.util.Vector;

import py.edu.uca.fcyt.toluca.game.TrucoPlayer;

/**
 *
 * @author  Interfaz de Inicio
 */
public class Fila
{
	boolean gameStarted;  // indica si el juego ya empezo en la mesa
	int numeroFila = 0;
	Vector jugadores;
	Vector observadores;
	
	
	public Fila(){
            observadores = new Vector();
            jugadores = new Vector();
            gameStarted = false;
            for(int i=0;i<6;i++){
				jugadores.add("Libre");
            }
           // System.out.println("Se instancia una fila.");
	}
	
	/** Crea una nueva instancia de Fila */
	public Fila(int numero){
            numeroFila = numero;
            gameStarted = false;
            observadores = new Vector();
            jugadores = new Vector();
		
            for(int i=0;i<6;i++){
                jugadores.add("Libre");
            }
	}
	
        public void setGameStatus(boolean gameStatus){
            gameStarted = gameStatus;
        }
        
        /*
         * Retorna "true" si el juego ya comenzo en la mesa,
         * "false" de lo contrario
         */
        public boolean getGameStatus(){
            return gameStarted;
        }
	/*
	 * Devuelve el numero de esta fila
	 */
	public int getFilaNumber(){
		return numeroFila;
	}
	
	/*
	 * En la posicion index de la fila se inserta el Player
	 */
	public void setPlayer(Object player, int index){
            
    	if ( index>=0 && index< jugadores.size() ){
    		//System.out.println("agregoPplayer en la pos="+index);
			jugadores.setElementAt(player,index);}
	}
	public void impJugadores()
	{
		System.out.println("Contenido de jugadores");
		for(int i=0;i<6;i++)
		{
			System.out.println(jugadores.get(i));
		}
	}
	/*
	 * Inserta al final al player
	 */
	public void addPlayer(String player){
            
            if(jugadores.size() < 6)
		jugadores.add(player);
	}
	
        /*
	 * Elimina al final al player
	 */
	public void removePlayer(String player){
            
            int index = 0;
            if(jugadores.size() > 0){
                index = jugadores.indexOf(player);
                jugadores.setElementAt("Libre", index);

            }
	}
        
	/*
	 * Retorna el Player que se encuentra en la posicion index de la fila
	 */
	public TrucoPlayer getPlayer(int index){
            
            TrucoPlayer ret = null;
            if( index>=0 && index<jugadores.size() )
                if( jugadores.elementAt(index) != "Libre")
                    ret = (TrucoPlayer) jugadores.elementAt(index);
		
            return ret;
	}
	
        /*
         * Retorna el nombre del player que esta sentado en la 
         *  posicion "index", si no hay nadie, retorna "Libre"
         * Metodo nuevo, para evitar los "null"
         */
        public String getPlayerName(int index){
        
            String ret = "Libre";
            if(index>=0 && index< jugadores.size() )
                if( jugadores.elementAt(index) != "Libre")
                    ret = jugadores.elementAt(index).toString();
            
            return ret;
        }
        
        
	/*
	 * Ingresa un Player como un observador de esta fila/mesa
	 */
	public void setObservador(Object player){
            if( !observadores.contains(player))
		observadores.add(player);
	}
	
	/*
	 * Devuelve true si el player esta observando en la fila/mesa
	 * false, de lo contrario;
	 */
	public boolean isObservador(String player){
		return observadores.contains(player);
	}
	
	/*
	 * Retorna la posici�n del jugador, si este se encuentra en esa fila.
	 * De lo contrario, retorna 0.
	 */
	public int isPlayer(String player){
		int ret = 0;
		if( jugadores.contains(player) )
			ret = jugadores.indexOf(player) + 1;
		
		return ret;
	}
	
	public int cuantosObservadores(){
		return observadores.size();
	}
	
	/*
	 * Devuelve el observador que se encuentra en la posicion index del
	 * vector de observadores en esta fila/mesa.
	 */
	public String getObservador(int index){
		//TrucoPlayer ret = new TrucoPlayer();
		String ret = "";
		if( index< observadores.size())
			ret = observadores.elementAt(index).toString();
		
		return ret;
	}
}