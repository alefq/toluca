package py.edu.uca.fcyt.toluca;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;


public class ButtonRenderer extends JButton implements TableCellRenderer {

  public ButtonRenderer() {
   
    setOpaque(true);
    
  }
  
  /*
   * Returns the component used for drawing the cell. 
   * This method is used to configure the renderer appropriately before drawing.
   */
  public Component getTableCellRendererComponent(JTable table, Object value,
                   boolean isSelected, boolean hasFocus, int row, int column) {
    if (isSelected) {
      setForeground(table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    } else {
      setForeground(table.getForeground());
      setBackground(UIManager.getColor("Button.background"));
    }
    setText( (value ==null) ? "" : value.toString() );
    return this;
  }
}

