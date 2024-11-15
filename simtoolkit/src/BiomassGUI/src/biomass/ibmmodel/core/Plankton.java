package biomass.ibmmodel.core;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import biomass.simulator.gui.BioMASSGUIFrame;


public class Plankton extends Autotroph {
	private double maxBiomass;

	public Plankton(FunctionalGroup fg, Population population, double concentration, double length, double x, double y)
	{
		this.fg=fg;
		this.population=population;
		this.length=length;
		radio=length/2;
		biomass=new Biomass(population);
		volume=4*Math.PI*Math.pow(radio, 3)/3;
		maxBiomass=volume*fg.maxBiomassConcentration;
		biomass.addLeanMass(volume*concentration*fg.optimalLeanMassFraction);
		biomass.addFatMass(volume*concentration*fg.optimalFatMassFraction);
		this.x=x;
		this.y=y;
		drawablecolor=Color.GREEN;
		agesecs=(int)(BioMASSGUIFrame.getInstance().r.nextDouble()*3600);
		//System.out.println("Concentraci—n: "+biomass.getBiomass()/volume);
	}


	public void update(double dt)
	{
		double biomassincrement=biomass.getBiomass()*dt*fg.k*(1-biomass.getBiomass()/maxBiomass);
		double leanincrement=biomassincrement*biomass.getLeanMass()/biomass.getBiomass();
		double fatincrement=biomassincrement*biomass.getFatMass()/biomass.getBiomass();	
		biomass.addLeanMass(leanincrement);	
		biomass.addFatMass(fatincrement);
	}


	@Override
	public void hourlystep() {
		// TODO Auto-generated method stub
		update(1);
	}

	@Override
	public void dailystep() {
		// TODO Auto-generated method stub
	}

	

	/* (non-Javadoc)
	 * @see multiagent.model.agent.ReactiveAgent#performBehavior()
	 */
	@Override
	public void performBehavior() {
		// TODO Auto-generated method stub
	}


	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		Color c=new Color(0, (int)(255*(biomass.getBiomass()/volume)/fg.maxBiomassConcentration),0);
		
		//Transparencia
		//Composite originalComposite = g2d.getComposite(); 
		//g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
	
		g2d.setPaint(c); 
		double scale=length*2;
		Ellipse2D plankton_shape = new Ellipse2D.Double(getX()-scale/2,getY()-scale/2,scale,scale);
		//Rectangle2D plankton_shape2 = new Rectangle2D.Double(getX()-scale,getY()-scale,scale*2,scale*2);
		
		//Ellipse2D fish_shape = new Ellipse2D.Double(x-lenght/2,y-lenght/2,lenght,lenght);
		g2d.fill(plankton_shape);	
		
		//Transparencia
		//g2d.setComposite(originalComposite);
	}
}
