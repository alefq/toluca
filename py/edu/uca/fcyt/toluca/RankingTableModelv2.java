/*
 * RankingTableModelv2.java
 *
 * Created on 29 de mayo de 2003, 08:14 AM
 */

package py.edu.uca.fcyt.toluca;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import py.edu.uca.fcyt.toluca.game.TrucoPlayer;

/**
 *
 * @author  Interfaz de Inicio
 */
public class RankingTableModelv2 extends AbstractTableModel {
    final String[] columnNames =    {  "Nivel", "Jugador", "Ranking"  };
    
    Vector jugadores = new Vector();
    //Vector values = new Vector();
    
    public int getColumnCount() {
        return columnNames.length;
    }
    
    public int getRowCount() {
        return jugadores.size();
    }
    
    public Object getValueAt(int row, int col) {
        String ret = "";
        if (row < jugadores.size()) {
            TrucoPlayer player = (TrucoPlayer)jugadores.get(row);
            switch (col) {
                case 0:
                    ret = String.valueOf(player.getRating());
                    break;
                case 1:
                    ret = player.getName();
                    break;
                case 2:
                    ret = String.valueOf(player.getRating());
                    break;
                default:
                    System.out.println("Columna no definida");
                    break;
            }
        }
        return ret; 
    }
    
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    public boolean isCellEditable(int row, int col) {
        return false;
    }
    
    public void setValueAt(Object value, int row, int col) {
        /*if (col == 1) {
            //System.out.println("Setting value at " + row + "," + col + " to " + value + " (an instance of " + value.getClass() + ")");
            values.set(row, value);
            e.setAttribute((String)attributes.get(row), (String)values.get(row));
            fireTableCellUpdated(row, col);
            xe.setModified(true);
        }*/
    }
    
    public void insertRow(int row, Object[] value) {
        jugadores.insertElementAt(value[0], row);
        fireTableRowsInserted(row, row);
    }
    
    public void insertRow(Object[] value) {
       
        insertRow(getRowCount(), value);
    }
    
    public void removeRow(int row) {
        jugadores.remove(row);
        fireTableRowsDeleted(row, row);
    }
    
    public void modifyRow(int row, TrucoPlayer player) {
        jugadores.set(row,player);
        fireTableDataChanged();
    }
    
    /*
     * Agrega un jugador a la tabla del Ranking.
     */
    public void addPlayer(TrucoPlayer player) {
       // System.out.println("Voy a agarlo a: " + player.getName());
        Object[] objs = new Object[1];
        objs[0] = player;
        insertRow(objs);
    }
    
    /*
     * El jugador "player" se elimina de la tabla del Ranking.
     */
    public void removePlayer(TrucoPlayer player){
        int row = filaDelJugador(player.getName());
        if( row!= -1)
            removeRow(row);
        else
           System.out.println("EL jugador que se quiere eliminar no se encuentra!");
    }
    
    //Nuevo agregado bola
    
    /*
     * Se modifican los datos del jugador "player" en la tabla del Ranking.
     */
    public void modifyPlayer(TrucoPlayer player){
        String name = new String(player.getName());
        int row = filaDelJugador(name);
        if( row!= -1)
            modifyRow(row, player);
        else
           System.out.println("EL jugador que se quiere modificar no se encuentra!");    
    }
    
    /*
     * Este metodo busca la fila donde se encuentra el jugador "Nombre".
     * Retorna -1 si el jugador no se encuentra en el Vector de jugadores.
     */
    int filaDelJugador(String Nombre){
        
        String aux = new String();
        for(int i=0; i< jugadores.size(); i++){
            aux = (String)  getValueAt(i,1);
            if(Nombre.equals(aux))
                return i;
        }
        return -1; //si no encontro el jugador en ninguna fila
    }
}
