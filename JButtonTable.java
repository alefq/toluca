package py.edu.uca.fcyt.toluca;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
//import jp.gr.java_conf.tame.swing.table.*;


public class JButtonTable extends JPanel {
    
    
    private DefaultTableModel dm = new DefaultTableModel();
    static int ultimaFila = 1;
    
    public JButtonTable(){
        //super( "TrucoOnLineUCA" );
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        dm.setDataVector(new Object[][]{{"1","Observar","Jugar1","Jugar2","Jugar3","Jugar4","Jugar5","Jugar6","Fulano"}},
        new Object[]{"Mesa#","Observar","Jugador1","Jugador2",
        "Jugador3","Jugador4","Jugador5","Jugador6","Observando"});
        
        JTable table = new JTable(dm);
        table.getColumn("Observar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Observar").setCellEditor(new ButtonEditor(/*new JCheckBox()*/));
        
        table.getColumn("Jugador1").setCellRenderer(new ButtonRenderer());
        table.getColumn("Jugador1").setCellEditor(new ButtonEditor(/*new JCheckBox()*/));
        
        table.getColumn("Jugador2").setCellRenderer(new ButtonRenderer());
        table.getColumn("Jugador2").setCellEditor(new ButtonEditor(/*new JCheckBox()*/));
        
        table.getColumn("Jugador3").setCellRenderer(new ButtonRenderer());
        table.getColumn("Jugador3").setCellEditor(new ButtonEditor(/*new JCheckBox()*/));
        
        table.getColumn("Jugador4").setCellRenderer(new ButtonRenderer());
        table.getColumn("Jugador4").setCellEditor(new ButtonEditor(/*new JCheckBox()*/));
        
        table.getColumn("Jugador5").setCellRenderer(new ButtonRenderer());
        table.getColumn("Jugador5").setCellEditor(new ButtonEditor(/*new JCheckBox()*/));
        
        table.getColumn("Jugador6").setCellRenderer(new ButtonRenderer());
        table.getColumn("Jugador6").setCellEditor(new ButtonEditor(/*new JCheckBox()*/));
        
        JScrollPane scroll = new JScrollPane(table);
        add( scroll );
        //setSize( 800, 200 );
        setVisible(true);
        
        
    }
    
    void insertarFila() {
        
        ultimaFila++;
        Integer Inte = new Integer(ultimaFila);
        String nuevoNumero = Inte.toString();
        
        Object[] data = {nuevoNumero,"Observar","Jugar1","Jugar2","Jugar3","Jugar4",
        "Jugar5","Jugar6","Fulano"};
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
    
    
}
