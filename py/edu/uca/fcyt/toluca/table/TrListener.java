package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.toluca.event.*;
import py.edu.uca.fcyt.game.*;

import java.util.*;

/**
 * Escucha los eventos del juego
 */
class TrListener implements TrucoListener
{
	protected Table table;				 // objeto Table
	protected PlayTable pTable;			 // PlayTable de 'table'
	protected LinkedList trucoListeners; // listeners de eventos
	protected Player aPlayer;		 // player asociado

	/** Construye un TrucoListenerManger con Table 'table' */
	public TrListener(Table table, Player aPlayer)
	{
		this.table = table;
		pTable = table.getPlayTable();
		trucoListeners = new LinkedList();
		this.aPlayer = aPlayer;
	}

	/** Registra un listener de eventos TrucoListener */
	public void addListener(TrucoListener t)
	{
		Util.verif(t != null, "TrucoListener nulo");
		trucoListeners.add(t);
	}

	/** Invocado cuando se inicia el juego de truco */
	public void gameStarted(TrucoEvent event)
	{
		System.out.println("Game started for player " + table.getOwner().getName());
    }


	public void play(TrucoEvent event) {}

	public void turn(TrucoEvent event)
	{
            // TODO: Add your code here
            // Si el player que esta en el TrucoEvent = Player del cual es el Table
            // entonces, permitir hacer la jugada correspondiente
            // Tal vez haya que mostrar un conjunto diferente de botones
            // Si no, poner algun indicador que el otro esta jugando
            // Los acentos han sido voluntariamente omitidos.

	}

	public void endOfHand(TrucoEvent event)
	{
		// TODO: Add your code here
	}

	public void cardsDeal(TrucoEvent event)
	{
	}

	public void handStarted(TrucoEvent event)
	{
		TrucoPlayer tPlayer;
		int dealPos;
		
		System.out.println("New hand started for player " + table.getOwner().getName());
		
		tPlayer = (TrucoPlayer) event.getTrucoPlayer();
		
		dealPos = table.pManager.getPos(tPlayer);
		
		table.cManager.putCardsInDealer(dealPos);
		table.cManager.deal(dealPos, false);
		table.cManager.take();
	}


	public void endOfGame(TrucoEvent event)
	{
		// TODO: Add your code here
	}

	public Player getAssociatedPlayer()
	{
		return aPlayer;
	}
}