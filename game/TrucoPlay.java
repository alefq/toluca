
/** Java class "TrucoPlay.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
package py.edu.uca.fcyt.toluca.game;

import py.edu.uca.fcyt.toluca.*;

import java.util.*;
/**
 * <p>
 * 
 * </p>
 */
public class TrucoPlay {

  //tipos de jugadas
    /** juego de tipo cantar <B>envido</B>
     */    
  public static final byte ENVIDO=1;//ok
  /** juego de tipo cantar <B>real envido</B>
   */  
  public static final byte REAL_ENVIDO=2;//ok
  /** juego de tipo cantar <B>falta envido</B>
   */  
  public static final byte FALTA_ENVIDO=3;//ok
  
  /** juego de tipo cantar falta envido
   */  
  public static final byte FLOR=11;//ok
  /** juego de tipo cantar <I>contra flor</I>
   */  
  public static final byte CONTRA_FLOR=12;//NO HABILITADO
  /** juego de tipo cantar <I>contra flor al resto</I>
   */  
  public static final byte CONTRA_FLOR_ALRESTO=13;//NO HABILITADO
  /** juego de tipo cantar <I>con flor me achico</I>
   */  
  public static final byte CON_FLOR_ME_ACHICO=14;//NO HABILITADO
  
  /** juego de tipo cantar truco
   */  
  public static final byte TRUCO=21;//ok
  public static final byte RETRUCO=22;//ok
  public static final byte VALE_CUATRO=23;//OK
  
  public static final byte QUIERO=31;//
  public static final byte NO_QUIERO=32;
  public static final byte ME_VOY_AL_MAZO=33;
  
  public static final byte CANTO_ENVIDO=61;
  public static final byte JUGAR_CARTA=62; //cuando solo tira la carta
  public static final byte PASO_ENVIDO = 63; //golpear la mesa en ronda de envido
  public static final byte PASO_FLOR = 64;
  public static final byte CANTO_FLOR=65;
  public static final byte CERRARSE = 66;
  
  
    private byte type = 0; 
    private int value = -1; 
    private TrucoCard card = null; 
    private TrucoPlayer player = null; 
    
    /*contructor para cantos de cuanto hay de envido, flor etc*/
    public TrucoPlay(TrucoPlayer tp, byte type, int value ){ 
        this(tp,type);
        this.value = value;
    }
    /*cantar truco, re..  , quiero, noquiero, flor, envido, mevoy al mazo*/
    public TrucoPlay(TrucoPlayer tp, byte type){
        this.player = tp;
        this.type = type;
    }
    /*Constructor para jugar carta*/
    public TrucoPlay(TrucoPlayer tp, byte type, TrucoCard card){
        this(tp,type);
        this.card = card;
    }
    
    public void setType (byte type){
        this.type = type;
    }
    public byte getType(){
        return type;
    }
    public void setCard(TrucoCard card){
        this.card = card;
    }
    public TrucoCard getCard(){
        return card;
    }
    public void setPlayer(TrucoPlayer tp){
        this.player = tp;
    }
    public TrucoPlayer getPlayer(){
        return player;
    }
    public void setValue(int value){
        this.value = value;
    }
    public int getValue (){
        return value;
    }
    
} // end TrucoPlay



