package py.edu.uca.fcyt.toluca.table;
import py.edu.uca.fcyt.toluca.*;

import java.util.*;
import py.edu.uca.fcyt.toluca.table.animation.Animable;
import py.edu.uca.fcyt.game.Card;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;

/**
 * Maneja a las cartas de un jugador
 */
class TablePlayer implements Animable
{
	private int posX, posY;	// posición x e y de la repartida
	private double angle;	// ángulo de repartida
	private Vector tCards;			// vector de TableCards
	private int playedCount = 0;	// cantidad de cartas jugadas
	private int playerPos;			// número de silla
		

	// escala de la posición x e y
	private final static double POS_SCALE_X = .4f;
	private final static double POS_SCALE_Y = .2f;

	/**
     * Construye un TablePlayer en la posición 'playerPos' con
     * 'playerCount' cantidad de jugadores y TableCards 'cards'
     */
	public TablePlayer(int playerPos, int playerCount, TableCard tCards[])
	{
		int i, j, k;

		// verificaciones
		Util.verifParam
		(
			playerPos >= 0 && playerPos < 6, 
			"Parámetro 'playerPos' inválido"
		);

		// establece la posición (x, y) y el ángulo de juego
		switch (playerCount)
		{
			case 2:
				posX = (int) (POS_SCALE_X * PlayTable.TABLE_WIDTH * -Math.cos((playerPos + .5) * Math.PI));
				posY = (int) (POS_SCALE_Y * PlayTable.TABLE_HEIGHT * Math.sin((playerPos + .5) * Math.PI));
				angle = -(playerPos * Math.PI);
				break;
			case 4:
				posX = (int) (.5f * POS_SCALE_X * PlayTable.TABLE_WIDTH * -Math.cos((playerPos + 1) * Math.PI / 2));
				posY = (int) (POS_SCALE_Y * PlayTable.TABLE_HEIGHT * Math.sin((playerPos + 1) * Math.PI / 2));
				angle = -((playerPos) * Math.PI / 2);
				break;
			case 6:
				k = playerPos < 2 ? playerPos :
					playerPos < 5 ? playerPos + 1 :
					7;

				posX = (int) (POS_SCALE_X * PlayTable.TABLE_WIDTH * -Math.cos((k + 2) * Math.PI / 4));
				posY = (int) (POS_SCALE_Y * PlayTable.TABLE_HEIGHT * Math.sin((k + 2) * Math.PI / 4));
				angle = -(k * Math.PI / 4);

				break;
			default:
				throw new RuntimeException("Cantidad de jugadores inválido");
		}

		// crea el vector de TableCards
		this.tCards = new Vector();
		
		// carga el vector de TableCards
		for (i = 0; i < tCards.length; i++)
			this.tCards.add(tCards[i]);

		// carga la posición del jugador
		this.playerPos = playerPos;
	}

	/**
	 * Pone la posicion finales de la 'index'-ésima carta boca
	 * abajo y de manera desordenada (la reparte), con
	 * una duración de animación 'duration'
	 */
	synchronized public void setDraw
	(
		TableCard tCard, long duration
	)
	{
		// reinicializa la cantidad de cartas jugadas
		playedCount = 0;
		
		// manda al tope la carta y la reparte
		//toTop(tCard);
		tCard.pushState
		(
			(int) (posX + Math.random() * 5 - 2.5),
			(int) (posY + Math.random() * 5 - 2.5),
			Math.random() * Math.PI * 2 - 1,
			Util.cardScale, null, duration
		);
	}

	/**
	 * Pone las posiciones finales de las
	 * cartas en la mano de cada jugador
	 */
	synchronized public TablePlayer setTake(long duration)
	{
		double ang;
		TableCard tCard;

		// pone el estado de las cartas en las manos de cada jugador
		for(int i = playedCount; i < 3; i++)
		{
			tCard = getTCard(i);
			ang = tCard.getLastState().angle;
			tCard.pushState
			(
				(int) (posX * 2),
				(int) (posY * 3.4),
				ang + Util.normAng(angle - ang), 
				Util.cardScale, null, duration
			);

			tCard.pushState
			(
				(int) (posX * 2),
				(int) (posY * 3.4),
				angle, 
				Util.cardScale, null, 0
			);
		}

		return this;
	}

	/**
	 * Establece los datos de animación
	 * para jugar una carta.
	 * @param tCard		TableCard a jugar
	 * @param duration	duración de la animación
	 */
	synchronized public void setPlayCard
	(
		TableCard tCard, Card card, long duration
	)
	{
		// envía la carta al fondo y la guarda
		tCards.remove(tCard);
		tCards.add(playedCount, tCard);
		
		// establece el estado del TableCard como para jugarla
		tCard.pushState
		(
			(int) (posX + 10 * Math.cos(angle) * (playedCount - 1)),
			(int) (posY + 10 * Math.sin(angle) * (playedCount - 1)),
			angle, Util.cardScale, card, duration
		);

		// incrementa el contador de cartas jugadas
		playedCount ++;
	}

	/**
	 * Establece los datos de animación
	 * para cerrar todas las cartas
	 * @param duration	duración de la animación
	 */
	synchronized public void setCloseCards
	(
		long duration
	)
	{
		TableCard tCard;
		
		for (int i = playedCount; i < 3; i++)
		{
			// establece el estado del TableCard como para jugarla
			getTCard(i).pushState
			(
				(int) (posX + 10 * Math.cos(angle) * (i - 1)),
				(int) (posY + 10 * Math.sin(angle) * (i - 1)),
				angle, Util.cardScale, null, duration
			);
		}

		// establece la cantidad de cartas jugadas
		playedCount = 3;
	}
	
	/**
     * Retorna la cantidad de cartas jugadas
     */
    synchronized public int getPlayedCount()
    {
    	return playedCount;
    }

	/**
	 * Establece los datos de animación para mostrar las cartas
	 * del jugador (que debe estar en la posición 0)
	 * @param duration	duración de la animación
	 */
	synchronized public void setShowHand
	(
		Card[] cards, long duration
	)
	{
		double c, m;
		Card card;
		TableCard tCard;

		// verificaciones
		Util.verif(playerPos == 0, "El jugador debe ser el 0");
		
		if (cards != null) playedCount = 0;

		switch (playedCount)
		{
			case 0: c = -1; break;
			case 1: c = -.5f; break;
			default: c = 0;
		}

		for (int i = playedCount; i < 3; i++)
		{
			tCard = getTCard(i);
			m = (c == 0) ? 15 : 0;
			try
			{
				card = cards[i];
			}
			catch (NullPointerException ex)
			{
				card = tCard.getCard();
			}
			
			tCard.pushState
			(
				(int) (40 * c),
				(int) (PlayTable.TABLE_HEIGHT/2 - 40 - m),
				.6f * c, 1, card, duration
			);
			c++;
		}
	}

	/**
	 * Establece los datos de animación para "abrir" o "cerrar" las
	 * cartas jugadas dependiendo de restore (falso o verdadero resp.)
	 */
	synchronized public void setShowPlayed
	(
		boolean restore, long duration
	)
	{
		double m, x, y;
		TCardState tcState;
		TableCard tCard;

		m = restore ? 10 : 50;
		
//		System.out.println("Oaaa");

		// establece los estados de cada TableCard
		for (int i = 0; i < playedCount; i++)
		{
			tCard = (TableCard) tCards.get(i);
			tCard.pushState
			(
				(int) (getX() + (m * (i - 1)) * Math.cos(angle)),
				(int) (getY() + (m * (i - 1)) * Math.sin(angle)),
				angle, Util.cardScale, tCard.getCard(), duration
			);
		}
	}


	/**
	 * Pone la posición y ángulo de la carta a la posición
	 * de colección de un cierto TablePlayer
	 */
	synchronized public void pushState(TablePlayer tPlayer, long duration)
	{
		TableCard tCard;
		Card card;
		TCardState lastState = null;
		
		for (int i = 0; i < 3; i++)
		{
			tCard = getTCard(i);
			
			try 
			{ 
				lastState = tCard.getLastState();
				card = lastState.card; 
			}
			catch(NullPointerException ex1) 
			{ 
				try
				{
					card = tCard.getCurrState().card;
				}
				catch(NullPointerException ex2)
				{
					card = null; 
				}
			}

			tCard.pushState
			(
				(int) (tPlayer.posX * 2),
				(int) (tPlayer.posY * 3.4f),
				tPlayer.angle,
				Util.cardScale, 
				card, duration
			);
		}
	}

//	/** Retorna el 'index'-ésimo TableCard en la mano */
//	public TableCard getTableCard(int index)
//	{
//		return (TableCard) tCards[index];
//	}

	// retornan la posición (x, y) de repartición
	public int getX() { return posX; }
	public int getY() { return posY; }
	public double getAngle() { return angle; }
	
	synchronized public void paint(BufferedImage biOut, AffineTransform afTrans) 
	{
		for (int i = 0; i < tCards.size(); i++)
			getTCard(i).paint(biOut, afTrans);
	}

	synchronized public void clear(Graphics2D grOut) 
	{
		for (int i = 0; i < tCards.size(); i++)
			getTCard(i).clear(grOut);
	}

	synchronized public boolean advance() 
	{
		for (int i = 0; i < tCards.size(); i++)
			getTCard(i).advance();
			
		return true;
	}

	public boolean isEnabled() 
	{
		return true;
	}

    /**
     * Agrega una pausa a todas las cartas igual a <b>time</b> - 
     * tiempo restante.
     * Si existen caritas del vector que tienen un tiempo restante
     * mayor que el tiempo especificado, éstas son omitidas,
     * @param time		cantidad de tiempo especificado
     */
	synchronized public void pushGeneralPause(long time)
	{
		TableCard card;
		
    	// agrega a cada carita la diferencia
    	for (int i = 0; i < tCards.size(); i++)
    	{
			card = (TableCard) tCards.get(i);
			card.pushPause(time - card.getRemainingTime());
    	}
	}  	
	
	/**
     * Retorna una carta de la mano
     * @param index	índice de la carta
     */
	synchronized public TableCard getTCard(int index)
	{
		return (TableCard) tCards.get(index);
	}
	
    /**
     * Obtiene el tiempo que falta para que todas las
     * cartas terminen de transicionar.
     */
    synchronized public long getRemainigTime()
    {
    	long actTime, maxTime = 0;
    	
    	// obtiene el tiempo máximo
    	for (int i = 0; i < tCards.size(); i++)
    	{
    		actTime = getTCard(i).getRemainingTime();
    		if (actTime > maxTime) maxTime = actTime;
    	}
    	
    	return maxTime;
    }		
    
    /** 
     * Envía una carta al principio del orden de dibujo
     * @param tCard		carta a enviar
     */
    synchronized private void toTop(TableCard tCard)
    {
    	tCards.remove(tCard);
    	tCards.add(tCard);
    }
	
	/**
     * Envía una {@link TableCard} al último orden de animación
     * @param tCard		carta a enviar
     */
	synchronized public void toBottom(TableCard tCard)
	{
    	tCards.remove(tCard);
    	tCards.add(0, tCard);
	}
	
	/**
     * Retorna la siguiente carta no jugada
     */
    synchronized public TableCard getNextHolding()
    {
    	return getTCard(playedCount);
    }

	/**
     * Retorna el {@link TableCard} que contiene a 
     * <code>card</code>. Si no existe dicho TableCard, 
     * retorna <code>null</code>.
     */
    synchronized public TableCard getTCard(Card card)
    {
    	TableCard tCard;
    	for (int i = 0; i < 3; i++)
    	{
    		tCard = (TableCard) tCards.get(i);
    		if (card == tCard.getCard())
    			return tCard;
    	}
    	return null;
    }
     
    /**
     * Agrega una pausa a todas las cartas
     * @param duration	duración de la pausa
     */
     synchronized public void pushPause(long duration)
     {
     	for (int i = 0; i < 3; i++)
     		getTCard(i).pushPause(duration);
     }
}