/**
 * 
 */
package biomass.simulator.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import multiagent.model.environment.PhysicalObject;
import multiagent.model.environment.Space;

/**
 * @author candysansores
 *
 */
public class Display extends JPanel {
	private static final long serialVersionUID = -7031099119245167162L;
	private Graphics2D g2d;
	private Space environment;

	/**
	 * Panel of the simulation model to visualize the space
	 */
	public Display(Space environment) {
		this.environment=environment;
		Dimension dim=new Dimension();
		dim.setSize(environment.getWidth(), environment.getHeight());
		setPreferredSize(dim);
		setBackground(Color.WHITE);	
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
		g2d = (Graphics2D) g;
		ArrayList<PhysicalObject> drawableobjects = environment.getAllObjects();
		for(int i=0;i<drawableobjects.size();i++)  {
			((DrawableObject)(drawableobjects.get(i))).draw(g2d);
		}
	}
	
	public Dimension getMinimumSize() { 
		return getPreferredSize();  
	}
	
	public void step() {
		this.repaint();
	}
	
}
