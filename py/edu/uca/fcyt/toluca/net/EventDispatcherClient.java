/*
 * Created on Sep 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package py.edu.uca.fcyt.toluca.net;

import java.util.HashMap;
import java.util.Iterator;

import py.edu.uca.fcyt.toluca.RoomClient;
import py.edu.uca.fcyt.toluca.event.RoomEvent;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;

/**
 * @author dcricco
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EventDispatcherClient extends EventDispatcher{

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#loginRequested(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	private TrucoPlayer trucoPlayer;
	public void loginRequested(RoomEvent event) {

		
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#loginCompleted(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void loginCompleted(RoomEvent event) {
		
		System.out.println(" Cliente Login completed");
		HashMap jugadores=event.getPlayers();
		Iterator it=event.getPlayers().keySet().iterator();
		while(it.hasNext())
		{
			
			String keyClave=(String) it.next();
			//System.out.println(" Se va a cargar "+newPlayer);
			if(!trucoPlayer.getName().equals(keyClave))
			{
				
				room.addPlayer((TrucoPlayer) jugadores.get(keyClave));
			}
		}
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerJoined(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void playerJoined(RoomEvent event) {
		
		
		room.addPlayer(event.getPlayer());
		if(trucoPlayer==null)
		{
			trucoPlayer=event.getPlayer();
			((RoomClient)room).loginCompleted(trucoPlayer);
		}
		
	}

}
