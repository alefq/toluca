package py.edu.uca.fcyt.toluca.table;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class Actions extends JButton implements ChangeListener
{
	JSlider jSlider;
	JLabel fps; 

	//Constructor	
	public Actions
	(
		JButton[] buttons, ActionListener actListener,
		ChangeListener chListener
	) 
	{	

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i].setPreferredSize(new Dimension(100, 22));
			buttons[i].setMaximumSize(new Dimension(100, 22));
			buttons[i].addActionListener(actListener);
			add(buttons[i]);
		}
		add(fps = new JLabel("FPS: 50"));
		add(jSlider = new JSlider(1, 100, 50));
		jSlider.addChangeListener(chListener);
		jSlider.addChangeListener(this);
		
		setBorder(null);
	}
	
	public int getValue()
	{
		return jSlider.getValue();
	}
		
	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent arg0)
	{
		fps.setText("FPS: " + jSlider.getValue());
	}

}