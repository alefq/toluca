package py.edu.uca.fcyt.toluca.table.state;

/**
 * Contiene las definiciones de los eventos 
 * referentes a la transiciónd de estados
 * @author Grupo Interfaz de Juego
 */
public interface StateListener
{
	/**
     * Llamado cuando la transición de un 
     * estado a otro ha sido completada
     * @param
     */
	public void transitionCompleted();
}