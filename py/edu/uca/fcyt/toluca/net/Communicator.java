package py.edu.uca.fcyt.toluca.net;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import py.edu.uca.fcyt.game.ChatPanelContainer;
import py.edu.uca.fcyt.game.Game;
import py.edu.uca.fcyt.net.XmlPackagesSession;
import py.edu.uca.fcyt.toluca.event.RoomEvent;
import py.edu.uca.fcyt.toluca.event.RoomListener;
import py.edu.uca.fcyt.toluca.event.SpaceListener;
import py.edu.uca.fcyt.toluca.event.TableEvent;
import py.edu.uca.fcyt.toluca.event.TableListener;
import py.edu.uca.fcyt.toluca.event.TrucoListener;
import py.edu.uca.fcyt.toluca.game.TrucoCard;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;
import py.edu.uca.fcyt.toluca.table.Table;
import py.edu.uca.fcyt.toluca.table.TableServer;

public abstract class Communicator extends XmlPackagesSession
implements RoomListener, TableListener,TrucoListener
{
    
    int current=0;
    
    /*
     * <p>Player del communicator</p>
     */
    TrucoPlayer player;
    
    Vector Players=new Vector();
    Vector Mesas=new Vector();
    /*
    public Communicator(Room pieza) {
	this.pieza=pieza;
    }*/
    public Communicator()
    {
	System.out.println("construyendo Communicator");
	tables = new Hashtable();
    }
    public String getInitErrorMessage(int errcode)
    {
	return "Sin errores";
    }
    public int init()
    {
	return XmlPackagesSession.XML_PACKAGE_SESSION_INIT_OK;
    }
    public void playerLeft(TableEvent te)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    public void sendXmlPackage(Document doc)
    {
	
	String xmlstring=new String();
	XMLOutputter serializer = new XMLOutputter("  ", true);
	xmlstring=serializer.outputString(doc);
	super.sendXmlPackage(xmlstring);
    }
    
    public void receiveXmlPackageWithParsingError(String rawXmlPackage)
    {
	System.out.println("El XML tiene errores");
    }
    
    
	/*
	public static Document xmlCreateError(RoomEvent te)
	{
		Element ROOT = new Element("Error");
		Element TIPO= new Element("Tipo");
		//TIPO.setAttribute("id",String.valueOf(te.getErrorId()));
		//TIPO.setAttribute("severity",String.valueOf(te.getSeverity()));
		Element MSG= new Element("MSG");
		//MSG.setText(te.getMessage());
	 
		ROOT.addContent(TIPO);
		ROOT.addContent(MSG);
		Document doc = new Document(ROOT);
		return doc;
	}
	 
	 */
	/*
	public static Document xmlCreateExitRoom(RoomEvent te)
	{
		Element ROOT = new Element("ExitRoom");
		Element PLAYER = new Element("Player");
		Element ROOM = new Element("Room");
	 
		//PLAYER.setAttribute("id",String.valueOf(te.getPlayerId()));
		//ROOM.setAttribute("id",String.valueOf(te.getRoomId()));
	 
		ROOT.addContent(PLAYER);
		ROOT.addContent(ROOM);
		Document doc = new Document(ROOT);
		return doc;
	}
	 */
    
    public  Document xmlCreateChatMsg(ChatPanelContainer cpc, TrucoPlayer jug,String message)
    {
	Element ROOT = new Element("ChatMsg");
	ROOT.setAttribute("origin", cpc.getOrigin());
	Element PLAYER= new Element("Player");
	Element MSG = new Element("Msg");
	
	PLAYER.setAttribute("name",String.valueOf(jug.getName()));
	CDATA datos=new CDATA(message);
	MSG.addContent(datos);
	
	ROOT.addContent(PLAYER);
	ROOT.addContent(MSG);
	
	Document doc = new Document(ROOT);
	return doc;
	
    }
    public Document xmlCreateUserJoined(TrucoPlayer jogador)
    {
	
	Element ROOT=new Element("UserJoined");
	Element PLAYER=new Element("Player");
	PLAYER.setAttribute("name",jogador.getName());
	PLAYER.setAttribute("rating",String.valueOf(jogador.getRating()));
	ROOT.addContent(PLAYER);
	Document doc = new Document(ROOT);
	return doc;
	
    }
    
    
    public Document xmlCreateTableRequested(RoomEvent te)
    {
	Element ROOT = new Element("CreateTable");
	Element PLAYER=new Element("Player");
	
	PLAYER.setAttribute("id",String.valueOf(te.getUser()));
	ROOT.addContent(PLAYER);
	
	Document doc = new Document(ROOT);
	return doc;
    }
    
    public  Document xmlCreateLoginOk(RoomEvent te)
    {
	Element ROOT=new Element("LoginOk");
	Document doc=new Document(ROOT);
	
	Vector jugadores/*=new Vector()*/;
	Vector mesas/*=new Vector()*/;
	
	jugadores=(Vector)te.getPlayerss();
	System.out.println("El tam de jugadores es: " + jugadores.size());
	mesas=(Vector)te.getTabless();
	System.out.println("El tam de mesas es: " + mesas.size());
	TrucoPlayer jug;
	
	Element player;
	for (Enumeration e = jugadores.elements() ; e.hasMoreElements() ;)
	{
	    jug=(TrucoPlayer)e.nextElement();
	    System.out.println("Se agrega el player: " + jug.getName() + " al xml que va a viajar");
	    
	    player=new Element("Player");
	    player.setAttribute("name",jug.getName());
	    player.setAttribute("rating",String.valueOf(jug.getRating()));
	    
	    ROOT.addContent(player);
	    
	}
	TableServer mesa;
	Element eleMesa;
	
	for (Enumeration e = mesas.elements() ; e.hasMoreElements() ;)
	{
	    
	    mesa=(TableServer)e.nextElement();
	    //System.out.println(mesa.getTableNumber());
	    jugadores=(Vector)mesa.getPlayers();
	    eleMesa=new Element("Table");
	    eleMesa.setAttribute("number",String.valueOf(mesa.getTableNumber()));
	    for (Enumeration e2 = jugadores.elements() ; e2.hasMoreElements() ;)
	    {
		jug=(TrucoPlayer)e2.nextElement();
		player=new Element("Playert");
		//System.out.println(jug.getName());
		if (jug == null)
		    player.setAttribute("name","El PUTO");
		else
		    player.setAttribute("name",jug.getName());
		
		eleMesa.addContent(player);
		
	    }
	    ROOT.addContent(eleMesa);
	    
	}
	
	return doc;
    }
    int gameID;
    int hand;
    TrucoCard []cartas=new TrucoCard[3];
    int currentCard=0;
    public void xmlreadSendCards(Object o)
    {
	gameID=0;
	hand=0;
	currentCard=0;
	xmlreadSendCardsAlg(o);
	
	
    }
    public void xmlreadSendCardsAlg(Object o)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
/*		String aux;
	if (o instanceof Element) {
	    Element element = (Element) o;
	    aux=element.getName();
	    if(aux.compareTo("TrucoPlayer")==0) {
		//System.out.println("PLAYER:"+element.getText());
		user=element.getAttributeValue("name");
	    }
	    if(aux.compareTo("Game")==0) {
		//System.out.println("MESSAGE:"+element.getText());
		gameID=Integer.parseInt(element.getAttributeValue("id"));
	    }
			if(aux.compareTo("Hand")==0)
			{
				hand=Integer.parseInt(element.getAttributeValue("number"));
			}
			if(aux.compareTo("Carta")==0)
			{
				String kind=new String();
				String value=new String();
				kind=element.getAttributeValue("kind");
				value=element.getAttributeValue("value");
				cartas[currentCard]=new TrucoCard(Integer.parseInt(kind),Integer.parseInt(value));
				currentCard++;
 
			}
	    List children = element.getContent();
	    Iterator iterator = children.iterator();
	    while (iterator.hasNext()) {
		Object child = iterator.next();
		xmlreadSendCardsAlg(child);
	    }
	    if(aux.compareTo("SendCards")==0) {
		//Chatpanel.showChatMessage(user,message);
		TrucoEvent te=new TrucoEvent(new TrucoGame(gameID),hand,new TrucoPlayer(user),TrucoEvent.ENVIAR_CARTAS,cartas);
				System.out.println("gameid: " + (te.getTrucoGame()).getId() + "\nHand" + te.getNumberOfHand() +"\nPlayer :" + (te.getPlayer()).getName());
				TrucoCard []cartasIMP=new TrucoCard[3];
				System.out.println("*******Cartas*********");
				cartasIMP=te.getCards();
				for(int i=0;i<3;i++)
				{
					System.out.println("Palo:" + cartasIMP[i].getKind() + "Value: " + cartasIMP[i].getValue());
 
				}
			}
	} */
    }
    int type;
    public void xmlReadCanto(Object o)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
/*		String aux;
	if (o instanceof Element) {
	    Element element = (Element) o;
	    aux=element.getName();
	    if(aux.compareTo("Type")==0) {
	     type=Integer.parseInt(element.getAttributeValue("id"));
	    }
			 if(aux.compareTo("Game")==0) {
		//System.out.println("MESSAGE:"+element.getText());
		gameID=Integer.parseInt(element.getAttributeValue("id"));
	    }
			if(aux.compareTo("Hand")==0)
			{
				hand=Integer.parseInt(element.getAttributeValue("number"));
			}
			 if(aux.compareTo("Player")==0) {
		//System.out.println("PLAYER:"+element.getText());
		user=element.getAttributeValue("name");
	    }
	    List children = element.getContent();
	    Iterator iterator = children.iterator();
	    while (iterator.hasNext()) {
		Object child = iterator.next();
		xmlReadCanto(child);
	    }
	    if(aux.compareTo("Canto")==0) {
			  TrucoEvent te=new TrucoEvent(new TrucoGame(gameID),hand,new TrucoPlayer(user),(byte)type);
			  System.out.println("Tipo:"+type);
			   System.out.println("Game:"+gameID);
			   System.out.println("Hand:"+hand);
			   System.out.println("Player:"+user);
	    }
	} */
    }
    TrucoCard cartaEnv;
    
    int tanto;
    public void xmlReadCantarTanto(Object o)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
/*				String aux;
	if (o instanceof Element) {
	    Element element = (Element) o;
	    aux=element.getName();
	    if(aux.compareTo("Type")==0) {
	     type=Integer.parseInt(element.getAttributeValue("id"));
	    }
			 if(aux.compareTo("Game")==0) {
		//System.out.println("MESSAGE:"+element.getText());
		gameID=Integer.parseInt(element.getAttributeValue("id"));
	    }
			if(aux.compareTo("Hand")==0)
			{
				hand=Integer.parseInt(element.getAttributeValue("number"));
			}
			 if(aux.compareTo("Player")==0) {
		//System.out.println("PLAYER:"+element.getText());
		user=element.getAttributeValue("name");
	    }
			if(aux.compareTo("Tanto")==0)
			{
				tanto=Integer.parseInt(element.getAttributeValue("tanto"));
			}
	    List children = element.getContent();
	    Iterator iterator = children.iterator();
	    while (iterator.hasNext()) {
		Object child = iterator.next();
		xmlReadCantarTanto(child);
	    }
	    if(aux.compareTo("CantarTanto")==0) {
				TrucoEvent te=new TrucoEvent(new TrucoGame(gameID),hand,new TrucoPlayer(user),(byte)type,tanto);
	       System.out.println("Tipo:"+type);
			   System.out.println("Game:"+gameID);
			   System.out.println("Hand:"+hand);
			   System.out.println("Player:"+user);
			  System.out.println("Tanto"+tanto);
			 }
	} */
    }
    public void xmlReadTurno(Object o)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
/*		String aux;
	if (o instanceof Element) {
	    Element element = (Element) o;
	    aux=element.getName();
	    if(aux.compareTo("Type")==0) {
	     type=Integer.parseInt(element.getAttributeValue("id"));
	    }
			 if(aux.compareTo("Game")==0) {
		//System.out.println("MESSAGE:"+element.getText());
		gameID=Integer.parseInt(element.getAttributeValue("id"));
	    }
			if(aux.compareTo("Hand")==0)
			{
				hand=Integer.parseInt(element.getAttributeValue("number"));
			}
			 if(aux.compareTo("Player")==0) {
		//System.out.println("PLAYER:"+element.getText());
		user=element.getAttributeValue("name");
	    }
 
	    List children = element.getContent();
	    Iterator iterator = children.iterator();
	    while (iterator.hasNext()) {
		Object child = iterator.next();
		xmlReadTurno(child);
	    }
	    if(aux.compareTo("Turno")==0) {
				System.out.println("Leyento paquete turno");
				TrucoEvent te=new TrucoEvent(new TrucoGame(gameID),hand,new TrucoPlayer(user),(byte)type);
	       System.out.println("Tipo:"+type);
			   System.out.println("Game:"+gameID);
			   System.out.println("Hand:"+hand);
			   System.out.println("Player:"+user);
 
			 }
	}
	}
	public void xmlReadTerminalMessage(Object o)
	{
		String aux;
	if (o instanceof Element) {
	    Element element = (Element) o;
	    aux=element.getName();
	    if(aux.compareTo("Type")==0) {
	     type=Integer.parseInt(element.getAttributeValue("id"));
	    }
			 if(aux.compareTo("Game")==0) {
		//System.out.println("MESSAGE:"+element.getText());
		gameID=Integer.parseInt(element.getAttributeValue("id"));
	    }
			if(aux.compareTo("Hand")==0)
			{
				hand=Integer.parseInt(element.getAttributeValue("number"));
			}
			 if(aux.compareTo("Player")==0) {
		//System.out.println("PLAYER:"+element.getText());
		user=element.getAttributeValue("name");
	    }
 
	    List children = element.getContent();
	    Iterator iterator = children.iterator();
	    while (iterator.hasNext()) {
		Object child = iterator.next();
		xmlReadTerminalMessage(child);
	    }
	    if(aux.compareTo("TerminalMessage")==0) {
				System.out.println("Leyento paquete terminall Message");
				TrucoEvent te=new TrucoEvent(new TrucoGame(gameID),hand,new TrucoPlayer(user),(byte)type);
	       System.out.println("Tipo:"+type);
			   System.out.println("Game:"+gameID);
			   System.out.println("Hand:"+hand);
			   System.out.println("Player:"+user);
 
			 }
	} */
    }
    
    
    //private RoomServer pieza; No
    //by sacoleiro
    public void chatMessageRequested(SpaceListener spaceListener, TrucoPlayer player, String htmlMessage)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    public void gameStartRequest(TableEvent ev)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    
	/*
	public static Document xmlCreateUserJoinedRoom(RoomEvent te)
	{
		Element ROOT=new Element("UserJoinedRoom");
		Element ROOM=new Element("Room");
		ROOT.addContent(ROOM);
	 
		Integer clave;
		String valor;
		Element PLAYER;
		//parte de los players
		Hashtable players = new Hashtable();
		//Hashtable players = te.getPlayers();
		for (Enumeration e = players.keys() ; e.hasMoreElements() ;)
		{
	 
			clave=(Integer)e.nextElement();
			valor=(String)players.get(clave);
			PLAYER=new Element("Player");
			PLAYER.setAttribute("id",String.valueOf(clave));
			PLAYER.setAttribute("name",valor);
			ROOM.addContent(PLAYER);
		}
		//parte de las tablas
		Element TABLE;
		Hashtable mesas = new Hashtable();
		//Hashtable mesas = te.getMesas();
		for (Enumeration e = mesas.keys() ; e.hasMoreElements() ;)
		{
	 
			clave=(Integer)e.nextElement();
			valor=(String)mesas.get(clave);
			TABLE=new Element("Table");
			TABLE.setAttribute("id",String.valueOf(clave));
			TABLE.setAttribute("name",valor);
			ROOM.addContent(TABLE);
		}
	 
		Document doc=new Document(ROOT);
		return doc;
	 
	 
	 
	}
	 */
/*
	public static Document xmlCreatePlayerLeftRoom(RoomEvent te)
	{
		Element ROOT= new Element("PlayerLeftRoom");
		Element PLAYER= new Element("Player");
		Element ROOM = new Element("Room");
 
		//PLAYER.setAttribute("id",String.valueOf(te.getPlayerId()));
		//ROOM.setAttribute("id",String.valueOf(te.getRoomId()));
 
		ROOT.addContent(PLAYER);
		ROOT.addContent(ROOM);
 
 
		Document doc = new Document(ROOT);
		return doc;
 
	}
 */
	/*
	 
	public static Document xmlCreateTableCreated(RoomEvent te)
	{
		Element ROOT= new Element("TableCreated");
		Element OWNER=new Element("Owner");
		Element TABLENAME=new Element("Tablename");
		Element PLAYERS=new Element("Players");
	 
		//OWNER.setAttribute("id",String.valueOf(te.getOwnerId()));
		//TABLENAME.setText(te.getName());
		//PLAYERS.setText(String.valueOf(te.getNplayers()));
	 
	 
		ROOT.addContent(OWNER);
		ROOT.addContent(TABLENAME);
		ROOT.addContent(PLAYERS);
	 
		Document doc = new Document(ROOT);
		return doc;
	}
	 
	 */
    public  Document xmlCreateLogin(RoomEvent te)
    {
	
	Document doc = null;
	try
	{
	    Element ROOT= new Element("Login");
	    Element PLAYER= new Element("Player");
	    Element PASSWORD = new Element("Password");
	    PLAYER.setText(String.valueOf(te.getUser()));
	    PASSWORD.setText(String.valueOf(te.getPassword()));
	    
	    //PLAYER.setText(String.valueOf("danic"));
	    //PASSWORD.setText(String.valueOf("cricco"));
	    
	    ROOT.addContent(PLAYER);
	    ROOT.addContent(PASSWORD);
	    
	    doc = new Document(ROOT);
	} catch (java.lang.NullPointerException npe)
	{
	    System.err.println("Hay un error al hacer el XMLCREATELOGIN en el Communicator");
	    npe.printStackTrace();
	    throw npe;
	} finally
	{
	    return doc;
	}
    }
	/*
 public  void cabecera(Document doc)
  {//saca la cabeza del paquete y envia el paquete al lector correspondiente
	 
		List children = doc.getContent();
		Iterator iterator = children.iterator();
		Object child = iterator.next();
		Element element = (Element) child;
	    String aux=element.getName();
		//System.out.println(aux);
	 
		if(aux.compareTo("UserJoinedRoom")==0)
		{
			//xmlReadUserJoinedRoom(child);
		}
		if(aux.compareTo("UserExitRoom")==0)
		{
			//xmlReadExitRoom(child);
		}
		if(aux.compareTo("CreateTable")==0)
		{
			//xmlReadCreateTable(child);
		}
		if(aux.compareTo("TableClosed")==0)
		{
			//xmlReadTableClosed(child);
		}
		if(aux.compareTo("PlayerLeftRoom")==0)
		{
			//xmlReadPlayerLeftRoom(child);
		}
		if(aux.compareTo("Login")==0)
		{
			xmlReadLogin(child);
		}
		if(aux.compareTo("ChatMsg")==0)
		{
			xmlReadChatMsg(child);
		}
		if(aux.compareTo("Error")==0)
		{
			//xmlReadError(child);
		}
		if(aux.compareTo("TableCreated")==0)
		{
			//xmlReadError(child);
		}
		if(aux.compareTo("LoginOk")==0)
		{
			xmlReadLoginOk(child);
		}
}
	 */
    
	/*
	public  void xmlReadLoginOk(Object o)
	{//este metodo no anda cuando se envian los paquetes a pesar de que anda para leer el Document
		String aux;
	 
		if (o instanceof Element)
		{
			Element element = (Element) o;
			aux=element.getName();
	 
			if(aux.compareTo("Player")==0)
			{
				String jugname=element.getAttributeValue("name");
				int rating=Integer.parseInt(element.getAttributeValue("rating"));
				Player jug1=new Player(jugname,rating);
				Players.add(jug1);
	 
			}
	 
			if(aux.compareTo("Table")==0)
			{
				Table mesa=new Table();
				int number=Integer.parseInt(element.getAttributeValue("number"));
				mesa.setTableNumber(number);
	 
				List childrenTable = element.getContent();
				Iterator iterator2 = childrenTable.iterator();
				Element element2;
	 
	 
				while (iterator2.hasNext())
				{
	 
					Object child = iterator2.next();
					element2=(Element)child;
					aux=element2.getName();
	 
					if(aux.compareTo("Playert")==0)
					{
						String jugname=element2.getAttributeValue("name");
						System.out.println("Playert:  "+jugname);
						Player jugaux=new Player(jugname,0);
						mesa.addPlayer(jugaux);
					}
	 
				}
				Mesas.add(mesa);
				//System.out.println("Table"+element.getAttributeValue("number"));
			}
			List children = element.getContent();
			Iterator iterator = children.iterator();
			while (iterator.hasNext())
			{
				Object child = iterator.next();
				xmlReadLoginOk(child);
			}
			if(aux.compareTo("LoginOk")==0)
			{
	 
				Player jug;
				Table mesa1;
				for (Enumeration e = Players.elements() ; e.hasMoreElements() ;)
				{
						jug=(Player)e.nextElement();
						System.out.println("Player: "+jug.getName()+" Rating: "+jug.getRating()+"\n");
						//pieza.addPlayer(jug);
				}
				for (Enumeration e = Mesas.elements() ; e.hasMoreElements() ;)
				{
						mesa1=(Table)e.nextElement();
						System.out.println("Table number: "+mesa1.getTableNumber()+"\n");
						Vector jugadores=(Vector)mesa1.getPlayers();
						for(Enumeration e2=jugadores.elements();e2.hasMoreElements();)
						{
							jug=(Player)e2.nextElement();
							System.out.println("Player: "+jug.getName()+" Rating: "+jug.getRating()+"\n");
						}
	 
						//pieza.addTable(mesa1);
	 
				}
			}//el if cuando termina de leer el paquete
	    }
	 
	}*/
    
    public void loginCompleted(RoomEvent re)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    
/*
	public void xmlReadLogin(Object o)
	{//este metodo no anda cuando se envian los paquetes a pesar de que anda para leer el Document
	String aux;
	String player=new String();
	String password=new String();
	if (o instanceof Element)
	{
		Element element = (Element) o;
		List children = element.getContent();
		Iterator iterator = children.iterator();
 
		while (iterator.hasNext())
		{
			Object child = iterator.next();
			element=(Element)child;
			aux=element.getName();
			if(aux.compareTo("Player")==0)
			{
				player=element.getText();
			}
			if(aux.compareTo("Password")==0)
			{
				password=element.getText();
			}
		}
		System.out.println("Player: "+player);
		System.out.println("Password: "+password);
	}
	//System.out.println("Leyendo login");
 
    }
 */
    String user;
    String password;
    String message;
    
    /** Holds value of property tables. */
    private Hashtable tables;
    
/*
	public static Document xmlCreateTableClosed(RoomEvent te)
	{
		Element ROOT= new Element("TableClosed");
		Element TABLE= new Element("Table");
 
		//TABLE.setAttribute("id",String.valueOf(te.getTableId()));
 
		ROOT.addContent(TABLE);
 
 
		Document doc = new Document(ROOT);
		return doc;
 
	}
 */
    public void loginRequested(RoomEvent te)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    public void loginFailed(RoomEvent te)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    
    /*
    public static void main(String[] args) {
     
     
	RoomEvent te=new RoomEvent();
	Vector jugadores=new Vector();
	Vector mesas=new Vector();
	Player jug1=new Player("Daniel",10);
	Player jug2=new Player("José",20);
     
	Table mesa1=new Table();
	Table mesa2=new Table();
	jugadores.add(jug1);
	jugadores.add(jug2);
	mesa1.setTableNumber(2);
     
	mesa1.addPlayer(jug1);
	mesa1.addPlayer(jug2);
	mesa2.setTableNumber(10);
	mesa2.addPlayer(jug1);
	mesa2.addPlayer(jug2);
	mesas.add(mesa1);
	mesas.add(mesa2);
	te.setTabless(mesas);
	te.setPlayerss(jugadores);
     
	te.setUser("AAdaniAA");
	te.setPassword("AAcriccoAAA");
     
	Communicator cc=new Communicator();
	Document doc=cc.xmlCreateChatMsg(jug1,"Prueba");
	try {
     
	    XMLOutputter serializer = new XMLOutputter("  ", true);
     
	    serializer.output(doc, System.out);
     
	}
	catch (IOException e) {
	    System.err.println(e);
	}
	//cc.cabecera(doc);
     
    }
     */
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
    public void chatMessageRequested(TrucoPlayer player, String htmlMessage)
    {
		new Exception("Nada implementado aun :-(     ").printStackTrace();

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
    public void chatMessageSent(TrucoPlayer player, String htmlMessage)
    {
	new Exception("").printStackTrace();
    }
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * @param ev ...
     * </p><p>
     *
     * </p>
     */
    public abstract void createTableRequested(RoomEvent ev);
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param game ...
     * </p>
     */
    public void gameFinished(Game game)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param game ...
     * </p>
     */
    public void gameStarted(Game game)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * @param player ...
     * </p><p>
     *
     * </p>
     *
     * public void playerJoined(Player player) {
     * }
     */
    /** <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param player ...
     * </p>
     */
    public void playerKicked(TrucoPlayer player)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param player ...
     * </p>
     */
    public void playerLeft(TrucoPlayer player)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    public void playerSit(TrucoPlayer player, int position)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    public void sitRequested(TrucoPlayer player, int position)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    //    /** <p>
    //     * Does ...
    //     * </p><p>
    //     *
    //     * </p><p>
    //     *
    //     * @param ev ...
    //     * </p>
    //     */
    //    public void tableCreated(RoomEvent ev) {
    //    }
    //    Se fue al server
    
    public void tableJoinRequested(RoomEvent ev)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * @param ev ...
     * </p><p>
     *
     * </p>
     */
    
    public void tableJoined(RoomEvent ev)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param table ...
     * </p>
     */
    public void tableLocked(Table table)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param ev ...
     * </p>
     */
    public void tableModified(RoomEvent ev)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param re ...
     * </p>
     */
    public void tableRemoved(RoomEvent re)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param table ...
     * </p>
     */
    public void tableUnlocked(Table table)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param ev ...
     * </p>
     */
    public void joinTableRequested(RoomEvent ev)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param game ...
     * </p>
     */
    
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param game ...
     * </p>
     */
    
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * @param player ...
     * </p><p>
     * @param position ...
     * </p><p>
     *
     * </p>
     */
    
    /** <p>
     * Does ...
     * </p><p>
     *
     * </p><p>
     *
     * @param ev ...
     * </p>
     */
    public void sitRequest(TableEvent ev)
    {
	new Exception("Nada implementado aun :-(     ").printStackTrace();
    }
    
    /** Getter for property tables.
     * @return Value of property tables.
     *
     */
    public Hashtable getTables()
    {
	return this.tables;
    }
    
    /** Setter for property tables.
     * @param tables New value of property tables.
     *
     */
    public void setTables(Hashtable tables)
    {
	this.tables = tables;
    }
    
}