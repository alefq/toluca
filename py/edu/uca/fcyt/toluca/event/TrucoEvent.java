package py.edu.uca.fcyt.toluca.event;

/** Java class "TrucoEvent.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.game.*;

import java.util.*;

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
    private int value;
    /*repartir cartas*/
    public TrucoEvent (TrucoGame game, int hand, TrucoPlayer player){
    	this(game,hand);
    	this.player = player;
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
    public Player getTrucoPlayer(){ //obtener el player
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
   public Player getPlayer (){
        return player;
    }
   
} // end TrucoEvent



