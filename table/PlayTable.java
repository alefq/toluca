package py.edu.uca.fcyt.toluca.table;

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

/**
 * Maneja el panel donde se juega propiamente.
 * Encargado de animar las cartas y mostrar las caritas.
 */
class PlayTable extends JPanel implements ComponentListener, MouseListener
{
	private boolean paintIt = false;

	private BufferedImage biOut;	// BufferedImage de la mesa
	private Graphics2D grOut;		// Graphics2D de la mesa

	/** dimensiones de la mesa */
	final public static float TABLE_WIDTH = 676;
	final public static float TABLE_HEIGHT = 489;

	// centro de la mesa
	private float centerX = TABLE_WIDTH / 2;
	private float centerY = TABLE_HEIGHT / 2;

	// escala y desplazamiento de la mesa al gráfico en sí
	private float scale;
	private int offsetX, offsetY;

	private Vector cards = new Vector(0, 3); // c. en la mesa
	private Vector faces = new Vector(0, 2); // caritas
	private PTableListener ptListener;		 // list. de eventos

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
	}

	/** rutina de pintado */
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(biOut, offsetX, offsetY, null);
	}


	// muestra la mesa (borra la salida a verde)
	private void drawTable(Graphics2D out)
	{
		// establece el color de la mesa y la pinta
		out.setColor(Color.GREEN.darker());
		out.fillRect
		(
			0,0,
			(int) (TABLE_WIDTH * scale),
			(int) (TABLE_HEIGHT * scale)
		);
	}

	/** Agrega un carita a la mesa */
	public void addFace(Face f) { faces.add(f); }

	/** Remueve la 'index'-ésima carita de la mesa */
	public void removeFace(int index)
	{
		faces.remove(index);
	}
	
	/** Remueve todas las caritas de la mesa */
	public void removeFaces()
	{
		faces.clear();
	}

	/** Retorna la cantidad de caritas en la mesa */
	public int getFaceCount() { return faces.size(); }

	/** Retorna la 'index'-ésima carita */
	public Face getFace(int index)
	{
		return (Face) faces.get(index);
	}

	// dibuja 'bfIn' en 'bfOut' en la posición
	// (x, y) con un ángulo de rotación 'rotAngle'
	private void drawImage
	(
		BufferedImage bfIn, BufferedImage bfOut,
		float x, float y, float rotAngle, float scale,
		AffineTransform afTrans
	)
	{
		AffineTransformOp afTransOp;	// Dibujador de la imagen
		int centX, centY;

		// obtiene el centro de 'bfIn'
		centX = bfIn.getWidth() / 2;
		centY =	bfIn.getHeight() / 2;

		// limpia la transformación
		afTrans.setToIdentity();

		// escala, translada y rota la imagen
		afTrans.scale(this.scale, this.scale);
		afTrans.translate
		(
			(int) (x + centerX),
			(int) (y + centerY)
		);
		afTrans.scale(scale, scale);
		afTrans.rotate(rotAngle);
		afTrans.translate
		(
			(int) - centX,
			(int) - centY
		);

		// dibuja la imagen 'bfIn' en 'bfOut
		new AffineTransformOp
		(
			afTrans, AffineTransformOp.TYPE_BILINEAR
		).filter(bfIn, bfOut);
	}

	/** Evento de cambio de tamaño del componente */
	public void componentResized(ComponentEvent e)
	{
		Rectangle bounds;

		// obtiene las coordenadas del área de pintado
		bounds = getBounds();

		// establece la escala y el desplazamiento de la mesa
		// con respecto al Graphic donde se la dibujará
		if (bounds.getWidth() / bounds.getHeight() < TABLE_WIDTH / TABLE_HEIGHT)
		{
			scale = (float) bounds.getWidth() / (float) TABLE_WIDTH;
			offsetX = 0;
			offsetY = (int) (bounds.getHeight() - (TABLE_HEIGHT * scale)) / 2;
		}
		else
		{
			scale = (float) bounds.getHeight() / (float) TABLE_HEIGHT;
			offsetX = (int) (bounds.getWidth() - (TABLE_WIDTH * scale)) / 2;
			offsetY = 0;
		}

		// crea el objeto BufferedImage en el cual se dibujará
		// los objetos para posteriormente ser dibujados en
		// el Graphic del componente
		biOut = new BufferedImage
		(
			(int) (TABLE_WIDTH * scale),
			(int) (TABLE_HEIGHT * scale),
			BufferedImage.TYPE_3BYTE_BGR
		);

		// obtiene el objeto Graphics2D del BufferedImage
		grOut = biOut.createGraphics();

		// establece el antialiasing de 'grOut' a verdadero
		grOut.setRenderingHint
		(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON
		);
		
		drawObjects();
	}

	// transforma una coordenada lógica X a una física
	private float transformX(float x)
	{
		return ((x + centerX) * scale);
	}

	// transforma una coordenada lógica Y a una física
	private float transformY(float y)
	{
		return ((y + centerY) * scale);
	}

	// transforma un tamaño lógico a uno físico
	private float transformSize(float size)
	{
		return size * scale;
	}

	/** Agrega una carta a la mesa y retorna su índice */
	public TableCard addCard(TableCard tc)
	{
		cards.add(tc);
		return tc;
	}

	/** Remueve una carta de la mesa y la retorna */
	public boolean removeCard(TableCard tc)
	{
		return cards.remove(tc);
	}

	/** Devuelve la cantidad de cartas agregadas */
	public int getCardCount()
	{
		return cards.size();
	}

	/** Establece la posición de la carta al principio */
	public void toTop(TableCard tc)
	{
		cards.remove(tc);
		cards.add(tc);
	}

	/** Anima las cartas */
	public void animate()
	{
		AffineTransform afTrans;// Transformación de la imagen
		int i, j, living;		// var. aux. y var. de cartas vivas
		long tIni, tAct;		// tiempo inicial y tiempo actual
		Card c;					// carta
		Enumeration vecCards;	// elementos del vector de cartas
		TableCard tCard;		// un TableCard
		BufferedImage back;		// fondo de las cartas en movimiento
		Graphics2D grBack;		// Graphic2D de 'back'
		boolean iddle[];		// estado iddle de varias cartas
		Graphics g; 			// Graphic del componente
		ObjectState cState[];   // estado de varias cartas

		// obtiene el Graphic del componente
		g = this.getGraphics();

		// crea el AffineTransform que utilizará 'drawImage'
		afTrans = new AffineTransform();

		// crea el fondo sobre el cual se moverán las
		// cartas (en él irán las cartas "muertas")
		back = new BufferedImage
		(
			biOut.getWidth(), biOut.getHeight(),
			BufferedImage.TYPE_3BYTE_BGR
		);

		// obtiene el Graphic2D de 'back'
		grBack = back.createGraphics();

		// inicializa la cantidad de cartas "vivas"
		living = cards.size();

		// inicializa la posición y los ángulos de cada carta
		cState = new ObjectState[living];
		for (i = 0; i < living; i++) cState[i] = new ObjectState();

		// inicializa el estado de cada carta
		iddle = new boolean[living];
		for (i = 0; i < living; i++) iddle[i] = false;

		// dibuja la mesa en el fondo y en 'grOut' el mismo
		drawTable(grBack);
		grOut.drawImage(back, null, 0, 0);

		// marca el tiempo inicial
		tIni = System.currentTimeMillis();
		do
		{
			// obtiene el tiempo actual
			tAct = System.currentTimeMillis() - tIni;

			// obtiene los elementos del vector de cartas
			vecCards = cards.elements();

			// recorre todos los elementos del vector de
			// cartas y a la vez los va contando con 'i'
			for (i = 0; vecCards.hasMoreElements(); i++)
			{
				// obtiene la siguiente carta, su posición y ángulo
				tCard = (TableCard) vecCards.nextElement();
				tCard.getStateInTime(tAct, cState[i]);

				// si el tiempo de anim. de la carta ha expirado...
				if (tCard.getStartTime() + tCard.getDurationTime() < tAct)
				{
					// si la carta no estaba muerta...
					if (!iddle[i])
					{
						iddle[i] = true;	// matarla
						living--;			// dec. el cont. de vivas

						// anula su tiempo de animación
						tCard.setTimes(0, -1);

						// dibujar la carta en 'back'
						drawImage
						(
							tCard.getBImage(),
							back,
							cState[i].x, cState[i].y,
							cState[i].angle,
							cState[i].scale,
							afTrans
						);

						// dibujar la carta en 'biOut'
						drawImage
						(
							tCard.getBImage(),
							biOut,
							cState[i].x, cState[i].y,
							cState[i].angle,
							cState[i].scale,
							afTrans
						);
					}
				}
				// si todavía no comienza su animación
				// dibujar la carta en 'biOut'
				else //if (tCard.getStartTime() <= tAct)
					drawImage
					(
						tCard.getBImage(),
						biOut,
						cState[i].x, cState[i].y,
						cState[i].angle,
						cState[i].scale,
						afTrans
					);
			}

			drawFaces();

			// actualiza la salida
			g.drawImage(biOut, offsetX, offsetY, null);
			grOut.drawImage(back, null, 0, 0);

			// para toda carta viva...
/*			for (j = 0; j < i; j++)	if (!iddle[j])
			{
				// dibuja el fondo sobre la misma
				grOut.drawImage
				(
					back,
					(int) transformX(cState[j].x - TableCard.CARD_RADIUS),
					(int) transformY(cState[j].y - TableCard.CARD_RADIUS),
					(int) transformX(cState[j].x + TableCard.CARD_RADIUS),
					(int) transformY(cState[j].y + TableCard.CARD_RADIUS),
					(int) transformX(cState[j].x - TableCard.CARD_RADIUS),
					(int) transformY(cState[j].y - TableCard.CARD_RADIUS),
					(int) transformX(cState[j].x + TableCard.CARD_RADIUS),
					(int) transformY(cState[j].y + TableCard.CARD_RADIUS),
					null
				);
			}*/

		} while(living > 0); // seguir mientras haya vivas
		drawFaces();
	}
	
	public void drawObjects()
	{
		TableCard tCard;
		AffineTransform afTrans;
		
		afTrans = new AffineTransform();
		
		drawTable(grOut);
		
		for (int i = 0; i < cards.size(); i++)
		{
			tCard = (TableCard) cards.get(i);
			
			drawImage
			(
				tCard.getBImage(),
				biOut,
				tCard.getX(1), tCard.getY(1),
				tCard.getAngle(1), tCard.getScale(1),
				afTrans
			);
		}
		
		for (int i = 0; i < faces.size(); i++)
			((Face) faces.get(i)).setOut(biOut);
		
		drawFaces();
		this.getGraphics().drawImage(biOut, offsetX, offsetY, null);
	}

	/** Retorna la i-ésima TableCard */
	public TableCard getTableCard(int ind) throws ArrayIndexOutOfBoundsException
	{
		return (TableCard) cards.get(ind);
	}

	/** Retorna la cantidad de TableCards en la mesa */
	public int getTableCardCount()
	{
		return cards.size();
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
		ptListener.mouseClicked
		(
			(e.getX() - offsetX) / scale - centerX,
			(e.getY() - offsetY) / scale - centerY,
			e
		);
	}

	// Dibuja las caritas
	private void drawFaces()
	{
		Enumeration fEnum;

		fEnum = faces.elements();
		while (fEnum.hasMoreElements())
		{
			((Face) fEnum.nextElement()).paint();
		}

		// dibuja un rectángulo negro alrededor de la mesa
		grOut.setColor(Color.BLACK);
		grOut.drawRect
		(
			0, 0,
			(int) (TABLE_WIDTH * scale) - 1,
			(int) (TABLE_HEIGHT * scale) - 1
		);
	}
	
	/** 
	 * Retorna el desplazamiento X de la 
	 * salida con respecto al JPanel
	 */
	public float getOffsetX() { return this.offsetX; }

	/** 
	 * Retorna el desplazamiento Y de la 
	 * salida con respecto al JPanel
	 */
	public float getOffsetY() { return this.offsetY; }
	
		
}