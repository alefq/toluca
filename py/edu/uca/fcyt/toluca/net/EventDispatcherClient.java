/*
 * Created on Sep 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package py.edu.uca.fcyt.toluca.net;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import py.edu.uca.fcyt.game.ChatMessage;
import py.edu.uca.fcyt.toluca.RoomClient;
import py.edu.uca.fcyt.toluca.TolucaConstants;
import py.edu.uca.fcyt.toluca.event.RoomEvent;
import py.edu.uca.fcyt.toluca.event.TableEvent;
import py.edu.uca.fcyt.toluca.event.TrucoEvent;
import py.edu.uca.fcyt.toluca.game.TrucoCard;
import py.edu.uca.fcyt.toluca.game.TrucoGameClient;
import py.edu.uca.fcyt.toluca.game.TrucoPlay;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;
import py.edu.uca.fcyt.toluca.game.TrucoTeam;
import py.edu.uca.fcyt.toluca.sound.PlaySound;
import py.edu.uca.fcyt.toluca.table.Table;
import py.edu.uca.fcyt.toluca.table.TableException;
import py.edu.uca.fcyt.toluca.table.TableServer;

/**
 * @author dcricco
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EventDispatcherClient extends EventDispatcher {

    protected Logger logeador = Logger.getLogger(TrucoGameClient.class
            .getName());

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#loginRequested(py.edu.uca.fcyt.toluca.event.RoomEvent)
     */
    private TrucoPlayer trucoPlayer;

    private CommunicatorClient commClient;

    public void loginRequested(RoomEvent event) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#loginCompleted(py.edu.uca.fcyt.toluca.event.RoomEvent)
     */
    synchronized public void loginCompleted(RoomEvent event) {

        //      if (trucoPlayer == null) {
        trucoPlayer = event.getPlayer();
        /*logeador.log(TolucaConstants.CLIENT_INFO_LOG_LEVEL,
                " Cliente Login  completed " + trucoPlayer);*/

        commClient.setTrucoPlayer(trucoPlayer);
        ((RoomClient) room).loginCompleted(trucoPlayer);
        getCommClient().setLoggedIn(true);
        //  }

        //        HashMap jugadores = event.getPlayers();

        Iterator it = event.getPlayers().values().iterator();
        while (it.hasNext()) {
            //String keyClave = (String) it.next();
            //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, " Se va a
            // cargar "+newPlayer);
            //se agrega a todos menos a si mismo
            TrucoPlayer tp = (TrucoPlayer) it.next();
            if (!trucoPlayer.getName().equals(tp.getName())) {
                room.addPlayer(tp);
            }
        }

        //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "El vector de
        // table vale ");
        //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
        // event.getTablesServers().length);
        TableServer[] tables = event.getTablesServers();

        for (int i = 0; i < tables.length; i++) {

            if (tables[i] != null) {
                //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "login
                // ok dentro de tables ");
                TrucoPlayer playerOwner = tables[i].getHost();//este player es
                // igual al
                // playerCreador,
                // solo que el
                // playerCreador
                // es la ref en el
                // cliente
                TrucoPlayer playerCreador = room.getPlayer(playerOwner
                        .getName());
                Table table = addTable(playerCreador, tables[i].getTableNumber(), tables[i].getGamePoints());

                Vector vec = tables[i].getPlayers();
                Iterator iterator = vec.iterator();

                //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "Los
                // player de la taba "+tables[i].getTableNumber());

                while (iterator.hasNext()) {
                    TrucoPlayer playerClient = room
                            .getPlayer(((TrucoPlayer) iterator.next())
                                    .getName());
                    if (!playerClient.getName().equals(playerCreador.getName())) {
                        table.addPlayer(playerClient);

                    }
                    Integer asiento = tables[i].getAsiento(playerClient);
                    if (asiento != null) {
                        //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
                        // "En el login ya se hace un sitPlayer de"+playerClient
                        // +" en la chair "+asiento);
                    	TolucaConstants.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "Sentando a: " + playerClient.getName() + " en el asiento: "+ asiento.intValue());
                   	
                        table.sitPlayer(playerClient, asiento.intValue());
                        ((RoomClient) room).setearPlayerTable(playerClient,
                                table, asiento.intValue());
                    }
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerJoined(py.edu.uca.fcyt.toluca.event.RoomEvent)
     */
    synchronized public void playerJoined(RoomEvent event) {

        //si es el playerJoined despues del loginCompleted entonces se agrega
        //el trucoPlayer nomas que fue creado en loginCompleted
        if (!trucoPlayer.getName().equals(event.getPlayer().getName())) {
            room.addPlayer(event.getPlayer());
            ((RoomClient)room).showSystemMessage(event.getPlayer().getName() + " se ha unido al Room");
        }
        else
            room.addPlayer(trucoPlayer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerLeft(py.edu.uca.fcyt.toluca.event.RoomEvent)
     */
    synchronized public void playerLeft(RoomEvent event) {

        //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, " salioooooo
        // "+event.getPlayer());
        ((RoomClient)room).showSystemMessage(event.getPlayer().getName() + " se ha retirado del Room");
        TrucoPlayer playerServer = event.getPlayer();

        room.removePlayer(room.getPlayer(playerServer.getName()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#loginFailed(py.edu.uca.fcyt.toluca.event.RoomEvent)
     */
    public void loginFailed(RoomEvent event) {
        ((RoomClient) room).loginFailed(event);
    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#chatRequested(py.edu.uca.fcyt.toluca.event.RoomEvent)
     */
    public void chatRequested(RoomEvent event) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#chatSend(py.edu.uca.fcyt.toluca.event.RoomEvent)
     */
    public void chatSend(RoomEvent event) {
        ChatMessage chatMessage = event.getChatMessage();
        //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
        // getClass().getName()+"Se resivio un chat message
        // "+chatMessage.getOrigin());
        if (chatMessage.getOrigin().equals("room")) {
            room.showChatMessage(chatMessage.getPlayer(), chatMessage
                    .getHtmlMessage());
        } else {
            //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "un chat
            // para la mesa "+event.getTableNumber());
            Table table = room.getTable(event.getTableNumber());

            TrucoPlayer playerServer = chatMessage.getPlayer();
            TrucoPlayer playerClient = room.getPlayer(playerServer.getName());
            table.showChatMessage(playerClient, chatMessage.getHtmlMessage());

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#createTableRequest(py.edu.uca.fcyt.toluca.event.RoomEvent)
     */
    public void createTableRequest(RoomEvent event) {
        // TODO Auto-generated method stub

    }

    /**
     * @return Returns the commClient.
     */
    public CommunicatorClient getCommClient() {
        return commClient;
    }

    /**
     * @param commClient
     *            The commClient to set.
     */
    public void setCommClient(CommunicatorClient commClient) {
        this.commClient = commClient;
    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#tableCreated(py.edu.uca.fcyt.toluca.event.RoomEvent)
     */
    public void tableCreated(RoomEvent event) {

        if (event != null) {
//            TolucaConstants.dumpBeanToXML(event);
            TableServer tableServer = event.getTableServer();
            if (tableServer != null) {
                TrucoPlayer playerOwner = tableServer.getHost();//este player
                // es igual

                // al playerCreador,
                // solo que el
                // playerCreador es la
                // ref en el cliente
                TrucoPlayer playerCreador = room.getPlayer(playerOwner
                        .getName());
                addTable(playerCreador, tableServer.getTableNumber(), event.getGamePoints());
            } else
                System.out
                        .println("NO salieron las cosas con el RoomEvent-tableServer :((");
        } else
            System.out.println("NO salieron las cosas con el RoomEvent :((");

    }

    /**
     * 
     * @param playerCreador
     * @param tableNumber
     * @return
     * 
     * @deprecated
     */
    private Table addTable(TrucoPlayer playerCreador, int tableNumber) {
    	return addTable(playerCreador, tableNumber, 30);
    }
    private Table addTable(TrucoPlayer playerCreador, int tableNumber, int points) {//crea
        // una
        // Tabla
        // y
        // agrega
        // al
        // room
        Table table = null;
        boolean mostrar = false;
        if (playerCreador.getName().equals(trucoPlayer.getName())) {
            //CREO EL
            // PLAYER
            // QUE ACABA
            // DE
            // RECIBIR
            // EL MSG

            table = new Table(playerCreador, true, points);
            mostrar = true;            
        } else {//fue otro el que creo
            table = new Table(trucoPlayer, false, points);
        }

        table.setTableNumber(tableNumber);

        table.addTableListener(commClient);
        //PP total - Ale
        table.setRoom((RoomClient) room);
        room.addTable(table);
        table.initResources();
        table.addPlayer(playerCreador);

        if (mostrar) {
            table.getJFrame().setVisible(true);
            table.getJTrucoTable().addSystemLog("Para sentarte, haz Click en uno de los cuadrados vacios");
        }
        return table;
    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#tableJoinRequested(py.edu.uca.fcyt.toluca.event.RoomEvent)
     */
    public void tableJoinRequested(RoomEvent event) {
        // TODO Auto-generated method stub

    }

    /**
     * Indica que un jugador se uni� a una Mesa de juego
     */
    synchronized public void tableJoined(RoomEvent event) {

        TableServer tableServer = event.getTableServer();
        TrucoPlayer playerServer = event.getPlayer();

        Table table = room.getTable(tableServer.getTableNumber());
        TrucoPlayer playerClient = room.getPlayer(playerServer.getName());

        table.addPlayer(playerClient);
        if (playerClient.getName().equals(trucoPlayer.getName())) {
            table.initResources();
            table.getJFrame().setVisible(true);
            table.getJTrucoTable().addSystemLog("Para sentarte, haz Click en uno de los cuadrados vacios");
        }

        /*
         * Play sound if the player is inside.
         */
        if (table.isInside(playerClient.getName())) {
            PlaySound.play(PlaySound.ENTER_SOUND);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerSitRequest(py.edu.uca.fcyt.toluca.event.TableEvent)
     */
    public void playerSitRequest(TableEvent event) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerSit(py.edu.uca.fcyt.toluca.event.TableEvent)
     */
    synchronized public void playerSit(TableEvent event) {

        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "player sit");
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "Se resive un
        // player sit a la mesa "+event.getTableServer().getTableNumber());
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "player
        // "+event.getPlayer()[0]);
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "chair
        // "+event.getValue());
        TableServer tableServer = event.getTableServer();
        TrucoPlayer playerServer = event.getPlayer()[0];
        int chair = event.getValue();
        Table table = room.getTable(tableServer.getTableNumber());

        TrucoPlayer playerClient = room.getPlayer(playerServer.getName());

        //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "La silla de
        
        try {
            table.sitPlayer(playerClient, chair);
        } catch (TableException e) {
            TolucaConstants.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, e.getMessage());
            TolucaConstants.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "Silla: " + chair + " Table: " + table.getTableNumber() + " Jugador Servidor: " + playerServer.getName() + " Jugador cliente: " + playerClient.getName());
            e.printStackTrace();
            table.showSystemMessage("Alguien se sent� antes que vos. Sentate en otra silla: " + e.getMessage());
        }
        //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "ya se hizo el
        // table.sitPlayer");
        ((RoomClient) room).setearPlayerTable(playerClient, table, chair);
    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerStandRequest(py.edu.uca.fcyt.toluca.event.TableEvent)
     */
    public void playerStandRequest(TableEvent event) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerStand(py.edu.uca.fcyt.toluca.event.TableEvent)
     */
    public void playerStand(TableEvent event) {

        //	logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "player
        // staaaaanded");
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "Se resive un
        // player stand a la mesa "+event.getTableServer().getTableNumber());
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "player
        // "+event.getPlayer()[0]);
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "chair
        // "+event.getValue());

        TableServer tableServer = event.getTableServer();
        TrucoPlayer playerServer = event.getPlayer()[0];

        int chair = event.getValue();

        Table table = room.getTable(tableServer.getTableNumber());
        TrucoPlayer playerClient = room.getPlayer(playerServer.getName());

        TrucoGameClient trucoGameClient = (TrucoGameClient) table
                .getTrucoGame();

        if (trucoGameClient == null)//si no hay juego nomas se levanta, si ya
            // hay juego va a recibir solamente el
            // playerkick
            table.standPlayer(chair);

        ((RoomClient) room).setStandPlayer(chair, table);

    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerKickRequest(py.edu.uca.fcyt.toluca.event.TableEvent)
     */
    public void playerKickRequest(TableEvent event) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerKicked(py.edu.uca.fcyt.toluca.event.TableEvent)
     */
    public void playerKicked(TableEvent event) {

        /*
         * Play a sound for player left.
         */
        PlaySound.play(PlaySound.LEAVE_SOUND);

        //TableServer tableServer = event.getTableServer();
        TrucoPlayer playerChutadoLadoServer = event.getPlayer()[0];

        Table table = room.getTable(event.getTableBeanRepresentation().getId());
        TrucoPlayer playerChutadoLadoCliente = room
                .getPlayer(playerChutadoLadoServer.getName());

        table.kickPlayer(playerChutadoLadoCliente);

        if (event.getTableBeanRepresentation().getHostPlayer().equals(
                playerChutadoLadoServer)) {

            //Sali� el HOST y hay que autoeliminarse de esa mesa
            if (table.getPlayers().contains(trucoPlayer)) {//solamente si la
                                                           // tabla le tiene al
                                                           // player del cliente

                table.selfKick();
                ((RoomClient) getRoom())
                        .getRoomUING()
                        .getChatPanel()
                        .showSystemMessage(
                                "La mesa "
                                        + table.getTableNumber()
                                        + " se ha cerrado porque el creador la ha abandonado",
                                new String[] { "[", "]" });
            }

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerLeft(py.edu.uca.fcyt.toluca.event.TableEvent)
     */
    public void playerLeft(TableEvent event) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#gameStartRequest(py.edu.uca.fcyt.toluca.event.TableEvent)
     */
    public void gameStartRequest(TableEvent event) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#gameStarted(py.edu.uca.fcyt.toluca.event.TableEvent)
     */
    public void gameStarted(TableEvent event) {

        /*logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
                "Llego un gameStarted de TableEvent ");*/
        TableServer tableServer = event.getTableServer();
        Table table = room.getTable(tableServer.getTableNumber());

        TrucoTeam[] trucoTeam = table.createTeams();
        TrucoGameClient trucoGameClient = new TrucoGameClient(trucoTeam[0],
        		trucoTeam[1], table.getGamePoints());
        trucoGameClient.setTableNumber(tableServer.getTableNumber());
        trucoGameClient.addTrucoListener(commClient);

        //es muy importante el orden de los faroles altera el valor del
        // alumbrado
        //esto me hizo perder 2 horas
        table.startGame(trucoGameClient);//atender esta linea primero que la
        // linea titulada morgue
        trucoGameClient.startGameClient();//linea morgue

    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#signSendRequest(py.edu.uca.fcyt.toluca.event.TableEvent)
     */
    public void signSendRequest(TableEvent event) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#signSend(py.edu.uca.fcyt.toluca.event.TableEvent)
     */
    public void signSend(TableEvent event) {

        TableServer tableServer = event.getTableServer();
        Table table = room.getTable(tableServer.getTableNumber());

        TrucoPlayer playerServerSenhador = event.getPlayer(0);
        TrucoPlayer playerServerSenhado = event.getPlayer(1);

        TrucoPlayer playerClienteSenhador = room.getPlayer(playerServerSenhador
                .getName());
        TrucoPlayer playerClienteSenhado = room.getPlayer(playerServerSenhado
                .getName());

        TableEvent te = new TableEvent(TableEvent.EVENT_signSent, table,
                playerClienteSenhador, playerClienteSenhado, event.getValue());
        table.showSign(te);

    }

    public void infoGame(TrucoEvent event) {
        /*logeador
                .log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
                        "****************************INFO DE JUEGO******************************");*/

/*        logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
                " Info del juego  type " + event.getType());

        if (event.getType() == TrucoEvent.INICIO_DE_JUEGO)
            logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
                    "SE EMPIEZA UN JUEGO");
        if (event.getType() == TrucoEvent.INICIO_DE_MANO)
            logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
                    "SE EMPIEZA LA MANO");*/

        if (event.getType() == TrucoEvent.FIN_DE_JUEGO) {
            /*logeador
                    .log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
                            " *******************llego un fin de juego**********************************");*/

        }
        Table table = room.getTable(event.getTableNumber());
        TrucoGameClient trucoGameClient = (TrucoGameClient) table.getTGame();
        /*logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, " Table "
                + table.getTableNumber());
        logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "Hands "
                + event.getNumberOfHand());*/

        if (event.getPlayer() != null) {

            TrucoPlayer trucoPlayer = room.getPlayer(event.getPlayer()
                    .getName());
            event.setPlayer(trucoPlayer);
            //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "
            // TrucoPlayer "+trucoPlayer.getName());
        } else {
            logeador
                    .log(
                            TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
                            "TrucoPlayer es nulo esto no es un problema. solo que este evento no trae player");
        }
        //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
        // "******************************************************");
        /*logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
                "trucoGameClient = " + trucoGameClient);*/
        trucoGameClient.play(event);

    }

    public void receiveCards(TrucoEvent event) {

        int tableId = event.getTableNumber();
        /*logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
                "Se reciben las cartas para el juego de la tabla " + tableId);*/
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, " cards
        // "+event.getCards().length);
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, " hand
        // "+event.getNumberOfHand());
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, " cartas del
        // player "+event.getPlayer().getName());
        TrucoPlayer playerServer = event.getPlayer();
        TrucoPlayer playerClient = room.getPlayer(playerServer.getName());
        Table table = room.getTable(tableId);
        TrucoCard[] cards = event.getCards();
        //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "Se resiven las
        // cartas");
        //		for(int i=0;i<cards.length;i++)
        //		{
        //			logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
        // cards[i].getKind() + " "+cards[i].getValue());
        //		}
        TrucoGameClient trucoGameClient = (TrucoGameClient) table.getTGame();
        trucoGameClient.recibirCartas(playerClient, event.getCards());
    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#play(py.edu.uca.fcyt.toluca.game.TrucoPlay)
     */
    public void play(TrucoPlay event) {
        /*
         * logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, " se resivio un
         * play con un trucoplay desc:");
         * 
         * logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "SE resive un
         * play de "+event.getPlayer().getName());
         * logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "TAbla :
         * "+event.getTableNumber());
         * logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "type :
         * "+event.getType());
         * logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "carta Palo:
         * "+event.getCard().getKind() +" val "+event.getCard().getValue());
         * logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "value >
         * "+event.getValue());
         * 
         * Table table=room.getTable(event.getTableNumber()); TrucoGameClient
         * trucoGameClient=(TrucoGameClient) table.getTGame();
         * 
         * TrucoPlayer playerServer=event.getPlayer(); TrucoPlayer
         * playerClient=room.getPlayer(playerServer.getName());
         * event.setPlayer(playerClient); trucoGameClient.play(event);
         */

        //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "
        // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHH BUENO; NO TIENE QUE
        // PASAR ESTO");
    }

    public void tirarCarta(TrucoEvent event) {

        /*logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
                "Se quire tirar carta table " + event.getTableNumber());*/

        Table table = room.getTable(event.getTableNumber());
        TrucoGameClient trucoGameClient = (TrucoGameClient) table.getTGame();

        TrucoPlayer playerServer = event.getPlayer();
        TrucoPlayer playerClient = room.getPlayer(playerServer.getName());

        TrucoCard cartaServer = event.getCard();
        TrucoCard cartaClient = trucoGameClient.getCard(cartaServer.getKind(),
                cartaServer.getValue());

        /*logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
                " Player cliente : " + playerClient);*/
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, " carta cliente:
        // "+cartaClient);
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "TrucoPlayer :
        // "+trucoPlayer);

        //TODO: ver por que no anda mas el tema del igual entre objetos como
        // era antes
        if (!trucoPlayer.getName().equals(playerClient.getName())) {
            //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
            // getClass().getName()+"tirarCarta: playerCliente no es igual a
            // trucoPlayer");
            //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "Ejecuto
            // primero la jugada en el cliente!!!: " +
            // tp.getPlayer().getName());
            TrucoPlay tp = new TrucoPlay(playerClient, TrucoPlay.JUGAR_CARTA,
                    cartaClient);
            trucoGameClient.play(tp);
        }

        trucoGameClient.playResponse(playerClient, cartaClient);
    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#playerConfirmado(py.edu.uca.fcyt.toluca.game.TrucoPlay)
     */
    public void playerConfirmado(TrucoPlay event) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#canto(py.edu.uca.fcyt.toluca.event.TrucoEvent)
     */
    public void canto(TrucoEvent event) {

        Table table = room.getTable(event.getTableNumber());
        TrucoGameClient trucoGameClient = (TrucoGameClient) table.getTGame();

        TrucoPlayer playerServer = event.getPlayer();
        TrucoPlayer playerClient = room.getPlayer(playerServer.getName());

        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "
        // **********************llego un Canto************************");
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "TrucoPlayer (el
        // del host): "+trucoPlayer);
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, " Player cliente
        // (el del evento): "+playerClient);
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, " type :
        // "+event.getType());
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "hand
        // "+event.getHand());
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
        // "*************************************************************");

        //TODO: ver por que no anda mas el tema del igual entre objetos como
        // era antes
        if (!trucoPlayer.getName().equals(playerClient.getName())) {
            //	logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, " se le hace
            // un play porque los players son diferentes");
            TrucoEvent ev = new TrucoEvent(trucoGameClient, event.getHand(),
                    playerClient, event.getType());
            TrucoPlay tp = ev.toTrucoPlay();
            trucoGameClient.play(tp);
        }

        trucoGameClient.playResponse(playerClient, event.getType());
    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#cantarTanto(py.edu.uca.fcyt.toluca.event.TrucoEvent)
     */
    public void cantarTanto(TrucoEvent event) {

        Table table = room.getTable(event.getTableNumber());
        TrucoGameClient trucoGameClient = (TrucoGameClient) table.getTGame();

        TrucoPlayer playerServer = event.getPlayer();
        TrucoPlayer playerClient = room.getPlayer(playerServer.getName());

        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "
        // **********************llego un Cantar
        // Tanto************************");
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "TrucoPlayer (el
        // del host): "+trucoPlayer);
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, " Player cliente
        // (el del evento): "+playerClient);
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, " type :
        // "+event.getType());
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "hand
        // "+event.getHand());
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, " value
        // "+event.getValue());
        //		logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL,
        // "*************************************************************");

        //TODO: ver por que no anda mas el tema del igual entre objetos como
        // era antes
        if (!trucoPlayer.getName().equals(playerClient.getName())) {
            //logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, " se le hace
            // un play porque los players son diferentes");
            TrucoEvent ev = new TrucoEvent(trucoGameClient, event.getHand(),
                    playerClient, event.getType(), event.getValue());
            TrucoPlay tp = ev.toTrucoPlay();
            trucoGameClient.play(tp);
        }

        trucoGameClient.playResponse(playerClient, event.getType(), event
                .getValue());
    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#tableDestroyed(py.edu.uca.fcyt.toluca.event.TableEvent)
     */
    synchronized public void tableDestroyed(TableEvent event) {

        /*logeador.log(Level.WARNING, "se destruyo la tabla "
                + event.getTableServer().getTableNumber());*/
        Table table = room.getTable(event.getTableServer().getTableNumber());
        if (table != null)
            ((RoomClient) room).tableDestroyed(table);

    }

    public static void main(String[] args) {
        XMLDecoder d;
        try {
            d = new XMLDecoder(new BufferedInputStream(new FileInputStream(
                    "/tmp/tableevent.xml")));
            Object result = d.readObject();
            d.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#rankingChanged(py.edu.uca.fcyt.toluca.event.RoomEvent)
     */
    public void rankingChanged(RoomEvent event) {

        RoomClient roomClient = (RoomClient) room;
        TrucoPlayer playerClient = roomClient.getPlayer(event.getPlayer()
                .getName());
        playerClient.setRating(event.getPlayer().getRating());
        roomClient.actualizarRanking(playerClient);

        Table table = room.getTable(event.getTableNumber());

        table.actualizarRanking(playerClient);
        table.showSystemMessage("Jugador: " + event.getPlayer().getName()
                + " - Ranking viejo: " + event.getPlayer().getOldRating()
                + "- Ranking nuevo: " + event.getPlayer().getRating());

    }

    public void testConexion(RoomEvent event) {

        long intervalo = System.currentTimeMillis() - event.getMsSend();
        /*
         * logeador.log(TolucaConstants.CLIENT_DEBUG_LOG_LEVEL, "Se recibe la
         * respuesta " + intervalo);
         */
        //System.out.println("Se recibe la respuesta "+intervalo);
        RoomClient roomClient = (RoomClient) room;
        roomClient.testConexionReceive(intervalo);

    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#invitacion(py.edu.uca.fcyt.toluca.event.RoomEvent)
     */
    public void invitacion(RoomEvent event) {
        Table t = ((RoomClient) getRoom()).getTable(event.getTableNumber());
        t.getJTrucoTable().showInvitation(event);
    }

    /*
     * (non-Javadoc)
     * 
     * @see py.edu.uca.fcyt.toluca.net.EventDispatcher#invitacionRejected(py.edu.uca.fcyt.toluca.event.RoomEvent)
     */
    public void invitacionRejected(RoomEvent event) {
        String msg = "<html>El jugador " + event.getPlayer()
                + " rechazo la invitaci�n. <br> Motivo: " + event.getErrorMsg();
        JOptionPane.showMessageDialog(((RoomClient) getRoom()).getRoomUING(),
                msg, "Invitacion rechazada", JOptionPane.INFORMATION_MESSAGE);
    }
}