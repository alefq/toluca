package py.edu.uca.fcyt.toluca.table.animation;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Esta interface contiene métodos que retornan
 * sitios en donde pintar
 * @author Grupo Interfaz de Juego
 */
 
public interface Graphics2DPainter
{
	/**
     * Retorna la transformación adecuada de la salida
     */
	public AffineTransform getTransform();
	
	/**
     * Retorna un {@link BufferedImage} para pintar en él
     */
	public BufferedImage getBImage();
	
	/**
     * Repinta la salida
     */
    public void repaint();
}