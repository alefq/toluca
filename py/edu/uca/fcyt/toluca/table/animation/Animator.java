package py.edu.uca.fcyt.toluca.table.animation;

import py.edu.uca.fcyt.toluca.table.*;
import py.edu.uca.fcyt.game.*;
import java.util.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.image .*;

/**
 * Clase que anima las cartas y las caritas
 */

public class Animator implements Runnable
{
	private Graphics2DPainter gPainter;
	protected Vector anims; // c. en la mesa
	private Vector faces; // caritas
	public boolean drawComplete;
	private static int delay = 0;
//	private static final long delayDelta = getDelta();
	private static int animCount = 0;
	private boolean alive = true;
	private static boolean counterAlive;

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
		if (animCount == 1) startCalc();
		calcDelay();
	}
	
	/**
     * Comienza el ciclo de animación
     */
	public void run()
	{
		AffineTransform afTrans;// Transformación de la imagen
		int i, j, living;		// var. aux. y var. de cartas vivas
		Card c;					// carta
		Animable tCard;		// un Animable
		boolean draw[];			// dibujar o cada carta
		boolean advance[], enabled[];
		Animable anim;
		Rectangle txtClearRegion;
		BufferedImage biBuff;	// BufferedImage de la mesa
		
		do 
		{
			// obtiene un BufferedImage en el cual pintar
			// y una transformación a utilizar del pintador
			biBuff = gPainter.getBImage();
			
			if (biBuff != null)
			{
				drawComplete = false;
				drawBack(biBuff);
				
				afTrans = gPainter.getTransform();
				
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
					((Animable) anims.get(i)).paint
					(
						biBuff, afTrans
					);
				}
				drawComplete = true;
				
				gPainter.repaint();

				// actualiza la salida
				Util.wait(this, delay);
			} else Util.wait(this, delay);
		} while(alive); // seguir mientras haya vivas
//		System.out.println("Animator stopped");
	}
 
	
	/**
	 * Avanza el estado de todas las cartas y marca en 'draw' 
	 * las que necesitan ser (re)dibujadas, en 'advance'
	 * las que avanzaron, y en 'enabled' las que están 
	 * habilitadas para dibujo. También carga las posiciones 
	 * antiguas x e y en 'oldX' y 'oldY' respectivamente y las 
	 * nuevas en 'newX' y 'newY'
     */
    private void advanceAnims
    (
    	Vector anims, 
    	boolean[] draw, boolean advance[], boolean enabled[]
    )
    {
    	Animable anim;
    	int i;

		// verificaciones    	
    	Util.verifParam(anims.size() == draw.length, "El tamaño del vector 'draw' es diferente al de 'anims'");
    	Util.verifParam(anims.size() == advance.length, "El tamaño del vector 'advance' es diferente al de 'anims'");
    	
    	// inicializa el vector indiciador de (re)dibujo
    	for (i = 0; i < draw.length; i++)
    		draw[i] = false;
		
		// avanza todas las cartas guardando sus
		// posiciones anteriores
		for (i = 0; i < anims.size(); i++)
		{
			anim = (Animable) anims.get(i);
			enabled[i] = anim.isEnabled();
			
			if (enabled[i])
			{
//				oldX[i] = anim.getX();
//				oldY[i] = anim.getY();
				
				advance[i] = anim.advance();
	
//				newX[i] = anim.getX();
//				newY[i] = anim.getY();
			}
			else advance[i] = false;
		}
		
		// carga el vector 'draw' de cartas a (re)dibujarse
		for (i = 0; i < anims.size(); i++)
//			if (advance[i] && !draw[i]) markDraw
//			(
//				i, draw, enabled, oldX, oldY, newX, newY
//			);
			if (advance[i]) draw[i] = true;
    }
    
//    private void markDraw
//    (
//    	int i, boolean draw[], boolean enabled[],
//    	float oldX[], float oldY[],
//    	float newX[], float newY[]
//    )
//    {
//    	Animable tCard, afCard;
//    	boolean drawIt;
//    	double x2, y2;
//    	float minX, minY, maxX, maxY;
//    	
//    	// marca la carta
//    	draw[i] = true;
//
//		// recorre todas las cartas que aún no están marcadas
//		// pero que se pueden ver afectadas por el borrado 
//		// de la carta actual de su posición vieja. Si lo
//		// estan las marca llamando nuevamente a esta función
//		for (int j = 0; j < draw.length; j++) 
//			if (!draw[j] && enabled[i])
//			{
//				// obtiene la coordenada X (vieja) máxima y mínima
//				if (oldX[i] < oldX[j]) 
//				{
//					minX = oldX[i]; maxX = oldX[j]; 
//				}
//				else 
//				{ 
//					minX = oldX[i]; maxX = oldX[j]; 
//				}
//	
//				// obtiene la coordenada Y (vieja) máxima y mínima
//				if (oldY[i] < oldY[j]) 
//				{
//					minY = oldY[i]; maxY = oldY[j];
//				}
//				else
//				{
//					minY = oldY[i]; maxY = oldY[j]; 
//				}
//					
//	    		// si el rectángulo que inscribe al círculo que
//	    		// inscribe a la posible carta afectada se intersecta 
//	    		// con el rectángulo borrador, marcar la carta 
//	    		if 
//	    		(
//	    			maxX < minX + 2 * Animable.CARD_RADIUS	&&
//	    			maxY < minY + 2 * Animable.CARD_RADIUS	
//	    		)
//	    			markDraw
//	    			(
//	    				j, draw, enabled, oldX, oldY, newX, newY
//	    			);
//	    	}
//
//		// recorre todas las cartas que aún no están marcadas
//		// pero que se pueden ver afectadas por (re)dibujo
//		// de la carta actual de su posición nueva. Si lo
//		// estan las marca llamando nuevamente a esta función
//		for (int j = i + 1; j < draw.length; j++) 
//			if (!draw[j] && enabled[i])
//			{
//				// obtiene las distancias cuadradas x e y (nuevas) 
//				// entre la carta actual y la posible afectada
//		    	x2 = Math.pow(newX[i] - newX[j], 2);
//		    	y2 = Math.pow(newY[i] - newY[j], 2);
//	    	
//	    		// si la posible carta afectada está en el 
//	    		// radio de la carta actual, marcarla 
//	    		if (x2 + y2 <= Animable.CARD_RADIUS2 * 4)
//	    			markDraw
//	    			(
//	    				j, draw, enabled, oldX, oldY, newX, newY
//	    			);
//			}
//	}

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
    		
    	return (int) (700000 /  sum);
	}
	
	private static void calcDelay()
	{
		int delta = getDelta();
		delay = (int) (delay * .5 + animCount * delta * .5);
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
}
