package py.edu.uca.fcyt.toluca.net;

import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.jdom.Element;

import py.edu.uca.fcyt.toluca.RoomServer;
import py.edu.uca.fcyt.toluca.event.RoomEvent;
import py.edu.uca.fcyt.toluca.event.TableEvent;
import py.edu.uca.fcyt.toluca.event.TrucoEvent;
import py.edu.uca.fcyt.toluca.game.TrucoCard;
import py.edu.uca.fcyt.toluca.game.TrucoGame;
import py.edu.uca.fcyt.toluca.game.TrucoPlay;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;

import py.edu.uca.fcyt.toluca.table.TableServer;





/**
 * @author CYT UCA
 *
 * 
 */
public class CommunicatorServer extends Communicator{
	static Logger logger = Logger.getLogger(CommunicatorServer.class);
	private RoomServer roomServer;
	
	public CommunicatorServer()
	{
		super();
		setEventDispatcher(new EventDispatcherServer(this));
		
	}
	
	/**
	 * @return Returns the roomServer.
	 */
	public RoomServer getRoomServer() {
		return roomServer;
	}
	/**
	 * @param roomServer The roomServer to set.
	 */
	public void setRoomServer(RoomServer roomServer) {
		this.roomServer = roomServer;
		eventDispatcher.setRoom(roomServer);
		roomServer.addRoomListener(this);
	}
	public void receiveXmlPackage(Element xmlPackage) {
		
		System.out.println("Se resive un packohiiiiiiiii");
	}
	public void connectionFailed()
	{
		logger.info("Fallo la coneccion de "+getTrucoPlayer());
		roomServer.removePlayer(getTrucoPlayer());
		try {
			close();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}
	public void playerJoined(TrucoPlayer player) {

		logger.debug("Player joined");
		
		RoomEvent event=new RoomEvent();
		event.setType(RoomEvent.TYPE_PLAYER_JOINED);
		event.setPlayer(player);
		super.sendXmlPackage(event);
	}
	public void loginCompleted(RoomEvent event)
	{
		logger.debug("Login completed");
		super.sendXmlPackage(event);
		TableServer[] tablesServers=event.getTablesServers();
		for(int i=0;i<tablesServers.length;i++)
		{//se agrega a todas las tablas como listener
			if(tablesServers[i]!=null)
			{
				tablesServers[i].addTableServerListener(this);
			}
		}
	}
	
	public void playerLeft(RoomEvent event)
	{
		super.sendXmlPackage(event);
	}
	public void loginFailed(RoomEvent event)
	{
		super.sendXmlPackage(event);
	}
	public void tableCreated(RoomEvent event) {
	
		event.getTableServer().addTableServerListener(this);
		super.sendXmlPackage(event);
	}
	
	public void tableJoined(RoomEvent event) {
		//if(getTrucoPlayer().getName().equals(event.getPlayer().getName()))
		//	event.getTableServer().addTableServerListener(this);
		
		super.sendXmlPackage(event);
		
	}
	
	
	
	//EVENTOS DE LA TABLA
	public void playerSit(TableEvent event) {
		logger.debug("Player sit");
		super.sendXmlPackage(event);
	}
	public void playerStanded(TableEvent event) {
		super.sendXmlPackage(event);
		
	}

	
	public void chatMessageSent(RoomEvent event) {
		super.sendXmlPackage(event);
		
	}
	public void playerKicked(TableEvent event) {
		super.sendXmlPackage(event);
		
	}
	public void gameStarted(TableEvent event) {
		TrucoGame trucoGame=event.getTableServer().getTrucoGame();
		trucoGame.addTrucoListener(this);
		super.sendXmlPackage(event);
		
	}
	
	//METODOS DEL TRUCOGAME
	
	public void play(TrucoEvent event) {
		logger.info("Play"+getTrucoPlayer().getName());

		super.sendXmlPackage(event);
		
	}
	
	public void playResponse(TrucoEvent event) {
		
	logger.info("Play Response "+getTrucoPlayer().getName());	
	}
	
	public void turn(TrucoEvent event) {
		logger.info("Turn "+getTrucoPlayer().getName()+ " event type> "+event.getType());
		
		
		
	}
	
	public void endOfHand(TrucoEvent event) {
	
		logger.info("End of Hand");
	}
	
	public void cardsDeal(TrucoEvent event) {
	
		logger.info("Se envia Cards Deal");
		TrucoCard[] cards=event.getCards();
		logger.info("Las cartas de "+event.getPlayer().getName()+ " son");
		for(int i=0;i<cards.length;i++)
		{
			System.out.println(cards[i].getKind() + " "+cards[i].getValue());
		}
		super.sendXmlPackage(event);
		
	}
	
	public void handStarted(TrucoEvent event) {
	
		super.sendXmlPackage(event);
	}
	
	public void gameStarted(TrucoEvent event) {
	
		logger.info("gameStarted");
		//super.sendXmlPackage(event)
	}
	
	public void endOfGame(TrucoEvent event) {
	
		logger.info("End of game");
	}
}
