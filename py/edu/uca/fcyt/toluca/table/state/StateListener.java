package py.edu.uca.fcyt.toluca.table.state;

/**
 * Contiene las definiciones de los eventos 
 * referentes a la transici�nd de estados
 * @author Grupo Interfaz de Juego
 */
public interface StateListener
{
	/**
     * Llamado cuando la transici�n de un 
     * estado a otro ha sido completada
     * @param
     */
	public void transitionCompleted();
}