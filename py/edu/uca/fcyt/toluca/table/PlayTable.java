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
import java.awt.image.ImageObserver;
import java.awt.Image;

/**
 * Maneja el panel donde se juega propiamente.
 * Encargado de animar las cartas y mostrar las caritas.
 */
class PlayTable extends JPanel 
implements ComponentListener, MouseListener, Graphics2DPainter, ImageObserver
{
	private boolean paintIt = false;

	private BufferedImage[] biBuff;		// Búfer de la mesa a pintar
	private AffineTransform afTrans;	// Transformación a aplicar

	private Image img;
	private Graphics2D grImg;
	
	private int currBuff = 0;

	/** dimensiones de la mesa */
	final public static int TABLE_WIDTH = 676;
	final public static int TABLE_HEIGHT = 489;

	int centerX = TABLE_WIDTH / 2;
	int centerY = TABLE_HEIGHT / 2;
	
	// desplazamiento de la salida
	public int offsetX, offsetY;

	private PTableListener ptListener;		 // list. de eventos
	private final Animator animator;		// animador de cartas
	
	Toolkit toolkit;
	Object waitFor;

	/**
	 * Construye un PlayTable con 'ptListener'
	 * como listener de eventos
	 */
	public PlayTable(PTableListener ptListener)
	{
		setDoubleBuffered(false);
		addComponentListener(this);
		addMouseListener(this);
		this.ptListener = ptListener;
		toolkit = getToolkit();
		biBuff = new BufferedImage[2];
		afTrans = new AffineTransform();
		animator = new Animator(this);
	}

	/** rutina de pintado */
	public void paint(Graphics g)
	{
		synchronized(animator)
		{
			super.paint(g);
			if (animator.drawComplete) switchImages();
			g.drawImage(biBuff[currBuff], offsetX, offsetY, this);
		}
	}

	/** Evento de cambio de tamaño del componente */
	public void componentResized(ComponentEvent e)
	{
		Rectangle bounds;
		double scale;
		
		img = this.createImage(1000, 1000);
		grImg = (Graphics2D) img.getGraphics();
		
		grImg.fillOval(0, 0, 100, 100);
		
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

		// crea los BufferedImages
		for (int i = 0; i < biBuff.length; i++)
			biBuff[i] = new BufferedImage
			(
				(int) (TABLE_WIDTH * scale), 
				(int) (TABLE_HEIGHT * scale),
				BufferedImage.TYPE_3BYTE_BGR
			);
			
		// crea la transformación y la carga
		afTrans = new AffineTransform();
		afTrans.scale(scale, scale);
		afTrans.translate(TABLE_WIDTH/2, TABLE_HEIGHT/2);
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
			p = afTrans.createInverse().transform
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

	public BufferedImage getBImage()
	{
		return biBuff[(currBuff + 1) % biBuff.length];
	}
	
	public AffineTransform getTransform()
	{
		return afTrans;
	}

	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) 
	{
		System.out.println("-------------Image Update--------------");
//		if ((infoflags & ImageObserver.ALLBITS) != 0)
//			switchImages();
		
		return false;
	}
	
	private void switchImages()
	{
		if (animator.drawComplete)
			currBuff = (currBuff + 1) % biBuff.length;
	}
}
