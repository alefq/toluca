package py.edu.uca.fcyt.toluca.table.animation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Vector;

import py.edu.uca.fcyt.game.Card;
import py.edu.uca.fcyt.toluca.table.Util;

/**
 * Clase que anima los objetos
 */

public class Animator implements Runnable, ObjectsPainter
{
	private Graphics2DPainter gPainter;
	protected Vector anims; // c. en la mesa
	private Vector faces; // caritas
	public boolean drawComplete;
	private static int delay = 20;
//	private static final long delayDelta = getDelta();
	private static int animCount = 0;
	private boolean alive = true;
	private static boolean counterAlive;
	private BufferedImage[] biOut;
	private AffineTransform afTrans;
	
	private static void startCalc()
	{
		Thread ret;
		
		new Thread()
		{
			public void run()
			{
				counterAlive = true;
				
				while(counterAlive)
				{
					calcDelay();
					Util.wait(this, 1000);
				}
			}
		}.start();
	}
	
	/**
     * Construye un nuevo Animator con 'gPainter' asociado
     */
	public Animator(Graphics2DPainter gPainter)
	{
		this.gPainter = gPainter;
		anims = new Vector(0, 3);
		faces = new Vector(0, 2);
//		delay += delayDelta;
		animCount ++;
//		if (animCount == 1) startCalc();
//		calcDelay();
	}
	
	/**
     * Comienza el ciclo de animación
     */
	public void run()
	{
		int i, j, living;		// var. aux. y var. de cartas vivas
		Card c;					// carta
		Animable tCard;		// un Animable
		boolean draw[];			// dibujar o cada carta
		boolean advance[], enabled[];
		Animable anim;
		Rectangle txtClearRegion;
		int bIndex;
		
		do 
		{if (biOut != null) 
		{
			bIndex = gPainter.getBuffIndex();
			
			drawComplete = false;
			drawBack(biOut[bIndex]);
			
			// recorre todos los elementos del vector 
			// de anims y las avanza
			for (i = 0; i < anims.size(); i++) 
			{
				((Animable) anims.get(i)).advance();
			}
			
			// recorre todos los elementos del vector 
			// de anims y las pinta
			for (i = 0; i < anims.size(); i++) 
			{
				((Animable) anims.get(i)).paint(bIndex);
			}

			drawComplete = true;
			
			// actualiza la salida
			gPainter.repaint();

			Util.wait(this, delay);
			}else Util.wait(this, delay);
		} while(alive); // seguir mientras haya vivas
	}
    

	/** 
	 * Pinta el fondo
	 */
	protected void drawBack(BufferedImage img)
	{
		Graphics2D gr2D;
		
		// obtiene el BufferedImage y el Graphics2D búfer
		gr2D = img.createGraphics();
		
		// establece el color de la mesa y la pinta
		gr2D.setColor(Color.GREEN.darker());
		gr2D.fillRect
		(
			(int) 0,
			(int) 0,
			(int) img.getWidth(),
			(int) img.getHeight()
		);
		
		gr2D.dispose();
	}


	/** 
	 * Agrega un nuevo objeto animable 
	 */
	synchronized public Animable addAnim(Animable a)
	{
		anims.add(a);
		if (biOut != null) a.setOut(biOut, afTrans);
		return a;
	}

	/* 
	 * Remueve un objeto animable y lo retorna 
	 */
	synchronized public boolean removeAnim(Animable a)
	{
		return anims.remove(a);
	}

	/* 
	 * Devuelve la cantidad de objetos animables
	 */
	synchronized public int getAnimCount()
	{
		return anims.size();
	}

	/** 
	 * Establece la posición del objeto animable <b>a</b>
	 * al primer lugar por encima de todos los demás
	 */
	synchronized public void toTop(Animable a)
	{
		anims.remove(a);
		anims.add(a);
	}

	/* 
	 * Retorna un objeto animable 
	 * @param index		índice del objeto
	 */
	synchronized public Animable getAnim(int index) 
	{
		return (Animable) anims.get(index);
	}
	
	private static int getDelta()
	{
    	long start;
    	long sum = 0;
    	
    	start = System.currentTimeMillis();
    	while(start + 10 > System.currentTimeMillis())
    		sum ++;
    		
    	return (int) (1000000 /  (sum + 1));
	}
	
	private static void calcDelay()
	{
		int delta = getDelta();
		delay = (int) (delay * .6 + animCount * delta * .4);
		delay = 20;
		// TODO ¡Frames per second!
//		System.out.println("Delay: " + delay);
	}
	
	/**
     * Finaliza la ejecución de este animador
     */
    synchronized public void stopAnimator()
    {
//		delay -= delayDelta;
    	alive = false;
    	animCount --;
    	if (animCount == 0) counterAlive = false;
    }
    
//    protected void finalize()
//    {
//    	System.out.println(this + " finalized");
//    }
	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.table.animation.ObjectsPainter#setOut(java.awt.image.BufferedImage, java.awt.geom.AffineTransform)
	 */
	public void setOut(BufferedImage[] biOut, AffineTransform afTrans)
	{
		this.afTrans = afTrans;
		this.biOut = biOut;
		
		for (int i = 0; i < anims.size(); i++)
			((Animable) anims.get(i)).setOut(biOut, afTrans);
	}

}
