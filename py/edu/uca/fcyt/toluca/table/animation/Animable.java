package py.edu.uca.fcyt.toluca.table.animation;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.*;


/**
 * Esta interfaz representa a un objeto animable en la mesa
 * @author Grupo Interfaz de Juego
 */
public interface Animable
{
	/** 
	 * Pinta el objeto.
	 * 
	 * El pintado se realiza sobre el estado actual del objeto.
	 * @param biOut		imagen en donde se pintará el objeto
	 * @param afTrans	transformación inicial del objeto
	 * 
	 * @see #advance() advance
	 */
	public void paint(BufferedImage biOut, AffineTransform afTrans);
	
	/**
     * Borra el objeto.
     * El borrado se realiza sobre la posición anterior del objeto.
     * </p>
     */
    public void clear(Graphics2D grOut);

    /**
     * Avanza el estado actual del objeto. Antes de avanzar
     * se guarda el estado anterior del objeto, el cual
     * es utilizado por {@link #clear(Graphics2D)}. El nuevo
     * estado es utilizado por 
     * {@link #paint(BufferedImage biOut, AffineTransform afTrans)}
     */
	public boolean advance();
	
	/**
     * @return verdadero si el objeto puede mostrarse
     */	
    public boolean isEnabled();

}