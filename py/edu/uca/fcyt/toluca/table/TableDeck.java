package py.edu.uca.fcyt.toluca.table;

import java.util.*;
import py.edu.uca.fcyt.toluca.*;

/* 
 * Representa a un mazo en la mesa
 */
 
class TableDeck implements TableObject
{
	TableCard cards[];
	
	// construye un TableDeck con 'nCards' cartas representándolo
	public TableDeck(int nCards)
	{
		super();
		
		// crea los TableCards
		cards = new TableCard[nCards];
		for (int i = 0; i < cards.length; i++)
			cards[i] = new TableCard();
	}

	// establece la 'ind'-ésima posición del maso
	public void setState
	(
		int ind, float x, float y, float angle, float scale
	) 
	{
		float myScale;
		
		for (int i = 0; i < cards.length; i++)
		{
			myScale = (float) Math.exp(i / 130.0) * scale;
			cards[i].setState
			(
				ind, x * myScale, y * myScale, 
				angle, myScale
			);
		}
	}

	// "empuja" una nueva posición del maso
	public void pushState(float x, float y, float angle, float scale) 
	{
		float myScale;
		
		for (int i = 0; i < cards.length; i++)
		{
			myScale = (float) Math.exp(i / 130.0);

			cards[i].pushState
			(
				x * myScale, y * myScale, 
				angle, myScale * scale
			);
		}
	}

	// establece los tiempos de animación del maso
	public void setTimes(long startTime, long durationTime) 
	{
		for (int i = 0; i < cards.length; i++)
		{
			cards[i].setTimes(startTime, durationTime);
		}
	}
	
	// devuelve la 'index'-ésima carta que conforma el maso
	public TableCard getTableCard(int index)
	{
		return cards[index];
	}
	
	// devuelve la cantidad de cartas que conforman el maso
	public int getCardCount() {	return cards.length; }
	
	public void setCutCards(int per, boolean undo)
	{
		float s, c;
		int sgn;
		
		// separa las cartas
		for (int i = 0; i < cards.length; i++)
		{
			c = (float) Math.cos(cards[i].getAngle(1));
			s = (float) Math.sin(cards[i].getAngle(1));
			if (undo)
				sgn = (i < cards.length - (cards.length * fixPer(per)) / 100) ? -1 : 1;
			else
				sgn = (i < (cards.length * fixPer(per)) / 100) ? -1 : 1;

			cards[i].pushState
			(
				cards[i].getX(1) + sgn * 40 * c,
				cards[i].getY(1) + sgn * 40 * s,
				cards[i].getAngle(1), 
				cards[i].getScale(1)
			);
		}
		
	}
	
	public void setInvertCards(int per)
	{
		float myScale;
		int cut;
		TableCard[] copy = new TableCard[cards.length];
		
		// nivela sus profundidades al la 
		// profundidad de la primera carta
		for (int i = 0; i < cards.length; i++)
		{
			myScale = (float) Math.exp(i / 130.0);

			cards[i].pushState
			(
				cards[i].getX(1) / myScale, 
				cards[i].getY(1) / myScale, 
				cards[i].getAngle(1), 
				cards[i].getScale(1) / myScale
			);
			copy[i] = cards[i];
		}

		cut = (cards.length * fixPer(per)) / 100;

		for (int i = 0; i < cards.length; i++)
			cards[i] = copy[(i + cut) % cards.length];

		// actualiza las profundidades de las cartas
		for (int i = 0; i < cards.length; i++)
		{
			myScale = (float) Math.exp(i / 130.0);

			cards[i].setState
			(
				1, 
				cards[i].getX(1) * myScale, 
				cards[i].getY(1) * myScale, 
				cards[i].getAngle(1), 
				cards[i].getScale(1) * myScale
			);
		}
	}
		
	private int fixPer(int per)
	{
		if ((cards.length * per) / 100 == 0) 
			return 100 / cards.length + 1;
		else
			return per;
	}	
}
	
	