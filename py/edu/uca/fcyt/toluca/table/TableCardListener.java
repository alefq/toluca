package py.edu.uca.fcyt.toluca.table;

/**
 * Contiene las definiciones de los eventos referentes a 
 * la transici�n de estados de un {@link TableCard}
 * @author Grupo Interfaz de Juego
 */
interface TableCardListener
{
	/**
     * Llamado cuando la transici�n de un Table card 
     * de estado a otro ha sido completada
     * @param tCard		TableCard que complet� su transici�n.
     */
	public void transitionCompleted(TableCard tCard);
}