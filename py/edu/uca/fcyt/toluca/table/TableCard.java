package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.game.*;

import java.awt.image.*;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

// representa a una carta en la mesa
class TableCard implements TableObject
{
	// tamaño de la carta
	final public static int CARD_WIDTH = 88;
	final public static int CARD_HEIGHT = 127;
	final public static int CARD_RADIUS = 78;

	Card card;				// carta
	boolean covered;		// si la carta está cubierta o no

	// estados inicial y final
	ObjectState state[] = new ObjectState[]
	{
		new ObjectState(),
		new ObjectState()
	};

	long startTime;			// tiempo de inicio de animación
	long durationTime;		// tiempo de fin de animación

	// BufferedImage para el dorso
	final private static BufferedImage biBack = createBack();

	// BufferedImage para la carta
	final private BufferedImage biCard = new BufferedImage
	(
		CARD_WIDTH, CARD_HEIGHT,
		BufferedImage.TYPE_3BYTE_BGR
	);

	// construye un TableCard sin carta en él
	protected TableCard()
	{
		this.covered = true;

		setTimes(0, -1);
	}

	// constructor
	public TableCard(boolean covered)
	{
		super();

		// crea el TableCard vacío
		this.covered = covered;
	}
	
	public void setCard(Card c)
	{
		// copia la imágen de la carta en el BufferedImage 'img'
		if (card != null) Util.copyImage(c.getImageIcon(), biCard);

		// Guarda la referencia a la carta
		card = c;
	}
	
		
	
	/** Retorna el Card asociado al TableCard */
	public Card getCard() { return card; }

	// establece el i-ésimo estado del objeto
	public void setState(int ind, float x, float y, float angle, float scale)
	{
		state[ind].x = x;
		state[ind].y = y;
		state[ind].angle = angle;
		state[ind].scale = scale;
	}

	// establece el último estado como el primero
	// y al último carga los datos pasados
	public void pushState(float x, float y, float angle, float scale)
	{
		state[0].x = state[1].x;
		state[0].y = state[1].y;
		state[0].angle = state[1].angle;
		state[0].scale = state[1].scale;

		state[1].x = x;
		state[1].y = y;
		state[1].angle = angle;
		state[1].scale = scale;
	}

	// establece el tiempo en el cual el objeto
	// debe pasar del estado 0 al 1
	public void setTimes(long startTime, long durationTime)
	{
		this.startTime = startTime;
		this.durationTime = durationTime;
	}

	// retorna el tiempo en el cual comienza la animación
	public long getStartTime()
	{
		return startTime;
	}

	// retorna el tiempo de duración de la animación
	public long getDurationTime()
	{
		return durationTime;
	}

	// retornan alguna propiedad del 'ind'-ésimo estado
	public float getX(int ind) { return state[ind].x; }
	public float getY(int ind) { return state[ind].y; }
	public float getAngle(int ind) { return state[ind].angle; }
	public float getScale(int ind) { return state[ind].scale; }

	// carga en 'out' la posición del objeto
	// dependiendo del tiempo 'time'
	public ObjectState getStateInTime(long time, ObjectState out)
	{
		float timeRatio, elapsedTime;

		elapsedTime = time - startTime;

		// establece la razón de tiempo (0 a 1)
		if (elapsedTime < 0)
			timeRatio = 0;
		else if (elapsedTime > durationTime)
			timeRatio = 1;
		else
			timeRatio = (float) elapsedTime / durationTime;

		out.x = state[0].x * (1 - timeRatio) + state[1].x * timeRatio;
		out.y = state[0].y * (1 - timeRatio) + state[1].y * timeRatio;
		out.angle = state[0].angle * (1 - timeRatio) + state[1].angle * timeRatio;
		out.scale = state[0].scale * (1 - timeRatio) + state[1].scale * timeRatio;

		return out;
	}

	// retorna el BufferedImage de la carta
	public BufferedImage getBImage()
	{
		if (covered) return biBack;
		else return biCard;
	}

	// crea la imagen del dorso
	private static BufferedImage createBack()
	{
		BufferedImage back;

		back = new BufferedImage
		(
			CARD_WIDTH, CARD_HEIGHT,
			BufferedImage.TYPE_3BYTE_BGR
		);

		Util.copyImage(new ImageIcon("..\\imagenes\\dorso.gif"), back);

		return back;
	}
}
