package py.edu.uca.fcyt.toluca.table;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.Vector;

import py.edu.uca.fcyt.game.Card;
import py.edu.uca.fcyt.toluca.event.TrucoEvent;
import py.edu.uca.fcyt.toluca.event.TrucoListener;
import py.edu.uca.fcyt.toluca.game.TrucoGame;
import py.edu.uca.fcyt.toluca.game.TrucoPlay;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;
import py.edu.uca.fcyt.toluca.table.animation.Animator;

/**
 * Escucha los eventos del juego
 */
class TrListener implements TrucoListener
{
	protected LinkedList trucoListeners; 	// listeners de eventos
	private Table table;
	private boolean endOfHand = false;

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
		table.getJTrucoTable().buttons[0].setText("Ok");
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
		TrucoPlayer player;
		byte type;
		String name;
		CardManager cManager;
		Card card;
		
		cManager = getCManager();
		
		type = event.getTypeEvent();
		player = event.getPlayer();
		pos = getPManager().getPos(player);
		name = Util.getEventName(type);
		//si name da null quiere decir q no fue ninguna jugada que se cante. ale
		
		System.out.println();
		System.out.println
		(
			"Play for player " + getAssociatedPlayer().getName() + ": " + name
		);
		
		switch (type)
		{
			case TrucoEvent.JUGAR_CARTA:
				card = event.getCard();
				cManager.playCard(pos, card);

				if (table.getPlayer() == player) 
					cManager.showCards(null);
				else if (endOfHand)
					cManager.pushPause(pos, 250);
				System.out.println("Card played: " + card.getDescription());
				break;
			
			case TrucoEvent.CERRARSE:
				System.out.println("Cerrarse! pos: " + pos);
				cManager.playClosed(pos);
				break;
				
			case TrucoEvent.PLAYER_CONFIRMADO:
				System.out.println("TrucoPlayer confirmed");
				break;
				
			case TrucoEvent.CANTO_ENVIDO:
				if (event.getValue() == -1)
					System.out.println("Canto envido inváldo!");
				else
					drawBalloon
					(
						event.getPlayer(), 
						Integer.toString(event.getValue()), 
						true
					);
				break;
				
			case TrucoEvent.CARTAS_REPARTIDAS:
				break;
				
			default:
				try
				{
					drawBalloon(event.getPlayer(), name, true);
				}
				catch(InvalidParameterException ex)
				{
					System.out.println("---------------------- ");
					System.out.println("- Unknown play event - ");
					System.out.println("---------------------- ");
					System.out.println("Play number: " + type);
					System.out.println("Play name: " + name);
					System.out.println("TrucoPlayer: " + event.getPlayer());
					System.out.println("---------------------- ");
//					throw new IllegalStateException();
				}
					
				break;
		}
	}

	public void turn(TrucoEvent event)
	{
		Vector aPlays = new Vector();
		TrucoGame tGame;
		TrucoPlay tPlay;
		TrucoPlayer aPlayer;
		byte play;
		int valEnvido;
		
		System.out.println("Turn for player " + getAssociatedPlayer().getName());
		
		// obtiene el TrucoGame de la mesa y el jugador actual
		tGame = table.getTGame();
		aPlayer = (TrucoPlayer) table.getPlayer();

		// establece el estado de la mesa
		if (event.getPlayer() == getAssociatedPlayer())
		{
			table.setStatus(Table.PLAY);

			table.envidoPoints = event.getValue();
			
			// caga el vector de jugadas habilitadas
			for (int i = 0; i < Util.getPlayCount(); i++)
			{
				play = Util.getPlay(i);
				
				if (play == TrucoPlay.CANTO_ENVIDO)
				{
					tPlay = new TrucoPlay
					(
						(TrucoPlayer) aPlayer, 
						play, 
						table.envidoPoints
					);
				}
				else
					tPlay = new TrucoPlay(aPlayer, play);
					
					
				if (tGame.esPosibleJugar(tPlay)) 
					aPlays.add(new Byte(play));
			}
			
			table.setAllowedPlays(aPlays);
		}
		else if (table.getPManager().isSitted(table.getPlayer()))
			table.setStatus(Table.WAIT);
		else
			table.setStatus(Table.WATCH);

		// resalta la carita con turno
		getFManager().setHighlight
		(
			table.getPManager().getPos(event.getPlayer())
		);

	}

	public void endOfHand(TrucoEvent event)
	{
		int pun1, pun2;

		System.out.println("End of hand for player " + getAssociatedPlayer().getName());
		
		pun1 = table.getPoints(0);
		pun2 = table.getPoints(1);

		table.updateScore(pun1, pun2);

		table.setStatus(Table.WAIT);
		table.getJTrucoTable().buttons[0].setEnabled(true);
		getCManager().pushGeneralPause(2000);
		endOfHand = true;
	}

	public void cardsDeal(TrucoEvent event)
	{
		Card[] cards;
		int sign;

                new Exception("dealing cards...").printStackTrace(System.out);
		System.out.println("Cards deal for player " + getAssociatedPlayer().getName());
		
		cards = event.getCards();

		for (int i = 0; i < 3; i++)
			System.out.println(cards[i].getDescription());
		
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
		CardManager cManager;
		
                //new Exception("player: " + getAssociatedPlayer().getName()).printStackTrace(System.out);
		System.out.println("New hand started for player " + getAssociatedPlayer().getName());
		
		endOfHand = false;		
		table.getJTrucoTable().buttons[0].setEnabled(false);
		cManager = getCManager();

		table.clearSigns();


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
		System.out.println("End of game for player " + table.getPlayer());
		table.initialize();
		table.getTEventMan().fireGameFinished();
	}

	public TrucoPlayer getAssociatedPlayer()
	{
		return table.getPlayer();
	}
	
	public void drawBalloon
	(
		TrucoPlayer player, String text, boolean highlighted
	)
	{
		Face face;
		
		// verificaciones
		Util.verifParam(player != null, "Parámetro 'player' nulo");
		Util.verifParam(text != null, "Parámetro 'text' nulo");
		
		if (player != getAssociatedPlayer())
		{
			// obtiene la carita
			face = getFManager().getFace
			(
				getPManager().getPos(player)
			);
			
			face.pushText(text, highlighted, 750);
			face.pushText(null, false, 100);
		}

		// muestra el mensaje también en el chat si está jugando
		if (!(table.getStatus() == Table.WATCH))
			table.getJTrucoTable().jpChat.showChatMessage
			(
				player, text
			);
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