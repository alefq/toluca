package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.table.animation.*;
import py.edu.uca.fcyt.toluca.table.state.*;
import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.game.*;
import java.util.*;

import java.awt.image.*;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

// representa a una carta en la mesa
class TableCard implements Animable
{
	// tamaño de la carta
	final public static int CARD_WIDTH = 88;
	final public static int CARD_HEIGHT = 127;
	final public static int CARD_RADIUS = 78;
	final public static int CARD_RADIUS2 = (int) Math.pow(CARD_RADIUS, 2);
	final public static int CARD_DIAMETER = CARD_RADIUS * 2;
	
	// BufferedImage para el dorso
	final private static BufferedImage biBack = createBack();
	final private static Hashtable images = getImages();
	
	private BufferedImage biCard;		// BufferedImage para la carta
	private StatesTransitioner states;	// cola de estados de la carta
	

	/**
     * Construye un TableCard sin carta en él
     */
	public TableCard()
	{
		states = new StatesTransitioner();
	}
	
	/**
     * Establece el Card asociado con este TableCard
     */
//	synchronized public void setCard(Card card)
//	{
//		// verificaciones
//		Util.verifParam(card != null, "Parámetro 'card' es nulo");
//		
//		// si no hay BufferedImage, crearlo
//		if (biCard == null) biCard = new BufferedImage
//		(
//			CARD_WIDTH, CARD_HEIGHT,
//			BufferedImage.TYPE_3BYTE_BGR
//		);
//		
//		// copia la imágen de la carta en el BufferedImage 'img'
//		Util.copyImage(card.getImageIcon(), biCard);
//
//		// Guarda la referencia a la carta
//		this.card = card;
//	}
	
	/** 
	 * Agrega un nuevo estado a la cola de estados a animar,
	 * con duración de transición igual a 'duration'
	 */
	synchronized public void pushState
	(
		int x, int y, double angle, 
		double scale, Card card, long duration
	)
	{
		State state;

		// crea el nuevo estado
		state = new TCardState
		(
			x, y, angle, scale, card
		);
		
		states.pushState(state, duration);
	}
	
	/** 
	 * Crea la imagen del dorso y la retorna
	 */
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
	
//	/**
//     * Devuelve una cadena identificadora del TableCard
//     */
//	public String toString()
//	{
//		String ret;
//		ret = super.toString();
//		if (card != null)
//			ret += " [" + card.getValue() 
//				+ " of " + Util.getKindName(card.getKind()) + "]";
//				
//		return ret;
//	}

	public void paint(BufferedImage biOut, AffineTransform afTrans)
	{
		AffineTransformOp afTransOp;	// Dibujador de la imagen
		int centX, centY;
		BufferedImage biCard;
		TCardState cState;
		
		// crea una copia de 'afTrans'
		afTrans = new AffineTransform(afTrans);
		
		// obtiene el estado actual
		cState = (TCardState) states.getCurrState();
		
		if (cState == null) return;
		
		// obtiene la imagen de la carta
		try
		{
			biCard = (BufferedImage) images.get(cState.card);
		}
		catch (NullPointerException ex)
		{
			biCard = biBack;
		}
		
		// obtiene el centro de 'bfIn'
		centX = (int) CARD_WIDTH / 2;
		centY =	(int) CARD_HEIGHT / 2;

		// escala, translada y rota la imagen
		afTrans.translate
		(
			(int) cState.x,
			(int) cState.y
		);
		afTrans.scale(cState.scale, cState.scale);
		afTrans.rotate(cState.angle);
		afTrans.translate
		(
			(int) - centX,
			(int) - centY
		);

		// dibuja la imagen 'bfIn' en 'bfOut
		new AffineTransformOp
		(
			afTrans, AffineTransformOp.TYPE_BILINEAR
		).filter(biCard, biOut);
	}
	
    public void clear(Graphics2D grOut)
    {
    	TCardState cState;
    	
    	cState = (TCardState) states.getCurrState();
    	
    	if (cState == null) return;
//		grOut.setColor(new Color((int) (Math.random() * Math.pow(2, 24))));
		grOut.setColor(Color.GREEN.darker());
		grOut.fillRect
		(
			(int) cState.x - TableCard.CARD_RADIUS,
			(int) cState.y - TableCard.CARD_RADIUS,
			(int) TableCard.CARD_DIAMETER, 
			(int) TableCard.CARD_DIAMETER
		);
	}
	
	/**
     * Agrega una pausa a la cola de estados
     * @param duration	duración de la pausa
     */
    synchronized public void pushPause(long duration)
    {
    	states.pushPause(duration);
    }
    
    /**
     * Avanza la animación de esta carta
     */
    public boolean advance()
    {
    	return states.advance();
    }
    
    /**
     * Retorna el tiempo restante de animación de la carta
     */
    public long getRemainingTime()
    {
    	return states.getRemainingTime();
    }
	
	/**
     * Retorna el estado actual.
     */
    public TCardState getCurrState() 
    {
    	return (TCardState) states.getCurrState();
    }
    
    public boolean isEnabled()
    {
    	return true;
    }
    
    /**
     * Retorna un Hashtable con los pares (Card, BufferedImage),
     * donde el BufferedImage la imagen del {@link Card}.
     */
    private static Hashtable getImages()
    {
    	int num;
    	Card card;
    	BufferedImage biCard;
    	ImageIcon iiCard;
    	Hashtable ret;
    	
    	ret = new Hashtable();
    	
    	// genera el Hashtable
    	for (int i = 0; i < 40; i++)
    	{
    		num = i % 10;
    		num += num > 6 ? 2: 0;
    		card = new Card(i / 10 + 1, num + 1);
    		iiCard = card.getImageIcon();
    		biCard = new BufferedImage
    		(
    			iiCard.getIconWidth(),
    			iiCard.getIconHeight(),
    			BufferedImage.TYPE_3BYTE_BGR
    		);
    		Util.copyImage(iiCard, biCard);
    		ret.put(card, biCard);
//			System.out.println("Card added: " + card.hashCode());
    	}
    	
    	return ret;
    }
    
    /**
     * Retorna la carta contenida
     */
    synchronized public Card getCard()
    {
    	Card card;
    	
		try { card = ((TCardState) states.getCurrState()).card; }
		catch (NullPointerException ex) { card = null; } 
		
		return card;
	}
	
	/**
     * Retorna el último estado de este TableCard
     */
    synchronized public TCardState getLastState()
    {
    	return (TCardState) states.getLastState();
    }
}
