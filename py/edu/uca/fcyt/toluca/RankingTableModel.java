package py.edu.uca.fcyt.toluca;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import py.edu.uca.fcyt.toluca.game.TrucoPlayer;

public class RankingTableModel extends DefaultTableModel{
    
    //	DefaultTableModel  ;
    //static	Object[] TableHeader = {"Nivel","Jugador","Puntaje" };
    //static	Object[][] TableData = {{ " ", " "," " }};
    
    Vector encabezado;
    Vector jugadores;
    
    public RankingTableModel(Vector data, Vector columnNames) {
        //super(data, columnNames);
        System.out.println("DATA SIZE " + data.size());
        System.out.println("CNAMES SIZE " + columnNames.size());
        this.encabezado = columnNames;
        this.jugadores = data;
        System.out.println("Se instancia el RankingTableModel");
        //super(TableData,TableHeader);
    }
    
    public String getColumnName(int column) {
        String ret = "";
        if (column < encabezado.size()) {
            ret = (String)encabezado.get(column);
        } else {
            System.out.println("Columna inválida!!!!!");
        }
        return ret;
    }
    
    public int getRowCount() { 
        int ret = 0;
        if (jugadores != null)
            ret = jugadores.size();
        return ret;
    }
    
    public int getColumnCount() { 
        int ret = 0;
        if (encabezado != null)
            ret = encabezado.size();
        return ret;
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
    public boolean isCellEditable(int row, int col)
        { return false; }
    /*public void setValueAt(Object value, int row, int col) {
        rowData[row][col] = value;
	fireTableCellUpdated(row, col);
    }*/
    
    public void addPlayer(TrucoPlayer player){
        System.out.println("******** SE AGREGA A LA TABLE EL JUGADOR: " + player.getName());
        //jugadores.add(player);
        Vector tmp = new Vector();
        tmp.add(player);
        addRow(tmp);
        // newRowsAdded(new TableModelEvent(this));
    /*    if( filaDelJugador(Nombre)<0){
            Object[] fila = {"....", Nombre, new Integer(Rating) };
            addRow(fila);
        } else {
            System.out.println("El player que quiere agregarse ya existe!");
        } */
    }
    
    void removeplayer(String Nombre){
        int fila = filaDelJugador(Nombre);
        if( fila>=0)
            removeRow(fila);
        else
            System.out.println("El player que quiere eliminarse no ya existe!");
    }
    
    void modifyplayer(String Nombre, int Rating){
        int fila = filaDelJugador(Nombre);
        if (fila>=0){
            setValueAt(new Integer(Rating),fila,2);
        }
    }
    
    int filaDelJugador(String Nombre){
        
        String aux = new String();
        for(int i=0; i< getRowCount(); i++){
            aux = (String)  getValueAt(i,1);
            if(Nombre.equals(aux))
                return i;
        }
        return -1; //si no encontro el jugador en ninguna fila
    }
}


