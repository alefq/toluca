
package py.edu.uca.fcyt.toluca.guinicio;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import py.edu.uca.fcyt.toluca.game.TrucoPlayer;


public class TableRanking extends JTable{

//    protected static org.apache.log4j.Logger logger = org.apache.log4j.Logger
//            .getLogger(TableRanking.class);
    
    public TableRanking()
    {
        super(new TableModelRanking());
		initComponents();
    }
    
	/**
     * 
     */
    private void initComponents() {
        rowSelectionAllowed=false;
		cellSelectionEnabled=false;
		setMaximumSize(new Dimension(100,400));
		TableColumn userCol=getColumn("User");
		//userCol.setMaxWidth(200);
		TableColumn puntajeCol=getColumn("Puntaje");
		//puntajeCol.setMaxWidth(50);						
		setRowHeight(25);
		HashMap coloresRanking = new HashMap();

        /*coloresRanking.put(RowRanking.RANKING_AZUL, new ImageIcon(getClass().getResource(RoomUING.IMAGE_DIR + "rAzul.gif"));
        coloresRanking.put(RowRanking.RANKING_GRIS, new ImageIcon(getClass().getResource(RoomUING.IMAGE_DIR + "rGris.gif"));
        coloresRanking.put(RowRanking.RANKING_LILA, new ImageIcon(getClass().getResource(RoomUING.IMAGE_DIR + "rLila.gif"));
        coloresRanking.put(RowRanking.RANKING_NARANJA,
                new ImageIcon(getClass().getResource(RoomUING.IMAGE_DIR + "rNaranja.gif"));
        coloresRanking.put(RowRanking.RANKING_ROJO, new ImageIcon(getClass().getResource(RoomUING.IMAGE_DIR + "rRojo.gif"));
        coloresRanking.put(RowRanking.RANKING_VERDE,
                new ImageIcon(getClass().getResource(RoomUING.IMAGE_DIR + "rVerde.gif"));*/
		coloresRanking.put(RowRanking.RANKING_AZUL, new ImageIcon(getClass().getResource(RoomUING.IMAGE_DIR + "rAzul.gif")));
        coloresRanking.put(RowRanking.RANKING_GRIS, new ImageIcon(getClass().getResource(RoomUING.IMAGE_DIR + "rGris.gif")));
        coloresRanking.put(RowRanking.RANKING_LILA, new ImageIcon(getClass().getResource(RoomUING.IMAGE_DIR + "rLila.gif")));
        coloresRanking.put(RowRanking.RANKING_NARANJA,
                new ImageIcon(getClass().getResource(RoomUING.IMAGE_DIR + "rNaranja.gif")));
        coloresRanking.put(RowRanking.RANKING_ROJO, new ImageIcon(getClass().getResource(RoomUING.IMAGE_DIR + "rRojo.gif")));
        coloresRanking.put(RowRanking.RANKING_VERDE,
                new ImageIcon(getClass().getResource(RoomUING.IMAGE_DIR + "rVerde.gif")));

	    userCol.setCellRenderer(
		new TableRankingRender(
                coloresRanking, new Color(255, 255, 255), new Color(240,
                        248, 146))); 
    }

    public TableRanking(TableCellRenderer render)
	{
	}
//	public Dimension getMinimumSize()
//	{
//	return new Dimension(50,100);	
//	}
//	public Dimension getPreferredSize()
//	{
//		return new Dimension(100,120);
//	}
//	public Dimension getMaximumSize()
//	{
//		return new Dimension(150,180);
//	}
    
    public Dimension getPreferredScrollableViewportSize()
    {
        Dimension size = super.getPreferredScrollableViewportSize();
        return new Dimension(Math.min(getPreferredSize().width, size.width), size.height);
    }

    /**
     * @param player
     */
    public void addPlayer(TrucoPlayer player) {
        
        TableModelRanking model=(TableModelRanking) getModel();
        model.insertRow(new RowRanking(player.getName(),new Integer(player.getRating())));
    }

    /**
     * @param player
     */
    public void removeplayer(TrucoPlayer player) {
        
    	TableModelRanking model=(TableModelRanking) getModel();
    	model.deletePlayer(player.getName());
    }

    /**
     * @param player
     */
    public void modifyplayer(TrucoPlayer player) {
        //logger.debug("falta copiar lo que se hac�a antes en el RoomUI viejo");        
    }
	
}
