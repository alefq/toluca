package py.edu.uca.fcyt.toluca.table;


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

/**
 * Maneja el juego de truco
 */
public class Table implements MouseListener, PTableListener, ChatPanelContainer {

	protected static final int SIT = 0;
	protected static final int PLAY = 1;
	protected static final int CALL = 2;
	protected static final int WAIT = 3;

	protected int status = SIT;	// estado actual 
    protected JFrame jFrame;				 // jFrame principal
    protected JTrucoTable jtTable;		 // panel principal
    protected PlayTable pTable;			 // mesa de juego
    protected int playerCount = -1;		 // cantidad de jugadores
    protected CardManager cManager;		 // manejador de cartas
    protected PopupTrucoPlays pTrucoPlays; // popup de jugadas
    protected Vector players;
    protected TrucoGame tGame;			 // juego de truco asociado
    protected boolean host;				 // es o no el host
    protected PlayerManager pManager;	 // manejador de jugadores
    protected TrucoPlayer actualPlayer;  // jugador actual
    protected int tableNumber;
    protected TolucaAgent agente;
    // manejador de eventos de mesa
    protected TableEventManager tEventMan;
    
    // TrucoListener asociado
    protected TrListener trListener;
    
    public Vector getPlayers() { return players; }
    
    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }
    
    
    /** Construye un Table */
    public Table() {
        int i;
        
        playerCount = 6;
        
        // crea los objetos
        jtTable = new JTrucoTable(this);
        pTable = jtTable.getPlayTable();
        pTrucoPlays= new PopupTrucoPlays(this);
        pManager = new PlayerManager(6);
        
        
        // registra a este objeto como listener de
        // eventos del mouse a 'jtTable' el
        jtTable.addMouseListener(this);
        
        //		Card[][] cards = new Card[playerCount][];
        //		Card[][] played = new Card[playerCount][];
        //
        //		for (i = 0; i < playerCount; i++)
        //		{
        //			cards[i] = new Card[3];
        //			played[i] = new Card[3];
        //
        //			for (int j = 0; j < 3; j++)
        //			{
        //				played[i][j] = cards[i][j] = new Card(j+1, i+1);
        //			}
        //		}
        //
        //		cManager = new CardManager
        //		(
        //			pTable, playerCount, cards, played, 0, null, 0
        //		);
        
        players = new Vector(0, 1);
        
        // agrega las caritas a 'pTable'
        for (i = 0; i < 6; i++)
            pTable.addFace(new Face
            (
            	i, 6, "Player's Nick Name Ultra",
            	getChairColor(i)
        	));
        
    }
    
    /** Crea un Table asociado con 'player' */
    public Table(TrucoPlayer player, boolean host) 
    {
        this();
        actualPlayer = player;
        tEventMan = new TableEventManager(this);
        trListener = new TrListener(this);
        
        jFrame = new JFrame();
        jFrame.setTitle("Toluca: " + actualPlayer.getName());
        jFrame.getContentPane().add(jtTable);
        jFrame.setSize(600, 500);
    }

    /** Crea un Table asociado con 'player' */
    public Table(TrucoPlayer player, boolean host, TolucaAgent ta) 
    {
        this();
        actualPlayer = player;
        tEventMan = new TableEventManager(this);
        trListener = new TrListener(this);
        agente = ta;
        jFrame = new JFrame();
        jFrame.setTitle("Toluca: " + actualPlayer.getName());
        jFrame.getContentPane().add(jtTable);
        jFrame.setSize(600, 500);
    }

    
    
    public void show() {
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
        Player player;
        int chair;
        
        // verificaciones
        Util.verif(actualPlayer != null, "No hay jugador actual");
        Util.verif(game != null, "TrucoGame nulo");
        Util.verif(players != null, "Vector de Players nulo");
        
        System.out.println("Starting game of " + actualPlayer.getName() + "...");
        
        // actualiza el TrucoGame actual y carga su TrucoListener
        tGame = game;
        tGame.addTrucoListener(trListener);
        tGame.addTrucoListener(agente);
        // quita todas las caritas
        pTable.removeFaces();
        
        pManager.controlStartGame();
        
        for (int i = 1; i < pManager.getPlayerCount(); i++) 
        {
        	chair = pManager.getChair(i);
            player = pManager.getPlayer(chair);
            pTable.addFace(new Face
            (
	            i, pManager.getPlayerCount(),
	            player.getName(), getChairColor(chair)
            ));
            
            System.out.println("Adding face of " + player.getName());
        }
        
        cManager = new CardManager(pTable, pManager.getPlayerCount());
        cManager.createTablePlayers();
        
        pTable.drawObjects();
    }
    
    public void sitPlayer(Player player, int chair) {
        pManager.sitPlayer(player, chair);
        if (player == actualPlayer)
            pManager.setActualPlayer(player);
        
        pTable.getFace(chair).setName(player.getName());
        pTable.drawObjects();
        
        System.out.println(player.getName() + " sitted in chair " + chair + " in table of " + actualPlayer.getName());
    }
    
    /** Retorna el Player propietario de esta mesa */
    public Player getOwner() 
    {
        return actualPlayer;
    }
    
    
    /** Inicia el juego */
    public void mouseClicked(MouseEvent e) 
    {
        tEventMan.fireGameStartRequest(null);
    }
    
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    
    
    public void mouseClicked(float x, float y, MouseEvent e) 
    {
    	switch (status)
    	{
    		case SIT:
		        sitIfClick(e.getX(), (int) e.getY());
		        break;
		    
		    case PLAY:
        		if (e.getButton() == e.BUTTON1)
        		{
        			Card card;
        			
        			card = cManager.playCardIfClick(x, y);
        			
        			if (card == null)
	        			cManager.showPlayedIfClick(x, y);
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
        	        pTrucoPlays.show(pTable, e.getX(), e.getY());
        		}
        }
    }
    
    /**
     * Sienta al jugador actual en una
     * silla si ésta fue seleccionada
     */
    protected boolean sitIfClick(int x, int y) {
        int playX, playY;
        
        
        playX = (int) (x - pTable.getOffsetX());
        playY = (int) (y - pTable.getOffsetY());
        
        for (int i = 0; i < pTable.getFaceCount(); i++) {
            if (pTable.getFace(i).inside(playX, playY)) {
                int actChair;
                Player p;
                
                p = pManager.getPlayer(i);
                if (p != null && p != actualPlayer)
                    return false;
                
                actChair = pManager.getChair(actualPlayer);
                if (actChair != -1)
                    return false;
                
                tEventMan.fireSitRequested(actualPlayer, i);
                return true;
            }
        }
        
        return false;
    }
    
    protected Color getChairColor(int chair)
    {
    	if (chair % 2 == 0) return Color.RED;
    	else return Color.BLUE;
    }

	public void say(int say)
	{
		String strPts;
		int intPts;
		
		switch (say)
		{
			case TrucoPlay.CANTO_ENVIDO:
				strPts = Util.lineInput("Puntos: ", 5);
				intPts = Integer.parseInt(strPts);
				tGame.play(new TrucoPlay(actualPlayer, (byte) say, intPts));
				break;
			
			default:
				tGame.play(new TrucoPlay(actualPlayer, (byte) say));
				break;
		}
	}

	public void drawBalloon(Player player, String text)
	{
		Face face;
		
		if (player == actualPlayer) return;

		face = pTable.getFace
		(
			pManager.getPos(player) - 1
		);
	
		face.setBalloonText(text);
		pTable.drawObjects();
		Util.sleep(1000);
		face.setBalloonText(null);
		pTable.drawObjects();
		
		
	}
    	
    
    // rutina principal
    public static void main(String args[]) {
        Table table = new Table();
    }
    
    public void sendChatMessage(Player player, String htmlMessage) {
        // TODO: Add your code here
    }
    
    public void showChatMessage(Player player, String htmlMessage) {
        // TODO: Add your code here
    }
    
    public boolean isHost() { return host; }
}