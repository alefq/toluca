package py.edu.uca.fcyt.toluca.net;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jdom.Element;

import py.edu.uca.fcyt.toluca.RoomServer;
import py.edu.uca.fcyt.toluca.event.RoomEvent;
import py.edu.uca.fcyt.toluca.event.TableEvent;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;





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
		event.getTableServer().addTableServerListener(this);
		
		super.sendXmlPackage(event);
		
	}
	
	
	
	//EVENTOS DE LA TABLA
	public void playerSit(TableEvent event) {
		logger.debug("Player sit");
		super.sendXmlPackage(event);
	}
}
