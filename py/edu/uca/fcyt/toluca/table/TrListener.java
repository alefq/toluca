package py.edu.uca.fcyt.toluca.table;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.Vector;
import java.awt.Cursor;

import py.edu.uca.fcyt.game.Card;
import py.edu.uca.fcyt.toluca.event.TrucoEvent;
import py.edu.uca.fcyt.toluca.event.TrucoListener;
import py.edu.uca.fcyt.toluca.game.TrucoGame;
import py.edu.uca.fcyt.toluca.game.TrucoPlay;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;
import py.edu.uca.fcyt.toluca.table.animation.Animator;
import py.edu.uca.fcyt.toluca.game.PointsDetail;
import java.awt.Color;
import javax.swing.JLabel;

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
		Util.verifParam(t != null, "Par�metro 't' nulo");
		trucoListeners.add(t);
	}
	
	/** Invocado cuando se inicia el juego de truco */
	public void gameStarted(TrucoEvent event)
	{
		System.out.println("Game started for player " + getAssociatedPlayer().getName());
//		new Throwable("").printStackTrace(System.out);
		table.getJTrucoTable().buttons[JTrucoTable.BUTTON_INICIAR_OK].setText("Ok");
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
		//System.out.println("Void play method in " + this.getClass().getName());
		//Aca llega cuando se "Confirma" un jugador desde el serva
		new Throwable("Nada implementado :-(").printStackTrace(System.out);
		//playResponse(event);
	}
	
	public void playResponse(TrucoEvent event)
	{
		int pos;
		TrucoPlayer player;
		byte type;
		String name;
		CardManager cManager;
		Card card;
		int team;
		JLabel jlSaying; 

		table.setCursor(Cursor.DEFAULT_CURSOR);
		
		jlSaying = table.getJTrucoTable().jlSaying; 
		
		cManager = getCManager();
		
		type = event.getTypeEvent();
		player = event.getPlayer();
		pos = getPManager().getPos(player);
		name = Util.getEventName(type);
		team = table.getPManager().getChair(player) % 2;
		//si name da null quiere decir q no fue ninguna jugada que se cante. ale
		
		System.out.println
		(
			"\nJugada para jugador " + 
			getAssociatedPlayer().getName() + ": " + name
		);
		
		switch (type)
		{
			case TrucoEvent.JUGAR_CARTA:
				card = event.getCard();
				cManager.playCard(pos, card);
				// Somos la estirpe guaran�
				if (table.getPlayer() == player)
					//if (getAssociatedPlayer() == player)
					cManager.showCards(null);
				else if (endOfHand)
					cManager.pushPause(pos, 250);
				System.out.println("Carta jugada: " + card.getDescription());
				break;
				
			case TrucoEvent.CERRARSE:
				System.out.println("Cerrarse! pos: " + pos);
				cManager.playClosed(pos);
				break;
				
			case TrucoEvent.PLAYER_CONFIRMADO:
				System.out.println("TrucoPlayer confirmado");
				break;
				
			case TrucoEvent.CANTO_ENVIDO:
				if (event.getValue() == -1)
					System.out.println("Canto envido inv�ldo!");
				else
				{
					String val = Integer.toString(event.getValue());
					
					drawBalloon
					(
						event.getPlayer(), val,	true
					);

					////////////////////
					// CODIGO NUEVO   //
					jlSaying.setText
					(
						jlSaying.getText() + 
						(jlSaying.getText().equals("Canto: ") ? "" : " - ") + 
						" " + val + (team == 0 ? "(R)" : "(A)") 
					);
					////////////////////
				}

				break;
				
			case TrucoEvent.CARTAS_REPARTIDAS:
				break;
				
			default:
				try
				{
					drawBalloon(event.getPlayer(), name, true);

					////////////////////
					// CODIGO NUEVO   //
					if (type ==  TrucoEvent.TRUCO)
						jlSaying.setText("Canto: ");
						
					jlSaying.setText
					(
						jlSaying.getText() + 
						(jlSaying.getText().equals("Canto: ") ? "" : " - ") + 
						" " + name + (team == 0 ? "(R)" : "(A)") 
					);
					////////////////////
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
		
		System.out.println("Turn for player " + event.getPlayer().getName());
		
		// obtiene el TrucoGame de la mesa y el jugador actual
		tGame = table.getTGame();
		aPlayer = (TrucoPlayer) table.getPlayer();
		
		// establece el estado de la mesa
		if (event.getPlayer() == getAssociatedPlayer())
		{
			table.flash(true);
			
			if (event.getTypeEvent() == TrucoEvent.TURNO_JUGAR_CARTA)
				table.setStatus(Table.PLAY);
			else
				table.setStatus(Table.PLAYCANTO);
			
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
		else 
		{
			table.flash(false);
			if (table.getPManager().isSitted(table.getPlayer()))
				table.setStatus(Table.WAIT);
			else
				table.setStatus(Table.WATCH);
		}
		
		// resalta la carita con turno
		getFManager().setHighlight
		(
		table.getPManager().getPos(event.getPlayer())
		);
		
	}
	
	public void endOfHand(TrucoEvent event)
	{
		int pun1, pun2;
		TrucoGame tGame;
		
		////////////////////
		/// CODIGO NUEVO ///
		table.flash(false);
		////////////////////
		
		System.out.println("End of hand for player " + getAssociatedPlayer().getName());
		
		tGame = table.getTGame();
		
		pun1 = table.getPoints(0);
		pun2 = table.getPoints(1);
		
		table.updateScore(pun1, pun2);
		
		String sStrings[], string, token;
		Vector vStrings;
		Vector vPoints;
		
		vPoints = tGame.getDetallesDeLaMano();
		 
		sStrings = new String[vPoints.size()];
		for (int i = 0; i < sStrings.length; i++)
			sStrings[i] = ((PointsDetail) vPoints.get(i)).aString();
			
		table.getTTextAnimator().showStrings
		(
			sStrings, new Color[]
			{
				Color.BLACK, 
				Color.YELLOW, 
				Color.ORANGE
			}, -50, 500, 15000
		);
		///////////////////
		
		table.setStatus(Table.WAIT);
		table.getJTrucoTable().buttons[JTrucoTable.BUTTON_INICIAR_OK].setEnabled(true);
		endOfHand = true;
	}
	
	public void cardsDeal(TrucoEvent event)
	{
		Card[] cards;
		int sign;
		
		//Fnew Exception("dealing cards...").printStackTrace(System.out);
		System.out.println("Cards deal for player " + getAssociatedPlayer().getName());
		
		cards = event.getCards();
		
		for (int i = 0; i < 3; i++)
			System.out.println(cards[i].getDescription());
		
		// carga las se�as
		for (int i = 0; i < 3; i++)
		{
			sign = Sign.getSign(cards[i]);
			if (sign != Sign.NONE) table.addSign(sign);
		}
		
		if (event.getPlayer() == getAssociatedPlayer())
			getCManager().showCards(cards);
	}
	
	public void handStarted(TrucoEvent event)
	{
		TrucoPlayer tPlayer;
		int dealPos, turnPos;
		CardManager cManager;

		///////////////////
		// CODIGO NUEVO ///
		table.getTTextAnimator().clearAll();
		///////////////////
		
		table.getJTrucoTable().jlSaying.setText("Canto: ");
		
		//new Exception("player: " + getAssociatedPlayer().getName()).printStackTrace(System.out);
		System.out.println("New hand started for player " + getAssociatedPlayer().getName());
		
		endOfHand = false;
		table.getJTrucoTable().buttons[JTrucoTable.BUTTON_INICIAR_OK].setEnabled(false);
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
		Util.verifParam(player != null, "Par�metro 'player' nulo");
		Util.verifParam(text != null, "Par�metro 'text' nulo");
		
		if (player != getAssociatedPlayer())
		{
			// obtiene la carita
			face = getFManager().getFace
			(
				getPManager().getPos(player)
			);
			
			face.pushText(text, highlighted, 1750);
			face.pushText(null, false, 100);
		}
		
		// muestra el mensaje tambi�n en el chat si est� jugando
		if (!(table.getStatus() == Table.WATCH))
			table.getJTrucoTable().jpChat.showChatMessage
			(
				player, text
			);
	}
	
	/** Retorna el animador */
	private Animator getAnimator()
	{ return table.getAnimator(); }
	
	/** Retorna el manejador de cartas */
	private CardManager getCManager()
	{ return table.getCManager(); }
	
	/** Retorna el manejador de jugadores */
	private PlayerManager getPManager()
	{ return table.getPManager(); }
	
	/** Retorna el manejador de caritas */
	private FaceManager getFManager()
	{ return table.getFManager(); }
	
	/** Retorna el TrucoGame */
	private TrucoGame getTGame()
	{ 
		return table.getTGame(); 
	}
}