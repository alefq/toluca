/*
 * TrucoClient.java
 *
 * Created on 5 de junio de 2003, 02:47 PM
 */

package py.edu.uca.fcyt.toluca.game;
import py.edu.uca.fcyt.toluca.event.*;

/**
 *
 * @author  PABLO JAVIER
 */
public class TrucoGameClient extends TrucoGame implements TrucoListener{
    
    /** Creates a new instance of TrucoClient */
    protected TrucoHandClient trucoHand;
    TrucoPlayer asociado;
    public TrucoGameClient(TrucoTeam t1, TrucoTeam t2) {
        super(t1, t2);
        System.err.println("Se crea un nuevo truco Game Client");
    }
    /** Enviar mensaje a todos los oyentes sobre el final del juego.
     *
     */
    public void recibirCartas (TrucoPlayer tp,TrucoCard cards[]){
        
    }
    public void fireEndOfGameEvent() {
    }
    
    /** Enviar mensaje a todos los oyentes sobre el final de la mano
     *
     */
    public void fireEndOfHandEvent() {
    }
    
    /** Enviar mensaje a todos los oyentes sobre el el comienzo del juego.
     *
     */
    public void fireGameStarted() {
    }
    
    /** Enviar mensaje a todos los oyentes sobre el el comienzo de la mano.
     *
     */
    public void fireHandStarted() {
    }
    
    /** Enviar mensaje de jugada a todos los oyentes del juego.
     * @param pl TrucoPlayer que realizo la jugada.
     * @param type Tipo de Jugada que realiz�.
     *
     */
    public void firePlayEvent(TrucoPlayer pl, byte type) { //eventos de juego sin carta o canto
        super.firePlayEvent(pl, type);
    }
    
    /** Enviar mensaje de jugada a todos los oyentes del juego.
     * @param pl TrucoPlayer que realiz� la jugada.
     * @param card Carta que jug� el Player.
     * @param type Tipo de jugada que realiz�.
     *
     */
    public void firePlayEvent(TrucoPlayer pl, TrucoCard card, byte type) { //eventos de juego con carta
        super.firePlayEvent(pl, card, type);
    }
    
    /** Enviar mensaje de jugada a todos los oyentes del juego.
     * @param pl Player que realiz� la jugada.
     * @param type Tipo de jugada que realiz�.
     * @param value Valor del canto (para jugadas de canto de valor Envido o Flor).
     *
     */
    public void firePlayEvent(TrucoPlayer pl, byte type, int value) {//eventos de canto de tanto
        super.firePlayEvent(pl, type, value);
    }
    
    /** Enviar mensajes a todos los oyentes del cambio de turno.
     * @param pl TrucoPlayer a quien se le asigna el turno.
     * @param type Tipo de Turno a ser asignado.
     *
     */
    public void fireTurnEvent(TrucoPlayer pl, byte type) { //avisar quien juega con type el tipo de turno, ronda de cartas, ronda de envidos o flores etc        
    }
    
    public void fireTurnEvent(TrucoPlayer pl, byte type, int value) { //avisar el Turno con envio del value of envido
    }
    public void play(TrucoEvent event){
    }
    public void turn (TrucoEvent event){
    }
    public void endOfHand(TrucoEvent event){
    }
    public void cardsDeal(TrucoEvent event){
    }
    public void handStarted(TrucoEvent event){
    }
    public void gameStarted(TrucoEvent event){
    }
     
    public void endOfGame(TrucoEvent event){
    }
    public TrucoPlayer getAssociatedPlayer(){
        return asociado;
    }
    
}
