
package py.edu.uca.fcyt.toluca.guinicio;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;


import javax.swing.JLabel;
/**
 * @author Dani Cricco
 *
 *14-mar-2005
 */
public class ConexionTestPanel extends JPanel{
    
	private JLabel jLabel = null;
	private JLabel labelConexion;
	private int intervalo=5;
	private int cont=0;
	private float test[];
	
	
	
    public ConexionTestPanel()
    {
        super();
 		
       test=new float[intervalo];
       initialize();
    }
    
    

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        
        this.setLayout(new BorderLayout());
        
        setBorder(BorderFactory.createEtchedBorder());
        this.add(getJLabel(), BorderLayout.EAST);
        this.add(new JLabel("Conexión"),BorderLayout.CENTER);
        
			
	}
	private Color getColorForMs(double ms)
	{
	    if(ms<30)
	        return Color.GREEN;
	    if(ms>30 && ms <40)
	        return Color.BLUE;
	    if(ms >40 && ms <55)
	        return Color.YELLOW;
	    return Color.RED;
	        
	}
	private JLabel getLabelConexion()
	{
	    if(labelConexion==null)
	    {
	        labelConexion=new JLabel("Conexión: ",JLabel.RIGHT);
	        
	    }
	    return labelConexion;
	}
	private String getStringForMs(double ms)
	{
	    if(ms<30)
	        return "Excelente";
	    if(ms>30 && ms <40)
	        return "Muy Buena";
	    if(ms >40 && ms <55)
	        return "Buena";
	    return "Aceptable";
	}
	public void actualizar(float ms)
	{

	    getJLabel().setOpaque(true);
		 getJLabel().setText("           ");
		 for(int i=0;i<test.length;i++)
		     ms+=test[i];
		 
		 ms=ms/intervalo;
		 String text=getStringForMs(ms)+" - "+ms+"ms.";
		 getJLabel().setToolTipText(text);
		 getJLabel().setBackground(getColorForMs(ms));
		 getLabelConexion().setToolTipText(text);
		 
		 
	    if(cont>=intervalo-1)
	        cont=0;
	    else
	        test[cont++]=ms;
	    
	}
	private JLabel getJLabel()
	{
	    if(jLabel==null)
	    {
	        jLabel = new JLabel();
	    }
	    return jLabel;
	}
    public int getIntervalo() {
        return intervalo;
    }
    public void setIntervalo(int intervaloDeAct) {
        this.intervalo = intervaloDeAct;
    }
}
