/*
 * Created on 02/09/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package py.edu.uca.fcyt.toluca.table.animation;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
 

/**
 * @author Roberto Gim�nez
 *
 * Objetos encargados del pintado de otros objetos
 * implementan esta interfaz para recibir eventos
 * relacionados con el cambio de la salida o de la 
 * transformaci�n del printado. 
 */
public interface ObjectsPainter
{
	/** Evento invocado cuando cambia la salida <code>biOut</code>
	 * o la transformaci�n <code>afTrans</code>
	 * @param biOut		Vector de imagenes en donde se pintan los objetos.
	 * @param afTrans	Transformaci�n del pintado.
	 */
	public void setOut(BufferedImage[] biOut, AffineTransform afTrans);
}
