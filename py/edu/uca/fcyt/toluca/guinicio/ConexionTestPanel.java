
package py.edu.uca.fcyt.toluca.guinicio;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.FlowLayout;
import javax.swing.JLabel;
/**
 * @author Dani Cricco
 *
 *14-mar-2005
 */
public class ConexionTestPanel extends JPanel{
    
	private JLabel jLabel = null;
    public ConexionTestPanel()
    {
        super();
 		initialize();
       actualizar(20);
    }
    

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        
        this.setLayout(new BorderLayout());
        
        setBorder(BorderFactory.createEtchedBorder());
        this.add(getJLabel(), BorderLayout.CENTER);
        this.add(new JLabel("Conexión"),BorderLayout.NORTH);
        
			
	}
	private Color getColorForMs(long ms)
	{
	    if(ms<30)
	        return Color.GREEN;
	    if(ms>30 && ms <40)
	        return Color.BLUE;
	    if(ms >40 && ms <55)
	        return Color.YELLOW;
	    return Color.RED;
	        
	}
	private String getStringForMs(long ms)
	{
	    if(ms<30)
	        return "Excelente";
	    if(ms>30 && ms <40)
	        return "Muy Buena";
	    if(ms >40 && ms <55)
	        return "Buena";
	    return "Aceptable";
	}
	public void actualizar(long ms)
	{
	    System.out.println("Se va a actualizar el ms: "+ms);
	 getJLabel().setBackground(getColorForMs(ms));
	 getJLabel().setOpaque(true);
	 getJLabel().setText(getStringForMs(ms)+" - "+ms+"ms.");
	}
	private JLabel getJLabel()
	{
	    if(jLabel==null)
	    {
	        jLabel = new JLabel();
	    }
	    return jLabel;
	}
}
