package py.edu.uca.fcyt.toluca.table.animation;

/**
 * Esta interface contiene m�todos que retornan
 * sitios en donde pintar
 * @author Grupo Interfaz de Juego
 */
 
public interface Graphics2DPainter
{
	/** @return El �ndice de la imagen b�fer en la cual pintar */ 
	public int getBuffIndex();
	
	/**
     * Repinta la salida
     */
    public void repaint();
    
    /** Agrega un listener de eventos de pintado */
    public void addListener(ObjectsPainter obj);
    
    public int getOutWidth();
    public int getOutHeight();
}