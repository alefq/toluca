package py.edu.uca.fcyt.toluca.net;

import org.apache.log4j.Logger;


import py.edu.uca.fcyt.toluca.LoginFailedException;
import py.edu.uca.fcyt.toluca.RoomServer;
import py.edu.uca.fcyt.toluca.event.RoomEvent;


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
	
	
}
