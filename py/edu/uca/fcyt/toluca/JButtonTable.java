package py.edu.uca.fcyt.toluca;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import py.edu.uca.fcyt.toluca.game.TrucoPlayer;
import py.edu.uca.fcyt.toluca.table.Table;

/**
 *
 * @author  Interfaz de Inicio
 */
public class JButtonTable extends JPanel {
    
    /*
    private DefaultTableModel dm = new DefaultTableModel();
    static int ultimaFila = 1;
    */
    
    MainTableModel mtm;
    Vector mesas = new Vector();
    Vector encabezado = new Vector();
    JTable jtable;
        
    ///////////////////////////////////////
  // operations
    
    public JButtonTable(){
        
       // System.out.println("Se instancia el MainTableModel");
            
        encabezado.add(new String("Mesa #"));
        encabezado.add(new String("Observar"));
        encabezado.add(new String("Jugador1"));        
        encabezado.add(new String("Jugador2"));        
        encabezado.add(new String("Jugador3"));        
        encabezado.add(new String("Jugador4"));        
        encabezado.add(new String("Jugador5"));        
        encabezado.add(new String("Jugador6"));        
        encabezado.add(new String("Observando"));        
            
        //System.out.println("Se instancia el MainTableModel-i");
        mtm = new MainTableModel();
       // System.out.println("Se termina de instanciar el MainTableModel-i");
          
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
       // System.out.println("Antes de crear la tabla");
        jtable = new JTable(mtm);
        //System.out.println("Despues de creat la tabla");

        //TableColumn nivelColumn = jtable.getColumn("Mesa #");
        //nivelColumn.setPreferredWidth(30);
            
            
        jtable.getColumn("Observar").setCellRenderer(new py.edu.uca.fcyt.toluca.ButtonRenderer());
        jtable.getColumn("Observar").setCellEditor(new py.edu.uca.fcyt.toluca.ButtonEditor2());
        
        jtable.getColumn("Jugador1").setCellRenderer(new JButtonTable.MainTableCellRenderer());
           // jtable.getColumn("Jugador1").setCellRenderer(new ButtonRenderer());
          //  jtable.getColumn("Jugador1").setCellEditor(new ButtonEditor());
        
        jtable.getColumn("Jugador2").setCellRenderer(new JButtonTable.MainTableCellRenderer());
         // jtable.getColumn("Jugador2").setCellRenderer(new ButtonRenderer());
         // jtable.getColumn("Jugador2").setCellEditor(new ButtonEditor());
        
        jtable.getColumn("Jugador3").setCellRenderer(new JButtonTable.MainTableCellRenderer());
      //      jtable.getColumn("Jugador3").setCellRenderer(new ButtonRenderer());
        //    jtable.getColumn("Jugador3").setCellEditor(new ButtonEditor());
            
        jtable.getColumn("Jugador4").setCellRenderer(new JButtonTable.MainTableCellRenderer());
            //jtable.getColumn("Jugador4").setCellRenderer(new ButtonRenderer());
           // jtable.getColumn("Jugador4").setCellEditor(new ButtonEditor());
        
        jtable.getColumn("Jugador5").setCellRenderer(new JButtonTable.MainTableCellRenderer());
         //  jtable.getColumn("Jugador5").setCellRenderer(new ButtonRenderer());
       //     jtable.getColumn("Jugador5").setCellEditor(new ButtonEditor());
        
        jtable.getColumn("Jugador6").setCellRenderer(new JButtonTable.MainTableCellRenderer());
            //jtable.getColumn("Jugador6").setCellRenderer(new ButtonRenderer());
          //  jtable.getColumn("Jugador6").setCellEditor(new ButtonEditor());
        
        
        //jtable.setDefaultRenderer(jtable.getColumnClass(0), new RankingTable.RankingCellRenderer());
        
        JScrollPane scroll = new JScrollPane(jtable);
        add( scroll );
        setPreferredSize( new Dimension(590,230) );
        setVisible(true);
          
    }
    
    /*
     * retorna en numero de mesa de que esta en la fila i.
     */
    public int getNumeroDeMesa(int rowNumber){
       if ( rowNumber >=0 && rowNumber < mtm.getRowCount() )
           return mtm.getNumeroDeMesa(rowNumber); 
       else
           return -1;
    }
    
    /*
     * Este metodo mete en la mesa numero "tableNumber" al
     * jugador "player", 
     */ 
    void addPlayer(TrucoPlayer player, int tableNumber, int chair){
        mtm.addPlayer(player, tableNumber, chair);
    }
    
    /*
     * Este metodo sirve cuando el jugador hace click en un boton
     * de la tabla.
     */
   /* void addPlayer(TrucoPlayer player, int row, int col){
        mtm.addPlayer(player, row, col);
    }
    */
    void addObserver(TrucoPlayer player, int tableNumber){
        mtm.addObserver(player, tableNumber);
    }
    
    /*
     * Remueve al player de todas las mesas de la Tabla Principal
     */
    void removeplayer(TrucoPlayer player){
        mtm.removePlayer(player);	
    }

    /*
     * Remueve al player de la mesa "tableNumber" de la Tabla Principal
     */
    public void removeplayer(TrucoPlayer player, int tableNumber){
        mtm.removePlayer(player, tableNumber);
    }
    
    void modifyplayer(TrucoPlayer player){
        mtm.modifyPlayer(player);
    }
    
    void insertarFila(Table table){
        //System.out.println("Se quiere insertar la fila en el MainTable");
        mtm.insertRow(table.getTableNumber());
    }
        
    /*
     * Elimina de la Tabla Principal a la mesa numero "tableNumber"
     */
    void eliminarFila(int tableNumber){
         //System.out.println("Se quiere remover la fila en el MainTable");
        mtm.removeRow(tableNumber);
    }
    
    /*
     * Retorna el numero de mesa que este seleccionado.
     * Si no se selecciono ninguna mesa, retorna -1.
     */
    public int isRowSelected(){
        
        for(int i=0; i<jtable.getRowCount(); i++)
            if( jtable.isRowSelected(i)==true )
                return i;
                
        return -1;
    }
    
    /*
     * Setea el status de juego de una mesa
     */
    public void setGameStatus(int tableNumber, boolean status){
        mtm.setGameStatus(tableNumber, status);
    }
    
    /*
     * Recibe un numero de fila de la Tabla Principal
     * y retorna "true" si el juego ya empezo, "false" de lo contrario
     */
    public boolean isGameStarted(int row){
        
        if ( mtm.getGameStatus(row) == true)
            return true;
        else
            return false;
               
    }
    
    
    /*
     * Este renderer es provisorio, sirve para las celdas
     * de la tabla que son JLabel.
     */
    public class MainTableCellRenderer implements TableCellRenderer {
          
        public Component getTableCellRendererComponent(JTable table, Object value, 
                        boolean isSelected, boolean hasFocus,int row, int column) {
                
            JLabel lab = new JLabel((value ==null) ? "Libre" : value.toString() );
            return lab;
        }       
    } // Fin del renderer
    
}
