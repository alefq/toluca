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
	
	int numeroFila = 0;
	Vector jugadores;
	Vector observadores;
	
	
	public Fila(){
            observadores = new Vector();
            jugadores = new Vector();
            for(int i=0;i<6;i++){
		jugadores.add("Libre");
            }
            System.out.println("Se instancia una fila.");
	}
	
	/** Creates a new instance of Fila */
	public Fila(int numero){
            numeroFila = numero;
            observadores = new Vector();
            jugadores = new Vector();
		
            for(int i=0;i<6;i++){
                jugadores.add("Libre");
            }
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
	public void setPlayer(TrucoPlayer player, int index){
            
            if ( index>=0 && index< jugadores.size() )
		jugadores.setElementAt(player,index);
	}
	
	/*
	 * Inserta al final al player
	 */
	public void addPlayer(TrucoPlayer player){
            
            if(jugadores.size() < 6)
		jugadores.add(player);
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
                    ret = ( (TrucoPlayer)jugadores.elementAt(index)).getName();
            
            return ret;
        }
        
        
	/*
	 * Ingresa un Player como un observador de esta fila/mesa
	 */
	public void setObservador(TrucoPlayer player){
		observadores.add(player);
	}
	
	/*
	 * Devuelve true si el player esta observando en la fila/mesa
	 * false, de lo contrario;
	 */
	public boolean isObservador(TrucoPlayer player){
		return observadores.contains(player);
	}
	
	/*
	 * Retorna la posición del jugador, si este se encuentra en esa fila.
	 * De lo contrario, retorna 0.
	 */
	public int isPlayer(TrucoPlayer player){
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
	public TrucoPlayer getObservador(int index){
		//TrucoPlayer ret = new TrucoPlayer();
		TrucoPlayer ret = null;
		if( index< observadores.size())
			ret = (TrucoPlayer) observadores.elementAt(index);
		
		return ret;
	}
}