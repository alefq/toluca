package py.edu.uca.fcyt.toluca;

import py.edu.uca.fcyt.toluca.table.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.toluca.event.*;
import py.edu.uca.fcyt.toluca.db.*;
import py.edu.uca.fcyt.toluca.net.*;
import py.edu.uca.fcyt.game.*;

/** Java class "RoomServer.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
import java.util.*;
import java.util.Vector;

import org.apache.log4j.Logger;

import org.apache.log4j.xml.DOMConfigurator;

/**
 * <p>
 *
 * </p>
 */
public class RoomServer extends Room

implements ChatPanelContainer
{
	static Logger logger = Logger.getLogger(RoomServer.class);	
	///////////////////////////////////////
	// attributes
	
	
	/**
	 * <p>
	 * Represents ...
	 * </p>
	 */
	private java.util.Properties properties;
	
	public final static int TIME_OUT=10000;
	
	/**
	 * <p>
	 * Representa a las tablas que estan activas en el servidor.
	 * </p>
	 */
	
	
	///////////////////////////////////////
	// associations
	
	/**
	 * <p>
	 *  Maneja las operaciones de base de datos del servidor.
	 * </p>
	 */
	private DbOperations dbOperations;
	
	/**
	 * <p>
	 *  Servidor de Conexiones.  Crea un CommunicatorServer por cada conexion que se recibe
	 * </p>
	 */
	
	protected ConnectionManager connManager;
	
	//private Map pendingConnections = new Collections.synchronizedMap( new HashMap() );
	private Map pendingConnections = Collections.synchronizedMap((Map)(new HashMap()));
	
	
	///////////////////////////////////////
	// operations
	
	/**
	 * <p>
	 * Does ...
	 * </p>
	 */
	public  RoomServer()
	{
		// your code here
		logger.info("Se creo el RoomServer");
		logger.info("Instanciando el ConnectionManager");
		
		connManager = new ConnectionManager(this);
		dbOperations = new DbOperations(null, null, null, this);
		//vTables = new Vector();
		//vPlayers = new Vector();
	} // end RoomServer
	
	
	
	/**
	 * <p>
	 * Does ...
	 * </p><p>
	 *
	 * </p>
	 */
	public void createTable(TrucoPlayer player)
	{
		// your code here
		logger.debug("Dentor del create table del room server: " + player.getName());
		TableServer tableServer= new TableServer(player);
		
		//tableServer.addPlayer(player);Comentado porque en el constructor del TableServer
		//ya se esta haciendo un addPlayer, osea esto esta alpedo
		//Table table = new Table(player, true);
		//vTables.add(tableServer);
		int key=getAvailableKey();
		if(key>=0)
		{
			tableServer.setTableNumber(key);
			addTable(tableServer);
			fireTableCreated(tableServer);
		}
		else
		{
			logger.info("No se pueden crear mas tablas");
		}
	} // end createTable
	
	/**
	 * <p>
	 * Does ...
	 * </p><p>
	 *
	 * </p><p>
	 *
	 * @param table ...
	 * </p>
	 */
	protected void fireTableCreated(TableServer table)
	{
		//
		//tHashTable().put(new Integer(table.getTableNumber()),table);//Agregado por Cricco 
		
		logger.debug("dentro del firetalbe created del room server" );
		//Vector players = table.getPlayers();
	//ector playerstmp = new Vector();
		//ayerstmp.add(table.getHost());
		
		
		RoomEvent re = new RoomEvent();
		re.setType(RoomEvent.TYPE_TABLE_CREATED);
		//re.setTableNumber(-108);
		//re.addTables(table);
		re.setTableServer(table);
		//re.setPlayers(playerstmp);
		
		re.setTableNumber(table.getTableNumber());
		Iterator iter = roomListeners.listIterator();
		while(iter.hasNext())
		{
			RoomListener ltmp = (RoomListener)iter.next();
			ltmp.tableCreated(re);
		}
	} // end fireTableCreated
	
	public void joinTable(RoomEvent re)
	{
		TableServer ts = re.getTableServer();
		ts.addPlayer(re.getPlayer());
		re.setType(RoomEvent.TYPE_TABLE_JOINED);
		fireTableJoined(re);
		
	}
	/**
	 * <p>
	 * Does ...
	 * </p><p>
	 *
	 * </p><p>
	 *
	 * @param table ...
	 * </p>
	 */
	private void fireTableJoined(RoomEvent re)
	{
		Iterator iter = roomListeners.listIterator();
		while(iter.hasNext())
		{
			RoomListener ltmp = (RoomListener)iter.next();
			ltmp.tableJoined(re);
		}
		
		
		
	} // end fireTableJoined
	
	/**
	 * <p>
	 * Does ...
	 * </p><p>
	 *
	 * </p><p>
	 *
	 * @param player ...
	 * </p><p>
	 * @param htmlMessage ...
	 * </p>
	 */
	public void fireChatSent(TrucoPlayer player, String htmlMessage)
	{
		// your code here
	} // end fireChatSent
	
	/**
	 * <p>
	 * Does ...
	 * </p><p>
	 *
	 * </p><p>
	 *
	 * @param player ...
	 * </p>
	 */
	//    public void firePlayerJoined(Player player) {
	//        // your code here
	//    } // end firePlayerJoined
	
	/**
	 * <p>
	 * Does ...
	 * </p><p>
	 *
	 * </p><p>
	 *
	 * @param player ...
	 * </p>
	 */
	public void removePlayer(TrucoPlayer player) {        /** lock-end */
		//le quita del vector de players
		TableServer [] tablasServers=getTablesServers();
		
		for(int i=0;i<tablasServers.length;i++)
		{
			
			
			TableServer tabela= tablasServers[i];
			if(tabela!=null)
			{
				
			
			Vector jugadores=tabela.getPlayers();
			if(jugadores.contains(player))
			{
					TableEvent te=new TableEvent(TableEvent.EVENT_playerStanded,tabela,null,null,tabela.getChair(player));
					tabela.standPlayer(te);
					tabela.kickPlayer(player);
				
			}
			}
		}
		players.remove(player.getName());
		//vPlayers.remove(player);
		firePlayerLeft(player);
	} 
	public void firePlayerLeft(TrucoPlayer player)
	{
		Iterator iter = roomListeners.listIterator();
		
		RoomEvent re = new RoomEvent();
		re.setType(RoomEvent.TYPE_PLAYER_LEFT);
		re.setPlayer(player);
		while(iter.hasNext())
		{
			RoomListener ltmp = (RoomListener)iter.next();
			ltmp.playerLeft(re);
		}
		
		// your code here
	} // end firePlayerLeft
	
	/**
	 * <p>
	 * Does ...
	 * </p><p>
	 *
	 * </p><p>
	 *
	 * @param player ...
	 * </p>
	 */
	public void firePlayerKicked(TrucoPlayer player)
	{
		// your code here
	} // end firePlayerKicked
	
	/**
	 * <p>
	 * Does ...
	 * </p><p>
	 *
	 * </p><p>
	 *
	 * @param username ...
	 * </p><p>
	 * @param password ...
	 * </p>
	 */
	public void login(String username, String password, CommunicatorServer cs) throws py.edu.uca.fcyt.toluca.LoginFailedException
	{
		// your code here
		TrucoPlayer jogador = null;
		try
		{
			
			pendingConnections.put(username, cs);
			
			//jogador = new Player("CIT", 108);
			jogador = dbOperations.authenticatePlayer(username, password);
			logger.debug("Se creo el jugador: " +  jogador.getName());
			
			cs.setTrucoPlayer(jogador);
			firePlayerJoined(jogador);
			fireLoginCompleted(jogador);
			//firePlayerJoined(jogador);
			//  return jogador;
		}
		catch (LoginFailedException le)
		{
			RoomEvent event=new RoomEvent();
			event.setType(RoomEvent.TYPE_LOGIN_FAILED);
			event.setUser(username);
			cs.loginFailed(event);
			
		}
	} // end login
	
	
	/**
	 * <p>
	 * Inicia el servidor del TOLUCA instanciando un objeto de clase RoomServer
	 * </p>
	 * <p>
	 *
	 * </p>
	 * <p>
	 *
	 * @param args Actualmente ninguno. Pasar como argumento el nombre del
	 * archivo de configuraci&#243;n?
	 * </p>
	 */
	public static void main(String[] args)
	{
		DOMConfigurator.configure(System.getProperty("user.dir")
                + System.getProperty("file.separator") + "log.xml");
		new RoomServer();
	} // end main
	
	/**
	 * <p>
	 * Does ...
	 * </p><p>
	 *
	 * @return a Vector with ...
	 * </p>
	 */
	
	
	
	
	
	/** Getter for property dbOperations.
	 * @return Value of property dbOperations.
	 */
	public DbOperations getDbOperations()
	{
		return this.dbOperations;
	}
	
	/** Setter for property dbOperations.
	 * @param dbOperations New value of property dbOperations.
	 */
	public void setDbOperations(DbOperations dbOperations)
	{
		this.dbOperations = dbOperations;
	}
	
	/** Getter for property connManager.
	 * @return Value of property connManager.
	 */
	public ConnectionManager getConnManager()
	{
		return this.connManager;
	}
	
	/** Setter for property connManager.
	 * @param connManager New value of property connManager.
	 */
	public void setConnManager(ConnectionManager connManager)
	{
		this.connManager = connManager;
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
	 */
	public void sendChatMessage(TrucoPlayer player, String htmlMessage)
	{
		fireChatMessageSent(player, htmlMessage);
	}
	
	public void showChatMessage(TrucoPlayer player, String htmlMessage)
	{
	}
	
	// end setVTables
	
	/**
	 * <p>
	 * Recorre el vector de listeners y ejecuta en cada uno de los objetos del
	 * mismo, el metodo fireTableCreated.
	 * </p>
	 *
	 */
	protected void firePlayerJoined(final TrucoPlayer jogador)
	{
		//la gran avestruz, deberia ser asi con RoomEvent que extiende de la inexistente SpaceEvent
		/*RoomEvent re = new RoomEvent();
		re.setType(RoomEvent.TYPE_PLAYER_JOINED);
		re.addPlayers(jogador);
		Iterator iter = roomListeners.listIterator();
		while(iter.hasNext()) {
			RoomListener ltmp = (py.edu.uca.fcyt.toluca.RoomListener)iter.next();
			ltmp.playerJoined(jogador);
			ltmp.loginCompleted(re);
		}*/
		/*Agrego el jugador a la lista de jugadores.*/
		addPlayer(jogador);
		
		logger.debug("Dentro de fire user joined (Room Server) , jugador = " + jogador.getName());
		RoomEvent re = new RoomEvent();
		re.setType(RoomEvent.TYPE_PLAYER_JOINED);
		if (jogador == null)
			logger.debug("jogador es null carajo");
		
		//Vector v = new Vector();
		//getVPlayers().add(jogador);
		//re.setPlayers(getVPlayers());
		
		Iterator iter = roomListeners.listIterator();
		int i = 0;
		
		while(iter.hasNext())
		{
			System.out.println("Player join iterando: " + i++);
			RoomListener ltmp = (RoomListener)iter.next();
			ltmp.playerJoined(jogador);
		}
		
		
	}
	
	/**
	 * <p>
	 * Se ejecuta cuando un Jugador se autentic� correctameente.
	 * Recorre el vector de listeners y ejecuta en cada uno de los objetos del
	 * mismo, el metodo fireTableCreated.
	 * </p>
	 *
	 */
	protected void fireLoginCompleted(final TrucoPlayer jogador)
	{
		//la gran avestruz, deberia ser asi con RoomEvent que extiende de la inexistente SpaceEvent
		
		try
		{
			logger.debug("Dentro de fire login completed , jugador = " + jogador.getName());
			RoomEvent re = new RoomEvent();
			re.setType(RoomEvent.TYPE_LOGIN_COMPLETED);
			
			
			re.setPlayers(getHashPlayers());
			re.setTablesServers(getTablesServers());
			
			RoomListener ltmp = (RoomListener) pendingConnections.get(jogador.getName());
			
			try
			{
				
				ltmp.loginCompleted(re);
			} catch (NullPointerException e)
			{
				logger.debug("No hay listener adherido");
				throw e;
			}
			
			
			logger.debug("Se disparo login Completed");
			pendingConnections.remove(jogador.getName());
			
		} catch (java.lang.NullPointerException npe)
		{
			logger.debug("Null pointer exceptiooooon en room server");
			if (jogador == null)
				logger.debug("jogador es nulo!");
			else
				logger.debug("nombre del jogador:" + jogador.getName());
			npe.printStackTrace(System.out);
		}
		/*
		Iterator iter = roomListeners.listIterator();
		while(iter.hasNext()) {
			RoomListener ltmp = (py.edu.uca.fcyt.toluca.RoomListener)iter.next();
		 
			String nombre = ((CommunicatorServer)ltmp).player.getName();
			System.out.println("Verificando el jugador: " + nombre);
		 
			if ( jogador.getName().compareTo(nombre)  == 0) { //
				System.out.println("dentro del roomserver, encontre el player que se logeuo");
				ltmp.loginCompleted(re);
				break;
			}
		 
		}*/
	}
	
	
	
	/**
	 * Dispara el evento de chatMessageSent
	 */
	protected void fireChatMessageSent(TrucoPlayer jogador, String htmlMessage)
	{
		Iterator iter = roomListeners.listIterator();
		int i =0;
		while(iter.hasNext())
		{
			RoomListener ltmp = (RoomListener)iter.next();
			System.out.println("Jogador vale "+jogador+ " el mensaje es"+htmlMessage);
			logger.debug(jogador.getName() + " enviando message sent al listener #" + (i++) + " clase:" + ltmp.getClass().getName());
			ltmp.chatMessageSent(this, jogador, htmlMessage);
		}
	}
	
	public void chatMessageSent(ChatPanelContainer cpc, TrucoPlayer player, String htmlMessage)
	{
		
	}
	
} // end RoomServer



