package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.game.Card;
import javax.swing.*;
import java.awt.event.*;

/**
 * Maneja la animación de las cartas
 */
class CardManager
{
	private TablePlayer players[];	// jugadores de mesa
	private int playerCount = -1;	// cantidad de jugadores
	private TableDeck tDeck; 		// maso
	private PlayTable pTable;		// mesa de juego


//	public CardManager
//	(
//		PlayTable pTable, int playerCount, Card[][] cards,
//		Card played[][], int dealer, int[] points,
//		int maxPoints
//	)
//	{
//		this(pTable, playerCount);
//
//		// crea los TablePlayers, uno por cada jugador
//		createTablePlayers();
//
//
//		for (int i = 0; i < playerCount; i++)
//		{
//			players[i].setTake();
//			for (int j = 0; j < played[i].length; j ++)
//				players[i].setPlayCard(played[i][j]);
//
//			players[i].setTimes(0, 1000, true);
//			players[i].setTimes(0, -1, false);
//		}
//	}

	/** construye un CardManager */
	public CardManager(PlayTable pTable, int playerCount)
	{
		int i;

		// crea el maso el cual lo representan 6 cartas
		tDeck = new TableDeck(6);
		tDeck.setState
		(
			1,
			PlayTable.TABLE_WIDTH * .7f,
			PlayTable.TABLE_HEIGHT * .7f,
			0f, Util.cardScale
		);

		// inicializa las variables
		this.playerCount = playerCount;
		this.pTable = pTable;

		// agrega las cartas del maso a 'pTable'
		for (i = 0; i < tDeck.getCardCount(); i++)
			pTable.addCard(tDeck.getTableCard(i));
	}

	// crea los jugadores de mesa
	public void createTablePlayers()
	{
		System.out.println("TablePlayers creados");
		TableCard tCards[];
		
		// verificaciones
		Util.verif
		(
			playerCount != -1, "Cantidad de jugadores inválida"
		);

		// crea el vector de jugadores de mesa
		players =  new TablePlayer[playerCount];

		// carga las cartas a cada jugador de mesa
		for (int i = 0; i < playerCount; i++)
		{
			tCards = new TableCard[3];
			for (int j = 0; j < 3; j++)
			{
				pTable.addCard
				(
					tCards[j] = new TableCard(false)
				);
				tCards[j].setState
				(
					1,
					PlayTable.TABLE_WIDTH * .7f,
					PlayTable.TABLE_HEIGHT * .7f,
					0f, Util.cardScale
				);
			}

			// crea el 'i'-ésimo jugador de mesa
			players[i] = new TablePlayer(i, playerCount, tCards);
		}
	}
	
	public void resetTableCards()
	{
		for (int i = 0; i < pTable.getCardCount(); i++)
			pTable.getTableCard(i).setCard(null);
	}
		

	/** Pone todas las cartas en manos del jugador 'dealer' */
	public void putCardsInDealer(int dealer)
	{
		// pone el maso en el jugador 'dealer'
		tDeck.pushState
		(
			players[dealer].getX() * 2f,
			players[dealer].getY() * 3.4f,
			0, Util.cardScale
		);


		for (int i = 0; i < playerCount; i++)
			players[i].pushState(players[dealer]);
	}

	/** Reparte las cartas */
	public void deal(int dealer, boolean touch)
	{
		TableCard tCard;

		// verificaciones
		Util.verif(dealer < playerCount, "Repartidor inválido");


		for (int i = 0; i < players.length; i++)
		{
			players[i].setDraw();
			for (int j = 0; j < 3; j++)
			{
				tCard = players[i].getTableCard(j);
			
				if (touch) tCard.setTimes(((i + playerCount - dealer - 1) * 3 + j) % (3 * players.length) * 250, 350);
				else tCard.setTimes((j * players.length + i + playerCount - dealer - 1) % (3 * players.length) * 250, 350);
			}
		}

		pTable.animate();
	}

	/** Hace que cada jugador toma sus cartas */
	public void take()
	{
		for (int i = 0; i < players.length; i++)
		{
			players[i].setTake();
			players[i].setTimes(0, 1500, false);
		}

		pTable.animate();
	}

	/**
	 * Hace que el 'player'-ésimo jugador
	 * tome su 'card'-ésima carta
	 */
	public void playCard(int player, int index)
	{
		TableCard tCard;

		// verificaciones
		Util.verif(player < playerCount, "Jugador inválido");

		tCard = players[player].setPlayCard(index);
		tCard.setTimes(0, 250);
		pTable.toTop(tCard);
		pTable.animate();
		tCard.setTimes(0, -10);
	}

	/** Pone el maso en el jugador de índice 'dealer' */
	public void putDeckInTable(int dealer)
	{
		int x, y;
		float ang;
		int d;

		// verificaciones
		Util.verif
		(
			dealer >= 0 && dealer < playerCount,
			"Repartidor de cartas inválido"
		);

		d = (dealer == 0 ? playerCount - 1 : dealer - 1);

		x = players[dealer].getX();
		x += players[d].getX();

		y = players[dealer].getY();
		y += players[d].getY();

		ang = players[dealer].getAngle();
		ang += players[d].getAngle();
		ang /= 2;

		if (dealer == 0) ang -= (float) Math.PI;

		switch (playerCount)
		{
			case 2:

				x = (int) ((dealer - .5f) * 350);

			case 4:
				x *= .73 * 1.5;
				y *= .93 * 1.5;
				break;

			case 6:
				x *= .68;
				y *= 1;
				break;
		}

		tDeck.pushState
		(
			x, y, ang, Util.cardScale
		);
		tDeck.setTimes(0, 1000);

		pTable.animate();
	}

	/**
	 * Hace que el jugador 'dealer'
	 * recoja las cartas y el maso
	 */
	public void gatherCards(int dealer)
	{
		float escX, escY;

		// verificaciones
		Util.verif
		(
			dealer >= 0 && dealer < playerCount,
			"Repartidor de cartas inválido"
		);

		switch (playerCount)
		{
			case 2:
				escX = 0; escY = 3f;
				break;

			case 4:
				escX = 3f; escY = 3f;
				break;

			default:
				escX = 2f; escY = 3f;
				break;
		}

		tDeck.pushState
		(
			players[dealer].getX() * escX,
			players[dealer].getY() * escY,
			0, Util.cardScale
		);
		tDeck.setTimes(0, 1000);

		pTable.animate();

		for (int i = 0; i < playerCount; i++)
		{
			players[i].pushState(players[dealer]);
			players[i].setTimes(0, 1000, false);
		}

		pTable.animate();
	}

	/** Hace que el jugador actual muestre sus cartas */
	public void showCards()
	{
		players[0].setShowHand();
		players[0].setTimes(0, 500, false);
		pTable.animate();
	}
	
	/** Asigna las cartas del jugador actual */
	public void setCards(Card cards[])
	{
		for (int i = 0; i < 3; i++)
			players[0].getTableCard(i).setCard(cards[i]);
	}
	
	/** 
	 * Establece siguiente TableCarta no jugada del  
	 * 'player'-ésimo player a 'card'. Devuelve su índice'*/
	public int setCard(int player, Card card)
	{
		TableCard tCard;
		
		for (int i = 0; i < 3; i++)
		{
			tCard = players[player].getTableCard(i);
			
			if (!players[player].getPlayed(i))
			{
				tCard.setCard(card);
				return i;
			}
		}
		throw new RuntimeException("Todas las cartas asignadas");
	}

	/** Hace que el jugador actual esconda sus cartas */
	public void hideCards()
	{
		players[0].setTake();
		players[0].setTimes(0, 500, false);
		pTable.animate();
	}
	
	/** 
	 * Hace que las cartas del jugador actual se 
	 * muestren en el orden correcto al pintarlas 
	 */
	public void toTop()
	{
		for (int i = 0; i < 3; i++)
			pTable.toTop(players[0].getTableCard(i));
	}

	/**
	 * Hace que las cartas del 'player'-ésimo
	 * jugador se muestren
	 */
	public void showPlayed(int player)
	{
		players[player].setShowPlayed(false);
		players[player].setTimes(0, 500, true);
		pTable.animate();
		players[player].setShowPlayed(true);
		players[player].setTimes(0, 500, true);
		pTable.animate();
	}

	/** Corta el mazo en un porcentaje 'per' */
	public void cutDeck(int per)
	{
		tDeck.setCutCards(per, false);
		tDeck.setTimes(0, 500);
		pTable.animate();
		tDeck.setInvertCards(per);
		tDeck.setTimes(0, 200);
		pTable.animate();

		for (int i = 0; i < tDeck.getCardCount(); i++)
		{
			pTable.toTop(tDeck.getTableCard(i));
		}

		tDeck.setCutCards(per, true);
		tDeck.setTimes(0, 500);
		pTable.animate();
	}

	/**
	 * Juega una carta del jugador actual si 
	 * se clickeó en ella, y la devuelve
	 */
	public Card playCardIfClick(float x, float y)
	{
		TableCard tc;
		float r2;
		r2 = (float) (Math.pow(TableCard.CARD_WIDTH / 2, 2));

		for (int i = 2; i >= 0; i--)
		{
			tc = players[0].getTableCard(i);
			if (!players[0].getPlayed(i)) if
			(
				Math.pow(x - tc.getX(1), 2) +
				Math.pow(y - tc.getY(1), 2)
				<= r2
			)
			{
				hideCards();
				playCard(0, i);

				for (int j = 0; j < 3; j++)
				{
					if (!players[0].getPlayed(j))
						pTable.toTop(players[0].getTableCard(j));
				}

				showCards();

				return tc.getCard();
			}
		}
		return null;
	}

	/**
	 * Muestra las cartas de un jugador
	 * si se cliqueó en ellas
	 */
	public boolean showPlayedIfClick(float x, float y)
	{
		float r2;


		r2 = (float) Math.pow(Util.cardScale * TableCard.CARD_RADIUS, 2);
		for (int i = 0; i < playerCount; i++)
			if
			(
				Math.pow(x - players[i].getX(), 2) +
				Math.pow(y - players[i].getY(), 2) <= r2
			)
			{
				hideCards();
				showPlayed(i);
				showCards();
				return true;
			}

		return false;
	}
	// convierte 'ang' en un ángulo en el rango [-180, 180]
/*	private static double minAng(double ang)
	{
		while (ang > Math.PI) ang -= Math.PI * 2;
		while (ang < -Math.PI) ang += Math.PI * 2;

		return ang;
	}	*/
}