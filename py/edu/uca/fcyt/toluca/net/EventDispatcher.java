/*
 * Created on Sep 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package py.edu.uca.fcyt.toluca.net;

import py.edu.uca.fcyt.toluca.Room;
import py.edu.uca.fcyt.toluca.event.RoomEvent;
import py.edu.uca.fcyt.toluca.event.TableEvent;
import py.edu.uca.fcyt.toluca.event.TrucoEvent;
import py.edu.uca.fcyt.toluca.game.TrucoPlay;

/**
 * @author dcricco
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class EventDispatcher {

	protected Room room;
	public void dispatchEvent(RoomEvent event)
	{
	System.out.println("DispatchEvent: Se resive un evento RoomEvent");
		switch(event.getType())
		{
			case RoomEvent.TYPE_CHAT_REQUESTED:chatRequested(event);break;
			case RoomEvent.TYPE_CHAT_SENT:chatSend(event);break;
			case RoomEvent.TYPE_CREATE_TABLE_REQUESTED:createTableRequest(event);break;
			case RoomEvent.TYPE_LOGIN_COMPLETED:loginCompleted(event);break;
			case RoomEvent.TYPE_LOGIN_FAILED:loginFailed(event);break;
			case RoomEvent.TYPE_LOGIN_REQUESTED: loginRequested(event);break;
			case RoomEvent.TYPE_PLAYER_JOINED:playerJoined(event);break;
			case RoomEvent.TYPE_PLAYER_KICKED:break;
			case RoomEvent.TYPE_PLAYER_LEFT: playerLeft(event);break;
			case RoomEvent.TYPE_TABLE_CREATED: tableCreated(event);break;
			case RoomEvent.TYPE_TABLE_CREATED_SERVER:break;
			case RoomEvent.TYPE_TABLE_JOIN_REQUESTED:tableJoinRequested(event);break;
			case RoomEvent.TYPE_TABLE_JOINED:tableJoined(event);break;
			
		}
	}
	public void dispatchEvent(TableEvent event)
	{
		switch(event.getEvent())
		{
			case TableEvent.EVENT_gameFinished:break;
			case TableEvent.EVENT_gameStarted:break;
			case TableEvent.EVENT_gameStartRequest:break;
			case TableEvent.EVENT_playerKicked:playerKicked(event);break;
			case TableEvent.EVENT_playerKickRequest:playerKickRequest(event);break;
			case TableEvent.EVENT_playerLeft:playerLeft(event);break;
			case TableEvent.EVENT_playerSit:playerSit(event);break;
			case TableEvent.EVENT_playerSitRequest: playerSitRequest(event);break;
			case TableEvent.EVENT_playerStanded:playerStand(event);break;
			case TableEvent.EVENT_playerStandRequest:playerStandRequest(event);break;
			case TableEvent.EVENT_showPlayed:break;
			case TableEvent.EVENT_signSendRequest:break;
			case TableEvent.EVENT_signSent:break;
			case TableEvent.EVENT_tableLocked:break;
			case TableEvent.EVENT_tableUnlocked:break;
			
		}
	}
	public void dispatchEvent(TrucoEvent event)
	{
		
	}
	public void dispatchEvent(TrucoPlay event)
	{
		
	}
	
	/**
	 * @return Returns the room.
	 */
	public Room getRoom() {
		return room;
	}
	/**
	 * @param room The room to set.
	 */
	public void setRoom(Room room) {
		this.room = room;
	}
	
	
	//ACA VIENENLOS METODOS QUE SE TIENEN QUE IMPLEMENTAR EN LOS HIJOS PARA DESPACHAR LOS EVENTOS
	
	
	//METODOS CORRESPONDIENTES AL ROOM_EVENT
	public abstract void loginRequested(RoomEvent event);
	public abstract void loginCompleted(RoomEvent event);
	public abstract void playerJoined(RoomEvent event);
	public abstract void playerLeft(RoomEvent event);
	public abstract void loginFailed(RoomEvent event);
	public abstract void chatRequested(RoomEvent event);
	public abstract void chatSend(RoomEvent event);
	public abstract void createTableRequest(RoomEvent event);
	public abstract void tableCreated(RoomEvent event);
	public abstract void tableJoinRequested(RoomEvent event);
	public abstract void tableJoined(RoomEvent event);
	
	
	public abstract void playerSitRequest(TableEvent event);
	public abstract void playerSit(TableEvent event);
	public abstract void playerStandRequest(TableEvent event);
	public abstract void playerStand(TableEvent event);
	public abstract void playerKickRequest(TableEvent event);
	public abstract void playerKicked(TableEvent event);
	public abstract void playerLeft(TableEvent event);
}
