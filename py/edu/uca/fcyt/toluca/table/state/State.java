package py.edu.uca.fcyt.toluca.table.state;

/**
 * Representa a un estado que puede transicionar
 * @author Grupo Interfaz de Juego
 */

public interface State
{
	/**
     * Transiciona del estado actual a <b>state</b>
     * @param state		estado final de la transici�n
     * @param ration	si es 0, no hay transici�n, si
     *					es 1, la transici�n es completa
     */
    public void transition(State state, double ratio);

	/**
     * Retorna una copia de este estado
     */    
    public State getCopy();
}