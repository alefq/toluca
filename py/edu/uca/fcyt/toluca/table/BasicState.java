package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.table.state.State;

/**
 * Aloja el estado de un objeto general
 * @author Grupo Interfaz de Juego
 */
abstract class BasicState implements State
{
	public int x, y;		// posición
	public double angle;		// ángulo de rotación
	public double scale = 1;	// escala  

	/**
     * Construye un nuevo BasicState con los parámetros pasados
     */	
	public BasicState(int x, int y, double angle, double scale)
	{
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.scale = scale;
	}
	
	/**
     * Construye un nuevo State a partir de otro 
     */
    public BasicState(BasicState state)
    {
		this.x = state.x;
		this.y = state.y;
		this.angle = state.angle;
		this.scale = state.scale;
    }
    
    public void transition(State state, double ratio)
    {
    	BasicState tcState;
    	
    	tcState = (BasicState) state;
    	
		x = (int) (x * (1 - ratio) + tcState.x * ratio);
		y = (int) (y * (1 - ratio) + tcState.y * ratio);
		angle = angle * (1 - ratio) + tcState.angle * ratio;
		scale = scale * (1 - ratio) + tcState.scale * ratio;
    }
} 
