package py.edu.uca.fcyt.toluca.table;
import py.edu.uca.fcyt.toluca.*;

import java.util.*;

/**
 * Maneja a las cartas de un jugador
 */
class TablePlayer
{
	private int posX, posY;	// posici�n x e y de la repartida
	private float angle;	// �ngulo de repartida
	TableCard[] tCards;		// vector de TableCards
	boolean played[];		// se ha jugado o no la i-�sima carta
	int playedCount = 0;	// cantidad de cartas jugadas
	int playerPos;			// n�mero de silla
		

	// escala de la posici�n x e y
	private final static float POS_SCALE_X = .4f;
	private final static float POS_SCALE_Y = .2f;

	/**
     * Construye un TablePlayer en la posici�n 'playerPos' con
     * 'playerCount' cantidad de jugadores y TableCards 'cards'
     */
	public TablePlayer(int playerPos, int playerCount, TableCard tCards[])
	{
		int i, j, k;

		// verificaciones
		Util.verif(playerPos >= 0 && playerPos < 6, "Posici�n del jugador inv�lida");

		// establece la posici�n (x, y) y el �ngulo de juego
		switch (playerCount)
		{
			case 2:
				posX = (int) (POS_SCALE_X * PlayTable.TABLE_WIDTH * -Math.cos((playerPos + .5) * Math.PI));
				posY = (int) (POS_SCALE_Y * PlayTable.TABLE_HEIGHT * Math.sin((playerPos + .5) * Math.PI));
				angle = (float) -(playerPos * Math.PI);
				break;
			case 4:
				posX = (int) (.5f * POS_SCALE_X * PlayTable.TABLE_WIDTH * -Math.cos((playerPos + 1) * Math.PI / 2));
				posY = (int) (POS_SCALE_Y * PlayTable.TABLE_HEIGHT * Math.sin((playerPos + 1) * Math.PI / 2));
				angle = (float) -((playerPos) * Math.PI / 2);
				break;
			case 6:
				k = playerPos < 2 ? playerPos :
					playerPos < 5 ? playerPos + 1 :
					7;

				posX = (int) (POS_SCALE_X * PlayTable.TABLE_WIDTH * -Math.cos((k + 2) * Math.PI / 4));
				posY = (int) (POS_SCALE_Y * PlayTable.TABLE_HEIGHT * Math.sin((k + 2) * Math.PI / 4));
				angle = (float) -(k * Math.PI / 4);

				break;
			default:
				throw new RuntimeException("Cantidad de jugadores inv�lido");
		}

		// crea el vector que indica si la i-�sima carta fue jugada
		played = new boolean[3];

		// carga el vector de TableCards
		this.tCards = tCards;

		// carga la posici�n del jugador
		this.playerPos = playerPos;
	}

	/**
	 * Pone las posiciones finales de las cartas boca
	 * abajo y de manera desordenada (las reparte)
	 */
	public void setDraw()
	{
		// reinicializa la cantidad de cartas jugadas
		playedCount = 0;

		// establece los estados para repartir las cartas
		for (int i = 0; i < 3; i++)
		{
			played[i] = false;

			tCards[i].covered = true;
			tCards[i].pushState
			(
				(float) (posX + Math.random() * 5 - 2.5),
				(float) (posY + Math.random() * 5 - 2.5),
				(float) (Math.random() * Math.PI * 2 - 1),
				Util.cardScale
			);
		}
	}

	/**
	 * Pone las posiciones finales de las
	 * cartas en la mano de cada jugador
	 */
	public TablePlayer setTake()
	{
		float ang;

		// pone el estado de las cartas en las manos de cada jugador
		for(int i = 0; i < 3; i++)
		{
			if (!played[i])
			{
				//ang = tCard.getAngle(1);
				ang = angle;

				tCards[i].pushState
				(
					(float) (posX * 2),
					(float) (posY * 3.4),
					ang, Util.cardScale
				);
			}
		}

		return this;
	}

	/**
	 * Establece los datos de animaci�n
	 * para jugar la 'card'-�sima carta
	 */
	public TableCard setPlayCard(int index)
	{
		// Verificaciones
		Util.verif(played[index] == false, "La carta " + index + " ya ha sido jugada");
		Util.verif(index >= 0 && index < 3, "Indice " + index + " inv�lido");
		
		// establece el atributo cubierto del TableCard
		tCards[index].covered = false;

		// establece el estado del TableCard como para jugarla
		tCards[index].pushState
		(
			(float) (posX + 10 * Math.cos(angle) * (playedCount - 1)),
			(float) (posY + 10 * Math.sin(angle) * (playedCount - 1)),
			angle,
			Util.cardScale
		);

		// marca la carta como jugada
		played[index] = true;
		
		// incrementa el contador de cartas jugadas
		playedCount ++;
		
		// retorna la carta jugada
		return tCards[index];
	}

	/**
	 * Establece los datos de animaci�n para mostrar las cartas
	 * del jugador (que debe estar en la posici�n 0)
	 */
	public void setShowHand()
	{
		float c, m;

		// verificaciones
		Util.verif(playerPos == 0, "El jugador debe ser el 0");

		switch (playedCount)
		{
			case 0: c = -1; break;
			case 1: c = -.5f; break;
			default: c = 0;
		}

		for(int i = 0; i < 3; i++)
		{
			tCards[i].covered = false;
			if (!played[i])
			{
				m = (c == 0) ? 15 : 0;
				tCards[i].pushState
				(
					40 * c,
					PlayTable.TABLE_HEIGHT/2 - 40 - m,
					.6f * c, 1
				);
				c++;
			}
		}

	}

	/**
	 * Establece los datos de animaci�n para "abrir" o "cerrar" las
	 * cartas jugadas dependiendo de restore (falso o verdadero resp.)
	 */
	public void setShowPlayed(boolean restore)
	{
		float m, x, y;

		m = restore ? 1f/5f : 5;

		// establece los estados de cada TableCard
		for(int i = 0; i < 3; i++)
		{
			if (played[i])
			{
				x = tCards[i].getX(1) - getX();
				y = tCards[i].getY(1) - getY();
				tCards[i].pushState
				(
					x * m + getX(),
					y * m + getY(),
					angle, Util.cardScale
				);
			}
		}
	}


	/**
	 * Pone la posici�n y �ngulo de la carta a la posici�n
	 * de colecci�n de un cierto TablePlayer
	 */
	public void pushState(TablePlayer tPlayer)
	{
		for(int i = 0; i < 3; i++)
		{
			tCards[i].pushState
			(
				(float) (tPlayer.posX * 2),
				(float) (tPlayer.posY * 3.4f),
				tPlayer.angle,
				Util.cardScale
			);
		}
	}

	// establece los tiempos de animaci�n
	// de la 'card'-�sima carta
	public void setTimes(int index, int start, int duration)
	{
		tCards[index].setTimes(start, duration);
	}

	/**
	 * Establece los tiempos de animaci�n para todas las cartas
	 * jugadas si 'cPlayed' es verdadero, y no jugadas y es falso
	 */
	public void setTimes(int start, int duration, boolean cPlayed)
	{
		for (int i = 0; i < 3; i++)
		{
			if (cPlayed == played[i]) setTimes
			(
				i, start, duration
			);
		}
	}

//	/** Retorna el 'card'-�simo TableCard en la mano */
//	public TableCard getTableCard(int card)
//	{
//		return ((TableCard) tCards.get(card)).tCard;
//	}

	/** Retorna el 'index'-�simo TableCard en la mano */
	public TableCard getTableCard(int index)
	{
		return tCards[index];
	}

	// retornan la posici�n (x, y) de repartici�n
	public int getX() { return posX; }
	public int getY() { return posY; }
	public float getAngle() { return angle; }
	public boolean getPlayed(int index)
	{
		return played[index];
	}
}