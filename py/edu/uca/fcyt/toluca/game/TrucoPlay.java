
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
    /** Juego de tipo cantar <I>"Envido"</I>.
     */    
  public static final byte ENVIDO=1;//ok
  /** Juego de tipo cantar <I>"Real Envido"</I>.
   */  
  public static final byte REAL_ENVIDO=2;//ok
  /** Juego de tipo cantar <I>"Falta Envido"</I>.
   */  
  public static final byte FALTA_ENVIDO=3;//ok
  
  /** Juego de tipo cantar <I>"Flor"</I>.
   */  
  public static final byte FLOR=11;//ok
  /** Juego de tipo cantar <I>"Contra Flor"</I>.
   */  
  public static final byte CONTRA_FLOR=12;//NO HABILITADO
  /** Juego de tipo cantar <I>"Contra Flor al Resto"</I>.
   */  
  public static final byte CONTRA_FLOR_ALRESTO=13;//NO HABILITADO
  /** Juego de tipo cantar <I>"con Flor Me Achico"</I>.
   */  
  public static final byte CON_FLOR_ME_ACHICO=14;//NO HABILITADO
  
  /** Juego de tipo cantar <I>"Truco"</I>.
   */  
  public static final byte TRUCO=21;//ok
  /** Juego de tipo cantar <I>"Retruco"</I>.
   */  
  public static final byte RETRUCO=22;//ok
  /** Juego de Tipo cantar <I>"Vale Cuatro"</I>.
   */  
  public static final byte VALE_CUATRO=23;//OK
  
  /** Juego de tipo cantar <I>"Quiero"</I>.
   */  
  public static final byte QUIERO=31;//
  /** Juego de tipo cantar <I>"No Quiero"</I>.
   */  
  public static final byte NO_QUIERO=32;
  /** Juego de tipo cantar <I>"Me voy al Mazo"</I>.
   */  
  public static final byte ME_VOY_AL_MAZO=33;
  
  /** Juego de tipo cantar <I>"cuanto tengo de Envido"</I>.
   */  
  public static final byte CANTO_ENVIDO=61;
  /** Juego de tipo <I>"jugar una Carta"</I>.
   */  
  public static final byte JUGAR_CARTA=62; //cuando solo tira la carta
  /** Juego de tipo  <I>"Paso el canto de cuanto tengo de Envido"</I>.
   */  
  public static final byte PASO_ENVIDO = 63; //golpear la mesa en ronda de envido
  /** Juego de tipo cantar <I>"Paso el canto de cuanto tengo de Flor"</I>.
   */  
  public static final byte PASO_FLOR = 64;
  /** Juego de tipo cantar <I>"cuanto tengo de Flor"</I>.
   */  
  public static final byte CANTO_FLOR=65;
  /** Juego de tipo <I>"Me Cierro"</I>.
   */  
  public static final byte CERRARSE = 66;
  
  
    private byte type = 0; 
    private int value = -1; 
    private TrucoCard card = null; 
    private TrucoPlayer player = null; 
    
    
    /** Contructor para cantos de cuanto hay de envido, flor etc.
     * @param tp TrucoPlayer a hacer la Jugada.
     * @param type Tipo de Jugada en este caso si canto el valor del Envido o Flor.
     * @param value Valor del Envido o de la Flor a cantar.
     */    
    public TrucoPlay(TrucoPlayer tp, byte type, int value ){ 
        this(tp,type);
        this.value = value;
    }
    
    /** Constructor de jugada de tipo cantar o acto(cerrarse)algo.
     * @param tp Player que jugará.
     * @param type Tipo de canto o acto a realizar.
     */    
    public TrucoPlay(TrucoPlayer tp, byte type){
        this.player = tp;
        this.type = type;
    }
    
    /** Contructor para jugada de tirar carta.
     * @param type Tipo de jugada a realizar.
     * @param tp TrucoPlayer a jugar.
     * @param card carta a ser tirada.
     */    
    public TrucoPlay(TrucoPlayer tp, byte type, TrucoCard card){
        this(tp,type);
        this.card = card;
    }
    
    /** Configura el Tipo de Jugada o Acto.
     * @param type Valor de la Jugada.
     */    
    public void setType (byte type){
        this.type = type;
    }
    /** Retorna el tipo de Jugada.
     * @return Retorna el byte asignado al tipo de Jugada.
     */    
    public byte getType(){
        return type;
    }
    /** Configurar la carta a jugar.
     * @param card carta a Jugar.
     */    
    public void setCard(TrucoCard card){
        this.card = card;
    }
    /** Retorna la carta de la jugada.
     * @return TrucoCard asignada a la jugada.
     */    
    public TrucoCard getCard(){
        return card;
    }
    /** Configurar el TrucoPlayer de la jugada.
     * @param tp TrucoPlayer que realiza la jugada.
     */    
    public void setPlayer(TrucoPlayer tp){
        this.player = tp;
    }
    /** Retorna el TrucoPlayer que realiza la Jugada.
     * @return TrucoPlayer que realiza la jugada.
     */    
    public TrucoPlayer getPlayer(){
        return player;
    }
    /** Configura el valor de la Jugada, para jugadas de tipo Cantar valor de Envido o Flor.
     * @param value valor del Envido o Flor a jugar.
     */    
    public void setValue(int value){
        this.value = value;
    }
    /** Retorna el valor del Envido o Flor.
     * @return valor del Envido o Flor.
     */    
    public int getValue (){
        return value;
    }
    
} // end TrucoPlay



