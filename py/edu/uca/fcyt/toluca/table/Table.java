package py.edu.uca.fcyt.toluca.table;

//import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.JFrame;

import py.edu.uca.fcyt.game.Card;
import py.edu.uca.fcyt.game.ChatPanelContainer;
import py.edu.uca.fcyt.toluca.event.TableEvent;
import py.edu.uca.fcyt.toluca.event.TableListener;
import py.edu.uca.fcyt.toluca.event.TrucoListener;
import py.edu.uca.fcyt.toluca.game.TrucoCard;
import py.edu.uca.fcyt.toluca.game.TrucoGame;
import py.edu.uca.fcyt.toluca.game.TrucoGameClient;
import py.edu.uca.fcyt.toluca.game.TrucoPlay;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;
import py.edu.uca.fcyt.toluca.game.TrucoTeam;
import py.edu.uca.fcyt.toluca.table.animation.Animator;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Maneja el juego de truco
 */
public class Table implements 
	PTableListener, ChatPanelContainer, 
	ActionListener, WindowListener, ChangeListener
{
	public static final int SIT = 0;
	public static final int PLAY = 1;
	public static final int PLAYCANTO = 2;
	public static final int CALL = 3;
	public static final int WAIT = 4;
	public static final int WATCH = 5;
	
	private int status;			// estado actual
	private JFrame jFrame;			// jFrame principal
	private JTrucoTable jtTable;		// panel principal
	private PlayTable pTable;			// mesa de juego
	private int playerCount;			// cantidad de jugadores
	private CardManager cManager;		// manejador de cartas
	private PopupTrucoPlays pTrucoPlays; // popup de jugadas
	private Vector players;
	private TrucoGameClient tGame;			// juego de truco asociado
	private boolean host;				// es o no el host
	private PlayerManager pManager;	// manejador de jugadores
	private TrucoPlayer actualPlayer; // jugador actual
	private int tableNumber;			// n�mero de mesa
	private FaceManager fManager;		// FaceManager asociado
	private Vector signs;				// vector de se�as
	private Animator animator;			// animador de objetos
	private Vector aPlays;	// jugadas habilitadas
	protected int envidoPoints;
	private TTextAnimator ttAnimator;
	private TableFrame tFrame; 
	
	// manejador de eventos de mesa
	protected TableEventManager tEventMan;
	
	// TrucoListener asociado
	protected TrListener trListener;
	
	public Vector getPlayers()
	{ return players; }
	
	public int getTableNumber()
	{ 
		return tableNumber; 
	}
	public void setTableNumber(int tableNumber)
	{ 
		this.tableNumber = tableNumber; 
	}
	
	
	/** Crea un Table asociado con 'player' */
	public Table(TrucoPlayer player, boolean host)
	{
		actualPlayer = player;
		this.host = host;
		players = new Vector(0, 1);
		playerCount = 6;

		// crea un nuevo manejador de eventos de tabla
		tEventMan = new TableEventManager(this);

		// crea el manejador de jugadores
		pManager = new PlayerManager(6);
	}
	
	/**
	 * Inicializa los manejadores
	 */
	public void initialize()
	{
		// inicializa el TrucoGame actual
		tGame = null;

		ttAnimator.clearAll();
		ttAnimator.showPresentation();

		// establece el texto del bot�n
		jtTable.buttons[JTrucoTable.BUTTON_INICIAR_OK].setText("Iniciar");
		jtTable.buttons[JTrucoTable.BUTTON_INICIAR_OK].setEnabled(false);
		
		// inicializa el estado actual
		status = Table.SIT;
		
		// remueve manejador de cartas de la animaci�n
		animator.removeAnim(cManager);
		
		// remueve manejador de caritas
		animator.removeAnim(fManager);
		
		// inicializa el puntaje actual
		jtTable.score.actualizarPuntaje(0, 0);
		
		// crea el manejador de caras y lo agrega al animador
		fManager = new FaceManager();
		animator.addAnim(fManager);
		
		// agrega 6 caritas sin nombre y las muestra
		fManager.addUnnamedFaces(6);
		fManager.hideFaces();
		fManager.pushGeneralPause(ttAnimator.getRemainingTime());
		fManager.showFaces();

		// crea el marco y lo agrega al animador
		tFrame = new TableFrame();
		animator.addAnim(tFrame);
	}
	
	public void show()
	{
		Face face;
		TrucoPlayer player;
		
		// crea los objetos
		jtTable = new JTrucoTable(this);
		pTable = jtTable.getPlayTable();
		trListener = new TrListener(this);
		ttAnimator = new TTextAnimator();
		
		jFrame = new JFrame();
		jFrame.setTitle
		(
			"Toluca: " + actualPlayer.getName()
			+ (host ? " (host)" : "")
		);
		jFrame.getContentPane().add(jtTable);
		jFrame.setSize(600, 500);
		
		animator = pTable.getAnimator();
		signs = new Vector(3, 0);

		// agrega los jugadores a la lista mostrada
		for (int i = 0; i < players.size(); i++)
			jtTable.jpWatchers.addPlayer
			(
				((TrucoPlayer) players.get(i)).getName()
			);
			
		animator.addAnim(ttAnimator);

		initialize();

		// agrega los nombres y las caritas de los players
		for (int i = 0; i < pManager.getPlayerCount(); i++)
		{
			face = fManager.getFace(i);
			player = pManager.getPlayer(i);
			if (player != null)
			{
				face.setName(player.getName());
				face.loadFacesFromURL("/py/edu/uca/fcyt/toluca/images/faces/standard/");
			}
		}

		jFrame.show();
		jFrame.addWindowListener(this);
		jtTable.jpChat.showChatMessage
		(
			actualPlayer, 
			"Inicia tus mensajes con \\ si no quieres que " +  
			"salgan en la mesa.", new String[]{"[", "]"}		);

		new Thread(animator).start();
	}
	
	
	/** Agrega el jugador 'player' a la mesa */
	public void addPlayer(TrucoPlayer player)
	{
		players.add(player);
		if (jtTable != null)
			jtTable.jpWatchers.addPlayer(player.getName());
		tEventMan.firePlayerJoined(player);
	}
	
	/**
	 * Elimina al jugador 'player' de la mesa.
	 * @return 	Verdadero si estaba en la mesa y se lo elimin�,
	 *			de lo contrario falso.
	 */
	public boolean removePlayer(TrucoPlayer player)
	{
		boolean ret;
		
		ret = players.remove(player);
		jtTable.jpWatchers.removePlayer(player.getName());
		
		if (ret && tGame != null && pManager.isSitted(player))
		{
			jtTable.jpChat.showChatMessage
			(
			new TrucoPlayer("[ System ]"),
			"El juego ha finalizado debido a que el jugador " +
			player.getName() + " lo ha abandonado."
			);
			initialize();
			tEventMan.fireGameFinished();
		}
		
		return ret;
	}
	
	/** Hecha a un jugador de la mesa */
	public void kickPlayer(TrucoPlayer player)
	{
		removePlayer(player);
		tEventMan.firePlayerKicked(player);
	}
	
	/** Retorna el PlayTable de esta mesa */
	PlayTable getPlayTable()
	{ 
		return pTable; 
	}
	
	/** Retorna el TrucoListener de esta Table */
	public TrucoListener getTrucoListener()
	{
		return trListener;
	}
	
	/** Registra un TableListener */
	public void addTableListener(TableListener t)
	{
		tEventMan.addListener(t);
	}
	
	/** Registra un TrucoListener */
	public void addTrucoListener(TrucoListener t)
	{
		trListener.addListener(t);
	}
	
	/** Inicia el juego de esta mesa con Game 'game'*/
	public void startGame(TrucoGameClient game)
	{
		long rem;
		
		// verificaciones
		Util.verifParam(actualPlayer != null, "No hay jugador actual");
		Util.verifParam(game != null, "TrucoGame nulo");
		Util.verifParam(players != null, "Vector de Players nulo");
		
		// actualiza el TrucoGame actual y carga su TrucoListener
		tGame = game;
		tGame.addTrucoListener(trListener);
		
		// reubica las caritas en pManager
		pManager.startGame();
		
		// crea un nuevo manejador de cartas y lo agrega al animador
		cManager = new CardManager(pManager.getPlayerCount());
		animator.addAnim(cManager);
		
		// quita todas las caritas
		fManager.pushGeneralPause(100);
		fManager.hideFaces();
		fManager.removeFaces();
		
		// agrega las caritas pero en el orden correcto
		fManager.addFaces(pManager, !pManager.isSitted(actualPlayer));
		fManager.pushGeneralPause(100);
		fManager.hideFaces();
		rem = Math.max
		(
			cManager.getRemainigTime(),
			fManager.getRemaninigTime()
		);
		
		fManager.pushGeneralPause(rem);
		fManager.showFaces();
		
		// limpia las se�as cargadas
		signs.clear();
		
		// manda al frente en el orden correcto
		animator.toTop(fManager);
		animator.toTop(ttAnimator);
		animator.toTop(tFrame);

		// avista que el juego inici� correctamente
		tEventMan.fireGameStarted();
	}
	
	/**
	 * Sienta a un jugador en una silla
	 * @param player	Jugador
	 * @param chair		silla
	 */
	public void sitPlayer(TrucoPlayer player, int chair)
	{
		System.out.println("Mesa de " + getPlayer() + ": sentando a " + player + " en la mesa " + chair);
		Face face;
		
		pManager.sitPlayer(player, chair);
		if (player == actualPlayer)
			pManager.setActualPlayer(player);
		
		if (fManager != null)
		{
			face = fManager.getFace(chair);
			face.setName(player.getName());
			face.loadFacesFromURL("/py/edu/uca/fcyt/toluca/images/faces/standard/");
		}
		
//		System.out.println("----------------- TABLA DE: " + actualPlayer.getName() + "-----------------");
//		System.out.println("evenTeams: " + pManager.evenTeams());
//		System.out.println("getActualChair: " + pManager.getActualChair());
//		System.out.println("isSitted: " + pManager.isSitted(actualPlayer));

		if (jtTable != null)
			jtTable.buttons[JTrucoTable.BUTTON_INICIAR_OK].setEnabled
			(
				pManager.evenTeams() &&
				(pManager.getActualChair() == 0) &&
				pManager.isSitted(actualPlayer)
			);
		
		// avisa que le player se sent� correctamente
		tEventMan.firePlayerSit();
	}
	
	/**
	 * Para a un jugador de la silla en donde est� sentado
	 */
	public void standPlayer(int chair)
	{
		Face face;
		TrucoPlayer p;
		
		p = pManager.standPlayer(chair);
		
		if (fManager != null)
		{
			face = fManager.getFace(chair);
			face.setName("");
			face.loadFacesFromURL(null);
		
			jtTable.buttons[JTrucoTable.BUTTON_INICIAR_OK].setEnabled(pManager.evenTeams());
		}
		
		// avisa que el player se levant� correctamente
		tEventMan.firePlayerStanded(p);
	}
	
	/** Retorna el TrucoPlayer propietario de esta mesa */
	public TrucoPlayer getPlayer()
	{
		return actualPlayer;
	}
	
	/**
	 * Establece el estado actual de la mesa.
	 * @param status 	nuevo estado de la mesa.
	 */
	protected void setStatus(int status)
	{
		this.status = status;
	}
	
	/** Retorna el estado actual de la mesa. */
	protected int getStatus()
	{
		return status;
	}
	
	public void mouseClicked(int x, int y, MouseEvent e)
	{
		int pos;
		boolean getOut = false;
		
		if (status == SIT)
			sitIfClick(x, y);
		else
		{ 
			if (status == PLAYCANTO || status == PLAY)
			{
				if (e.getButton() == MouseEvent.BUTTON1)
				{
					Card card;
					
					if (status == PLAYCANTO)
						// si hay que responder alg�n canto,
						// no jugar la carta
						card = null;
					else
						// retorna la carta jugada si se jug�
						card = cManager.playCardIfClick(x, y);
					
					// si no se jug�...
					if (card != null)
					{
						setCursor(Cursor.WAIT_CURSOR);
						tGame.play(new TrucoPlay
						(
							actualPlayer,
							(byte) TrucoPlay.JUGAR_CARTA,
							(TrucoCard) card
						));
					}
				}
				else
				{
					try
					{
						new PopupTrucoPlays
						(
							this, aPlays, envidoPoints
						).show(pTable, e.getX(), e.getY());
					}
					catch(NullPointerException ex)
					{}
				}
			}

			
				// abrir las cartas de alg�n jugador si
				// se presion� en ellas, si tampoco
				// ocurri� esto...
				pos = cManager.getClickCards(x, y);
				if (pos == -1)
				{
					if (status != WATCH) 
						sendSignsIfClick(x, y);
				}
				else
					showPlayed(pos);
		}
	}
	
	/**
	 * Sienta o para al jugador actual en/de una
	 * silla si �sta fue seleccionada
	 */
	protected boolean sitIfClick(int x, int y)
	{
		int pos;
		
		// carga la posici�n clickeada
		pos = fManager.getPosIfClick(x, y);
		
		// si existe dicha posici�n...
		if (pos != -1)
		{
			TrucoPlayer player;
			int chair, actualChair;
			
			// obtiene la silla en esa posici�n
			chair = pManager.getChair(pos);
			
			// obtiene el player sentado en esa silla
			player = pManager.getPlayer(chair);
			
			if (player == actualPlayer)
			{
				tEventMan.firePlayerStandRequest(chair);
				return true;
			}
			
			// si hay otra persona sentada retornar con falso
			if (player != null) return false;
			
			
			// obtiene la silla actual
			actualChair = pManager.getChair(actualPlayer);
			
			// si el TrucoPlayer actual ya est� sentado ret. con falso
			if (actualChair != -1)
			{
				tEventMan.firePlayerStandRequest(actualChair);
				tEventMan.firePlayerSitRequest(chair);
				return true;
			}
			
			// dispara los eventos 'sitRequiested' de todos
			// los TableListeners registrados
			tEventMan.firePlayerSitRequest(chair);
			return true;
		}
		
		return false;
	}
	
	public void say(byte say)
	{
		String strPts;
		int intPts;
		boolean bad = true;
		
		setCursor(Cursor.WAIT_CURSOR);
		switch (say)
		{
			case TrucoPlay.CANTO_ENVIDO:
				intPts = envidoPoints;
				tGame.play(new TrucoPlay(actualPlayer, (byte) say, intPts));
				break;
			default:
				tGame.play(new TrucoPlay(actualPlayer, say));
				break;
		}
	}
	
	/**
	 * Muestra las se�as a un cierto
	 * jugador si se clicke� en �l
	 */
	protected boolean sendSignsIfClick(int x, int y)
	{
		int pos;
		TrucoPlayer player;
		
		// obtiene la posici�n de jugador al
		// cual se debe enviar las se�as
		pos = fManager.getPosIfClick(x, y);
		
		// si no se hizo click en ninguno, salir con falso
		if (pos == -1) return false;
		
		// si es la posici�n del contrincante, salir
		if (pos % 2 == 0) return false;
		
		// obtiene el jugador sentado en esa posici�n
		player = pManager.getPlayer(pManager.getChair(pos + 1));
		
		// si no hay se�as, informa a todos los
		// TableListeners registrados la se�a "seca"
		if (signs.size() == 0) tEventMan.fireSignSendRequest
		(
			player, Sign.SECA
		);
		// si las hay informa a todos los
		// TableListeners registrados las se�as
		else for (int i = 0; i < signs.size(); i++)
			tEventMan.fireSignSendRequest
			(
				player, ((Integer) signs.get(i)).intValue()
			);
		
		return true;
	}
	
	/**
	 * Muestra la se�a emitida por un cierto jugador
	 */
	public void showSign(TableEvent event)
	{
		TrucoPlayer src, dest;
		
		// obtiene el TrucoPlayer emisor y el receptor
		src = event.getPlayer(0);
		dest = event.getPlayer(1);
		
		// muestra la se�a
		if (dest == actualPlayer) fManager.showSign
		(
			pManager.getPos(src) - 1, event.getValue()
		);
		
		// avisa que la se�a fue enviada correctamente
		tEventMan.fireSignSent(event.getPlayer(1));
	}
	
	/**
	 * Agrega una se�a a emitir posteriormente
	 */
	protected void addSign(int sign)
	{
		// verificaciones
		Util.verifParam(Sign.isSign(sign), "Se�a inv�lida");
		
		signs.add(new Integer(sign));
	}
	
	/**
	 * Limpia las se�as
	 */
	protected void clearSigns()
	{ signs.clear(); }
	
	public void sendChatMessage(TrucoPlayer player, String htmlMessage)
	{
		tEventMan.fireChatMessageRequested(player, htmlMessage);
	}
	
	public void showChatMessage(TrucoPlayer player, String htmlMessage)
	{
		int pos;
		
		// si el mensaje comienza con una barra, va s�lo para
		// el chat
		if (htmlMessage.startsWith("\\"))
		{
			htmlMessage = htmlMessage.substring(1, htmlMessage.length());
		}
		else if (pManager.isSitted(player) && fManager != null)
		{
			pos = pManager.getPos(player);
			fManager.pushText
			(
				pos, htmlMessage, false
			);
		}
		
		// muestra el mensaje de chat y
		// aviza que lleg� correctamente
		if (jtTable != null) jtTable.jpChat.showChatMessage
		(
			player, htmlMessage
		);
		tEventMan.fireChatMessageSent(player, htmlMessage);
	}
	
	public boolean isHost()
	{ return host; }
	
	/** Retorna el animador */
	protected Animator getAnimator()
	{ return animator; }
	
	/** Retorna el manejador de cartas */
	protected CardManager getCManager()
	{ return cManager; }
	
	/** Retorna el manejador de jugadores */
	protected PlayerManager getPManager()
	{ return pManager; }
	
	/** Retorna el manejador de caritas */
	protected FaceManager getFManager()
	{ return fManager; }
	
	/**
	 * Retorna el puntaje de un equipo
	 * @param index		�ndice del equipo
	 */
	protected int getPoints(int index)
	{
		return tGame.getTeam(index).getPoints();
	}
	
	/**
	 * Actualiza los puntajes de cada equipo
	 * @param p0	puntaje para el equipo 0
	 * @param p1	puntaje para el equipo 1
	 */
	protected void updateScore(int p0, int p1)
	{
		jtTable.score.actualizarPuntaje(p0, p1);
	}
	
	/**
	 * Retorna el TrucoGame de esta mesa
	 */
	public TrucoGame getTGame()
	{
		return tGame;
	}
	
	/**
	 * Carga las jugadas habilitadas
	 * @param aPlays	Vector de Bytes de jugadas habilitadas
	 */
	protected void setAllowedPlays(Vector aPlays)
	{
		this.aPlays = aPlays;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String text, pName;
		JButton source;
		TrucoPlayer tPlayer;
		
		source = (JButton) e.getSource();
		text = source.getText();
		if (text.equals("Iniciar"))
		{
			source.setEnabled(false);
			tEventMan.fireGameStartRequest();
			
		}
		else if (text.equals("Ok"))
		{
			jtTable.buttons[JTrucoTable.BUTTON_INICIAR_OK].setEnabled(false);
			ttAnimator.clearAll();
			tGame.startHand(actualPlayer);
		}
		else if (text.equals("Echar"))
		{
			new Thread()
			{
				public void run()
				{
					jtTable.buttons[JTrucoTable.BUTTON_HECHAR].setText("Confirmar");
					Util.wait(this, 3000);
					jtTable.buttons[JTrucoTable.BUTTON_HECHAR].setText("Echar");
				}
			}.start();
		}
		else if (text.equals("Confirmar"))
		{
			jtTable.buttons[JTrucoTable.BUTTON_HECHAR].setText("Echar");
			pName = jtTable.jpWatchers.getSelection();
			
			for (int i = 0; i < players.size(); i++)
			{
				tPlayer = (TrucoPlayer) players.get(i);
				if
				(
					tPlayer != actualPlayer &&
					tPlayer.getName().equals(pName)
				)
					tEventMan.firePlayerKickRequest(tPlayer);
			}
		}
		else if (text.equals("Ayuda"))
		{
			ttAnimator.showIndications();
			new Thread()
			{
				public void run()
				{
					jtTable.buttons[JTrucoTable.BUTTON_AYUDA].setEnabled(false);
					Util.wait(this, 20000);
					jtTable.buttons[JTrucoTable.BUTTON_AYUDA].setEnabled(true);
				}
			}.start();
		}
	}
	
	/**
	 * Muestra las cartas de un player
	 * @param chair		Silla donde est� el player
	 */
	private void showPlayed(int pos)
	{
		cManager.showPlayed(pos);
	}
	
	/**
	 * Retorna la mesa de juego
	 */
	protected JTrucoTable getJTrucoTable()
	{
		return jtTable;
	}
	
	/**
	 * Retorna el manejador de eventos de mesa
	 */
	protected TableEventManager getTEventMan()
	{
		return tEventMan;
	}
	
	/**
	 * Retorna el {@link TrucoPlayer} sentado en una silla.
	 * @param chair		Indice de la silla.
	 */
	public TrucoPlayer getPlayerInChair(int chair)
	{
		return pManager.getPlayer(chair);
	}
	
	/**
	 * Retorna el {@link TrucoPlayer} sentado en una silla.
	 * @param chair		Indice de la silla.
	 */
	public TrucoPlayer getPlayer(int chair)
	{
		return (TrucoPlayer) players.get(chair);
	}
	
	/** Retorna la cantidad de {@link TrucoPlayer}s agregados. */
	public int getPlayerCount()
	{
		return players.size();
	}
	
	/**
	 * Crea un array de dos {@link TrucoTeam}s que se pueden
	 * posteriormente utilizar para crear un {@link TrucoGame}
	 * @return Un array de dos {@link TrucoTeam}s
	 */
	public TrucoTeam[] createTeams()
	{
		TrucoTeam tTeams[];
		TrucoPlayer player;
		
		if (tGame != null)
		{ 
			tTeams = new TrucoTeam[]
			{
				tGame.getTeam(0),
				tGame.getTeam(1)
			};
			
			for (int i = 0; i < 2; i++)
			{
				System.out.println("TEAM: " + i);
				for (int j = 0; j < tTeams[i].getNumberOfPlayers(); j++)
					System.out.println("PLAYER: " + tTeams[i].getPlayerNumber(j));
			}
			
			return tTeams;
		}
			
		// se crean los teams
		tTeams = new TrucoTeam[]
		{
			new TrucoTeam("Rojo"),
			new TrucoTeam("Azul")
		};
		
		// se agregan los players a los teams
		for (int i = 0; i < pManager.getPlayerCount(); i++)
		{
			player = pManager.getPlayer(i);
			if (player != null)
				tTeams[i % 2].addPlayer(player);
		}
		
		return tTeams;
	}
	
	public void windowClosing(WindowEvent e)
	{
		jFrame.remove(jtTable);
		jFrame.dispose();
	}
	
	public void windowClosed(WindowEvent e)
	{
		tEventMan.firePlayerLeft();
		animator.stopAnimator();
	}
	
	/**
	 * Retorna el {@link TrucoGame asociado}
	 */
	public TrucoGame getTrucoGame()
	{
		return tGame;
	}
	
	public void windowOpened(WindowEvent e)
	{}
	public void windowIconified(WindowEvent e)
	{}
	public void windowDeiconified(WindowEvent e)
	{}
	public void windowActivated(WindowEvent e)
	{}
	public void windowDeactivated(WindowEvent e)
	{}
	
	public String getOrigin()
	{
		return String.valueOf(getTableNumber());
	}
	
	public void finish()
	{
		jFrame.dispose();
	}
	
	void flash(boolean on)
	{ 
		tFrame.flash(on);		
	}
	
	protected TTextAnimator getTTextAnimator()
	{
		return ttAnimator;
	}
	
	protected void setCursor(int type)
	{
		if (pTable != null)
			pTable.setCursor(type);
	}

	public void stateChanged(ChangeEvent arg0)
	{
		animator.setDelay(1000 / jtTable.actions.getValue());
	}

	public int getChair(TrucoPlayer p)
	{
		return pManager.getChair(p);
	}
}

