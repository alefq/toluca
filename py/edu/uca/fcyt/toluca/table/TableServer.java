/*
 * TableServer.java
 *
 * Created on 4 de junio de 2003, 07:22 PM
 */

package py.edu.uca.fcyt.toluca.table;

import java.util.Iterator;
import java.util.Vector;

import py.edu.uca.fcyt.game.ChatPanelContainer;
import py.edu.uca.fcyt.toluca.event.TableEvent;
import py.edu.uca.fcyt.toluca.event.TableListener;
import py.edu.uca.fcyt.toluca.event.TrucoEvent;
import py.edu.uca.fcyt.toluca.event.TrucoListener;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;
import py.edu.uca.fcyt.toluca.game.TrucoTeam;
import py.edu.uca.fcyt.toluca.game.TrucoGame;
/**
 *
 * @author  PABLO JAVIER
 */
public class TableServer  implements TrucoListener, ChatPanelContainer {
    
    /** Holds value of property host. */
    private TrucoPlayer host;
    
    protected Vector tableListeners; // of type Vector
    protected Vector players;
    
    protected PlayerManager pManager;
    
    /** Holds value of property tableNumber. */
    private int tableNumber;
    private TrucoGame tGame;
    
    private static int nextTableNumber = 0;
    
    /** Creates a new instance of TableServer */
    public TableServer(TrucoPlayer host) {
        this.host= host;
        tableListeners = new Vector();
        players = new Vector();
        pManager = new PlayerManager(6);
        addPlayer(host);
        setHost(host);
        setTableNumber(nextTableNumber++);
        System.out.println("EL TABLE NUMBER SETEADO ES: " + getTableNumber());
        System.out.println("El HOST de la tabela es: " + getHost().getName());
    }
    
    public void addTableServerListener(TableListener tableListener) {        /**
     * lock-end */
        tableListeners.add(tableListener);
    } // end addRoomListener        /** lock-begin */
    
    public void sitPlayer(TrucoPlayer player, int chair) {
        pManager.sitPlayer(player, chair);
        firePlayerSat(player, chair);
        
        if (player == getHost())
            pManager.setActualPlayer(getHost());
        
        System.out.println(player.getName() + " sitted in server chair " + chair + " in table of " + getHost().getName());
    }
    
    public void startGame() {
        
        //TrucoGame tGame;
        TrucoTeam tTeams[];
        
        System.out.println("Requesting game start...");
        tTeams = createTeams();
        
        // se crea el TrucoGame con los teams creados
        tGame = new TrucoGame(tTeams[0], tTeams[1]);
        tGame.addTrucoListener(this);  

		//primero disparamos el evento, asi los cc se registran
		// como listeners del tgame
		fireGameStarted(
		new TableEvent(TableEvent.EVENT_gameStarted, this, null, null,-1)
		);

        //empieza realmente el juego y se disparan los eventos correspondientes
        tGame.startGame();
        
    }
    
    public TrucoTeam[] createTeams() {
        TrucoTeam tTeams[];
        TrucoPlayer player;
        
        // se crean los teams
        tTeams = new TrucoTeam[] {
            new TrucoTeam("Rojo"),
            new TrucoTeam("Azul")
        };
        
        // se agregan los players a los teams
        for (int i = 0; i < 6; i++) {
            player = pManager.getPlayer(i);
            if (player != null) {
                tTeams[i % 2].addPlayer(player);
            }
        }
        
        return tTeams;
    }
    
    
    public void cardsDeal(TrucoEvent event) {
    }
    
    public void endOfGame(TrucoEvent event) {
    }
    
    public void endOfHand(TrucoEvent event) {
    }
    
    public void gameStarted(TrucoEvent event) {
    }
    
    public TrucoPlayer getAssociatedPlayer() {
        return host;
    }
    
    public void handStarted(TrucoEvent event) {
        // IGNORAR en el lado del servidor
    }
    
	public void play(TrucoEvent event) {
		// IGNORAR en el lado del servidor
		// El TableServer no sirve absolutamente para nada
	}

	public void playResponse(TrucoEvent event) {
		// IGNORAR en el lado del servidor
		// El TableServer no sirve absolutamente para nada
	}
    
    public void turn(TrucoEvent event) {
        // IGNORAR en el lado del servidor
    }
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * @param player ...
     * </p><p>
     * @param htmlMessage ...
     * </p><p>
     *
     * </p>
     *
     */
    public void sendChatMessage(TrucoPlayer player, String htmlMessage) {
        fireChatMessageSent(player, htmlMessage);
    }
    
    /**
     * Dispara el evento de chatMessageSent
     */
    protected void fireChatMessageSent(TrucoPlayer jogador, String htmlMessage) {
        Iterator iter = tableListeners.listIterator();
        int i =0;
        while(iter.hasNext()) {
            TableListener ltmp = (TableListener)iter.next();
            System.out.println(jogador.getName() + " enviando message sent al listener #" + (i++) + " clase:" + ltmp.getClass().getName());
            ltmp.chatMessageSent(this, jogador, htmlMessage);
        }
    }
    
    protected void fireGameStarted(TableEvent te) {
        Iterator iter = tableListeners.listIterator();
        
        while(iter.hasNext()) {
            TableListener ltmp = (TableListener)iter.next();
            System.out.println("VOY A DISPARAR UN GAME STARTED EN EL SERVA. -> " + ltmp.getClass().getName());
            ltmp.gameStarted(te);
        }
    }
    
    protected void firePlayerSat(TrucoPlayer jogador, int chair ) {
        Iterator iter = tableListeners.listIterator();
        int i =0;
        while(iter.hasNext()) {
            TableListener ltmp = (TableListener)iter.next();
            System.out.println(jogador.getName() + " enviando message sent al listener #" + (i++) + " clase:" + ltmp.getClass().getName());
            TableEvent te= new TableEvent(TableEvent.EVENT_playerSit,this, jogador, null,chair);
            ltmp.playerSit(te);
        }
    }
    
    public void showChatMessage(TrucoPlayer player, String htmlMessage) {
    }
    
    /** Getter for property host.
     * @return Value of property host.
     *
     */
    public TrucoPlayer getHost() {
        return this.host;
    }
    
    /** Setter for property host.
     * @param host New value of property host.
     *
     */
    public void setHost(TrucoPlayer host) {
        this.host = host;
    }
    
    public void addPlayer(TrucoPlayer player) 
    {
        players.add(player);
    }
    
    /** Getter for property players.
     * @return Value of property players.
     *
     */
    public java.util.Vector getPlayers() {
        return players;
    }
    
    /** Setter for property players.
     * @param players New value of property players.
     *
     */
    public void setPlayers(java.util.Vector players) {
        this.players = players;
    }
    
    /** Getter for property tableNumber.
     * @return Value of property tableNumber.
     *
     */
    public int getTableNumber() {
        return this.tableNumber;
    }
    
    /** Setter for property tableNumber.
     * @param tableNumber New value of property tableNumber.
     *
     */
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
    
    public String getOrigin() {
        return String.valueOf(getTableNumber());
    }
    
    public TrucoGame getTrucoGame() {
        return tGame;
    }

	public void standPlayer(TableEvent event)
	{
		pManager.standPlayer(event.getValue());

		// avisa que el player se levantó correctamente
		for (int i = 0; i < tableListeners.size(); i++)
			((TableListener) tableListeners.get(i)).playerStanded(event);
	}

	public void showSign(TableEvent event)
	{
		for (int i = 0; i < tableListeners.size(); i++)
			((TableListener) tableListeners.get(i)).signSent(event);
	}

	public void kickPlayer(TrucoPlayer tptmp) 
	{
		// TODO Auto-generated method stub
		System.out.println("Se fue: " + tptmp.getName());
		getPlayers().remove(tptmp);
		
		TableEvent te = new TableEvent(TableEvent.EVENT_playerKicked, this, tptmp, null,0);
		firePlayerKicked(te);
	}

	private void firePlayerKicked(TableEvent te) {
		Iterator iter = tableListeners.listIterator();
		int i =0;
		while(iter.hasNext()) {
			TableListener ltmp = (TableListener)iter.next();
			ltmp.playerKicked(te);
		}
	}
	
	public PlayerManager getPlayerManager()
	{
		return pManager;
	}
	
	public int getChair(TrucoPlayer p)
	{
		return pManager.getChair(p);
	}
}
