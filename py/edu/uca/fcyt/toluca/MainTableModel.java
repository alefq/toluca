/*
 * MainTableModel.java
 *
 * Created on 3 de junio de 2003, 11:31 AM
 */

package py.edu.uca.fcyt.toluca;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.Vector;

import py.edu.uca.fcyt.toluca.game.TrucoPlayer;

/**
 *
 * @author  Interfaz de Inicio
 */
public class MainTableModel extends AbstractTableModel{
    
    final String[] columnNames =    {  "Mesa#", "Observar", "Jugador1", "Jugador2", 
                    "Jugador3", "Jugador4", "Jugador5", "Jugador6", "Observando"  };
    
    Vector filas= new Vector();
    
    /*
     * Devuelve la cantidad de columnas que tiene la tabla
     */
    public int getColumnCount() {
        return columnNames.length;
    }
    
    /*
     * Devuelve la cantidad de filas que tiene la tabla
     */
    public int getRowCount() {
        return filas.size();
    }
    
    public int getNumeroDeMesa(int row){
        Fila fila = (Fila) filas.elementAt(row);
        return fila.getFilaNumber();
    }
    
    /*
     * Retorna el valor de la tabla en la posicion row, col
     */
    public Object getValueAt(int row, int col) {
 
        String ret = "";
        if (row < filas.size()) {
            Fila fila = (Fila)filas.get(row);
            
            if(col==0)
              ret = String.valueOf( fila.getFilaNumber() ); 
             
            else if(col==1)
              ret = "Observar";
            
            else if(col>1 && col<8)
              ret = String.valueOf( fila.getPlayer(col));
            
            else if(col==8)
                for(int i=0; i<fila.cuantosObservadores();i++)
                    ret = ret.concat( String.valueOf( fila.getObservador(i) ));
            
            else
             System.out.println("Columna no definida");
          
        }//if row < filas.size()
        return ret; 
    }
    
    /*
     * Devuelve el nombre de la columna en la posicion col.
     */ 
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    public boolean isCellEditable(int row, int col) {
        return false;
    }
    
    /*
     * Coloca en la tabla el valor "value" en la posicion row, col.
     * row es el numero de fila que se encuentra en la tabla principal, 
     * no el numero de mesa.
     */
    public void setValueAt(Object value, int row, int col) {
        
        Fila fila = (Fila) filas.get(row);
        if (col>1 && col<8)
            fila.setPlayer( (TrucoPlayer) value, col);    
        else if( col == 8)
            fila.setObservador((TrucoPlayer) value);
        
        fireTableDataChanged();   
    }
    
    /*
     * Se inserta una fila/mesa
     */ 
    public void insertRow(int numeroDeMesa) {
        Fila fila = new Fila(numeroDeMesa);
        Object[] obj = new Object[1];
        obj[0] = fila;
        
        System.out.println("Se inserta mesa numero=" + numeroDeMesa);
        insertRow( obj, getRowCount());
    }
    
    public void insertRow(Object[] value, int row) {
        filas.add(value[0]);
        //filas.insertElementAt(value[0], row);
        System.out.println("Aca se agrega la fila");
        fireTableRowsInserted(row, row);
    }
    
    /*
     * Elimina la mesa numero row de la tabla.
     */
    public void removeRow(int row) {
        Fila fila = new Fila();
        for(int i=0; i<filas.size();i++){ //recorre el vector de filas
            fila = (Fila)filas.get(i);
            if( fila.getFilaNumber() == row){ //busca la fila que me interesa
                filas.remove(i);
                System.out.println("Aca se remueve la fila");
                fireTableRowsDeleted(i, i);
                i = filas.size() + 10; // para que salga del ciclo
            }
        }
    }
    
    /*
     * Modifica el player que se encuentra en la fila/mesa con numero de mesa "row"
     */
    public void modifyRow(int row, TrucoPlayer player) {
        Fila fila = new Fila();
        for(int i=0; i<filas.size();i++){
            fila = (Fila)filas.get(i);
            if( fila.getFilaNumber() == row){ // si es la fila correcta
                filas.set(i,player);          // se modifica el player
                fireTableDataChanged();
                i = filas.size() + 10;
            }
        }
    }
    /*
     * Se le agrega al player a la mesa numero tableNumber
     */
    public void addPlayer(TrucoPlayer player, int tableNumber){
        Fila fila = new Fila();
        
        for(int i=0; i<filas.size(); i++){
            fila = (Fila) filas.get(i);
            if( fila.getFilaNumber() == tableNumber)
                fila.addPlayer(player);
        }
    }
    
    /*
     * Agrega un jugador a la tabla principal, en la posicion row, column.
     * Si el player quiere observar, se controla que no este observando ya.
     * Si el player quiere jugar, se controla que no juegue dos veces en la misma mesa.
     */
     public void addPlayer(TrucoPlayer player, int row, int column) {
         Fila fila = (Fila) filas.get(row);
         
         if( fila.isPlayer(player) == 0){
            
             if(column == 1){ 
                 if( fila.isObservador(player) == false)
                     setValueAt(player, row, column);
             } else if ( column>1 && column<8){
                     setValueAt(player, row, column);
             }    
         } else {
            System.out.println("El jugador ya se encuentra en esa mesa");
         }
     }
    
    /*
     * El jugador "player" se elimina de la tabla principal.
     */
    public void removePlayer(TrucoPlayer player){
        Fila fila = new Fila();
        int columnaJugador = 0;
        
        for(int i=0; i<filas.size();i++){
            fila = (Fila)filas.get(i);
            columnaJugador = fila.isPlayer(player);
            if( columnaJugador > 0){
                setValueAt( new String("Jugar"), i, columnaJugador - 1);
                
            } 
        }
    }
    
    /*
     * Se modifican los datos del jugador "player" en la tabla principal.
     */
    public void modifyPlayer(TrucoPlayer player){
        Fila fila = new Fila();
        int columnaJugador = 0;
        
        for(int i=0; i<filas.size();i++){
            fila = (Fila)filas.get(i); // se mira una fila
            columnaJugador = fila.isPlayer(player); //se ve si el jugador esta en esa fila
            if( columnaJugador > 0){
                setValueAt( player, i, columnaJugador - 1);
                
            } 
        }
    }
    
}// Fin de la Clase MainTableModel
