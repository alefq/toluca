/*
 * TrucoHand.java
 *
 * Created on 5 de marzo de 2003, 02:49 PM
 */

package py.edu.uca.fcyt.toluca.game;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.statusGame.*;
import py.edu.uca.fcyt.toluca.event.*;

import java.util.*;

/** constructor
 * @author Julio Rey || Cristian Benitez
 */
public class TrucoHand {
    
    private TrucoGame game;
    /*estados del juego*/
    static final byte PRIMERA_RONDA = 0;
    static final byte SEGUNDA_RONDA = 1;
    static final byte TERCERA_RONDA = 2;
    static final byte FLOR = 3;
    static final byte CONTRAFLOR = 4;
    static final byte CONTRAFLOR_AL_RESTO = 5;
    static final byte ENVIDO = 6;
    static final byte REAL_ENVIDO = 7;
    static final byte FALTA_ENVIDO = 8;
    
    static final byte ESPERANDO_RESPUESTA_DEL_TRUCO = 9;
    static final byte ESPERANDO_RESPUESTA_DEL_RETRUCO = 10;
    static final byte ESPERANDO_RESPUESTA_DEL_VALECUATRO = 11;
    
    
    static final byte RONDA_DE_FLOR = 13;
    static final byte RONDA_DE_ENVIDO = 14;
    
    
    
   
    private LinkedList puntajes = new LinkedList(); //lista delos puntajes de esta mano;
    private int numerodecarta = 0; //cuantas cartas se jugaron
    private TrucoPlayer pie1; //pie del team 1;
    private TrucoPlayer pie2; //pie del team 2;
    private int[] winRound=new int[3]; //ganadores de la ronda
    private boolean equipo1_canto_flor = false; /*los equipos ya cantaron flor?*/
    private boolean equipo2_canto_flor = false; 
    
    private boolean florFlor = false; //si se canto flor flor
    private boolean contraFlor = false; //si se canto contra flor
    private boolean contraFlorAlResto = false; //si se canto contra flor al resto
    
    private boolean flor = false; /*ya se canto flor*/
    private boolean envido = false; //se canto envido?
    private boolean realEnvido = false; //se canto real envido?
    private boolean faltaEnvido = false; //se canto falta envido?
    
    private boolean sePuedeCantarEnvido = true; //se puede cantar Envido?
    private boolean sePuedeCantarFlor = true; //se puede cantar Flor?
    
    private boolean elEnvidoEstaPrimero = false;
      
        
    private int numberOfEnvidos = 0; //cantidad de envido que se dijeron
    private int numberOfRealEnvidos = 0; //cantidad de real envido que se dijeron
    private int pointsOfEnvido = 0;
    private boolean truco = false;//se canto truco
    private boolean retruco = false;//" " retrtuco
    private boolean valecuatro = false;//" " vale Cuatro
    private int pointsOfHand = 1;
    
    private int cantidadDePlayers=0;
    
    private TrucoStatusTable statusTable;
    
    private Team[] teams = new Team[2];
    private int[] points  = new int[2];
    
    private TrucoPlayer playTurn=null; //turno para jugar
    
    private int primerTurnoNumber; //numero de jugador que juega primero
    private boolean huboRondaDeEnvido = false;
    
    private int playTurnNumber; //numero de jugador que le toca tirar carta
    private int envidoTurnNumber; //numero de jugador que le toca cantar su envido
    private int florTurnNumber; /*numero de jugador que le toca cantar su flor*/
    private Team laPalabra = null; //quien puede cantar retruco, etc
  
    private int pieTeam1Number; //numero de player que es pie del equipo1
    private int pieTeam2Number; //numero de player que es pie del equipo2
  
    private int cartasJugadas = 0; //cantidad de cartas jugadas
    private int numeroDeRonda = 1; //numero de ronda
    
    
    private byte estadoActual = PRIMERA_RONDA; //estado Actual, para controlar el juego
    
   
    
    
    
    /** constructor con TrucoGame y quienReparte
     * @param tg juego al que pertenece(un truco hand es una parte de un TrucoGame)
     * @param reparteCartas numero de jugador que repartira(para saber quien tiene el primer turno)
     * @throws InvalidPlayExcepcion  */    
    public TrucoHand(TrucoGame tg, int reparteCartas) throws InvalidPlayExcepcion{ //Se crea la Instancia de la mano
    	game = tg; //a que game se refiere
    	cantidadDePlayers = game.getNumberOfPlayers();
        primerTurnoNumber = (reparteCartas+1)%cantidadDePlayers;
        System.out.println("jaja el que reparte es " + reparteCartas + "jaja el que empieza es"+primerTurnoNumber);
        playTurnNumber = primerTurnoNumber; //quien juega la primera carta
        envidoTurnNumber = primerTurnoNumber; //quien canta el primer envido en caso de cantar
        florTurnNumber = primerTurnoNumber; //quien canta su flor en caso de haber
        statusTable = new TrucoStatusTable(cantidadDePlayers); //se crea un estado de la mesa
        for (int i=0; i<3; i++)
            winRound[i] = -1;
        points[0] = 0;  /*Cerar los puntajes.*/
    	points[0] = 0;    
        try{
            teams[0] = game.getTeam(0);
            teams[1] = game.getTeam(1);
            getPies(); //obtener los pies de la mano
            dealtCards(); //repartir cartas
            playTurn(); //asignar turno
        }
        catch(InvalidPlayExcepcion e){
            throw e; //se tiran excepciones
        }
    }
    private void getPies(){ /*quienes seran los pies de esta mano*/
        pieTeam1Number = (primerTurnoNumber+cantidadDePlayers-1)%(cantidadDePlayers); //numero de player que sera pie del 
        pie1=teams[0].getPlayerNumber(pieTeam1Number/2); //obtener player
        pieTeam2Number =  (primerTurnoNumber+cantidadDePlayers-2)%(cantidadDePlayers); //numero de player que sera pie del team2
        pie2=teams[1].getPlayerNumber(pieTeam2Number/2); //obtener playe
        if(pieTeam1Number%2 == 1){
            int pieAuxiliar = pieTeam1Number;
            pieTeam1Number = pieTeam2Number;
            pieTeam2Number = pieAuxiliar;
        }
        
    }
    /*devolver el numero de player que es pie*/
    /** Retorna el numero de jugador que es el pie del equipo
     * @param i
     */    
    public int getPieTeam(int i){ //obtener el numero de player pie
        if (i == 1)
            return (pieTeam1Number);
         if(i==2)
             return (pieTeam2Number);
        return -1;
    }
    /*repartir cartas a todos los players del game*/
    private void dealtCards() throws InvalidPlayExcepcion{ 
        int i;
        TrucoCard[] cardsToPlayer;
        TrucoPlayer pl;
        try{
            for (i=0; i<cantidadDePlayers; i++){
                pl = teams[i%2].getPlayerNumber(i/2);
                System.out.println("repartiendo a team"+i%2+"player"+i/2);
                cardsToPlayer = statusTable.getPlayerCards(i); //2pregunto al estado de la mesa cuales son sus cartas
                if(cardsToPlayer==null)
                    System.out.println("erro null en dealtcards");
                game.dealtCards(pl,cardsToPlayer); //3les envio al player las cartas
              /*pl = teams[0].getPlayerNumber(i);
              cardsToPlayer = statusTable.getPlayerCards((2*i)); //2pregunto al estado de la mesa cuales son sus cartas
              game.dealCards(pl,cardsToPlayer); //3les envio al player las cartas
              pl = teams[1].getPlayerNumber(i);
              cardsToPlayer = statusTable.getPlayerCards((2*i+1)); // " '" """"2
              game.dealCards(pl,cardsToPlayer); //" """ 3*/
            }
            game.fireCardsDealt();
            System.out.println("cartas repartidas..?");
        }
        catch (InvalidPlayExcepcion e){
              throw e;
        }
    }
    /*asignar al player el turno*/
    private void playTurn() throws InvalidPlayExcepcion{
        System.out.println("dando turno");
        if(statusTable.estaCerrado(playTurnNumber)){
            System.out.println(game.getNumberOfHand()+"esta cerrado" + playTurnNumber);
            nextPlayTurn();
            return;
        }
        playTurn = teams[playTurnNumber%2].getPlayerNumber(playTurnNumber/2);
        System.out.println(game.getNumberOfHand()+"turno de "+playTurn.getName());
        game.fireTurnEvent(playTurn,TrucoEvent.TURNO_JUGAR_CARTA);
    }
    private void nextPlayTurn() throws InvalidPlayExcepcion{
        cartasJugadas++;
        if (cartasJugadas%cantidadDePlayers == 0){
            System.out.println("fin de ronda");
            winRound[numeroDeRonda-1] = statusTable.resultadoRonda(numeroDeRonda); /*fin de ronda*/
            
            if(winRound[numeroDeRonda-1]>=0)
                playTurnNumber = winRound[numeroDeRonda-1];
            else
                playTurnNumber = (playTurnNumber+1)%cantidadDePlayers;/*siguiente player*/
            
            System.out.println(winRound[numeroDeRonda-1]);
            statusTable.terminoRonda();
            if(finDeRonda())
                return;
            numeroDeRonda++;
            volverAEstadoDeJuego();
        }
        else{
            playTurnNumber = (playTurnNumber+1)%cantidadDePlayers;/*siguiente player*/
        }
        playTurn();/*asignar turno*/
    }
    private boolean finDeRonda() throws InvalidPlayExcepcion{
        System.out.println("controlar el final de la mano");
        sePuedeCantarFlor = false;
        sePuedeCantarEnvido = false;
        
       if(numeroDeRonda == 1)/*en la primera ronda no se decide nada*/
            return false;
       int t1=0; //rondas al team 1;
       int t2=0; //ronda al team 2;
       int e = 0;
       for(int i=0; i<numeroDeRonda;i++){
           System.out.println("gandador de ronda" + i + " = " +winRound[i]);
           if(winRound[i]<0){
               e++;
               System.out.println("empate"+e);
           }
           if(winRound[i]>=0 && winRound[i]%2==0){
               t1++;
               System.out.println("gano t1 : "+t1);
           }
           if(winRound[i]>=0 && winRound[i]%2 == 1){
              t2++;
              System.out.println("gano t2: "+ t2);
           }
       }
       if(numeroDeRonda==2){ //en dos rondas
           if(e==2) /*empate las dos rondas se decide en la tercera*/
               return false;
           if (t1 == t2) //1 punto para cada , se decide en tercera
               return false;
           if (t1 == 0){ //1 0 2 para equipo 1, gano la mano
               finDeMano(1);
               return true;
           }
           if(t2 == 0){
               finDeMano(0); //gano equipo 2
               return true;
           }
           return false;
       }
       else{
           int tercera = winRound[2];
           if(tercera >= 0)
               finDeMano(tercera%2);//ganador de tercera
           else
               finDeMano(primerTurnoNumber); //gano, equipo mano gana
       }
       return true;
    }
    /** avisaa al trucoHand sobre el final de la mano
     */    
    private void finDeMano(int win) throws InvalidPlayExcepcion{
        System.out.println("equipo 0 :" + points[0]);
        System.out.println("equipo 1 :" + points[1] + "\nahora se verifica");
        
        if(huboRondaDeEnvido){

            int resultadoEnvido = statusTable.resultadoEnvido();
            if (resultadoEnvido<0)
                resultadoEnvido = primerTurnoNumber;
            if(resultadoEnvido%2 == 0 )
                points[0] = points[0] + pointsOfEnvido;
            else
                points[1] = points[1] + pointsOfEnvido;
        }
        System.out.println("puntos de envido" + pointsOfEnvido);
        System.out.println("equipo 0 :" + points[0]);
        System.out.println("equipo 1 :" + points[1]);
        /*controlas los puntos por flor*/
        for (int i=0; i<cantidadDePlayers;i++){ 
            
            if(statusTable.mostroFlor(i)){ /*si el player canto flor*/
                if(statusTable.jugoTresCartas(i)){ //coloco en mesa
                    points[i%2] = points[i%2] + 3;//tres puntos porque mostro
                    System.out.println(i+"cantoFlor y jugo sus tres cartas");
                }
                else{
                    points[(i+1)%2] = points[(i+1)%2] +3; //se le da 3 al equipo contrario penalizacion por no mostrar
                    System.out.println(i+"canto flor pero no jugo sus tres cartas");
                }
            }
            else{//si no canto
                if(statusTable.tieneFlor(i)){ //si podia cantar flor
                    if(statusTable.jugoTresCartas(i)){ //y mostro que tenia
                        points[(i+1)%2] =  points[(i+1)%2] + 3; //tres puntos para equipo contrario por penalizacion
                        System.out.println( i + "tenia flor y jugo sus tres cartas");
                    }
                }
            }
        }
        System.out.println("puntos de la mano"+ pointsOfHand);
        if(win%2 == 0){
            points[0] = points[0] + pointsOfHand;
        } 
        else{
            points[1] = points[1] + pointsOfHand;
        }
        System.out.println("el ganador es" + (teams[win%2].getPlayerNumber(win/2)).getName() + "\nFinal de ronda:" + game.getNumberOfHand());
        System.out.println("puntos de la mano" + pointsOfHand);
        System.out.println("puntos de la mano" + pointsOfEnvido);
        System.out.println("equipo 0 :" + points[0]);
        System.out.println("equipo 1 :" + points[1]);
        
        game.fireEndOfHandEvent();
    }
    private void envidoTurn() throws InvalidPlayExcepcion{
        if(statusTable.estaCerrado(envidoTurnNumber)){
            nextEnvidoTurn();
            return;
        }
        System.out.println("aqui?"+envidoTurnNumber);
    	playTurn = teams[envidoTurnNumber%2].getPlayerNumber(envidoTurnNumber/2);
        game.fireTurnEvent(playTurn,TrucoEvent.TURNO_CANTAR_ENVIDO);
    }
    private void nextEnvidoTurn() throws InvalidPlayExcepcion{
    
        do{
            System.out.println("aqui?"+envidoTurnNumber);
            envidoTurnNumber=(++envidoTurnNumber)%cantidadDePlayers; //siguiente turno
            System.out.println("y aqui?"+envidoTurnNumber);
            if(envidoTurnNumber == primerTurnoNumber){ /*si termino la ronda de envidos*/
            	finDeEnvido();    		
                return;
            }
            
        }
        while(statusTable.estaCerrado(envidoTurnNumber));
        envidoTurn(); //asignar player
    }
    /*no esta habilitado*/
    /*void florTurn() throws InvalidPlayExcepcion {
    	if(!statusTable.mostroFlor(florTurnNumber))
            throw (new InvalidPlayExcepcion("\n**Excepcion de turno en florTurn **"));
    	playTurn = teams[florTurnNumber%2].getPlayerNumber(florTurnNumber/2);
    }
    void nextFlorTurn() throws InvalidPlayExcepcion{
            while(!statusTable.mostroFlor(florTurnNumber)){
    		florTurnNumber=(florTurnNumber+1)%cantidadDePlayers;
    		if(florTurnNumber == primerTurnoNumber){
    			finDeFlor();
    			return;
    		}
            }
        try{
            florTurn();
        }
        catch(InvalidPlayExcepcion e){
            throw e;
        }
    }*/
    /** realizar jugada
     * @param tp
     */    
/*esta funcion tengo que cambiar con choco*/
    public boolean esPosibleJugar(TrucoPlay tp) throws InvalidPlayExcepcion{
        int numPlayer = getNumberOfPlayer(tp.getPlayer());
        System.out.println("verificando"+tp.getType());
        switch(tp.getType()){

            case TrucoPlay.ENVIDO:
               if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
               if(sePuedeCantarEnvido(tp)>0)
                   return false;
               break;
        
            case TrucoPlay.REAL_ENVIDO:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                if(sePuedeCantarEnvido(tp)>0)
                   return false;
               break;
            
            case TrucoPlay.FALTA_ENVIDO:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                if(sePuedeCantarEnvido(tp)>0)
                   return false;
               break;
            
            case TrucoPlay.TRUCO:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
               
                if(playTurn != tp.getPlayer() || (estadoActual==PRIMERA_RONDA && playTurn != pie1 && playTurn != pie2))
                    return false; /*el player no puede cantar TRUCO ahora*/
                if(truco)
                    return false;/*("Ya se canto truco, no se puede cantar mas nada"));*/
                if(laPalabra!=null)
                    return false; /*el el equipo no tiene la palabra*/
                if(estadoActual!=PRIMERA_RONDA && estadoActual != SEGUNDA_RONDA && estadoActual != TERCERA_RONDA)
                    return false; /*no hay estado para cantar*/
                
                break;
            
            case TrucoPlay.RETRUCO:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                if(playTurn != tp.getPlayer() || (estadoActual==PRIMERA_RONDA && playTurn != pie1 && playTurn != pie2))
                    return false; /*el player no puede cantar ahora*/
                if(!truco)
                    return false; /*tdvia no se canto truco*/
                if(retruco)
                    return false;/*("Ya se canto retruco, no se puede cantar mas nada"));*/
                if(!laPalabra.isPlayerTeam(playTurn))
                    return false; /*el el equipo no tiene la palabra*/
            
                if(estadoActual!=PRIMERA_RONDA && estadoActual != SEGUNDA_RONDA && estadoActual != TERCERA_RONDA && estadoActual != ESPERANDO_RESPUESTA_DEL_TRUCO)
                    return false; /*no es posible cantar*/

                break;
            
            case TrucoPlay.VALE_CUATRO:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
            
                if(playTurn != tp.getPlayer() || (estadoActual==PRIMERA_RONDA && playTurn != pie1 && playTurn != pie2))
                    return false; /*el player no puede cantar, */
                if(!retruco)
                    return false; /*tdvia no se canto truco*/
                if(valecuatro)
                    return false; /*ya no se puede nada, se canto vale cuatro*/
                if(!laPalabra.isPlayerTeam(playTurn))
                    return false; /*el equipo no tiene la palabra*/
                if(estadoActual!=PRIMERA_RONDA && estadoActual != SEGUNDA_RONDA && estadoActual != TERCERA_RONDA && estadoActual != ESPERANDO_RESPUESTA_DEL_RETRUCO)
                    return false; /*en este estado no se puede cantar*/

                break;

            case TrucoPlay.QUIERO:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
            
                if(playTurn!=pie1 && playTurn!=pie2)
                    return false; /*solamente los pies pueden responder eso*/
                if(estadoActual != ENVIDO && estadoActual != REAL_ENVIDO && estadoActual != FALTA_ENVIDO && estadoActual != ESPERANDO_RESPUESTA_DEL_TRUCO
                && estadoActual != ESPERANDO_RESPUESTA_DEL_RETRUCO && estadoActual!=ESPERANDO_RESPUESTA_DEL_VALECUATRO)
                    return false; //no se reponde nada
                break;
            
            case TrucoPlay.NO_QUIERO:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                if(playTurn!=pie1 && playTurn!=pie2)
                    return false; /*solamente los pies pueden responder eso*/
                if(estadoActual != ENVIDO && estadoActual != REAL_ENVIDO && estadoActual != FALTA_ENVIDO && estadoActual != ESPERANDO_RESPUESTA_DEL_TRUCO
                && estadoActual != ESPERANDO_RESPUESTA_DEL_RETRUCO && estadoActual!=ESPERANDO_RESPUESTA_DEL_VALECUATRO)
                    return false; //no se reponde nada
                
                break;
            
            case TrucoPlay.ME_VOY_AL_MAZO:
                if(tp.getPlayer() != playTurn)
                    return false;
                if(tp.getPlayer()!=pie1 && tp.getPlayer() != pie2)
                    return false; /*solamente los pies puede llevar al mazo al equipo*/
                break;
            
            case TrucoPlay.JUGAR_CARTA:
               if(tp.getPlayer() != playTurn)
                   return false; //no es el turno, no puede ju
               if (estadoActual != PRIMERA_RONDA && estadoActual != SEGUNDA_RONDA && estadoActual != TERCERA_RONDA)
                   return false; //solamente en juegos de ronda se puede jugar carta
                     /*aqui>>>>>*/
               if (!statusTable.puedeJugarCarta(playTurnNumber, tp.getCard()))
                   return false;/*no tiene esa carta para jugar*/
                break;
            
            case TrucoPlay.PASO_ENVIDO:
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                if(estadoActual != RONDA_DE_ENVIDO) 
                    return false; /*no puede pasar envido porque no es ronda*/
                break;
            
            case TrucoPlay.PASO_FLOR:
               // if(estadoActual != RONDA_DE_ENVIDO)
                        return false;
            
            
            case TrucoPlay.CERRARSE:
                System.out.println("verificando que se cierre"+playTurnNumber);
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                
                if(statusTable.estaCerrado(playTurnNumber)){
                    System.out.println("ya esta cerrado");
                    return false;
                }
                break;
            
            case TrucoPlay.CANTO_ENVIDO:
                
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                
                if(estadoActual != RONDA_DE_ENVIDO)
                    return false;
                
                if(!statusTable.tieneEnvido(envidoTurnNumber,tp.getValue()))
                    return false;
                
                break;
            
            case TrucoPlay.FLOR:
                /*VERIFICANDO LA FLOR*/
                if(tp.getPlayer() != playTurn)
                    return false; //no es el turno, no puede ju
                System.out.println("vericacion a" + playTurn.getName());
                
                if(!statusTable.puedeCantarFlor(getNumberOfPlayer(playTurn))){
                    return false;
                }
                
                if(!sePuedeCantarFlor){
                    return false;
                }
                
                if(estadoActual!=PRIMERA_RONDA && estadoActual!=ENVIDO && estadoActual != REAL_ENVIDO && estadoActual != FALTA_ENVIDO){
                    return false; /*fuera de estos estados no se puede cantar*/
                    }
                break;
                
            default:
                System.out.println("no esta definido el juego"+tp.getType());
                return false;
                
        }
        System.out.println("verificacion verdadera");
        return true;
    }
    /** metodo para realizar las jugadas
     * @param tp
     */    
    public void play(TrucoPlay tp) throws InvalidPlayExcepcion{
        try{
            switch(tp.getType()){
                case  62: /*Jugar carta*/
                    jugarCarta(tp);
                    break;
                case TrucoPlay.ENVIDO:
                    cantarEnvido(tp);/*cantar envido*/
                    break;
                case TrucoPlay.REAL_ENVIDO:
                    cantarEnvido(tp);/*cantar real envido*/
                    break;
                case TrucoPlay.FALTA_ENVIDO:
                    cantarEnvido(tp);/*cantar falta envido*/
                    break;
               	case TrucoPlay.QUIERO:
                    quiero(tp);/*responder - quiero*/
                    break;
               			
               	case TrucoPlay.NO_QUIERO:
                    noQuiero(tp);/*responde - no quiero*/
                    break;
               	
               	case TrucoPlay.CANTO_ENVIDO:
                    rondaEnvido(tp); /*canto mi tanto*/
                    break;
               	
               	case TrucoPlay.PASO_ENVIDO:
                    rondaEnvido(tp);/*paso o no canto "golpear la mesa"*/
                    break;
               	case TrucoPlay.FLOR:
                    cantarFlor(tp);
                    break;
               	/**case TrucoPlay.CONTRA_FLOR:
                    cantarFlor(tp);
                    break;  No esta habilitado*/
                /*case TrucoPlay.CANTO_FLOR:
                    rondaFlor(tp);
                    break; *//*No esta Habilitado*/
                case TrucoPlay.TRUCO:
                    cantarTruco(tp);
                    break;
                case TrucoPlay.RETRUCO:
                    cantarTruco(tp);
                    break;
                case TrucoPlay.VALE_CUATRO:
                    cantarTruco(tp);
                    break;
                case TrucoPlay.CERRARSE:
                    cerrarse(tp);
                    break;
                
                case TrucoPlay.ME_VOY_AL_MAZO:
                    meVoyAlMazo(tp);
                    break;
                default:
                    throw(new InvalidPlayExcepcion ("+++++ error muy grave!!!!!!!!!!!!!!!!"));
            }
        }
        catch(InvalidPlayExcepcion e){
        	throw e;
        }
        
    }
    private void meVoyAlMazo() throws InvalidPlayExcepcion{
        System.out.println("quien se cerro"+playTurnNumber);
        System.out.println("actual,"+(playTurnNumber+1)+"siguiente"+primerTurnoNumber);
        if(estadoActual == PRIMERA_RONDA && sePuedeCantarEnvido && cartasJugadas < cantidadDePlayers-1){ //puntos de penalizacion por retirarse en primera ronda, habiendo posibilidad que el equipo contrario cante envido
            points[(playTurnNumber+1)%2] = points[(playTurnNumber+1)%2] + 1; 
            System.out.println("penalizacion por retirarse");
        }
        System.out.println("gana" + (playTurnNumber+1));
        finDeMano((playTurnNumber+1)%2);
    }
    private void meVoyAlMazo(TrucoPlay tp) throws InvalidPlayExcepcion{
        if(tp.getPlayer() != playTurn)
            throw (new InvalidPlayExcepcion("No es el turno de ese player"));
      
        if(tp.getPlayer() != pie1 && tp.getPlayer() != pie2)
            throw (new InvalidPlayExcepcion("solamente los pies pueden retirarse"));
        
        if(estadoActual != PRIMERA_RONDA && estadoActual != SEGUNDA_RONDA && estadoActual != TERCERA_RONDA)
            throw (new InvalidPlayExcepcion("No es posible cerrarse en este momento"));
        if(tp.getPlayer() == pie1)
            playTurnNumber = pieTeam1Number;
        else
            playTurnNumber = pieTeam2Number;
        
        meVoyAlMazo();
        
    }
    private void cerrarse (TrucoPlay tp) throws InvalidPlayExcepcion{
        
       int cerradosDelTeam1=0; 
       int cerradosDelTeam2=0; 
       
        if(tp.getPlayer() != playTurn)
            throw (new InvalidPlayExcepcion("No es el turno de ese player"));
        if(estadoActual != PRIMERA_RONDA && estadoActual != SEGUNDA_RONDA && estadoActual != TERCERA_RONDA)
            throw (new InvalidPlayExcepcion("No es posible cerrarse en este momento"));
        if(!statusTable.seCerro(playTurnNumber))
            throw (new InvalidPlayExcepcion("No pudo cerrarse - ver con choco"));
       
        for(int i=0; i<cantidadDePlayers; i++){
            if(statusTable.estaCerrado(i)){
                if(i%2 == 0)
                {
                    System.out.println("parece que se cerro 0"+playTurnNumber);
                    cerradosDelTeam1++;
                }
                else
                {
                    System.out.println("parece que se cerro 1"+playTurnNumber);;
                    cerradosDelTeam2++;
                }
            }
        }
       System.out.println("pie1: "+pieTeam1Number+pie1.getName());
       System.out.println("pie2: "+pieTeam2Number+pie2.getName());
        if(cerradosDelTeam1 == cantidadDePlayers/2){
            playTurnNumber = pieTeam1Number;
            System.out.println("perdio 0,choco"+playTurnNumber);
            game.firePlayEvent(tp.getPlayer(),tp.getType());
            meVoyAlMazo();
            return;
        }
        if(cerradosDelTeam2 == cantidadDePlayers/2){
            playTurnNumber = pieTeam2Number;
            System.out.println("perdio 1,julio"+playTurnNumber);
            game.firePlayEvent(tp.getPlayer(),tp.getType());
            meVoyAlMazo();
            return;
        }
        game.firePlayEvent(tp.getPlayer(),tp.getType());
        nextPlayTurn();
        
    }
    private void rondaEnvido(TrucoPlay tp) throws InvalidPlayExcepcion{

    	if(tp.getPlayer() != playTurn)
    		throw (new InvalidPlayExcepcion("TrucoHand - rondaEnvido(TrucoPlay): no es tu turno"+estadoActual));

    	if(estadoActual != RONDA_DE_ENVIDO )
    		throw (new InvalidPlayExcepcion("TrucoHand - rondaEnvido(TrucoPlay): no es ronda de envido"+estadoActual));
    	
        if(tp.getType() == TrucoPlay.CANTO_ENVIDO){
    	System.out.println("se cantaa?");
    		if(!statusTable.jugarEnvido(envidoTurnNumber,tp.getValue()))
    			throw (new InvalidPlayExcepcion("TrucoHand - rondaEnvido(TrucoPlay): no puedes cantar ese envido"+estadoActual));
    		game.firePlayEvent(tp.getPlayer(),TrucoEvent.CANTO_ENVIDO,tp.getValue());
    	
    	}
    	else{
    		game.firePlayEvent(tp.getPlayer(),TrucoEvent.PASO_ENVIDO);
    	}
        System.out.println("parece que pasamos"+tp.getType());
        game.firePlayEvent(tp.getPlayer(),TrucoEvent.CANTO_ENVIDO,tp.getValue());
    	nextEnvidoTurn();
    }/*No esta Habilitado
    private void rondaFlor(TrucoPlay tp) throws InvalidPlayExcepcion{
    	if(tp.getPlayer() != playTurn)
               throw (new InvalidPlayExcepcion("No es el turno de ese jugador"));
        if(estadoActual != RONDA_DE_FLOR)
                throw (new InvalidPlayExcepcion("No es ronda de flor"));
        
         
    }*/
    private void quiero(TrucoPlay tp) throws InvalidPlayExcepcion{

    	if(tp.getPlayer() != playTurn)
            throw (new InvalidPlayExcepcion("no es el turno de ese player"));
        if(tp.getPlayer() != pie1 && tp.getPlayer() != pie2)
            throw (new InvalidPlayExcepcion("solamente el pie puede responder"+pie1.getName()+pie2.getName()));
                
    		
   	
    	try{
    		switch(estadoActual){
    			case ENVIDO:
    				quieroEnvido(tp);
        			break;
                	case REAL_ENVIDO:
	   			quieroEnvido(tp);
	   			break;
    			case FALTA_ENVIDO:
    				quieroEnvido(tp);
    				break;
    			/*case CONTRAFLOR:
    				quieroContraflor(tp);
    				break;
                        case CONTRAFLOR_AL_RESTO:
    				quieroContraflor(tp);
    				break;*/
                        case ESPERANDO_RESPUESTA_DEL_TRUCO:
                                quieroTruco(tp);
                                break;
                        case ESPERANDO_RESPUESTA_DEL_RETRUCO:
                                quieroTruco(tp);
                                break;
                        case ESPERANDO_RESPUESTA_DEL_VALECUATRO:
                                quieroTruco(tp);
                                break;
                        default :
                            throw (new InvalidPlayExcepcion("TrucoHand quiero(TrucoPlay): No se responde a nada"));
    				
	    	}
	 	}
	 	catch(InvalidPlayExcepcion e){
	 		throw e;
		}
	}
        private void quieroTruco(TrucoPlay tp) throws InvalidPlayExcepcion{
            if(tp.getPlayer() != playTurn)
                throw(new InvalidPlayExcepcion("no es el turno de ese jugador"));
            if(playTurn != pie1 && playTurn != pie2)
                throw (new InvalidPlayExcepcion("solamente el pie puede responder el quiero"));
            if(estadoActual == ESPERANDO_RESPUESTA_DEL_TRUCO){
                pointsOfHand = 2;
            }
            if (estadoActual == ESPERANDO_RESPUESTA_DEL_RETRUCO){
                pointsOfHand = 3;
            }
            if (estadoActual == ESPERANDO_RESPUESTA_DEL_VALECUATRO){
                pointsOfHand = 4;
                
            }
            game.firePlayEvent(tp.getPlayer(),tp.getType());
            volverAEstadoDeJuego();
            playTurn();
        }
	/*NoHabilitadoprivate void quieroContraflor(TrucoPlay tp){
		if(estadoActual != CONTRAFLOR)
		
			game.firePlayEvent(tp.getPlayer(),tp.getType());
			estadoActual = RONDA_DE_FLOR;
	}*/
	private void quieroEnvido(TrucoPlay tp) throws InvalidPlayExcepcion{
		

		sePuedeCantarEnvido = false; /*ya no se puede volver cantar envido*/
		sePuedeCantarFlor = false; /*ya no se puede cantar flor*/
                huboRondaDeEnvido = true; 
		
		if(estadoActual == FALTA_ENVIDO)
                    pointsOfEnvido = game.getFaltear();
		else
                    pointsOfEnvido = numberOfEnvidos*2 + numberOfRealEnvidos*3; //puntajes que se jugaran

                estadoActual = RONDA_DE_ENVIDO;
                game.firePlayEvent(tp.getPlayer(),tp.getType());
		envidoTurn();
	}
	private void noQuiero(TrucoPlay tp) throws InvalidPlayExcepcion{
		if(tp.getPlayer() != playTurn)
			throw (new InvalidPlayExcepcion("no es el turno de ese player"));
                if(tp.getPlayer() != pie1 && tp.getPlayer() != pie2)
                    throw (new InvalidPlayExcepcion("solamente el pie puede responder"+pie1.getName()+pie2.getName()));
			
		
		try{
			switch(estadoActual){
				case ENVIDO:
					noQuieroEnvido(tp);
					break;
				case REAL_ENVIDO:
					noQuieroEnvido(tp);
					break;
				case FALTA_ENVIDO:
					noQuieroEnvido(tp);
					break;
                                case ESPERANDO_RESPUESTA_DEL_TRUCO:
                                        noQuieroTruco(tp);
                                        break;
                                case ESPERANDO_RESPUESTA_DEL_RETRUCO:
                                        noQuieroTruco(tp);
                                        break;
                                case ESPERANDO_RESPUESTA_DEL_VALECUATRO:
                                        noQuieroTruco(tp);
                                        break;
			}
		}
		catch(InvalidPlayExcepcion e){
                    throw e;
		}
    }
    private void noQuieroEnvido(TrucoPlay tp) throws InvalidPlayExcepcion{
    	
        if(estadoActual != ENVIDO && estadoActual != REAL_ENVIDO && estadoActual != FALTA_ENVIDO )
            throw (new InvalidPlayExcepcion("No se esta respondiendo a nada"));
    	
    	if((numberOfEnvidos+numberOfRealEnvidos)==1){
    		pointsOfEnvido =1;
    	}
    	else{
    		if(estadoActual == FALTA_ENVIDO )
                    pointsOfEnvido =numberOfEnvidos*2 + numberOfRealEnvidos*3; //puntajes que se jugaran
	   	if(estadoActual == ENVIDO )
                    pointsOfEnvido = (numberOfEnvidos-1)*2+numberOfRealEnvidos*3;
	   	if(estadoActual == REAL_ENVIDO)
                    pointsOfEnvido = (numberOfRealEnvidos-1)*3+numberOfEnvidos*2;
	}
        if(tp.getPlayer() == pie1){ //al no querer ya se asigna los puntos
            //puntajes.add(new String(pointsOfEnvido + "al equipo: " + teams[1).getName + " por Envido no querido");
            points[1] = pointsOfEnvido + points[1];
        }
        if(tp.getPlayer() == pie2){
            //puntajes.add(new String(pointsOfEnvido + "al equipo: " + teams[0).getName + " por Envido no querido");
            points[0] = pointsOfEnvido + points[0];
        }
        game.firePlayEvent(tp.getPlayer(),tp.getType());
        if(elEnvidoEstaPrimero){
            game.firePlayEvent(tp.getPlayer(),tp.getType());
            elEnvidoEstaPrimero();
            return;
        }
        else{
            game.firePlayEvent(tp.getPlayer(),tp.getType());
            playTurn();
            volverAEstadoDeJuego();
        }
    }
    private void noQuieroTruco(TrucoPlay tp) throws InvalidPlayExcepcion{
        if(tp.getPlayer() != playTurn)
            throw(new InvalidPlayExcepcion("No es el turno de ese player"));
        if(tp.getPlayer() != pie1 && tp.getPlayer() != pie2)
            throw(new InvalidPlayExcepcion ("Solamente los pies pueden aceptar o no"));
        if (estadoActual != ESPERANDO_RESPUESTA_DEL_TRUCO && estadoActual != ESPERANDO_RESPUESTA_DEL_RETRUCO && estadoActual != ESPERANDO_RESPUESTA_DEL_VALECUATRO)
            throw(new InvalidPlayExcepcion("noQuieroTruco - No se esta respondiendo a nada"));
        game.firePlayEvent(tp.getPlayer(),tp.getType());
        if(teams[0].isPlayerTeam(playTurn))
        {
            
            finDeMano(1);
        }
        else
        {
            
            finDeMano(0);
        }
        
    }
    /*el player juega una carta*/
    private void jugarCarta(TrucoPlay tp) throws InvalidPlayExcepcion{ //alguien juega una carta
        
        System.out.println("estado actual" + estadoActual);
        if (estadoActual != PRIMERA_RONDA && estadoActual != SEGUNDA_RONDA && estadoActual != TERCERA_RONDA)
            throw(new InvalidPlayExcepcion("TrucoHand - jugarCarta(TrucoPlay ) > No se puede jugar carta"));
        if(playTurn != tp.getPlayer())
                throw(new InvalidPlayExcepcion("TrucoHand - jugarCarta(TrucoPlay ) > No es el turno de ese player"));
        if (!statusTable.jugarCarta(playTurnNumber, tp.getCard())) //*****************************funcion que necesito de choco
                throw(new InvalidPlayExcepcion("TrucoHand - jugarCarta(TrucoPlay ) > el player no puede jugar esa carta"));
        game.firePlayEvent(playTurn,tp.getCard(),TrucoEvent.JUGAR_CARTA);
        
        
        nextPlayTurn();
    }
    private int sePuedeCantarEnvido (TrucoPlay tp) {
        if (!sePuedeCantarEnvido)
            /*throw (new InvalidPlayExcepcion("TrucoHand - cantarEnvido(TrucoPlay) : no se puede mas cantar envido"));*/
            return 1;
        if(playTurn != tp.getPlayer())
            /*throw(new InvalidPlayExcepcion("TrucoHand - jugarCarta(TrucoPlay ) > No es el turno de ese player"));*/
            return 2;
	if (tp.getPlayer()!=pie1 && tp.getPlayer()!=pie2)
            /*throw (new InvalidPlayExcepcion("TrucoHand - cantarEnvido(TrucoPlay) : solamente los pies pueden cantar envido"));*/
            return 3;
        if ((numberOfEnvidos+numberOfRealEnvidos) >= 3)
            /*throw (new InvalidPlayExcepcion("TrucoHand - cantarEnvido(TrucoPlay) : solamente se puede cantar hasta 3 veces"));*/
            return 4;
        if(estadoActual != PRIMERA_RONDA && estadoActual != ENVIDO && estadoActual != REAL_ENVIDO)
           /*throw (new InvalidPlayExcepcion("TrucoHand - cantarEnvido(TrucoPlay) : el player no puede cantar falta envido porque ya se le canto"));*/
            return 5;
        return 0;
    }
    /*El Player canta envido, real envido o falta envido>>>>>>>*/
        
    private void cantarEnvido(TrucoPlay tp) throws InvalidPlayExcepcion{ //cantar envido
        
            if(sePuedeCantarEnvido(tp)>0)
                throw(new InvalidPlayExcepcion("error al cantar el envido"+sePuedeCantarEnvido(tp)));
        
        
/*        if (!sePuedeCantarEnvido)
        	throw (new InvalidPlayExcepcion("TrucoHand - cantarEnvido(TrucoPlay) : no se puede mas cantar envido"));
        
        if(playTurn != tp.getPlayer())
        	throw(new InvalidPlayExcepcion("TrucoHand - jugarCarta(TrucoPlay ) > No es el turno de ese player"));
		
	if (tp.getPlayer()!=pie1 && tp.getPlayer()!=pie2)
            throw (new InvalidPlayExcepcion("TrucoHand - cantarEnvido(TrucoPlay) : solamente los pies pueden cantar envido"));
            
        if ((numberOfEnvidos+numberOfRealEnvidos) > 3)
        	throw (new InvalidPlayExcepcion("TrucoHand - cantarEnvido(TrucoPlay) : solamente se puede cantar hasta 3 veces"));*/
            
        byte respuesta = ENVIDO; //que respuesta enviar
        
        switch(estadoActual){
        	case PRIMERA_RONDA:
                	if(tp.getType() == TrucoPlay.ENVIDO){
                            respuesta = TrucoEvent.TURNO_RESPONDER_ENVIDO;
                            numberOfEnvidos = 1; //primer envido
                            estadoActual = ENVIDO;
                        }   
                        if(tp.getType() == TrucoPlay.REAL_ENVIDO){
                            respuesta = TrucoEvent.TURNO_RESPONDER_REALENVIDO;
                            estadoActual = REAL_ENVIDO;
                            numberOfRealEnvidos = 1; //primer real
                        }
                        if(tp.getType() == TrucoPlay.FALTA_ENVIDO){
                            respuesta = TrucoEvent.TURNO_RESPONDER_FALTAENVIDO;
                            estadoActual = FALTA_ENVIDO;
                        }
                        break;
           	case ENVIDO: //ya se habria cantado envido
            
                        sePuedeCantarFlor = false; //ya no se puede cantar flor
                                   	         	
                        if(tp.getType() == TrucoPlay.ENVIDO){
                            respuesta = TrucoEvent.TURNO_RESPONDER_ENVIDO;
                            numberOfEnvidos = numberOfEnvidos + 1; //otro envido
                            estadoActual = ENVIDO;
                        }
                        if(tp.getType() == TrucoPlay.REAL_ENVIDO){
                            respuesta = TrucoEvent.TURNO_RESPONDER_REALENVIDO;
                            numberOfRealEnvidos = numberOfRealEnvidos +1; //otro real
                            estadoActual = REAL_ENVIDO;
                        }
                        if(tp.getType() == TrucoPlay.FALTA_ENVIDO){
                            respuesta = TrucoEvent.TURNO_RESPONDER_FALTAENVIDO;
                            estadoActual = FALTA_ENVIDO;
                        }
                 break;
                    
           	case REAL_ENVIDO:
                        sePuedeCantarFlor = false; //ya no se puede cantar flor
                 
                        if(tp.getType() == TrucoPlay.ENVIDO){
                            respuesta = TrucoEvent.TURNO_RESPONDER_ENVIDO;
                            numberOfEnvidos = numberOfEnvidos + 1; //otro envido
                            estadoActual = ENVIDO;
                        }
                        if(tp.getType() == TrucoPlay.REAL_ENVIDO){
                            respuesta = TrucoEvent.TURNO_RESPONDER_REALENVIDO;
                            numberOfRealEnvidos = numberOfRealEnvidos +1; //otro real
                            estadoActual = REAL_ENVIDO;
                        }
                        if(tp.getType() == TrucoPlay.FALTA_ENVIDO){
                            respuesta = TrucoEvent.TURNO_RESPONDER_FALTAENVIDO;
                            estadoActual = FALTA_ENVIDO;
                        }
                        break;
              	case FALTA_ENVIDO:
                        throw (new InvalidPlayExcepcion("TrucoHand - cantarEnvido(TrucoPlay) : el player no puede cantar falta envido porque ya se le canto"));
            
                case ESPERANDO_RESPUESTA_DEL_TRUCO:
            	
                    
                    sePuedeCantarFlor = false; //ya no se puede cantar flor
                    elEnvidoEstaPrimero = true; //avisar que tiene que responder a truco despues de la ronda
                    
                   
            	
                    if(tp.getType() == TrucoPlay.ENVIDO){
                 	
                 	respuesta = TrucoEvent.TURNO_RESPONDER_ENVIDO;
                   	numberOfEnvidos = 1; //primer envido
                        estadoActual = ENVIDO;
                    }
                    if(tp.getType() == TrucoPlay.REAL_ENVIDO){
                 	respuesta = TrucoEvent.TURNO_RESPONDER_REALENVIDO;
                        estadoActual = REAL_ENVIDO;
                        numberOfRealEnvidos = 1; //primer real
                    }
                    if(tp.getType() == TrucoPlay.FALTA_ENVIDO){
                        respuesta = TrucoEvent.TURNO_RESPONDER_FALTAENVIDO;
                        estadoActual = FALTA_ENVIDO;
                    }
                    break;
                default:
                    throw (new InvalidPlayExcepcion("TrucoHand - cantarEnvido(TrucoPlay) : en este estado no se puede cantar envido"));
               
            }
            game.firePlayEvent(tp.getPlayer(),tp.getType());
            esperarRespuesta();
            game.fireTurnEvent(playTurn,respuesta);
    }
    /*cambiar turno y esperar respuesta*/
    private void esperarRespuesta(){ //asignar turnos al pie
        
        if(teams[0].isPlayerTeam(playTurn)){
            playTurn = pie2;
        }
        else{
            playTurn = pie1;
        }
    }
    private int getNumberOfPlayer(TrucoPlayer tp){
        // RSHK - cambié (decia getNumberOfPLayer(tp)) porque no había definición correspondiente
       int numero = teams[1].getNumberOfPlayers();
         if(numero >= 0)
            return numero;
      System.out.println("opa, error grave - getNumberOfPlayer  - TH");
      return -1;
        
    }
    private void cantarFlor(TrucoPlay tp) throws InvalidPlayExcepcion{

        if(tp.getPlayer() != playTurn) /*no es el turno del jugador*/
            throw(new InvalidPlayExcepcion("No es el turno del jugador"));
    	if(!sePuedeCantarFlor) /*ya acepto envido o algun error porahi*/
            throw(new InvalidPlayExcepcion("ya no se puede cantar flor"));
    	if(!statusTable.cantaFlor(getNumberOfPlayer(tp.getPlayer())))
            throw(new InvalidPlayExcepcion("el player no puede cantar flor"));
        if(estadoActual!=PRIMERA_RONDA && estadoActual!=ENVIDO && estadoActual != REAL_ENVIDO && estadoActual != FALTA_ENVIDO)
            throw(new InvalidPlayExcepcion("en este estado no se puede cantar"+estadoActual));
            
    	
    	sePuedeCantarEnvido=false; /*ya no se puede cantar envido*/

    	if(playTurnNumber%2 == 0)
            equipo1_canto_flor = true;
    	else
            equipo2_canto_flor = true;
        
        game.firePlayEvent(tp.getPlayer(),tp.getType());
        volverAEstadoDeJuego();
        playTurn();
        
    }
    /*No esta habilitado>>*/
    /*public void cantarContraFlor(TrucoPlay tp) throws InvalidPlayExcepcion{

    	if(tp.getPlayer() != playTurn) /*no es el turno del jugador
    		throw(new InvalidPlayExcepcion("No es el turno del jugador"));
    	if(!sePuedeCantarFlor) /*ya acepto envido o algun error porahi
    		throw(new InvalidPlayExcepcion("ya no se puede cantar flor"));
    	if(!statusTable.CantaFlor(playTurnNumber))
    		throw(new InvalidPlayExcepcion("el player no puede cantar flor"));
    	if(playTurnNumber%2 == 0 && equipo2_canto_flor==false) /*canta equipo1 y equipo 2 tdvia tiene flor
    		throw(new InvalidPlayExcepcion("el equipo contrario tdvia canto flor"));
    	
    	if(playTurnNumber%2 != 0 && equipo1_canto_flor==false)/*canta equipo1 y equipo 2 tdvia tiene 
    		throw(new InvalidPlayExcepcion("el equipo contrario tdvia canto flor"));
    	
    	contraFlor=true;
    	sePuedeCantarEnvido=false; /*ya no se puede cantar envido
    	
    	game.firePlayEvent(tp.getPlayer(),tp.getType());
    	esperarRespuesta();
    	game.fireTurnEvent(playTurn,TrucoEvent.TURNO_RESPONDER_CONTRAFLOR);
    }*/
    /*public void contraFlor(TrucoPlay tp) throws InvalidPlayExcepcion{
    	
    }*/
    /**
     * @param tp
     * @throws InvalidPlayExcepcion
     */    
     private void cantarTruco(TrucoPlay tp) throws InvalidPlayExcepcion{
        if(playTurn != tp.getPlayer() || (estadoActual==PRIMERA_RONDA && playTurn != pie1 && playTurn != pie2))
            throw(new InvalidPlayExcepcion("el jugador no puede cantar truco"));
        if(valecuatro)
            throw(new InvalidPlayExcepcion("Ya se canto todo, no se puede cantar mas nada"));
        if(laPalabra!=null && !laPalabra.isPlayerTeam(playTurn))
            throw(new InvalidPlayExcepcion("ese equipo no puede cantar truco - no tiene la palabra"));
        if(estadoActual!=PRIMERA_RONDA && estadoActual != SEGUNDA_RONDA && estadoActual != TERCERA_RONDA && estadoActual != ESPERANDO_RESPUESTA_DEL_TRUCO && estadoActual != ESPERANDO_RESPUESTA_DEL_RETRUCO && estadoActual != ESPERANDO_RESPUESTA_DEL_VALECUATRO)
            throw(new InvalidPlayExcepcion("no es posible cantar truco"+ estadoActual));
        
        byte typeTurn=TrucoEvent.TRUCO;
        switch(tp.getType()){
            case TrucoPlay.TRUCO:
                if(truco)
                    throw(new InvalidPlayExcepcion ("Ya se canto truco"));//ya se canto truco
                truco = true; //se canto truco
                laPalabra = cambiarEquipo(playTurn);//cambio la palabra
                estadoActual = ESPERANDO_RESPUESTA_DEL_TRUCO;
                typeTurn = TrucoEvent.TURNO_RESPONDER_TRUCO;
                break;
                    
            case TrucoPlay.RETRUCO:
                if(retruco)
                    throw( new InvalidPlayExcepcion ("Ya se canto retruco"));//ya se canto retruco
                pointsOfHand = 2;
                retruco = true;//se canto retruco
                laPalabra = cambiarEquipo(playTurn);//cambio la palabra
                estadoActual = ESPERANDO_RESPUESTA_DEL_RETRUCO;
                typeTurn = TrucoEvent.TURNO_RESPONDER_RETRUCO;
                sePuedeCantarEnvido = false;
                break;
                    
            case TrucoPlay.VALE_CUATRO:
                if(valecuatro) 
                    throw(new InvalidPlayExcepcion ("Ya se canto vale Cuatro"));//ya se canto vale cuatro
                valecuatro = true; //se canto valecuatro
                pointsOfHand = 3;
                estadoActual = ESPERANDO_RESPUESTA_DEL_VALECUATRO;
                typeTurn = TrucoEvent.TURNO_RESPONDER_RETRUCO;
                sePuedeCantarEnvido = false;
                break;
        }
        game.firePlayEvent(tp.getPlayer(),tp.getType());
        esperarRespuesta();
        game.fireTurnEvent(playTurn,typeTurn);
        
    }
    private void finDeEnvido() throws InvalidPlayExcepcion{
         if(elEnvidoEstaPrimero){
            elEnvidoEstaPrimero();
            return;
        }
        volverAEstadoDeJuego();
        playTurn();
    }
    private Team cambiarEquipo(TrucoPlayer pl){
        if(teams[0].isPlayerTeam(pl))
            return teams[1];
        return teams[0];
    }
    /*void finDeFlor(){
    	int puntos;
    	if(contraFlorAlResto){
    		if(statusTable.resultadoFlor()%2== 0){
    			puntos = game.alResto(0); /*cuantos puntos le flata para ganar
    			points[0] = puntos + points[0];/*sumar puntos
    			//puntajes.add(new String("equipo: " + teams.getName(0) + " gano" + puntos + " puntos de Contra Flor Al Resto"));
    			/*lista de puntajes
    			
    		}
    		else{
    			puntos = game.alResto(1);/*cuantos puntos le falta para ganar
    			points[1] = puntos + points[1]; /*sumar puntos
    			//puntajes.add(new String("equipo: " + teams.getName(0) + " gano" + puntos + " puntos de Contra Flor Al Resto"));
    		}
    	}
    	else{
    		if(statusTable.resultadoFlor()%2 == 0){
    			puntos = statusTable.cuantasFloresGano()*3;/*3 puntos por cada flor ganada
    			points[0] = puntos + points[0];/*sumar puntos*/
    			//puntajes.add(new String("equipo: " + teams.getName(0) + " gano" + puntos + " puntos de Contra Flor));
    			/*listade puntajes
    		}
    		else{
    			puntos = statusTable.cuantasFloresGano()*3;/*3 puntos por cada flor ganada
    			points[1] = puntos + points[1]; /*sumar puntos
    			//puntajes.add(new String("equipo: " + teams.getName(0) + " gano" + puntos + " puntos de Contra Flor));
    		}
    	}
       
        volverAEstadoDeJuego();
        playTurn();
    }*/
    private void elEnvidoEstaPrimero(){
        if(!elEnvidoEstaPrimero)
            return;
        elEnvidoEstaPrimero = false;
        if(laPalabra.isPlayerTeam(pie1))
            playTurn = pie2;
        else
            playTurn = pie1;

        estadoActual = ESPERANDO_RESPUESTA_DEL_TRUCO;
        game.firePlayEvent(playTurn,TrucoEvent.TRUCO);
        esperarRespuesta();
        game.fireTurnEvent(playTurn,TrucoEvent.TURNO_RESPONDER_TRUCO);
    }
    private void volverAEstadoDeJuego(){
        switch(numeroDeRonda){
            case 1:
                estadoActual = PRIMERA_RONDA;
                break;
            case 2:
                estadoActual = SEGUNDA_RONDA;
                break;
            case 3:
                estadoActual = TERCERA_RONDA;
                break;
        }
    }
    /** retorna el puntaje de un equipo en mano
     * @param team el equipo de quien se quiere saber
     */    
    public int getPointsOfTeam(int team){
        if(team%2==0)
            return (points[0]);
        return points[1];
    }
}
