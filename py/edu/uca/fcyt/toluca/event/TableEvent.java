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
		// verificaciones ("comentarizado" para el ejemplo)
//		Util.verifParam(table != null, "Parámetro 'table' nulo");
		
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
	 * Retorna una máscara que contiene información sobre
	 * los parámetros activos de este TableEvent. Esta
	 * máscara es posteriormente utilizada por los 
     */
    public int getParamMask()
    {
    	int p, v;
    	
    	p = player == null ? 0 : PLAYER_MASK;
    	v = value == -1 ? 0 : VALUE_MASK;
    	
    	return p | v;
    }
    
    /**
     * Retorna verdadero si la máscara de parámetros pasada 
     * indica que el campo <code>player</code> está especificado.
     * @param paramMask		Máscara de parámetros
     */
    public static boolean isValueSpecified(int paramMask)
    {
    	return (paramMask & VALUE_MASK) != 0;
    }
    
    /**
     * Retorna verdadero si la máscara de parámetros pasada 
     * indica que el campo <code>value</code> está especificado.
     * @param paramMask		Máscara de parámetros
     */
    public static boolean isPlayerSpecified(int paramMask)
    {
    	return (paramMask & PLAYER_MASK) != 0;
    }

    /**
     * Ejemplo de uso. La máscara juega el rol de tipo de 
     * evento y las 'isXSpecified' el rol de determinadores 
     * de existencia o inexistencia de un cierto parámetro 
     * para un cierto tipo (máscara).
     * El parámetro Table siempre está especificado.
     */
    public static void main(String[] args)
    {
    	TableEvent[] tEvents;
    	int pMask;
    	
    	// crea 4 TableEvents con todas las 
    	// combinaciones de parámetros activos
    	tEvents = new TableEvent[]
    	{
    		new TableEvent(null, null, -1),
    		new TableEvent(null, null, 1),
    		new TableEvent(null, new Player(), -1),
    		new TableEvent(null, new Player(), 2)
    	};
    	
    	// imprime la existencia o no de los 
    	// parámetros de cada TableEvent
    	for (int i = 0; i < 4; i++)
    	{
    		// obtiene la másca del 'i'-ésimo TableEvent
	    	pMask = tEvents[i].getParamMask();
	    	
	    	// imprime la existencia o inexistencia 
	    	// de c/u de los parámetros
	    	System.out.println("TableEvent numero " + i + ":");
	    	System.out.println("- Especifica 'player': " + TableEvent.isPlayerSpecified(pMask));
	    	System.out.println("- Especifica 'value': " + TableEvent.isValueSpecified(pMask));
	    }
    }
}