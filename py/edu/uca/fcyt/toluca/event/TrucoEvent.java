package py.edu.uca.fcyt.toluca.event;

/** Java class "TrucoEvent.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

import org.jdom.Document;
import org.jdom.Element;

import py.edu.uca.fcyt.toluca.game.TrucoCard;
import py.edu.uca.fcyt.toluca.game.TrucoGame;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;

public class TrucoEvent {

  ///////////////////////////////////////
  /*tipoy de eventos*/
  public static final byte ENVIAR_CARTAS=0;
  public static final byte ENVIDO=1; //mensajes de juego , para el pie contestar
  public static final byte REAL_ENVIDO=2;
  public static final byte FALTA_ENVIDO=3;
  
  public static final byte FLOR=11;//mensajes de juego
  public static final byte CONTRA_FLOR=12; //elpiedebe contestar//
  public static final byte CONTRA_FLOR_AL_RESTO=13;//el pie debe contestar//
  public static final byte CON_FLOR_ME_ACHICO=14;
  
  public static final byte TRUCO=21; //mensajes de juego el `pie debe contestar//
  public static final byte RETRUCO=22;
  public static final byte VALE_CUATRO=23;
  
  public static final byte QUIERO=31;//mensajes de juego
  public static final byte NO_QUIERO=32;
  public static final byte ME_VOY_AL_MAZO=33;
  
  public static final byte TURNO_JUGAR_CARTA= 50;
  public static final byte TURNO_CANTAR_ENVIDO= 51;
  public static final byte TURNO_CANTAR_FLOR = 52;
  public static final byte TURNO_RESPONDER_TRUCO = 53;
  public static final byte TURNO_RESPONDER_RETRUCO = 54;
  public static final byte TURNO_RESPONDER_VALECUATRO = 55;
  public static final byte TURNO_RESPONDER_CONTRAFLOR = 56;
  public static final byte TURNO_RESPONDER_CONTRAFLORALRESTO = 57;
  public static final byte TURNO_RESPONDER_ENVIDO = 58;
  public static final byte TURNO_RESPONDER_REALENVIDO = 59;
  public static final byte TURNO_RESPONDER_FALTAENVIDO = 60;
    
  public static final byte JUGAR_CARTA=61; 
  public static final byte CANTO_ENVIDO=62;//avisar los cantos
  public static final byte CANTO_FLOR = 63;
  public static final byte PASO_ENVIDO = 64;
  public static final byte PASO_FLOR = 65;
  public static final byte CERRARSE = 66;
  /*no utilizadas...*/
  public static final byte SIN_MENSAJES=100;
  public static final byte INICIO_DE_JUEGO = 101;
  public static final byte INICIO_DE_MANO = 102;
  public static final byte FIN_DE_MANO = 103;
  public static final byte FIN_DE_JUEGO = 104;
  public static final byte CARTAS_REPARTIDAS = 105;
  /*para mensajes de confirmacion de espera del player*/
  public static final byte PLAYER_CONFIRMADO = 110;
  
  
    
  
  
 // attributes


/**
 * <p>
 * Represents ...
 * </p>
 */
	private TrucoGame game;  //EL JUEGO
	private int hand;  //NUMERO DE MANO
	private TrucoPlayer player;  //PLAYER QUE DISPARA EL EVENTO
	private byte type;  //TIPO DE JUGADA 0 CANTO
	private TrucoCard[] cards; 
	private TrucoCard card;
	int tableNumber;//para que el Communicator sepa a quien ejecutarle los metodos
	private int value=-1;
	/*repartir cartas*/
	public TrucoEvent (TrucoGame game, int hand, TrucoPlayer player){
		this(game,hand);
		this.player = player;
	}
	public TrucoEvent(int tableId,int hand,TrucoPlayer player,byte type, TrucoCard[] cards)
	{
		this.tableNumber = tableId;
		this.hand = hand;
		this.player = player;
		this.type = type;
		this.cards = cards;
	}
	public TrucoEvent(int tableId,int hand,TrucoPlayer player,byte type,int value)
	{
		this.tableNumber = tableId;
		this.hand = hand;
		this.player = player;
		this.type = type;
		this.value = value;
	}
	public TrucoEvent (TrucoGame game, int hand){
		this.game = game;
		this.hand = hand;
        
	}
	public TrucoEvent(TrucoGame game,int hand, TrucoPlayer player, byte type, TrucoCard[] cards){ //contructor de instancias
		this(game,hand,player,type);    
		this.cards = cards;
	}
	/*cantos envido truco, flor etc*/
	public TrucoEvent(TrucoGame game,int hand, TrucoPlayer player, byte type){
		this(game,hand,type);
		this.player = player;
        
	}
	public TrucoEvent(int tableId,int hand,TrucoPlayer player,byte type){
		this.tableNumber=tableId;
		this.hand=hand;
		this.player=player;
		this.type=type;
	}
	public TrucoEvent(TrucoGame game,int hand, byte type){
		this.game = game;
		this.hand= hand;
		this.type = type;
	}
	/*jugadas */
	public TrucoEvent (TrucoGame game, int hand, TrucoPlayer player, byte type, TrucoCard card){
		this(game, hand, player, type);
		this.card = card;
	}
	public TrucoEvent(int tableid,int hand,TrucoPlayer player,byte type,TrucoCard card)
	{
		this(tableid,hand,player,type);
		this.card=card;
	}
	public TrucoEvent (TrucoGame game, int hand, TrucoPlayer player, byte type, int value){
		this(game, hand, player,type);
		this.value = value;
	}
    
	public TrucoGame getTrucoGame(){ //obtener el TrucoGame que disparo el evento
		return game;
	}
	public int getNumberOfHand(){ //obtener el numero de mano del truco game
		return hand;
	}
	public TrucoPlayer getTrucoPlayer(){ //obtener el player
		return player;
	}
	public byte getTypeEvent(){ //tipo de evento
		return type;
	}
	public TrucoCard[] getCards(){
		return cards;
	}
	public TrucoCard getCard(){
		return card;
	}
   public TrucoPlayer getPlayer (){
		return player;
	}
   public int getValue (){
	   return value;
   }
   public void setTableNumber(int tableNumber)
	{
		this.tableNumber=tableNumber;
	}
	public int getTableNumber(){return tableNumber;}
public Document xmlCreateSendCards()
   {
		Element ROOT =new Element ("SendCards");
		
		Element PLAYER= new Element ("TrucoPlayer");
		PLAYER.setAttribute("name",player.getName());
		ROOT.addContent(PLAYER);

		Element TABLE = new Element("Table");
		TABLE.setAttribute("id",String.valueOf(getTableNumber()));
		ROOT.addContent(TABLE);

		Element HAND = new Element("Hand");
		HAND.setAttribute("number",String.valueOf(hand));
		ROOT.addContent(HAND);
		
		Element CARDS =new Element ("Cards");
		for (int i=0;i<3;i++)
		{
			  
			  TrucoCard carta=(TrucoCard)cards[i];
			  Element CARTA=new Element("Carta");
			  CARTA.setAttribute("kind",String.valueOf(carta.getKind()));
			  CARTA.setAttribute("value",String.valueOf(carta.getValue()));
			  CARDS.addContent(CARTA);
		}
		ROOT.addContent(CARDS);
		Document doc = new Document(ROOT);
		return doc;

   }
	public Document xmlCreateCantarTanto()
   {//envia el tanto cantado por el jugador sea envido,real envido,......
		Element ROOT = new Element ("CantarTanto");
		
		Element TIPO =new Element ("Type");
		TIPO.setAttribute("id",String.valueOf(type));
		ROOT.addContent(TIPO);

		Element TABLE =new Element ("Table");
		TABLE.setAttribute("id",String.valueOf(getTableNumber()));
		ROOT.addContent(TABLE);

		Element HAND = new Element("Hand");
		HAND.setAttribute("number",String.valueOf(hand));
		ROOT.addContent(HAND);

		Element PLAYER =new Element("Player");
		PLAYER.setAttribute("name",player.getName());
		ROOT.addContent(PLAYER);

		Element TANTO = new Element("Tanto");
		TANTO.setAttribute("tanto",String.valueOf(value));
		ROOT.addContent(TANTO);

		Document doc=new Document(ROOT);
		return doc;
   }


	

  
  public Document xmlCreateCanto()
   {//Sirve para enviar los cantos de los player truco-evndio-flor-etc
		Element ROOT = new Element ("Canto");
		
		Element TIPO =new Element ("Type");
		TIPO.setAttribute("id",String.valueOf(type));
		ROOT.addContent(TIPO);

		Element TABLE =new Element ("Table");
		TABLE.setAttribute("id",String.valueOf(getTableNumber()));
		ROOT.addContent(TABLE);

		Element HAND = new Element("Hand");
		HAND.setAttribute("number",String.valueOf(hand));
		ROOT.addContent(HAND);

		Element PLAYER =new Element("Player");
		PLAYER.setAttribute("name",player.getName());
		ROOT.addContent(PLAYER);

		Document doc= new Document(ROOT);
		return doc;
   }
  
   
   
   public Document xmlCreateCard()
	{//envia la carta jugada por el jugador PLAYER
		Element ROOT = new Element ("Cardsend");
		
		Element TIPO =new Element ("Type");
		TIPO.setAttribute("id",String.valueOf(type));
		ROOT.addContent(TIPO);

		Element TABLE =new Element ("Table");
		TABLE.setAttribute("id",String.valueOf(getTableNumber()));
		ROOT.addContent(TABLE);

		Element HAND = new Element("Hand");
		HAND.setAttribute("number",String.valueOf(hand));
		ROOT.addContent(HAND);

		Element PLAYER =new Element("Player");
		PLAYER.setAttribute("name",player.getName());
		ROOT.addContent(PLAYER);
		
		Element CARTA=new Element("Carta");
		CARTA.setAttribute("kind",String.valueOf(card.getKind()));
		CARTA.setAttribute("value",String.valueOf(card.getValue()));
		ROOT.addContent(CARTA);

		Document doc=new Document(ROOT);
		return doc;
   }

   //SE VA CON EL CANTO 
   /*
   public Document xmlCreateTurno()
	{//Del lado del cliente dispara el evento turno
	   Element ROOT = new Element ("Turno");
		
		Element TIPO =new Element ("Type");
		TIPO.setAttribute("id",String.valueOf(type));
		ROOT.addContent(TIPO);

		Element GAME =new Element ("Game");
		GAME.setAttribute("id",String.valueOf(game.getId()));
		ROOT.addContent(GAME);

		Element HAND = new Element("Hand");
		HAND.setAttribute("number",String.valueOf(hand));
		ROOT.addContent(HAND);

		Element PLAYER =new Element("Player");
		PLAYER.setAttribute("name",player.getName());
		ROOT.addContent(PLAYER);
		Document doc=new Document(ROOT);
		return doc;
   }*/
	   int tableIdAux;
	int handAux;
	String userAux;
	int typeAux;
	int tantoAux;


  //SE VA CON EL CANTO NOMAS
/*   public Document xmlCreateTerminalMessage()
  {
		Element ROOT = new Element ("TerminalMessage");
		
		Element TIPO =new Element ("Type");
		TIPO.setAttribute("id",String.valueOf(type));
		ROOT.addContent(TIPO);

		Element TABLE =new Element ("Table");
		TABLE.setAttribute("id",String.valueOf(getTableNumber()));
		ROOT.addContent(TABLE);

		Element HAND = new Element("Hand");
		HAND.setAttribute("number",String.valueOf(hand));
		ROOT.addContent(HAND);

		Element PLAYER =new Element("Player");
		PLAYER.setAttribute("name",player.getName());
		ROOT.addContent(PLAYER);


		Document doc=new Document(ROOT);
		return doc;
   }*/
   public Document toXml()
	{
	   Document doc = null;
	   switch(type)
		{
			case ENVIAR_CARTAS:
				doc = xmlCreateSendCards();
				break;
			case ENVIDO:
				doc = xmlCreateCanto();
				break;
			case REAL_ENVIDO:
				doc = xmlCreateCanto();
				break;
			case FALTA_ENVIDO:
				doc = xmlCreateCanto();
				break;
			case FLOR:
				doc = xmlCreateCanto();
				break;
			case CANTO_ENVIDO:
				doc = xmlCreateCanto();
				break;

			case CANTO_FLOR:
				doc = xmlCreateCanto();
				break;
			
			case CONTRA_FLOR:
				doc = xmlCreateCanto();
				break;
		
			case CONTRA_FLOR_AL_RESTO:
				doc = xmlCreateCanto();
				break;
			case CON_FLOR_ME_ACHICO:
				doc = xmlCreateCanto();
				break;
			case TRUCO:
				doc = xmlCreateCanto();
				break;
			case RETRUCO:
				doc = xmlCreateCanto();
				break;
			case VALE_CUATRO:
				doc = xmlCreateCanto();
				break;
			case QUIERO:
				doc = xmlCreateCanto();
				break;
			case NO_QUIERO:
				doc = xmlCreateCanto();
				break;
			case ME_VOY_AL_MAZO:
				doc = xmlCreateCanto();
				break;
			case CERRARSE:
				doc = xmlCreateCanto();
				break;
			case PASO_ENVIDO:
				doc = xmlCreateCanto();
				break;
			case PASO_FLOR:
				doc = xmlCreateCanto();
				break;
			case TURNO_JUGAR_CARTA:
				doc = xmlCreateCanto();
				break;
			
			case TURNO_CANTAR_FLOR:
				doc = xmlCreateCanto();
				break;
			case TURNO_RESPONDER_TRUCO:
				doc = xmlCreateCanto();
				break;
			case TURNO_RESPONDER_RETRUCO:
				doc = xmlCreateCanto();
				break;
			case TURNO_RESPONDER_VALECUATRO:
				doc = xmlCreateCanto();
				break;
			case TURNO_RESPONDER_ENVIDO:
				doc = xmlCreateCantarTanto();
				break;
			case TURNO_RESPONDER_REALENVIDO:
				doc = xmlCreateCantarTanto();
				break;
			case TURNO_RESPONDER_FALTAENVIDO:
				doc = xmlCreateCantarTanto();
				break;
			case TURNO_RESPONDER_CONTRAFLOR:
				doc =xmlCreateCantarTanto();
				break;
			case TURNO_RESPONDER_CONTRAFLORALRESTO:
				doc = xmlCreateCantarTanto();
				break;
			case JUGAR_CARTA:
				doc = xmlCreateCard();
				break;
		
			case FIN_DE_MANO:
				doc = xmlCreateCanto();
				break;
			case FIN_DE_JUEGO:
				doc = xmlCreateCanto();
				break;
			case INICIO_DE_JUEGO:
				doc = xmlCreateCanto();
				break;
			case INICIO_DE_MANO:
				doc = xmlCreateCanto();
				break;
			default:
				System.out.println("tipo de event no encontrado:" + type);
	   }
	   return doc;
   }
  	
} // end TrucoEvent



