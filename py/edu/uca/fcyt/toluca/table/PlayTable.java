package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.table.animation.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.event.*;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.game.*;

import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.util.*;
import java.lang.*;
import java.awt.event.MouseListener;
import java.util.EventListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Maneja el panel donde se juega propiamente.
 * Encargado de animar las cartas y mostrar las caritas.
 */
class PlayTable extends JPanel 
implements ComponentListener, MouseListener, Graphics2DPainter
{
	private boolean paintIt = false;

	private BufferedImage biBuff;	// Búfer de la mesa a pintar
	private Graphics2D grBuff;		// Graphics2D del búfer

	/** dimensiones de la mesa */
	final public static int TABLE_WIDTH = 676;
	final public static int TABLE_HEIGHT = 489;

	int centerX = TABLE_WIDTH / 2;
	int centerY = TABLE_HEIGHT / 2;
	
	// desplazamiento de la salida
	public int offsetX, offsetY;

	private PTableListener ptListener;		 // list. de eventos
	private final Animator animator;		// animador de cartas

	/**
	 * Construye un PlayTable con 'ptListener'
	 * como listener de eventos
	 */
	public PlayTable(PTableListener ptListener)
	{
		super(false);

		addComponentListener(this);
		addMouseListener(this);
		this.ptListener = ptListener;
		animator = new Animator(this);
		animator.start();
	}

	/** rutina de pintado */
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(biBuff, offsetX, offsetY, null);
	}

	/** Evento de cambio de tamaño del componente */
	public void componentResized(ComponentEvent e)
	{
		Rectangle bounds;
		double scale;

		// obtiene las coordenadas del área de pintado
		bounds = getBounds();

		// establece la escala y el desplazamiento de la mesa
		// con respecto al Graphic donde se la dibujará
		if (bounds.getWidth() / bounds.getHeight() < (double) TABLE_WIDTH / TABLE_HEIGHT)
		{
			scale = bounds.getWidth() / TABLE_WIDTH;
			offsetX = 0;
			offsetY = (int) (bounds.getHeight() - (TABLE_HEIGHT * scale)) / 2;
		}
		else
		{
			scale = bounds.getHeight() / TABLE_HEIGHT;
			offsetX = (int) (bounds.getWidth() - (TABLE_WIDTH * scale)) / 2;
			offsetY = 0;
		}

		// crea el objeto BufferedImage en el cual se dibujará
		// los objetos para posteriormente ser dibujados en
		// el Graphic del componente
		biBuff = new BufferedImage
		(
			(int) (TABLE_WIDTH * scale),
			(int) (TABLE_HEIGHT * scale),
			BufferedImage.TYPE_3BYTE_BGR
		);

		// obtiene el objeto Graphics2D del BufferedImage
		grBuff = biBuff.createGraphics();

		// establece el antialiasing de 'grBuff' a verdadero
		grBuff.setRenderingHint
		(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON
		);
		
		grBuff.scale(scale, scale);
		grBuff.translate(TABLE_WIDTH/2, TABLE_HEIGHT/2);
		
		animator.redraw = true;
	}
	
	// a implementar
	public void componentMoved(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
	public void componentHidden(ComponentEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	/**
	 * Captura el evento click del mouse y llama a
	 * mouseClicked de 'ptListener' con las coordenadas
	 * transformadas y el MouseEvent 'e'
	 */
	public void mouseClicked(MouseEvent e)
	{
		Point2D p;
		
		try
		{
			p = grBuff.getTransform().createInverse().transform
			(
				new Point
				(
					e.getX() - offsetX, 
					e.getY()  - offsetY
				), 
				null
			);
		}
		catch (Exception ex) {	throw new RuntimeException(ex);	}

		ptListener.mouseClicked((int) p.getX(), (int) p.getY(), e);
	}
	
	/**
     * Retorna el animador de objetos
     */
	public Animator getAnimator()
	{
		return animator;
	}
	
	public Graphics2D getGraphics2D()
	{
		return grBuff;
	}

	public BufferedImage getBImage()
	{
		return biBuff;
	}
}
