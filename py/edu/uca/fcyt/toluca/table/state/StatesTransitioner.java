package py.edu.uca.fcyt.toluca.table.state;

import py.edu.uca.fcyt.toluca.table.*;
import java.util.*;
import java.security.*;

/**
 * Esta clase maneja a un objeto en la mesa el cual 
 * tiene sus diferentes estados en una cola
 * @author Roberto Giménez
 */
 
public class StatesTransitioner
{
	// Contiene el par (estado, duración)
	private class StateDuration
	{
		public State state;
		public long duration;
		
		public StateDuration(State state, long duration)
		{
			this.state = state;
			this.duration = duration;
		}
	}
	
	private LinkedList states;			// estados a animar
	private StateDuration nextState; 	// siguiente estado a animar
	private long lastTime = -1;			// último tiempo de avance
	protected State currState; 			// estado actual
	private long remainingTime;			// tiempo hasta quedar inactiva
	private LinkedList stateListeners;	// listeners de eventos
	
	/**
     * Construye un nuevo StatesTransitioner. El nuevo objeto
     * tiene una cola de estados vacía.
     */								
	public StatesTransitioner()
	{
		states = new LinkedList();
		stateListeners = new LinkedList();
	}		
	
	/**
     * Agrega un nuevo listener de eventos.
     * @param obj	Listener de eventos a agregar.
     */
    public void addListener(StateListener obj)
    {
    	stateListeners.add(obj);
    }
    
    /**
     * Dispara el evento 
     * {@link StateListener#transitionCompleted()}
     * de cada uno de los listeners registrados.
     */
    private void fireTransitionCompleted()
    {
    	Iterator slIter;
    	
    	slIter = stateListeners.iterator();
    	while (slIter.hasNext())
    		((StateListener) slIter.next()).transitionCompleted();
    }
	
	/**
     * Realiza el avance entre de estados.
     * Para avanzar el estado actual, llama al evento 
     * {@link State#transition(State, double)}
     * del mismo pasándole como parámetro el siguiente estado y
     * la cantidad de cambio (0 a 1). A la vez, consume el tiempo 
     * del estado actual en una cantidad igual al tiempo 
     * transcurrido entre la última vez que se llamó a la función
     * y el momento en el cual se la llama. Si se la llama por
     * primera vez, el último tiempo es considerado es tiempo
     * en el cual se agregó el primer elemento de la cola.
     * @return 	Verdadero si pudo avanzar. Falso si no hay 
     * más estados en la cola
     */
	synchronized public boolean advance()
	{
		long elapsedTime, actualTime;
		
		// obtiene el tiempo actual
		actualTime = System.currentTimeMillis();
		
		// si no hay último tiempo de avance, que sea el actual
		if (lastTime == -1) 
		{
			lastTime = actualTime;
			return false;
		}
		
		// si no hay estado siguiente, salir con falso
		if (nextState == null) return false;

		// obtiene el tiempo transcurrido desde el último avance
		elapsedTime = actualTime - lastTime;
		lastTime = actualTime;

		// si el tiempo es mayor al restante, establecer el 
		// estado al siguiente, quitar este de la cola
		// y actualizarlo al siguiente de la misma
		if (elapsedTime >= nextState.duration)
		{
			// elimina el estado de la cola
			states.removeFirst();
			
			// si no es una pausa, actualizar el estado actual
			if (nextState.state != null) 
				currState = nextState.state;
				
			try
			{
				nextState = (StateDuration) states.getFirst();
			} catch (NoSuchElementException ex)
			{
				nextState = null;
				remainingTime = 0;
			}
			fireTransitionCompleted();
		}
		// Si el tiempo es menor que el restante, establecer
		// el estado actual a 'elapsedTime' milisegundos después
		// y restar el tiempo avanzado
		else
		{
			float timeRatio;
			
			timeRatio = (float) elapsedTime / nextState.duration / 1;
			nextState.duration -= elapsedTime / 1;
			remainingTime -= elapsedTime;
			if (remainingTime < 0) remainingTime = 0;

			if (nextState.state != null) 
				currState.transition(nextState.state, timeRatio);
		}
		return true;
	}
	
	/**
     * Agrega un estado a la cola. Si la cola carece de estados,
     * el actual es el agregado, y su duración se omite.
     * @param state		Estado a agregar. Si es nulo, se agrega una pausa.
     * @param duration	Duración del estado.
     */
	synchronized public void pushState(State state, long duration)
	{
		StateDuration stDur;
		
		// verificaciones
		Util.verifParam(state != null, "Parámetro 'state' nulo");
		Util.verifParam
		(
			duration >= 0, "Parámetro duration inválido: " + duration
		);
		
		// actualiza el tiempo restante para quedar inactiva
		remainingTime += duration;

		// si el estado actual es nulo, que sea éste		
    	if (currState == null)
    	{
    		currState = state;
    	}
    	// si no, agregarlo a la cola de estados
    	else
		{
			// crea el nuevo estado y lo agrega a la cola
			stDur = new StateDuration(state, duration);
			states.add(stDur);
			
			// si no había siguiente estado, el agregado es el 
			// siguiente y el tiempo de último avance es el actual
			if (nextState == null)
			{
				lastTime = System.currentTimeMillis();
				nextState = stDur;
			}
		}
	}
	

	/**
     * Agrega una pausa a la cola de estados.
     * @param duration	duración de la pausa.
     */
    synchronized public void pushPause(long duration)
    {
    	// si la duración es 0, no se hace nada
    	if (duration <= 0) return;
    	
    	try 
    	{
    		pushState(getLastState(), duration);
    	}
    	catch (InvalidParameterException ex1) 
    	{
    		try
    		{
    			pushState(getCurrState(), duration);
    		}
    		catch (InvalidParameterException ex2)
    		{
    			throw new IllegalStateException
    			(
    				"No hay un estado actual"
    			);
    		}
    	}
    }

    public boolean isEnabled()
    {
    	return currState != null;
    }

    /**
     * Retorna el tiempo que falta para que quede inactiva
     */
    synchronized public long getRemainingTime() { return remainingTime; }
    
    /**
     * Retorna una copia del estado actual, si lo hay, de
     * lo contrario nulo.
     */
    synchronized public State getCurrState()
    {
    	try
    	{
    		return currState.getCopy();
    	}
    	catch (NullPointerException ex)
    	{
    		return null;
    	}
    }
    
    /**
     * Retorna el último estado de la cola
     */
    synchronized public State getLastState()
    {
    	StateDuration stDur;
    	try
    	{
    		stDur = (StateDuration) states.getLast();
    		return stDur.state.getCopy();
    	}
    	catch (NoSuchElementException ex) {	return null; }
    	catch (NullPointerException ex)
    	{
    		throw new IllegalStateException
    		(
    			"Hay un estado en la cola que es nulo"
    		);
    	}
    }
     
}