package biomass.ibmmodel.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import biomass.simulator.core.BioMASSModel;
import biomass.simulator.gui.DrawableObject;
import multiagent.model.agent.Agent;
import multiagent.model.agent.PhysicalObject;

public class Refuge extends PhysicalObject implements DrawableObject {

	public Refuge(double x, double y) {
		this.x=x;
		this.y=y;
		radio=50;
	}


	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
        Color refugeeColor = new Color(24,130,170);
        Color refugeeDistColor = Color.BLACK;  
        
        //El refugio
        g2d.setPaint(refugeeColor);
        Ellipse2D refugee_shape = new Ellipse2D.Double(getX()-radio, getY()-radio, radio*2, radio*2);
        g2d.fill(refugee_shape);
        
        /*g2d.setPaint(refugeeDistColor);
	    double ds=100;
	    Ellipse2D refugee_dist_shape = new Ellipse2D.Double(x-ds/2,y-ds/2,ds,ds);
	    g2d.fill(refugee_dist_shape);*/
	}

}
