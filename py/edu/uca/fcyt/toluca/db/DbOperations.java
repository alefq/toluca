package py.edu.uca.fcyt.toluca.db;


/** Java class "DbOperations.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

import py.edu.uca.fcyt.game.Game;
import py.edu.uca.fcyt.toluca.LoginFailedException;
import py.edu.uca.fcyt.toluca.RoomServer;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;

/**
 * <p>
 * La clase DbOperations provee al TOLUCA de la interfaz a los datos
 * alojados en una base de datos relacional en el lado del servidor. Los
 * m&#233;todos p&#250;blicos permiten a los otros objetos acceder a los datos
 * subyacentes. Es instanciado por el RoomServer que es el programa
 * principal del lado del servidor. Los m&#233;todos son todos s&#237;ncronos. Queda
 * para las futuras generaciones hacer que esta clase sea un thread
 * as&#237;ncrono para procesar de manera m&#225;s eficiente los requests a la base
 * de datos.
 * </p>
 * 
 */
public class DbOperations {

  ///////////////////////////////////////
  // attributes


/**
 * <p>
 * Representa la conexi&#243;n al servidor de datos (SQL Server)
 * </p>
 * 
 */
    private Connection conn; 

   ///////////////////////////////////////
   // associations

/**
 * <p>
 * 
 * </p>
 */
    private RoomServer roomServer; 


  ///////////////////////////////////////
  // operations


/**
 * <p>
 * Crea un nuevo usuario en el sistema, haciendo la inserci&#243;n
 * correspondiente en la tabla &quot;USUARIOS&quot; del sistema
 * </p>
 * <p>
 * 
 * @param uname ... Nombre de usuario
 * </p> Inserta un Player en la base de datos
 * <p>
 * @param upass ... Password
 * </p>
 */
    public void createPlayer(String email, String upass, String uname) throws SQLException {        
        // your code here
    } // end createPlayer        

/**
 * <p>
 * Borra de la tabla de usuarios a un jugador
 * </p>
 * <p>
 * 
 * </p>
 * <p>
 * 
 * @param uname Nombre de usuario del jugador a borrar
 * </p>
 */
    public void deletePlayer(TrucoPlayer player) {        
        // your code here
    } // end deletePlayer        

/**
 * <p>
 * Acutaliza los datos del jugador en la Tabla
 * </p>
 * <p>
 * 
 * @param player Datos actualizados del jugador que deben ser reflejados en
 * la base de datos
 * </p>
 */
    public void updatePlayerData(TrucoPlayer player)  {        
        // your code here
    } // end updatePlayerData        

/**
 * <p>
 * Does ...
 * </p><p>
 * 
 * </p><p>
 * 
 * @param game ...
 * </p>
 */
    public void createGame(Game game) {        
        // your code here
    } // end createGame        

/**
 * <p>
 * Does ...
 * </p><p>
 * 
 * </p><p>
 * 
 * @param game ...
 * </p>
 */
    public void updateGameData(Game game) {        
        // your code here
    } // end updateGameData        

/**
 * <p>
 * Crea una entrada en la tabla &quot;LOG&quot; con el mensaje. Utilizado
 * exclusivamente para mantener una bit&#225;cora de los eventos interesantes
 * del sistema.
 * </p>
 * <p>
 * 
 * </p>
 * <p>
 * 
 * @param logMessage El mensaje que se quiere alojar en la tabla
 * </p>
 */
    public void log(String logMessage) {        
        // your code here
    } // end log        

/**
 * <p>
 * Autentica al usuario con su password correspondiente
 * </p>
 * <p>
 * 
 * </p>
 * <p>
 * 
 * @param username Nombre de usuario
 * </p>  Hace la autenticaci�n del usuario.
 * <p>
 * @param password Password o contrasena
 * @return Player el objeto Player o una excepcion de Login
 * </p>
 */
    public TrucoPlayer authenticatePlayer(String username, String password) throws LoginFailedException { 
        
        TrucoPlayer p = getPlayer(username);
        //verificar el password
        // if (password = Player.getPassword() )
        return p;
        // else
        //throw new LoginFailedException();
        
    }
    
    /** Getter for property roomServer.
     * @return Value of property roomServer.
     */
    public RoomServer getRoomServer() {
        return this.roomServer;
    }
    
    /** Setter for property roomServer.
     * @param roomServer New value of property roomServer.
     */
    public void setRoomServer(RoomServer roomServer) {
        this.roomServer = roomServer;
    }
    
    public TrucoPlayer getPlayer(final java.lang.String uname) {
    	Random rand = new Random();
    	
        return new TrucoPlayer( uname , rand.nextInt());
    }
    
 // end authenticatePlayer        

/**
 * <p>
 * Does ...
 * </p><p>
 * 
 * @param dbUrl ...
 * </p><p>
 * @param dbUsername ...
 * </p><p>
 * @param dbPassword ...
 * </p>
 */
    public  DbOperations(String dbUrl, String dbUsername, String dbPassword, RoomServer rs) {                
        // your code here
        setRoomServer(rs);
    } // end DbOperations        

} // end DbOperations




