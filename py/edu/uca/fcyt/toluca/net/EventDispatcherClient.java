/*
 * Created on Sep 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package py.edu.uca.fcyt.toluca.net;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import py.edu.uca.fcyt.game.ChatMessage;
import py.edu.uca.fcyt.toluca.RoomClient;
import py.edu.uca.fcyt.toluca.event.RoomEvent;
import py.edu.uca.fcyt.toluca.event.TableEvent;
import py.edu.uca.fcyt.toluca.event.TrucoEvent;
import py.edu.uca.fcyt.toluca.game.TrucoCard;
import py.edu.uca.fcyt.toluca.game.TrucoGameClient;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;
import py.edu.uca.fcyt.toluca.game.TrucoTeam;

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
				System.out.println("login ok dentro de tables ");
				TrucoPlayer playerOwner=tables[i].getHost();//este player es igual al playerCreador, solo que el playerCreador es la ref en el cliente
				TrucoPlayer playerCreador=room.getPlayer(playerOwner.getName());
				Table table=addTable(playerCreador,tables[i].getTableNumber());
				

				Vector vec=tables[i].getPlayers();
				Iterator iterator=vec.iterator();
				
				System.out.println("Los player de la taba "+tables[i].getTableNumber());
				
				
				while(iterator.hasNext())
				{
					TrucoPlayer playerClient=room.getPlayer(((TrucoPlayer)iterator.next()).getName());
					if(!playerClient.getName().equals(playerCreador.getName()))
					{
						table.addPlayer(playerClient);
						
					}
					Integer asiento=tables[i].getAsiento(playerClient);
					if(asiento!=null)
					{
						System.out.println("En el login ya se hace un sitPlayer de"+playerClient +" en la chair "+asiento);
						table.sitPlayer(playerClient,asiento.intValue());
						((RoomClient)room).setearPlayerTable(playerClient,table,asiento.intValue());
					}
				}
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
		System.out.println(getClass().getName()+"Se resivio un chat message "+chatMessage.getOrigin());
		if(chatMessage.getOrigin().equals("room"))
		{
			room.showChatMessage(chatMessage.getPlayer(),chatMessage.getHtmlMessage());
		}
		else
		{
			System.out.println("un chat para la mesa "+event.getTableNumber());
			Table table=room.getTable(event.getTableNumber());
			
			TrucoPlayer playerServer=chatMessage.getPlayer();
			TrucoPlayer playerClient=room.getPlayer(playerServer.getName());
			table.showChatMessage(playerClient,chatMessage.getHtmlMessage());
			
		}
		
		
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
	private Table addTable(TrucoPlayer playerCreador,int tableNumber)
	{//crea una Tabla y agrega al room
		Table table=null;
		boolean mostrar=false;
		if(playerCreador.getName().equals(trucoPlayer.getName()))
		{//CREO EL PLAYER QUE ACABA DE RESIVIR EL MSG
			
			table=new Table(trucoPlayer,true);
			mostrar=true;
		}
		else
		{//fue otro el que creo
			table=new Table(trucoPlayer,false);
		}
		
		table.setTableNumber(tableNumber);
		
		table.addTableListener(commClient);
		room.addTable(table);
		table.addPlayer(playerCreador);
		
		if(mostrar)
			table.show();
		return table;
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
		
		System.out.println("player sit");
		System.out.println("Se resive un player sit a la mesa "+event.getTableServer().getTableNumber());
		System.out.println("player "+event.getPlayer()[0]);
		System.out.println("chair "+event.getValue());
		TableServer tableServer=event.getTableServer();
		TrucoPlayer playerServer=event.getPlayer()[0];
		
		int chair=event.getValue();
		
		Table table=room.getTable(tableServer.getTableNumber());
		TrucoPlayer playerClient=room.getPlayer(playerServer.getName());
		
		System.out.println("La silla de  "+playerClient.getName()+" es "+table.getChair(playerClient));
		table.sitPlayer(playerClient,chair);
		System.out.println("ya se hizo el table.sitPlayer");
		((RoomClient)room).setearPlayerTable(playerClient,table,chair);
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerStandRequest(py.edu.uca.fcyt.toluca.event.TableEvent)
	 */
	public void playerStandRequest(TableEvent event) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerStand(py.edu.uca.fcyt.toluca.event.TableEvent)
	 */
	public void playerStand(TableEvent event) {
		
		System.out.println("player staaaaanded");
		System.out.println("Se resive un player stand a la mesa "+event.getTableServer().getTableNumber());
		System.out.println("player "+event.getPlayer()[0]);
		System.out.println("chair "+event.getValue());
		
		TableServer tableServer=event.getTableServer();
		TrucoPlayer playerServer=event.getPlayer()[0];
		
		int chair=event.getValue();
		
		Table table=room.getTable(tableServer.getTableNumber());
		TrucoPlayer playerClient=room.getPlayer(playerServer.getName());
		
		table.standPlayer(chair);
		((RoomClient)room).setStandPlayer(chair,table);
		
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerKickRequest(py.edu.uca.fcyt.toluca.event.TableEvent)
	 */
	public void playerKickRequest(TableEvent event) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerKicked(py.edu.uca.fcyt.toluca.event.TableEvent)
	 */
	public void playerKicked(TableEvent event) {
		
		
		TableServer tableServer=event.getTableServer();
		TrucoPlayer playerServer=event.getPlayer()[0];
		
		Table table=room.getTable(tableServer.getTableNumber());
		TrucoPlayer playerClient=room.getPlayer(playerServer.getName());
		
		table.kickPlayer(playerClient);
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerLeft(py.edu.uca.fcyt.toluca.event.TableEvent)
	 */
	public void playerLeft(TableEvent event) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#gameStartRequest(py.edu.uca.fcyt.toluca.event.TableEvent)
	 */
	public void gameStartRequest(TableEvent event) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#gameStarted(py.edu.uca.fcyt.toluca.event.TableEvent)
	 */
	public void gameStarted(TableEvent event) {

		System.out.println("Llego un gameStarted");
		TableServer tableServer=event.getTableServer();
		Table table=room.getTable(tableServer.getTableNumber());
		
		
		TrucoTeam[] trucoTeam=table.createTeams();
		TrucoGameClient trucoGameClient=new TrucoGameClient(trucoTeam[0],trucoTeam[1]);
		trucoGameClient.addTrucoListener(commClient);
		
		trucoGameClient.startGameClient();
		table.startGame(trucoGameClient);
	}

	public void infoGame(TrucoEvent event) {
		System.out.println(" info del juego ");
		TrucoPlayer playerServer=event.getPlayer();
		TrucoPlayer playerClient=room.getPlayer(playerServer.getName());
		Table table=room.getTable(event.getTableNumber());
		TrucoGameClient trucoGameClient=(TrucoGameClient) table.getTGame();
		trucoGameClient.play(event);
		
	}
	public void receiveCards(TrucoEvent event) {

		
		int tableId=event.getTableNumber();
		System.out.println("Se reciben las cartas para el juego de la tabla "+tableId);
		System.out.println(" cards "+event.getCards().length);
		System.out.println(" hand "+event.getNumberOfHand());
		System.out.println(" cartas del player "+event.getPlayer().getName());
		TrucoPlayer playerServer=event.getPlayer();
		TrucoPlayer playerClient=room.getPlayer(playerServer.getName());
		Table table=room.getTable(tableId);
		TrucoCard[] cards=event.getCards();
		System.out.println("Se resiven las cartas");
		for(int i=0;i<cards.length;i++)
		{
			System.out.println(cards[i].getKind() + " "+cards[i].getValue());
		}
		TrucoGameClient trucoGameClient=(TrucoGameClient) table.getTGame();
		trucoGameClient.recibirCartas(playerClient,event.getCards());
	}

}
