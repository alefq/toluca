package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.toluca.event.*;
import py.edu.uca.fcyt.game.*;

import java.util.*;

/**
 * Escucha los eventos del juego
 */
class TrListener implements TrucoListener {
    protected Table table;				 	// objeto Table
    protected PlayTable pTable;			 	// PlayTable de 'table'
    protected LinkedList trucoListeners; 	// listeners de eventos
    
    /** Construye un TrucoListenerManger con Table 'table' */
    public TrListener(Table table) {
        this.table = table;
        pTable = table.getPlayTable();
        trucoListeners = new LinkedList();
    }
    
    /** Registra un listener de eventos TrucoListener */
    public void addListener(TrucoListener t) {
        Util.verif(t != null, "TrucoListener nulo");
        trucoListeners.add(t);
    }
    
    /** Invocado cuando se inicia el juego de truco */
    public void gameStarted(TrucoEvent event) {
        System.out.println("Game started for player " + getAssociatedPlayer().getName());
    }
    
    
    public void play(TrucoEvent event) {
        
        System.out.println("Play for player " + getAssociatedPlayer().getName());
        
        switch (event.getTypeEvent()) {
            case TrucoEvent.JUGAR_CARTA: {
                Card card;
                Player player;
                int pos;
                
                card = event.getCard();
                player = event.getPlayer();
                
                System.out.println("La carta jugada es:" + card.getValue() + " " + card.getKind());
                
                if (player != getAssociatedPlayer()) {
                    pos = table.pManager.getPos(player);
                    table.cManager.playCard(pos, table.cManager.setCard(pos, card));
                }
            }
            break;
            
            case TrucoEvent.ENVIDO:
                table.drawBalloon(event.getPlayer(), "Envido");
                break;
            case TrucoEvent.REAL_ENVIDO:
                table.drawBalloon(event.getPlayer(), "Real Envido");
                break;
            case TrucoEvent.FALTA_ENVIDO:
                table.drawBalloon(event.getPlayer(), "Falta Envido");
                break;
            case TrucoEvent.PASO_ENVIDO:
                table.drawBalloon(event.getPlayer(), "Paso");
                break;
            case TrucoEvent.FLOR:
                table.drawBalloon(event.getPlayer(), "Flor");
                break;
            case TrucoEvent.TRUCO:
                table.drawBalloon(event.getPlayer(), "Truco");
                break;
            case TrucoEvent.RETRUCO:
                table.drawBalloon(event.getPlayer(), "Quiero retruco");
                break;
            case TrucoEvent.VALE_CUATRO:
                table.drawBalloon(event.getPlayer(), "Quiero vale 4");
                break;
            case TrucoEvent.NO_QUIERO:
                table.drawBalloon(event.getPlayer(), "No quiero");
                break;
            case TrucoEvent.QUIERO:
                table.drawBalloon(event.getPlayer(), "Quiero");
                break;
        }
        
    }
    
    public void turn(TrucoEvent event) {
        System.out.println("Turn for player " + getAssociatedPlayer().getName());
        
        
        
        if (event.getPlayer() == getAssociatedPlayer()) {
            System.out.println("------------------------------ACA PARECE QUE JUEGA EL AGENTE");
            table.status = table.PLAY;
        } else {
            //			int pos;
            //			Face face;
            //
            //			pos = table.pManager.getPos(event.getPlayer());
            //
            //			for (int i = 0; i < table.pTable.getFaceCount(); i++)
            //			{
            //				face = table.pTable.getFace(i);
            //				if (i + 1 == pos)
            //					face.setBorderColor
            //					(
            //						table.getChairColor(i + 1).brighter()
            //					);
            //				else
            //					face.setBorderColor
            //					(
            //						table.getChairColor(i + 1)
            //					);
            //			}
            table.status = table.WAIT;
        }
    }
    
    public void endOfHand(TrucoEvent event) {
        System.out.println("End of hand for player " + getAssociatedPlayer().getName());
        table.cManager.gatherCards(0);
    }
    
    public void cardsDeal(TrucoEvent event) {
        Card[] cards;
        
        System.out.println("Cards deal for player " + getAssociatedPlayer().getName());
        
        table.cManager.setCards(event.getCards());
        table.cManager.toTop();
        table.cManager.showCards();
    }
    
    public void handStarted(TrucoEvent event) {
        TrucoPlayer tPlayer;
        int dealPos;
        int pun1, pun2;
        
        System.out.println("New hand started for player " + getAssociatedPlayer().getName());
        
        
        pun1 = table.tGame.getTeam(0).getPoints();
        pun2 = table.tGame.getTeam(1).getPoints();
        
        System.out.println("Puntaje 1 = " + pun1);
        System.out.println("Puntaje 2 = " + pun2);
        
        table.jtTable.score.actualizarPuntaje(pun1, pun2);
        
        tPlayer = (TrucoPlayer) event.getTrucoPlayer();
        
        dealPos = table.pManager.getPos(tPlayer);
        
        table.cManager.putCardsInDealer(dealPos);
        table.cManager.deal(dealPos, false);
        table.cManager.take();
        table.cManager.putDeckInTable(dealPos);
        
        
    }
    
    
    public void endOfGame(TrucoEvent event) {
        // TODO: Add your code here
    }
    
    public Player getAssociatedPlayer() {
        return table.getOwner();
    }
    
    
}