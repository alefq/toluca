
package py.edu.uca.fcyt.toluca.guinicio;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTable;

import py.edu.uca.fcyt.toluca.game.TrucoPlayer;
import py.edu.uca.fcyt.toluca.table.Table;



public class TableGame extends JTable{

    protected static org.apache.log4j.Logger logger = org.apache.log4j.Logger
            .getLogger(TableGame.class);
    
    private TableModelGame tableModelGame = new TableModelGame();
    
	public TableGame()
	{
		super();
		setModel(getTableModelGame());
		setSelectionBackground(new Color(240,248,146));		
	}
	
	public Dimension getPreferredScrollableViewportSize()
	{
	    Dimension size = super.getPreferredScrollableViewportSize();
	    return new Dimension(Math.min(getPreferredSize().width, size.width), size.height);
	}

    /**
     * @param table
     */
    public void insertarFila(Table table) {
        logger.debug("falta copiar lo que se hac�a antes en el RoomUI viejo");
    }

    /**
     * @param tableNumber
     */
    public void eliminarFila(int tableNumber) {
        logger.debug("falta copiar lo que se hac�a antes en el RoomUI viejo");      
    }

    /**
     * @param player
     */
    public void removeplayer(TrucoPlayer player) {
        logger.debug("falta copiar lo que se hac�a antes en el RoomUI viejo"); 
    }

    /**
     * @param player
     * @param tableNumber
     */
    public void addObserver(TrucoPlayer player, int tableNumber) {
        logger.debug("falta copiar lo que se hac�a antes en el RoomUI viejo");        
    }

    /**
     * @param tableNumber
     * @param b
     */
    public void setGameStatus(int tableNumber, boolean b) {
        logger.debug("falta copiar lo que se hac�a antes en el RoomUI viejo");        
    }

    /**
     * @param player
     * @param tableNumber
     * @param chair
     */
    public void addPlayer(TrucoPlayer player, int tableNumber, int chair) {
        logger.debug("falta copiar lo que se hac�a antes en el RoomUI viejo");        
    }

    /**
     * @param player
     * @param tableNumber
     */
    public void removeplayer(TrucoPlayer player, int tableNumber) {
        logger.debug("falta copiar lo que se hac�a antes en el RoomUI viejo");        
    }
    
    public int getNumeroDeMesa(int row)
    {
        int ret = -1;
        try {
            ret = Integer.parseInt((String) getTableModelGame().getValueAt(row,
                    TableModelGame.INDICE_MESA));            
        } catch (Exception e) {
            ret = -1;
        }
        return ret;
    }
    /**
     * @return Returns the tableModelGame.
     */
    public TableModelGame getTableModelGame() {
        return tableModelGame;
    }
    /**
     * @param tableModelGame The tableModelGame to set.
     */
    public void setTableModelGame(TableModelGame tableModelGame) {
        this.tableModelGame = tableModelGame;
    }
}