package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.table.state.State;

/**
 * Contiene una se�a
 * @author Grupo Interfaz de Juego
 */
class SignState implements State
{
	private int sign;
	
	// construye un nuevo SignState
	public SignState(int sign)	
	{ 
		Util.verifParam(Sign.isSign(sign), "Par�metro 'sign' inv�lido");
		this.sign = sign; 
	}
	
	public void transition(State state, double ratio) 
	{
		sign = ((SignState) state).getSign();
	}
	
	public int getSign() { return sign; }
	
	public State getCopy()
	{
		return new SignState(sign);
	}
}
