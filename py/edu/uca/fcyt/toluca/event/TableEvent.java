package py.edu.uca.fcyt.toluca.event;

import py.edu.uca.fcyt.game.*;
import py.edu.uca.fcyt.toluca.table.*;
import java.security.*;

/**
 * Contiene los datos necesarios para manejar un evento de mesa
 * @author Grupo Interfaz de Juego
 */
public class TableEvent
{
	private Table table;
	private Player player;
	private int value;
	
	/**
     * Crea una nueva instancia de TableEvent para el
     * envío de una seña.
     * @param table		mesa asociada
     * @param player	jugador asociado
     * @param value		valor asociado
     */
	public TableEvent
	(
		Table table, Player player, int value
	)
	{
		this.table = table;
		this.value = value;
		this.player = player;
	}
	
	/** Retorna el jugador asociado */
	public Player getPlayer() { return player; }
	
	/** Retorna el valor asociado */
	public int getValue() { return value; }

	/** Retorna la mesa asociada */	
	public Table getTable() { return table; }
}