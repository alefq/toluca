package py.edu.uca.fcyt.toluca;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.Vector;

import py.edu.uca.fcyt.toluca.game.TrucoPlayer;


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
    
    public JButtonTable(){
       /*
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        dm.setDataVector(new Object[][]{{"1","Observar","Jugar1","Jugar2","Jugar3","Jugar4","Jugar5","Jugar6","Fulano"}},
        new Object[]{"Mesa#","Observar","Jugador1","Jugador2",
        "Jugador3","Jugador4","Jugador5","Jugador6","Observando"});
        
        JTable table = new JTable(dm);
        table.getColumn("Observar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Observar").setCellEditor(new ButtonEditor());
        
        table.getColumn("Jugador1").setCellRenderer(new ButtonRenderer());
        table.getColumn("Jugador1").setCellEditor(new ButtonEditor());
        
        table.getColumn("Jugador2").setCellRenderer(new ButtonRenderer());
        table.getColumn("Jugador2").setCellEditor(new ButtonEditor());
        
        table.getColumn("Jugador3").setCellRenderer(new ButtonRenderer());
        table.getColumn("Jugador3").setCellEditor(new ButtonEditor());
        
        table.getColumn("Jugador4").setCellRenderer(new ButtonRenderer());
        table.getColumn("Jugador4").setCellEditor(new ButtonEditor());
        
        table.getColumn("Jugador5").setCellRenderer(new ButtonRenderer());
        table.getColumn("Jugador5").setCellEditor(new ButtonEditor());
        
        table.getColumn("Jugador6").setCellRenderer(new ButtonRenderer());
        table.getColumn("Jugador6").setCellEditor(new ButtonEditor());
        */
        
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
            JTable jtable = new JTable(mtm);
            System.out.println("Despues de creat la tabla");

            //TableColumn nivelColumn = jtable.getColumn("Mesa #");
            //nivelColumn.setPreferredWidth(30);
            
            jtable.getColumn("Observar").setCellRenderer(new py.edu.uca.fcyt.toluca.ButtonRenderer());
            jtable.getColumn("Observar").setCellEditor(new py.edu.uca.fcyt.toluca.ButtonEditor());
        
            jtable.getColumn("Jugador1").setCellRenderer(new ButtonRenderer());
            jtable.getColumn("Jugador1").setCellEditor(new ButtonEditor());
        
            jtable.getColumn("Jugador2").setCellRenderer(new ButtonRenderer());
            jtable.getColumn("Jugador2").setCellEditor(new ButtonEditor());
        
            jtable.getColumn("Jugador3").setCellRenderer(new ButtonRenderer());
            jtable.getColumn("Jugador3").setCellEditor(new ButtonEditor());
        
            jtable.getColumn("Jugador4").setCellRenderer(new ButtonRenderer());
            jtable.getColumn("Jugador4").setCellEditor(new ButtonEditor());
        
            jtable.getColumn("Jugador5").setCellRenderer(new ButtonRenderer());
            jtable.getColumn("Jugador5").setCellEditor(new ButtonEditor());
        
            jtable.getColumn("Jugador6").setCellRenderer(new ButtonRenderer());
            jtable.getColumn("Jugador6").setCellEditor(new ButtonEditor());
        
            //jtable.setDefaultRenderer(jtable.getColumnClass(0), new RankingTable.RankingCellRenderer());
        JScrollPane scroll = new JScrollPane(jtable);
        add( scroll );
        setPreferredSize( new Dimension(680,300) );
        setVisible(true);
        
        
    }
    
    void addPlayer(TrucoPlayer player, int row, int col){
        mtm.addPlayer(player, row, col);
    }
    
    void removeplayer(TrucoPlayer player){
        mtm.removePlayer(player);	
    }

    void modifyplayer(TrucoPlayer player){
        mtm.modifyPlayer(player);
    }
    
    void insertarFila(){
        System.out.println("Se quiere insertar la fila en el MainTable");
        mtm.insertRow();
    }
        
    void eliminarFila(int row){
         System.out.println("Se quiere remover la fila en el MainTable");
        mtm.removeRow(row);
    }
    /*
    void insertarFila() {
        
        ultimaFila++;
        Integer Inte = new Integer(ultimaFila);
        String nuevoNumero = Inte.toString();
        
        Object[] data = {nuevoNumero,"Observar","Jugar1","Jugar2","Jugar3","Jugar4",
        "Jugar5","Jugar6",""};
        dm.addRow(data);
    }
    
    //elimina la fila numero "row"
    void eliminarFila(int row) {
        System.out.println("Se elimino la fila nº=" +row);
        if( row <= dm.getRowCount() )
            dm.removeRow(row);
        System.out.println("numeroDeFilas=" + dm.getRowCount() );
    }
    
    boolean isCellEditable(int row, int column){
        return false;
    }
    */
    
}
