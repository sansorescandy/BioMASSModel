/**
 * 
 */
package biomass.simulator.core;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import multiagent.model.environment.Space;
import biomass.simulator.gui.DrawableObject;

/**
 * @author candysansores
 *
 */
@SuppressWarnings("serial")
public class Display extends JPanel {
	/**
	 * 
	 */
	private Graphics2D g2d;
	private Space seaSpace;
	private double viewportX=1536;
	private double viewportY=960;
	

	/**
	 * 
	 */
	public Display(Space seaSpace) {
		// TODO Auto-generated constructor stub
		this.seaSpace=seaSpace;
		Dimension dim=new Dimension();
		viewportX=Toolkit.getDefaultToolkit().getScreenSize().width;
		viewportY=Toolkit.getDefaultToolkit().getScreenSize().height*0.85;
		dim.setSize(viewportX, viewportY);
		setPreferredSize(dim);
		setBackground(Color.white);
		
	}

	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		g2d = (Graphics2D) g;
		g2d.translate(getWidth()/2, getHeight()/2);
		g2d.scale(viewportX/seaSpace.getWidth(), viewportY/seaSpace.getHeight());		
		Bag drawableobjects = seaSpace.getAllObjects();
		for(int i=0;i<drawableobjects.numObjs;i++)  {
			((DrawableObject)(drawableobjects.objs[i])).draw(g2d);
		}
	}
	
	public Dimension getMinimumSize() { 
		return getPreferredSize();  
	}
	
	public void step() {
		repaint();
	}
	
}
