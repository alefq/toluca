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
	private static int PLAYER_MASK = 1;
	private static int VALUE_MASK = 2;
	
	/**
     * Crea una nueva instancia de TableEvent para el
     * env�o de una se�a.
     * @param table		mesa asociada
     * @param player	jugador asociado
     * @param value		valor asociado
     */
	public TableEvent
	(
		Table table, Player player, int value
	)
	{
		// verificaciones ("comentarizado" para el ejemplo)
//		Util.verifParam(table != null, "Par�metro 'table' nulo");
		
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
	
	/** 
	 * Retorna una m�scara que contiene informaci�n sobre
	 * los par�metros activos de este TableEvent. Esta
	 * m�scara es posteriormente utilizada por los 
     */
    public int getParamMask()
    {
    	int p, v;
    	
    	p = player == null ? 0 : PLAYER_MASK;
    	v = value == -1 ? 0 : VALUE_MASK;
    	
    	return p | v;
    }
    
    /**
     * Retorna verdadero si la m�scara de par�metros pasada 
     * indica que el campo <code>player</code> est� especificado.
     * @param paramMask		M�scara de par�metros
     */
    public static boolean isValueSpecified(int paramMask)
    {
    	return (paramMask & VALUE_MASK) != 0;
    }
    
    /**
     * Retorna verdadero si la m�scara de par�metros pasada 
     * indica que el campo <code>value</code> est� especificado.
     * @param paramMask		M�scara de par�metros
     */
    public static boolean isPlayerSpecified(int paramMask)
    {
    	return (paramMask & PLAYER_MASK) != 0;
    }

    /**
     * Ejemplo de uso. La m�scara juega el rol de tipo de 
     * evento y las 'isXSpecified' el rol de determinadores 
     * de existencia o inexistencia de un cierto par�metro 
     * para un cierto tipo (m�scara).
     * El par�metro Table siempre est� especificado.
     */
    public static void main(String[] args)
    {
    	TableEvent[] tEvents;
    	int pMask;
    	
    	// crea 4 TableEvents con todas las 
    	// combinaciones de par�metros activos
    	tEvents = new TableEvent[]
    	{
    		new TableEvent(null, null, -1),
    		new TableEvent(null, null, 1),
    		new TableEvent(null, new Player(), -1),
    		new TableEvent(null, new Player(), 2)
    	};
    	
    	// imprime la existencia o no de los 
    	// par�metros de cada TableEvent
    	for (int i = 0; i < 4; i++)
    	{
    		// obtiene la m�sca del 'i'-�simo TableEvent
	    	pMask = tEvents[i].getParamMask();
	    	
	    	// imprime la existencia o inexistencia 
	    	// de c/u de los par�metros
	    	System.out.println("TableEvent numero " + i + ":");
	    	System.out.println("- Especifica 'player': " + TableEvent.isPlayerSpecified(pMask));
	    	System.out.println("- Especifica 'value': " + TableEvent.isValueSpecified(pMask));
	    }
    }
}