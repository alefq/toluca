package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.table.animation.*;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.toluca.event.*;
import py.edu.uca.fcyt.game.*;
import py.edu.uca.fcyt.toluca.ai.TolucaAgent;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.awt.event.ActionEvent;

/**
 * Maneja el juego de truco
 */
public class Table implements PTableListener, ChatPanelContainer, ActionListener 
{
	public static final int SIT = 0;
	public static final int PLAY = 1;
	public static final int CALL = 2;
	public static final int WAIT = 3;

	private int status;			// estado actual 
    private JFrame jFrame;			// jFrame principal
    private JTrucoTable jtTable;		// panel principal
    private PlayTable pTable;			// mesa de juego
    private int playerCount = -1;		// cantidad de jugadores
    private CardManager cManager;		// manejador de cartas
    private PopupTrucoPlays pTrucoPlays; // popup de jugadas
    private Vector players;
    private TrucoGame tGame;			// juego de truco asociado
    private boolean host;				// es o no el host
    private PlayerManager pManager;	// manejador de jugadores
    private TrucoPlayer actualPlayer; // jugador actual
    private int tableNumber;			// número de mesa
    private FaceManager fManager;		// FaceManager asociado
	private Vector signs;				// vector de señas
	private Animator animator;			// animador de objetos
	// hashtable con el par (jugada, nombre)
	protected static final Hashtable pNames = getPNames();
	private Vector aPlays;	// jugadas habilitadas
    
    // manejador de eventos de mesa
    protected TableEventManager tEventMan;
    
    // TrucoListener asociado
    protected TrListener trListener;
    
    public Vector getPlayers() { return players; }
    
    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }
    
    /** Crea un Table asociado con 'player' */
    public Table(TrucoPlayer player, boolean host) 
    {

        actualPlayer = player;
        jtTable = new JTrucoTable(this);

        playerCount = 6;

        players = new Vector(0, 1);
        
        // crea los objetos
        pTable = jtTable.getPlayTable();
        trListener = new TrListener(this);
        tEventMan = new TableEventManager(this);
       
        jFrame = new JFrame();
        jFrame.setTitle("Toluca: " + actualPlayer.getName());
        jFrame.getContentPane().add(jtTable);
        jFrame.setSize(600, 500);

        animator = pTable.getAnimator();
		signs = new Vector(3, 0);

		initialize();
    }
    
    /**
     * Inicializa los manejadores
     */
    public void initialize()
    {
    	// establece el texto del botón
		jtTable.buttons[0].setText("Iniciar");
		jtTable.buttons[0].setEnabled(false);
		
    	// inicializa el estado actual
		status = Table.SIT;
		
    	// remueve manejador de cartas de la animación
    	animator.removeAnim(cManager);

    	// remueve manejador de caritas
    	animator.removeAnim(fManager);
    	
    	// inicializa el puntaje actual
    	jtTable.score.actualizarPuntaje(0, 0);
    	
    	// crea el manejador de jugadores
        pManager = new PlayerManager(6);
        
		// crea el manejador de caras y lo agrega al animador
		fManager = new FaceManager();
        animator.addAnim(fManager);

		// agrega 6 caritas sin nombre y las muestra
        fManager.addUnnamedFaces(6);
        fManager.hideFaces();
        fManager.showFaces();
    }

    /** Crea un Table asociado con 'player' */
    public Table(TrucoPlayer player, boolean host, TolucaAgent ta) 
    {
        actualPlayer = player;
        tEventMan = new TableEventManager(this);
        trListener = new TrListener(this);
        jFrame = new JFrame();
        jFrame.setTitle("Toluca: " + actualPlayer.getName());
        jFrame.getContentPane().add(jtTable);
        jFrame.setSize(600, 500);
    }

    
    
    public void show() 
    {
        jFrame.show();
    }
    
    
    /** Agrega el jugador 'player' a la mesa */
    public void addPlayer(Player player) {
        players.add(player);
        jtTable.jpWatchers.addPlayer(player.getName());
    }
    
    /** Retorna el PlayTable de esta mesa */
    PlayTable getPlayTable() { return pTable; }
    
    
    /** Retorna el TrucoListener de esta Table */
    public TrucoListener getTrucoListener() {
        return trListener;
    }
    
    /** Registra un TableListener */
    public void addTableListener(TableListener t) {
        tEventMan.addListener(t);
    }
    
    /** Registra un TrucoListener */
    public void addTrucoListener(TrucoListener t) {
        trListener.addListener(t);
    }
    
    /** Inicia el juego de esta mesa con Game 'game'*/
    public void startGame(TrucoGame game) 
    {
    	long rem;
    	
        // verificaciones
        Util.verifParam(actualPlayer != null, "No hay jugador actual");
        Util.verifParam(game != null, "TrucoGame nulo");
        Util.verifParam(players != null, "Vector de Players nulo");
        
        System.out.println("Starting game of " + actualPlayer.getName() + "...");
        
        // actualiza el TrucoGame actual y carga su TrucoListener
        tGame = game;
        tGame.addTrucoListener(trListener);
        // quita todas las caritas
   		fManager.hideFaces();
   		fManager.removeFaces();

		// reubica las caritas en pManager
        pManager.startGame();

		// crea un nuevo manejador de cartas y lo agrega al animador
        cManager = new CardManager(pManager.getPlayerCount());
        animator.addAnim(cManager);
        
        // vuelve al frente a las caritas
        animator.toTop(fManager);

        // agrega las caritas pero en el orden correcto
        fManager.addFaces(pManager);
   		fManager.hideFaces();
   		rem = Math.max
   		(
   			cManager.getRemainigTime(), 
   			fManager.getRemaninigTime()
   		);
   		
        fManager.pushGeneralPause(rem);
//        cManager.pushGeneralPause(rem + 5000);
        fManager.showFaces();
        
        // limpia las señas cargadas
    	signs.clear();
    }
    
    /**
     * Sienta a un jugador en una silla
     * @param player	Jugador
     * @param chair		silla
     */
    public void sitPlayer(Player player, int chair) 
    {
        pManager.sitPlayer(player, chair);
        if (player == actualPlayer)
            pManager.setActualPlayer(player);
        
        fManager.getFace(chair).setName(player.getName());
        
       	jtTable.buttons[0].setEnabled(pManager.evenTeams());
        
        System.out.println(player.getName() + " sitted in chair " + chair + " in table of " + actualPlayer.getName());
    }

	/**
     * Para a un jugador de la silla en donde está sentado
     */
    public void standPlayer(int chair)
    {
    	pManager.standPlayer(chair);
        
        fManager.getFace(chair).setName("(vacío)");
        
       	jtTable.buttons[0].setEnabled(pManager.evenTeams());
        
//        System.out.println(player.getName() + " standed of chair " + chair + " in table of " + actualPlayer.getName());
    }
    
    /** Retorna el Player propietario de esta mesa */
    public Player getPlayer() 
    {
        return actualPlayer;
    }
    
    /**
     * Establece el estado actual de la mesa.
     * @param status 	nuevo estado de la mesa.
     */
    public void setStatus(int status)
    {
    	this.status = status;
    }
    
    public void mouseClicked(int x, int y, MouseEvent e) 
    {
    	int pos;
    	
    	switch (status)
    	{
    		case SIT:
		        sitIfClick(x, y);
		        break;
		    
		    case PLAY:
        		if (e.getButton() == MouseEvent.BUTTON1)
        		{
        			Card card;
        			
        			// retorna la carta jugada si se jugó
        			card = cManager.playCardIfClick(x, y);
        			
        			// si no se jugó...
        			if (card == null) 
        			{
        				// abrir las cartas de algún jugador si 
        				// se presionó en ellas, si tampoco 
        				// ocurrió esto...
        				pos = cManager.getClickCards(x, y);
        				if (pos == -1)
        					sendSignsIfClick(x, y);
        				else
        					tEventMan.fireShowPlayed
        					(
        						pManager.getChair(pos)
	        				);
        			}
	        		// si se jugó
	        		else 
	        			tGame.play(new TrucoPlay
	        			(
	        				actualPlayer, 
	        				(byte) TrucoPlay.JUGAR_CARTA, 
	        				(TrucoCard) card
	        			));
        		}
        		else
        		{
        			try
        			{
        				new PopupTrucoPlays(this, aPlays).show
        				(
        					pTable, e.getX(), e.getY()
        				);
        			}
        			catch(NullPointerException ex) {}
        		}
        		break;
        	case WAIT:
				pos = cManager.getClickCards(x, y);
				if (pos == -1)
					sendSignsIfClick(x, y);
				else
					tEventMan.fireShowPlayed
					(
						pManager.getChair(pos)
				);
        		break;
        }
    }
    
    /**
     * Sienta o para al jugador actual en/de una
     * silla si ésta fue seleccionada
     */
    protected boolean sitIfClick(int x, int y) 
    {
        int pos;
        
		// carga la posición clickeada        
        pos = fManager.getPosIfClick(x, y);

		// si existe dicha posición...
		if (pos != -1)
		{
			Player player;
			int chair, actualChair;
            
            // obtiene la silla en esa posición    
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
            
            // si el Player actual ya está sentado ret. con falso 
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
		
		switch (say)
		{
			case TrucoPlay.CANTO_ENVIDO:
//				while (bad) try
//				{
//					strPts = Util.lineInput("Puntos: ", 5);
//					bad = false;
//					intPts = Integer.parseInt(strPts);
					intPts = tGame.getValueOfEnvido(actualPlayer);
//					tEventMan.fireChatMessageRequested
//					(
//						actualPlayer, Integer.toString(intPts)
//					);
					tGame.play(new TrucoPlay(actualPlayer, (byte) say, intPts));
//				}
//				catch (InvalidPlayExcepcion ex) {}
				break;
//			case TrucoPlay.CERRARSE:
//				tGame.play(new TrucoPlay(actualPlayer, say));
//				break;
			
			default:
//				tEventMan.fireChatMessageRequested
//				(
//					actualPlayer,
//					(String) Table.pNames.get(new Byte((byte) say))
//				);
				tGame.play(new TrucoPlay(actualPlayer, say));
				break;
		}
	}

	/** 
	 * Muestra las señas a un cierto 
	 * jugador si se clickeó en él
	 */
	protected boolean sendSignsIfClick(int x, int y)
	{
		int pos;
		Player player;
		
		// obtiene la posición de jugador al 
		// cual se debe enviar las señas
		pos = fManager.getPosIfClick(x, y);
		
		// si no se hizo click en ninguno, salir con falso
		if (pos == -1) return false;
		
		// si es la posición del contrincante, salir
		if (pos % 2 == 0) return false;
		
		// obtiene el jugador sentado en esa posición
		player = pManager.getPlayer(pManager.getChair(pos + 1));
		
		// si no hay señas, informa a todos los 
		// TableListeners registrados la seña "seca"
		if (signs.size() == 0) tEventMan.fireSignSendRequest
		(
			player, Sign.SECA
		);
		// si las hay informa a todos los 
		// TableListeners registrados las señas
		else for (int i = 0; i < signs.size(); i++)
			tEventMan.fireSignSendRequest
			(
				player, ((Integer) signs.get(i)).intValue()
			);
		
		return true;
	}
	
    /**
     * Muestra la seña emitida por un cierto jugador
     */	
    public void showSign(TableEvent event)
    {
    	Player src, dest;
    	
    	// obtiene el Player emisor y el receptor
    	src = event.getTable().getPlayer();
    	dest = event.getPlayer();
    	
    	// si el destinatario no soy yo, salir
    	if (dest != actualPlayer) return;
    	
    	// muestra la seña
    	fManager.showSign
    	(
    		pManager.getPos(src) - 1, event.getValue()
    	);
    }

    /**
     * Agrega una seña a emitir posteriormente
     */
    protected void addSign(int sign)
    {
    	// verificaciones
    	Util.verifParam(Sign.isSign(sign), "Seña inválida");
    	
    	signs.add(new Integer(sign));
    }
    
    /**
     * Limpia las señas
     */
    protected void clearSigns() { signs.clear(); }
    
    public void sendChatMessage(Player player, String htmlMessage) 
    {
        tEventMan.fireChatMessageRequested(player, htmlMessage);
    }
    
    public void showChatMessage(Player player, String htmlMessage) 
    {
    	int pos;
    	
        jtTable.jpChat.showChatMessage(player, htmlMessage);
        
        if (pManager.isSitted(player))
        {
        	pos = pManager.getPos(player);
        	fManager.pushText
	        (
	        	pos, htmlMessage, false
	        );
	    }
        tEventMan.fireChatMessageSent(player, htmlMessage);
    }
    
    public boolean isHost() { return host; }
    
    /** Retorna el animador */
    protected Animator getAnimator() { return animator; }
    
    /** Retorna el manejador de cartas */
    protected CardManager getCManager() { return cManager; }
    
    /** Retorna el manejador de jugadores */
    protected PlayerManager getPManager() { return pManager; }

    /** Retorna el manejador de caritas */
    protected FaceManager getFManager() { return fManager; }
    
    /**
     * Retorna el puntaje de un equipo
     * @param index		índice del equipo
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
	
	private static Hashtable getPNames()
	{
		Hashtable ret;
		
		ret = new Hashtable();
		ret.put(new Byte(TrucoPlay.ENVIDO), "Envido");
		ret.put(new Byte(TrucoPlay.REAL_ENVIDO), "Real Envido");
		ret.put(new Byte(TrucoPlay.FALTA_ENVIDO), "Falta Envido");
		ret.put(new Byte(TrucoPlay.FLOR), "Flor");
		ret.put(new Byte(TrucoPlay.TRUCO), "Truco");
		ret.put(new Byte(TrucoPlay.QUIERO), "Quiero");
		ret.put(new Byte(TrucoPlay.RETRUCO), "Quiero Retruco");
		ret.put(new Byte(TrucoPlay.VALE_CUATRO), "Quiero Vale 4");
		ret.put(new Byte(TrucoPlay.NO_QUIERO), "No quiero");
		ret.put(new Byte(TrucoPlay.CANTO_ENVIDO), "Cantar puntos");
		ret.put(new Byte(TrucoPlay.PASO_ENVIDO), "Paso envido");
		ret.put(new Byte(TrucoPlay.CERRARSE), "Cerrarse");
		
		return ret;
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
		String text;
		JButton source;
		
		source = (JButton) e.getSource();
		text = source.getText();
		if (text.equals("Iniciar"))
		{
//    		source.setEnabled(false);
    		tEventMan.fireGameStartRequest();
    	}
		if (text.equals("Repartir"))
			tGame.startHand(actualPlayer);
		if (text.equals("Finalizar"))
			tEventMan.fireGameFinished();
	}
	
	/**
     * Muestra las cartas de un player 
     * @param chair		Silla donde está el player
     */
	public void showPlayed(int chair)
	{
		cManager.showPlayed(pManager.getPos(chair));
	}
	
	/**
     * Retorna la mesa de juego
     */
	public JTrucoTable getJTrucoTable()
	{
		return jtTable;
	}
	
	
}

