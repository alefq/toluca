package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.table.animation.*;
import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.toluca.event.*;
import py.edu.uca.fcyt.game.*;

import java.util.*;

/**
 * Escucha los eventos del juego
 */
class TrListener implements TrucoListener
{
	protected LinkedList trucoListeners; 	// listeners de eventos
	private Table table;

	/** Construye un TrucoListenerManger con Table 'table' */
	public TrListener(Table table)
	{
		trucoListeners = new LinkedList();
		this.table = table;
	}

	/** Registra un listener de eventos TrucoListener */
	public void addListener(TrucoListener t)
	{
		Util.verifParam(t != null, "Parámetro 't' nulo");
		trucoListeners.add(t);
	}

	/** Invocado cuando se inicia el juego de truco */
	public void gameStarted(TrucoEvent event)
	{
		System.out.println("Game started for player " + getAssociatedPlayer().getName());
		table.getJTrucoTable().buttons[0].setText("Repartir");
    }

	/**
     * Devuelve la cantidad de jugadores 
     */
	private int playerCount()
	{
		return getPManager().getPlayerCount();
	}

	public void play(TrucoEvent event) 
	{
		int pos;
		Player player;
		byte type;
		String name;
		CardManager cManager;
		Card card;
		
		cManager = getCManager();
		System.out.println("Play for player " + getAssociatedPlayer().getName());
		
		type = event.getTypeEvent();
		player = event.getPlayer();
		pos = getPManager().getPos(player);
		switch (type)
		{
			case TrucoEvent.JUGAR_CARTA:
				card = event.getCard();
				
				cManager.playCard(pos, card);
				if (player == getAssociatedPlayer())
					cManager.showCards(null);
				break;
			
			case TrucoEvent.CERRARSE:
				cManager.playClosed(pos);
				break;
			
			default:
				if (type == TrucoEvent.CANTO_ENVIDO)
					name = Integer.toString(event.getValue());
				else
					name = (String) table.pNames.get(new Byte(type));
					
				if (name == null) 
					System.out.println("Jugada número: " +  type);
				else
				{
					drawBalloon(event.getPlayer(), name, true);
					table.getJTrucoTable().jpChat.showChatMessage
					(
						player, name
					);
				}
				break;
		}
	}

	public void turn(TrucoEvent event)
	{
		Vector aPlays = new Vector();
		Enumeration pEnum = Table.pNames.keys();
		int pos;
		
		System.out.println("Turn for player " + getAssociatedPlayer().getName());

		if (event.getPlayer() == getAssociatedPlayer())
			table.setStatus(table.PLAY);
		else
			table.setStatus(table.WAIT);

		pos = table.getPManager().getPos(event.getPlayer());
		getFManager().setHighlight(pos - 1);
		
		
		while (pEnum.hasMoreElements())
			aPlays.add(pEnum.nextElement());
		
		table.setAllowedPlays(aPlays);
	}

	public void endOfHand(TrucoEvent event)
	{
		System.out.println("End of hand for player " + getAssociatedPlayer().getName());
		table.setStatus(Table.WAIT);
		table.getJTrucoTable().buttons[0].setEnabled(true);
		getCManager().pushGeneralPause(2000);
	}

	public void cardsDeal(TrucoEvent event)
	{
		Card[] cards;
		int sign;

		System.out.println("Cards deal for player " + getAssociatedPlayer().getName());
		
		cards = event.getCards();
		
		// carga las señas
		for (int i = 0; i < 3; i++)
		{
			sign = Sign.getSign(cards[i]);
			if (sign != Sign.NONE) table.addSign(sign);
		}
		
		getCManager().showCards(cards);
	}

	public void handStarted(TrucoEvent event)
	{
		TrucoPlayer tPlayer;
		int dealPos, turnPos;
		int pun1, pun2;
		CardManager cManager;
		
		System.out.println("New hand started for player " + getAssociatedPlayer().getName());
		
		table.getJTrucoTable().buttons[0].setEnabled(false);
		cManager = getCManager();

		table.clearSigns();

		pun1 = table.getPoints(0);
		pun2 = table.getPoints(1);

		table.updateScore(pun1, pun2);

		tPlayer = (TrucoPlayer) event.getTrucoPlayer();
		
		dealPos = getPManager().getPos(tPlayer);
		System.out.println(tPlayer.getName() + " is dealing");
		turnPos = (dealPos + 1) % playerCount();

		cManager.gatherCards(dealPos);
		cManager.deal(dealPos, false);
		cManager.take();
		cManager.putDeckInTable
		(
			playerCount() * 3 * 250 + 100, turnPos
		);
	}

	public void endOfGame(TrucoEvent event)
	{
		table.getJTrucoTable().buttons[0].setText("Finalizar");
	}

	public Player getAssociatedPlayer()
	{
		return table.getPlayer();
	}
	
	public void drawBalloon
	(
		Player player, String text, boolean highlighted
	)
	{
		Face face;
		
		if (player == getAssociatedPlayer()) return;

		face = getFManager().getFace
		(
			getPManager().getPos(player) - 1
		);
		
		face.pushText(text, highlighted, 1000);
		face.pushText(null, false, 100);
	}	

	/** Retorna el animador */	
	private Animator getAnimator() { return table.getAnimator(); }

	/** Retorna el manejador de cartas */
	private CardManager getCManager() { return table.getCManager(); }

	/** Retorna el manejador de jugadores */
	private PlayerManager getPManager() { return table.getPManager(); }

	/** Retorna el manejador de caritas */
	private FaceManager getFManager() { return table.getFManager(); }

	/** Retorna el TrucoGame */
	private TrucoGame getTGame() { return table.getTGame(); }
}