package py.edu.uca.fcyt.toluca.table;


import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.toluca.event.*;
import py.edu.uca.fcyt.game.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * Maneja el juego de truco
 */
public class Table implements MouseListener, PTableListener, ChatPanelContainer {
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
    protected Player actualPlayer; 		 // jugador actual
    protected int tableNumber;
    
    // listeners de eventos de mesa
    protected TableEventManager tableEventMan;
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
            pTable.addFace(new Face(i, 6, "Player's Nick Name Ultra"));
        
    }
    
    /** Crea un Table asociado con 'player' */
    public Table(Player player, boolean host) {
        this();
        actualPlayer = player;
        tableEventMan = new TableEventManager(this);
        trListener = new TrListener(this, player);
        
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
        tableEventMan.addListener(t);
    }
    
    /** Registra un TrucoListener */
    public void addTrucoListener(TrucoListener t) {
        trListener.addListener(t);
    }
    
    /** Inicia el juego de esta mesa con Game 'game'*/
    public void startGame(TrucoGame game) {
        Player player;
        
        // verificaciones
        Util.verif(actualPlayer != null, "No hay jugador actual");
        Util.verif(game != null, "TrucoGame nulo");
        Util.verif(players != null, "Vector de Players nulo");
        
        System.out.println("Starting game of " + actualPlayer.getName() + "...");
        
        // actualiza el TrucoGame actual y carga su TrucoListener
        tGame = game;
        tGame.addTrucoListener(trListener);
        
        // quita todas las caritas
        pTable.removeFaces();
        
        pManager.controlStartGame();
        
        for (int i = 1; i < pManager.getPlayerCount(); i++) {
            player = pManager.getPlayer(pManager.getChair(i));
            pTable.addFace(new Face
            (
            i, pManager.getPlayerCount(),
            player.getName()
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
    public Player getOwner() {
        return actualPlayer;
    }
    
    
    /** Inicia el juego */
    public void mouseClicked(MouseEvent e) {
        tableEventMan.fireGameStartRequest(null);
    }
    
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    
    
    public void mouseClicked(float x, float y, MouseEvent e) {
        sitIfClick(e.getX(), (int) e.getY());
        
        
        //		if (e.getButton() == e.BUTTON1)
        //		{
        //			if (!cManager.playCardIfClick(x, y))
        //				cManager.showPlayedIfClick(x, y);
        //		}
        //		else
        //		{
        //	        pTrucoPlays.show(pTable, e.getX(), e.getY());
        //		}
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
                
                tableEventMan.fireSitRequested(actualPlayer, i);
                return true;
            }
        }
        
        return false;
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