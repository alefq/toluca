package py.edu.uca.fcyt.toluca;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.Vector;

import py.edu.uca.fcyt.toluca.game.TrucoPlayer;
import py.edu.uca.fcyt.toluca.table.*;

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
    
    public JButtonTable(){
        
        System.out.println("Se instancia el MainTableModel");
            
        encabezado.add(new String("Mesa #"));
        encabezado.add(new String("Observar"));
        encabezado.add(new String("Jugador1"));        
        encabezado.add(new String("Jugador2"));        
        encabezado.add(new String("Jugador3"));        
        encabezado.add(new String("Jugador4"));        
        encabezado.add(new String("Jugador5"));        
        encabezado.add(new String("Jugador6"));        
        encabezado.add(new String("Observando"));        
            
        System.out.println("Se instancia el RankingTableModel-i");
        mtm = new MainTableModel();
        System.out.println("Se termina de instanciarddddd");
          
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        System.out.println("Antes de crear la tabla");
        jtable = new JTable(mtm);
        System.out.println("Despues de creat la tabla");

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
       return mtm.getNumeroDeMesa(rowNumber); 
    }
    
    /*
     * Este metodo mete en la mesa numero "tableNumber" al
     * jugador "player", 
     */ 
    void addPlayer(TrucoPlayer player, int tableNumber){
        mtm.addPlayer(player, tableNumber);
    }
    
    /*
     * Este metodo sirve cuando el jugador hace click en un boton
     * de la tabla.
     */
    void addPlayer(TrucoPlayer player, int row, int col){
        mtm.addPlayer(player, row, col);
    }
    
    void removeplayer(TrucoPlayer player){
        mtm.removePlayer(player);	
    }

    void modifyplayer(TrucoPlayer player){
        mtm.modifyPlayer(player);
    }
    
    void insertarFila(Table table){
        System.out.println("Se quiere insertar la fila en el MainTable");
        mtm.insertRow(table.getTableNumber());
    }
        
    void eliminarFila(int row){
         System.out.println("Se quiere remover la fila en el MainTable");
        mtm.removeRow(row);
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
         * Este renderer es provisorio, sirve para las celdas
         * de la tabla que son JLabel.
         */
        public class MainTableCellRenderer implements TableCellRenderer {
            
            public Component getTableCellRendererComponent(JTable table, Object value, 
                             boolean isSelected, boolean hasFocus,int row, int column) {
                
                JLabel lab = new JLabel((value ==null) ? "Jugar" : value.toString() );
                return lab;
            }
               
            
        } // Fin del renderer
    
}
