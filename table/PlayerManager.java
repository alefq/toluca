package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.game.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/** 
 * Maneja la posicion de los jugadores en la mesa
 */
class PlayerManager
{

	protected Player actualPlayer;
	protected Vector players;/*Vector de referencia de 
										jugadores cuyo subindice
										corresponde al numero de
										su silla*/
	
	
	/** Crea un PlayerManager */
	public PlayerManager(int playerCount)
	{
		players= new Vector(playerCount);//vector de referencias 
													   //de los players		
		players.setSize(playerCount);
	}
	
	
	
	/** 
	 * Sienta al jugador en un numero 
	 * de silla correspondiente 
	 */
	public void sitPlayer(Player p,int chair)
	{
		if (players.contains(p))
			throw new TableException("Jugador ya está sentado");

	 	if (getPlayer(chair) != null)
 			throw new TableException("Silla ocupada");

		players.set(chair, p);
	}
	
	public void setActualPlayer(Player p)
	{
		Util.verif(p != null, "Jugador nulo");
		Util.verif(players.contains(p), "Jugador " + p.getName() + " no agregado");
		
		actualPlayer = p;
	}
	
	public int getActualChair()
	{
		if (actualPlayer == null) return 0;
		else return getChair(actualPlayer);
	}
		
	
	
	/* Antes de que empiece el juego controla si estan todos 
	 * los jugadores sentados.
	 * Puede pasar que 
	 * - falten jugadores pero se esta parejo y igual se inicia el 
	 * juego(devuelve true) 
	 * - falten jugadores y no este parejo(devuelve falso) */
	
	public boolean controlStartGame()
	{
		int[] teamCount; // cantidad de jugadores de cada team
		
		teamCount = new int[2]; 
		
		for (int i = 0; i < getPlayerCount(); i+=2)
		{
			teamCount[0] += (getPlayer(i) != null) ? 1 : 0;
			teamCount[1] += (getPlayer(i+1) != null) ? 1 : 0;
		}
		
		//si los contadores son iguales se empieza el juego
		if (teamCount[0] == teamCount[1])
		{
			Iterator pIter;
			
			pIter = players.listIterator();
			
			while (pIter.hasNext())
				if (pIter.next() == null) pIter.remove();
				
			return true;
		} 
		
		return false;
	}
	
	/* Te devuelve la referencia de un player de acuerdo
	 * al lugar donde esta sentado, si no hay nadie sentado
	 *alli te devuelve null */
	 
	public Player getPlayer(int chair)
	{
		return (Player) players.get(chair);
	}
	
	public int getChair(Player player)
	{
		return players.indexOf(player);
	}
	
	
	/* Devuelve la posicion relativa de un jugador
	 * en relacion al jugador que esta enfrente a la pantalla.
	 * Se le manda el lugar de la silla en donde eligió sentarse
	 * y de acuerdo a algunos calculos se le coloca en la posicion
	 * correspondiente para que el jugador actual lo vea
	 */
	public int getPos(int chair)
	{	return (getPlayerCount() + chair - getActualChair()) 
				% getPlayerCount();
	}
	
	public int getPos(Player player)
	{
		return getPos(getChair(player));
	}

	public int getChair(int pos)
	{
		return (getActualChair() + pos) % getPlayerCount();
	}
	
	public int getPlayerCount() { return players.size(); }
}