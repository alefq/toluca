package py.edu.uca.fcyt.toluca.event;

import java.util.*;

import py.edu.uca.fcyt.toluca.game.*;
import py.edu.uca.fcyt.toluca.*;
import py.edu.uca.fcyt.game.*;



/**
* <p>
*
* @author Interfaz de Inicio
* </p>
*/
public interface SpaceListener {

  ///////////////////////////////////////
  // operations

/**
* <p>
* Does ...
* </p><p>
*
* @param player ...
* </p><p>
*
* </p>
*/
    public void playerJoined(Player player);
/**
* <p>
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
    public void chatMessageSent(ChatPanelContainer cpc, Player player, String htmlMessage);
/**
* <p>
* Does ...
* </p><p>
*
* </p><p>
*
* @param player ...
* </p>
*/
    public void playerLeft(Player player);
/**
* <p>
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
    public void chatMessageRequested(ChatPanelContainer cpc, Player player, String htmlMessage);

} // end SpaceListener







