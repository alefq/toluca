package py.edu.uca.fcyt.toluca.net;

import org.apache.log4j.Logger;


import py.edu.uca.fcyt.game.ChatMessage;
import py.edu.uca.fcyt.toluca.LoginFailedException;
import py.edu.uca.fcyt.toluca.RoomServer;
import py.edu.uca.fcyt.toluca.event.RoomEvent;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;
import py.edu.uca.fcyt.toluca.table.TableServer;
import sun.rmi.runtime.GetThreadPoolAction;


/**
 * @author dcricco
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EventDispatcherServer extends EventDispatcher{
	static Logger logger = Logger.getLogger(EventDispatcherServer.class);
	
	private CommunicatorServer communicatorServer;
	
	public EventDispatcherServer(CommunicatorServer communicatorServer)
	{
		this.communicatorServer=communicatorServer;
	}
	public void loginRequested(RoomEvent event) {
		logger.debug("Se resivio un login Requested");
		try {
			((RoomServer)room).login(event.getUser(),event.getPassword(),communicatorServer);
		} catch (LoginFailedException e) {
			logger.info("Fallo el logeo");
		}
	}
	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#loginCompleted(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void loginCompleted(RoomEvent event) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerJoined(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void playerJoined(RoomEvent event) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerLeft(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void playerLeft(RoomEvent event) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#loginFailed(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void loginFailed(RoomEvent event) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#chatRequested(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void chatRequested(RoomEvent event) {
 
		ChatMessage chatMsg=event.getChatMessage();
		logger.debug("Llego un chat Requested de :");
		logger.debug(chatMsg.getPlayer());
		logger.debug(chatMsg.getHtmlMessage());
		if(chatMsg.getOrigin().equals("room"))
			room.sendChatMessage(chatMsg.getPlayer(),chatMsg.getHtmlMessage());
	}
	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#chatSend(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void chatSend(RoomEvent event) {
		
		
	}
	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#createTableRequest(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void createTableRequest(RoomEvent event) {
		logger.debug("Llego un Create Table");
		
		TrucoPlayer player=communicatorServer.getTrucoPlayer();
		((RoomServer)room).createTable(player);
		
	}
	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#tableCreated(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void tableCreated(RoomEvent event) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#tableJoinRequested(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void tableJoinRequested(RoomEvent event) {
	
			logger.debug("Llego una solicitud para unirse a la mesa"+event.getTableNumber());
			logger.debug("La solicitud fue de "+event.getPlayer());
			
			TableServer tableServer= room.getTableServer(event.getTableNumber());
			
			
			RoomEvent e=new RoomEvent();
			e.setType(RoomEvent.TYPE_TABLE_JOIN_REQUESTED);
			e.setPlayer(communicatorServer.getTrucoPlayer());
			e.setTableServer(tableServer);
			((RoomServer)room).joinTable(e);
	
	}
	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#tableJoined(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void tableJoined(RoomEvent event) {
		// TODO Auto-generated method stub
		
	}
}
