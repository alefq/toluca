/*
 * Created on Sep 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package py.edu.uca.fcyt.toluca.net;

import java.util.HashMap;
import java.util.Iterator;

import py.edu.uca.fcyt.game.ChatMessage;
import py.edu.uca.fcyt.toluca.RoomClient;
import py.edu.uca.fcyt.toluca.event.RoomEvent;
import py.edu.uca.fcyt.toluca.event.TableEvent;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;
import py.edu.uca.fcyt.toluca.table.Table;
import py.edu.uca.fcyt.toluca.table.TableServer;
import sun.rmi.runtime.GetThreadPoolAction;

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
	private CommunicatorClient commClient;

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
		
		System.out.println("El vector de table vale ");
		System.out.println(event.getTablesServers().length);
		TableServer [] tables=event.getTablesServers();
		for(int i=0;i<tables.length;i++)
		{
			
			if(tables[i]!=null)
			{
				TrucoPlayer playerOwner=tables[i].getHost();//este player es igual al playerCreador, solo que el playerCreador es la ref en el cliente
				TrucoPlayer playerCreador=room.getPlayer(playerOwner.getName());
				addTable(playerCreador,tables[i].getTableNumber());
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

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerLeft(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void playerLeft(RoomEvent event) {

		System.out.println(" salioooooo "+event.getPlayer());
		TrucoPlayer playerServer=event.getPlayer();
		
		room.removePlayer(room.getPlayer(playerServer.getName()));
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#loginFailed(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void loginFailed(RoomEvent event) {
		
		((RoomClient)room).loginFailed(event.getErrorMsg());
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#chatRequested(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void chatRequested(RoomEvent event) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#chatSend(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void chatSend(RoomEvent event) {
		ChatMessage chatMessage=event.getChatMessage();
		if(chatMessage.getOrigin().equals("room"))
			room.showChatMessage(chatMessage.getPlayer(),chatMessage.getHtmlMessage());
		
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#createTableRequest(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void createTableRequest(RoomEvent event) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return Returns the commClient.
	 */
	public CommunicatorClient getCommClient() {
		return commClient;
	}
	/**
	 * @param commClient The commClient to set.
	 */
	public void setCommClient(CommunicatorClient commClient) {
		this.commClient = commClient;
	}
	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#tableCreated(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void tableCreated(RoomEvent event) {
		
		TableServer tableServer=event.getTableServer();
		TrucoPlayer playerOwner=tableServer.getHost();//este player es igual al playerCreador, solo que el playerCreador es la ref en el cliente
		TrucoPlayer playerCreador=room.getPlayer(playerOwner.getName());
		addTable(playerCreador,tableServer.getTableNumber());
		
		
	}
	private void addTable(TrucoPlayer playerCreador,int tableNumber)
	{
		Table table=null;
		boolean mostrar=false;
		if(playerCreador.getName().equals(trucoPlayer.getName()))
		{//CREO EL PLAYER QUE ACABA DE RESIVIR EL MSG
			
			table=new Table(playerCreador,true);
			mostrar=true;
		}
		else
		{//fue otro el que creo
			table=new Table(playerCreador,false);
		}
		
		table.setTableNumber(tableNumber);
		System.out.println("El sila le asigna "+table.getTableNumber());
		table.addTableListener(commClient);
		room.addTable(table);
		table.addPlayer(playerCreador);
		
		if(mostrar)
			table.show();
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#tableJoinRequested(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void tableJoinRequested(RoomEvent event) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#tableJoined(py.edu.uca.fcyt.toluca.event.RoomEvent)
	 */
	public void tableJoined(RoomEvent event) {

			TableServer tableServer=event.getTableServer();
			TrucoPlayer playerServer=event.getPlayer();
			
			Table table= room.getTable(tableServer.getTableNumber());
			TrucoPlayer playerClient=room.getPlayer(playerServer.getName());
			
			table.addPlayer(playerClient);
			if(playerClient.getName().equals(trucoPlayer.getName()))
			{
				table.show();
			}
			if(playerClient==trucoPlayer)
					System.out.println("andaaaaaaaaaaaaaaaa bien la ref");
			else
					System.out.println("waaaaaaaaaaaaring no anda la ref");
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerSitRequest(py.edu.uca.fcyt.toluca.event.TableEvent)
	 */
	public void playerSitRequest(TableEvent event) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerSit(py.edu.uca.fcyt.toluca.event.TableEvent)
	 */
	public void playerSit(TableEvent event) {
		
		System.out.println("Se resive un player sit a la mesa "+event.getTableServer().getTableNumber());
		System.out.println("player "+event.getPlayer()[1]);
		System.out.println("chair "+event.getValue());
		
		TableServer tableServer=event.getTableServer();
		TrucoPlayer playerServer=event.getPlayer()[1];
		int chair=event.getValue();
		
		Table table=room.getTable(tableServer.getTableNumber());
		TrucoPlayer playerClient=room.getPlayer(playerServer.getName());
		table.sitPlayer(playerClient,chair);
		
	}
}
