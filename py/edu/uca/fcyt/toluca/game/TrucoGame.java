package py.edu.uca.fcyt.toluca.game;

/*
 * trucoGame.java
 *
 * Created on 3 de marzo de 2003, 10:25 PM
 */
import java.util.*;
import py.edu.uca.fcyt.toluca.event.*;
import py.edu.uca.fcyt.game.*;

/**
 *
 * @authors  Julio Rey || Christian Benitez
 */
public class TrucoGame extends Game {
    
    /** Creates a new instance of trucoGame */
    LinkedList listenerlist; //lista de todos los listener
    private int[] points = new int [2]; //puntajes de los teams
    private TrucoTeam[] teams = new TrucoTeam[2]; //equipos que juegan
    private TrucoHand trucoHand; //mano actual
    private int numberOfHand; //numero de mano actual
    private int numberOfPlayers; //cantidad de jugadores
    private int numberOfTeams=0; //numero de equipos
    private int reparteCartas = 0;
    
    /** Holds value of property id. */
    private int id;
    
 //quien empieza la mano
    
    public TrucoGame(int id) {
    }
    
    /** Constructor con dos equipos, asi crea un TrucoGame
     */    
    public TrucoGame(TrucoTeam tm1, TrucoTeam tm2) throws InvalidPlayExcepcion{ //contructor con los teams 
        listenerlist = new LinkedList(); //instancia de la lista de Trucolistener
        teams[0] = tm1;
        teams[1] = tm2;
        numberOfHand = 1;
        newGame();
    }
    /** Dispara el inicio del juego
     */    
    public void startGame() throws InvalidPlayExcepcion{
        try{
            fireGameStarted();
            newGame();
            numberOfPlayers = teams[0].getNumberOfPlayers()*2;
            newHand();
        }
        catch(InvalidPlayExcepcion e){
            throw e;
        }
    }
    /** nuevo Juego
     */    
    public void newGame(){ //juevo juego
        points[0] = 0; //cerando el puntaje
        points[1] = 0; 
    }
    /** adherir un nuevo TrucoListener al juego
     */    
    public void addTrucoListener(TrucoListener tl){
        listenerlist.add(tl);
    }
    /** configurar los equipos que jugaran el TrucoGame
     */    
    public void setTeam(TrucoTeam team_1, TrucoTeam team_2){//insertar los teams que partciparan del juego de truco
        teams[0] = team_1;
        teams[1] = team_2;
    }
    public void dealtCards(TrucoPlayer tp, TrucoCard[] card)throws InvalidPlayExcepcion{//reparte las cartas a los jugadores
        for (int i=0; i<listenerlist.size(); i++){
            if (((TrucoListener)listenerlist.get(i)).getAssociatedPlayer()==tp){
                TrucoEvent event = new TrucoEvent(this,numberOfHand,tp,(byte)0,card);
                ((TrucoListener)listenerlist.get(i)).cardsDeal(event);
                return;
            }
        }
        throw (new InvalidPlayExcepcion("TrucoGame.dealCards(Player,Card[]) no se encontro el player" + tp.getName()));
    }
    /** retorna el Equipo nro i
     */    
    public Team getTeam(int i){ //retorna el team numero i
        if (i == 0 || i == 1)
            return teams[i];
        return null;
    }
    /** retorna el puntaje del equipo Team
     */    
    public int getGameTotalPoints(Team tm) throws InvalidPlayExcepcion{ //el puntaje del team tm
        if (teams[0] == tm)
             return points[0];
        if (teams[1] == tm)
             return points[1];
        throw (new InvalidPlayExcepcion("getGameTotalPoints() - fuera del dominio de la funcion"));
     //   return 0;
    }
    /** configurar el puntaje del equipo
     */    
    public void setGameTotalPoints(Team tm, int pts){ //inserta puntaje
        if (teams[0] == tm){
           points[0] = pts;
           return;
        }
        if (teams[1] == tm){
            points[1] = pts;
            return;
        }
    }
    /** metodo para realizar una jugada
     */    
    public void play (TrucoPlay tp) throws InvalidPlayExcepcion{ //play trucoGame
        if (teams[0] == null || teams[1] == null)
                throw (new InvalidPlayExcepcion("Teams not found in Trucogame"));
        try{
            trucoHand.play(tp);
        }
        catch (InvalidPlayExcepcion e){
            throw e;
        }
    }
    /** verifica si valido hacer la jugada
     * @param tp
     */    
    public boolean esPosibleJugar(TrucoPlay tp) throws InvalidPlayExcepcion{
        try{
            return trucoHand.esPosibleJugar(tp);
        }
        catch(InvalidPlayExcepcion e){
            throw e;
        }
    }
    /** enviar mensajes a todos los oyentes del cambio de turno
     */    
    public void fireTurnEvent(TrucoPlayer pl,byte type){ //avisar quien juega con type el tipo de turno, ronda de cartas, ronda de envidos o flores etc
        
        TrucoEvent event = new TrucoEvent(this, numberOfHand, pl, type); //crear el evento
        for (int i=0; i<listenerlist.size(); i++){ //enviarle a todos el evento
            ((TrucoListener)(listenerlist.get(i))).turn(event);
        }
    }
    /** enviar mensaje de jugada a todos los oyentes del juego
     */    
    public void firePlayEvent(TrucoPlayer pl, byte type){ //eventos de juego sin carta o canto
        TrucoEvent event = new TrucoEvent(this,numberOfHand,pl,type);
        for(int i=0; i<listenerlist.size();i++){
            ((TrucoListener)(listenerlist.get(i))).play(event);
        }
    }
    /** enviar mensaje de jugada a todos los oyentes del juego
     */    
    public  void firePlayEvent(TrucoPlayer pl, TrucoCard card,byte type){ //eventos de juego con carta
        TrucoEvent event = new TrucoEvent(this,numberOfHand,pl,type,card);
        for(int i=0; i<listenerlist.size();i++){
            ((TrucoListener)(listenerlist.get(i))).play(event);
        }
    }
    /** enviar mensaje de jugada a todos los oyentes del juego
     */    
    public void firePlayEvent (TrucoPlayer pl, byte type, int value){//eventos de canto de tanto
    	TrucoEvent event = new TrucoEvent(this,numberOfHand,pl, type, value);
    	for(int i=0; i<listenerlist.size();i++){
            ((TrucoListener)(listenerlist.get(i))).play(event);
        }
    }
    public void fireEventType(byte type){
        TrucoEvent event = new TrucoEvent(this,numberOfHand,type);
    }
    /** enviar mensaje a todos los oyentes sobre el final de la mano
     */    
    public void fireEndOfHandEvent() throws InvalidPlayExcepcion{
        points[0] = points[0] + trucoHand.getPointsOfTeam(0);
        points[1] = points[1] + trucoHand.getPointsOfTeam(1);
        teams[0].setPoints(points[0]);
        teams[1].setPoints(points[1]);
        fireEventType(TrucoEvent.FIN_DE_MANO);
        if(points[0] >= 30 || points[1] >= 30){
            fireEndOfGameEvent();
            System.out.println("congratulaciones alguien a ganado, lo importante es que parece que funciona!!");
        }
        else{
            numberOfHand++;
            newHand();
        }
    }
    /** enviar mensaje a todos los oyentes sobre el final del juego
     */    
    public void fireEndOfGameEvent(){
        fireEventType(TrucoEvent.FIN_DE_JUEGO);
    }
    /** enviar las cartas a cada jugador
     */    
    public void fireCardsDealt(){
        fireEventType(TrucoEvent.CARTAS_REPARTIDAS);
    }
    /** enviar mensaje a todos los oyentes sobre el el comienzo de la mano
     */    
    public void fireHandStarted(){
    	TrucoPlayer tp = teams[numberOfHand%2].getPlayerNumber((numberOfHand%numberOfPlayers)/2);
        TrucoEvent event = new TrucoEvent(this,numberOfHand,tp);
        for(int i=0; i<listenerlist.size();i++){
            ((TrucoListener)(listenerlist.get(i))).handStarted(event);
        }
    }
    /** enviar mensaje a todos los oyentes sobre el el comienzo del juego
     */    
    public void fireGameStarted(){
        TrucoEvent event = new TrucoEvent(this,numberOfHand);
        for(int i=0; i<listenerlist.size();i++){
            ((TrucoListener)(listenerlist.get(i))).gameStarted(event);
        }
    }
    /**
     * @throws InvalidPlayExcepcion  */    
    private void newHand() throws InvalidPlayExcepcion{ //nueva mano
        try{
            if (teams[0].getNumberOfPlayers() != teams[1].getNumberOfPlayers())
                throw (new InvalidPlayExcepcion("TrucoGame.newHand - la cantidad de players de los Teams son distintos"));
            fireHandStarted();/*para que se preparen los jugadores*/
            System.out.println("quien reparte las cartas" + numberOfPlayers);
            trucoHand = new TrucoHand(this, (numberOfHand+1)%numberOfPlayers); /*se crea un truco hand y guardo la referencia*/
            System.out.println("numero de mano" + numberOfHand);
            System.out.println("siguiente numero de mano" + numberOfHand);
            //reparteCartas=(++reparteCartas)%getNumberOfPlayers();/*incrementa el que reparte las cartas*/
            ///System.out.println("despues va repartir las cartas" + reparteCartas + "numero de players" + getNumberOfPlayers());
        }
        catch(InvalidPlayExcepcion e){
            throw e;
        }
    }
    /** configuar el puntaje
     * @param
     */    
    public void setPoints(){
    }
    /** obtener el puntaje de un equipo especifico
     */    
    public int getPoints(Team team){
        return 0;
    }
    /** retorna el numero de jugadores de la mesa
     * @return
     */    
    public int getNumberOfPlayers(){
        return (numberOfPlayers);
    }
    /** retorna el numero de mano
     */    
    public int getNumberOfHand(){
        return numberOfHand;
    }
    
    /** (deprecated)
     */    
    public int getPieTeam(int i){
        return (trucoHand.getPieTeam(i));
    }
    /** retorna cuantos puntos se juegan en caso de faltear
     */
 
    public int getFaltear(){ /*retorna los puntos que se jugar en falta envido*/
    	int i=0;	
    	if(points[1] > points[0])
    		i=1;
    	if(points[i]>=15)
    		return (30-points[i]);
    	return (15-points[i]);
    }
    /** (en construccion)
     */    
    public int alResto(int team){
    	if(team==0)
    		return (30-points[0]);
    	if(team == 1)
    		return (30-points[1]);
    	return 0;
    }
    
    /** Getter for property id.
     * @return Value of property id.
     *
     */
    public int getId() {
        return this.id;
    }
    
    /** Setter for property id.
     * @param id New value of property id.
     *
     */
    public void setId(int id) {
        this.id = id;
    }
    
}