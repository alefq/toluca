/*
 * MainTableModel.java
 *
 * Created on 3 de junio de 2003, 11:31 AM
 */

package py.edu.uca.fcyt.toluca;


import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import py.edu.uca.fcyt.toluca.game.TrucoPlayer;

/**
 *
 * @author  Interfaz de Inicio
 */
public class MainTableModel extends AbstractTableModel
{
	
    final String[] columnNames =
    {  "Mesa#", "Observar", "Jugador1", "Jugador2",
       "Jugador3", "Jugador4", "Jugador5", "Jugador6", "Observando"  };
	   
    Vector filas = new Vector();
	   
    /*
     * Devuelve la cantidad de columnas que tiene la tabla
     */
    public int getColumnCount()
    {
        return columnNames.length;
    }
	   
    /*
     * Devuelve la cantidad de filas que tiene la tabla
     */
    public int getRowCount()
    {
        return filas.size();
    }
	   
    public int getNumeroDeMesa(int row)
    {
        Fila fila = (Fila) filas.elementAt(row);
        return fila.getFilaNumber();
    }
	   
    /*
     * Retorna el valor de la tabla en la posicion row, col
     */
    public Object getValueAt(int row, int col)
    {
        String ret = "";
	if (row < filas.size())
	{
            Fila fila = (Fila) filas.get(row);
			   
            if(col==0)
                ret = String.valueOf( fila.getFilaNumber() );
            else if(col==1)
                ret = "Observar";
                else if(col>1 && col<8)
                    ret = fila.getPlayerName(col-2);
                  //  ret = String.valueOf( fila.getPlayer(col));
                    else if(col==8)
                        for(int i=0; i<fila.cuantosObservadores();i++){
                            ret = ret.concat( String.valueOf( fila.getObservador(i) ));
                            ret = ret.concat(", ");
                        }
                        else
                            System.out.println("Columna no definida");
        }//if row < filas.size()
       
	return ret;
    }
	   
    /*
    * Devuelve el nombre de la columna en la posicion col.
    */
    public String getColumnName(int col)
    {
        return columnNames[col];
    }
	   
    public boolean isCellEditable(int row, int col)
    {
        return false;
    }
	   
    /*
    * Coloca en la tabla el valor "value" en la posicion row, col.
     * row es el numero de fila que se encuentra en la tabla principal,
     * no el numero de mesa.
     */
    public void setValueAt(Object value, int row, int col)
    {
        Fila fila = (Fila) filas.get(row);
        System.out.println("Dentro de setValueAt Clase Main Table Model");
        fila.impJugadores();
		if (col>1 && col<8){
            System.out.println("Agregando el player a la Tabla Principal");
            fila.setPlayer(value, col-2);
        } else if( col == 8){
            System.out.println("Agregando el observador a la Tabla Principal");
            fila.setObservador(value);
        }
        fireTableDataChanged();
    }

    /*
     * Se le agrega al player como observador
     */
    public void addObserver(TrucoPlayer player, int TableNumber){
       Fila filaTmp = new Fila();
       for(int i=0; i<filas.size(); i++){
            filaTmp = (Fila) filas.get(i);
            if( filaTmp.getFilaNumber() == TableNumber)
                setValueAt(player.getName(), i, 8);
       }
    }
    
    /*
    * Se inserta una fila/mesa
    */
    public void insertRow(int numeroDeMesa)
    {
        Fila fila = new Fila(numeroDeMesa);
	Object[] obj = new Object[1];
	obj[0] = fila;
		   
	System.out.println("Se inserta mesa numero=" + numeroDeMesa);
	insertRow( obj, getRowCount());
    }
	   
   public void insertRow(Object[] value, int row)
   {
        filas.add(value[0]);
       //filas.insertElementAt(value[0], row);
        System.out.println("Aca se agrega la fila");
	fireTableRowsInserted(row, row);
    }
	   
    /*
    * Elimina la mesa numero "tableNumber" de la Tabla.
    */
    public void removeRow(int tableNumber)
    {
        Fila fila = new Fila();
	for(int i=0; i<filas.size();i++)
	{                                       //recorre el vector de filas
            fila = (Fila)filas.get(i);
            if( fila.getFilaNumber() == tableNumber)
            {                                   //busca la fila que me interesa
                filas.remove(i);
		System.out.println("Aca se remueve la fila");
		fireTableRowsDeleted(i, i);
		i = filas.size() + 10;          // para que salga del ciclo
            }
	}
    }
	   
    /*
    * Modifica el player que se encuentra en la fila/mesa con numero de mesa "row"
    */
    public void modifyRow(int row, TrucoPlayer player)
    {
        new Exception("").printStackTrace(System.out);
	Fila fila = new Fila();
	for(int i=0; i<filas.size();i++)
	{
            fila = (Fila)filas.get(i);
            if( fila.getFilaNumber() == row)
            {                                   // si es la fila correcta
                filas.set(i,player.getName());          // se modifica el player
		fireTableDataChanged();
		i = filas.size() + 10;
            }
	}
    }
	
    /*
    * Se le agrega al player a la mesa numero tableNumber
    */
    public void addPlayer(TrucoPlayer player, int tableNumber, int chair)
    {
        new Exception("").printStackTrace(System.out);
		Fila fila=null;
	   
		for(int i=0; i<filas.size(); i++)
		{
            fila = (Fila) filas.get(i);
            if( fila.getFilaNumber() == tableNumber)
            {
            	System.out.println("Agregando el player a la Tabla numero:"+tableNumber);
            	System.out.println("El player: "+player.getName()+" Silla: "+chair);
            	fila.impJugadores();
            	setValueAt(player.getName(), i, chair + 2);
            	System.out.println("Despues de setear dentro de addplayer mtm");
            	fila.impJugadores();
                //fila.addPlayer(player.getName());
			}
        }
    }
	   
    /*
    * Agrega un jugador a la tabla principal, en la posicion row, column.
    * Si el player quiere observar, se controla que no este observando ya.
    * Si el player quiere jugar, se controla que no juegue dos veces en la misma mesa.
    */
    /*
    public void addPlayer(TrucoPlayer player, int row, int column)
    {
        Fila fila = (Fila) filas.get(row);
		if( fila.isPlayer(player.getName()) == 0)   // si no esta jugando en la mesa
             if ( column>1 && column<8) //si quiere jugar
				setValueAt(player.getName(), row, column);
        
    }deprecated by Leti P
	  */ 
    /*
    * El jugador "player" se elimina de la tabla principal.
    * Se le elimina de todas las mesas donde este jugando.
    */
    public void removePlayer(TrucoPlayer player)
    {
        Fila fila = new Fila();
		int columnaJugador = 0;
			   
		for(int i=0; i<filas.size();i++)
		{
	    	fila = (Fila)filas.get(i);
	        columnaJugador = fila.isPlayer(player.getName());
	        if( columnaJugador > 0)
	        {
	            setValueAt( new String("Libre"), i, columnaJugador - 1);
	        }
		}
    }
    
    /*
     * El jugador "player" se elimina de la mesa "tableNumber"
     */
    public void removePlayer(TrucoPlayer player, int tableNumber){
        
        Fila fila = new Fila();
        int chair = 0;
        for(int i=0; i<filas.size(); i++){
            fila = (Fila)filas.get(i);
            if ( fila.getFilaNumber()== tableNumber){
            	chair = fila.isPlayer(player.getName()) - 1;
            	System.out.println("Se levanto="+ player.getName()+" de la mesa"+tableNumber+" de la silla="+chair);
				
				setValueAt( new String("Libre"), i, chair+2);
                
                //fila.removePlayer(player.getName());
               // fireTableDataChanged();
            }
        }
    }
    
    
    /*
     * Se modifican los datos del jugador "player" en la tabla principal.
     */
    public void modifyPlayer(TrucoPlayer player)
    {
        Fila fila = new Fila();
	int columnaJugador = 0;
		   
	for(int i=0; i<filas.size();i++)
	{
            fila = (Fila)filas.get(i); // se mira una fila
            columnaJugador = fila.isPlayer(player.getName()); //se ve si el jugador esta en esa fila
            if( columnaJugador > 0)
            {
                setValueAt( player.getName(), i, columnaJugador - 1);
            }
	}
    }
    
    /*
     * Setea el status de la mesa "tableNumber".
     */
    public void setGameStatus(int tableNumber, boolean status)
    {
        Fila fila = new Fila();
	for(int i=0; i<filas.size(); i++)
	{                                       //recorre el vector de filas
            fila = (Fila)filas.get(i);
            if( fila.getFilaNumber() == tableNumber)
            {                                   //busca la fila que me interesa
                fila.setGameStatus(status);     // setea el status de la mesa
		i = filas.size() + 10;          // para que salga del ciclo
            }
	}
    }
    
    /*
     * Retorna el status de la mesa que se encuentra almacenada en la 
     * fila "row" de la Tabla Principal
     */
    public boolean getGameStatus(int row){
        
        Fila fila = new Fila();
	fila = (Fila)filas.get(row);
        return fila.getGameStatus();
        
    }
    
}// Fin de la Clase MainTableModel
